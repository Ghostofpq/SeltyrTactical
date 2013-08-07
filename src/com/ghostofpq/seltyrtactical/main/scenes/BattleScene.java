package com.ghostofpq.seltyrtactical.main.scenes;

import lombok.extern.slf4j.Slf4j;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import com.ghostofpq.seltyrtactical.main.entities.GameCharacter;
import com.ghostofpq.seltyrtactical.main.entities.Player;
import com.ghostofpq.seltyrtactical.main.entities.battlefield.Battlefield;
import com.ghostofpq.seltyrtactical.main.graphics.CharacterRender;
import com.ghostofpq.seltyrtactical.main.graphics.Cube;
import com.ghostofpq.seltyrtactical.main.graphics.PointOfView;
import com.ghostofpq.seltyrtactical.main.graphics.Position;
import com.ghostofpq.seltyrtactical.main.utils.GraphicsManager;
import com.ghostofpq.seltyrtactical.main.utils.HighlightColor;
import com.ghostofpq.seltyrtactical.main.utils.SaveManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class BattleScene implements Scene {
    private static volatile BattleScene instance = null;
    private Map<Position, Cube> todraw;
    private List<Position> positionsToDraw;
    private List<Position> positionsToSelect;
    private boolean graphicManagerIsWorking;
    private Position cursor;
    private Battlefield battlefield;
    private BattleSceneState currentState;
    private List<Player> players;
    private Player currentPlayer;
    private GameCharacter currentGameCharacter;
    private GameCharacter targetGameCharacter;
    private CharacterRender characterRenderLeft;
    private CharacterRender characterRenderRight;

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
        currentState = BattleSceneState.DEPLOY;
        battlefield = SaveManager.getInstance().loadMap("mapTest1");

        todraw = battlefield.toDrawableList();
        updatePositionLists();
        cursor = new Position(4, 0, 4);
        todraw.get(cursor).setHighlight(HighlightColor.BLUE);

        GraphicsManager.getInstance().setupLights();
        GraphicsManager.getInstance().ready3D();
        GraphicsManager.getInstance().requestCenterPosition(cursor);
        graphicManagerIsWorking = true;

    }

    public void placeCharacter() {
        currentGameCharacter.setPositionOnMap(new Position(cursor));

        int indexOfPlayer = players.indexOf(currentPlayer);
        int indexOfChar = currentPlayer.getTeam().getTeam().indexOf(currentGameCharacter);

        if (indexOfChar == currentPlayer.getTeam().getTeam().size() - 1) {
            if (indexOfPlayer == players.size() - 1) {
                currentState = BattleSceneState.FIGHT;
                cleanHighlightDeploymentZone();
            } else {
                cleanHighlightDeploymentZone();
                currentPlayer = players.get(indexOfPlayer + 1);
                currentGameCharacter = currentPlayer.getTeam().getTeam().get(0);
                characterRenderLeft = new CharacterRender(0, 0, 300, 100, 2, currentGameCharacter);
                highlightDeploymentZone();
            }
        } else {
            currentGameCharacter = currentPlayer.getTeam().getTeam().get(indexOfChar + 1);
            characterRenderLeft = new CharacterRender(0, 0, 300, 100, 2, currentGameCharacter);
        }
        battlefield.getDeploymentZones().get(indexOfPlayer).remove(cursor);
    }

    public void setPlayer(List<Player> players) {
        this.players = players;
        currentPlayer = players.get(0);
        currentGameCharacter = currentPlayer.getTeam().getTeam().get(0);
        characterRenderLeft = new CharacterRender(0, 0, 300, 100, 2, currentGameCharacter);
        highlightDeploymentZone();
    }

    private void highlightDeploymentZone() {
        int indexOfPlayer = players.indexOf(currentPlayer);
        List<Position> deploymentZonePlayer = battlefield.getDeploymentZones().get(indexOfPlayer);
        for (Position deploymentPosition : deploymentZonePlayer) {
            todraw.get(deploymentPosition).setHighlight(HighlightColor.GREEN);
        }
    }

    private void cleanHighlightDeploymentZone() {
        int indexOfPlayer = players.indexOf(currentPlayer);
        List<Position> deploymentZonePlayer = battlefield.getDeploymentZones().get(indexOfPlayer);
        for (Position deploymentPosition : deploymentZonePlayer) {
            if (todraw.get(deploymentPosition).getHighlight().equals(HighlightColor.GREEN)) {
                todraw.get(deploymentPosition).setHighlight(HighlightColor.NONE);
            }
        }
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
                        GraphicsManager.getInstance().requestCenterPosition(cursor);
                        graphicManagerIsWorking = true;
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
                        GraphicsManager.getInstance().requestCenterPosition(cursor);
                        graphicManagerIsWorking = true;
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
                        GraphicsManager.getInstance().requestCenterPosition(cursor);
                        graphicManagerIsWorking = true;
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
                        GraphicsManager.getInstance().requestCenterPosition(cursor);
                        graphicManagerIsWorking = true;
                    }

                    if (Keyboard.getEventKey() == Keyboard.KEY_TAB) {
                        cursorTab();
                        GraphicsManager.getInstance().requestCenterPosition(cursor);
                        graphicManagerIsWorking = true;
                    }


                    if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
                        System.out.println("pos: " + cursor.getX() + "/" + cursor.getY() + "/" + cursor.getZ());
                        placeCharacter();
                    }
                }
            }
        } else {
            while (Keyboard.next()) {
                // DO NOTHING
            }
        }
    }

    private void render3D() {
        GraphicsManager.getInstance().make3D();
        for (Position position : positionsToDraw) {
            todraw.get(position).draw(GraphicsManager.getInstance().getCurrentPointOfView());
        }
        for (Player player : players) {
            for (GameCharacter chara : player.getTeam().getTeam()) {
                if (chara.getPositionOnMap() != null) {
                    chara.render();
                }
            }
        }
    }

    private void render2D() {
        GraphicsManager.getInstance().make2D();
        characterRenderLeft.render(Color.white);
        // hud.render();
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

    private void cursorUp() {

        if (cursor.getZ() != 0) {
            todraw.get(cursor).setHighlight(HighlightColor.NONE);

            if (currentState.equals(BattleSceneState.DEPLOY)) {
                int indexOfPlayer = players.indexOf(currentPlayer);
                if (battlefield.getDeploymentZones().get(indexOfPlayer).contains(cursor)) {
                    todraw.get(cursor).setHighlight(HighlightColor.GREEN);
                }
            }

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

            if (currentState.equals(BattleSceneState.DEPLOY)) {
                int indexOfPlayer = players.indexOf(currentPlayer);
                if (battlefield.getDeploymentZones().get(indexOfPlayer).contains(cursor)) {
                    todraw.get(cursor).setHighlight(HighlightColor.GREEN);
                }
            }

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

            if (currentState.equals(BattleSceneState.DEPLOY)) {
                int indexOfPlayer = players.indexOf(currentPlayer);
                if (battlefield.getDeploymentZones().get(indexOfPlayer).contains(cursor)) {
                    todraw.get(cursor).setHighlight(HighlightColor.GREEN);
                }
            }

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

            if (currentState.equals(BattleSceneState.DEPLOY)) {
                int indexOfPlayer = players.indexOf(currentPlayer);
                if (battlefield.getDeploymentZones().get(indexOfPlayer).contains(cursor)) {
                    todraw.get(cursor).setHighlight(HighlightColor.GREEN);
                }
            }

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
            if (possiblePositions.contains(deltaUp)) {
                return deltaUp;
            }
            if (possiblePositions.contains(deltaDown)) {
                return deltaDown;
            }
        }
        return cursor;
    }

    private enum BattleSceneState {
        DEPLOY, FIGHT
    }
}
