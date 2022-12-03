package com.progzesp.stalking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.progzesp.stalking.domain.TeamEto;
import com.progzesp.stalking.service.GameService;
import com.progzesp.stalking.service.TaskService;

@RestController
@RequestMapping("/rest/stat")
public class StatsRestController {

    @Autowired 
    private GameService gameService;
    
    @GetMapping("/leaderboard")
    public ResponseEntity<List<TeamEto>> teamScores(@RequestParam(name = "gameId") Long gameId){
        List<TeamEto> teams = gameService.findTeamsSortedDesc(gameId);
        if(teams == null){
            return ResponseEntity.status(400).body(null);
        }
        else{
            return ResponseEntity.ok().body(teams);
        }
    }

    @GetMapping("/tasks")
    public ResponseEntity<Object> avgTasksStatsGame(@RequestParam(name = "gameId") Long gameId){
        List<Object> avgTasksStats = gameService.findAvgTasksStats(gameId);
        if(avgTasksStats == null){
            return ResponseEntity.status(400).body(null);
        }
        else{
            return ResponseEntity.ok().body(avgTasksStats);
        }
    }
}
