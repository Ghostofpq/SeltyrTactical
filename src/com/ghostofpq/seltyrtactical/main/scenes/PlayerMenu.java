package com.ghostofpq.seltyrtactical.main.scenes;

import com.ghostofpq.seltyrtactical.main.Game;
import com.ghostofpq.seltyrtactical.main.entities.Player;
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
    private CharacterRender characterRender1;
    private CharacterRender characterRender2;
    private CharacterRender characterRender3;
    private CharacterRender characterRender4;

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

        characterRender1 = new CharacterRender(0, 0, 400, 150, 3, player.getTeam().getTeam().get(0));
        //characterRender2 = new CharacterRender(0, 150, 400, 150, 3, player.getTeam().getTeam().get(0));
        //characterRender3 = new CharacterRender(400, 0, 400, 150, 3, player.getTeam().getTeam().get(0));
        //characterRender4 = new CharacterRender(400, 150, 400, 150, 3, player.getTeam().getTeam().get(0));


    }

    @Override
    public void update() {
        player.getTeam().getTeam().get(0).setCurrentHealthPoint(player.getTeam().getTeam().get(0).getCurrentHealthPoint() - 1);
    }

    @Override
    public void render() {
        characterRender1.render(Color.white);
        //characterRender2.render(Color.white);
        // characterRender3.render(Color.white);
        //characterRender4.render(Color.white);

    }

    @Override
    public void manageInput() {

    }
}
