package com.ghostofpq.seltyrtactical.main.entities.battlefield;

import com.ghostofpq.seltyrtactical.main.graphics.Cube;
import com.ghostofpq.seltyrtactical.main.graphics.Position;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 05/07/13
 * Time: 08:41
 * To change this template use File | Settings | File Templates.
 */
@Getter
public class Battlefield implements Serializable {

    private static final long serialVersionUID = -6878010880627782277L;
    private int length;  //x
    private int height;  //y
    private int depth;  //z
    private Map<Position, BattlefieldElement> battlefieldElementMap;

    public Battlefield(int length, int height, int depth) {
        this.length = length;
        this.height = height;
        this.depth = depth;
        battlefieldElementMap = new HashMap<Position, BattlefieldElement>();
    }

    public void addBattlefieldElement(int x, int y, int z, BattlefieldElement.BattlefieldElementType type) {
        Position position = new Position(x, y, z);
        BattlefieldElement battlefieldElement = null;
        switch (type) {
            case BLOC:
                battlefieldElement = new Block();
                break;
        }
        if (battlefieldElement != null) {
            battlefieldElementMap.put(position, battlefieldElement);
        }
    }

    public Map<Position, Cube> toDrawableList() {
        Map<Position, Cube> toDraw = new HashMap<Position, Cube>();
        for (Position position : battlefieldElementMap.keySet()) {
            BattlefieldElement element = battlefieldElementMap.get(position);
            if (element != null) {
                if (element.getType().equals(BattlefieldElement.BattlefieldElementType.BLOC)) {
                    Cube cube = new Cube(position);
                    toDraw.put(position, cube);
                }
            }
        }
        for (Position position : toDraw.keySet()) {
            Cube cube = toDraw.get(position);
            Position positionAbove = new Position(position.getX(), position.getY() + 1, position.getZ());
            Position positionLeft = new Position(position.getX() - 1, position.getY(), position.getZ());
            Position positionRight = new Position(position.getX() + 1, position.getY(), position.getZ());
            Position positionUp = new Position(position.getX(), position.getY(), position.getZ() - 1);
            Position positionDown = new Position(position.getX(), position.getY(), position.getZ() + 1);

            if (toDraw.keySet().contains(positionAbove)) {
                cube.setSelectable(false);
                if (toDraw.keySet().contains(positionLeft) && toDraw.keySet().contains(positionRight) && toDraw.keySet().contains(positionUp) && toDraw.keySet().contains(positionDown)) {
                    cube.setVisible(false);
                }
            }

        }

        return toDraw;
    }

}
