package com.ghostofpq.seltyrtactical.main.scenes;

import com.ghostofpq.seltyrtactical.main.Game;
import com.ghostofpq.seltyrtactical.main.entities.Player;
import com.ghostofpq.seltyrtactical.main.graphics.BarRender;
import com.ghostofpq.seltyrtactical.main.graphics.CharacterRender;
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

    private PlayerMenu() {
    }

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

    @Override
    public void init() {
        player = Game.getInstance().getPlayer();

        characterRender = new CharacterRender(50f, 50f, player.getTeam().getTeam().get(0));



    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        characterRender.render(Color.white);
    }

    @Override
    public void manageInput() {

    }
}
