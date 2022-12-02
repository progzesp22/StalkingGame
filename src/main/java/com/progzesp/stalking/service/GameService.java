package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.MessageEto;
import com.progzesp.stalking.domain.MessageInputEto;
import com.progzesp.stalking.persistance.entity.GameState;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.progzesp.stalking.persistance.entity.MessageEntity;
import org.springframework.data.util.Pair;

public interface GameService extends Service {

    Pair<Integer, GameEto> save(GameEto newGame, Principal user);

    GameEto save(GameEto newGame);

    List<GameEto> findAllGames();

    GameState openWaitingRoom(Long id);

    GameState startGameplay(Long id);

    GameState endGameplay(Long id);

    boolean deleteGame(Long id);

    Optional<GameEto> findGameById(Long id);

    Pair <Integer, List<MessageEto> >findMessagesByCriteria(Optional<Long> gameId, Optional<Long> newerThan);

    Pair<Integer, MessageEto > addMessage(MessageInputEto input);
}
