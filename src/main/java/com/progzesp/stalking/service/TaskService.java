package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.TaskEto;

import java.security.Principal;
import java.util.List;

import org.springframework.data.util.Pair;

public interface TaskService extends Service {

    Pair<Integer, TaskEto> save(TaskEto newTask, Principal user);

    List<TaskEto> findAllTasks();

    TaskEto modifyTask(Long id, TaskEto taskEto);

    boolean deleteTask(Long id);
}
