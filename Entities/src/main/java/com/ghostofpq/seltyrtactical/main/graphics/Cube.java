package java.com.ghostofpq.seltyrtactical.main.graphics;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.com.ghostofpq.seltyrtactical.main.utils.HighlightColor;
import java.com.ghostofpq.seltyrtactical.main.utils.TextureKey;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: GhostOfPQ
 * Date: 15/06/13
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
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
    private TextureKey textureTop;
    private TextureKey side;
    private boolean visible;
    private boolean selectable;
    private HighlightColor highlight;

    public Cube(Position position) {
        this.setPosition(position);
        this.setVisible(true);
        this.setSelectable(true);
        textureTop = TextureKey.GRASS;
        side = TextureKey.EARTH;
        highlight = HighlightColor.NONE;

        // Creating the facets
        PositionAbsolute positionAbsolute = position.toAbsolute();
        PositionAbsolute p1 = positionAbsolute;
        PositionAbsolute p2 = positionAbsolute.plus(0, 0, 1f);
        PositionAbsolute p3 = positionAbsolute.plus(1f, 0, 1f);
        PositionAbsolute p4 = positionAbsolute.plus(1f, 0, 0);
        PositionAbsolute p5 = positionAbsolute.plus(0, 1f, 0);
        PositionAbsolute p6 = positionAbsolute.plus(0, 1f, 1f);
        PositionAbsolute p7 = positionAbsolute.plus(1f, 1f, 1f);
        PositionAbsolute p8 = positionAbsolute.plus(1f, 1f, 0);

        facetZenith = new Facet(p5, p8, p7, p6, textureTop);
        facetSouth = new Facet(p6, p7, p3, p2, side);
        facetWest = new Facet(p5, p6, p2, p1, side);
        facetNorth = new Facet(p8, p5, p1, p4, side);
        facetEast = new Facet(p7, p8, p4, p3, side);
    }

    public void draw(PointOfView pointOfView) {
        if (isVisible()) {
            if (!highlight.equals(HighlightColor.NONE)) {
                TextureKey texture = TextureKey.HIGHLIGHT_BLUE;
                if (highlight.equals(HighlightColor.GREEN)) {
                    texture = TextureKey.HIGHLIGHT_GREEN;
                } else if (highlight.equals(HighlightColor.RED)) {
                    texture = TextureKey.HIGHLIGHT_RED;
                }
                facetZenith.setTexture(texture);
            } else {
                facetZenith.setTexture(textureTop);
            }

            switch (pointOfView) {
                case SOUTH:
                    facetSouth.draw();
                    facetEast.draw();
                    facetZenith.draw();
                    break;
                case WEST:
                    facetWest.draw();
                    facetSouth.draw();
                    facetZenith.draw();
                    break;
                case NORTH:
                    facetNorth.draw();
                    facetWest.draw();
                    facetZenith.draw();
                    break;
                case EAST:
                    facetEast.draw();
                    facetNorth.draw();
                    facetZenith.draw();
                    break;
            }
        }
    }

    @Override
    public int compareTo(Cube other) {
        return this.getPosition().compareTo(other.getPosition());
    }


}
