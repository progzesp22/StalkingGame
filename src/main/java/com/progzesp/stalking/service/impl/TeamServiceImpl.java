package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.TeamEto;
import com.progzesp.stalking.domain.mapper.TeamMapper;
import com.progzesp.stalking.persistance.entity.GameEntity;
import com.progzesp.stalking.persistance.entity.TeamEntity;
import com.progzesp.stalking.persistance.entity.UserEntity;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.TeamRepo;
import com.progzesp.stalking.persistance.repo.UserRepo;
import com.progzesp.stalking.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private TeamRepo teamRepo;


    @Override
    public Pair<Integer, List<TeamEto>> findTeamsByCriteria(Principal user, Optional<Long> gameId, Optional<String> filter) {
        TeamEntity toFind = new TeamEntity();
        if (gameId.isPresent()) {
            Optional<GameEntity> game = gameRepo.findById(gameId.get());
            if (game.isPresent()) {
                toFind.setGame(game.get());
            } else {
                return Pair.of(400, new ArrayList<>());
            }
        } else if (filter.isPresent()) {
            if (filter.get().equals("joined")) {
                UserEntity ue = userRepo.getByUsername(user.getName());
                return Pair.of(200, teamMapper.mapToETOList(ue.getTeams()));
            }
        }
        List<TeamEntity> result = teamRepo.findAll(Example.of(toFind));
        return Pair.of(200, teamMapper.mapToETOList(result));
    }

    @Override
    public Pair<Integer, TeamEto> save(Principal user, TeamEto newTeam) {
        Optional<GameEntity> game = gameRepo.findById(newTeam.getGameId());
        TeamEntity teamEntity = teamMapper.mapToEntity(newTeam);
        if (game.isPresent()) {
            UserEntity ue = userRepo.getByUsername(user.getName());
            removeUserFromAllTeamsInGame(ue, game.get());

            List<UserEntity> members = new ArrayList<>();
            members.add(ue);
            teamEntity.setGame(game.get());
            teamEntity.setScore(0);
            teamEntity.setMembers(members);
            teamEntity.setCreator(ue);
            teamEntity = teamRepo.save(teamEntity);
            return Pair.of(200, teamMapper.mapToETO(teamEntity));
        } else {
            return Pair.of(400, newTeam);
        }
    }

    @Override
    public Pair<Integer, TeamEto> findTeamById(Long teamId) {
        Optional<TeamEntity> teamOpt = teamRepo.findById(teamId);
        if (teamOpt.isEmpty()) {
            return Pair.of(400, new TeamEto());
        } else {
            return Pair.of(200, teamMapper.mapToETO(teamOpt.get()));
        }

    }

    @Override
    public Pair<Integer, TeamEto> modifyTeam(Principal user, Long id, TeamEto teamEto) {
        Optional<TeamEntity> foundEntity = teamRepo.findById(id);
        if (foundEntity.isPresent()) {
            TeamEntity teamEntity = foundEntity.get();
            if (!teamEntity.getCreator().getUsername().equals(user.getName()) &&
                    !user.getName().equals(teamEntity.getGame().getGameMaster().getUsername())) {
                return Pair.of(403, new TeamEto());
            }
            if (teamEto.getMembers() != null) {
                if (!teamEto.getMembers().contains(teamEntity.getCreator().getUsername())) {
                    return Pair.of(400, new TeamEto());
                }
            }
            TeamEntity teamToSave = teamMapper.mapToEntity(teamEto);
            try {
                copyNonStaticNonNull(teamEntity, teamToSave);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            teamRepo.save(teamEntity);
            return Pair.of(200, teamMapper.mapToETO(teamEntity));
        } else {
            return Pair.of(400, new TeamEto());
        }
    }

    private void removeUserFromAllTeamsInGame(UserEntity ue, GameEntity ge){
        TeamEntity teamExample = new TeamEntity();
        teamExample.setGame(ge);
        List<TeamEntity> teams = teamRepo.findAll(Example.of(teamExample));
        for(TeamEntity team : teams){
            List<UserEntity> members = team.getMembers();
            members.remove(ue);
            team.setMembers(members);
            teamRepo.save(team);
        }
    }

    @Override
    public Pair<Integer, TeamEto> join(Principal user, Long id) {
        Optional<TeamEntity> teamOpt = teamRepo.findById(id);
        if (teamOpt.isEmpty()) {
            return Pair.of(400, new TeamEto());
        } else {
            TeamEntity team = teamOpt.get();
            GameEntity game = team.getGame();
            UserEntity ue = userRepo.getByUsername(user.getName());
            List<UserEntity> members = team.getMembers();
            if (members.contains(ue)) {
                return Pair.of(400, new TeamEto());
            } else {
                removeUserFromAllTeamsInGame(ue, game);
                members.add(ue);
                team.setMembers(members);
                team = teamRepo.save(team);
            }
            return Pair.of(200, teamMapper.mapToETO(team));
        }
    }
}
