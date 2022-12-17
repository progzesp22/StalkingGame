package com.progzesp.stalking;

import com.progzesp.stalking.domain.game.GameEto;
import com.progzesp.stalking.domain.UserEto;
import com.progzesp.stalking.persistance.entity.game.EndCondition;
import com.progzesp.stalking.service.GameService;
import com.progzesp.stalking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    GameService gameService;

    @Autowired
    UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserEto user = new UserEto();
        user.setUsername("siema");
        user.setPassword("123");
        userService.encodePasswordAndSave(user);
        /*GameEto gameEto = new GameEto(userService.getByUsername("siema").getId());
        gameEto.setEndCondition(EndCondition.MANUAL);
        gameEto.setNumberOfTeams(4);
        gameEto.setNumberOfPlayersInTeam(1);
        gameEto.setName("testGame");
        gameService.save(gameEto);*/
    }
}
