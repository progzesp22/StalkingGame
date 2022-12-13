package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.TaskStatEto;
import com.progzesp.stalking.persistance.entity.TaskStatEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskStatMapper implements Mapper<TaskStatEto, TaskStatEntity> {

    public final TaskStatEntity mapToEntity(TaskStatEto eto){
        TaskStatEntity entity = new TaskStatEntity();
        entity.setTaskId(eto.getTaskId());
        entity.setName(eto.getName());
        entity.setMaxScore(eto.getMaxScore());
        entity.setAverageScore(eto.getAverageScore());
        entity.setTeamsAttempted(eto.getTeamsAttempted());
        entity.setTeamsApproved(eto.getTeamsApproved());
        entity.setAverageSolvingTime(eto.getAverageSolvingTime());
        return entity;
    }

    public final TaskStatEto mapToETO(TaskStatEntity entity){
        TaskStatEto eto = new TaskStatEto();
        eto.setTaskId(entity.getTaskId());
        eto.setName(entity.getName());
        eto.setMaxScore(entity.getMaxScore());
        eto.setAverageScore(entity.getAverageScore());
        eto.setTeamsAttempted(entity.getTeamsAttempted());
        eto.setTeamsApproved(entity.getTeamsApproved());
        eto.setAverageSolvingTime(entity.getAverageSolvingTime());

        return eto;
    }
}
