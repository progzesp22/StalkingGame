package com.progzesp.stalking.persistance.entity;

public class TaskStatEntity extends AbstractEntity {

    private Long taskId;

    private String name;

    private Integer maxScore;

    private Double averageScore;

    private Integer teamsAttempted;

    private Integer teamsApproved;

    private Double averageSolvingTime;

    public Long getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public Integer getTeamsAttempted() {
        return teamsAttempted;
    }

    public Integer getTeamsApproved() {
        return teamsApproved;
    }

    public Double getAverageSolvingTime() {
        return averageSolvingTime;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public void setTeamsAttempted(Integer teamsAttempted) {
        this.teamsAttempted = teamsAttempted;
    }

    public void setTeamsApproved(Integer teamsApproved) {
        this.teamsApproved = teamsApproved;
    }

    public void setAverageSolvingTime(Double averageSolvingTime) {
        this.averageSolvingTime = averageSolvingTime;
    }

}
