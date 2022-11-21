package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.persistance.entity.GameState;

import java.util.List;

public interface GameService extends Service {

    GameEto save(GameEto newGame);

    List<GameEto> findAllGames();

    GameState openWaitingRoom(Long id);

    GameState startGameplay(Long id);

    GameState endGameplay(Long id);

    boolean deleteGame(Long id);
}
