package com.ghostofpq.seltyrtactical.main.graphics;

import lombok.Getter;
import lombok.Setter;
import org.newdawn.slick.opengl.Texture;

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
public class Cube implements Serializable, Comparable<Cube> {
    private static final long serialVersionUID = 4804104249115278769L;
    private Position position;
    private Facet facetZenith;
    private Facet facetNorth;
    private Facet facetEast;
    private Facet facetWest;
    private Facet facetSouth;
    private Texture textureTop;
    private Texture side;
    private float scale;
    private boolean visible;

    public Cube(Position position, Texture textureTop, Texture side, float scale) {
        this.setPosition(position);
        this.setVisible(true);

        // Creating the facets
        PositionAbsolute positionAbsolute = position.toAbsolute();
        PositionAbsolute p1 = positionAbsolute;
        PositionAbsolute p2 = positionAbsolute.plus(1f, 0, 0);
        PositionAbsolute p3 = positionAbsolute.plus(1f, 1f, 0);
        PositionAbsolute p4 = positionAbsolute.plus(0, 1f, 0);
        PositionAbsolute p5 = positionAbsolute.plus(0, 0, 1f);
        PositionAbsolute p6 = positionAbsolute.plus(1f, 0, 1f);
        PositionAbsolute p7 = positionAbsolute.plus(1f, 1f, 1f);
        PositionAbsolute p8 = positionAbsolute.plus(0, 1f, 1f);

        facetZenith = new Facet(p5, p8, p7, p6, textureTop, scale);
        facetSouth = new Facet(p6, p7, p3, p2, side, scale);
        facetWest = new Facet(p5, p6, p2, p1, side, scale);
        facetNorth = new Facet(p8, p5, p1, p4, side, scale);
        facetEast = new Facet(p7, p8, p4, p3, side, scale);
    }

    public void setScale(float scale) {
        this.scale = scale;
        facetZenith.setScale(scale);
        facetSouth.setScale(scale);
        facetWest.setScale(scale);
        facetNorth.setScale(scale);
        facetEast.setScale(scale);
    }

    public void Draw(PointOfView pointOfView) {

        if (isVisible()) {
            switch (pointOfView) {
                case SOUTH:
                    facetSouth.Draw();
                    facetEast.Draw();
                    facetZenith.Draw();
                    break;
                case WEST:
                    facetWest.Draw();
                    facetSouth.Draw();
                    facetZenith.Draw();
                    break;
                case NORTH:
                    facetNorth.Draw();
                    facetWest.Draw();
                    facetZenith.Draw();
                    break;
                case EAST:
                    facetEast.Draw();
                    facetNorth.Draw();
                    facetZenith.Draw();
                    break;
            }
        }
    }

    @Override
    public int compareTo(Cube other) {
        return this.getPosition().compareTo(other.getPosition());
    }

}
