package com.ghostofpq.seltyrtactical.main.utils;

import com.ghostofpq.seltyrtactical.main.Game;
import com.ghostofpq.seltyrtactical.main.graphics.PointOfView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 15/06/13
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class GraphicsManager {
    private static volatile GraphicsManager instance = null;
    @Getter
    private float originX;
    @Getter
    private float originY;
    @Getter
    private float originZ;
    @Getter
    private PointOfView currentPointOfView;
    @Getter
    private float scale;
    private float zscale;
    private float scaleToGo;
    private float focusXToGo;
    private float focusYToGo;
    private float focusZToGo;
    private float rotationToGo;

    private GraphicsManager() {
        originZ = 0;
        originX = 0;
        originY = -2;
        scaleToGo = 0;
        rotationToGo = 0f;
        scale = 0.2f;
        currentPointOfView = PointOfView.SOUTH;
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

    public void setupLigths() {
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

    public void updateLights() {
        FloatBuffer lightPosition;
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.5f).put(0.5f).put(5.0f).put(0.2f).flip();
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);
    }

    public void make2D() {
        // Remove the Z axis
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Game.getInstance().getWidth(), Game.getInstance().getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
    }

    public void make3D() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    public void ready3D() {
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

    public void requestPointOfView(PointOfView v) {
        if (!v.equals(currentPointOfView)) {
            switch (currentPointOfView) {
                case SOUTH:
                    switch (v) {
                        case WEST:
                            rotationToGo = -90f;
                            break;
                        case NORTH:
                            rotationToGo = -180f;
                            break;
                        case EAST:
                            rotationToGo = 90f;
                            break;
                    }
                    break;
                case WEST:
                    switch (v) {
                        case SOUTH:
                            rotationToGo = 90f;
                            break;
                        case NORTH:
                            rotationToGo = -90f;
                            break;
                        case EAST:
                            rotationToGo = -180f;
                            break;
                    }
                    break;
                case NORTH:
                    switch (v) {
                        case SOUTH:
                            rotationToGo = -180f;
                            break;
                        case WEST:
                            rotationToGo = 90f;
                            break;
                        case EAST:
                            rotationToGo = -90f;
                            break;
                    }
                    break;
                case EAST:
                    switch (v) {
                        case SOUTH:
                            rotationToGo = -90f;
                            break;
                        case WEST:
                            rotationToGo = -180f;
                            break;
                        case NORTH:
                            rotationToGo = 90f;
                            break;
                    }
                    break;
            }
        }
    }

    public boolean update3DMovement() {
        make3D();
        focusXToGo = (float) Math.round(focusXToGo * 100) / 100;
        focusYToGo = (float) Math.round(focusYToGo * 100) / 100;
        focusZToGo = (float) Math.round(focusZToGo * 100) / 100;
        rotationToGo = (float) Math.round(rotationToGo * 100) / 100;
        float step = 0.05f;
        if (focusXToGo != 0) {
            if (focusXToGo < 0) {
                originZ -= step;
                focusXToGo += step;
            } else if (focusXToGo > 0) {
                originZ += step;
                focusXToGo -= step;
            }
        }
        if (focusYToGo != 0) {
            if (focusYToGo < 0) {
                originX -= step;
                focusYToGo += step;
            } else if (focusYToGo > 0) {
                originX += step;
                focusYToGo -= step;
            }
        }
        if (focusZToGo != 0) {
            focusZToGo = 0;
            if (focusZToGo < 0) {
                if (-step < focusZToGo) {
                    GL11.glTranslated(0, -focusZToGo, 0);
                    focusZToGo = 0;
                } else {
                    GL11.glTranslated(0, -step, 0);
                    focusZToGo += step;
                }
            } else if (focusZToGo > 0) {
                if (step > focusZToGo) {
                    GL11.glTranslated(0, focusZToGo, 0);
                    focusZToGo = 0;
                } else {
                    GL11.glTranslated(0, step, 0);
                    focusZToGo -= step;
                }
            }
        }
            /*
             * if (scaleToGo != 0) { if (scaleToGo < scale) { scale -= 0.001f; }
			 * if (scaleToGo > scale) { scale += 0.001f; } }
			 */
        if (rotationToGo != 0) {
            double a = Math.sin(Math.PI / 180) * (1 / 2);
            double b = Math.sin(Math.PI / 180) * (1 / 2);

            if (rotationToGo < 0) {
                GL11.glRotatef(1f, 0, 1, 0);
                GL11.glTranslated(-b, 0, a);
                updateLights();
                rotationToGo += 1f;
            }
            if (rotationToGo > 0) {
                GL11.glRotatef(-1f, 0, 1, 0);
                GL11.glTranslated(b, 0, -a);
                updateLights();
                rotationToGo -= 1f;
            }


            if (rotationToGo == -45f || rotationToGo == -135f || rotationToGo == -225f || rotationToGo == -315f) {
                switch (currentPointOfView) {
                    case SOUTH:
                        currentPointOfView = PointOfView.WEST;
                        break;
                    case WEST:
                        currentPointOfView = PointOfView.NORTH;
                        break;
                    case NORTH:
                        currentPointOfView = PointOfView.EAST;
                        break;
                    case EAST:
                        currentPointOfView = PointOfView.SOUTH;
                        break;
                }
            }
            if (rotationToGo == 45f || rotationToGo == 135f || rotationToGo == 225f || rotationToGo == 315f) {
                switch (currentPointOfView) {
                    case SOUTH:
                        currentPointOfView = PointOfView.EAST;
                        break;
                    case WEST:
                        currentPointOfView = PointOfView.SOUTH;
                        break;
                    case NORTH:
                        currentPointOfView = PointOfView.WEST;
                        break;
                    case EAST:
                        currentPointOfView = PointOfView.NORTH;
                        break;
                }
            }
        }
        if (focusXToGo == 0 && focusYToGo == 0 && focusZToGo == 0 && rotationToGo == 0) {
            return false;
        }
        return true;
    }

}

