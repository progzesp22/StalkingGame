package com.progzesp.stalking.persistance.entity;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "game")
public class GameEntity extends AbstractEntity {

    //TODO add more fields
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private List<TaskEntity> taskEntityList;

    public List<TaskEntity> getTaskEntityList() {
        return taskEntityList;
    }

    public void setTaskEntityList(List<TaskEntity> taskEntityList) {
        this.taskEntityList = taskEntityList;
    }
}
