package com.ghostofpq.seltyrtactical.main.utils;

import com.ghostofpq.seltyrtactical.main.Game;
import com.ghostofpq.seltyrtactical.main.graphics.MenuSelect;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 26/06/13
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 */
public class MainMenu implements Scene {
    private static volatile MainMenu instance = null;

    private MainMenu() {
        List<String> options = new ArrayList<String>();
        options.add("New player");
        options.add("Load");
        options.add("Quit");
        menu = new MenuSelect(options, 150, 150, 100, 50, 600, 800);
    }

    public static MainMenu getInstance() {
        if (instance == null) {
            synchronized (MainMenu.class) {
                if (instance == null) {
                    instance = new MainMenu();
                }
            }
        }
        return instance;
    }

    private MenuSelect menu;

    @Override
    public void update() {
        manageInput();
        render();
        if (menu.isFinished()) {
            if (menu.getIndex() == 0) {
                //CREATE NEW PLAYER
            } else if (menu.getIndex() == 1) {

            } else if (menu.getIndex() == 2) {
                Game.getInstance().quit();
            }
        }
    }

    @Override
    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        menu.update();
        Display.update();
        Display.sync(60);
    }

    @Override
    public void manageInput() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
                    menu.indexDown();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
                    menu.indexDown();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
                    menu.indexUp();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
                    menu.indexUp();
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
                    menu.split();
                }
            }
        }
    }
}
