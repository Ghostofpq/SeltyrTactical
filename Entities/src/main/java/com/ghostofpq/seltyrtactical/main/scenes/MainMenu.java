package java.com.ghostofpq.seltyrtactical.main.scenes;

import org.lwjgl.input.Keyboard;

import java.com.ghostofpq.seltyrtactical.main.Game;
import java.com.ghostofpq.seltyrtactical.main.graphics.MenuSelect;
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
    private MenuSelect menu;

    private MainMenu() {
    }

    public static MainMenu getInstance() {
        if (instance == null) {
            synchronized (MainMenu.class) {
                if (instance == null) {
                    instance = new MainMenu();
                }
            }
        }
        instance.init();
        return instance;
    }

    @Override
    public void init() {
        List<String> options = new ArrayList<String>();
        options.add("New player");
        options.add("Load");
        options.add("Quit");
        menu = new MenuSelect(options, 300, 200, 0, 50, 600, 800);
    }

    @Override
    public void update() {
        menu.update();
        if (menu.isFinished()) {
            if (menu.getIndex() == 0) {
                Game.getInstance().setCurrentScene(NewPlayer.getInstance());
            } else if (menu.getIndex() == 1) {
                Game.getInstance().setCurrentScene(LoadMenu.getInstance());
            } else if (menu.getIndex() == 2) {
                Game.getInstance().quit();
            }
        }
    }

    @Override
    public void render() {
        menu.render();
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
