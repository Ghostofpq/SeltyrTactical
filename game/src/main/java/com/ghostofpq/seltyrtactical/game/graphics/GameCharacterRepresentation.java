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

public class GameCharacterRepresentation extends DrawableObject {

    private GameCharacter character;
    private Animation animation;
    private Texture chara;

    public GameCharacterRepresentation(GameCharacter character, Position position) {
        this.setCharacter(character);
        this.setPosition(position);

        SpriteSheet spriteSheet = SpritesheetManager.getInstance().getSpriteSheet("Arthur");
        animation = new Animation();
        animation.setAutoUpdate(true);
        animation.addFrame(spriteSheet.getSubImage(10, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(42, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(74, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(106, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(74, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(42, 300, 32, 36), 100);

        chara = TextureManager.getInstance().getTexture(TextureKey.CHAR);
    }

    public void draw() {
        PointOfView pointOfView = GraphicsManager.getInstance().getCurrentPointOfView();
        GL11.glColor4f(1f, 1f, 1f, 1f);

        PositionAbsolute corner1 = new PositionAbsolute(
                (this.getPosition().getX() + 0.1f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getY() + 1.5f - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getZ() + 0.9f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        PositionAbsolute corner2 = new PositionAbsolute(
                (this.getPosition().getX() + 0.9f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getY() + 1.5f - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getZ() + 0.1f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        PositionAbsolute corner3 = new PositionAbsolute(
                (this.getPosition().getX() + 0.9f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getZ() + 0.1f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        PositionAbsolute corner4 = new PositionAbsolute(
                (this.getPosition().getX() + 0.1f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getZ() + 0.9f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());


        animation.getCurrentFrame().bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(animation.getCurrentFrame().getTextureOffsetX(), animation.getCurrentFrame().getTextureOffsetY());
        GL11.glVertex3d(corner1.getX(), corner1.getY(), corner1.getZ());
        GL11.glTexCoord2d(animation.getCurrentFrame().getTextureOffsetX() + animation.getCurrentFrame().getTextureWidth(), animation.getCurrentFrame().getTextureOffsetY());
        GL11.glVertex3d(corner2.getX(), corner2.getY(), corner2.getZ());
        GL11.glTexCoord2d(animation.getCurrentFrame().getTextureOffsetX() + animation.getCurrentFrame().getTextureWidth(), animation.getCurrentFrame().getTextureOffsetY() + animation.getCurrentFrame().getTextureHeight());
        GL11.glVertex3d(corner3.getX(), corner3.getY(), corner3.getZ());
        GL11.glTexCoord2d(animation.getCurrentFrame().getTextureOffsetX(), animation.getCurrentFrame().getTextureOffsetY() + animation.getCurrentFrame().getTextureHeight());
        GL11.glVertex3d(corner4.getX(), corner4.getY(), corner4.getZ());
        animation.update(5);

        GL11.glEnd();
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
}
