package com.ghostofpq.seltyrtactical.main.entities;

import com.ghostofpq.seltyrtactical.main.entities.characteristics.PrimaryCharacteristics;
import com.ghostofpq.seltyrtactical.main.entities.characteristics.SecondaryCharacteristics;
import com.ghostofpq.seltyrtactical.main.entities.job.Job;
import com.ghostofpq.seltyrtactical.main.entities.job.Warrior;
import com.ghostofpq.seltyrtactical.main.entities.race.Race;
import com.ghostofpq.seltyrtactical.main.entities.race.RaceType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: VMPX4526
 * Date: 14/06/13
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */

@Getter
@Setter
public class GameCharacter implements Serializable{
    private static final long serialVersionUID = 1519266158170332774L;


    /**
     * Name
     */
    private String name;
    /**
     * {@link RaceType}
     */
    private Race race;
    /**
     * {@link Gender}
     */
    private Gender gender;
    /**
     * Background story of the character
     */
    private String story;

    // Evolution
    private final int DEFAULT_START_LEVEL = 1;
    private final int DEFAULT_START_XP = 0;
    private final int DEFAULT_START_NEXT_LEVEL = 250;
    /**
     * Level of the character
     */
    private int level;
    /**
     * Current experience of the character
     */
    private int experience;
    /**
     * Experience goal for the next level
     */
    private int nextLevel;

    // Learnings
    /**
     * Current {@link Job} of the character
     */
    private Job currentJob;
    /**
     * {@link Warrior} path of the character
     */
    private Warrior jobWarrior;

    // Caracteristics
    /**
     * {@link PrimaryCharacteristics} of the character acquired by leveling
     */
    private PrimaryCharacteristics characteristics;
    /**
     * {@link SecondaryCharacteristics} of the character acquired by calculation
     * from leveling
     */
    private SecondaryCharacteristics secondaryCharacteristics;

    /**
     * Aggregated {@link PrimaryCharacteristics} of the character (with job and
     * equipement)
     */
    private PrimaryCharacteristics aggregatedCharacteristics;
    /**
     * Aggregated {@link SecondaryCharacteristics} of the character (with job
     * and equipement)
     */
    private SecondaryCharacteristics aggregatedSecondaryCharacteristics;

    /**
     * Life point of the character
     */
    private int lifePoint;
    /**
     * Mana point of the character
     */
    private int manaPoint;

    /**
     * Creates a new Character level 1 Warrior.
     *
     * @param name
     *            name of the character
     * @param race
     *            {@link RaceType} of the character
     * @param gender
     *            {@link Gender} of the character
     */
    public GameCharacter(String name, RaceType race, Gender gender) {
        // Identity
        this.name = name;
        this.race = Race.Race(race);
        this.gender = gender;

        // XP
        this.level = DEFAULT_START_LEVEL;
        this.experience = DEFAULT_START_XP;
        this.nextLevel = DEFAULT_START_NEXT_LEVEL;

        // Jobs
        this.jobWarrior = new Warrior();
        this.currentJob = this.jobWarrior;

        // Caracteristics
        this.characteristics = this.getRace().getBaseCaracteristics();
        this.aggregatedCharacteristics = new PrimaryCharacteristics(0, 0, 0, 0,
                0, 0);

        updateLifeAndManaPoint();
    }

    public void gainXp(double experience) {
        this.experience += experience;
    }

    public void gainJobpoints(int jobPoints) {
        this.currentJob.gainJobPoints(jobPoints);
    }

    public boolean canLevelUp() {
        return (experience >= nextLevel);
    }

    public void levelUp() {
        this.level++;
        this.calculateNextLevel();
        this.characteristics.plus(this.getRace().getLevelUpCaracteristics());

        updateLifeAndManaPoint();
    }

    private void calculateNextLevel() {
        double coef = (1 / (Math.sqrt(this.level)));
        this.nextLevel = (int) Math.floor(coef * this.nextLevel);
    }

    private void updateLifeAndManaPoint() {
        calculateAggregatedCaracteristics();
        this.lifePoint = this.aggregatedCharacteristics.getEndurance() * 10;
        this.manaPoint = this.aggregatedCharacteristics.getIntelligence() * 10;
    }

    private PrimaryCharacteristics getBonusFromJobs() {
        PrimaryCharacteristics result = new PrimaryCharacteristics(0, 0, 0, 0,
                0, 0);
        result.plus(this.jobWarrior.getAggregatedCaracteristics());
        return result;
    }

    private PrimaryCharacteristics getBonusFromEquipement() {
        PrimaryCharacteristics result = new PrimaryCharacteristics(0, 0, 0, 0,
                0, 0);

        return result;
    }

    private void calculateAggregatedCaracteristics() {
        this.aggregatedCharacteristics = this.characteristics;
        this.aggregatedCharacteristics.plus(getBonusFromJobs());
        this.aggregatedCharacteristics.plus(getBonusFromEquipement());
    }

}
