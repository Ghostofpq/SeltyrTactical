package com.ghostofpq.kulkan.client.graphics;

public abstract class HUDElement {
    protected boolean hasFocus;
    protected int posX;
    protected int posY;
    protected int length;
    protected int height;

    public abstract void draw();

    public abstract void onClick();

    public abstract void onKeyPressed(int keyEnvent);

    public abstract boolean isClicked(int mouseX, int mouseY);

    /*
    GETTERS & SETTERS
     */

    public boolean hasFocus() {
        return hasFocus;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }
}
