package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.TaskStatEto;
import com.progzesp.stalking.domain.TeamEto;
import com.progzesp.stalking.domain.mapper.GameMapper;
import com.progzesp.stalking.domain.mapper.TaskStatMapper;
import com.progzesp.stalking.domain.mapper.TeamMapper;
import com.progzesp.stalking.persistance.entity.*;
import com.progzesp.stalking.persistance.repo.AnswerRepo;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.UserRepo;
import com.progzesp.stalking.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private TaskStatMapper taskStatMapper;

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AnswerRepo answerRepo;


    @Override
    public Pair<Integer, GameEto> save(GameEto newGame, Principal user) {
        final Long userId = userRepo.getByUsername(user.getName()).getId();
        final Long gameMasterId = newGame.getGameMasterId();
        GameEntity gameEntity = gameMapper.mapToEntity(newGame);

        if (gameMasterId != null) {
            if (userId == gameMasterId) {
                gameEntity.setGameMaster(userRepo.findById(gameMasterId).get());
                return Pair.of(200, gameMapper.mapToETO(this.gameRepo.save(gameEntity)));// ResponseEntity.ok().body(gameService.save(newGame, user));
            } else {
                return Pair.of(400, gameMapper.mapToETO(gameEntity));
            }
        } else {
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

    @Override
    public List<TeamEto> findTeamsSortedDesc(Long id) {
        Optional<GameEntity> optGame = this.gameRepo.findById(id);
        if (optGame.isPresent()) {
            List<TeamEntity> teams = optGame.get().getTeams();
            teams.sort((x, y) -> Integer.compare(x.getScore(), y.getScore()));
            return teamMapper.mapToETOList(teams);
        } else {
            return null;
        }
    }

    @Override
    public List<TaskStatEto> findAvgTasksStats(Long id) {
        Optional<GameEntity> optGame = this.gameRepo.findById(id);
        if (optGame.isPresent()) {
            List<TaskEntity> tasks = optGame.get().getTaskEntityList();
            List<TaskStatEntity> tasksStats = new ArrayList<>();
            for (TaskEntity task : tasks) {
                TaskStatEntity taskStat = new TaskStatEntity();
                taskStat.setName(task.getName());
                taskStat.setMaxScore(task.getPoints());

                AnswerEntity exampleAnswer = new AnswerEntity();
                exampleAnswer.setTask(task);
                List<AnswerEntity> exampleAnswers = answerRepo.findAll(Example.of(exampleAnswer));
                List<TeamEntity> uniqueTeams = exampleAnswers.stream().map(AnswerEntity::getUser).map(UserEntity::getTeam).distinct().toList();
                taskStat.setTeamsAttempted(uniqueTeams.size());

                exampleAnswer.setApproved(true);
                exampleAnswers = answerRepo.findAll(Example.of(exampleAnswer));
                uniqueTeams = exampleAnswers.stream().map(AnswerEntity::getUser).map(UserEntity::getTeam).distinct().toList();
                taskStat.setTeamsAttempted(uniqueTeams.size());

                double scoreSum = exampleAnswers.stream().map(AnswerEntity::getScore).mapToInt(Long::intValue).sum();
                taskStat.setAverageScore(scoreSum / exampleAnswers.size());

                long gameStart = optGame.get().getStartDate().getTime();
                double timeSum = exampleAnswers.stream().map(x -> x.getSubmitTime().getTime() - gameStart).mapToInt(Long::intValue).sum();
                taskStat.setAverageSolvingTime(timeSum/exampleAnswers.size());

                tasksStats.add(taskStat);
            }
            // TODO: add functions to calculate avg stats of answers for each task and return those stats
            // probably the best idea is to do something like AvgAnswerEntity/ETO and mapper to it 
            // and return List<> of them instead of generic List<Object>
            return taskStatMapper.mapToETOList(tasksStats);
        } else {
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
        } else {
            gameRepo.delete(gameOptional.get());
            return gameRepo.findById(id).isEmpty();
        }
    }

    @Override
    public Optional<GameEto> findGameById(Long id) {
        Optional<GameEntity> game = gameRepo.findById(id);
        if (game.isPresent()) {
            return Optional.of(gameMapper.mapToETO(game.get()));
        }
        return Optional.empty();
    }
}
