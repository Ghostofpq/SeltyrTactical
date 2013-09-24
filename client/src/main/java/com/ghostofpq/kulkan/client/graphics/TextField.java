package com.ghostofpq.kulkan.client.graphics;

public class TextField extends HUDElement {

    private String content;
    private int maxLength;

    @Override
    public boolean isClicked(int mouseX, int mouseY) {
        boolean result = false;

        if ((mouseX >= posX && mouseX <= posX + length) && (mouseY >= posY && mouseY <= posY + height)) {
            result = true;
        }
        onClick();
        return result;
    }

    @Override
    public void draw() {
    }

    @Override
    public void onClick() {
    }

    public String getContent() {
        return content;
    }
}
