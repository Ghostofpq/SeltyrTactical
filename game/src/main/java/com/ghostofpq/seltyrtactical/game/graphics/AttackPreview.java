package com.ghostofpq.seltyrtactical.game.graphics;


import com.ghostofpq.seltyrtactical.entities.character.GameCharacter;
import org.newdawn.slick.Color;

public class AttackPreview {

    private final String FONT = "optimus_princeps_16";
    private int posX;
    private int posY;
    private int frameWidth;
    private int frameLength;
    private int frameHeight;
    private int estimatedDamage;
    private int applicableEscapeRate;
    private int applicableCriticalChance;


    public AttackPreview(int posX, int posY, int frameLength, int frameHeight, int frameWidth, GameCharacter attackingChar, GameCharacter targetedChar) {
        this.posX = posX;
        this.posY = posY;

        this.frameLength = frameLength;
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;

        int armor = (targetedChar.getAggregatedSecondaryCharacteristics().getArmor() - attackingChar.getAggregatedSecondaryCharacteristics().getArmorPenetration());
        double ratio = (100 / (100 - armor));

        double estimatedDamageF = attackingChar.getAggregatedSecondaryCharacteristics().getAttackDamage() * ratio;
        double applicableEscapeRateF = targetedChar.getAggregatedSecondaryCharacteristics().getEscape() - attackingChar.getAggregatedSecondaryCharacteristics().getPrecision();
        double applicableCriticalChanceF = attackingChar.getAggregatedSecondaryCharacteristics().getCriticalStrike() - targetedChar.getAggregatedSecondaryCharacteristics().getResilience();

        this.estimatedDamage = (int) Math.floor(estimatedDamageF);
        this.applicableEscapeRate = Math.max(0, (int) Math.floor(applicableEscapeRateF));
        this.applicableCriticalChance = Math.max(0, (int) Math.floor(applicableCriticalChanceF));
    }

    public void render(Color color) {
        Toolbox.drawFrame(posX, posY, frameLength, frameHeight, frameWidth, color);


    }
}
