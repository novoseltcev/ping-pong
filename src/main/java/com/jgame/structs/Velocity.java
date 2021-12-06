package com.jgame.structs;


public class Velocity extends Vector2D {
    private double maxSpeed;

    public Velocity(double x, double y) {
        super(x, y);
    }

    public Velocity(Angle angle, double speed) {
        super(angle, speed);
    }

    @Override
    protected void limit() {
        limit(maxSpeed);
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }
}
