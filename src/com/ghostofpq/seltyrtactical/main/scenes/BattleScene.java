package com.ghostofpq.seltyrtactical.main.scenes;

import com.ghostofpq.seltyrtactical.main.entities.battlefield.Battlefield;
import com.ghostofpq.seltyrtactical.main.entities.battlefield.BattlefieldElement;
import com.ghostofpq.seltyrtactical.main.graphics.Cube;
import com.ghostofpq.seltyrtactical.main.graphics.PointOfView;
import com.ghostofpq.seltyrtactical.main.graphics.Position;
import com.ghostofpq.seltyrtactical.main.utils.GraphicsManager;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vmpx4526
 * Date: 04/07/13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class BattleScene implements Scene {
    private static volatile BattleScene instance = null;
    List<Cube> todraw;

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
        Battlefield battlefield=new Battlefield(10,10,10);
        battlefield.addBattlefieldElement(0, 0, 0, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(1, 0, 0, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(0, 2, 0, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(0, 0, 1, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(2, 0, 0, BattlefieldElement.BattlefieldElementType.BLOC);
        battlefield.addBattlefieldElement(1, 1, 0, BattlefieldElement.BattlefieldElementType.BLOC);
        todraw = battlefield.toDrawableList();
        Collections.sort(todraw);

        GraphicsManager.getInstance().setupLigths();
        GraphicsManager.getInstance().ready3D();
    }

    public void addToDrawingList(Cube cube) {
        todraw.add(cube);
        Collections.sort(todraw);
    }

    @Override
    public void update() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render() {
        render3D();
        render2D();
    }

    @Override
    public void manageInput() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    private void render3D() {
        // ready3D();
        GraphicsManager.getInstance().make3D();
        for (Cube cube : todraw) {
            cube.Draw(PointOfView.SOUTH);
        }
    }

    private void render2D() {
        // ready2D();
        GraphicsManager.getInstance().make2D();
        // hud.render();
    }
}
