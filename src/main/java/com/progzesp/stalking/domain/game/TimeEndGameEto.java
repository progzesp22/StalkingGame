package com.progzesp.stalking.domain.game;

import java.sql.Timestamp;

public class TimeEndGameEto extends GameEto{

    private Timestamp endTime;

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
