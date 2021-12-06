package com.jgame.structs;

public class Angle {
    private static final double conversionRatio = 180 / Math.PI;

    public static Angle fromDegree(double degree) {
        return new Angle(degree);
    }

    public static Angle fromRadian(double radian) {
        return new Angle(Angle.convertToDegree(radian));
    }

    public static double convertToDegree(double radian) {
        return radian * conversionRatio;
    }

    public static double convertToRadian(double degree) {
        return degree / conversionRatio;
    }


    private double degree;

    protected Angle(double degree) {
        setDegree(degree);
    }

    public double getDegree() {
        return degree;
    }

    public void setDegree(double degree) {
        if (degree < 0) {
            this.degree = (360 - (-degree % 360)) % 360;
        } else {
            this.degree = degree % 360;
        }
    }

    public double getRadian() {
        return Angle.convertToRadian(degree);
    }

    public double getX() {
        return Math.cos(getRadian());
    }

    public double getY() {
        return Math.sin(getRadian());
    }

    public void rotateToDegree(double degree) {
        setDegree(this.degree + degree);
    }

    public void rotateToRadian(double radian) {
        setDegree(this.degree + convertToDegree(radian));
    }

    public void invert() {
        setDegree(degree + 180);
    }
}
