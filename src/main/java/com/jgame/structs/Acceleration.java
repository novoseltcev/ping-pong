package com.jgame.structs;

public class Acceleration extends Vector2D {
    private final double GRAVITY = 9.8;
    private double maxAcceleration;


    public Acceleration(double x, double y) {
        super(x, y);
    }

    public Acceleration(Angle angle, double acceleration) {
        super(angle, acceleration);
    }

    @Override
    protected void limit() {
        limit(maxAcceleration);
    }

    public void setMaxAcceleration(double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public double getMaxAcceleration() {
        return maxAcceleration;
    }


    public void brake(Velocity velocity, double brakeCoefficient) {
        Angle velocityAngle = velocity.getAngle();
        velocityAngle.invert();

        Acceleration tmp = new Acceleration(velocityAngle, brakeCoefficient * GRAVITY);
        normalize();
        multiply(tmp.magnitude());
    }
}