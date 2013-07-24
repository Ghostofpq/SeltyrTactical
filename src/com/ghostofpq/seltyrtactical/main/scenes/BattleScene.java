package com.ghostofpq.seltyrtactical.main.scenes;

import com.ghostofpq.seltyrtactical.main.entities.battlefield.Battlefield;
import com.ghostofpq.seltyrtactical.main.entities.battlefield.BattlefieldElement;
import com.ghostofpq.seltyrtactical.main.graphics.Cube;
import com.ghostofpq.seltyrtactical.main.graphics.PointOfView;
import com.ghostofpq.seltyrtactical.main.graphics.Position;
import com.ghostofpq.seltyrtactical.main.utils.GraphicsManager;
import com.ghostofpq.seltyrtactical.main.utils.HighlightColor;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vmpx4526
 * Date: 04/07/13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class BattleScene implements Scene {
    private static volatile BattleScene instance = null;
    private Map<Position, Cube> todraw;
    private List<Position> positionsToDraw;
    private List<Position> positionsToSelect;
    private boolean graphicManagerIsWorking;
    private Position cursor;
    private Battlefield battlefield;

    private BattleScene() {
    }

    public static BattleScene getInstance() {
        if (instance == null) {
            synchronized (BattleScene.class) {
                if (instance == null) {
                    instance = new BattleScene();
                }
            }
        }
        return instance;
    }

    @Override
    public void init() {
        battlefield = new Battlefield(5, 5, 5);

        battlefield.addBattlefieldElement(0, 0, 0, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(1, 0, 0, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(2, 0, 0, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(3, 0, 0, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(4, 0, 0, BattlefieldElement.BattlefieldElementType.BLOC);

        battlefield.addBattlefieldElement(0, 0, 1, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(1, 0, 1, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(2, 0, 1, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(3, 0, 1, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(4, 0, 1, BattlefieldElement.BattlefieldElementType.BLOC);

        battlefield.addBattlefieldElement(0, 0, 2, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(1, 0, 2, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(2, 0, 2, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(2, 3, 2, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(3, 0, 2, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(4, 0, 2, BattlefieldElement.BattlefieldElementType.BLOC);

        battlefield.addBattlefieldElement(0, 0, 3, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(1, 0, 3, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(2, 0, 3, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(2, 3, 3, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(3, 0, 3, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(4, 0, 3, BattlefieldElement.BattlefieldElementType.BLOC);

        battlefield.addBattlefieldElement(0, 0, 4, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(1, 0, 4, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(2, 0, 4, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(2, 2, 4, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(3, 0, 4, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(3, 1, 4, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(4, 0, 4, BattlefieldElement.BattlefieldElementType.BLOC);

        todraw = battlefield.toDrawableList();
        updatePositionLists();
        cursor = new Position(0, 0, 0);
        todraw.get(cursor).setHighlight(HighlightColor.BLUE);

        GraphicsManager.getInstance().setupLigths();
        GraphicsManager.getInstance().ready3D();

        graphicManagerIsWorking = false;
    }

    @Override
    public void update() {
        if (graphicManagerIsWorking) {
            graphicManagerIsWorking = GraphicsManager.getInstance().update3DMovement();
        }
    }

    @Override
    public void render() {
        render3D();
        render2D();
    }

    @Override
    public void manageInput() {
        if (!graphicManagerIsWorking) {
            while (Keyboard.next()) {
                if (Keyboard.getEventKeyState()) {
                    if (Keyboard.getEventKey() == Keyboard.KEY_O) {
                        GraphicsManager.getInstance().requestPointOfView(PointOfView.NORTH);
                        graphicManagerIsWorking = true;
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_I) {
                        GraphicsManager.getInstance().requestPointOfView(PointOfView.WEST);
                        graphicManagerIsWorking = true;
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_L) {
                        GraphicsManager.getInstance().requestPointOfView(PointOfView.EAST);
                        graphicManagerIsWorking = true;
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_K) {
                        GraphicsManager.getInstance().requestPointOfView(PointOfView.SOUTH);
                        graphicManagerIsWorking = true;
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_P) {
                        GraphicsManager.getInstance().zoomIn();
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_M) {
                        GraphicsManager.getInstance().zoomOut();
                    }


                    if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
                        switch (GraphicsManager.getInstance().getCurrentPointOfView()) {
                            case EAST:
                                cursorLeft();
                                break;
                            case NORTH:
                                cursorDown();
                                break;
                            case SOUTH:
                                cursorUp();
                                break;
                            case WEST:
                                cursorRight();
                                break;
                        }
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
                        switch (GraphicsManager.getInstance().getCurrentPointOfView()) {
                            case EAST:
                                cursorRight();
                                break;
                            case NORTH:
                                cursorUp();
                                break;
                            case SOUTH:
                                cursorDown();
                                break;
                            case WEST:
                                cursorLeft();
                                break;
                        }
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                        switch (GraphicsManager.getInstance().getCurrentPointOfView()) {
                            case EAST:
                                cursorDown();
                                break;
                            case NORTH:
                                cursorRight();
                                break;
                            case SOUTH:
                                cursorLeft();
                                break;
                            case WEST:
                                cursorUp();
                                break;
                        }
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                        switch (GraphicsManager.getInstance().getCurrentPointOfView()) {
                            case EAST:
                                cursorUp();
                                break;
                            case NORTH:
                                cursorLeft();
                                break;
                            case SOUTH:
                                cursorRight();
                                break;
                            case WEST:
                                cursorDown();
                                break;
                        }
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_TAB) {
                        cursorTab();
                    }
                }
            }
        }

    }

    private void updatePositionLists() {
        positionsToDraw = new ArrayList<Position>();
        for (Position position : todraw.keySet()) {
            if (todraw.get(position).isVisible()) {
                positionsToDraw.add(position);
            }
        }
        Collections.sort(positionsToDraw);

        positionsToSelect = new ArrayList<Position>();
        for (Position position : positionsToDraw) {
            if (todraw.get(position).isSelectable()) {
                positionsToSelect.add(position);
            }
        }
        Collections.sort(positionsToSelect);
    }

    private void render3D() {
        GraphicsManager.getInstance().make3D();
        for (Position position : positionsToDraw) {
            todraw.get(position).draw(GraphicsManager.getInstance().getCurrentPointOfView());
        }
    }

    private void render2D() {
        GraphicsManager.getInstance().make2D();
        // hud.render();
    }

    private void cursorUp() {
        if (cursor.getZ() != 0) {
            todraw.get(cursor).setHighlight(HighlightColor.NONE);
            cursor.setZ(cursor.getZ() - 1);
            if (!positionsToSelect.contains(cursor)) {
                Position closestPosition = getClosestPosition(cursor);
                cursor = new Position(closestPosition);
            }
            todraw.get(cursor).setHighlight(HighlightColor.BLUE);
        }
    }

    private void cursorDown() {
        if (cursor.getZ() != battlefield.getDepth() - 1) {
            todraw.get(cursor).setHighlight(HighlightColor.NONE);
            cursor.setZ(cursor.getZ() + 1);
            if (!positionsToSelect.contains(cursor)) {
                Position closestPosition = getClosestPosition(cursor);
                cursor = new Position(closestPosition);
            }
            todraw.get(cursor).setHighlight(HighlightColor.BLUE);
        }
    }

    private void cursorLeft() {
        if (cursor.getX() != 0) {
            todraw.get(cursor).setHighlight(HighlightColor.NONE);
            cursor.setX(cursor.getX() - 1);
            if (!positionsToSelect.contains(cursor)) {
                Position closestPosition = getClosestPosition(cursor);
                cursor = new Position(closestPosition);
            }
            todraw.get(cursor).setHighlight(HighlightColor.BLUE);
        }
    }

    private void cursorRight() {
        if (cursor.getX() != battlefield.getLength() - 1) {
            todraw.get(cursor).setHighlight(HighlightColor.NONE);
            cursor.setX(cursor.getX() + 1);
            if (!positionsToSelect.contains(cursor)) {
                Position closestPosition = getClosestPosition(cursor);
                cursor = new Position(closestPosition);
            }
            todraw.get(cursor).setHighlight(HighlightColor.BLUE);
        }
    }

    private void cursorTab() {
        List<Position> possiblePositions = getPossiblePositions(cursor.getX(), cursor.getZ());

        if (possiblePositions.size() != 1) {
            int i = possiblePositions.indexOf(cursor);
            if (i == possiblePositions.size() - 1) {
                i = 0;
            } else {
                i++;
            }
            todraw.get(cursor).setHighlight(HighlightColor.NONE);
            cursor.setY(possiblePositions.get(i).getY());
            todraw.get(cursor).setHighlight(HighlightColor.BLUE);
        }
    }

    private List<Position> getPossiblePositions(int x, int z) {
        List<Position> possiblePositions = new ArrayList<Position>();
        for (Position position : positionsToSelect) {
            if ((position.getX() == x) && (position.getZ() == z)) {
                possiblePositions.add(position);
            }
        }
        if (possiblePositions.size() != 1) {
            Collections.sort(possiblePositions);
        }
        return possiblePositions;
    }

    private Position getClosestPosition(Position cursor) {
        List<Position> possiblePositions = getPossiblePositions(cursor.getX(), cursor.getZ());
        for (int delta = 0; delta < battlefield.getHeight() - 1; delta++) {
            Position deltaUp = new Position(cursor.getX(), cursor.getY() + delta, cursor.getZ());
            Position deltaDown = new Position(cursor.getX(), cursor.getY() - delta, cursor.getZ());
            if (positionsToSelect.contains(deltaUp)) {
                return deltaUp;
            }
            if (positionsToSelect.contains(deltaDown)) {
                return deltaDown;
            }
        }
        return cursor;
    }

    public enum BattlePhases {
        PLACING,
    }

}
