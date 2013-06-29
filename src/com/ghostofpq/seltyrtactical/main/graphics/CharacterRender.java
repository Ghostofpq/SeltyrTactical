package com.ghostofpq.seltyrtactical.main.graphics;

import com.ghostofpq.seltyrtactical.main.entities.GameCharacter;
import com.ghostofpq.seltyrtactical.main.utils.FontManager;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 29/06/13
 * Time: 10:01
 * To change this template use File | Settings | File Templates.
 */
public class CharacterRender {
    private GameCharacter character;
    float posX;
    float posY;
    private UnicodeFont font;

    public CharacterRender(float posX, float posY, GameCharacter character) {
        this.posX = posX;
        this.posY = posY;
        this.character = character;
        this.font = FontManager.getInstance().getFont();
    }

    public void render(Color color) {

        this.font.drawString(posX, posY, character.getName());


    }
}
