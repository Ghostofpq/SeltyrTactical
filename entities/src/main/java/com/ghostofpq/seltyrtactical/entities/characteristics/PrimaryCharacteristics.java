package com.ghostofpq.seltyrtactical.entities.characteristics;

import java.io.Serializable;

public class PrimaryCharacteristics implements Serializable {

    private static final long serialVersionUID = 6681761667504505940L;

    private int strength;
    private int endurance;
    private int intelligence;
    private int will;
    private int agility;
    private int movement;

    public PrimaryCharacteristics() {
        this.setStrength(0);
        this.setEndurance(0);
        this.setIntelligence(0);
        this.setWill(0);
        this.setAgility(0);
        this.setMovement(0);
    }

    public PrimaryCharacteristics(int strength, int endurance,
                                  int intelligence, int will, int agility, int movement) {
        this.setStrength(strength);
        this.setEndurance(endurance);
        this.setIntelligence(intelligence);
        this.setWill(will);
        this.setAgility(agility);
        this.setMovement(movement);
    }

    public void plus(PrimaryCharacteristics characteristics) {
        this.setStrength(this.getStrength() + characteristics.getStrength());
        this.setEndurance(this.getEndurance() + characteristics.getEndurance());
        this.setIntelligence(this.getIntelligence()
                + characteristics.getIntelligence());
        this.setWill(this.getWill() + characteristics.getWill());
        this.setAgility(this.getAgility() + characteristics.getAgility());
        this.setMovement(this.getMovement() + characteristics.getMovement());
    }

    /**
     * Getters and Setters
     */

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWill() {
        return will;
    }

    public void setWill(int will) {
        this.will = will;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }
}
