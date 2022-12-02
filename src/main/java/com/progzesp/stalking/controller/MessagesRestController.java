package com.progzesp.stalking.controller;

import com.progzesp.stalking.domain.MessageEto;
import com.progzesp.stalking.domain.MessageInputEto;
import com.progzesp.stalking.persistance.entity.MessageEntity;
import com.progzesp.stalking.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/messages")
public class MessagesRestController {

    @Autowired
    private GameService gameService;

    @GetMapping()
    public ResponseEntity<List<MessageEto>> findSpecificAnswers(@RequestParam Optional<Long> gameId, @RequestParam Optional<Long> newerThan) {
        final List<MessageEto> messages = this.gameService.findMessagesByCriteria( gameId, newerThan);
        return ResponseEntity.ok().body(messages);
    }
    @PostMapping()
    public MessageEto addAnswer(@RequestBody MessageInputEto newMessage) {
        return gameService.addMessage(newMessage);
    }


}
