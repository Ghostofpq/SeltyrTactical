package com.ghostofpq.seltyrtactical.main.graphics;

import lombok.Getter;
import org.newdawn.slick.UnicodeFont;

/**
 * Created with IntelliJ IDEA.
 * User: VMPX4526
 * Date: 17/06/13
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
@Getter
public class MenuElementSelect {
    private float posX;
    private float posY;
    private float offset;
    private String text;
    private boolean placed;
    private UnicodeFont font;

    public MenuElementSelect(float posX, float posY, String text, UnicodeFont font) {
        this.posX = posX;
        this.posY = posY;
        this.offset = posX + font.getWidth(text);

        this.text = text;
        this.font = font;

        this.placed = false;
    }

    public void render() {
        this.font.drawString(posX - offset, posY, text);
    }

    public void addOffset(float delta) {
        this.offset += delta;
        if (offset <= 0) {
            this.placed = true;
        }
    }

}