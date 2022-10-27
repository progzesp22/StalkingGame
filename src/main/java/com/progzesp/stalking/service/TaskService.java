package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.TaskEto;

import java.util.List;

public interface TaskService extends Service {

    TaskEto save(TaskEto newTask);

    List<TaskEto> findAllTasks();

    TaskEto modifyTask(Long id, TaskEto taskEto);

    boolean deleteTask(Long id);
}
