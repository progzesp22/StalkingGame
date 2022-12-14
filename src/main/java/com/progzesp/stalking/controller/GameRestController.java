package com.progzesp.stalking.controller;

import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.service.GameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games")
public class GameRestController {

    @Autowired
    private GameService gameService;

    @GetMapping()
    public ResponseEntity<List<GameEto>> findAllGames() {
        final List<GameEto> allGames = this.gameService.findAllGames();
        return ResponseEntity.ok().body(allGames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameEto> findGameById(@PathVariable("id") Long id) {
        final Optional<GameEto> answer = this.gameService.findGameById(id);
        return answer.map(gameEto -> ResponseEntity.ok().body(gameEto)).orElseGet(() -> ResponseEntity.status(400).body(null));
    }

    @PostMapping()
    public ResponseEntity<GameEto> addGame(Principal user, @RequestBody GameEto newGame) {
        Pair<Integer, GameEto> response = gameService.save(newGame, user);
        if (response.getFirst() == 200) {
            return ResponseEntity.ok().body(response.getSecond());
        } else {
            return ResponseEntity.status(response.getFirst()).body(null);
        }
    }

    /**
     * Deletes the game alongside with all the tasks assigned to it.
     *
     * @param id id of the object to delete.
     * @return true if delete successful. False if object with this id does not exist or delete unsuccessful.
     */
    @DeleteMapping("/{id}")
    public boolean deleteGame(@PathVariable("id") Long id) {
        return gameService.deleteGame(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GameEto> modifyGame(Principal user, @RequestBody GameEto newGame, @PathVariable("id") Long id) {
        Pair<Integer, GameEto> response = gameService.modify(user, newGame, id);
        if (response.getFirst() == 200) {
            return ResponseEntity.ok().body(response.getSecond());
        } else {
            return ResponseEntity.status(response.getFirst()).body(null);
        }
    }
}
