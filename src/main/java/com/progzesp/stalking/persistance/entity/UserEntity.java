package com.progzesp.stalking.persistance.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class UserEntity extends AbstractEntity {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private List<TeamEntity> teams;

    @Transient
    public List<TeamEntity> getTeams() {
        return this.teams;
    }

    public TeamEntity getTeamByGameId(Long id){
        for(TeamEntity team : teams){
            if(team.getGameId() == id){
                return team;
            }
        }
        return null;
    }

    public void setTeams(List<TeamEntity> teams) {
        this.teams = teams;
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
