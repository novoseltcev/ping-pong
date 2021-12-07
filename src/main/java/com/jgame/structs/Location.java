package com.jgame.structs;

public class Location extends Vector2D {
    private Vector2D leftTopCorner = new Direction(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
    private Vector2D rightBottomCorner = new Direction(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    private Direction baseReferenceFrameOffset = new Direction(0, 0);


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
    public Direction getBaseReferenceFrameOffset() {
        return this.baseReferenceFrameOffset;
    }

    @Override
    protected void limit() {
        if (getX() < leftTopCorner.getX()) {
            x = leftTopCorner.getX();
        } else if (getX() > rightBottomCorner.getX()) {
            x = rightBottomCorner.getX();
        }

        if (getY() < leftTopCorner.getY()) {
            y = leftTopCorner.getY();
        } else if (getY() > rightBottomCorner.getY()) {
            y = rightBottomCorner.getY();
        }
    }

    public Direction getDirectionTo(Vector2D other) {
        Vector2D referenceFrameOffset = new Direction(
                ((Location)other).baseReferenceFrameOffset.getX() - baseReferenceFrameOffset.getX(),
                ((Location)other).baseReferenceFrameOffset.getY() - baseReferenceFrameOffset.getY()
        );
        Direction locationOffset = new Direction(
                other.getX() - getX(),
                other.getY() - getY()
        );
        locationOffset.add(referenceFrameOffset);
        return locationOffset;
    }

    public double getDistanceTo(Vector2D other) {
        return getDirectionTo(other).getDistance();
    }
}
