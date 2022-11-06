package com.progzesp.stalking.controller;

import com.progzesp.stalking.domain.AnswerEto;
import com.progzesp.stalking.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/answers")
public class AnswerRestController {

    @Autowired
    private AnswerService answerService;


    @GetMapping("/unchecked")
    public ResponseEntity<List<AnswerEto>> findAllUncheckedAnswers() {
        final List<AnswerEto> allTasks = this.answerService.findAllUncheckedAnswers();
        return ResponseEntity.ok().body(allTasks);
    }

    @GetMapping()
    public ResponseEntity<List<AnswerEto>> findAllAnswers() {
        final List<AnswerEto> allTasks = this.answerService.findAllAnswers();
        return ResponseEntity.ok().body(allTasks);
    }

    @PostMapping()
    public AnswerEto addAnswer(@RequestBody AnswerEto newAnswer) {
        return answerService.save(newAnswer);
    }

    @PatchMapping("/{id}")
    public AnswerEto modifyAnswer(@PathVariable("id") Long id, @RequestBody AnswerEto answerEto) {
        return answerService.modifyAnswer(id, answerEto);
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
