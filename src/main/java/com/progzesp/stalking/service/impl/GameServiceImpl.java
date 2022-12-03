package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.MessageEto;
import com.progzesp.stalking.domain.MessageInputEto;
import com.progzesp.stalking.domain.mapper.GameMapper;
import com.progzesp.stalking.domain.mapper.MessageMapper;
import com.progzesp.stalking.persistance.entity.*;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.MessageRepo;
import com.progzesp.stalking.persistance.repo.UserRepo;
import com.progzesp.stalking.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

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
        final Long userId = userRepo.getByUsername(user.getName()).getId();
        final Long gameMasterId = newGame.getGameMasterId();
        GameEntity gameEntity = gameMapper.mapToEntity(newGame);

        if(gameMasterId != null){
            if(userId == gameMasterId){
                gameEntity.setGameMaster(userRepo.findById(gameMasterId).get());
                return Pair.of(200, gameMapper.mapToETO(this.gameRepo.save(gameEntity)));// ResponseEntity.ok().body(gameService.save(newGame, user));
            }
            else{
                return Pair.of(400, gameMapper.mapToETO(gameEntity));  
            }      
        }
        else{
            gameEntity.setGameMaster(userRepo.findById(userId).get());
            return Pair.of(200, gameMapper.mapToETO(this.gameRepo.save(gameEntity)));
        }
    }

    @Override
    public GameEto save(GameEto newGame) {

        GameEntity gameEntity = gameMapper.mapToEntity(newGame);

        Long id = newGame.getGameMasterId();
        Optional<UserEntity> optionalGM = userRepo.findById(id);
        UserEntity gm = optionalGM.orElse(null);

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
     * @param id game id
     * @param newState new state
     * @param requiredOldStates list of states that would allow to change to newState
     * @return the new state
     */
    public GameState advanceGame(Long id, GameState newState, List<GameState> requiredOldStates) {
        Optional<GameEntity> gameOptional = gameRepo.findById(id);
        if (gameOptional.isEmpty()) {
            return null;
        }
        else {
            GameEntity gameEntity = gameOptional.get();
            if (requiredOldStates.contains(gameEntity.getState()) ) {
                gameEntity.setState(newState);
                gameRepo.save(gameEntity);
            }
            return gameEntity.getState();
        }
    }

    @Override
    public GameState openWaitingRoom(Long id) {
        return advanceGame(id, GameState.WAITING_FOR_PLAYERS, List.of(GameState.SETTING_UP));
    }
    @Override
    public GameState startGameplay(Long id) {
        return advanceGame(id, GameState.ONGOING, List.of(GameState.WAITING_FOR_PLAYERS));
    }
    @Override
    public GameState endGameplay(Long id) {
        return advanceGame(id, GameState.ENDED, List.of(GameState.ONGOING));
    }

    @Override
    public boolean deleteGame(Long id) {
        Optional<GameEntity> gameOptional = gameRepo.findById(id);
        if (gameOptional.isEmpty()) {
            return false;
        }
        else {
            gameRepo.delete(gameOptional.get());
            return gameRepo.findById(id).isEmpty();
        }
    }

    @Override
    public Optional<GameEto> findGameById(Long id){
        Optional<GameEntity> game = gameRepo.findById(id);
        if(game.isPresent()){
            return Optional.of(gameMapper.mapToETO(game.get()));
        }
        return Optional.empty();
    }


}
