package com.progzesp.stalking.persistance.entity.game;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("TIME")
public class TimeEndGameEntity extends GameEntity {

    private Timestamp endTime;

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
