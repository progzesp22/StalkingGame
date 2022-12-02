package com.progzesp.stalking.domain.mapper;


import com.progzesp.stalking.domain.MessageEto;
import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.persistance.entity.AbstractEntity;
import com.progzesp.stalking.persistance.entity.MessageEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class MessageMapper implements Mapper<MessageEto, MessageEntity> {


    @Override
    public MessageEto mapToETO(MessageEntity entity) {
        MessageEto messageEto = new MessageEto();
        messageEto.setContent(entity.getContent());
        messageEto.setId(entity.getId());
        return messageEto;
    }

    @Override
    public MessageEntity mapToEntity(MessageEto To) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(To.getId());
        messageEntity.setContent(To.getContent());
        return messageEntity;
    }
}
