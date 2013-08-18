package com.ghostofpq.seltyrtactical.commons;

import java.io.Serializable;

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


    public void plusX(float x) {
        this.setX(this.getX() + x);
    }

    public void plusY(float y) {
        this.setY(this.getY() + y);
    }

    public void plusZ(float z) {
        this.setZ(this.getZ() + z);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("X: ");
        sb.append(this.getX());
        sb.append(" Y: ");
        sb.append(this.getY());
        sb.append(" Z: ");
        sb.append(this.getZ());
        return sb.toString();
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

    /**
     * Getters and Setters
     */

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
