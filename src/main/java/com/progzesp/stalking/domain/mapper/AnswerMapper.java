package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.answer.AnswerEto;
import com.progzesp.stalking.domain.answer.ModifyAnswerEto;
import com.progzesp.stalking.domain.answer.NavPosEto;
import com.progzesp.stalking.domain.answer.NoNavPosEto;
import com.progzesp.stalking.persistance.entity.answer.AnswerEntity;
import com.progzesp.stalking.persistance.entity.TaskType;
import com.progzesp.stalking.persistance.entity.answer.*;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapper implements Mapper<AnswerEto, AnswerEntity> {

    @Override
    public final AnswerEto mapToETO(AnswerEntity entity) {
        AnswerEto answerEto;
        if (entity instanceof NavPosEntity) {
            answerEto = new NavPosEto();
            ((NavPosEto) answerEto).setResponse(((NavPosEntity) entity).getResponseAsCoords());
        } else {
            answerEto = new NoNavPosEto();
            ((NoNavPosEto) answerEto).setResponse(((NoNavPosEntity) entity).getResponseAsString());
        }
        answerEto.setId(entity.getId());
        answerEto.setTaskId(entity.getTaskId());
        answerEto.setApproved(entity.isApproved());
        answerEto.setChecked(entity.isChecked());
        answerEto.setScore(entity.getScore());
        answerEto.setUserId(entity.getUserId());
        answerEto.setGameId(entity.getGameId());
        answerEto.setType(fromEntityToType(entity));
        return answerEto;
    }

    @Override
    public final AnswerEntity mapToEntity(AnswerEto answerEto) {
        AnswerEntity answerEntity = fromTypeToEntity(answerEto.getType());
        if (answerEto instanceof NavPosEto) {
            ((NavPosEntity) answerEntity).setResponseFromCoords(((NavPosEto) answerEto).getResponse());
        } else {
            ((NoNavPosEntity) answerEntity).setResponseFromString(((NoNavPosEto) answerEto).getResponse());
        }
        answerEntity.setId(answerEto.getId());
        answerEntity.setApproved(answerEto.isApproved());
        answerEntity.setChecked(answerEto.isChecked());
        answerEntity.setScore(answerEto.getScore());
        answerEntity.setTaskId(answerEto.getTaskId());
        answerEntity.setGameId(answerEto.getGameId());
        answerEntity.setUserId(answerEto.getUserId());
        return answerEntity;
    }

    public final AnswerEntity mapModifyAnswerEtoToEntity(ModifyAnswerEto answerEto) {
        AnswerEntity answerEntity = new TextEntity();
        answerEntity.setApproved(answerEto.isApproved());
        answerEntity.setChecked(answerEto.isChecked());
        answerEntity.setScore(answerEto.getScore());
        return answerEntity;
    }

    public AnswerEntity fromTypeToEntity(TaskType taskType) {
        return switch (taskType) {
            case AUDIO -> new AudioEntity();
            case LOCALIZATION -> new NavPosEntity();
            case PHOTO -> new PhotoEntity();
            case QR_CODE -> new QREntity();
            case TEXT -> new TextEntity();
        };
    }

    public TaskType fromEntityToType(AnswerEntity answerEntity) {
        if (answerEntity instanceof AudioEntity) {
            return TaskType.AUDIO;
        } else if (answerEntity instanceof NavPosEntity) {
            return TaskType.LOCALIZATION;
        } else if (answerEntity instanceof PhotoEntity) {
            return TaskType.PHOTO;
        } else if (answerEntity instanceof  QREntity) {
            return TaskType.QR_CODE;
        } else {
            return TaskType.TEXT;
        }
    }
}
