package com.ghostofpq.seltyrtactical.main.graphics;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 29/06/13
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
public class Toolbox {

    public static void drawIcon(float X, float Y, float width, float height, Texture icon) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        icon.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(0, 0);
        GL11.glVertex2f(X, Y);
        GL11.glTexCoord2d(1, 0);
        GL11.glVertex2f(X + width, Y);
        GL11.glTexCoord2d(1, 1);
        GL11.glVertex2f(X + width, Y + height);
        GL11.glTexCoord2d(0, 1);
        GL11.glVertex2f(X, Y + height);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
}
