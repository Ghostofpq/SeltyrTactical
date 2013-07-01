package com.ghostofpq.seltyrtactical.main.scenes;

import com.ghostofpq.seltyrtactical.main.Game;
import com.ghostofpq.seltyrtactical.main.entities.Player;
import com.ghostofpq.seltyrtactical.main.graphics.BarRender;
import com.ghostofpq.seltyrtactical.main.graphics.CharacterRender;
import com.ghostofpq.seltyrtactical.main.graphics.Toolbox;
import org.newdawn.slick.Color;


/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 29/06/13
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
public class PlayerMenu implements Scene {
    private static PlayerMenu instance = new PlayerMenu();
    private Player player;
    private CharacterRender characterRender;
    private BarRender experienceBar;

    public static PlayerMenu getInstance() {
        if (instance == null) {
            synchronized (MainMenu.class) {
                if (instance == null) {
                    instance = new PlayerMenu();
                }
            }
        }
        instance.init();
        return instance;
    }

    private PlayerMenu() {
    }

    @Override
    public void init() {
        player = Game.getInstance().getPlayer();
        characterRender = new CharacterRender(50f, 50f, player.getTeam().getTeam().get(0));
        experienceBar = new BarRender(200, 250, 100, 50, 100, 20, Color.cyan, Color.magenta);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        experienceBar.render();
    }

    @Override
    public void manageInput() {

    }
}
