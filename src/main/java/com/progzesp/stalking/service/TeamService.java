package com.progzesp.stalking.service;

import com.progzesp.stalking.domain.TeamEto;
import org.springframework.data.util.Pair;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface TeamService extends Service {
    Pair<Integer, List<TeamEto>> findTeamsByCriteria(Principal user, Optional<Long> gameId, Optional<String> filter);

    Pair<Integer, TeamEto> save(Principal user, TeamEto newTeam);

    Pair<Integer, TeamEto> findTeamById(Long teamId);

    Pair<Integer, TeamEto> modifyTeam(Principal user, Long id, TeamEto teamEto);

    Pair<Integer, TeamEto> join(Principal user, Long id);
}
