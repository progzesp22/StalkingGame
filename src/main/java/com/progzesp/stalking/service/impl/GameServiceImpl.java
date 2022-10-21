package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.mapper.GameMapper;
import com.progzesp.stalking.domain.mapper.TaskMapper;
import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.TaskRepo;
import com.progzesp.stalking.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final GameRepo gameRepository;
    private final TaskRepo taskRepository;

    @Autowired
    public GameServiceImpl(final GameRepo gameRepository, final TaskRepo taskRepository) {
        this.gameRepository = gameRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public GameEto save(GameEto newGame) {

        List<Long> ids = newGame.getTaskEntityList();
        List<TaskEntity> tasks = this.taskRepository.findAllById(ids);

        GameEntity gameEntity = GameMapper.mapToEntity(newGame);
        gameEntity.setTaskEntityList(tasks);
        gameEntity = this.gameRepository.save(gameEntity);
        return GameMapper.mapToETO(gameEntity);
    }
}
