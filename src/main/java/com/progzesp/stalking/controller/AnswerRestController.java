package com.progzesp.stalking.controller;

import com.google.gson.Gson;
import com.progzesp.stalking.domain.AnswerEto;
import com.progzesp.stalking.domain.AnswerEtoNoResponse;
import com.progzesp.stalking.domain.answer.NavPosEto;
import com.progzesp.stalking.domain.answer.NoNavPosEto;
import com.progzesp.stalking.persistance.entity.TaskType;
import com.progzesp.stalking.service.AnswerService;
import com.progzesp.stalking.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/rest/answers")
public class AnswerRestController {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private TaskService taskService;

    @GetMapping()
    public ResponseEntity<List<AnswerEtoNoResponse>> findSpecificAnswers(@RequestParam Optional<Long> gameId, @RequestParam Optional<String> filter) {
        final List<AnswerEto> answers = this.answerService.findAnswersByCriteria( gameId, filter);
        return ResponseEntity.ok().body(answers.stream().map(AnswerEto::makeBodyWithoutResponse).toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<AnswerEto> findAnswerById(@PathVariable("id") Long id) {
        final AnswerEto answer = this.answerService.findAnswerById(id);
        return ResponseEntity.ok().body(answer);
    }

    @PostMapping()
    public AnswerEto addAnswer(HttpEntity<String> httpEntity) {
        AnswerEto newAnswer = getAnswerFromHttpEntity(httpEntity);
        return answerService.save(newAnswer);
    }

    @PatchMapping("/{id}")
    public AnswerEto modifyAnswer(@PathVariable("id") Long id, HttpEntity<String> httpEntity) {
        AnswerEto answerEto = getAnswerFromHttpEntity(httpEntity);
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

    public AnswerEto getAnswerFromHttpEntity(HttpEntity<String> httpEntity) {
        Long id = Long.parseLong(Objects.requireNonNull(httpEntity.getHeaders().getFirst("taskId")));
        TaskType taskType = taskService.findTask(id).getType();
        Class<? extends AnswerEto> answerEtoClass;
        if (taskType == TaskType.LOCALIZATION)
            answerEtoClass = NavPosEto.class;
        else
            answerEtoClass = NoNavPosEto.class;
        Gson gson = new Gson();
        AnswerEto answerEto = gson.fromJson(httpEntity.getBody(), answerEtoClass);
        answerEto.setType(taskType);
        return answerEto;
    }
}
