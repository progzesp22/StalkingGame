package com.progzesp.stalking.persistance.entity;

import com.progzesp.stalking.persistance.entity.answer.AnswerEntity;
import com.progzesp.stalking.persistance.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "game")
public class GameEntity extends AbstractEntity {
    @NotNull
    private String name;

    private String description;

    @NotNull
    @ManyToOne
    private UserEntity gameMaster;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TeamEntity> teams;

    @NotNull
    private Integer numberOfTeams;

    @NotNull
    private Integer numberOfPlayersInTeam;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TaskEntity> taskEntityList;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AnswerEntity> answerEntityList;

    @OneToMany
    private List<MessageEntity> messages;

    private Date startTime;

    @NotNull
    private GameState state;

    public List<AnswerEntity> getAnswerEntityList() {
        return answerEntityList;
    }

    public void setAnswerEntityList(List<AnswerEntity> answerEntityList) {
        this.answerEntityList = answerEntityList;
    }

    //TODO add endCondition

    @Transient
    public String getGameMasterId() {

        if (this.gameMaster == null) {
            return null;
        }
        return this.gameMaster.getUsername();
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    public void addMessage(MessageEntity message) {
        messages.add(message);
    }
}
