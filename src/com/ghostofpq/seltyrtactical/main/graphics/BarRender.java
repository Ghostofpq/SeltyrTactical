package com.ghostofpq.seltyrtactical.main.graphics;

import com.ghostofpq.seltyrtactical.main.utils.FontManager;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

/**
 * Created with IntelliJ IDEA.
 * User: VMPX4526
 * Date: 01/07/13
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */
public class BarRender {
    Color colorBack;
    Color color;
    float posX1;
    float posY1;
    float posX2;
    float posY2;
    float posX3;
    float posY3;
    float posX4;
    float posY4;
    String text;

    public BarRender(float value, float maxValue, float posX, float posY, float width, float height, Color color, Color colorBack) {
        this.posX1 = posX;
        this.posY1 = posY;
        this.posX2 = posX + width;
        this.posY2 = posY + height;
        this.posX3 = posX + (width * (value / maxValue));
        this.posY3 = posY + height;

        StringBuilder builder = new StringBuilder();
        builder.append((int) value);
        builder.append(" / ");
        builder.append((int) maxValue);
        text = builder.toString();

        AngelCodeFont font = FontManager.getInstance().getFontMap().get("optimus_princeps_16");

        this.posX4 = posX + ((width - font.getWidth(text)) / 2);
        this.posY4 = posY + ((height - font.getHeight(text)) / 2);
        this.color = color;
        this.colorBack = colorBack;
    }

    public void render() {
        /**GL11.glColor4f(colorBack.getRed(), colorBack.getGreen(), colorBack.getBlue(), 1f);

         GL11.glBegin(GL11.GL_QUADS);
         GL11.glVertex2f(posX1, posY1);
         GL11.glVertex2f(posX2, posY1);
         GL11.glVertex2f(posX2, posY2);
         GL11.glVertex2f(posX1, posY2);
         GL11.glEnd();

         GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), 1f);
         GL11.glBegin(GL11.GL_QUADS);
         GL11.glVertex2f(posX1, posY1);
         GL11.glVertex2f(posX3, posY1);
         GL11.glVertex2f(posX3, posY3);
         GL11.glVertex2f(posX1, posY3);
         GL11.glEnd();   */

        FontManager.getInstance().drawString("arial_16", (int) posX4, (int) posY4, "blablablabla", Color.white);
    }
}
