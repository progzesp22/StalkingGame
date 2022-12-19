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
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Pair<Integer, GameEto> save(GameEto newGame, Principal user) {
        GameEntity gameEntity = gameMapper.mapToEntity(newGame);
        gameEntity.setGameMaster(userRepo.getByUsername(user.getName()));
        return Pair.of(200, gameMapper.mapToETO(this.gameRepo.save(gameEntity)));
    }

    @Override
    public GameEto save(GameEto newGame) {

        GameEntity gameEntity = gameMapper.mapToEntity(newGame);

        String gmName = newGame.getGameMaster();
        UserEntity gm = userRepo.getByUsername(gmName);

        gameEntity.setGameMaster(gm);
        gameEntity = this.gameRepo.save(gameEntity);
        return gameMapper.mapToETO(gameEntity);
    }

    @Override
    public List<GameEto> findAllGames() {
        return gameMapper.mapToETOList(this.gameRepo.findAll());
    }

    /**
     * Changes state of the game
     *
     * @param id                game id
     * @param newState          new state
     * @param requiredOldStates list of states that would allow to change to newState
     * @return the new state
     */
    public GameState advanceGame(Long id, GameState newState, List<GameState> requiredOldStates) {
        Optional<GameEntity> gameOptional = gameRepo.findById(id);
        if (gameOptional.isEmpty()) {
            return null;
        } else {
            GameEntity gameEntity = gameOptional.get();
            if (requiredOldStates.contains(gameEntity.getState())) {
                gameEntity.setState(newState);
                gameRepo.save(gameEntity);
            }
            return gameEntity.getState();
        }
    }

    @Override
    public GameState openWaitingRoom(Long id) {
        return advanceGame(id, GameState.PENDING, List.of(GameState.CREATED));
    }

    @Override
    public GameState startGameplay(Long id) {
        return advanceGame(id, GameState.STARTED, List.of(GameState.PENDING));
    }

    @Override
    public GameState endGameplay(Long id) {
        return advanceGame(id, GameState.FINISHED, List.of(GameState.STARTED));
    }

    @Override
    public boolean deleteGame(Long id) {
        Optional<GameEntity> gameOptional = gameRepo.findById(id);
        if (gameOptional.isEmpty()) {
            return false;
        } else {
            gameRepo.delete(gameOptional.get());
            return gameRepo.findById(id).isEmpty();
        }
    }

    @Override
    public Optional<GameEto> findGameById(Long id) {
        Optional<GameEntity> game = gameRepo.findById(id);
        return game.map(gameEntity -> gameMapper.mapToETO(gameEntity));
    }

    @Override
    public Pair<Integer, GameEto> modify(Principal user, GameEto newGame, Long id) {
        Optional<GameEntity> game = gameRepo.findById(id);
        if (game.isEmpty()) {
            return Pair.of(400, new GameEto());
        }
        GameEntity gameEntity = game.get();
        if (!user.getName().equals(gameEntity.getGameMaster().getUsername())) {
            return Pair.of(403, new GameEto());
        }
// Temporarily disabled
// TODO: add endpoints for starting games. Currently waiting for proto
//
//        if (gameEntity.getState() != GameState.CREATED) {
//            return Pair.of(400, new GameEto());
//        }

        GameEntity gameToSave = gameMapper.mapToEntity(newGame);
        try {
            copyNonStaticNonNull(gameEntity, gameToSave);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        gameRepo.save(gameEntity);
        return Pair.of(200, gameMapper.mapToETO(gameEntity));
    }
}
