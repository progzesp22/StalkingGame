package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.domain.mapper.TaskMapper;
import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.TaskRepo;
import com.progzesp.stalking.persistance.repo.UserRepo;
import com.progzesp.stalking.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private GameRepo gameRepo;


    @Override
    public Pair<Integer, TaskEto> save(TaskEto newTask, Principal user){
        Optional<GameEntity> game = gameRepo.findById(newTask.getGameId());
        TaskEntity taskEntity = taskMapper.mapToEntity(newTask);   
        if(game.isPresent()){
            final Long userId = userRepo.getByUsername(user.getName()).getId();

            final Long gameMasterId = game.get().getGameMasterId();

            taskEntity.setGame(game.get());

            if(gameMasterId == userId){
                return Pair.of(200, taskMapper.mapToETO(taskRepo.save(taskEntity)));// ResponseEntity.ok().body(taskService.save(newTask, user));
            }
            else{
                return Pair.of(403, taskMapper.mapToETO(taskEntity));
            }            
        }
        else{
                return Pair.of(400, taskMapper.mapToETO(taskEntity));
        }
    }

    @Override
    public List<TaskEto> findAllTasks() {
        return taskMapper.mapToETOList(this.taskRepo.findAll());
    }

    @Override
    public TaskEto modifyTask(Long id, TaskEto taskEto) {

        Optional<TaskEntity> foundEntity = taskRepo.findById(id);
        if (foundEntity.isPresent()) {
            TaskEntity taskEntity = foundEntity.get();
            TaskEntity taskToSave = taskMapper.mapToEntity(taskEto);

            try {
                copyNonStaticNonNull(taskEntity, taskToSave);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return taskMapper.mapToETO(taskEntity);
        }
        else
            return null;
    }

    @Override
    public boolean deleteTask(Long id) {

        Optional<TaskEntity> taskOptional = taskRepo.findById(id);
        if (taskOptional.isEmpty()) {
            return false;
        }
        else {
            taskRepo.delete(taskOptional.get());
            return taskRepo.findById(id).isEmpty();
        }
    }
}
