package com.progzesp.stalking.controller;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/games")
public class GameRestController {

    @Autowired
    private GameService gameService;


    @GetMapping()
    public ResponseEntity<List<GameEto>> findAllGames() {
        final List<GameEto> allGames = this.gameService.findAllGames();
        return ResponseEntity.ok().body(allGames);
    }

    @PostMapping()
    public GameEto addGame(@RequestBody GameEto newGame) {
        return gameService.save(newGame);
    }
}
