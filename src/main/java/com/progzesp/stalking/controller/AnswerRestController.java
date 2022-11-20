package com.progzesp.stalking.controller;

import com.progzesp.stalking.domain.AnswerEto;
import com.progzesp.stalking.domain.AnswerEtoNoResponse;
import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.service.AnswerService;
import com.progzesp.stalking.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/answers")
public class AnswerRestController {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private TaskService taskService;


    @GetMapping("/unchecked")
    public ResponseEntity<List<AnswerEto>> findAllUncheckedAnswers() {
        final List<AnswerEto> allAnswers = this.answerService.findAllUncheckedAnswers();
        return ResponseEntity.ok().body(allAnswers);
    }
    @GetMapping(params = { "gameId", "filter" })
    public ResponseEntity<List<AnswerEtoNoResponse>> findSpecificAnswers(@RequestParam Long gameId, @RequestParam String filter) {
        boolean checked = false;
        boolean getAll = false;
        if (Objects.equals(filter, "checked")) {
            checked = true;
        }
        else if (Objects.equals(filter, "all")) {
            getAll = true;
        }
        final List<AnswerEto> allAnswers = this.answerService.findAllAnswers();
        boolean finalChecked = checked;
        boolean finalGetAll = getAll;
        List<AnswerEto> gameAnswers = allAnswers.stream().filter(
                p -> Objects.equals(taskService.findAllTasks().stream().filter(q -> q.getId().equals(p.getTaskId())).toList().get(0).getGameId(), gameId)
                        && (p.isChecked() == finalChecked || finalGetAll)
        ).toList();
        return ResponseEntity.ok().body(gameAnswers.stream().map(AnswerEto::makeBodyWithoutResponse).toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AnswerEto> findIdAnswer(@PathVariable("id") Long id) {
        final List<AnswerEto> allAnswers = this.answerService.findAllAnswers();
        return ResponseEntity.ok().body(allAnswers.stream().filter(p -> p.getId().equals(id)).toList().get(0));
    }
    @GetMapping()
    public ResponseEntity<List<AnswerEto>> findAllAnswers() {
        final List<AnswerEto> allAnswers = this.answerService.findAllAnswers();
        return ResponseEntity.ok().body(allAnswers);
    }

    @PostMapping()
    public AnswerEto addAnswer(@RequestBody AnswerEto newAnswer) {
        return answerService.save(newAnswer);
    }

    @PutMapping("/{id}")
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
