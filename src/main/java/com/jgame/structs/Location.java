package com.jgame.structs;

public class Location extends Vector2D {
    private Vector2D leftTopCorner = new Location(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
    private Vector2D rightBottomCorner = new Location(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    private Vector2D baseReferenceFrameOffset = new Direction(0, 0);


    public Location(double x, double y) {
        super(x, y);
    }

    public Vector2D getLeftTopCorner() { return leftTopCorner; }
    public void setLeftTopCorner(Vector2D leftTopCorner) {
        this.leftTopCorner = leftTopCorner;
    }

    public Vector2D getRightBottomCorner() { return rightBottomCorner; }
    public void setRightBottomCorner(Vector2D rightBottomCorner) {
        this.rightBottomCorner = rightBottomCorner;
    }

    public void setBaseReferenceFrameOffset(Direction baseReferenceFrameOffset) {
        this.baseReferenceFrameOffset = baseReferenceFrameOffset;
    }

    @Override
    protected void limit() {
        if (getX() < leftTopCorner.getX()) {
            setX(leftTopCorner.getX());
        } else if (getX() > rightBottomCorner.getX()) {
            setX(rightBottomCorner.getX());
        }

        if (getY() < leftTopCorner.getY()) {
            setY(leftTopCorner.getY());
        } else if (getY() > rightBottomCorner.getY()) {
            setY(rightBottomCorner.getY());
        }
    }

    public Direction getDirectionTo(Location other) {
        Vector2D referenceFrameOffset = new Direction(
                other.baseReferenceFrameOffset.getX() - baseReferenceFrameOffset.getX(),
                other.baseReferenceFrameOffset.getY() - baseReferenceFrameOffset.getY()
        );
        Direction locationOffset = new Direction(
                other.getX() - getX(),
                other.getY() - getY()
        );
        locationOffset.add(referenceFrameOffset);
        return locationOffset;
    }

    public double getDistanceTo(Location other) {
        return getDirectionTo(other).getDistance();
    }
}
