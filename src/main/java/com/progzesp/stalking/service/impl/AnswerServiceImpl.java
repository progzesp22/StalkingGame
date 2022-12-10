package com.progzesp.stalking.service.impl;

import com.google.gson.JsonObject;
import com.progzesp.stalking.domain.AnswerEto;
import com.progzesp.stalking.domain.mapper.AnswerMapper;
import com.progzesp.stalking.persistance.entity.AnswerEntity;
import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import com.progzesp.stalking.persistance.entity.answer.*;
import com.progzesp.stalking.persistance.repo.AnswerRepo;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.TaskRepo;
import com.progzesp.stalking.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public AnswerEto save(AnswerEto newAnswer) {

        AnswerEntity answerEntity = answerMapper.mapToEntity(newAnswer);
        if (!answerEntity.validate())
            return null;

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
    public AnswerEto modifyAnswer(Long id, JsonObject jsonObject) {

        Optional<AnswerEntity> foundEntity = answerRepository.findById(id);
        if (foundEntity.isPresent()) {
            AnswerEntity answerEntity = foundEntity.get();
            if (jsonObject.has("approved")) {
                try {
                    answerEntity.setApproved(Boolean.parseBoolean(String.valueOf(jsonObject.get("approved"))));
                } catch (Exception ignored) {}
            }
            if (jsonObject.has("checked")) {
                try {
                    answerEntity.setChecked(Boolean.parseBoolean(String.valueOf(jsonObject.get("checked"))));
                } catch (Exception ignored) {}
            }
            if (jsonObject.has("score")) {
                try {
                    answerEntity.setScore(Integer.parseInt(String.valueOf(jsonObject.get("score"))));
                } catch (Exception ignored) {}
            }

            return answerMapper.mapToETO(answerEntity);
        }
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
                    return new ArrayList<>();
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

        return answerMapper.mapToETOList(result);
    }
}
