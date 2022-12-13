package com.progzesp.stalking.domain.mapper;

import com.progzesp.stalking.domain.TeamEto;
import com.progzesp.stalking.persistance.entity.AbstractEntity;
import com.progzesp.stalking.persistance.entity.TeamEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class TeamMapper implements Mapper<TeamEto, TeamEntity> {
    @Override
    public TeamEto mapToETO(TeamEntity entity) {
        TeamEto eto = new TeamEto();
        eto.setId(entity.getId());
        eto.setName(entity.getName());
        eto.setMembers(entity.getMembers().stream().map(AbstractEntity::getId).collect(Collectors.toList()));
        eto.setCreator(entity.getCreator().getId());
        eto.setGameId(entity.getGameId());
        return eto;
    }

    @Override
    public TeamEntity mapToEntity(TeamEto To) {
        TeamEntity entity = new TeamEntity();
        entity.setId(To.getId());
        entity.setName(To.getName());
        entity.setMembers(new ArrayList<>());
        return entity;
    }
}
