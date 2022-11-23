package com.progzesp.stalking.controller;

import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/tasks")
public class TaskRestController {

    @Autowired
    private TaskService taskService;


    @GetMapping()
    public ResponseEntity<List<TaskEto>> findGameTasks(@RequestParam Optional<Long> gameId) {
        List<TaskEto> tasks = this.taskService.findTasksByCriteria(gameId);
        return ResponseEntity.ok().body(tasks);
    }

    @PostMapping()
    public TaskEto addTask(@RequestBody TaskEto newTask) {
        return taskService.save(newTask);
    }

    //NOTE: PUT mapping requests to send all parameters again.
    // If we only need to change some fields we might change to PATCH mapping later.
    @PatchMapping("/{id}")
    public TaskEto modifyTask(@PathVariable("id") Long id, @RequestBody TaskEto taskEto) {
        return taskService.modifyTask(id ,taskEto);
    }

    /**
     * Deletes the task with specified id.
     * @param id id of the object to delete.
     * @return true if delete successful. False if object with this id does not exist or delete unsuccessful.
     */
    @DeleteMapping("/{id}")
    public boolean deleteTask(@PathVariable("id") Long id) {
        return taskService.deleteTask(id);
    }
}
