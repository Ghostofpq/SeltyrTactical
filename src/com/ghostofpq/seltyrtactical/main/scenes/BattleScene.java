package com.ghostofpq.seltyrtactical.main.scenes;

import com.ghostofpq.seltyrtactical.main.entities.battlefield.Battlefield;
import com.ghostofpq.seltyrtactical.main.entities.battlefield.BattlefieldElement;
import com.ghostofpq.seltyrtactical.main.graphics.Cube;
import com.ghostofpq.seltyrtactical.main.graphics.PointOfView;
import com.ghostofpq.seltyrtactical.main.utils.GraphicsManager;
import com.ghostofpq.seltyrtactical.main.utils.HighlightColor;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.input.Keyboard;

import java.util.Collections;
import java.util.List;

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
    List<Cube> todraw;
    private boolean graphicManagerIsWorking;
    private int currentTileOnFocusX;
    private int currentTileOnFocusY;
    private int currentTileOnFocusZ;

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
        Battlefield battlefield = new Battlefield(5, 5, 5);

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
        battlefield.addBattlefieldElement(3, 0, 2, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(4, 0, 2, BattlefieldElement.BattlefieldElementType.BLOC);

        battlefield.addBattlefieldElement(0, 0, 3, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(1, 0, 3, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(2, 0, 3, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(3, 0, 3, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(4, 0, 3, BattlefieldElement.BattlefieldElementType.BLOC);

        battlefield.addBattlefieldElement(0, 0, 4, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(1, 0, 4, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(2, 0, 4, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(3, 0, 4, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(4, 0, 4, BattlefieldElement.BattlefieldElementType.BLOC);

        todraw = battlefield.toDrawableList();
        Collections.sort(todraw);

        todraw.get(5).setHighlight(HighlightColor.BLUE);
        todraw.get(12).setHighlight(HighlightColor.RED);
        todraw.get(17).setHighlight(HighlightColor.GREEN);

        GraphicsManager.getInstance().setupLigths();
        GraphicsManager.getInstance().ready3D();


        currentTileOnFocusX = 0;
        currentTileOnFocusY = 0;
        currentTileOnFocusZ = 0;

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
                    System.out.println("Key Pressed");
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
                }
            }
        }

    }

    private void render3D() {
        GraphicsManager.getInstance().make3D();
        for (Cube cube : todraw) {
            cube.draw(GraphicsManager.getInstance().getCurrentPointOfView());
        }
    }

    private void render2D() {
        GraphicsManager.getInstance().make2D();
        // hud.render();
    }

    public enum BattlePhases {
        PLACING,
    }


}
