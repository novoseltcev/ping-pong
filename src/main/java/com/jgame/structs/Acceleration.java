package com.jgame.structs;

public class Acceleration extends Vector2D {
    private final double GRAVITY = 9.8;


    public Acceleration(double x, double y) {
        super(x, y);
    }

    public Acceleration(Angle angle, double acceleration) {
        super(angle, acceleration);
    }

    @Override
    protected void limit() {}

    public void brake(Velocity velocity) {
        Angle velocityAngle = velocity.getAngle();
        velocityAngle.invert();

        Acceleration tmp = new Acceleration(velocityAngle, Math.pow(Math.abs(velocity.getX()), 0.43) / 5);
        setX(tmp.x);
        setY(tmp.y);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Acceleration{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", angle=").append(getAngle().getDegree());
        sb.append('}');
        return sb.toString();
    }
}