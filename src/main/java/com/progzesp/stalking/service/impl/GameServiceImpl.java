package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.TeamEto;
import com.progzesp.stalking.domain.mapper.GameMapper;
import com.progzesp.stalking.domain.mapper.TeamMapper;
import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.GameState;
import com.progzesp.stalking.persistance.entity.TaskEntity;
import com.progzesp.stalking.persistance.entity.TeamEntity;
import com.progzesp.stalking.persistance.entity.UserEntity;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.TeamRepo;
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
    private TeamMapper teamMapper;

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

    @Override
    public List<TeamEto> findTeamsSortedDesc(Long id){
        Optional<GameEntity> optGame = this.gameRepo.findById(id);
        if(optGame.isPresent()){
            List<TeamEntity> teams = optGame.get().getTeams();
            // TODO:
            // teams.sort((x, y) -> x.getTeamScore().compareTo(y.getTeamScore())); // add getTeamScore() to TeamEntity
            // the problem is: do we want to have "score" field in TeamEntity? 
            // if we can't have that then we can't use List<TeamEto> as ResponseEntity body
            // but we need another type of object with additional field of score
            // also we don't need to calculate team score on each request what may be a very hard task
            // as we need to connect info from many different repos (!!!)
            // however we need to keep track of it as the game goes
            return teamMapper.mapToETOList(teams);
        }
        else{
            return null;
        }
    }

    @Override
    public List<Object> findAvgTasksStats(Long id){
        Optional<GameEntity> optGame = this.gameRepo.findById(id);
        if(optGame.isPresent()){
            List<TaskEntity> tasks = optGame.get().getTaskEntityList();
            // TODO: add functions to calculate avg stats of answers for each task and return those stats
            // probably the best idea is to do something like AvgAnswerEntity/ETO and mapper to it 
            // and return List<> of them instead of List<Object>
            return null;
        }
        else{
            return null;
        }
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
        if (gameEntity.getState() != GameState.CREATED) {
            return Pair.of(400, new GameEto());
        }
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
