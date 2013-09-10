package com.ghostofpq.seltyrtactical.game.scenes;

import com.ghostofpq.seltyrtactical.commons.Node;
import com.ghostofpq.seltyrtactical.commons.Position;
import com.ghostofpq.seltyrtactical.commons.PositionAbsolute;
import com.ghostofpq.seltyrtactical.commons.Tree;
import com.ghostofpq.seltyrtactical.entities.battlefield.Battlefield;
import com.ghostofpq.seltyrtactical.entities.battlefield.BattlefieldElement;
import com.ghostofpq.seltyrtactical.entities.character.GameCharacter;
import com.ghostofpq.seltyrtactical.entities.character.Player;
import com.ghostofpq.seltyrtactical.game.graphics.*;
import com.ghostofpq.seltyrtactical.game.utils.GraphicsManager;
import com.ghostofpq.seltyrtactical.game.utils.HighlightColor;
import com.ghostofpq.seltyrtactical.game.utils.SaveManager;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import java.util.*;

@Slf4j
public class BattleScene implements Scene {
    private static volatile BattleScene instance = null;
    private Map<Position, Cube> todraw;
    private List<DrawableObject> toDrawList;
    private List<Position> positionsToDraw;
    private List<Position> positionsToSelect;
    private boolean engineIsBusy;
    private Position cursor;
    private Battlefield battlefield;
    private BattleSceneState currentState;
    private List<Player> players;
    private List<GameCharacterRepresentation> gameCharacterRepresentations;
    private int gameCharacterRepresentationsIndex;
    private Player currentPlayer;
    private GameCharacter currentGameCharacter;
    private GameCharacterRepresentation currentGameCharacterRepresentation;
    private GameCharacterRepresentation targetGameCharacterRepresentation;
    private CharacterRender characterRenderLeft;
    private CharacterRender characterRenderRight;
    private MenuSelectAction menuSelectAction;
    private PointOfView currentPointOfView;
    private List<Position> possiblePositionsToMove;
    private Tree<Position> possiblePositionsToMoveTree;
    private PositionAbsolute southPointOfView;
    private PositionAbsolute northPointOfView;
    private PositionAbsolute eastPointOfView;
    private PositionAbsolute westPointOfView;

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
        setEngineIsBusy(true);
        currentState = BattleSceneState.DEPLOY;
        battlefield = SaveManager.getInstance().loadMap("mapTest1");
        currentPointOfView = GraphicsManager.getInstance().getCurrentPointOfView();
        todraw = toDrawableList(battlefield);
        toDrawList = new ArrayList<DrawableObject>();
        toDrawList.addAll(todraw.values());

        southPointOfView = new PositionAbsolute(battlefield.getLength(), battlefield.getHeight(), battlefield.getDepth());
        northPointOfView = new PositionAbsolute(0, battlefield.getHeight(), 0);
        eastPointOfView = new PositionAbsolute(battlefield.getLength(), battlefield.getHeight(), 0);
        westPointOfView = new PositionAbsolute(0, battlefield.getHeight(), battlefield.getDepth());

        possiblePositionsToMove = new ArrayList<Position>();

        gameCharacterRepresentations = new ArrayList<GameCharacterRepresentation>();

        List<String> options = new ArrayList<String>();
        options.add("Move");
        options.add("Attack");
        options.add("End Turn");
        menuSelectAction = new MenuSelectAction(300, 0, 100, 100, 2);

        sortToDrawList();

