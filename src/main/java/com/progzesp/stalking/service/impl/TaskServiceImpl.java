package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.domain.mapper.TaskMapper;
import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.TaskRepo;
import com.progzesp.stalking.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepo taskRepository;

    @Autowired
    private GameRepo gameRepository;


    @Override
    public TaskEto save(TaskEto newTask) {

        TaskEntity taskEntity = TaskMapper.mapToEntity(newTask);
        Long id = newTask.getGameId();
        Optional<GameEntity> optionalGame = gameRepository.findById(id);
        GameEntity game = optionalGame.orElse(null);
        taskEntity.setGame(game);
        taskEntity = this.taskRepository.save(taskEntity);
        return TaskMapper.mapToETO(taskEntity);
    }

    @Override
    public List<TaskEto> findAllTasks() {
        return TaskMapper.mapToETOList(this.taskRepository.findAll());
    }

    @Override
    public TaskEto modifyTask(Long id, TaskEto taskEto) {
        Objects.requireNonNull(taskEto, "game");

        Optional<TaskEntity> foundEntity = taskRepository.findById(id);
        if (foundEntity.isPresent()) {
            TaskEntity taskEntity = foundEntity.get();
            TaskEntity taskToSave = TaskMapper.mapToEntity(taskEto);

            try {
                copyDiff(taskEntity, taskToSave);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return TaskMapper.mapToETO(taskEntity);
        }
        else
            return null;
    }
}
