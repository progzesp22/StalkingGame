package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.UserEto;
import com.progzesp.stalking.persistance.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserEto, UserEntity> {
    @Override
    public UserEto mapToETO(UserEntity entity) {
        UserEto eto = new UserEto();
        eto.setId(entity.getId());
        eto.setUsername(entity.getUsername());
        eto.setPassword(entity.getPassword());
        return eto;
    }

    @Override
    public UserEntity mapToEntity(UserEto To) {
        UserEntity entity = new UserEntity();
        entity.setId(To.getId());
        entity.setUsername(To.getUsername());
        entity.setPassword(To.getPassword());
        return entity;
    }
}
