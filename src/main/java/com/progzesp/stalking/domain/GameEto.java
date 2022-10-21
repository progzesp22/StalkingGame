package com.progzesp.stalking.domain;


import java.util.List;

public class GameEto extends AbstractEto {

    private List<Long> taskEntityList;

    public List<Long> getTaskEntityList() {
        return taskEntityList;
    }

    public void setTaskEntityList(List<Long> taskEntityList) {
        this.taskEntityList = taskEntityList;
    }
}
