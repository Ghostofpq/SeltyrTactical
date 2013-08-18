package com.ghostofpq.seltyrtactical.game.utils;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.HashMap;
import java.util.Map;

public class SpritesheetManager {

    private static SpritesheetManager instance = new SpritesheetManager();
    private Map<String, SpriteSheet> spriteSheetMap;

    private SpritesheetManager() {
        spriteSheetMap = new HashMap<String, SpriteSheet>();
        try {
            SpriteSheet sheet = new SpriteSheet("./resources/spritesheets/ramza.png", 32, 42);
            spriteSheetMap.put("Ramza", sheet);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static SpritesheetManager getInstance() {
        if (instance == null) {
            synchronized (SpritesheetManager.class) {
                if (instance == null) {
                    instance = new SpritesheetManager();
                }
            }
        }
        return instance;
    }

    public SpriteSheet getSpriteSheet(String key) {
        return spriteSheetMap.get(key);
    }

    /**
     * Getters and Setters
     */

    public Map<String, SpriteSheet> getSpriteSheetMap() {
        return spriteSheetMap;
    }
}

