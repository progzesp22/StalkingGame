package com.progzesp.stalking.persistance.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "message")
public class MessageEntity extends AbstractEntity{

    private String content;

    /*@ManyToOne(fetch = FetchType.LAZY)
    private GameEntity game;*/

    public String getContent() {
        return content;
    }
    /*public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }*/
    public void setContent(String content) {
        this.content = content;
    }
}
