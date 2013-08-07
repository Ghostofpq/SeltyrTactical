package com.ghostofpq.seltyrtactical.main.graphics;

import lombok.Getter;
import lombok.Setter;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * Created with IntelliJ IDEA.
 * User: VMPX4526
 * Date: 28/06/13
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
@Getter
public class TextField {
    private float posX;
    private float posY;
    @Setter
    private String text;
    private boolean placed;
    private UnicodeFont font;

    public TextField(float posX, float posY, String text) {

        try {
            font = new UnicodeFont("resources/font/old_london/OldLondon.ttf", 24, false, false);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect());
            font.loadGlyphs();

        } catch (SlickException e) {
            e.printStackTrace();
        }

        this.posX = posX;
        this.posY = posY;

        this.text = text;
        this.font = font;

        this.placed = false;
    }

    public void render() {
        this.font.drawString(posX, posY, text);
    }

    public void render(Color color) {
        this.font.drawString(posX, posY, text, color);
    }
}
