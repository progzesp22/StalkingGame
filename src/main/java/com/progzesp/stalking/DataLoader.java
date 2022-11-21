package com.progzesp.stalking;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.persistance.entity.UserEntity;
import com.progzesp.stalking.persistance.repo.UserRepo;
import com.progzesp.stalking.service.GameService;
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
    UserRepo userRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername("siema");
        user.setPassword("123");
        userRepo.save(user);
        GameEto gameEto = new GameEto(userRepo.getByUsername("siema").getId());
        gameService.save(gameEto);
    }
}
