package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.mapper.GameMapper;
import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.GameState;
import com.progzesp.stalking.persistance.entity.UserEntity;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.UserRepo;
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

    @Autowired
    private UserRepo userRepo;

    @Override
    public GameEto save(GameEto newGame) {

        GameEntity gameEntity = gameMapper.mapToEntity(newGame);

        Long id = newGame.getGameMasterId();
        Optional<UserEntity> optionalGM = userRepo.findById(id);
        UserEntity gm = optionalGM.orElse(null);

        gameEntity.setGameMaster(gm);
        gameEntity = this.gameRepository.save(gameEntity);
        return gameMapper.mapToETO(gameEntity);
    }

    @Override
    public List<GameEto> findAllGames() {
        return gameMapper.mapToETOList(this.gameRepository.findAll());
    }

    /**
     * Changes state of the game
     * @param id game id
     * @param newState new state
     * @return the new state
     */
    public GameState advanceGame(Long id, GameState newState) {
        //TODO: Restrict if we can change to newState based on the current state?
        Optional<GameEntity> gameOptional = gameRepository.findById(id);
        if (gameOptional.isEmpty()) {
            return null;
        }
        else {
            GameEntity gameEntity = gameOptional.get();
            gameEntity.setState(newState);
            gameRepository.save(gameEntity);
            return gameEntity.getState();
        }
    }

    @Override
    public GameState openWaitingRoom(Long id) {
        return advanceGame(id, GameState.WAITING_FOR_PLAYERS);
    }
    @Override
    public GameState startGameplay(Long id) {
        return advanceGame(id, GameState.ONGOING);
    }
    @Override
    public GameState endGameplay(Long id) {
        return advanceGame(id, GameState.ENDED);
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
