package com.ghostofpq.seltyrtactical.game.main;

import com.ghostofpq.seltyrtactical.commons.Position;
import com.ghostofpq.seltyrtactical.entities.battlefield.Battlefield;
import com.ghostofpq.seltyrtactical.entities.battlefield.BattlefieldElement;
import com.ghostofpq.seltyrtactical.game.utils.SaveManager;

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
            battlefield.addDeployementZone(0, position);
            Position position2 = new Position(i, 0, depth - 1);
            battlefield.addDeployementZone(1, position2);
        }


        SaveManager saveManager = SaveManager.getInstance();
        saveManager.saveMap(battlefield, "mapTest1");

    }
}
