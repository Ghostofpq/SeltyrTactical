package com.ghostofpq.seltyrtactical.main;

import com.ghostofpq.seltyrtactical.main.entities.GameCharacter;
import com.ghostofpq.seltyrtactical.main.entities.Gender;
import com.ghostofpq.seltyrtactical.main.entities.Player;
import com.ghostofpq.seltyrtactical.main.entities.race.RaceType;
import com.ghostofpq.seltyrtactical.main.utils.SaveManager;

/**
 * Created with IntelliJ IDEA.
 * User: VMPX4526
 * Date: 07/08/13
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public class PlayerCreator {
    public static void main(String[] argv) {
        Player player1 = new Player("Bob");

        GameCharacter char1 = new GameCharacter("Bob1Human", RaceType.HUMAN, Gender.MALE);
        GameCharacter char2 = new GameCharacter("Bob2Elve", RaceType.ELVE, Gender.FEMALE);
        GameCharacter char3 = new GameCharacter("Bob3Dwarf", RaceType.DWARF, Gender.MALE);

        player1.getTeam().getTeam().add(char1);
        player1.getTeam().getTeam().add(char2);
        player1.getTeam().getTeam().add(char3);

        SaveManager saveManager = SaveManager.getInstance();
        saveManager.savePlayer(player1);

        Player player2 = new Player("Jack");

        GameCharacter char4 = new GameCharacter("Jack1Human", RaceType.HUMAN, Gender.MALE);
        GameCharacter char5 = new GameCharacter("Jack2Elve", RaceType.ELVE, Gender.FEMALE);
        GameCharacter char6 = new GameCharacter("Jack3Dwarf", RaceType.DWARF, Gender.MALE);

        player2.getTeam().getTeam().add(char4);
        player2.getTeam().getTeam().add(char5);
        player2.getTeam().getTeam().add(char6);


        saveManager.savePlayer(player2);
    }
}
