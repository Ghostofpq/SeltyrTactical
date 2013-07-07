package com.ghostofpq.seltyrtactical.main.entities.battlefield;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 05/07/13
 * Time: 08:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class BattlefieldElement implements Serializable {
    private static final long serialVersionUID = 3537734489408631256L;

    public enum BattlefieldElementType {
        BLOC
    }

    protected BattlefieldElementType type;

}
