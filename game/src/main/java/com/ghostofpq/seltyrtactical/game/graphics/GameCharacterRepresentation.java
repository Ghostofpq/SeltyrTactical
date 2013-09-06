package com.ghostofpq.seltyrtactical.game.graphics;

import com.ghostofpq.seltyrtactical.commons.Position;
import com.ghostofpq.seltyrtactical.commons.PositionAbsolute;
import com.ghostofpq.seltyrtactical.entities.character.GameCharacter;
import com.ghostofpq.seltyrtactical.game.utils.GraphicsManager;
import com.ghostofpq.seltyrtactical.game.utils.SpritesheetManager;
import com.ghostofpq.seltyrtactical.game.utils.TextureKey;
import com.ghostofpq.seltyrtactical.game.utils.TextureManager;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GameCharacterRepresentation extends DrawableObject {

    private GameCharacter character;
    private Map<PointOfView, Animation> animationWalk;
    private Texture chara;
    private boolean hasMoved;
    private PointOfView headingAngle;
    private PositionAbsolute positionToGo;
    private List<PositionAbsolute> positionsToGo;

    public GameCharacterRepresentation(GameCharacter character, Position position) {
        this.setCharacter(character);
        this.setPosition(position);
        this.setPositionAbsolute(position.toAbsolute());
        positionToGo = position.toAbsolute();
        positionsToGo = new ArrayList<PositionAbsolute>();

        SpriteSheet spriteSheet = SpritesheetManager.getInstance().getSpriteSheet("Arthur");
        headingAngle = GraphicsManager.getInstance().getCurrentPointOfView();

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
        animation.addFrame(spriteSheet.getSubImage(292, 251, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(324, 251, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(356, 251, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(388, 251, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(356, 251, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(324, 251, 32, 36), 100);
        animationWalk.put(PointOfView.WEST, animation);

        animation = new Animation();
        animation.setAutoUpdate(true);
        animation.addFrame(spriteSheet.getSubImage(274, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(306, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(338, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(370, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(338, 300, 32, 36), 100);
        animation.addFrame(spriteSheet.getSubImage(306, 300, 32, 36), 100);
        animationWalk.put(PointOfView.NORTH, animation);

        chara = TextureManager.getInstance().getTexture(TextureKey.CHAR);
    }

    public void update(long deltaTime) {
        for (Animation animation : animationWalk.values()) {
            animation.update(deltaTime);
        }

        boolean xDifferent = getPositionAbsolute().getX() != getPositionToGo().getX();
        boolean yDifferent = getPositionAbsolute().getY() != getPositionToGo().getY();
        boolean zDifferent = getPositionAbsolute().getZ() != getPositionToGo().getZ();

        if (xDifferent || yDifferent || zDifferent) {
            log.debug("to go : {}/{}/{} ({}/{}/{}) => {}/{}/{} ",
                    getPositionAbsolute().getX(), getPositionAbsolute().getY(), getPositionAbsolute().getZ(),
                    getPosition().getX(), getPosition().getY(), getPosition().getZ(),
                    getPositionToGo().getX(), getPositionToGo().getY(), getPositionToGo().getZ());
            setHeadingAngle(getHeadingAngleFor(getPositionToGo()));

            float x = getPositionAbsolute().getX();
            float y = getPositionAbsolute().getY();
            float z = getPositionAbsolute().getZ();

            float step = 0.1f;

            if (xDifferent) {
                if (getPositionAbsolute().getX() > getPositionToGo().getX()) {
                    if (getPositionAbsolute().getX() - getPositionToGo().getX() < step) {
                        x = getPositionToGo().getX();
                    } else {
                        x = getPositionAbsolute().getX() - step;
                    }
                } else if (getPositionAbsolute().getX() < getPositionToGo().getX()) {
                    if (getPositionToGo().getX() - getPositionAbsolute().getX() < step) {
                        x = getPositionToGo().getX();
                    } else {
                        x = getPositionAbsolute().getX() + step;
                    }
                }
            }

            if (yDifferent) {
                if (getPositionAbsolute().getY() > getPositionToGo().getY()) {
                    if (getPositionAbsolute().getY() - getPositionToGo().getY() < step) {
                        y = getPositionToGo().getY();
                    } else {
                        y = getPositionAbsolute().getY() - step;
                    }
                } else if (getPositionAbsolute().getY() < getPositionToGo().getY()) {
                    if (getPositionToGo().getY() - getPositionAbsolute().getY() < step) {
                        y = getPositionToGo().getY();
                    } else {
                        y = getPositionAbsolute().getY() + step;
                    }
                }
            }

            if (zDifferent) {
                if (getPositionAbsolute().getZ() > getPositionToGo().getZ()) {
                    if (getPositionAbsolute().getZ() - getPositionToGo().getZ() < step) {
                        z = getPositionToGo().getZ();
                    } else {
                        z = getPositionAbsolute().getZ() - step;
                    }
                } else if (getPositionAbsolute().getZ() < getPositionToGo().getZ()) {
                    if (getPositionToGo().getZ() - getPositionAbsolute().getZ() < step) {
                        z = getPositionToGo().getZ();
                    } else {
                        z = getPositionAbsolute().getZ() + step;
                    }
                }
            }


            setPositionAbsolute(new PositionAbsolute(x, y, z));
        } else if (!positionsToGo.isEmpty()) {
            positionsToGo.remove(0);
            if (!positionsToGo.isEmpty()) {
                setPositionToGo(positionsToGo.get(0));
            }
        }
    }

    public void draw() {
        PointOfView pointOfView = GraphicsManager.getInstance().getCurrentPointOfView();
        GL11.glColor4f(1f, 1f, 1f, 1f);
        PositionAbsolute corner1;
        PositionAbsolute corner2;
        PositionAbsolute corner3;
        PositionAbsolute corner4;
        if (pointOfView.equals(PointOfView.SOUTH)) {
            corner1 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.1f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() + 1.5f - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.9f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            corner2 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.9f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() + 1.5f - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.1f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            corner3 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.9f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.1f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            corner4 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.1f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.9f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        } else if (pointOfView.equals(PointOfView.NORTH)) {
            corner1 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.9f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() + 1.5f - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.1f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            corner2 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.1f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() + 1.5f - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.9f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            corner3 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.1f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.9f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            corner4 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.9f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.1f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        } else if (pointOfView.equals(PointOfView.WEST)) {
            corner1 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.1f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() + 1.5f - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.1f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            corner2 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.9f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() + 1.5f - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.9f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            corner3 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.9f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.9f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            corner4 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.1f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.1f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        } else {  //if(pointOfView.equals(PointOfView.EAST))
            corner1 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.9f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() + 1.5f - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.9f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            corner2 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.1f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() + 1.5f - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.1f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            corner3 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.1f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.1f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
            corner4 = new PositionAbsolute(
                    (this.getPositionAbsolute().getX() + 0.9f - GraphicsManager.getInstance().getOriginX()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getY() - GraphicsManager.getInstance().getOriginY()) * GraphicsManager.getInstance().getScale(),
                    (this.getPositionAbsolute().getZ() + 0.9f - GraphicsManager.getInstance().getOriginZ()) * GraphicsManager.getInstance().getScale());
        }

        Animation animation = animationWalk.get(getEquivalentPointOfView(pointOfView, headingAngle));
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

    public PositionAbsolute getPositionAbsolute() {
        return positionAbsolute;
    }

    public void setPositionAbsolute(PositionAbsolute positionAbsolute) {
        this.positionAbsolute = positionAbsolute;

        int xFromPositionAbsolute = (int) Math.floor(this.getPositionAbsolute().getX());
        int yFromPositionAbsolute = (int) Math.floor(this.getPositionAbsolute().getY());
        int zFromPositionAbsolute = (int) Math.floor(this.getPositionAbsolute().getZ());

        //log.debug("{}/{}/{} // {}/{}/{}",
        //        xFromPositionAbsolute, yFromPositionAbsolute, zFromPositionAbsolute,
        //        getPosition().getX(), getPosition().getY(), getPosition().getZ());

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

    private PointOfView getEquivalentPointOfView(PointOfView boardPointOfView, PointOfView charPointOfView) {
        PointOfView result = PointOfView.SOUTH;


        switch (boardPointOfView) {
            case NORTH:
                switch (charPointOfView) {
                    case NORTH:
                        result = PointOfView.SOUTH;
                        break;
                    case SOUTH:
                        result = PointOfView.NORTH;
                        break;
                    case EAST:
                        result = PointOfView.EAST;
                        break;
                    case WEST:
                        result = PointOfView.WEST;
                        break;
                }
                break;
            case SOUTH:
                switch (charPointOfView) {
                    case NORTH:
                        result = PointOfView.NORTH;
                        break;
                    case SOUTH:
                        result = PointOfView.SOUTH;
                        break;
                    case EAST:
                        result = PointOfView.WEST;
                        break;
                    case WEST:
                        result = PointOfView.EAST;
                        break;
                }
                break;
            case EAST:
                switch (charPointOfView) {
                    case NORTH:
                        result = PointOfView.WEST;
                        break;
                    case SOUTH:
                        result = PointOfView.EAST;
                        break;
                    case EAST:
                        result = PointOfView.SOUTH;
                        break;
                    case WEST:
                        result = PointOfView.NORTH;
                        break;
                }
                break;
            case WEST:
                switch (charPointOfView) {
                    case NORTH:
                        result = PointOfView.EAST;
                        break;
                    case SOUTH:
                        result = PointOfView.WEST;
                        break;
                    case EAST:
                        result = PointOfView.NORTH;
                        break;
                    case WEST:
                        result = PointOfView.SOUTH;
                        break;
                }
                break;
        }
        return result;
    }

    public PositionAbsolute getPositionToGo() {
        return positionToGo;
    }

    public void setPositionToGo(PositionAbsolute positionToGo) {
        this.positionToGo = positionToGo;
    }

    public void setPositionsToGo(List<Position> positions) {
        positionsToGo.add(positions.get(0).toAbsolute());
        if (positions.size() > 1) {
            for (int i = 1; i < positions.size(); i++) {
                if (positions.get(i).getY() == positions.get(i - 1).getY()) {
                    positionsToGo.add(positions.get(i).toAbsolute());
                } else {
                    while (positions.get(i).getY() != positions.get(i - 1).getY()) {
                        positionsToGo.add(positions.get(i).toAbsolute());
                        i++;
                    }
                    positionsToGo.add(positions.get(i).toAbsolute());
                    i++;

                    PositionAbsolute step1 = positions.get(i).toAbsolute();
                    PositionAbsolute step2 = positions.get(i).toAbsolute();
                    PositionAbsolute step3 = positions.get(i).toAbsolute();

                    step1.setY(step1.getY() + 0.5f);
                    step2.setY(step1.getY() + 1f);
                    step3.setY(step1.getY() + 0.5f);

                    if (positions.get(i).getX() != positions.get(i - 1).getX()) {
                        if (positions.get(i).getX() < positions.get(i - 1).getX()) {
                            step1.setX(step1.getX() - 0.1f);
                            step2.setX(step1.getX() - 0.5f);
                            step3.setX(step1.getX() - 0.9f);
                        } else if (positions.get(i).getX() > positions.get(i - 1).getX()) {
                            step1.setX(step1.getX() + 0.1f);
                            step2.setX(step1.getX() + 0.5f);
                            step3.setX(step1.getX() + 0.9f);
                        }
                    }
                    if (positions.get(i).getZ() != positions.get(i - 1).getZ()) {
                        if (positions.get(i).getZ() < positions.get(i - 1).getZ()) {
                            step1.setZ(step1.getZ() - 0.1f);
                            step2.setZ(step1.getZ() - 0.5f);
                            step3.setZ(step1.getZ() - 0.9f);
                        } else if (positions.get(i).getZ() > positions.get(i - 1).getZ()) {
                            step1.setZ(step1.getZ() + 0.1f);
                            step2.setZ(step1.getZ() + 0.5f);
                            step3.setZ(step1.getZ() + 0.9f);
                        }
                    }
                    positionsToGo.add(positions.get(i).toAbsolute());
                }
            }
        }
    }

    public PointOfView getHeadingAngle() {
        return headingAngle;
    }

    public void setHeadingAngle(PointOfView headingAngle) {
        this.headingAngle = headingAngle;
    }

    public PointOfView getHeadingAngleFor(PositionAbsolute positionToGo) {
        boolean xBigger = positionToGo.getX() > getPositionAbsolute().getX();
        boolean zBigger = positionToGo.getZ() > getPositionAbsolute().getZ();
        PointOfView result;

        if (xBigger) {
            if (zBigger) {
                result = PointOfView.SOUTH;
            } else {
                result = PointOfView.EAST;
            }
        } else {
            if (zBigger) {
                result = PointOfView.WEST;
            } else {
                result = PointOfView.NORTH;
            }
        }
        return result;
    }
}
