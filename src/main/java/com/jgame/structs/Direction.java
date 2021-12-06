package com.jgame.structs;

public class Direction extends Vector2D {
    public Direction(double x, double y) {
        super(x, y);
    }

    @Override
    protected void limit() {}

    public double getDistance() {
        return magnitude();
    }
}
