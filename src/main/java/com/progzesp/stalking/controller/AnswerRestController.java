package com.progzesp.stalking.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.progzesp.stalking.domain.answer.*;
import com.progzesp.stalking.persistance.entity.TaskType;
import com.progzesp.stalking.service.AnswerService;
import com.progzesp.stalking.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/answers")
public class AnswerRestController {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private TaskService taskService;
    private final Gson gson = new Gson();

    @GetMapping()
    public ResponseEntity<List<AnswerEtoNoResponse>> findSpecificAnswers(Principal user, @RequestParam Optional<Long> gameId, @RequestParam Optional<String> filter) {
        final Pair<Integer, List<AnswerEto>> answers = this.answerService.findAnswersByCriteria(gameId, filter, user);
        int statusCode = answers.getFirst();
        if(statusCode == 200) {
            return ResponseEntity.ok().body(answers.getSecond().stream().map(AnswerEto::makeBodyWithoutResponse).toList());
        }
        return ResponseEntity.status(statusCode).body(null);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AnswerEto> findAnswerById(Principal user, @PathVariable("id") Long id) {
        final Pair<Integer, AnswerEto> answer = this.answerService.findAnswerById(id, user);
        int statusCode = answer.getFirst();
        if (statusCode == 200) {
            return ResponseEntity.ok().body(answer.getSecond());
        }
        return ResponseEntity.status(statusCode).body(null);
    }

    @PostMapping()
    public ResponseEntity<AnswerEto> addAnswer(Principal user, HttpEntity<String> httpEntity) {
        AnswerEto newAnswer = getAnswerFromHttpEntity(httpEntity);
        if (newAnswer == null)
            return ResponseEntity.status(400).body(null);
        Pair<Integer, AnswerEto> response = answerService.save(newAnswer, user);
        int statusCode = response.getFirst();
        if(statusCode == 200) {
            return ResponseEntity.ok().body(response.getSecond());
        }
        return ResponseEntity.status(statusCode).body(null);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ModifyAnswerEto> modifyAnswer(Principal user, @PathVariable("id") Long id, ModifyAnswerEto answerEto) {
        Pair<Integer, ModifyAnswerEto> response = answerService.modifyAnswer(id, answerEto, user);
        int statusCode = response.getFirst();
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

    public AnswerEto getAnswerFromHttpEntity(HttpEntity<String> httpEntity) {
        JsonObject jsonObject = gson.fromJson(httpEntity.getBody(), JsonObject.class);
        if (jsonObject.has("taskId")) {
            try {
                Long taskId = Long.parseLong(String.valueOf(jsonObject.get("taskId")));
                TaskType taskType = taskService.findTask(taskId).getType();
                Class<? extends AnswerEto> answerEtoClass;
                if (taskType == TaskType.LOCALIZATION)
                    answerEtoClass = NavPosEto.class;
                else
                    answerEtoClass = NoNavPosEto.class;
                AnswerEto answerEto = gson.fromJson(httpEntity.getBody(), answerEtoClass);
                answerEto.setType(taskType);
                return answerEto;
            } catch (Exception ignored) {}
        }
        return null;
    }
}
