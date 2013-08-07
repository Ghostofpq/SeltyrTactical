package java.com.ghostofpq.seltyrtactical.main.graphics;

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
    private int x;  // Length
    private int y;  // Height
    private int z;  // Depth

    public Position(int x, int y, int z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }

    public Position(Position position) {
        this.setX(position.getX());
        this.setY(position.getY());
        this.setZ(position.getZ());
    }

    public PositionAbsolute toAbsolute() {
        return new PositionAbsolute((float) x, (float) y, (float) z);
    }

    @Override
    public int compareTo(Position other) {
        // Priority on Z axis
        if (this.getZ() < other.getZ()) {
            return -1;
        } else if (this.getZ() > other.getZ()) {
            return 1;
        } else {
            // Then X axis
            if (this.getX() < other.getX()) {
                return -1;
            } else if (this.getX() > other.getX()) {
                return 1;
            } else {
                // Finally Y axis
                if (this.getY() < other.getY()) {
                    return -1;
                } else if (this.getY() > other.getY()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        if (y != position.y) return false;
        if (z != position.z) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
