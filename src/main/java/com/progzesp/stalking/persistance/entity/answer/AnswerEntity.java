package com.progzesp.stalking.persistance.entity.answer;

import com.progzesp.stalking.persistance.entity.AbstractEntity;
import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import com.progzesp.stalking.persistance.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    private Boolean approved;

    @Value("false")
    private Boolean checked;

    @Value("0")
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private GameEntity game;

    @NotNull
    private Date submitTime;

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

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

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean isChecked() {
        return checked;
    }

    public abstract boolean validate();

}
