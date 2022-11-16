package com.progzesp.stalking.domain;

import com.progzesp.stalking.persistance.entity.UserEntity;

import java.util.Date;
import java.util.List;

public class GameEto extends AbstractEto {

    private List<Long> taskEntityList;

    private Long gameMasterId;

    private List<Long> teams;

    private int numberOfTeams;

    private int numberOfPlayersInTeam;

    private Date startDate;

    public Long getGameMasterId() {
        return gameMasterId;
    }

    public void setGameMasterId(Long gameMasterId) {
        this.gameMasterId = gameMasterId;
    }

    public List<Long> getTeams() {
        return teams;
    }

    public void setTeams(List<Long> teams) {
        this.teams = teams;
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

    public List<Long> getTaskEntityList() {
        return taskEntityList;
    }

    public void setTaskEntityList(List<Long> taskEntityList) {
        this.taskEntityList = taskEntityList;
    }
}
