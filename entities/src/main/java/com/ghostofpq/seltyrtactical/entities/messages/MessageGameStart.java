package com.ghostofpq.seltyrtactical.entities.messages;

import com.ghostofpq.seltyrtactical.entities.battlefield.Battlefield;

public class MessageGameStart extends Message {
    private Battlefield battlefield;

    public MessageGameStart() {
        type = MessageType.GAME_START;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }
}
