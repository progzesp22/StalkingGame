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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        user.setPassword(encoded("123"));
        user = userService.save(user);
        createRandomUsers(5);
        GameEto gameEto = new GameEto(user.getId());
        gameService.save(gameEto);
    }

    private void createRandomUsers(int n) {
        for(int i = 0; i < n; i++) {
            UserEto user = new UserEto();
            user.setUsername("user" + i);
            user.setPassword(encoded("password" + i));
            userService.save(user);
        }
    }

    private String encoded(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}
