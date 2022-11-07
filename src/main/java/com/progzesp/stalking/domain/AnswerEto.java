package com.progzesp.stalking.domain;

import com.progzesp.stalking.persistance.entity.TaskType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class AnswerEto extends AbstractEto {

   private String response;

    private boolean approved;

    private boolean checked;

    private Long taskId;

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
    //TODO USER
}
