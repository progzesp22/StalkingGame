package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.answer.AnswerEto;
import com.progzesp.stalking.domain.answer.ModifyAnswerEto;
import com.progzesp.stalking.domain.answer.NoNavPosEto;
import com.progzesp.stalking.domain.mapper.AnswerMapper;
import com.progzesp.stalking.persistance.entity.answer.AnswerEntity;
import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import com.progzesp.stalking.persistance.entity.answer.*;
import com.progzesp.stalking.persistance.entity.UserEntity;
import com.progzesp.stalking.persistance.repo.AnswerRepo;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.TaskRepo;
import com.progzesp.stalking.persistance.repo.UserRepo;
import com.progzesp.stalking.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private AnswerRepo answerRepository;

    @Autowired
    private TaskRepo taskRepository;

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Pair<Integer, AnswerEto> save(AnswerEto newAnswer, Principal user) {

        AnswerEntity answerEntity = answerMapper.mapToEntity(newAnswer);

        if (!answerEntity.validate())
            return null;

        UserEntity userEntity = userRepo.getByUsername(user.getName());
        answerEntity.setUser(userEntity);

        // bind task with answer
        Optional<TaskEntity> optionalTask = taskRepository.findById(newAnswer.getTaskId());
        TaskEntity taskEntity = optionalTask.orElse(null);
        if(taskEntity == null) {
            return Pair.of(400, answerMapper.mapToETO(answerEntity));
        }
        answerEntity.setTask(taskEntity);

        // bind Game with answer
        Optional<GameEntity> optionalGame = gameRepo.findById(taskEntity.getGameId());
        GameEntity gameEntity = optionalGame.orElse(null);
        if(gameEntity == null) {
            return Pair.of(400, answerMapper.mapToETO(answerEntity));
        }
        answerEntity.setGame(gameEntity);

        // check if all prerequisite tasks are approved
        for(TaskEntity prerequisiteTask : taskEntity.getPrerequisiteTasks()) {
            AnswerEntity res = null;

            AnswerEntity[] entities = new AnswerEntity[] {new TextEntity(), new QREntity(),
                    new PhotoEntity(), new AudioEntity(), new NavPosEntity()};
            for (AnswerEntity toFind : entities) {
                toFind.setTask(prerequisiteTask);
                toFind.setChecked(true);
                toFind.setApproved(true);

                List<AnswerEntity> list = answerRepository.findAll(Example.of(toFind));
                for(AnswerEntity entity : list) {
                    if(entity.getUser().getTeam().getId() == userEntity.getTeamId()) {
                        res = list.get(0);
                    }
                }
            }

            if(res == null) {
                return Pair.of(403, answerMapper.mapToETO(answerEntity));
            }
        }

        answerEntity = this.answerRepository.save(answerEntity);
        return Pair.of(200, answerMapper.mapToETO(answerEntity));
    }

    @Override
    public Pair<Integer, ModifyAnswerEto> modifyAnswer(Long id, ModifyAnswerEto answerEto, Principal user) {

        Optional<AnswerEntity> foundEntity = answerRepository.findById(id);
        if (foundEntity.isPresent()) {
            AnswerEntity answerEntity = foundEntity.get();


            if(answerEntity.getGame().getGameMasterId() != userRepo.getByUsername(user.getName()).getId()) {
                return Pair.of(403, new ModifyAnswerEto());
            }

            AnswerEntity answerToSave = answerMapper.mapModifyAnswerEtoToEntity(answerEto);

            try {
                copyNonStaticNonNull(answerEntity, answerToSave);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return Pair.of(200, answerMapper.mapToModifyAnswerETO(answerEntity));
        }
        else
            return Pair.of(400, new ModifyAnswerEto());
    }

    @Override
    public boolean deleteAnswer(Long id) {

        Optional<AnswerEntity> answerOptional = answerRepository.findById(id);
        if (answerOptional.isEmpty()) {
            return false;
        }
        else {
            answerRepository.delete(answerOptional.get());
            return answerRepository.findById(id).isEmpty();
        }
    }

    @Override
    public Pair<Integer, AnswerEto> findAnswerById(Long id, Principal user) {
        final Optional<AnswerEntity> result = this.answerRepository.findById(id);
        if(result.isPresent()) {
            AnswerEntity answerEntity = result.get();
            Long userId = userRepo.getByUsername(user.getName()).getId();
            if(answerEntity.getUserId() != userId && answerEntity.getGame().getGameMasterId() != userId) {
                return Pair.of(403, new NoNavPosEto());
            }
            return Pair.of(200, answerMapper.mapToETO(answerEntity));
        }
        return Pair.of(400, new NoNavPosEto());
    }

    @Override
    public Pair<Integer, List<AnswerEto>> findAnswersByCriteria(Optional<Long> gameId, Optional<String> filter, Principal user) {
        List<AnswerEntity> result = new LinkedList<>();
        AnswerEntity[] entities = new AnswerEntity[] {new TextEntity(), new QREntity(),
                new PhotoEntity(), new AudioEntity(), new NavPosEntity()};
        for (AnswerEntity toFind : entities) {
            if (gameId.isPresent()) {
                Optional<GameEntity> game = gameRepo.findById(gameId.get());
                if (game.isPresent()) {
                    toFind.setGame(game.get());
                }
                else {
                    return Pair.of(400, new ArrayList<>());
                }
            }
            if (filter.isPresent()) {
                if (filter.get().equals("checked")) {
                    toFind.setChecked(true);
                }
                else if (filter.get().equals("unchecked")) {
                    toFind.setChecked(false);
                }
            }
            result.addAll(answerRepository.findAll(Example.of(toFind)));
        }

        return Pair.of(200, answerMapper.mapToETOList(result));
    }

}
