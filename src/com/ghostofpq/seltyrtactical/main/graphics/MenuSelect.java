package com.ghostofpq.seltyrtactical.main.graphics;

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

public class MenuSelect {

    private UnicodeFont font = null;
    private UnicodeFont fontSelected = null;
    private List<MenuElementSelect> options;
    private int index;

    public MenuSelect(List<String> optionString, float startX, float startY, float offsetX, float offsetY) {
        try {
            font = new UnicodeFont("resources/font/old_london/OldLondon.ttf", 24,false,false);
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
        index=0;
    }

    public void indexUp() {
        if (index + 1 == options.size()) {
            index = 0;
        } else {
            index++;
        }
    }

    public void indexDown() {
        if (index == 0) {
            index = options.size() - 1;
        } else {
            index--;
        }
    }

    public int getIndex() {
        return index;
    }

    public void render() {
        for (int i = 0; i < options.size(); i++) {
            if (!options.get(i).isPlaced()) {
                options.get(i).addOffset(-10f);
                break;
            }
        }



        for (int i = 0; i < options.size(); i++) {
            options.get(i).render(Color.white);
        }
        options.get(index).render(Color.yellow);

    }
}
