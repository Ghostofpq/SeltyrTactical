package com.ghostofpq.seltyrtactical.game.graphics;

import com.ghostofpq.seltyrtactical.commons.Position;
import com.ghostofpq.seltyrtactical.commons.PositionAbsolute;
import com.ghostofpq.seltyrtactical.entities.character.GameCharacter;
import com.ghostofpq.seltyrtactical.game.utils.GraphicsManager;
import com.ghostofpq.seltyrtactical.game.utils.SpritesheetManager;
import com.ghostofpq.seltyrtactical.game.utils.TextureKey;
import com.ghostofpq.seltyrtactical.game.utils.TextureManager;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;
import java.util.Map;

public class GameCharacterRepresentation extends DrawableObject {

    private GameCharacter character;
    private PositionAbsolute positionAbsolute;
    private Map<PointOfView, Animation> animationWalk;
    private Texture chara;
    private boolean hasMoved;

    public GameCharacterRepresentation(GameCharacter character, Position position) {
        this.setCharacter(character);
        this.setPosition(position);
        this.setPositionAbsolute(position.toAbsolute());

        SpriteSheet spriteSheet = SpritesheetManager.getInstance().getSpriteSheet("Arthur");


        animationWalk = new HashMap<PointOfView, Animation>();


        Animation animation = new Animation();
        animation.setAutoUpdate(true);
        animation.addFrame(spriteSheet.getSubImage(10, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(42, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(74, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(106, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(74, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(42, 300, 32, 36), 100);
        animationWalk.put(PointOfView.SOUTH, animation);

        animation = new Animation();
        animation.setAutoUpdate(true);
        animation.addFrame(spriteSheet.getSubImage(138, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(170, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(202, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(234, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(202, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(170, 300, 32, 36), 100);
        animationWalk.put(PointOfView.EAST, animation);

        animation = new Animation();
        animation.setAutoUpdate(true);
        animation.addFrame(spriteSheet.getSubImage(138, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(170, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(202, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(234, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(202, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(170, 300, 32, 36), 100);
        animationWalk.put(PointOfView.WEST, animation);

        animation = new Animation();
        animation.setAutoUpdate(true);
        animation.addFrame(spriteSheet.getSubImage(266, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(298, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(330, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(362, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(330, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(298, 300, 32, 36), 100);
        animationWalk.put(PointOfView.NORTH, animation);

        chara = TextureManager.getInstance().getTexture(TextureKey.CHAR);
    }

    public void draw() {
        PointOfView pointOfView = GraphicsManager.getInstance().getCurrentPointOfView();
        GL11.glColor4f(1f, 1f, 1f, 1f);

        PositionAbsolute corner1 = new PositionAbsolute(
                (this.getPositionAbsolute().getX() + 0.1f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                (this.getPositionAbsolute().getY() + 1.5f - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                (this.getPositionAbsolute().getZ() + 0.9f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        PositionAbsolute corner2 = new PositionAbsolute(
                (this.getPositionAbsolute().getX() + 0.9f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                (this.getPositionAbsolute().getY() + 1.5f - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                (this.getPositionAbsolute().getZ() + 0.1f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        PositionAbsolute corner3 = new PositionAbsolute(
                (this.getPositionAbsolute().getX() + 0.9f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                (this.getPositionAbsolute().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                (this.getPositionAbsolute().getZ() + 0.1f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        PositionAbsolute corner4 = new PositionAbsolute(
                (this.getPositionAbsolute().getX() + 0.1f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                (this.getPositionAbsolute().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                (this.getPositionAbsolute().getZ() + 0.9f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());

        Animation animation = animationWalk.get(pointOfView);
        animation.getCurrentFrame().bind();
        if (pointOfView.equals(PointOfView.EAST)) {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2d(animation.getCurrentFrame().getTextureOffsetX() + animation.getCurrentFrame().getTextureWidth(), animation.getCurrentFrame().getTextureOffsetY() + animation.getCurrentFrame().getTextureHeight());
            GL11.glVertex3d(corner1.getX(), corner1.getY(), corner1.getZ());
            GL11.glTexCoord2d(animation.getCurrentFrame().getTextureOffsetX(), animation.getCurrentFrame().getTextureOffsetY() + animation.getCurrentFrame().getTextureHeight());
            GL11.glVertex3d(corner2.getX(), corner2.getY(), corner2.getZ());
            GL11.glTexCoord2d(animation.getCurrentFrame().getTextureOffsetX(), animation.getCurrentFrame().getTextureOffsetY());
            GL11.glVertex3d(corner3.getX(), corner3.getY(), corner3.getZ());
            GL11.glTexCoord2d(animation.getCurrentFrame().getTextureOffsetX() + animation.getCurrentFrame().getTextureWidth(), animation.getCurrentFrame().getTextureOffsetY());
            GL11.glVertex3d(corner4.getX(), corner4.getY(), corner4.getZ());
            GL11.glEnd();
        } else {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2d(animation.getCurrentFrame().getTextureOffsetX(), animation.getCurrentFrame().getTextureOffsetY());
            GL11.glVertex3d(corner1.getX(), corner1.getY(), corner1.getZ());
            GL11.glTexCoord2d(animation.getCurrentFrame().getTextureOffsetX() + animation.getCurrentFrame().getTextureWidth(), animation.getCurrentFrame().getTextureOffsetY());
            GL11.glVertex3d(corner2.getX(), corner2.getY(), corner2.getZ());
            GL11.glTexCoord2d(animation.getCurrentFrame().getTextureOffsetX() + animation.getCurrentFrame().getTextureWidth(), animation.getCurrentFrame().getTextureOffsetY() + animation.getCurrentFrame().getTextureHeight());
            GL11.glVertex3d(corner3.getX(), corner3.getY(), corner3.getZ());
            GL11.glTexCoord2d(animation.getCurrentFrame().getTextureOffsetX(), animation.getCurrentFrame().getTextureOffsetY() + animation.getCurrentFrame().getTextureHeight());
            GL11.glVertex3d(corner4.getX(), corner4.getY(), corner4.getZ());
            GL11.glEnd();
        }
        for (Animation anim : animationWalk.values()) {
            anim.update(5);
        }

    }

    public String toString() {
        return character.getName();
    }

    public GameCharacter getCharacter() {
        return character;
    }

    public void setCharacter(GameCharacter character) {
        this.character = character;
    }

    public PositionAbsolute getPositionAbsolute() {
        return positionAbsolute;
    }

    public void setPositionAbsolute(PositionAbsolute positionAbsolute) {
        this.positionAbsolute = positionAbsolute;

        int xFromPositionAbsolute = (int) Math.floor(this.getPositionAbsolute().getX());
        int yFromPositionAbsolute = (int) Math.floor(this.getPositionAbsolute().getY());
        int zFromPositionAbsolute = (int) Math.floor(this.getPositionAbsolute().getZ());

        if (xFromPositionAbsolute != this.getPosition().getX()) {
            this.getPosition().setX(xFromPositionAbsolute);
            this.setHasMoved(true);
        }
        if (yFromPositionAbsolute != this.getPosition().getY()) {
            this.getPosition().setY(yFromPositionAbsolute);
            this.setHasMoved(true);
        }
        if (zFromPositionAbsolute != this.getPosition().getZ()) {
            this.getPosition().setZ(zFromPositionAbsolute);
            this.setHasMoved(true);
        }
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
