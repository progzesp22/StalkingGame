package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.TaskEto;
import org.springframework.data.util.Pair;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface TaskService extends Service {

    Pair<Integer, TaskEto> save(TaskEto newTask, Principal user);

    TaskEto modifyTask(Long id, TaskEto taskEto);

    boolean deleteTask(Long id);

    TaskEto findTask(Long id);

    List<TaskEto> findTasksByCriteria(Optional<Long> gameId);
}