        updatePositionLists();
        cursor = new Position(4, 0, 4);
        todraw.get(cursor).setHighlight(HighlightColor.BLUE);
        GraphicsManager.getInstance().setupLights();
        GraphicsManager.getInstance().ready3D();
        GraphicsManager.getInstance().requestCenterPosition(cursor);

    }

    public void placeCharacter() {
        int indexOfPlayer = players.indexOf(currentPlayer);
        int indexOfChar = currentPlayer.getTeam().getTeam().indexOf(currentGameCharacter);

        if (battlefield.getDeploymentZones().get(indexOfPlayer).contains(cursor)) {
            Position position = new Position(cursor);
            position.plusY(1);
            GameCharacterRepresentation gameCharacterRepresentation = new GameCharacterRepresentation(currentGameCharacter, position);
            //gameCharacterRepresentation.setPositionsToGo(battlefield.getPath(position, new Position(4, 1, 4)));
            gameCharacterRepresentations.add(gameCharacterRepresentation);
            toDrawList.add(gameCharacterRepresentation);
            sortToDrawList();

            if (indexOfChar == currentPlayer.getTeam().getTeam().size() - 1) {
                if (indexOfPlayer == players.size() - 1) {
                    currentState = BattleSceneState.PENDING;
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
    }

    public void moveCharacter() {
        if (cursor.equals(currentGameCharacterRepresentation.getFootPosition())) {
            currentState = BattleSceneState.ACTION;
            clearHighlightPossibleMovement();
        } else if (possiblePositionsToMoveTree.contains(cursor)) {
            List<Node<Position>> nodeList = possiblePositionsToMoveTree.find(cursor);
            if (!nodeList.isEmpty()) {
                List<Position> path = nodeList.get(0).getPathFromTop();
                currentGameCharacterRepresentation.setPositionsToGo(path);
            }
            clearHighlightPossibleMovement();
            currentGameCharacterRepresentation.setHasMoved(true);
            menuSelectAction.setHasMoved();
        } else {
            todraw.get(cursor).setHighlight(HighlightColor.NONE);
            cursor = new Position(currentGameCharacterRepresentation.getFootPosition());
            todraw.get(cursor).setHighlight(HighlightColor.BLUE);
            GraphicsManager.getInstance().requestCenterPosition(cursor);
            currentState = BattleSceneState.ACTION;
            clearHighlightPossibleMovement();
        }
    }

    public void getCurrentCharacter() {
        currentGameCharacterRepresentation = null;
        while (null == currentGameCharacterRepresentation) {
            log.debug("{}", gameCharacterRepresentationsIndex);
            GameCharacterRepresentation gameCharacterRepresentation = gameCharacterRepresentations.get(gameCharacterRepresentationsIndex);
            if (gameCharacterRepresentation.tickHourglass()) {
                currentGameCharacterRepresentation = gameCharacterRepresentation;
                characterRenderLeft = new CharacterRender(0, 0, 300, 100, 2, currentGameCharacterRepresentation.getCharacter());

                todraw.get(cursor).setHighlight(HighlightColor.NONE);

                cursor = currentGameCharacterRepresentation.getPosition().plusYNew(-1);
                todraw.get(cursor).setHighlight(HighlightColor.BLUE);

                GraphicsManager.getInstance().requestCenterPosition(cursor);


                setEngineIsBusy(true);
                currentState = BattleSceneState.ACTION;
                incrementGameCharacterRepresentationsIndex();
            } else {
                incrementGameCharacterRepresentationsIndex();
            }
        }
    }

    public void highlightPossibleMovement() {
        Position characterPosition = currentGameCharacterRepresentation.getFootPosition();
        //log.debug("FootPosition : {}", characterPosition.toString());

        possiblePositionsToMoveTree = battlefield.getPositionTree(characterPosition,
                3,
                2,
                1);

        for (GameCharacterRepresentation gameCharacterRepresentation : gameCharacterRepresentations) {
            log.debug("remove : {}", gameCharacterRepresentation.getFootPosition().toString());
            possiblePositionsToMoveTree.remove(gameCharacterRepresentation.getFootPosition());
        }


        possiblePositionsToMove = possiblePositionsToMoveTree.getAllElements();
        possiblePositionsToMove.remove(currentGameCharacterRepresentation.getFootPosition());

        for (Position position : possiblePositionsToMove) {
            log.debug("highlight green : {}", position.toString());
            todraw.get(position).setHighlight(HighlightColor.GREEN);
        }
        todraw.get(cursor).setHighlight(HighlightColor.BLUE);
    }

    public void clearHighlightPossibleMovement() {
        for (Position position : possiblePositionsToMove) {
            //log.debug("clear highlight green : {}", position.toString());
            todraw.get(position).setHighlight(HighlightColor.NONE);
        }
        todraw.get(cursor).setHighlight(HighlightColor.BLUE);
    }

    private void incrementGameCharacterRepresentationsIndex() {
        if (gameCharacterRepresentationsIndex >= gameCharacterRepresentations.size() - 1) {
            gameCharacterRepresentationsIndex = 0;
        } else {
            gameCharacterRepresentationsIndex++;
        }
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
    public void update(long deltaTime) {
        boolean busy = false;

        for (DrawableObject drawableObject : toDrawList) {
            if (drawableObject.isMoving()) {
                busy = true;
                //log.debug("Object has moved");
                sortToDrawList();
                break;
            }
        }
        if (pointOfViewHasChanged()) {
            //log.debug("Point of view has changed");
            sortToDrawList();
        }
        if (GraphicsManager.getInstance().update3DMovement()) {
            busy = true;
        }

        setEngineIsBusy(busy);

        if (currentState.equals(BattleSceneState.PENDING)) {
            getCurrentCharacter();
        }

        for (DrawableObject drawableObject : toDrawList) {
            drawableObject.update(deltaTime);
        }
    }

    @Override
    public void render() {
        render3D();
        render2D();
    }

    @Override
    public void manageInput() {
        if (!engineIsBusy()) {
            while (Keyboard.next()) {
                if (Keyboard.getEventKeyState()) {
                    if (Keyboard.getEventKey() == Keyboard.KEY_O) {
                        GraphicsManager.getInstance().requestPointOfView(PointOfView.NORTH);
                        setEngineIsBusy(true);
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_I) {
                        GraphicsManager.getInstance().requestPointOfView(PointOfView.WEST);
                        setEngineIsBusy(true);
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_L) {
                        GraphicsManager.getInstance().requestPointOfView(PointOfView.EAST);
                        setEngineIsBusy(true);
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_K) {
                        GraphicsManager.getInstance().requestPointOfView(PointOfView.SOUTH);
                        setEngineIsBusy(true);
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_P) {
                        GraphicsManager.getInstance().zoomIn();
                    }
                    if (Keyboard.getEventKey() == Keyboard.KEY_M) {
                        GraphicsManager.getInstance().zoomOut();
                    }

                    if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
                        if (currentState.equals(BattleSceneState.ATTACK)
                                || currentState.equals(BattleSceneState.DEPLOY)
                                || currentState.equals(BattleSceneState.MOVE)) {

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
                            setEngineIsBusy(true);
                        } else if (currentState.equals(BattleSceneState.ACTION)) {
                            menuSelectAction.decrementOptionsIndex();
                        }
                    }

                    if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
                        if (currentState.equals(BattleSceneState.ATTACK)
                                || currentState.equals(BattleSceneState.DEPLOY)
                                || currentState.equals(BattleSceneState.MOVE)) {
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
                            setEngineIsBusy(true);
                        } else if (currentState.equals(BattleSceneState.ACTION)) {
                            menuSelectAction.incrementOptionsIndex();
                        }
                    }

                    if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                        if (currentState.equals(BattleSceneState.ATTACK)
                                || currentState.equals(BattleSceneState.DEPLOY)
                                || currentState.equals(BattleSceneState.MOVE)) {

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
                            setEngineIsBusy(true);
                        } else if (currentState.equals(BattleSceneState.ACTION)) {
                            menuSelectAction.decrementOptionsIndex();
                        }
                    }

                    if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                        if (currentState.equals(BattleSceneState.ATTACK)
                                || currentState.equals(BattleSceneState.DEPLOY)
                                || currentState.equals(BattleSceneState.MOVE)) {

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
                            setEngineIsBusy(true);
                        } else if (currentState.equals(BattleSceneState.ACTION)) {
                            menuSelectAction.incrementOptionsIndex();
                        }
                    }

                    if (Keyboard.getEventKey() == Keyboard.KEY_TAB) {
                        cursorTab();
                        GraphicsManager.getInstance().requestCenterPosition(cursor);
                        setEngineIsBusy(true);
                    }


                    if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
                        switch (currentState) {
                            case DEPLOY:
                                log.debug("pos: {}/{}/{}", cursor.getX(), cursor.getY(), cursor.getZ());
                                placeCharacter();
                                break;
                            case ACTION:
                                if (menuSelectAction.getSelectedOption().equals(MenuSelectAction.MenuSelectActions.MOVE)) {
                                    currentState = BattleSceneState.MOVE;
                                    highlightPossibleMovement();
                                } else if (menuSelectAction.getSelectedOption().equals(MenuSelectAction.MenuSelectActions.ATTACK)) {
                                    currentState = BattleSceneState.ATTACK;
                                } else if (menuSelectAction.getSelectedOption().equals(MenuSelectAction.MenuSelectActions.END_TURN)) {
                                    currentState = BattleSceneState.PENDING;
                                    menuSelectAction.reinitMenu();
                                }
                                break;

                            case MOVE:
                                moveCharacter();
                                currentState = BattleSceneState.ACTION;
                                break;
                        }
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
        for (int i = 0; i < toDrawList.size(); i++) {
            toDrawList.get(i).draw();
        }
        /**
         for (Position position : positionsToDraw) {
         todraw.get(position).draw();
         }  */

    }

    private void render2D() {
        GraphicsManager.getInstance().make2D();
        characterRenderLeft.render(Color.white);
        if (currentState.equals(BattleSceneState.ACTION)) {
            menuSelectAction.render(Color.white);
        }
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

    /**
     * CURSOR
     */

    private void cursorUp() {

        if (cursor.getZ() != 0) {
            todraw.get(cursor).setHighlight(HighlightColor.NONE);

            if (currentState.equals(BattleSceneState.DEPLOY)) {
                int indexOfPlayer = players.indexOf(currentPlayer);
                if (battlefield.getDeploymentZones().get(indexOfPlayer).contains(cursor)) {
                    todraw.get(cursor).setHighlight(HighlightColor.GREEN);
                }
            }
            if (currentState.equals(BattleSceneState.MOVE)) {
                if (possiblePositionsToMove.contains(cursor)) {
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

            if (currentState.equals(BattleSceneState.MOVE)) {
                if (possiblePositionsToMove.contains(cursor)) {
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
            if (currentState.equals(BattleSceneState.MOVE)) {
                if (possiblePositionsToMove.contains(cursor)) {
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
            if (currentState.equals(BattleSceneState.MOVE)) {
                if (possiblePositionsToMove.contains(cursor)) {
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

    private Map<Position, Cube> toDrawableList(Battlefield battlefield) {
        Map<Position, Cube> toDraw = new HashMap<Position, Cube>();
        for (Position position : battlefield.getBattlefieldElementMap().keySet()) {
            BattlefieldElement element = battlefield.getBattlefieldElementMap().get(position);
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

    private int comparePositionForNorthPointOfView(PositionAbsolute thisPosition, PositionAbsolute otherPosition) {
        int res;
        if (thisPosition.distanceWith(northPointOfView) > otherPosition.distanceWith(northPointOfView)) {
            res = -1;
        } else if (thisPosition.distanceWith(northPointOfView) < otherPosition.distanceWith(northPointOfView)) {
            res = 1;
        } else {
            res = 0;
        }
        return res;
    }

    private int comparePositionForSouthPointOfView(PositionAbsolute thisPosition, PositionAbsolute otherPosition) {
        int res;
        if (thisPosition.distanceWith(southPointOfView) > otherPosition.distanceWith(southPointOfView)) {
            res = -1;
        } else if (thisPosition.distanceWith(southPointOfView) < otherPosition.distanceWith(southPointOfView)) {
            res = 1;
        } else {
            res = 0;
        }
        return res;
    }

    private int comparePositionForEastPointOfView(PositionAbsolute thisPosition, PositionAbsolute otherPosition) {
        int res;
        if (thisPosition.distanceWith(eastPointOfView) > otherPosition.distanceWith(eastPointOfView)) {
            res = -1;
        } else if (thisPosition.distanceWith(eastPointOfView) < otherPosition.distanceWith(eastPointOfView)) {
            res = 1;
        } else {
            res = 0;
        }
        return res;
    }

    private int comparePositionForWestPointOfView(PositionAbsolute thisPosition, PositionAbsolute otherPosition) {
        int res;
        if (thisPosition.distanceWith(westPointOfView) > otherPosition.distanceWith(westPointOfView)) {
            res = -1;
        } else if (thisPosition.distanceWith(westPointOfView) < otherPosition.distanceWith(westPointOfView)) {
            res = 1;
        } else {
            res = 0;
        }
        return res;
    }

    private int comparePosition(PositionAbsolute thisPosition, PositionAbsolute otherPosition) {
        int res = 0;
        switch (GraphicsManager.getInstance().getCurrentPointOfView()) {
            case EAST:
                res = comparePositionForEastPointOfView(thisPosition, otherPosition);
                break;
            case NORTH:
                res = comparePositionForNorthPointOfView(thisPosition, otherPosition);
                break;
            case SOUTH:
                res = comparePositionForSouthPointOfView(thisPosition, otherPosition);
                break;
            case WEST:
                res = comparePositionForWestPointOfView(thisPosition, otherPosition);
                break;
        }

        return res;
    }

    private void sortToDrawList() {
        DrawableObject temp;
        if (toDrawList.size() > 1) {
            for (int x = 0; x < toDrawList.size(); x++) {
                for (int i = 0; i < toDrawList.size() - x - 1; i++) {
                    int compare = comparePosition(toDrawList.get(i).getPositionToCompare(currentPointOfView), toDrawList.get(i + 1).getPositionToCompare(currentPointOfView));
                    if (compare > 0) {
                        temp = toDrawList.get(i);
                        toDrawList.set(i, toDrawList.get(i + 1));
                        toDrawList.set(i + 1, temp);
                    }
                }
            }
        }

    }

    private boolean pointOfViewHasChanged() {
        boolean result = false;
        if (!currentPointOfView.equals(GraphicsManager.getInstance().getCurrentPointOfView())) {
            currentPointOfView = GraphicsManager.getInstance().getCurrentPointOfView();
            result = true;
        }
        return result;
    }

    public boolean engineIsBusy() {
        return engineIsBusy;
    }

    public void setEngineIsBusy(boolean engineIsBusy) {
        this.engineIsBusy = engineIsBusy;
    }

    private enum BattleSceneState {
        DEPLOY, PENDING, ACTION, MOVE, ATTACK
    }
}
