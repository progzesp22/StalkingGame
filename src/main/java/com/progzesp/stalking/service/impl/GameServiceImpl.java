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
import java.util.Optional;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private GameRepo gameRepository;

    @Override
    public GameEto save(GameEto newGame) {

        GameEntity gameEntity = gameMapper.mapToEntity(newGame);
        gameEntity = this.gameRepository.save(gameEntity);
        return gameMapper.mapToETO(gameEntity);
    }

    @Override
    public List<GameEto> findAllGames() {
        return gameMapper.mapToETOList(this.gameRepository.findAll());
    }

    @Override
    public boolean deleteGame(Long id) {
        Optional<GameEntity> gameOptional = gameRepository.findById(id);
        if (gameOptional.isEmpty()) {
            return false;
        }
        else {
            gameRepository.delete(gameOptional.get());
            return gameRepository.findById(id).isEmpty();
        }
    }
}
