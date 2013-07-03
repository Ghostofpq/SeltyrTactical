package com.ghostofpq.seltyrtactical.main.graphics;

import com.ghostofpq.seltyrtactical.main.entities.GameCharacter;
import com.ghostofpq.seltyrtactical.main.utils.FontManager;
import org.newdawn.slick.Color;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 29/06/13
 * Time: 10:01
 * To change this template use File | Settings | File Templates.
 */
public class CharacterRender {
    private final String FONT = "optimus_princeps_16";
    private final int FRAME_WIDTH = 2;
    private final int FRAME_LENGTH = 300;
    private final int FRAME_HEIGHT = 200;

    private final int TEXT_PADDING_X = 20;

    private final int NAME_FRAME_LENGTH = FRAME_LENGTH;
    private final int NAME_FRAME_HEIGHT = (1 * FRAME_HEIGHT / 5);

    private final int BARS_FRAME_LENGTH = (2 * FRAME_LENGTH / 3) + FRAME_WIDTH;
    private final int BARS_FRAME_HEIGHT = (3 * FRAME_HEIGHT / 5);
    private final int BARS_PADDING_X = 60;
    private final int BARS_PADDING_Y = 10;
    private final int BARS_HEIGHT = 20;
    private final int BARS_LENGTH = 50;

    private final int CHARACS_FRAME_LENGTH = (1 * FRAME_LENGTH / 3);
    private final int CHARACS_FRAME_HEIGHT = BARS_FRAME_HEIGHT;
    // Origin and Name Frame Position
    private int posX;
    private int posY;
    // Name Position
    private int posXName;
    private int posYName;
    // Bars Frame Position
    private int posXBarsFrame;
    private int posYBarsFrame;
    // HP Bar Position
    private int posXBar1;
    private int posYBar1;
    // MP Bar Position
    private int posXBar2;
    private int posYBar2;
    // XP Bar Position
    private int posXBar3;
    private int posYBar3;
    // HP Label Position
    private int posXLabel1;
    private int posYLabel1;
    // MP Label Position
    private int posXLabel2;
    private int posYLabel2;
    // XP Label Position
    private int posXLabel3;
    private int posYLabel3;
    // Characs Frame Position
    private int posXCharacsFrame;
    private int posYCharacsFrame;
    // Characs Strength Label Position
    private int posXCharacsStrengthLabel;
    private int posYCharacsStrengthLabel;
    // Characs Endurance Label Position
    private int posXCharacsEnduranceLabel;
    private int posYCharacsEnduranceLabel;
    // Characs Intelligence Label Position
    private int posXCharacsIntelligenceLabel;
    private int posYCharacsIntelligenceLabel;
    // Characs Will Label Position
    private int posXCharacsWillLabel;
    private int posYCharacsWillLabel;
    // Characs Agility Label Position
    private int posXCharacsAgilityLabel;
    private int posYCharacsAgilityLabel;
    // Characs Move Label Position
    private int posXCharacsMoveLabel;
    private int posYCharacsMoveLabel;

    // Characs Strength Position
    private int posXCharacsStrength;
    private int posYCharacsStrength;
    // Characs Endurance Position
    private int posXCharacsEndurance;
    private int posYCharacsEndurance;
    // Characs Intelligence Position
    private int posXCharacsIntelligence;
    private int posYCharacsIntelligence;
    // Characs Will Position
    private int posXCharacsWill;
    private int posYCharacsWill;
    // Characs Agility Position
    private int posXCharacsAgility;
    private int posYCharacsAgility;
    // Characs Move Position
    private int posXCharacsMove;
    private int posYCharacsMove;

    private GameCharacter character;
    private BarRender experienceBar;
    private BarRender lifeBar;
    private BarRender manaBar;

    public CharacterRender(float posX, float posY, GameCharacter character) {
        this.posX = (int) posX;
        this.posY = (int) posY;
        this.character = character;
        calculatePositions();
    }

