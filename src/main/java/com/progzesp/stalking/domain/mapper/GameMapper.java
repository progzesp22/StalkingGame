package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GameMapper implements Mapper<GameEto, GameEntity> {

    @Autowired
    private UserRepo userRepo;
    public GameEto mapToETO(GameEntity entity) {
        GameEto game = new GameEto();
        game.setId(entity.getId());
        game.setGameMasterId(entity.getGameMasterId());
        game.setNumberOfTeams(entity.getNumberOfTeams());
        game.setNumberOfPlayersInTeam(entity.getNumberOfPlayersInTeam());
        game.setStartTime(entity.getStartTime());
        game.setState(entity.getState());
        game.setName(entity.getName());
        game.setDescription(entity.getDescription());
        game.setEndCondition(entity.getEndCondition());
        game.setEndTime(entity.getEndTime());
        game.setEndScore(entity.getEndScore());
        return game;
    }


    public GameEntity mapToEntity(GameEto to) {
        GameEntity entity = new GameEntity();
        entity.setTaskEntityList(new ArrayList<>());
        entity.setId(to.getId());
        entity.setNumberOfTeams(to.getNumberOfTeams());
        entity.setNumberOfPlayersInTeam(to.getNumberOfPlayersInTeam());
        entity.setStartTime(to.getStartTime());
        entity.setTeams(new ArrayList<>());
        entity.setTaskEntityList(new ArrayList<>());
        entity.setAnswerEntityList(new ArrayList<>());
        entity.setState(to.getState());
        entity.setMessages(new ArrayList<>());
        entity.setName(to.getName());
        entity.setDescription(to.getDescription());
        entity.setGameMaster(userRepo.getByUsername(to.getGameMasterId()));
        entity.setEndCondition(to.getEndCondition());
        entity.setEndTime(to.getEndTime());
        entity.setEndScore(to.getEndScore());
        return entity;
    }
}
