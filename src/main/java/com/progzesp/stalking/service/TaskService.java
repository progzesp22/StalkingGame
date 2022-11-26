package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.TaskEto;

import java.util.List;
import java.util.Optional;

public interface TaskService extends Service {

    TaskEto save(TaskEto newTask);

    TaskEto modifyTask(Long id, TaskEto taskEto);

    boolean deleteTask(Long id);

    TaskEto findTask(Long id);

    List<TaskEto> findTasksByCriteria(Optional<Long> gameId);
}
