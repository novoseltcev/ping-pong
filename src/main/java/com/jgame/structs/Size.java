package com.jgame.structs;

public class Size {
    private double height;
    private double width;

    public Size(double height, double width) {
        setHeight(height);
        setWidth(width);
    }

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }

    public double getRadius() { return getCenter().magnitude(); }
    public Location getCenter() { return new Location(width / 2, height / 2); }
}
