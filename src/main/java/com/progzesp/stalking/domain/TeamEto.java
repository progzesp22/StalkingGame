package com.progzesp.stalking.domain;

import java.util.List;

public class TeamEto extends AbstractEto {

    private String name;

    private List<String> members;

    private Long gameId;

    private String creator;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    private int score;

    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public void setScore(int newScore){
        this.score = newScore;
    }

    public int getScore(){
        return this.score;
    }
}
