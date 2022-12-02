package com.progzesp.stalking.controller;

import com.progzesp.stalking.domain.AnswerEto;
import com.progzesp.stalking.domain.AnswerEtoNoResponse;
import com.progzesp.stalking.domain.GameEto;
import com.progzesp.stalking.service.AnswerService;
import com.progzesp.stalking.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/rest/answers")
public class AnswerRestController {

    @Autowired
    private AnswerService answerService;

    @GetMapping()
    public ResponseEntity<List<AnswerEtoNoResponse>> findSpecificAnswers(Principal user, @RequestParam Optional<Long> gameId, @RequestParam Optional<String> filter) {
        final Pair<Integer, List<AnswerEto>> answers = this.answerService.findAnswersByCriteria(gameId, filter, user);
        Integer statusCode = answers.getFirst();
        if(statusCode == 200) {
            return ResponseEntity.ok().body(answers.getSecond().stream().map(AnswerEto::makeBodyWithoutResponse).toList());
        }
        return ResponseEntity.status(statusCode).body(null);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AnswerEto> findAnswerById(Principal user, @PathVariable("id") Long id) {
        final Pair<Integer, AnswerEto> answer = this.answerService.findAnswerById(id, user);
        Integer statusCode = answer.getFirst();
        if (statusCode == 200) {
            return ResponseEntity.ok().body(answer.getSecond());
        }
        return ResponseEntity.status(statusCode).body(null);
    }

    @PostMapping()
    public ResponseEntity<AnswerEto> addAnswer(Principal user, @RequestBody AnswerEto newAnswer) {
        Pair<Integer, AnswerEto> response = answerService.save(newAnswer, user);
        Integer statusCode = response.getFirst();
        if(statusCode == 200) {
            return ResponseEntity.ok().body(response.getSecond());
        }
        return ResponseEntity.status(statusCode).body(null);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AnswerEto> modifyAnswer(Principal user, @PathVariable("id") Long id, @RequestBody AnswerEto answerEto) {
        Pair<Integer, AnswerEto> response = answerService.modifyAnswer(id, answerEto, user);
        Integer statusCode = response.getFirst();
        if(statusCode == 200) {
            return ResponseEntity.ok().body(response.getSecond());
        }
        return ResponseEntity.status(statusCode).body(null);
    }

    /**
     * Deletes the task with specified id.
     * @param id id of the object to delete.
     * @return true if delete successful. False if object with this id does not exist or delete unsuccessful.
     */
    @DeleteMapping("/{id}")
    public boolean deleteTask(@PathVariable("id") Long id) {
        return answerService.deleteAnswer(id);
    }
}
