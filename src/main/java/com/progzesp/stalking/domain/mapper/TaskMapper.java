package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.persistance.entity.AbstractEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class TaskMapper implements Mapper<TaskEto, TaskEntity> {

    public final TaskEto mapToETO(TaskEntity entity){

        TaskEto task = new TaskEto();
        task.setId(entity.getId());
        task.setName(entity.getName());
        task.setDescription(entity.getDescription());
        task.setType(entity.getTaskType());
        task.setGameId(entity.getGameId());
        task.setPoints(entity.getPoints());
        task.setPrerequisiteTasks(entity.getPrerequisiteTasks().stream().map(AbstractEntity::getId).collect(Collectors.toList()));
        return task;
    }

    public final TaskEntity mapToEntity(TaskEto taskTo){

        TaskEntity entity = new TaskEntity();
        entity.setId(taskTo.getId());
        entity.setName(taskTo.getName());
        entity.setDescription(taskTo.getDescription());
        entity.setTaskType(taskTo.getType());
        entity.setPoints(taskTo.getPoints());
        entity.setPrerequisiteTasks(new ArrayList<>());
        return entity;
    }
}
