package com.ghostofpq.seltyrtactical.entities.messages;

import com.ghostofpq.seltyrtactical.entities.character.Player;

public class MessageOtherPlayer extends Message {
    private Player otherPlayer;

    public MessageOtherPlayer() {
        type = MessageType.OTHER_PLAYER;
    }

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public void setOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }
}
