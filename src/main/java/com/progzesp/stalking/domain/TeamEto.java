package com.progzesp.stalking.domain;

import java.util.List;

public class TeamEto extends AbstractEto{

    private String name;

    private List<Long> members;

    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getMembers() {
        return members;
    }

    public void setMembers(List<Long> members) {
        this.members = members;
    }

    public void setScore(int newScore){
        this.score = newScore;
    }

    public int getScore(){
        return this.score;
    }
}
