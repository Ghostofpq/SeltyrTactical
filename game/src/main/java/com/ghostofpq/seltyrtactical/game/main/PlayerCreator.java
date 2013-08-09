package com.ghostofpq.seltyrtactical.game.main;

import com.ghostofpq.seltyrtactical.entities.character.GameCharacter;
import com.ghostofpq.seltyrtactical.entities.character.Gender;
import com.ghostofpq.seltyrtactical.entities.character.Player;
import com.ghostofpq.seltyrtactical.entities.race.RaceType;
import com.ghostofpq.seltyrtactical.game.utils.SaveManager;

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
