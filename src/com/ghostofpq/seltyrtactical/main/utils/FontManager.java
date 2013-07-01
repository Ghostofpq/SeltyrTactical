package com.ghostofpq.seltyrtactical.main.utils;

import com.ghostofpq.seltyrtactical.main.scenes.MainMenu;
import lombok.Getter;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;

import java.util.HashMap;


/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 29/06/13
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
@Getter
public class FontManager {

    private static FontManager instance = new FontManager();
    @Getter
    private HashMap<String, AngelCodeFont> fontMap;

    private FontManager() {
        fontMap = new HashMap<String, AngelCodeFont>();

        try {

            fontMap.put("arial_16", new AngelCodeFont("resources/font/Arial/arial_16_white.fnt", "resources/font/Arial/arial_16_white.png"));
            fontMap.put("arial_12", new AngelCodeFont("resources/font/Arial/arial_12_white.fnt", "resources/font/Arial/arial_12_white.png"));
            fontMap.put("optimus_princeps_16", new AngelCodeFont("resources/font/optimus_princeps/optimus_princeps_16.fnt", "resources/font/optimus_princeps/optimus_princeps_16.png"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static FontManager getInstance() {
        if (instance == null) {
            synchronized (MainMenu.class) {
                if (instance == null) {
                    instance = new FontManager();
                }
            }
        }
        return instance;
    }

    public void drawString(String font, int x, int y, String str, Color color) {
        fontMap.get(font).drawString(x, y, str, color);
    }

    public void drawString(String font, int size, int x, int y, String str, Color color) {
        fontMap.get(font + "_" + size).drawString(x, y, str, color);
    }
}
