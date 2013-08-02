package com.ghostofpq.seltyrtactical.main;

import com.ghostofpq.seltyrtactical.main.entities.battlefield.Battlefield;
import com.ghostofpq.seltyrtactical.main.entities.battlefield.BattlefieldElement;
import com.ghostofpq.seltyrtactical.main.graphics.Position;
import com.ghostofpq.seltyrtactical.main.utils.SaveManager;

/**
 * Created with IntelliJ IDEA.
 * User: VMPX4526
 * Date: 02/08/13
 * Time: 13:43
 * To change this template use File | Settings | File Templates.
 */
public class MapCreator {
    public static void main(String[] argv) {

        int length = 10;
        int height = 3;
        int depth = 10;

        Battlefield battlefield = new Battlefield(length, height, depth, 2);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < depth; j++) {
                battlefield.addBattlefieldElement(i, 0, j, BattlefieldElement.BattlefieldElementType.BLOC);
            }
        }

        for (int i = 0; i < length; i++) {
            Position position = new Position(i, 0, 0);
            battlefield.addDeployementZone(1, position);
            Position position2 = new Position(i, 0, depth - 1);
            battlefield.addDeployementZone(2, position2);
        }


        SaveManager saveManager = SaveManager.getInstance();
        saveManager.saveMap(battlefield, "mapTest1");

    }
}
