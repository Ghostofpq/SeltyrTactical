package com.ghostofpq.seltyrtactical.main.graphics;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.opengl.GL11;

import com.ghostofpq.seltyrtactical.main.utils.GraphicsManager;
import com.ghostofpq.seltyrtactical.main.utils.TextureKey;
import com.ghostofpq.seltyrtactical.main.utils.TextureManager;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 15/06/13
 * Time: 13:13
 * To change this template use File | Settings | File Templates.
 */

@Getter
@Setter
public class Facet implements Serializable {

    private static final long serialVersionUID = 5436385396379038962L;
    private PositionAbsolute corner1;
    private PositionAbsolute corner2;
    private PositionAbsolute corner3;
    private PositionAbsolute corner4;
    private boolean visible;
    private TextureKey texture;

    public Facet(PositionAbsolute corner1, PositionAbsolute corner2, PositionAbsolute corner3, PositionAbsolute corner4, TextureKey texture) {
        this.setCorner1(corner1);
        this.setCorner2(corner2);
        this.setCorner3(corner3);
        this.setCorner4(corner4);
        this.setTexture(texture);
        this.setVisible(true);
    }

    public void draw() {
        if (isVisible()) {
            GL11.glColor4f(1f, 1f, 1f, 1f);
            TextureManager.getInstance().getTexture(texture).bind();
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2d(0, 0);
            GL11.glVertex3d((corner1.getX() - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale()
                    , (corner1.getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale()
                    , (corner1.getZ() - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            GL11.glTexCoord2d(1, 0);
            GL11.glVertex3d((corner2.getX() - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale()
                    , (corner2.getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale()
                    , (corner2.getZ() - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            GL11.glTexCoord2d(1, 1);
            GL11.glVertex3d((corner3.getX() - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale()
                    , (corner3.getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale()
                    , (corner3.getZ() - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            GL11.glTexCoord2d(0, 1);
            GL11.glVertex3d((corner4.getX() - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale()
                    , (corner4.getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale()
                    , (corner4.getZ() - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            GL11.glEnd();
        }
    }
}
