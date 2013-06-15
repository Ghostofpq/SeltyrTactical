package com.ghostofpq.seltyrtactical.main.graphics;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 15/06/13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */

@Getter
@Setter
public class Position implements Serializable, Comparable<Position> {
    private static final long serialVersionUID = 8427348252935902307L;

    private int x;
    private int y;
    private int z;

    public Position(int x, int y, int z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }

    @Override
    public int compareTo(Position other) {
        // Priority on Z axis
        if (this.getZ() < other.getZ()) {
            return -1;
        } else if (this.getZ() > other.getZ()) {
            return 1;
        } else {
            // Then Y axis
            if (this.getY() < other.getY()) {
                return -1;
            } else if (this.getY() > other.getY()) {
                return 1;
            } else {
                // Finally X axis
                if (this.getX() < other.getX()) {
                    return -1;
                } else if (this.getX() > other.getX()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }


}
