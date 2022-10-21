package com.progzesp.stalking.domain;

import com.progzesp.stalking.persistance.entity.TaskType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class TaskEto extends AbstractEto {

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    private Long gameId;

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
