package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.persistance.entity.TaskEntity;

import java.util.List;
import java.util.stream.Collectors;

public final class TaskMapper {

    public static final TaskEto mapToETO(TaskEntity entity){

        TaskEto task = new TaskEto();
        task.setId(entity.getId());
        task.setName(entity.getName());
        task.setDescription(entity.getDescription());
        task.setTaskType(entity.getTaskType());
        task.setGameId(entity.getGameId());
        return task;
    }

    public static final TaskEntity mapToEntity(TaskEto taskTo){

        TaskEntity entity = new TaskEntity();
        entity.setId(taskTo.getId());
        entity.setName(taskTo.getName());
        entity.setDescription(taskTo.getDescription());
        entity.setTaskType(taskTo.getTaskType());
        return entity;
    }

    public static final List<TaskEto> mapToETOList(List<TaskEntity> entities){
        return entities.stream().map(e -> mapToETO(e)).collect(Collectors.toList());
    }

    public static final List<TaskEntity> mapToEntityList(List<TaskEto> tos){
        return tos.stream().map(t -> mapToEntity(t)).collect(Collectors.toList());
    }
}
