package com.progzesp.stalking.domain;

import com.progzesp.stalking.persistance.entity.TaskType;

public abstract class AnswerEto extends AbstractEto {

    private Long userId;

    private boolean approved;

    private boolean checked;

    private int score;

    private Long taskId;

    private Long gameId;

    private TaskType type;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public AnswerEtoNoResponse makeBodyWithoutResponse() {
        AnswerEtoNoResponse newBody = new AnswerEtoNoResponse();
        newBody.setApproved(this.approved);
        newBody.setChecked(this.checked);
        newBody.setTaskId(this.taskId);
        newBody.setUserId(this.userId);
        newBody.setId(this.getId());
        return  newBody;
    }

}
