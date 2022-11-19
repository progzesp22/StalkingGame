package com.progzesp.stalking.persistance.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "game")
public class GameEntity extends AbstractEntity {
    @NotNull
    @ManyToOne
    private UserEntity gameMaster;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TeamEntity> teams;

    @NotNull
    private int numberOfTeams;

    @NotNull
    private int numberOfPlayersInTeam;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TaskEntity> taskEntityList;

    @NotNull
    private Date startDate;

    @NotNull
    private GameState state;

    //TODO add endCondition

    @Transient
    public Long getGameMasterId() {

        if (this.gameMaster == null) {
            return null;
        }
        return this.gameMaster.getId();
    }

    public void setGameMasterId(Long gameId) {

        if (gameId == null) {
            this.gameMaster = null;
        } else {
            UserEntity gameMasterEntity = new UserEntity();
            gameMasterEntity.setId(gameId);
            this.gameMaster = gameMasterEntity;
        }
    }

    public UserEntity getGameMaster() {
        return gameMaster;
    }

    public void setGameMaster(UserEntity gameMaster) {
        this.gameMaster = gameMaster;
    }

    public List<TeamEntity> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamEntity> teams) {
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

    public List<TaskEntity> getTaskEntityList() {
        return taskEntityList;
    }

    public void setTaskEntityList(List<TaskEntity> taskEntityList) {
        this.taskEntityList = taskEntityList;
    }

    public void setState(GameState state) {
        this.state = state;
    }
    public GameState getState() {
        return state;
    }
}
