package com.ghostofpq.seltyrtactical.main;

import com.ghostofpq.seltyrtactical.main.graphics.MenuSelect;
import com.ghostofpq.seltyrtactical.main.utils.Scene;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * User: GhostOfPQ
 * Date: 11/06/13
 */
public class Game {
    private static volatile Game instance = null;

    public static Game getInstance() {
        if (instance == null) {
            synchronized (Game.class) {
                if (instance == null) {
                    instance = new Game();
                }
            }
        }
        return instance;
    }

    boolean requestClose;
    private int height;
    private int width;
    private MenuSelect test;


    private Game() {
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

        List<String> options = new ArrayList<String>();
        options.add("New player");
        options.add("Load");
        options.add("Quit");
        test = new MenuSelect(options, 150, 150, 100, 50, 600, 800);
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
            update();
        }
        Display.destroy();
    }

    public void update() {
        handleInput();
        render();

    }

    public void setScene(Scene scene) {
    }

    public void quit() {
        requestClose = true;
    }

    public void handleInput() {

    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        requestClose = Display.isCloseRequested();

        test.update();
        Display.update();
        Display.sync(60);
    }
}
