package com.ping_pong.utils;


public class Vector2D {
    private double x;
    private double y;
    public Vector2D(double x, double y) {
        this.x  = x;
        this.y = y;
    }

    public static Vector2D zero() {
        return new Vector2D(0, 0);
    }

    public static Vector2D getVelocityByAngle(double angle, double speed) {
        double radians = angle * Math.PI / 180.;
        Vector2D tmp = new Vector2D(
                Math.cos(radians),
                Math.sin(radians)
        );
        tmp.multiply(speed);
        return tmp;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
     public double magnitude() {
        return Math.sqrt(x * x + y * y);
     }

     public void add(Vector2D other) {
        x += other.getX();
        y += other.getY();
     }

    public void sub(Vector2D other) {
        x -= other.getX();
        y -= other.getY();
    }

     public void multiply(double lambda) {
        x *= lambda;
        y *= lambda;
     }

    public void div(double lambda) {
        x /= lambda;
        y /= lambda;
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

    public double heading2D() {
        return Math.atan2(y, x);
    }

    public static Vector2D add(Vector2D self, Vector2D other) {
        return new Vector2D(
                self.getX() + other.getX(),
                self.getY() + other.getY()
        );
    }

    public static Vector2D sub(Vector2D self, Vector2D other) {
        return new Vector2D(
                self.getX() - other.getX(),
                self.getY() - other.getY()
        );
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + Math.round(  x * 10000) / 10000 +
                ", y=" + Math.round(y * 10000) / 10000 +
                '}';
    }
}
