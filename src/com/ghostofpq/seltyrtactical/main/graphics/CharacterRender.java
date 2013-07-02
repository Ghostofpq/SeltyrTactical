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
    private final int TEXT_PADDING_X = 20;
    private final int BARS_PADDING_X = 60;
    private final int BARS_PADDING_Y = 10;
    private final int BARS_HEIGHT = 20;
    private final int BARS_LENGTH = 150;
    private final int FRAME_WIDTH = 2;
    private final int BARS_FRAME_LENGTH = FRAME_WIDTH + BARS_PADDING_X + BARS_LENGTH + TEXT_PADDING_X + FRAME_WIDTH;
    private final int BARS_FRAME_HEIGHT = FRAME_WIDTH + 4 * BARS_PADDING_Y + 3 * BARS_HEIGHT + FRAME_WIDTH;
    private final int CHARACS_FRAME_LENGTH = 150;
    private final int CHARACS_FRAME_HEIGHT = BARS_FRAME_HEIGHT;
    private final int NAME_FRAME_LENGTH = BARS_FRAME_LENGTH + CHARACS_FRAME_LENGTH - FRAME_WIDTH;
    private final int NAME_FRAME_HEIGHT = (BARS_PADDING_Y * 2) + (FRAME_WIDTH * 2) + 10;
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
    private GameCharacter character;
    private BarRender experienceBar;
    private BarRender lifeBar;
    private BarRender manaBar;


    public CharacterRender(float posX, float posY, GameCharacter character) {
        this.posX = (int) posX;
        this.posY = (int) posY;
        this.character = character;

        this.posXName = this.posX + TEXT_PADDING_X + FRAME_WIDTH;
        this.posYName = this.posY + (NAME_FRAME_HEIGHT - FontManager.getInstance().getFontMap().get(FONT).getHeight(character.getName())) / 2;

        this.posXBarsFrame = this.posX;
        this.posYBarsFrame = this.posY + NAME_FRAME_HEIGHT - FRAME_WIDTH;
        this.posXBar1 = this.posXBarsFrame + BARS_PADDING_X + FRAME_WIDTH;
        this.posYBar1 = this.posYBarsFrame + BARS_PADDING_Y + FRAME_WIDTH;
        this.posXBar2 = this.posXBar1;
        this.posYBar2 = this.posYBar1 + BARS_PADDING_Y + BARS_HEIGHT;
        this.posXBar3 = this.posXBar1;
        this.posYBar3 = this.posYBar2 + BARS_PADDING_Y + BARS_HEIGHT;

        this.posXLabel1 = this.posXBarsFrame + TEXT_PADDING_X + FRAME_WIDTH;
        this.posYLabel1 = this.posYBar1 + FRAME_WIDTH;
        this.posXLabel2 = this.posXLabel1;
        this.posYLabel2 = this.posYLabel1 + BARS_PADDING_Y + BARS_HEIGHT;
        this.posXLabel3 = this.posXLabel1;
        this.posYLabel3 = this.posYLabel2 + BARS_PADDING_Y + BARS_HEIGHT;

        this.posXCharacsFrame = this.posX + BARS_FRAME_LENGTH - FRAME_WIDTH;
        this.posYCharacsFrame = this.posY + NAME_FRAME_HEIGHT - FRAME_WIDTH;

        this.lifeBar = new BarRender(character.getLifePoint(), 500, this.posXBar1, this.posYBar1, BARS_LENGTH, BARS_HEIGHT, Color.green, Color.red);
        this.manaBar = new BarRender(character.getManaPoint(), 500, this.posXBar2, this.posYBar2, BARS_LENGTH, BARS_HEIGHT, Color.blue, Color.darkGray);
        this.experienceBar = new BarRender(character.getExperience(), character.getNextLevel(), this.posXBar3, this.posYBar3, BARS_LENGTH, BARS_HEIGHT, Color.yellow, Color.gray);
    }

    public void render(Color color) {

        //this.font.drawString(posX, posY, character.getName());

        Toolbox.drawFrame(this.posX, this.posY, NAME_FRAME_LENGTH, NAME_FRAME_HEIGHT, FRAME_WIDTH, color);
        FontManager.getInstance().drawString(FONT, posXName, posYName, character.getName(), Color.white);

        Toolbox.drawFrame(this.posXBarsFrame, this.posYBarsFrame, BARS_FRAME_LENGTH, BARS_FRAME_HEIGHT, FRAME_WIDTH, color);
        FontManager.getInstance().drawString(FONT, posXLabel1, posYLabel1, "HP", Color.white);
        FontManager.getInstance().drawString(FONT, posXLabel2, posYLabel2, "MP", Color.white);
        FontManager.getInstance().drawString(FONT, posXLabel3, posYLabel3, "XP", Color.white);

        Toolbox.drawFrame(this.posXCharacsFrame, this.posYCharacsFrame, CHARACS_FRAME_LENGTH, CHARACS_FRAME_HEIGHT, FRAME_WIDTH, color);

        experienceBar.render();
        lifeBar.render();
        manaBar.render();
    }
}
