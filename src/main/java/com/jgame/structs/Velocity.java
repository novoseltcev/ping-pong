package com.jgame.structs;


public class Velocity extends Vector2D {
    private double maxSpeed;

    public Velocity(double x, double y) {
        super(x, y);
    }

    public Velocity(Angle angle, double speed) {
        super(angle, speed);
        maxSpeed = speed;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Velocity{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", maxSpeed=").append(maxSpeed);
        sb.append('}');
        return sb.toString();
    }
}
