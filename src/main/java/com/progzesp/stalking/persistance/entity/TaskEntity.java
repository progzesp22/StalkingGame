package com.progzesp.stalking.persistance.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "task")
public class TaskEntity extends AbstractEntity {

    @NotNull
    private String name;

    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private GameEntity game;

    @NotNull
    private int points;

    @OneToMany()
    private List<TaskEntity> requiredTasks;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<TaskEntity> getRequiredTasks() {
        return requiredTasks;
    }

    public void setRequiredTasks(List<TaskEntity> requiredTasks) {
        this.requiredTasks = requiredTasks;
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

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
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

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
}
