package com.progzesp.stalking.persistance.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "team")
public class TeamEntity extends AbstractEntity{

    @NotNull
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<UserEntity> members;

    @NotNull
    @ManyToOne
    private GameEntity game;

    @NotNull
    private int score;

    @Transient
    public Long getGameId() {

        if (this.game == null) {
            return null;
        }
        return this.game.getId();
    }

    public void setGameId(Long gameId) {

        if (gameId == null) {
            this.game = null;
        } else {
            GameEntity gameEntity = new GameEntity();
            gameEntity.setId(gameId);
            this.game = gameEntity;
        }
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(List<UserEntity> members) {
        this.members = members;
    }

    public void setScore(int newScore){
        this.score = newScore;
    }

    public void updateScore(int newScore){
        this.score += newScore;
    }

    public int getScore(){
        return this.score;
    }
}
