package com.progzesp.stalking.scheduler;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.persistance.entity.EndCondition;
import com.progzesp.stalking.persistance.entity.GameState;
import com.progzesp.stalking.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class GameSchedulingConfig {

	@Autowired
	private GameService gameService;

	@Scheduled(fixedDelay = 5000, initialDelay = 1000)
	public void scheduleFixedRateWithInitialDelayTask() {
		List<GameEto> games = gameService.findAllGames();
		for (GameEto gameEto : games) {
			if (!gameEto.getState().equals(GameState.STARTED) || !gameEto.getEndCondition().equals(EndCondition.TIME))
				continue;
			Date now = new Date(System.currentTimeMillis());
			if (now.after(gameEto.getEndTime())) {
				gameService.endGameplay(gameEto.getId());
			}
		}
	}

}
