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
public class PositionAbsolute implements Serializable, Comparable<PositionAbsolute> {
    private static final long serialVersionUID = 8427348252935902307L;
    private float x;  // Length
    private float y;  // Height
    private float z;  // Depth

    public PositionAbsolute(float x, float y, float z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }

    public PositionAbsolute plus(float x, float y, float z) {
        return new PositionAbsolute(this.getX() + x, this.getY() + y, this.getZ() + z);
    }

    @Override
    public int compareTo(PositionAbsolute other) {
        // Priority on Y axis
        if (this.getY() < other.getY()) {
            return -1;
        } else if (this.getY() > other.getY()) {
            return 1;
        } else {
            // Then X axis
            if (this.getX() < other.getX()) {
                return -1;
            } else if (this.getX() > other.getX()) {
                return 1;
            } else {
                // Finally Z axis
                if (this.getZ() < other.getZ()) {
                    return -1;
                } else if (this.getZ() > other.getZ()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
}
