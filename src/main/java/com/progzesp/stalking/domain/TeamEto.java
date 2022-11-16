package com.progzesp.stalking.domain;

import java.util.List;

public class TeamEto extends AbstractEto{

    private String name;

    private List<Long> members;

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
}
