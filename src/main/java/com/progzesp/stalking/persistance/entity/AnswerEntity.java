package com.progzesp.stalking.persistance.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "answer")
public class AnswerEntity extends AbstractEntity {

    @NotNull
    @ManyToOne
    private UserEntity user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private TaskEntity task;

    @Value("false")
    private boolean approved;

    @Value("false")
    private boolean checked;

    @NotNull
    private String response;

    @Transient
    public Long getTaskId() {

        if (this.task == null) {
            return null;
        }
        return this.task.getId();
    }

    public void setTaskId(Long taskId) {

        if (taskId == null) {
            this.task = null;
        } else {
            TaskEntity taskEntity = new TaskEntity();
            taskEntity.setId(taskId);
            this.task = taskEntity;
        }
    }

    @Transient
    public Long getUserId() {

        if (this.user == null) {
            return null;
        }
        return this.user.getId();
    }

    public void setUserId(Long userId) {

        if (userId == null) {
            this.user = null;
        } else {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);
            this.user = userEntity;
        }
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

}
