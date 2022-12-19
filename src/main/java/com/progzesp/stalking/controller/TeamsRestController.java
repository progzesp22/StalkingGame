package com.progzesp.stalking.controller;

import com.progzesp.stalking.domain.TeamEto;
import com.progzesp.stalking.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamsRestController {
    @Autowired
    private TeamService teamService;

    @GetMapping()
    public ResponseEntity<List<TeamEto>> findTeams(Principal user, @RequestParam Optional<Long> gameId, @RequestParam Optional<String> filter) {
        Pair<Integer, List<TeamEto>> teams = this.teamService.findTeamsByCriteria(user, gameId, filter);
        if (teams.getFirst() == 200) {
            return ResponseEntity.ok().body(teams.getSecond());
        } else {
            return ResponseEntity.status(teams.getFirst()).body(null);
        }
    }

    @PostMapping()
    public ResponseEntity<TeamEto> addTeam(Principal user, @RequestBody TeamEto newTeam) {
        final Pair<Integer, TeamEto> team = teamService.save(user, newTeam);
        if (team.getFirst() == 200) {
            return ResponseEntity.ok().body(team.getSecond());
        } else {
            return ResponseEntity.status(team.getFirst()).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamEto> findTeamById(@PathVariable("id") Long id) {
        final Pair<Integer, TeamEto> team = this.teamService.findTeamById(id);
        if (team.getFirst() == 200) {
            return ResponseEntity.ok().body(team.getSecond());
        } else {
            return ResponseEntity.status(team.getFirst()).body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeamEto> modifyTeam(Principal user, @PathVariable("id") Long id, @RequestBody TeamEto teamEto) {
        final Pair<Integer, TeamEto> team = teamService.modifyTeam(user, id, teamEto);
        if (team.getFirst() == 200) {
            return ResponseEntity.ok().body(team.getSecond());
        } else {
            return ResponseEntity.status(team.getFirst()).body(null);
        }
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<TeamEto> joinTeam(Principal user, @PathVariable("id") Long id) {
        final Pair<Integer, TeamEto> team = teamService.join(user, id);
        if (team.getFirst() == 200) {
            return ResponseEntity.ok().body(team.getSecond());
        } else {
            return ResponseEntity.status(team.getFirst()).body(null);
        }
    }
}
