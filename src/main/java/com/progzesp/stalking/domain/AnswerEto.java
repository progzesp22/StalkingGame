package com.progzesp.stalking.domain;

public class AnswerEto extends AbstractEto {

    private Long userId;

    private String response;

    private boolean approved;

    private boolean checked;

    private Long taskId;

    private Long gameId;

    private Long score;

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
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

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
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

    public AnswerEtoNoResponse makeBodyWithoutResponse() {
        AnswerEtoNoResponse newBody = new AnswerEtoNoResponse();
        newBody.setApproved(this.approved);
        newBody.setChecked(this.checked);
        newBody.setTaskId(this.taskId);
        newBody.setUserId(this.userId);
        newBody.setId(this.getId());
        return  newBody;
    }

    //TODO USER
}
