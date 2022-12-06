package com.progzesp.stalking.domain.mapper;


import com.progzesp.stalking.domain.MessageEto;
import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.persistance.entity.AbstractEntity;
import com.progzesp.stalking.persistance.entity.MessageEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Component
public class MessageMapper implements Mapper<MessageEto, MessageEntity>{


    @Override
    public MessageEto mapToETO(MessageEntity entity) {
        MessageEto messageEto = new MessageEto();
        messageEto.setContent(entity.getContent());
        messageEto.setId(entity.getId());
        messageEto.setTimestamp(entity.getTimestamp());
        return messageEto;
    }

    @Override
    public MessageEntity mapToEntity(MessageEto To) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(To.getId());
        messageEntity.setContent(To.getContent());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
        messageEntity.setTimestamp(sdf.format(new Date(System.currentTimeMillis())));
        return messageEntity;
    }
}
