package com.ghostofpq.seltyrtactical.main.utils;

import com.ghostofpq.seltyrtactical.main.scenes.MainMenu;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.TrueTypeFont;


/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 29/06/13
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
public class FontManager {

    private static FontManager instance = new FontManager();
    private UnicodeFont font;

    private FontManager() {
        try {
            font = new UnicodeFont("resources/font/optimus_princeps/OptimusPrinceps.ttf", 24, false, false);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect());
            font.loadGlyphs();
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

    public UnicodeFont getFont() {
        return font;
    }


}
