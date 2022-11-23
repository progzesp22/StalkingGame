package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.AnswerEto;
import com.progzesp.stalking.domain.mapper.AnswerMapper;
import com.progzesp.stalking.persistance.entity.AnswerEntity;
import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import com.progzesp.stalking.persistance.repo.AnswerRepo;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.TaskRepo;
import com.progzesp.stalking.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public AnswerEto save(AnswerEto newAnswer) {

        AnswerEntity answerEntity = answerMapper.mapToEntity(newAnswer);


        Optional<TaskEntity> optionalTask = taskRepository.findById(newAnswer.getTaskId());
        TaskEntity taskEntity = optionalTask.orElse(null);
        answerEntity.setTask(taskEntity);

        Optional<GameEntity> optionalGame = gameRepo.findById(newAnswer.getGameId());
        GameEntity gameEntity = optionalGame.orElse(null);
        answerEntity.setGame(gameEntity);

        answerEntity = this.answerRepository.save(answerEntity);
        return answerMapper.mapToETO(answerEntity);
    }

    @Override
    public AnswerEto modifyAnswer(Long id, AnswerEto answerEto) {

        Optional<AnswerEntity> foundEntity = answerRepository.findById(id);
        if (foundEntity.isPresent()) {
            AnswerEntity answerEntity = foundEntity.get();
            AnswerEntity answerToSave = answerMapper.mapToEntity(answerEto);

            try {
                copyNonStaticNonNull(answerEntity, answerToSave);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return answerMapper.mapToETO(answerEntity);
        }
        else
            return null;
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
    public AnswerEto findAnswerById(Long id) {
        final Optional<AnswerEntity> result = this.answerRepository.findById(id);
        return result.map(answerEntity -> this.answerMapper.mapToETO(answerEntity)).orElse(null);
    }

    @Override
    public List<AnswerEto> findAnswersByCriteria(Optional<Long> gameId, Optional<String> filter) {
        List<AnswerEntity> result = new ArrayList<>();
        if (gameId.isPresent()) {

            if (filter.isPresent() && !filter.get().equals("all") ) {
                if (filter.get().equals("checked")) {
                    result = this.answerRepository.findByGame_IdAndChecked(gameId.get(), true);
                } else if (filter.get().equals("unchecked")) {
                    result = this.answerRepository.findByGame_IdAndChecked(gameId.get() ,false);
                }
                else {
                    result = this.answerRepository.findByGame_Id(gameId.get());
                }
            }else {
                result = this.answerRepository.findByGame_Id(gameId.get());
            }
        } else if (filter.isPresent()) {
            if (filter.get().equals("checked")) {
                result = this.answerRepository.findByChecked( true);
            } else if (filter.get().equals("unchecked")) {
                result = this.answerRepository.findByChecked( false);
            }
        }
        else {
            result = this.answerRepository.findAll();
        }
        return answerMapper.mapToETOList(result);
    }
}
