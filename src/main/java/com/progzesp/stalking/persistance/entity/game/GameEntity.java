package com.progzesp.stalking.persistance.entity.game;

import com.progzesp.stalking.persistance.entity.*;
import com.progzesp.stalking.persistance.entity.answer.AnswerEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "game")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DISCRIMINATOR", discriminatorType = DiscriminatorType.STRING)
public class GameEntity extends AbstractEntity {

    @NotNull
    private String name;

    private String description;

    //TODO: uncomment
    //@NotNull
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

    @NotNull
    private GameState state;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EndCondition endCondition;

    private Timestamp startTime;

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setNumberOfTeams(Integer numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public void setNumberOfPlayersInTeam(Integer numberOfPlayersInTeam) {
        this.numberOfPlayersInTeam = numberOfPlayersInTeam;
    }

    public EndCondition getEndCondition() {
        return endCondition;
    }

    public void setEndCondition(EndCondition endCondition) {
        this.endCondition = endCondition;
    }

    public List<AnswerEntity> getAnswerEntityList() {
        return answerEntityList;
    }

    public void setAnswerEntityList(List<AnswerEntity> answerEntityList) {
        this.answerEntityList = answerEntityList;
    }

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
