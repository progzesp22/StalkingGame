package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.GameEto;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.util.Pair;

public interface GameService extends Service {

    Pair<Integer, GameEto> save(GameEto newGame, Principal user);

    GameEto save(GameEto newGame);

    List<GameEto> findAllGames();

    boolean deleteGame(Long id);

    Optional<GameEto> findGameById(Long id);
}
