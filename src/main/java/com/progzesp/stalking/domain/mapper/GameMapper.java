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
        game.setGameMasterId(entity.getGameMasterId());
        game.setTeams(entity.getTeams().stream().map(AbstractEntity::getId).collect(Collectors.toList()));
        game.setNumberOfTeams(entity.getNumberOfTeams());
        game.setNumberOfPlayersInTeam(entity.getNumberOfPlayersInTeam());
        game.setStartDate(entity.getStartDate());
        game.setState(entity.getState());
        return game;
    }


    public GameEntity mapToEntity(GameEto to) {

        GameEntity entity = new GameEntity();
        entity.setTaskEntityList(new ArrayList<>());
        entity.setId(to.getId());
        entity.setNumberOfTeams(to.getNumberOfTeams());
        entity.setNumberOfPlayersInTeam(to.getNumberOfPlayersInTeam());
        entity.setStartDate(to.getStartDate());
        entity.setTeams(new ArrayList<>());
        entity.setTaskEntityList(new ArrayList<>());
        entity.setState(to.getState());
        return entity;
    }
}