    private void calculatePositions() {
        posXName = posX + TEXT_PADDING_X + FRAME_WIDTH;
        posYName = posY + (NAME_FRAME_HEIGHT - FontManager.getInstance().getFontMap().get(FONT).getHeight(character.getName())) / 2;

        posXBarsFrame = posX;
        posYBarsFrame = posY + NAME_FRAME_HEIGHT - FRAME_WIDTH;
        posXBar1 = posXBarsFrame + BARS_PADDING_X + FRAME_WIDTH;
        posYBar1 = posYBarsFrame + BARS_PADDING_Y + FRAME_WIDTH;
        posXBar2 = posXBar1;
        posYBar2 = posYBar1 + BARS_PADDING_Y + BARS_HEIGHT;
        posXBar3 = posXBar1;
        posYBar3 = posYBar2 + BARS_PADDING_Y + BARS_HEIGHT;

        posXLabel1 = posXBarsFrame + TEXT_PADDING_X + FRAME_WIDTH;
        posYLabel1 = posYBar1 + FRAME_WIDTH;
        posXLabel2 = posXLabel1;
        posYLabel2 = posYLabel1 + BARS_PADDING_Y + BARS_HEIGHT;
        posXLabel3 = posXLabel1;
        posYLabel3 = posYLabel2 + BARS_PADDING_Y + BARS_HEIGHT;

        posXCharacsFrame = posX + BARS_FRAME_LENGTH - FRAME_WIDTH;
        posYCharacsFrame = posY + NAME_FRAME_HEIGHT - FRAME_WIDTH;
        // LABELS
        posXCharacsStrengthLabel = posXCharacsFrame + ((CHARACS_FRAME_LENGTH / 6 - FontManager.getInstance().getFontMap().get(FONT).getWidth("S")) / 2);
        posYCharacsStrengthLabel = posYCharacsFrame + ((CHARACS_FRAME_HEIGHT / 3 - FontManager.getInstance().getFontMap().get(FONT).getHeight("S")) / 2);

        posXCharacsEnduranceLabel = posXCharacsFrame + (CHARACS_FRAME_LENGTH / 2) + ((CHARACS_FRAME_LENGTH / 6 - FontManager.getInstance().getFontMap().get(FONT).getWidth("E")) / 2);
        posYCharacsEnduranceLabel = posYCharacsFrame + ((CHARACS_FRAME_HEIGHT / 3 - FontManager.getInstance().getFontMap().get(FONT).getHeight("E")) / 2);

        posXCharacsIntelligenceLabel = posXCharacsFrame + ((CHARACS_FRAME_LENGTH / 6 - FontManager.getInstance().getFontMap().get(FONT).getWidth("I")) / 2);
        posYCharacsIntelligenceLabel = posYCharacsFrame + (CHARACS_FRAME_HEIGHT / 3) + (((CHARACS_FRAME_HEIGHT) / 3 - FontManager.getInstance().getFontMap().get(FONT).getHeight("I")) / 2);

        posXCharacsWillLabel = posXCharacsFrame + (CHARACS_FRAME_LENGTH / 2) + ((CHARACS_FRAME_LENGTH / 6 - FontManager.getInstance().getFontMap().get(FONT).getWidth("W")) / 2);
        posYCharacsWillLabel = posYCharacsFrame + (CHARACS_FRAME_HEIGHT / 3) + (((CHARACS_FRAME_HEIGHT) / 3 - FontManager.getInstance().getFontMap().get(FONT).getHeight("W")) / 2);

        posXCharacsAgilityLabel = posXCharacsFrame + ((CHARACS_FRAME_LENGTH / 6 - FontManager.getInstance().getFontMap().get(FONT).getWidth("A")) / 2);
        posYCharacsAgilityLabel = posYCharacsFrame + ((2 * CHARACS_FRAME_HEIGHT) / 3) + (((CHARACS_FRAME_HEIGHT) / 3 - FontManager.getInstance().getFontMap().get(FONT).getWidth("A")) / 2);

        posXCharacsMoveLabel = posXCharacsFrame + (CHARACS_FRAME_LENGTH / 2) + ((CHARACS_FRAME_LENGTH / 6 - FontManager.getInstance().getFontMap().get(FONT).getWidth("M")) / 2);
        posYCharacsMoveLabel = posYCharacsFrame + ((2 * CHARACS_FRAME_HEIGHT) / 3) + (((CHARACS_FRAME_HEIGHT) / 3 - FontManager.getInstance().getFontMap().get(FONT).getHeight("M")) / 2);
        // VALUES
        posXCharacsStrength = posXCharacsFrame + (CHARACS_FRAME_LENGTH / 6)
                + ((CHARACS_FRAME_LENGTH / 3 - FontManager.getInstance().getFontMap().get(FONT).getWidth(String.valueOf(character.getCharacteristics().getStrength()))) / 2);
        posYCharacsStrength = posYCharacsFrame
                + ((CHARACS_FRAME_HEIGHT / 3 - FontManager.getInstance().getFontMap().get(FONT).getHeight(String.valueOf(character.getCharacteristics().getStrength()))) / 2);

        posXCharacsEndurance = posXCharacsFrame + (CHARACS_FRAME_LENGTH / 2) + (CHARACS_FRAME_LENGTH / 6)
                + ((CHARACS_FRAME_LENGTH / 3 - FontManager.getInstance().getFontMap().get(FONT).getWidth(String.valueOf(character.getCharacteristics().getEndurance()))) / 2);
        posYCharacsEndurance = posYCharacsFrame
                + ((CHARACS_FRAME_HEIGHT / 3 - FontManager.getInstance().getFontMap().get(FONT).getHeight(String.valueOf(character.getCharacteristics().getEndurance()))) / 2);

        posXCharacsIntelligence = posXCharacsFrame + (CHARACS_FRAME_LENGTH / 6)
                + ((CHARACS_FRAME_LENGTH / 3 - FontManager.getInstance().getFontMap().get(FONT).getWidth(String.valueOf(character.getCharacteristics().getIntelligence()))) / 2);
        posYCharacsIntelligence = posYCharacsFrame + (CHARACS_FRAME_HEIGHT / 3)
                + (((CHARACS_FRAME_HEIGHT) / 3 - FontManager.getInstance().getFontMap().get(FONT).getHeight(String.valueOf(character.getCharacteristics().getIntelligence()))) / 2);

        posXCharacsWill = posXCharacsFrame + (CHARACS_FRAME_LENGTH / 2) + (CHARACS_FRAME_LENGTH / 6)
                + ((CHARACS_FRAME_LENGTH / 3 - FontManager.getInstance().getFontMap().get(FONT).getWidth(String.valueOf(character.getCharacteristics().getWill()))) / 2);
        posYCharacsWill = posYCharacsFrame + (CHARACS_FRAME_HEIGHT / 3)
                + (((CHARACS_FRAME_HEIGHT) / 3 - FontManager.getInstance().getFontMap().get(FONT).getHeight(String.valueOf(character.getCharacteristics().getWill()))) / 2);

        posXCharacsAgility = posXCharacsFrame + (CHARACS_FRAME_LENGTH / 6)
                + ((CHARACS_FRAME_LENGTH / 3 - FontManager.getInstance().getFontMap().get(FONT).getWidth(String.valueOf(character.getCharacteristics().getAgility()))) / 2);
        posYCharacsAgility = posYCharacsFrame + ((2 * CHARACS_FRAME_HEIGHT) / 3)
                + (((CHARACS_FRAME_HEIGHT) / 3 - FontManager.getInstance().getFontMap().get(FONT).getHeight(String.valueOf(character.getCharacteristics().getAgility()))) / 2);

        posXCharacsMove = posXCharacsFrame + (CHARACS_FRAME_LENGTH / 2) + (CHARACS_FRAME_LENGTH / 6)
                + ((CHARACS_FRAME_LENGTH / 3 - FontManager.getInstance().getFontMap().get(FONT).getWidth(String.valueOf(character.getCharacteristics().getMovement()))) / 2);
        posYCharacsMove = posYCharacsFrame + ((2 * CHARACS_FRAME_HEIGHT) / 3)
                + (((CHARACS_FRAME_HEIGHT) / 3 - FontManager.getInstance().getFontMap().get(FONT).getHeight(String.valueOf(character.getCharacteristics().getMovement()))) / 2);


        lifeBar = new BarRender(character.getCurrentHealthPoint(), character.getMaxHealthPoint(), posXBar1, posYBar1, BARS_LENGTH, BARS_HEIGHT, Color.green, Color.red);
        manaBar = new BarRender(character.getCurrentManaPoint(), character.getMaxManaPoint(), posXBar2, posYBar2, BARS_LENGTH, BARS_HEIGHT, Color.blue, Color.darkGray);
        experienceBar = new BarRender(character.getExperience(), character.getNextLevel(), posXBar3, posYBar3, BARS_LENGTH, BARS_HEIGHT, Color.yellow, Color.gray);
    }

