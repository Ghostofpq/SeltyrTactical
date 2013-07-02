package com.ghostofpq.seltyrtactical.main.graphics;

import com.ghostofpq.seltyrtactical.main.entities.GameCharacter;
import com.ghostofpq.seltyrtactical.main.utils.FontManager;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 29/06/13
 * Time: 10:01
 * To change this template use File | Settings | File Templates.
 */
public class CharacterRender {
    private final int TEXT_PADDING_X = 20;
    private final int BARS_PADDING_X = 60;
    private final int BARS_PADDING_Y = 10;
    private final int BARS_HEIGHT = 20;
    private final int BARS_LENGTH = 150;
    private final int BARS_FRAME_WIDTH = 5;
    private final int BARS_FRAME_LENGTH = BARS_FRAME_WIDTH + BARS_PADDING_X + BARS_LENGTH +TEXT_PADDING_X+  BARS_FRAME_WIDTH;
    private final int BARS_FRAME_HEIGHT = BARS_FRAME_WIDTH + 4 * BARS_PADDING_Y + 3 * BARS_HEIGHT + BARS_FRAME_WIDTH;
    private int posX;
    private int posY;
    private int posXBar1;
    private int posYBar1;
    private int posXBar2;
    private int posYBar2;
    private int posXBar3;
    private int posYBar3;
    private int posXLabel1;
    private int posYLabel1;
    private int posXLabel2;
    private int posYLabel2;
    private int posXLabel3;
    private int posYLabel3;
    private GameCharacter character;
    private BarRender experienceBar;
    private BarRender lifeBar;
    private BarRender manaBar;
    private AngelCodeFont font;


    public CharacterRender(float posX, float posY, GameCharacter character) {
        this.posX = (int) posX;
        this.posY = (int) posY;
        this.character = character;
        this.font = FontManager.getInstance().getFontMap().get("arial_16");

        this.posXBar1 = this.posX + BARS_PADDING_X + BARS_FRAME_WIDTH;
        this.posYBar1 = this.posY + BARS_PADDING_Y + BARS_FRAME_WIDTH;
        this.posXBar2 = this.posXBar1;
        this.posYBar2 = this.posYBar1 + BARS_PADDING_Y + BARS_HEIGHT;
        this.posXBar3 = this.posXBar1;
        this.posYBar3 = this.posYBar2 + BARS_PADDING_Y + BARS_HEIGHT;

        this.posXLabel1 = this.posX + TEXT_PADDING_X + BARS_FRAME_WIDTH;
        this.posYLabel1 = this.posYBar1 + BARS_FRAME_WIDTH;
        this.posXLabel2 = this.posXLabel1;
        this.posYLabel2 = this.posYLabel1 + BARS_PADDING_Y + BARS_HEIGHT;
        this.posXLabel3 = this.posXLabel1;
        this.posYLabel3 = this.posYLabel2 + BARS_PADDING_Y + BARS_HEIGHT;

        this.lifeBar = new BarRender(character.getLifePoint(), 500, this.posXBar1, this.posYBar1, BARS_LENGTH, BARS_HEIGHT, Color.green, Color.red);
        this.manaBar = new BarRender(character.getManaPoint(), 500, this.posXBar2, this.posYBar2, BARS_LENGTH, BARS_HEIGHT, Color.blue, Color.darkGray);
        this.experienceBar = new BarRender(character.getExperience(), character.getNextLevel(), this.posXBar3, this.posYBar3, BARS_LENGTH, BARS_HEIGHT, Color.yellow, Color.gray);
    }

    public void render(Color color) {

        //this.font.drawString(posX, posY, character.getName());

        Toolbox.drawFrame(this.posX, this.posY, BARS_FRAME_LENGTH, BARS_FRAME_HEIGHT, BARS_FRAME_WIDTH, color);
        FontManager.getInstance().drawString("optimus_princeps_16", posXLabel1, posYLabel1, "HP", Color.white);
        FontManager.getInstance().drawString("optimus_princeps_16", posXLabel2, posYLabel2, "MP", Color.white);
        FontManager.getInstance().drawString("optimus_princeps_16", posXLabel3, posYLabel3, "XP", Color.white);

        experienceBar.render();
        lifeBar.render();
        manaBar.render();
    }
}
