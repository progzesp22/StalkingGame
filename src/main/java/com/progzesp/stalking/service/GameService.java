package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.TaskEto;

import java.util.List;

public interface GameService extends Service {

    GameEto save(GameEto newGame);

    List<GameEto> findAllGames();
}
