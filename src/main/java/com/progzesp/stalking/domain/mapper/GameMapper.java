package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.persistance.entity.AbstractEntity;
import com.progzesp.stalking.persistance.entity.GameEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class GameMapper {
    public static GameEto mapToETO(GameEntity entity) {

        GameEto game = new GameEto();
        game.setTaskEntityList(entity.getTaskEntityList().stream().map(AbstractEntity::getId).collect(Collectors.toList()));
        game.setId(entity.getId());
        return game;
    }


    public static GameEntity mapToEntity(GameEto to) {

        GameEntity entity = new GameEntity();
        entity.setTaskEntityList(new ArrayList<>());
        entity.setId(to.getId());
        return entity;
    }


    public static List<GameEto> mapToETOList(List<GameEntity> entities) {
        return entities.stream().map(e -> mapToETO(e)).collect(Collectors.toList());
    }


    public List<GameEntity> mapToEntityList(List<GameEto> tos) {
        return tos.stream().map(t -> mapToEntity(t)).collect(Collectors.toList());
    }
}