    public void render(Color color) {

        Toolbox.drawFrame(posX, posY, NAME_FRAME_LENGTH, NAME_FRAME_HEIGHT, FRAME_WIDTH, color);
        FontManager.getInstance().drawString(FONT, posXName, posYName, character.getName(), Color.white);

        Toolbox.drawFrame(posXBarsFrame, posYBarsFrame, BARS_FRAME_LENGTH, BARS_FRAME_HEIGHT, FRAME_WIDTH, color);
        FontManager.getInstance().drawString(FONT, posXLabel1, posYLabel1, "HP", Color.white);
        FontManager.getInstance().drawString(FONT, posXLabel2, posYLabel2, "MP", Color.white);
        FontManager.getInstance().drawString(FONT, posXLabel3, posYLabel3, "XP", Color.white);

        Toolbox.drawFrame(posXCharacsFrame, posYCharacsFrame, CHARACS_FRAME_LENGTH, CHARACS_FRAME_HEIGHT, FRAME_WIDTH, color);
        FontManager.getInstance().drawString(FONT, posXCharacsStrengthLabel, posYCharacsStrengthLabel, "S", Color.white);
        FontManager.getInstance().drawString(FONT, posXCharacsEnduranceLabel, posYCharacsEnduranceLabel, "E", Color.white);
        FontManager.getInstance().drawString(FONT, posXCharacsIntelligenceLabel, posYCharacsIntelligenceLabel, "I", Color.white);
        FontManager.getInstance().drawString(FONT, posXCharacsWillLabel, posYCharacsWillLabel, "W", Color.white);
        FontManager.getInstance().drawString(FONT, posXCharacsAgilityLabel, posYCharacsAgilityLabel, "A", Color.white);
        FontManager.getInstance().drawString(FONT, posXCharacsMoveLabel, posYCharacsMoveLabel, "M", Color.white);

        FontManager.getInstance().drawString(FONT, posXCharacsStrength, posYCharacsStrength, String.valueOf(character.getCharacteristics().getStrength()), Color.white);
        FontManager.getInstance().drawString(FONT, posXCharacsEndurance, posYCharacsEndurance, String.valueOf(character.getCharacteristics().getEndurance()), Color.white);
        FontManager.getInstance().drawString(FONT, posXCharacsIntelligence, posYCharacsIntelligence, String.valueOf(character.getCharacteristics().getIntelligence()), Color.white);
        FontManager.getInstance().drawString(FONT, posXCharacsWill, posYCharacsWill, String.valueOf(character.getCharacteristics().getWill()), Color.white);
        FontManager.getInstance().drawString(FONT, posXCharacsAgility, posYCharacsAgility, String.valueOf(character.getCharacteristics().getIntelligence()), Color.white);
        FontManager.getInstance().drawString(FONT, posXCharacsMove, posYCharacsMove, String.valueOf(character.getCharacteristics().getMovement()), Color.white);

        experienceBar.render();
        lifeBar.render();
        manaBar.render();
    }
}
