package com.progzesp.stalking.controller;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class GameRestController {

    @Autowired
    private GameService gameService;


    @PostMapping("/games")
    public GameEto addTeacher(@RequestBody GameEto newTeacher) {
        return gameService.save(newTeacher);
    }
}
