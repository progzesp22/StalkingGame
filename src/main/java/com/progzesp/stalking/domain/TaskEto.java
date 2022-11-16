package com.progzesp.stalking.domain;

import com.progzesp.stalking.persistance.entity.TaskType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

public class TaskEto extends AbstractEto {

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    private Long gameId;

    private int points;

    private List<Long> prerequisiteTasks;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Long> getPrerequisiteTasks() {
        return prerequisiteTasks;
    }

    public void setPrerequisiteTasks(List<Long> prerequisiteTasks) {
        this.prerequisiteTasks = prerequisiteTasks;
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

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }
}
