package com.progzesp.stalking.domain;

import com.progzesp.stalking.persistance.entity.GameState;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

public class GameEto extends com.progzesp.stalking.domain.AbstractEto {

    private String name;

    private String description;

    private Long gameMasterId;

    private int numberOfTeams;

    private int numberOfPlayersInTeam;

    private Date startDate;

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

    public Long getGameMasterId() {
        return gameMasterId;
    }

    public void setGameMasterId(Long gameMasterId) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
        this.setStartDate(new Date());
        this.setState(GameState.CREATED);
    }
}
