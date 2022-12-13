package com.progzesp.stalking.domain.answer;

import com.progzesp.stalking.domain.AbstractEto;
import com.progzesp.stalking.persistance.entity.TaskType;

public abstract class AnswerEto extends AbstractEto {

    private Long userId;

    private Boolean approved;

    private Boolean checked;

    private Integer score;

    private Long taskId;

    private Long gameId;

    private TaskType type;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

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

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
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
        newBody.setScore(this.score);
        newBody.setTaskId(this.taskId);
        newBody.setUserId(this.userId);
        newBody.setGameId(this.gameId);
        newBody.setId(this.getId());
        return newBody;
    }

}
