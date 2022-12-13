package com.progzesp.stalking;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.UserEto;
import com.progzesp.stalking.persistance.entity.UserEntity;
import com.progzesp.stalking.persistance.repo.UserRepo;
import com.progzesp.stalking.service.GameService;
import com.progzesp.stalking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

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
        GameEto gameEto = new GameEto(userService.getByUsername("siema").getId());
        gameEto.setName("testGame");
        gameService.save(gameEto);
    }
}
