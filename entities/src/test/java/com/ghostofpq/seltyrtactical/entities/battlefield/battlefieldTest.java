package com.ghostofpq.seltyrtactical.entities.battlefield;

import com.ghostofpq.seltyrtactical.commons.Node;
import com.ghostofpq.seltyrtactical.commons.Position;
import com.ghostofpq.seltyrtactical.commons.Tree;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@Slf4j
@RunWith(JUnit4.class)
public class battlefieldTest {

    @Test
    public void getPositionTreeTest() {
        int length = 10;
        int height = 5;
        int depth = 10;

        Battlefield battlefield = new Battlefield(length, height, depth, 2);

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < depth; j++) {
                battlefield.addBattlefieldElement(i, 0, j, BattlefieldElement.BattlefieldElementType.BLOC);
            }
        }

        Position originPoint = new Position(1, 0, 1);

        int dist = 2;
        int heightLimit = 2;
        int jumpLimit = 1;

        Tree<Position> result = battlefield.getPositionTree(originPoint, dist, heightLimit, jumpLimit);

        log.debug("     {} : {}", result.getRoot().getData().toString(), result.getRoot().getDistanceFromTop());
        for (Node<Position> child : result.getRoot().getChildren()) {
            log.debug("   ->{} : {}", child.getData().toString(), child.getDistanceFromTop());
            for (Node<Position> child2 : child.getChildren()) {
                log.debug("  -->{} : {}", child2.getData().toString(), child2.getDistanceFromTop());
                for (Node<Position> child3 : child2.getChildren()) {
                    log.debug(" --->{} : {}", child3.getData().toString(), child3.getDistanceFromTop());
                    for (Node<Position> child4 : child3.getChildren()) {
                        log.debug("---->{} : {}", child4.getData().toString(), child4.getDistanceFromTop());
                    }
                }
            }
        }
    }
}