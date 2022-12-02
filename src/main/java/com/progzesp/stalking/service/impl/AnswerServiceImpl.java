package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.AnswerEto;
import com.progzesp.stalking.domain.mapper.AnswerMapper;
import com.progzesp.stalking.persistance.entity.AnswerEntity;
import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
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

            AnswerEntity template = new AnswerEntity();
            template.setUser(userEntity);
            template.setTask(prerequisiteTask);
            template.setChecked(true);
            template.setApproved(true);
            List<AnswerEntity> list = answerRepository.findAll(Example.of(template));

            if(list.isEmpty()) {
                return Pair.of(403, answerMapper.mapToETO(answerEntity));
            }
        }

        answerEntity = this.answerRepository.save(answerEntity);
        return Pair.of(200, answerMapper.mapToETO(answerEntity));
    }

    @Override
    public Pair<Integer, AnswerEto> modifyAnswer(Long id, AnswerEto answerEto, Principal user) {

        Optional<AnswerEntity> foundEntity = answerRepository.findById(id);
        if (foundEntity.isPresent()) {
            AnswerEntity answerEntity = foundEntity.get();

            if(answerEntity.getGame().getGameMasterId() != userRepo.getByUsername(user.getName()).getId()) {
                return Pair.of(403, new AnswerEto());
            }

            AnswerEntity answerToSave = answerMapper.mapToEntity(answerEto);

            try {
                copyNonStaticNonNull(answerEntity, answerToSave);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return Pair.of(200, answerMapper.mapToETO(answerEntity));
        }
        else
            return Pair.of(400, new AnswerEto());
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
                return Pair.of(403, new AnswerEto());
            }
            return Pair.of(200, answerMapper.mapToETO(answerEntity));
        }
        return Pair.of(400, new AnswerEto());
    }

    @Override
    public Pair<Integer, List<AnswerEto>> findAnswersByCriteria(Optional<Long> gameId, Optional<String> filter, Principal user) {
        AnswerEntity toFind = new AnswerEntity();

        boolean checkedFilter = false;
        boolean unchecked = false;
        if (filter.isPresent()) {
            if (filter.get().equals("checked")) {
                toFind.setChecked(true);
                checkedFilter = true;
            }
            else if (filter.get().equals("unchecked")) {
                toFind.setChecked(false);
                checkedFilter = true;
                unchecked = true;
            }
        }

        if (gameId.isPresent()) {
            Optional<GameEntity> optionalGame = gameRepo.findById(gameId.get());
            if (optionalGame.isPresent()) {
                GameEntity game = optionalGame.get();
                if(!unchecked && game.getGameMasterId() != userRepo.getByUsername(user.getName()).getId()) {
                    return Pair.of(403, new ArrayList<>());
                }
                toFind.setGame(game);

            }
            else {
                return Pair.of(400, new ArrayList<>());
            }
        }

        String[] paths = {"approved"};
        if(!checkedFilter) paths = new String[] {"approved", "checked"};
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnorePaths(paths);
        List<AnswerEntity> result = answerRepository.findAll(Example.of(toFind, exampleMatcher));
        return Pair.of(200, answerMapper.mapToETOList(result));
    }

}
