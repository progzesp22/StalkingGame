package com.progzesp.stalking.domain;

import com.progzesp.stalking.persistance.entity.TaskEntity;
import com.progzesp.stalking.persistance.entity.TaskType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

public class TaskEto extends AbstractEto {

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    private Long gameId;

    private int points;

    private List<Long> requiredTasks;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Long> getRequiredTasks() {
        return requiredTasks;
    }

    public void setRequiredTasks(List<Long> requiredTasks) {
        this.requiredTasks = requiredTasks;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
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
