package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.persistance.entity.GameState;
import org.springframework.data.util.Pair;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface GameService extends Service {

    Pair<Integer, GameEto> save(GameEto newGame, Principal user);

    GameEto save(GameEto newGame);

    List<GameEto> findAllGames();

    GameState openWaitingRoom(Long id);

    GameState startGameplay(Long id);

    GameState endGameplay(Long id);

    boolean deleteGame(Long id);

    Optional<GameEto> findGameById(Long id);

    Pair<Integer, GameEto> modify(Principal user, GameEto newGame, Long id);
}
