package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.TaskStatEto;
import com.progzesp.stalking.domain.TeamEto;
import com.progzesp.stalking.domain.mapper.GameMapper;
import com.progzesp.stalking.domain.mapper.TaskStatMapper;
import com.progzesp.stalking.domain.mapper.TeamMapper;
import com.progzesp.stalking.persistance.entity.*;
import com.progzesp.stalking.persistance.entity.answer.AnswerEntity;
import com.progzesp.stalking.persistance.entity.answer.AudioEntity;
import com.progzesp.stalking.persistance.entity.answer.NavPosEntity;
import com.progzesp.stalking.persistance.entity.answer.PhotoEntity;
import com.progzesp.stalking.persistance.entity.answer.QREntity;
import com.progzesp.stalking.persistance.entity.answer.TextEntity;
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
                taskStat.setMaxScore(task.getMaxScore());
                taskStat.setTaskId(task.getId());

                TaskType taskType = task.getTaskType();
                AnswerEntity exampleAnswer;

                switch(taskType){
                    case PHOTO:
                        exampleAnswer = new PhotoEntity();
                        break;
                    case LOCALIZATION:
                        exampleAnswer = new NavPosEntity();
                        break;
                    case QR_CODE:
                        exampleAnswer = new QREntity();
                        break;
                    case TEXT:
                        exampleAnswer = new TextEntity();
                        break;
                    case AUDIO:
                        exampleAnswer = new AudioEntity();
                        break;
                    default:
                        return null;
                }
                
                exampleAnswer.setTask(task);
                List<AnswerEntity> exampleAnswers = answerRepo.findAll(Example.of(exampleAnswer));
                List<UserEntity> uniqueUsers = exampleAnswers.stream().map(AnswerEntity::getUser).distinct().toList();
                List<TeamEntity> uniqueTeams = new ArrayList<>();
                for(UserEntity user : uniqueUsers){
                    uniqueTeams.add(user.getTeamByGameId(id));
                }
                uniqueTeams = uniqueTeams.stream().distinct().toList();
                taskStat.setTeamsAttempted(uniqueTeams.size());

                uniqueTeams = new ArrayList<>();
                exampleAnswer.setApproved(true);
                exampleAnswers = answerRepo.findAll(Example.of(exampleAnswer));
                uniqueUsers = exampleAnswers.stream().map(AnswerEntity::getUser).distinct().toList();
                for(UserEntity user : uniqueUsers){
                    uniqueTeams.add(user.getTeamByGameId(id));
                }
                uniqueTeams = uniqueTeams.stream().distinct().toList();
                taskStat.setTeamsApproved(uniqueTeams.size());

                double scoreSum = exampleAnswers.stream().map(AnswerEntity::getScore).reduce(0, Integer::sum);
                taskStat.setAverageScore(scoreSum / exampleAnswers.size());

                long gameStart = optGame.get().getStartTime().getTime();
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
