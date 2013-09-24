package com.ghostofpq.kulkan.client.graphics;

public class PasswordField extends TextField {

    public PasswordField(int posX, int posY, int length, int height, int maxLength) {
        super(posX, posY, length, height, maxLength);
    }

    public String getContentToPrint() {
        return content.replaceAll("[\\x21-\\x7E]", "*");
    }
}
