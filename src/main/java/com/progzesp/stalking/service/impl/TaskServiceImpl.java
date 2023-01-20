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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
            final String userName = user.getName();

            final String gameMasterId = game.get().getGameMasterId();

            taskEntity.setGame(game.get());

            List<TaskEntity> prerequisiteTasks = new ArrayList<>();

            if (newTask.getPrerequisiteTasks() != null) {
                for(Long taskId : newTask.getPrerequisiteTasks()) {
                    Optional<TaskEntity> optionalPrerequisiteTask = taskRepo.findById(taskId);
                    if(optionalPrerequisiteTask.isPresent()) {
                        TaskEntity prerequisiteTask = optionalPrerequisiteTask.get();
                        if(Objects.equals(prerequisiteTask.getGameId(), taskEntity.getGameId())) {
                            prerequisiteTasks.add(prerequisiteTask);
                        }
                    }
                }
            }

            taskEntity.setPrerequisiteTasks(prerequisiteTasks);

            if(Objects.equals(gameMasterId, userName)){
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

    @Override
    public TaskEto findTask(Long id) {
        Optional<TaskEntity> foundEntity = taskRepo.findById(id);
        if (foundEntity.isPresent()) {
            TaskEntity taskEntity = foundEntity.get();
            return taskMapper.mapToETO(taskEntity);
        }
        return null;
    }

    @Override
    public List<TaskEto> findTasksByCriteria(Principal user, Optional<Long> gameId) {
        List<TaskEntity> result;
        if (gameId.isPresent()) {
            result = this.taskRepo.findByGame_Id(gameId.get());
        }
        else {
            result = this.taskRepo.findAll();
        }
        List<TaskEto> resultEto = taskMapper.mapToETOList(result);
        for (TaskEto task : resultEto) {
            Optional<GameEntity> game = gameRepo.findById(task.getGameId());
            if (game.isPresent()) {
                if (!Objects.equals(game.get().getGameMasterId(), user.getName()))
                    task.setCorrect_answer("unauthorized");
            }
        }
        return resultEto;
    }
}
