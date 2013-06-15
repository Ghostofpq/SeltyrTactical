package com.ghostofpq.seltyrtactical.main.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 15/06/13
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
public class GraphicsManager {
    private static volatile GraphicsManager instance = null;
    boolean requestClose;
    List<Cube> todraw;
    private int height = 600;
    private int width = 800;

    private GraphicsManager() {
        this.requestClose = false;
        try {
            Display.setDisplayMode(new DisplayMode(this.width, this.height));
            Display.setSwapInterval(1);
            Display.sync(60);
            Display.create();
            todraw = new ArrayList<Cube>();

        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static GraphicsManager getInstance() {
        if (instance == null) {
            synchronized (GraphicsManager.class) {
                if (instance == null) {
                    instance = new GraphicsManager();
                }
            }
        }
        return instance;
    }

    public static void main(String[] argv) {
        GraphicsManager display = GraphicsManager.getInstance();

        try {
            Texture earthTop = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("ressources/Earth.png"));
            Texture earthSide = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("ressources/Earth.png"));
            Cube cubeTest1 = new Cube(new Position(0, 0, 0), earthTop, earthSide, 0.2f);
            Cube cubeTest2 = new Cube(new Position(1, 0, 0), earthTop, earthSide, 0.2f);
            Cube cubeTest3 = new Cube(new Position(0, 1, 0), earthTop, earthSide, 0.2f);
            Cube cubeTest4 = new Cube(new Position(0, 0, 1), earthTop, earthSide, 0.2f);

            display.addToDrawingList(cubeTest1);
            display.addToDrawingList(cubeTest2);
            display.addToDrawingList(cubeTest3);
            display.addToDrawingList(cubeTest4);
        } catch (IOException e) {
            e.printStackTrace();
        }


        display.Init();
        display.run();
    }

    public void addToDrawingList(Cube cube) {
        this.todraw.add(cube);
        Collections.sort(todraw);
    }

    public void Init() {
        GL11.glViewport(0, 0, this.width, this.height);
        GL11.glDepthRange(0, 1000);
        SetupLigths();
        Ready3D();
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
        if (Display.isVisible()) {
            Render3D();
            Render2D();
        }
        Display.update();
        Display.sync(60);
    }

    private void SetupLigths() {
        FloatBuffer matSpecular;
        FloatBuffer lightPosition;
        FloatBuffer whiteLight;
        FloatBuffer lModelAmbient;

        matSpecular = BufferUtils.createFloatBuffer(4);
        matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.5f).put(0.5f).put(5.0f).put(0.2f).flip();

        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();

        lModelAmbient = BufferUtils.createFloatBuffer(4);
        lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();

        GL11.glShadeModel(GL11.GL_SMOOTH);

        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, whiteLight);
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, whiteLight);
        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, lModelAmbient);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);

    }

    private void UpdateLights() {
        FloatBuffer lightPosition;
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.5f).put(0.5f).put(5.0f).put(0.2f).flip();
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);
    }

    private void Render3D() {
        // Ready3D();
        make3D();
        for (Cube cube : todraw) {
            cube.Draw(PointOfView.SOUTH);
        }
    }

    private void Render2D() {
        // Ready2D();
        make2D();
        // hud.Render();
    }

    private void make2D() {
        // Remove the Z axis
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

    private void make3D() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    private void Ready3D() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        GL11.glOrtho(-1, 1, -1, 1, -10, 1000);

        GL11.glRotatef((float) Math.toDegrees(Math.atan(0.5)), 1, 0, 0);
        GL11.glRotatef(-45.0f, 0, 1, 0);
        GL11.glTranslatef(0, -0.5f, 0);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }
}
