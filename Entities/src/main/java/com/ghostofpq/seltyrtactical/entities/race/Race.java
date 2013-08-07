package com.ghostofpq.seltyrtactical.entities.race;

import com.ghostofpq.seltyrtactical.entities.characteristics.PrimaryCharacteristics;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class Race implements Serializable {

    private static final long serialVersionUID = -3933914420338387526L;

    private RaceType race;
    private PrimaryCharacteristics baseCaracteristics;
    private PrimaryCharacteristics levelUpCaracteristics;

    private String description;
    private String name;

    public static Race Race(RaceType race) {
        switch (race) {
            case ELVE:
                return new Elve();
            case HUMAN:
                return new Human();
            case DWARF:
                return new Dwarf();
            default:
                return null;
        }
    }
}
