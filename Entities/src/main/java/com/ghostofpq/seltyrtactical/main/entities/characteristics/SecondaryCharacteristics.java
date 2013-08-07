package java.com.ghostofpq.seltyrtactical.main.entities.characteristics;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SecondaryCharacteristics implements Serializable {

    private static final long serialVersionUID = -2135901452592205936L;
    private int attackDamage;
    private int magicalDamage;
    private int armor;
    private int magicResist;
    private int armorPenetration;
    private int magicPenetration;
    private int speed;
    private int lifeRegeneration;
    private int manaRegeneration;
    private float escape;
    private float criticalStrike;
    private float precision;
    private float resilience;

    public SecondaryCharacteristics() {
        this.setAttackDamage(0);
        this.setMagicalDamage(0);
        this.setArmor(0);
        this.setMagicResist(0);

        this.setEscape(0);
        this.setCriticalStrike(0);
        this.setPrecision(0);
        this.setResilience(0);

        this.setArmorPenetration(0);
        this.setMagicPenetration(0);
        this.setSpeed(0);
        this.setLifeRegeneration(0);
        this.setManaRegeneration(0);
    }

    public SecondaryCharacteristics(int attackDamage, int magicalDamage,
                                    int armor, int magicResist, int armorPenetration,
                                    int magicPenetration, int speed, int lifeRegeneration,
                                    int manaRegeneration, float escape, float criticalStrike,
                                    float precision, float resilience) {
        super();

        this.setAttackDamage(attackDamage);
        this.setMagicalDamage(magicalDamage);
        this.setArmor(armor);
        this.setMagicResist(magicResist);

        this.setEscape(escape);
        this.setCriticalStrike(criticalStrike);
        this.setPrecision(precision);
        this.setResilience(resilience);

        this.setArmorPenetration(armorPenetration);
        this.setMagicPenetration(magicPenetration);
        this.setSpeed(speed);
        this.setLifeRegeneration(lifeRegeneration);
        this.setManaRegeneration(manaRegeneration);
    }

    public SecondaryCharacteristics(PrimaryCharacteristics caracteristics) {

        this.setAttackDamage(caracteristics.getStrength());
        this.setMagicalDamage(caracteristics.getIntelligence());
        this.setArmor(caracteristics.getEndurance());
        this.setMagicResist(caracteristics.getWill());

        this.setEscape(caracteristics.getAgility() / 1000);
        this.setCriticalStrike(caracteristics.getAgility() / 1000);
        this.setPrecision(Math.max(caracteristics.getEndurance(),
                caracteristics.getWill()) / 1000);
        this.setResilience(Math.max(caracteristics.getStrength(),
                caracteristics.getIntelligence()) / 1000);

        this.setArmorPenetration(0);
        this.setMagicPenetration(0);
        this.setSpeed(0);
        this.setLifeRegeneration(0);
        this.setManaRegeneration(0);
    }

    public void plus(SecondaryCharacteristics secondaryCharacteristics) {
        this.setAttackDamage(this.getAttackDamage()
                + secondaryCharacteristics.getAttackDamage());
        this.setMagicalDamage(this.getMagicalDamage()
                + secondaryCharacteristics.getMagicalDamage());
        this.setArmor(this.getArmor() + secondaryCharacteristics.getArmor());
        this.setMagicResist(this.getMagicResist()
                + secondaryCharacteristics.getMagicResist());

        this.setEscape(this.getEscape() + secondaryCharacteristics.getEscape());
        this.setCriticalStrike(this.getCriticalStrike()
                + secondaryCharacteristics.getCriticalStrike());
        this.setPrecision(this.getPrecision()
                + secondaryCharacteristics.getPrecision());
        this.setResilience(this.getResilience()
                + secondaryCharacteristics.getResilience());

        this.setArmorPenetration(this.getArmorPenetration()
                + secondaryCharacteristics.getArmorPenetration());
        this.setMagicPenetration(this.getMagicPenetration()
                + secondaryCharacteristics.getMagicPenetration());
        this.setSpeed(this.getSpeed() + secondaryCharacteristics.getSpeed());
        this.setLifeRegeneration(this.getLifeRegeneration()
                + secondaryCharacteristics.getLifeRegeneration());
        this.setManaRegeneration(this.getManaRegeneration()
                + secondaryCharacteristics.getManaRegeneration());
    }
}
