package com.progzesp.stalking.persistance.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class UserEntity extends AbstractEntity {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @ManyToOne
    private TeamEntity team;

    @Transient
    public Long getTeamId() {

        if (this.team == null) {
            return null;
        }
        return this.team.getId();
    }

    public void setTeamId(Long teamId) {

        if (teamId == null) {
            this.team = null;
        } else {
            TeamEntity teamEntity = new TeamEntity();
            teamEntity.setId(teamId);
            this.team = teamEntity;
        }
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
