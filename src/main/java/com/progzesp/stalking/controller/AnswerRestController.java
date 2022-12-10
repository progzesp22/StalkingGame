package com.progzesp.stalking.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import java.util.Optional;

@RestController
@RequestMapping("/rest/answers")
public class AnswerRestController {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private TaskService taskService;
    private final Gson gson = new Gson();

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
        if (newAnswer == null)
            return null;
        return answerService.save(newAnswer);
    }

    @PatchMapping("/{id}")
    public AnswerEto modifyAnswer(@PathVariable("id") Long id, HttpEntity<String> httpEntity) {
        JsonObject jsonObject = gson.fromJson(httpEntity.getBody(), JsonObject.class);
        return answerService.modifyAnswer(id, jsonObject);
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
