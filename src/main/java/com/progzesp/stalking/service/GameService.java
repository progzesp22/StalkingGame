package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.TaskEto;

import java.util.List;
import java.util.Optional;

public interface GameService extends Service {

    GameEto save(GameEto newGame);

    List<GameEto> findAllGames();

    boolean deleteGame(Long id);

    Optional<GameEto> findGameById(Long id);
}
