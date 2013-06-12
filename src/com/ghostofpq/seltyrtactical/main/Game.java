package com.ghostofpq.seltyrtactical.main;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * User: GhostOfPQ
 * Date: 11/06/13
 */
public class Game {

    private int height;
    private int width;
    boolean requestClose;



    public Game(){
        this.height = 600;
        this.width = 800;
        this.requestClose = false;
        try {
            Display.setDisplayMode(new DisplayMode(this.width, this.height));
            Display.setSwapInterval(1);
            Display.sync(60);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void Init() {
        GL11.glViewport(0, 0, this.width, this.height);
        GL11.glDepthRange(0, 1000);
    }

    public void run() {
        while (!requestClose) {
            Render();
        }
        Display.destroy();
    }

    public void Render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        requestClose = Display.isCloseRequested();

        Display.update();
        Display.sync(60);
    }

    public static void main(String[] argv) {
        Game g = new Game();
          g.run();
    }
}
