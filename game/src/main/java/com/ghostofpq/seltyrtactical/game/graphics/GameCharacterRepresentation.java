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

        SpriteSheet spriteSheet = SpritesheetManager.getInstance().getSpriteSheet("Ramza");
        animation = new Animation();
        animation.setAutoUpdate(true);
        animation.addFrame(spriteSheet.getSprite(1, 0), 100);
        animation.addFrame(spriteSheet.getSprite(1, 1), 100);
        animation.addFrame(spriteSheet.getSprite(1, 2), 100);
        animation.addFrame(spriteSheet.getSprite(1, 1), 100);

        chara = TextureManager.getInstance().getTexture(TextureKey.CHAR);
    }

    public void draw() {
        PointOfView pointOfView = GraphicsManager.getInstance().getCurrentPointOfView();
        GL11.glColor4f(1f, 1f, 1f, 1f);

        PositionAbsolute corner1 = new PositionAbsolute((this.getPosition().getX() - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getY() + 1 - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getZ() + 1 - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        PositionAbsolute corner2 = new PositionAbsolute((this.getPosition().getX() + 1 - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getY() + 1 - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getZ() - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        PositionAbsolute corner3 = new PositionAbsolute((this.getPosition().getX() + 1 - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getZ() - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        PositionAbsolute corner4 = new PositionAbsolute((this.getPosition().getX() - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                (this.getPosition().getZ() + 1 - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());


        chara.bind();
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(0, 0);
        GL11.glVertex3d(corner1.getX(), corner1.getY(), corner1.getZ());
        GL11.glTexCoord2d(1, 0);
        GL11.glVertex3d(corner2.getX(), corner2.getY(), corner2.getZ());
        GL11.glTexCoord2d(1, 1);
        GL11.glVertex3d(corner3.getX(), corner3.getY(), corner3.getZ());
        GL11.glTexCoord2d(0, 1);
        GL11.glVertex3d(corner4.getX(), corner4.getY(), corner4.getZ());
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
