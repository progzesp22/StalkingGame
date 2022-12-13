package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.AnswerEto;
import com.progzesp.stalking.persistance.entity.AnswerEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

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
        answerEto.setUserId(entity.getUserId());
        answerEto.setGameId(entity.getGameId());
        answerEto.setScore(entity.getScore());
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
        answerEntity.setScore(answerEntity.getScore());
        answerEntity.setSubmitTime(new Date());
        return answerEntity;
    }
}
