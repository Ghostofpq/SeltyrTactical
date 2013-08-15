package com.ghostofpq.seltyrtactical.game.graphics;

import com.ghostofpq.seltyrtactical.commons.Position;

public abstract class DrawableObject implements Comparable<DrawableObject> {

    protected Position position;

    public abstract void draw();

    @Override
    public int compareTo(DrawableObject other) {
        return this.getPosition().compareTo(other.getPosition());
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
