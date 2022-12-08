package com.progzesp.stalking.persistance.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "answer")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DISCRIMINATOR", discriminatorType = DiscriminatorType.STRING)
public abstract class AnswerEntity extends AbstractEntity {

    //TODO: odkomentować @NotNull przy user, przypisywać wartość na podstawie session tokena w POST answer
    //@NotNull
    @ManyToOne
    private UserEntity user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private TaskEntity task;

    @Value("false")
    private boolean approved;

    @Value("false")
    private boolean checked;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private GameEntity game;

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    @Transient
    public Long getGameId() {

        if (this.game == null) {
            return null;
        }
        return this.game.getId();
    }

    public void setGameId(Long gameId) {

        if (gameId == null) {
            this.game = null;
        } else {
            GameEntity gameEntity = new GameEntity();
            gameEntity.setId(gameId);
            this.game = gameEntity;
        }
    }

    @Transient
    public Long getTaskId() {

        if (this.task == null) {
            return null;
        }
        return this.task.getId();
    }

    public void setTaskId(Long taskId) {

        if (taskId == null) {
            this.task = null;
        } else {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setId(taskId);
            this.task = taskEntity;
        }
    }

    @Transient
    public Long getUserId() {

        if (this.user == null) {
            return null;
        }
        return this.user.getId();
    }

    public void setUserId(Long userId) {

        if (userId == null) {
            this.user = null;
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);
            this.user = userEntity;
        }
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    //TODO: implementacja metod abstrakcyjnych w podklasach

    public abstract boolean validate();

}
