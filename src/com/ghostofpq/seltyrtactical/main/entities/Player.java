package com.ghostofpq.seltyrtactical.main.entities;

import com.ghostofpq.seltyrtactical.main.entities.race.RaceType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ghostofpq
 * Date: 12/06/13
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class Player implements Serializable {
    private static final long serialVersionUID = 3105715593359746378L;
    private String pseudo;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private int grade;
    private Team team;

    public Player(String pseudo) {
        this.pseudo = pseudo;

        StringBuilder teamNameBuilder = new StringBuilder();
        teamNameBuilder.append(pseudo);
        teamNameBuilder.append("'s Team");
        team = new Team(teamNameBuilder.toString());

        GameCharacter mainChar = new GameCharacter("Jacky la fripouille", RaceType.HUMAN, Gender.MALE);
        team.getTeam().add(mainChar);
    }


}
