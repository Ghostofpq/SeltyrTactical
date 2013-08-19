package com.ghostofpq.seltyrtactical.entities.character;


import com.ghostofpq.seltyrtactical.entities.characteristics.PrimaryCharacteristics;
import com.ghostofpq.seltyrtactical.entities.characteristics.SecondaryCharacteristics;
import com.ghostofpq.seltyrtactical.entities.job.Job;
import com.ghostofpq.seltyrtactical.entities.job.Warrior;
import com.ghostofpq.seltyrtactical.entities.race.Race;
import com.ghostofpq.seltyrtactical.entities.race.RaceType;

import java.io.Serializable;

public class GameCharacter implements Serializable {
    private static final long serialVersionUID = 1519266158170332774L;
    // Evolution
    private final int DEFAULT_START_LEVEL = 1;
    private final int DEFAULT_START_XP = 0;
    private final int DEFAULT_START_NEXT_LEVEL = 250;
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
     * Health point of the character
     */
    private int currentHealthPoint;
    private int maxHealthPoint;
    /**
     * Mana point of the character
     */
    private int currentManaPoint;
    private int maxManaPoint;

    /**
     * Creates a new Character level 1 Warrior.
     *
     * @param name   name of the character
     * @param race   {@link RaceType} of the character
     * @param gender {@link Gender} of the character
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
        currentHealthPoint = maxHealthPoint;
        currentManaPoint = maxManaPoint;
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
        this.maxHealthPoint = this.aggregatedCharacteristics.getEndurance() * 10;
        this.maxManaPoint = this.aggregatedCharacteristics.getIntelligence() * 10;
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

    /**
     * Getters and Setters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(int nextLevel) {
        this.nextLevel = nextLevel;
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }

    public Warrior getJobWarrior() {
        return jobWarrior;
    }

    public void setJobWarrior(Warrior jobWarrior) {
        this.jobWarrior = jobWarrior;
    }

    public PrimaryCharacteristics getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(PrimaryCharacteristics characteristics) {
        this.characteristics = characteristics;
    }

    public SecondaryCharacteristics getSecondaryCharacteristics() {
        return secondaryCharacteristics;
    }

    public void setSecondaryCharacteristics(SecondaryCharacteristics secondaryCharacteristics) {
        this.secondaryCharacteristics = secondaryCharacteristics;
    }

    public PrimaryCharacteristics getAggregatedCharacteristics() {
        return aggregatedCharacteristics;
    }

    public void setAggregatedCharacteristics(PrimaryCharacteristics aggregatedCharacteristics) {
        this.aggregatedCharacteristics = aggregatedCharacteristics;
    }

    public SecondaryCharacteristics getAggregatedSecondaryCharacteristics() {
        return aggregatedSecondaryCharacteristics;
    }

    public void setAggregatedSecondaryCharacteristics(SecondaryCharacteristics aggregatedSecondaryCharacteristics) {
        this.aggregatedSecondaryCharacteristics = aggregatedSecondaryCharacteristics;
    }

    public int getCurrentHealthPoint() {
        return currentHealthPoint;
    }

    public void setCurrentHealthPoint(int currentHealthPoint) {
        this.currentHealthPoint = currentHealthPoint;
    }

    public int getMaxHealthPoint() {
        return maxHealthPoint;
    }

    public void setMaxHealthPoint(int maxHealthPoint) {
        this.maxHealthPoint = maxHealthPoint;
    }

    public int getCurrentManaPoint() {
        return currentManaPoint;
    }

    public void setCurrentManaPoint(int currentManaPoint) {
        this.currentManaPoint = currentManaPoint;
    }

    public int getMaxManaPoint() {
        return maxManaPoint;
    }

    public void setMaxManaPoint(int maxManaPoint) {
        this.maxManaPoint = maxManaPoint;
    }
}
