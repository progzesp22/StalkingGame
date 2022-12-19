package com.progzesp.stalking.domain;

import com.progzesp.stalking.persistance.entity.EndCondition;
import com.progzesp.stalking.persistance.entity.GameState;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

public class GameEto extends com.progzesp.stalking.domain.AbstractEto {

    private String name;

    private String description;

    private String gameMasterId;

    private int numberOfTeams;

    private int numberOfPlayersInTeam;

    private Date startTime;

    @Enumerated(EnumType.STRING)
    private EndCondition endCondition;

    private Date endTime;

    private Integer endScore;

    @Enumerated(EnumType.STRING)
    private GameState state;

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

    public String getGameMasterId() {
        return gameMasterId;
    }

    public void setGameMasterId(String gameMasterId) {
        this.gameMasterId = gameMasterId;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public int getNumberOfPlayersInTeam() {
        return numberOfPlayersInTeam;
    }

    public void setNumberOfPlayersInTeam(int numberOfPlayersInTeam) {
        this.numberOfPlayersInTeam = numberOfPlayersInTeam;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public GameEto(String gameMasterId) {
        this.setGameMasterId(gameMasterId);
        this.setStartTime(new Date());
        this.setState(GameState.CREATED);
    }

    public EndCondition getEndCondition() {
        return endCondition;
    }

    public void setEndCondition(EndCondition endCondition) {
        this.endCondition = endCondition;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getEndScore() {
        return endScore;
    }

    public void setEndScore(Integer endScore) {
        this.endScore = endScore;
    }
}
