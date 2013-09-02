package com.ghostofpq.seltyrtactical.entities.battlefield;

import com.ghostofpq.seltyrtactical.commons.Position;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class Battlefield implements Serializable {

    private static final long serialVersionUID = -6878010880627782277L;
    private int length;  //x
    private int height;  //y
    private int depth;  //z
    private Map<com.ghostofpq.seltyrtactical.commons.Position, BattlefieldElement> battlefieldElementMap;
    private int numberOfPlayers;
    private Map<Integer, List<Position>> deploymentZones;

    public Battlefield(int length, int height, int depth, int numberOfPlayers) {
        this.length = length;
        this.height = height;
        this.depth = depth;
        this.numberOfPlayers = numberOfPlayers;
        battlefieldElementMap = new HashMap<Position, BattlefieldElement>();
        deploymentZones = new HashMap<Integer, List<Position>>();
    }

    public void addDeployementZone(Integer playerNumber, Position position) {
        if (null != playerNumber) {
            List<Position> deploymentZone = deploymentZones.get(playerNumber);
            if (null == deploymentZone) {
                deploymentZone = new ArrayList<Position>();
            }
            deploymentZone.add(position);
            deploymentZones.put(playerNumber, deploymentZone);
        }
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

    /**
     * Getters and Setters
     */

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public Map<Position, BattlefieldElement> getBattlefieldElementMap() {
        return battlefieldElementMap;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Map<Integer, List<Position>> getDeploymentZones() {
        return deploymentZones;
    }

    public boolean canMoveTo(Position position, int height) {
        if (height == 0) {
            return true;
        }

        position.plusY(1);
        if (getBattlefieldElementMap().containsKey(position)) {
            return false;
        } else {
            return canMoveTo(position, height - 1);
        }
    }

    public List<Position> getPath(Position position, Position positionToGo) {
        List<Position> path = new ArrayList<Position>();
        if (position.getX() != positionToGo.getX()) {
            if (position.getX() < positionToGo.getX()) {
                // We clone the position to test if we can move.
                Position altPos = new Position(position);
                altPos.plusX(1);

                if (canMoveTo(altPos, 2)) {
                    position.plusX(1);
                    path.add(new Position(position));
                    path.addAll(getPath(position, positionToGo));
                }
            } else {
                // We clone the position to test if we can move.
                Position altPos = new Position(position);
                altPos.plusX(-1);

                if (canMoveTo(altPos, 2)) {
                    position.plusX(-1);
                    path.add(new Position(position));
                    path.addAll(getPath(position, positionToGo));
                }
            }
        }

        if (position.getZ() != positionToGo.getZ()) {
            if (position.getZ() < positionToGo.getZ()) {
                // We clone the position to test if we can move.
                Position altPos = new Position(position);
                altPos.plusZ(1);

                if (canMoveTo(altPos, 2)) {
                    position.plusZ(1);
                    path.add(new Position(position));
                    path.addAll(getPath(position, positionToGo));
                }
            } else {
                // We clone the position to test if we can move.
                Position altPos = new Position(position);
                altPos.plusZ(-1);

                if (canMoveTo(altPos, 2)) {
                    position.plusZ(-1);
                    path.add(new Position(position));
                    path.addAll(getPath(position, positionToGo));
                }
            }
        }

        if (position.getY() != positionToGo.getY()) {
            if (position.getY() < positionToGo.getY()) {
                // We clone the position to test if we can move.
                Position altPos = new Position(position);
                altPos.plusY(1);

                if (canMoveTo(altPos, 2)) {
                    position.plusY(1);
                    path.add(new Position(position));
                    path.addAll(getPath(position, positionToGo));
                }
            } else {
                // We clone the position to test if we can move.
                Position altPos = new Position(position);
                altPos.plusY(-1);

                if (canMoveTo(altPos, 2)) {
                    position.plusY(-1);
                    path.add(new Position(position));
                    path.addAll(getPath(position, positionToGo));
                }
            }
        }

        return path;
    }
}
