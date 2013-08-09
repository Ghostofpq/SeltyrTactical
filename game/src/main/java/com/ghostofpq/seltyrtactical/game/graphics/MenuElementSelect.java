package com.ghostofpq.seltyrtactical.game.graphics;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;

@Getter
public class MenuElementSelect {
    private float posX;
    private float posY;
    private float offset;
    @Setter
    private String text;
    private boolean placed;
    private AngelCodeFont font;

    public MenuElementSelect(float posX, float posY, String text, AngelCodeFont font) {
        this.posX = posX;
        this.posY = posY;
        this.offset = posX + font.getWidth(text);

        this.text = text;
        this.font = font;

        this.placed = false;
    }

    public void render() {
        this.font.drawString(posX - offset, posY, text);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public void render(Color color) {
        this.font.drawString(posX - offset, posY, text, color);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public void addOffset(float delta) {
        this.offset += delta;
        if (offset <= 0) {
            this.placed = true;
        }
    }

    public boolean isOutOfScreenLeft() {
        return ((posX - offset + font.getWidth(text)) <= 0);
    }

    public boolean isOutOfScreenRight(float width) {
        return ((posX - offset) >= width);
    }

}
