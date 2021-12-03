package com.ping_pong.model;

import com.ping_pong.utils.Utils;
import com.ping_pong.utils.Vector2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import static java.lang.Thread.sleep;


public abstract class Sprite extends Region implements Runnable {
    private final Vector2D startLocation;
    Vector2D location;
    Vector2D velocity;
    Vector2D acceleration;
    double maxSpeed;

    Node view;

    double width;
    double height;
    double centerX;
    double centerY;
    double radius;
    double angle;

    Pane layer;

    boolean isEnabled = true;
    protected Vector2D leftTopCorner;
    protected Vector2D rightBottomCorner;

    public Sprite(Pane layer, Vector2D location, Vector2D velocity, Vector2D acceleration, double width, double height, double maxSpeed) {
        this.layer = layer;
        this.startLocation = location;

        this.location = location;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.width = width;
        this.height = height;
        this.maxSpeed = maxSpeed;

        this.centerX = width / 2;
        this.centerY = height / 2;
        this.radius = Math.pow((width * width + height * height) / 4, 0.5);
        respawn();
    }

    public void respawn() {
        this.view = createView();
        setPrefSize(width, height);

        int viewsSize = getChildren().size();
        if (viewsSize >= 1) {
            getChildren().set(viewsSize - 1, view);
        } else {
            getChildren().add(view);
        }

        int regionsSize = layer.getChildren().size();
        if (regionsSize >= 1) {
            layer.getChildren().set(regionsSize - 1, this);
        } else {
            layer.getChildren().add(this);
        }
        location = startLocation;
        view.setLayoutX(location.getX());
        view.setLayoutY(location.getY());
    }

    public void setBoundary(Vector2D left_top_corner, Vector2D right_bottom_corner) {
        leftTopCorner = left_top_corner;
        rightBottomCorner = right_bottom_corner;
    }

    protected abstract Node createView();

    public void updateView() {
        view.setLayoutX(location.getX());
        view.setLayoutY(location.getY());
    }

    public void move() {
        velocity.add(acceleration);
        velocity.limit(maxSpeed);
        location.add(velocity);
        angle = velocity.heading2D();
        acceleration.multiply(0.);
    }

    protected void limit() {

        seekX(leftTopCorner);
        if (location.getX() < leftTopCorner.getX()) {
            location.setX(leftTopCorner.getX());
        }

        seekX(rightBottomCorner);
        if (location.getX() > rightBottomCorner.getX()) {
            location.setX(rightBottomCorner.getX());
        }

        seekY(leftTopCorner);
        if (location.getY() < leftTopCorner.getY()) {
            location.setY(leftTopCorner.getY());
        }

        seekY(rightBottomCorner);
        if (location.getY() > rightBottomCorner.getY()) {
            location.setY(rightBottomCorner.getY());
        }
    }

    public void brake() {
        acceleration.setX(
                ((velocity.getX() > 0) ? -1 : 1) *
                        Math.pow(Math.abs(velocity.getX()), 1.5) / 3
        );

        acceleration.setY(
                ((velocity.getY() > 0) ? -1 : 1)  *
                        Math.pow(Math.abs(velocity.getY()), 1.5) / 3
        );

        if (Double.isNaN(acceleration.getX())) {
            acceleration.setX(0.);
            velocity.setX(0.);
        }
        if (Double.isNaN(acceleration.getY())) {
            acceleration.setY(0.);
            velocity.setY(0.);
        }
    }

    public void seek(Vector2D target) {
        Vector2D desired = Vector2D.sub(target, location);
        double distance = desired.magnitude();
        desired.normalize();
        if (distance < 100) {
            double m = Utils.map(distance, 0, 100, 0, maxSpeed);
            desired.multiply(m);
        } else {
            desired.multiply(maxSpeed);
        }
        Vector2D steer = Vector2D.sub(desired, velocity);
        steer.limit(0.1);
        applyForce(steer);
    }

    public void seekX(Vector2D target) {
        Vector2D desired = new Vector2D(Vector2D.sub(target, location).getX(), 0);
        double distance = desired.magnitude();
        desired.normalize();
        if (distance < 100) {
            double m = Utils.map(distance, 0, 100, 0, maxSpeed);
            desired.multiply(m);
        } else {
            desired.multiply(maxSpeed);
        }
        Vector2D steer = Vector2D.sub(desired, velocity);
        steer.limit(0.1);
        applyForce(steer);
    }

    public void seekY(Vector2D target) {
        Vector2D desired = new Vector2D(0, Vector2D.sub(target, location).getY());
        double distance = desired.magnitude();
        desired.normalize();
        if (distance < 100) {
            double m = Utils.map(distance, 0, 100, 0, maxSpeed);
            desired.multiply(m);
        } else {
            desired.multiply(maxSpeed);
        }
        Vector2D steer = Vector2D.sub(desired, velocity);
        steer.limit(0.1);
        applyForce(steer);
    }

    public void applyForce(Vector2D force) {
        acceleration.add(force);
    }

    public Vector2D getDiff(Sprite other) {
        Vector2D layerVector = new Vector2D(layer.getLayoutX(), layer.getLayoutY());
        Vector2D otherLayerVector = new Vector2D(other.layer.getLayoutX(), other.layer.getLayoutY());
        layerVector.add(location);
        otherLayerVector.add(other.location);
        return Vector2D.sub(otherLayerVector, layerVector);
    }

    public void delay(int execNanos) throws InterruptedException {
        int mills = Settings.milPerFrame - execNanos / 1000;
        int nanos = Settings.nanosPerFrame - execNanos % 1000;
        sleep(
                Math.max(mills, 0),
                Math.max(nanos, 0)
        );
    }
    public void stop() { isEnabled = false; }

    public void start() {isEnabled = true; }

    public void run() {
        try{
            while (true) {
                long startTime = System.nanoTime();
                if (isEnabled) {
                    runtime();
                }
                delay( (int) (System.nanoTime() - startTime) );
            }
        } catch(InterruptedException ignored) {}
    }

    protected abstract void runtime() throws InterruptedException;

    public Vector2D goTo(Sprite other) {
        return  Vector2D.add(getDiff(other), this.location);
    }

    @Override
    public String toString() {
        return "Sprite {" +
                "\n\tlocation = " + location +
                "\n\tvelocity = " + velocity +
                "\n\tacceleration = " + acceleration +
                "\n\tisEnabled = " + isEnabled +
                "\n\tleftTopCorner = " + leftTopCorner +
                "\n\trightBottomCorner = " + rightBottomCorner +
                "\n}";
    }
}
