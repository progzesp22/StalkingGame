package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.persistance.entity.AbstractEntity;
import com.progzesp.stalking.persistance.entity.GameEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameMapper implements Mapper<GameEto, GameEntity> {
    public GameEto mapToETO(GameEntity entity) {

        GameEto game = new GameEto();
        game.setTaskEntityList(entity.getTaskEntityList().stream().map(AbstractEntity::getId).collect(Collectors.toList()));
        game.setId(entity.getId());
        return game;
    }


    public GameEntity mapToEntity(GameEto to) {

        GameEntity entity = new GameEntity();
        entity.setTaskEntityList(new ArrayList<>());
        entity.setId(to.getId());
        return entity;
    }
}
