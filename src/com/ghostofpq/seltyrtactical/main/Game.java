package com.ghostofpq.seltyrtactical.main;

import com.ghostofpq.seltyrtactical.main.entities.Player;
import com.ghostofpq.seltyrtactical.main.scenes.BattleScene;
import com.ghostofpq.seltyrtactical.main.scenes.Scene;
import com.ghostofpq.seltyrtactical.main.utils.GraphicsManager;
import com.ghostofpq.seltyrtactical.main.utils.SaveManager;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * User: GhostOfPQ
 * Date: 11/06/13
 */
@Getter
public class Game {
    private static volatile Game instance = null;
    boolean requestClose;
    private int height;
    private int width;
    private Scene currentScene;
    @Setter
    private Player player;

    private Game() {
        this.height = 600;
        this.width = 800;
        this.requestClose = false;

        player = new Player("Jack");
        init();

    }

    public static Game getInstance() {
        if (instance == null) {
            synchronized (Game.class) {
                if (instance == null) {
                    instance = new Game();
                }
            }
        }
        return instance;
    }

    public static void main(String[] argv) {
        //System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        Game g = Game.getInstance();
        g.setCurrentScene(BattleScene.getInstance());

        SaveManager sm = SaveManager.getInstance();
        Player p1 = sm.loadPlayer("Bob");
        Player p2 = sm.loadPlayer("Jack");
        List<Player> players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);


        System.out.println(p1.getTeam().getTeam().get(2).getName());

        BattleScene.getInstance().setPlayer(players);
        g.run();
    }

    public void init() {
        try {
            Display.setDisplayMode(new DisplayMode(this.width, this.height));
            Display.setSwapInterval(1);
            Display.sync(60);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        GraphicsManager.getInstance().ready3D();
    }

    public void run() {
        while (!requestClose) {
            manageInput();
            update();
            render();
        }
        Display.destroy();
    }

    public void manageInput() {
        currentScene.manageInput();
    }

    public void update() {
        requestClose = Display.isCloseRequested();
        currentScene.update();
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        currentScene.render();

        Display.update();
        Display.sync(60);
    }

    public void quit() {
        requestClose = true;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
        this.currentScene.init();
    }

}
