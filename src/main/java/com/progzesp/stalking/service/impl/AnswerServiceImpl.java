package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.AnswerEto;
import com.progzesp.stalking.domain.mapper.AnswerMapper;
import com.progzesp.stalking.persistance.entity.AnswerEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import com.progzesp.stalking.persistance.repo.AnswerRepo;
import com.progzesp.stalking.persistance.repo.TaskRepo;
import com.progzesp.stalking.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public AnswerEto save(AnswerEto newAnswer) {

        AnswerEntity answerEntity = answerMapper.mapToEntity(newAnswer);


        Optional<TaskEntity> optionalTask = taskRepository.findById(newAnswer.getTaskId());
        TaskEntity taskEntity = optionalTask.orElse(null);
        answerEntity.setTask(taskEntity);
        answerEntity = this.answerRepository.save(answerEntity);
        return answerMapper.mapToETO(answerEntity);
    }

    @Override
    public List<AnswerEto> findAllAnswers() {
        return answerMapper.mapToETOList(this.answerRepository.findAll());
    }

    @Override
    public List<AnswerEto> findAllUncheckedAnswers() {
        return answerMapper.mapToETOList(this.answerRepository.findAllUncheckedAnswers());
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
}
