package com.ghostofpq.seltyrtactical.main.graphics;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 15/06/13
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
@Getter
@Setter
public class MapElement implements Serializable, Comparable<MapElement>{
    private static final long serialVersionUID = 4804104249115278769L;

    private Position position;



    @Override
    public int compareTo(MapElement o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
