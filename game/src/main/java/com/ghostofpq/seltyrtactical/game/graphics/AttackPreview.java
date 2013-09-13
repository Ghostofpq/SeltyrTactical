package com.ghostofpq.seltyrtactical.game.graphics;


import com.ghostofpq.seltyrtactical.entities.character.GameCharacter;
import com.ghostofpq.seltyrtactical.game.utils.FontManager;
import org.newdawn.slick.Color;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        BigDecimal applicableEscapeRateF = targetedChar.getEscape().min(attackingChar.getPrecision());
        applicableEscapeRateF.setScale(2, RoundingMode.DOWN);
        BigDecimal applicableCriticalChanceF = attackingChar.getCriticalStrike().min(targetedChar.getResilience());
        applicableCriticalChanceF.setScale(2, RoundingMode.DOWN);

        estimatedDamage = (int) Math.floor(estimatedDamageF);
    }

    public void render(Color color) {
        Toolbox.drawFrame(posX, posY, frameLength, frameHeight, frameWidth, color);
        StringBuilder damage = new StringBuilder();
        damage.append(estimatedDamage);
        damage.append("Dmg");
        int posXDamage = posX + (frameLength - FontManager.getInstance().getFontMap().get(FONT).getWidth(damage.toString())) / 2;
        int posYDamage = posY + (frameHeight - FontManager.getInstance().getFontMap().get(FONT).getHeight(damage.toString())) / 2;

        FontManager.getInstance().drawString(FONT, posXDamage, posYDamage, damage.toString(), Color.white);
    }
}
