package com.ghostofpq.seltyrtactical.entities.messages;

import com.ghostofpq.seltyrtactical.entities.character.Player;

public class MessageMainPlayer extends Message {
    private Player mainPlayer;

    public MessageMainPlayer() {
        type = MessageType.MAIN_PLAYER;
    }

    public Player getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }
}
