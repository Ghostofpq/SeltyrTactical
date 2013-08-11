package com.ghostofpq.seltyrtactical.entities.character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {

    private String name;
    private List<GameCharacter> team;

    public Team(String name) {
        team = new ArrayList<GameCharacter>();
    }


    /**
     * Getters and Setters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GameCharacter> getTeam() {
        return team;
    }

    public void setTeam(List<GameCharacter> team) {
        this.team = team;
    }
}
