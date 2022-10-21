package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.TaskEto;

public interface GameService extends Service {

    GameEto save(GameEto newGame);
}
