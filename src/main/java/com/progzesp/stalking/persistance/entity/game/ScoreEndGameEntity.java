package com.progzesp.stalking.persistance.entity.game;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SCORE")
public class ScoreEndGameEntity extends GameEntity {

    private Integer endScore;

    public Integer getEndScore() {
        return endScore;
    }

    public void setEndScore(Integer endScore) {
        this.endScore = endScore;
    }
}
