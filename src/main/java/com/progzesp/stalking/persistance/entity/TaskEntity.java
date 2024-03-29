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

    private String correct_answer;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private GameEntity game;

    @NotNull
    private Integer maxScore;

    @ManyToMany()
    private List<TaskEntity> prerequisiteTasks;

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer points) {
        this.maxScore = points;
    }

    public List<TaskEntity> getPrerequisiteTasks() {
        return prerequisiteTasks;
    }

    public void setPrerequisiteTasks(List<TaskEntity> prerequisiteTasks) {
        this.prerequisiteTasks = prerequisiteTasks;
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

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }
}
