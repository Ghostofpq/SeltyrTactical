package com.ghostofpq.kulkan.game.graphics;

import com.ghostofpq.kulkan.commons.PointOfView;
import com.ghostofpq.kulkan.commons.Position;
import com.ghostofpq.kulkan.commons.PositionAbsolute;
import com.ghostofpq.kulkan.game.utils.GraphicsManager;
import com.ghostofpq.kulkan.game.utils.HighlightColor;
import com.ghostofpq.kulkan.game.utils.TextureKey;

import java.io.Serializable;

public class Cube extends DrawableObject implements Serializable {
    private static final long serialVersionUID = 4804104249115278769L;
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
        this.setHeight(1.0f);
        this.setPosition(position);
        this.setPositionAbsolute(position.toAbsolute());
        this.setVisible(true);
        this.setSelectable(true);
        this.setMoving(false);
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

        facetZenith = new Facet(position, p5, p8, p7, p6, textureTop);
        facetSouth = new Facet(position, p6, p7, p3, p2, side);
        facetWest = new Facet(position, p5, p6, p2, p1, side);
        facetNorth = new Facet(position, p8, p5, p1, p4, side);
        facetEast = new Facet(position, p7, p8, p4, p3, side);
    }

    public void update(long deltaTime) {

    }

    public void draw() {
        PointOfView pointOfView = GraphicsManager.getInstance().getCurrentPointOfView();
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

    public String toString() {
        return "Cube";
    }

    /**
     * Getters and Setters
     */

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Facet getFacetZenith() {
        return facetZenith;
    }

    public void setFacetZenith(Facet facetZenith) {
        this.facetZenith = facetZenith;
    }

    public Facet getFacetNorth() {
        return facetNorth;
    }

    public void setFacetNorth(Facet facetNorth) {
        this.facetNorth = facetNorth;
    }

    public Facet getFacetEast() {
        return facetEast;
    }

    public void setFacetEast(Facet facetEast) {
        this.facetEast = facetEast;
    }

    public Facet getFacetWest() {
        return facetWest;
    }

    public void setFacetWest(Facet facetWest) {
        this.facetWest = facetWest;
    }

    public Facet getFacetSouth() {
        return facetSouth;
    }

    public void setFacetSouth(Facet facetSouth) {
        this.facetSouth = facetSouth;
    }

    public TextureKey getTextureTop() {
        return textureTop;
    }

    public void setTextureTop(TextureKey textureTop) {
        this.textureTop = textureTop;
    }

    public TextureKey getSide() {
        return side;
    }

    public void setSide(TextureKey side) {
        this.side = side;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public HighlightColor getHighlight() {
        return highlight;
    }

    public void setHighlight(HighlightColor highlight) {
        this.highlight = highlight;
    }

    public PositionAbsolute getPositionToCompare(PointOfView pointOfView) {
        PositionAbsolute result = null;

        switch (pointOfView) {
            case NORTH:
                result = getPositionAbsolute().plusNew(0.5f, 0, 0.5f);
                break;
            case SOUTH:
                result = getPositionAbsolute().plusNew(0, 0, 0);
                break;
            case EAST:
                result = getPositionAbsolute().plusNew(0, 0, 0.5f);
                break;
            case WEST:
                result = getPositionAbsolute().plusNew(0.5f, 0, 0);
                break;
        }
        return result;
    }
}
