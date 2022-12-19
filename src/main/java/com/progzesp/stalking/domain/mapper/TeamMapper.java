package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.TeamEto;
import com.progzesp.stalking.persistance.entity.TeamEntity;
import com.progzesp.stalking.persistance.entity.UserEntity;
import com.progzesp.stalking.persistance.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TeamMapper implements Mapper<TeamEto, TeamEntity> {
    @Autowired
    private UserRepo userRepo;

    @Override
    public TeamEto mapToETO(TeamEntity entity) {
        TeamEto eto = new TeamEto();
        eto.setId(entity.getId());
        eto.setName(entity.getName());
        eto.setMembers(entity.getMembers().stream().map(UserEntity::getUsername).collect(Collectors.toList()));
        eto.setCreator(entity.getCreator().getUsername());
        eto.setGameId(entity.getGameId());
        return eto;
    }

    @Override
    public TeamEntity mapToEntity(TeamEto To) {
        TeamEntity entity = new TeamEntity();
        entity.setId(To.getId());
        entity.setName(To.getName());
        if (To.getMembers() != null) {
            entity.setMembers(To.getMembers().stream().map(userRepo::getByUsername).collect(Collectors.toList()));
        }
        return entity;
    }
}
