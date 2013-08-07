package com.ghostofpq.seltyrtactical.entities.battlefield;

import lombok.Getter;

import java.io.Serializable;

public abstract class BattlefieldElement implements Serializable {
    private static final long serialVersionUID = 3537734489408631256L;
    @Getter
    protected BattlefieldElementType type;

    public enum BattlefieldElementType {
        BLOC
    }

}
