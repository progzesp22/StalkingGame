package com.progzesp.stalking.controller;

import com.progzesp.stalking.domain.TaskEto;
import com.progzesp.stalking.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskRestController {

    @Autowired
    private TaskService taskService;



    @GetMapping()
    public ResponseEntity<List<TaskEto>> findGameTasks(@RequestParam Optional<Long> gameId) {
        List<TaskEto> tasks = this.taskService.findTasksByCriteria(gameId);
        return ResponseEntity.ok().body(tasks);
    }

    @PostMapping()
    public ResponseEntity<TaskEto> addTask(Principal user, @RequestBody TaskEto newTask) {
        Pair<Integer, TaskEto> response = taskService.save(newTask, user);
        if(response.getFirst() == 200){
            return ResponseEntity.ok().body(response.getSecond());
        }
        else if(response.getFirst() == 403){
            return ResponseEntity.status(403).body(null);
        }    
        else if(response.getFirst() == 400){
            return ResponseEntity.status(400).body(null);
        }
        else{
            return ResponseEntity.status(400).body(null);
        }
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
