package com.ghostofpq.seltyrtactical.main;

import com.ghostofpq.seltyrtactical.main.graphics.MenuElementSelect;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 * User: GhostOfPQ
 * Date: 11/06/13
 */
public class Game {

    boolean requestClose;
    private int height;
    private int width;
    private MenuElementSelect test1;


    public Game() {
        this.height = 600;
        this.width = 800;
        this.requestClose = false;
        UnicodeFont font = null;


        try {
            Display.setDisplayMode(new DisplayMode(this.width, this.height));
            Display.setSwapInterval(1);
            Display.sync(60);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0, this.width, this.height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();

        try {
            font = new UnicodeFont("resources/font/old_london/OldLondon.ttf", 24, false, false);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect());
            font.loadGlyphs();

        } catch (SlickException e) {
            e.printStackTrace();
        }

        test1 = new MenuElementSelect(80, 80, "New player", font);
    }

    public static void main(String[] argv) {
        Game g = new Game();
        g.run();
    }

    public void init() {
        GL11.glViewport(0, 0, this.width, this.height);
        GL11.glDepthRange(0, 1000);
    }

    public void run() {
        while (!requestClose) {
            render();
        }
        Display.destroy();
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        requestClose = Display.isCloseRequested();

        if (!test1.isPlaced()) {
            test1.addOffset(-5f);
        }
        test1.render();

        Display.update();
        Display.sync(60);
    }
}
