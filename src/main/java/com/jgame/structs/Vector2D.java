package com.jgame.structs;


public abstract class Vector2D {
    private double x;
    private double y;

    Vector2D(double x, double y) {
        setX(x);
        setY(y);
    }

    Vector2D(Angle angle, double length) {
        setX(angle.getX());
        setY(angle.getY());
        multiply(length);
    }

    public double getX() { return x; }
    public void setX(double x) {
        this.x = x;
        limit();
    }

    public double getY() { return y; }
    public void setY(double y) {
        this.y = y;
        limit();
    }

    public Angle getAngle() {
        return Angle.fromRadian(
                Math.atan2(y, x)
        );
    }

    protected abstract void limit();

    public double magnitude() { return Math.sqrt(x * x + y * y); }

    public void add(Vector2D other) {
        setX(x + other.getX());
        setY(y + other.getY());
     }

    public void sub(Vector2D other) {
        setX(x - other.getX());
        setY(y - other.getY());
    }

    public void multiply(double lambda) {
        setX(x * lambda);
        setY(y * lambda);
     }

    public void div(double lambda) {
        setX(x / lambda);
        setY(y / lambda);
    }

    public void normalize() {
        if (magnitude() != 0) {
            div(magnitude());
        }
    }

    public void limit(double max) {
        if (magnitude() > max) {
            normalize();
            multiply(max);
        }
    }

    @Override
    public String toString() {
        return '\n' + getClass().getSimpleName() + " {" +
                " \n\tx=" + Math.round(x * 10000) / 10000 +
                ",\n\ty=" + Math.round(y * 10000) / 10000 +
                "\n}";
    }

    public void cloneFrom(Vector2D vector) {
        setX(vector.getX());
        setY(vector.getY());
    }


}
