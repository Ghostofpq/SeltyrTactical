package com.ghostofpq.seltyrtactical.main.graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.Color;

/**
 * Created with IntelliJ IDEA.
 * User: VMPX4526
 * Date: 17/06/13
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
public class MenuElementSelect {
    private float posX;
    private float posY;
    private float offset;
    private String text;
    private MenuState state;
    private UnicodeFont font;

    public MenuElementSelect(float posX, float posY, String text,UnicodeFont font) {
        this.posX = posX;
        this.posY = posY;
        this.offset=100;

        this.text = text;
        this.font = font;
        this.state = MenuState.INIT;
    }

    public void render() {
        switch (state) {
            case INIT:
                if(offset<=0){
                    state=MenuState.DEFAULT;
                }else{
                    offset-=10f;
                }
                break;
            case CHOSEN:
                if(offset>=100){
                    state=MenuState.DEFAULT;
                }else{
                    offset+=10f;
                }
                break;
            case DEFAULT:
                Color.white.bind();
                break;
            case SELECTED:
                Color.yellow.bind();
                break;
            case FINISHED:
                break;
        }
        font.drawString(posX-offset,posY,text);
    }



    private enum MenuState {
        INIT, DEFAULT, SELECTED, CHOSEN, FINISHED
    }


}
