package com.progzesp.stalking.controller;

import com.progzesp.stalking.domain.MessageEto;
import com.progzesp.stalking.domain.MessageInputEto;
import com.progzesp.stalking.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessagesRestController {

    @Autowired
    private MessageService messageService;

    @GetMapping()
    public ResponseEntity<List<MessageEto>> getGameMessages(@RequestParam Optional<Long> gameId, @RequestParam Optional<Long> newerThan) {
        final Pair<Integer, List<MessageEto> > response = messageService.findMessagesByCriteria( gameId, newerThan);
        if(response.getFirst() == 200){
            return ResponseEntity.ok().body(response.getSecond());
        }
        else if(response.getFirst() == 400){
            return ResponseEntity.status(400).body(null);
        }
        else{
            return ResponseEntity.status(400).body(null);
        }
    }
    @PostMapping()
    public ResponseEntity<MessageEto> addMessage(Principal user, @RequestBody MessageInputEto newMessage) {
        final Pair<Integer, MessageEto > response = messageService.addMessage(newMessage, user);
        if(response.getFirst() == 200){
            return ResponseEntity.ok().body(response.getSecond());
        }
        else if (response.getFirst() == 403) {
            return ResponseEntity.status(403).body(null);
        }
        else{
            return ResponseEntity.status(400).body(null);
        }
    }


}
