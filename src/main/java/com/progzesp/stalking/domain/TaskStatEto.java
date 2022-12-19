package com.progzesp.stalking.domain;

public class TaskStatEto {

    private Long taskId;

    private String name;

    private int maxScore;

    private double averageScore;

    private int teamsAttempted;

    private int teamsApproved;

    private Double averageSolvingTime;

    public Long getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public int getTeamsAttempted() {
        return teamsAttempted;
    }

    public int getTeamsApproved() {
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

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public void setTeamsAttempted(int teamsAttempted) {
        this.teamsAttempted = teamsAttempted;
    }

    public void setTeamsApproved(int teamsApproved) {
        this.teamsApproved = teamsApproved;
    }

    public void setAverageSolvingTime(Double averageSolvingTime) {
        this.averageSolvingTime = averageSolvingTime;
    }
}
