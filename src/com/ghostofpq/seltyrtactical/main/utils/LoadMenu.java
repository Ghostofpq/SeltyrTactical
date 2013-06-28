package com.ghostofpq.seltyrtactical.main.utils;

import com.ghostofpq.seltyrtactical.main.Game;
import com.ghostofpq.seltyrtactical.main.graphics.MenuSelect;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VMPX4526
 * Date: 28/06/13
 * Time: 09:19
 * To change this template use File | Settings | File Templates.
 */
public class LoadMenu implements Scene {
    private static LoadMenu instance = new LoadMenu();
    private MenuSelect menuLoad;

    private LoadMenu() {

    }

    public static LoadMenu getInstance() {
        if (instance == null) {
            synchronized (MainMenu.class) {
                if (instance == null) {
                    instance = new LoadMenu();
                }
            }
        }
        instance.init();
        return instance;
    }

    @Override
    public void init() {
        List<String> savedPlayers = null;
        try {
            savedPlayers = SaveManager.getInstance().checkFolderForPlayers();
        } catch (IOException e) {
            e.printStackTrace();
            Game.getInstance().quit();
        }

        List<String> options = new ArrayList<String>();
        for (String player : savedPlayers) {
            options.add(player);
        }
        options.add("Back");
        menuLoad = new MenuSelect(options, 150, 150, 0, 50, 600, 800);
    }

    @Override
    public void update() {
        menuLoad.update();
        if (menuLoad.isFinished()) {
            if (menuLoad.getIndex() == (menuLoad.getOptions().size() - 1)) {
                Game.getInstance().setCurrentScene(MainMenu.getInstance());
            }
        }
    }

    @Override
    public void render() {
        menuLoad.render();
    }

    @Override
    public void manageInput() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
                    menuLoad.indexDown();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                    menuLoad.indexDown();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
                    menuLoad.indexUp();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                    menuLoad.indexUp();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
                    menuLoad.split();
                }
            }
        }
    }
}
