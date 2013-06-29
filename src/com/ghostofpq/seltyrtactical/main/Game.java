package com.ghostofpq.seltyrtactical.main;

import com.ghostofpq.seltyrtactical.main.entities.Player;
import com.ghostofpq.seltyrtactical.main.scenes.MainMenu;
import com.ghostofpq.seltyrtactical.main.scenes.Scene;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * User: GhostOfPQ
 * Date: 11/06/13
 */
@Getter
public class Game {
    private static volatile Game instance = null;
    boolean requestClose;
    private int height;
    private int width;

    @Setter
    private Scene currentScene;
    @Setter
    private Player player;

    private Game() {
        this.height = 600;
        this.width = 800;
        this.requestClose = false;

        init();

        currentScene = MainMenu.getInstance();
    }

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

    public static void main(String[] argv) {
        Game g = Game.getInstance();
        g.run();
    }

    public void init() {
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
    }

    public void run() {
        while (!requestClose) {
            manageInput();
            update();
            render();
        }
        Display.destroy();
    }

    public void manageInput() {
        currentScene.manageInput();
    }

    public void update() {
        requestClose = Display.isCloseRequested();
        currentScene.update();
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        currentScene.render();

        Display.update();
        Display.sync(60);
    }

    public void quit() {
        requestClose = true;
    }

}
