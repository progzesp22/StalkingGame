package com.progzesp.stalking.domain.game;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.progzesp.stalking.persistance.entity.game.EndCondition;
import com.progzesp.stalking.persistance.entity.game.GameState;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "endCondition")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TimeEndGameEto.class, name = "TIME"),
        @JsonSubTypes.Type(value = ScoreEndGameEto.class, name = "SCORE"),
        @JsonSubTypes.Type(value = ManualEndGameEto.class, name = "MANUAL"),
        @JsonSubTypes.Type(value = TasksEndGameEto.class, name = "TASKS"),
})
public class GameEto extends com.progzesp.stalking.domain.AbstractEto {

    private String name;

    private String description;

    private Long gameMasterId;

    private Integer numberOfTeams;

    private Integer numberOfPlayersInTeam;

    @Enumerated(EnumType.STRING)
    private GameState state;

    private Timestamp startTime;

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGameMasterId() {
        return gameMasterId;
    }

    public void setGameMasterId(Long gameMasterId) {
        this.gameMasterId = gameMasterId;
    }

    public Integer getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(Integer numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public Integer getNumberOfPlayersInTeam() {
        return numberOfPlayersInTeam;
    }

    public void setNumberOfPlayersInTeam(Integer numberOfPlayersInTeam) {
        this.numberOfPlayersInTeam = numberOfPlayersInTeam;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public GameEto() {
        this.setState(GameState.CREATED);
    }

    public GameEto(Long gameMasterId) {
        this.setGameMasterId(gameMasterId);
        this.setState(GameState.CREATED);
    }
}
