package com.ghostofpq.seltyrtactical.main.graphics;

import lombok.Getter;
import lombok.Setter;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VMPX4526
 * Date: 21/06/13
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class MenuSelect {

    private UnicodeFont font = null;
    private UnicodeFont fontSelected = null;

    private List<MenuElementSelect> options;
    private int index;
    private MenuState state;
    private float screenHeight;
    private float screenWidth;


    public MenuSelect(List<String> optionString, float startX, float startY, float offsetX, float offsetY, float screenHeight, float screenWidth) {
        try {
            font = new UnicodeFont("resources/font/old_london/OldLondon.ttf", 24, false, false);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect());
            font.loadGlyphs();

        } catch (SlickException e) {
            e.printStackTrace();
        }

        float x = startX;
        float y = startY;
        this.options = new ArrayList<MenuElementSelect>();
        for (int i = 0; i < optionString.size(); i++) {
            options.add(new MenuElementSelect(x, y, optionString.get(i), font));
            x += offsetX;
            y += offsetY;
        }
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        state = MenuState.PLACING;
        index = 0;
    }

    public void updatePlacing() {
        boolean allIsPlaced = true;

        for (int i = 0; i < options.size(); i++) {
            if (!options.get(i).isPlaced()) {
                options.get(i).addOffset(-10f);
                allIsPlaced = false;
                break;
            }
        }

        if (allIsPlaced) {
            state = MenuState.STATIC;
        }

    }

    public void updateSplitting() {
        boolean allIsOutOfScreen = true;
        for (int i = 0; i < options.size(); i++) {
            if (i != index) {
                options.get(i).addOffset(10f);
                if (!options.get(i).isOutOfScreenLeft()) {
                    allIsOutOfScreen = false;
                }
            }
        }
        options.get(index).addOffset(-20f);
        if (!options.get(index).isOutOfScreenRight(screenWidth)) {
            allIsOutOfScreen = false;
        }

        if (allIsOutOfScreen) {
            state = MenuState.FINISHED;
        }
    }

    public void indexUp() {
        if (state.equals(MenuState.STATIC)) {
            if (index + 1 == options.size()) {
                index = 0;
            } else {
                index++;
            }
        }
    }

    public void indexDown() {
        if (state.equals(MenuState.STATIC)) {
            if (index == 0) {
                index = options.size() - 1;
            } else {
                index--;
            }
        }
    }

    public void update() {
        switch (state) {
            case PLACING:
                updatePlacing();
                render();
                break;
            case STATIC:
                render();
                break;
            case SPLITTING:
                updateSplitting();
                render();
                break;
            case FINISHED:

                break;
        }
    }

    public void render() {
        for (int i = 0; i < options.size(); i++) {
            options.get(i).render(Color.white);
        }
        options.get(index).render(Color.yellow);
    }

    public void split() {
        if (state.equals(MenuState.STATIC)) {
            state = MenuState.SPLITTING;
        }
    }

    public boolean isFinished() {
        return (state.equals(MenuState.FINISHED));
    }

    private enum MenuState {
        PLACING, STATIC, SPLITTING, FINISHED
    }
}
