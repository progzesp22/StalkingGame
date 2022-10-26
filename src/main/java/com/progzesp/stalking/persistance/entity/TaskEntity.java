package com.progzesp.stalking.persistance.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "task")
public class TaskEntity extends AbstractEntity {

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private GameEntity game;

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
