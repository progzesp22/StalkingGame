package com.progzesp.stalking.persistance.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "game")
public class GameEntity extends AbstractEntity {

    //TODO add more fields
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<TaskEntity> taskEntityList;

    public List<TaskEntity> getTaskEntityList() {
        return taskEntityList;
    }

    public void setTaskEntityList(List<TaskEntity> taskEntityList) {
        this.taskEntityList = taskEntityList;
    }
}
