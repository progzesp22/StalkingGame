package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.AnswerEto;
import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.persistance.entity.AnswerEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper implements Mapper<AnswerEto, AnswerEntity> {

    @Override
    public final AnswerEto mapToETO(AnswerEntity entity) {
        AnswerEto answerEto = new AnswerEto();
        answerEto.setId(entity.getId());
        answerEto.setResponse(entity.getResponse());
        answerEto.setTaskId(entity.getTaskId());
        answerEto.setApproved(entity.isApproved());
        answerEto.setChecked(entity.isChecked());
        return answerEto;
    }

    @Override
    public final AnswerEntity mapToEntity(AnswerEto answerEto) {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setResponse(answerEto.getResponse());
        answerEntity.setId(answerEto.getId());
        answerEntity.setApproved(answerEto.isApproved());
        answerEntity.setChecked(answerEto.isChecked());
        answerEntity.setTaskId(answerEto.getTaskId());
        return answerEntity;
    }
}
