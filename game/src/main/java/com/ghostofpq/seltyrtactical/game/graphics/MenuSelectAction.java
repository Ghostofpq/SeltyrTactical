package com.ghostofpq.seltyrtactical.game.graphics;


import com.ghostofpq.seltyrtactical.game.utils.FontManager;
import org.newdawn.slick.Color;

import java.util.List;

public class MenuSelectAction {
    private final String FONT = "optimus_princeps_16";
    private int posX;
    private int posY;
    private int frameWidth;
    private int frameLength;
    private int frameHeight;
    private List<String> options;
    private int index;

    public MenuSelectAction(int posX, int posY, int frameLength, int frameHeight, int frameWidth, List<String> options) {
        this.posX = posX;
        this.posY = posY;

        this.frameLength = frameLength;
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        this.options = options;
    }

    public void render(Color color) {
        if (!options.isEmpty()) {
            Toolbox.drawFrame(posX, posY, frameLength, frameHeight, frameWidth, color);
            int optionHeight = frameHeight / (options.size() + 2);

            int optionY = posY;
            optionY += optionHeight;
            for (String option : options) {
                int optionX = posX + ((frameLength - FontManager.getInstance().getFontMap().get(FONT).getWidth(option)) / 2);
                if (options.get(index).equals(option)) {
                    FontManager.getInstance().drawString(FONT, optionX, optionY, option, Color.yellow);
                } else {
                    FontManager.getInstance().drawString(FONT, optionX, optionY, option, Color.white);
                }
                optionY += optionHeight;
            }
        }
    }

    public void incrementOptionsIndex() {
        if (index >= options.size() - 1) {
            index = 0;
        } else {
            index++;
        }
    }

    public void decrementOptionsIndex() {
        if (index <= 0) {
            index = options.size() - 1;
        } else {
            index--;
        }
    }

    public String getSelectedOption() {
        return options.get(index);
    }
}
