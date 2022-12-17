package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.game.*;
import com.progzesp.stalking.persistance.entity.game.EndCondition;
import com.progzesp.stalking.persistance.entity.game.GameEntity;
import com.progzesp.stalking.persistance.entity.game.ScoreEndGameEntity;
import com.progzesp.stalking.persistance.entity.game.TimeEndGameEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GameMapper implements Mapper<GameEto, GameEntity> {
    public GameEto mapToETO(GameEntity entity) {
        GameEto game = fromEndConditionToEtoType(entity);
        game.setId(entity.getId());
        game.setGameMasterId(entity.getGameMasterId());
        game.setNumberOfTeams(entity.getNumberOfTeams());
        game.setNumberOfPlayersInTeam(entity.getNumberOfPlayersInTeam());
        game.setState(entity.getState());
        game.setName(entity.getName());
        game.setDescription(entity.getDescription());
        game.setStartTime(entity.getStartTime());
        return game;
    }


    public GameEntity mapToEntity(GameEto to) {
        GameEntity entity = fromEndConditionToEntity(to);
        entity.setTaskEntityList(new ArrayList<>());
        entity.setId(to.getId());
        entity.setNumberOfTeams(to.getNumberOfTeams());
        entity.setNumberOfPlayersInTeam(to.getNumberOfPlayersInTeam());
        entity.setTeams(new ArrayList<>());
        entity.setTaskEntityList(new ArrayList<>());
        entity.setAnswerEntityList(new ArrayList<>());
        entity.setState(to.getState());
        entity.setMessages(new ArrayList<>());
        entity.setName(to.getName());
        entity.setDescription(to.getDescription());
        entity.setStartTime(to.getStartTime());
        return entity;
    }

    private GameEntity fromEndConditionToEntity(GameEto gameEto) {
        EndCondition endCondition = fromEtoToEndCondition(gameEto);
        switch (endCondition) {
            case SCORE -> {
                ScoreEndGameEntity entity = new ScoreEndGameEntity();
                entity.setEndCondition(endCondition);
                entity.setEndScore(((ScoreEndGameEto) gameEto).getEndScore());
                return entity;
            }
            case TIME -> {
                TimeEndGameEntity entity = new TimeEndGameEntity();
                entity.setEndCondition(endCondition);
                entity.setEndTime(((TimeEndGameEto) gameEto).getEndTime());
                return entity;
            }
            default -> {
                GameEntity entity = new GameEntity();
                entity.setEndCondition(endCondition);
                return entity;
            }
        }
    }

    private GameEto fromEndConditionToEtoType(GameEntity gameEntity) {
        EndCondition endCondition = gameEntity.getEndCondition();
        switch (endCondition) {
            case SCORE -> {
                ScoreEndGameEto eto = new ScoreEndGameEto();
                eto.setEndScore(((ScoreEndGameEntity) gameEntity).getEndScore());
                return eto;
            }
            case TIME -> {
                TimeEndGameEto eto = new TimeEndGameEto();
                eto.setEndTime(((TimeEndGameEntity) gameEntity).getEndTime());
                return eto;
            }
            default -> {
                return new GameEto();
            }
        }
    }

    private EndCondition fromEtoToEndCondition(GameEto gameEto) {

        if (gameEto instanceof ManualEndGameEto) {
            return EndCondition.MANUAL;
        } else if (gameEto instanceof  ScoreEndGameEto) {
            return EndCondition.SCORE;
        } else if (gameEto instanceof TasksEndGameEto) {
            return EndCondition.TASKS;
        } else if (gameEto instanceof TimeEndGameEto) {
            return EndCondition.TIME;
        }
        else return null;
    }
}
