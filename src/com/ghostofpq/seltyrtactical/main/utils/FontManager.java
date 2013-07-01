package com.ghostofpq.seltyrtactical.main.utils;

import com.ghostofpq.seltyrtactical.main.scenes.MainMenu;
import lombok.Getter;
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
@Getter
public class FontManager {

    private static FontManager instance = new FontManager();
    private UnicodeFont font;
    private UnicodeFont font2;

    private FontManager() {
        try {
            font = new UnicodeFont("resources/font/optimus_princeps/OptimusPrinceps.ttf", 24, false, false);
            font.addAsciiGlyphs();
            font.getEffects().add(new ColorEffect());
            font.loadGlyphs();
            font2 = new UnicodeFont("resources/font/optimus_princeps/OptimusPrinceps.ttf", 16, false, false);
            font2.addAsciiGlyphs();
            font2.getEffects().add(new ColorEffect());
            font2.loadGlyphs();
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
}
