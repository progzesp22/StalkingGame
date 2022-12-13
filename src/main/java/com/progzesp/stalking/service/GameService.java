package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.domain.TaskStatEto;
import com.progzesp.stalking.domain.TeamEto;
import com.progzesp.stalking.persistance.entity.GameState;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.util.Pair;

public interface GameService extends Service {

    Pair<Integer, GameEto> save(GameEto newGame, Principal user);

    GameEto save(GameEto newGame);

    List<GameEto> findAllGames();

    List<TeamEto> findTeamsSortedDesc(Long id);

    List<TaskStatEto> findAvgTasksStats(Long id);

    GameState openWaitingRoom(Long id);

    GameState startGameplay(Long id);

    GameState endGameplay(Long id);

    boolean deleteGame(Long id);

    Optional<GameEto> findGameById(Long id);
}
