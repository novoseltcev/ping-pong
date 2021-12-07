package com.jgame;

import com.jgame.structs.*;
import com.ping_pong.model.Settings;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import static java.lang.Thread.sleep;


public abstract class Sprite extends Region implements Runnable {
    protected Location location;
    protected Velocity velocity;
    protected Acceleration acceleration;
    protected Size size;

    protected Location startLocation;
    public boolean isEnabled = true;

    protected final Pane layer;
    protected Node view;

    public Location getLocation() {
        return location;
    }
    public Velocity getVelocity() { return velocity; }
    public Acceleration getAcceleration() { return acceleration; }
    public Size getSize() { return size; }

    public Sprite(Pane layer, double startPosX, double startPosY, double maxSpeed, double width, double height) {
        this.layer = layer;

        location = new Location(startPosX, startPosY);
        velocity = new Velocity(0, 0);
        acceleration = new Acceleration(0, 0);

        startLocation = new Location(startPosX, startPosY);
        size = new Size(height, width);

        velocity.setMaxSpeed(maxSpeed);
        respawn();
    }

    public void respawn() {
        this.view = createView();
        setPrefSize(size.getWidth(), size.getHeight());

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
        location.cloneFrom(startLocation);
        updateView();
    }

    protected abstract Node createView();

    public void activate() {
        assert !isEnabled;
        isEnabled = true;
    }

    public void deactivate() {
        isEnabled = false;
    }

    public void updateView() {
        view.setLayoutX(location.getX());
        view.setLayoutY(location.getY());
    }

    protected void move() {
        velocity.add(acceleration);
        location.add(velocity);
        acceleration.multiply(0.);
    }

    private double x(Direction direction){
        return -1 * velocity.getMaxSpeed() * direction.getDistance() / 100;
    }

    protected void seek(Location target) {
        Direction direction = location.getDirectionTo(target);
        if (direction.getDistance() < 100) {
            direction.multiply(x(direction));
            direction.sub(velocity);
            direction.limit(0.1);
            acceleration.add(direction);
        }
    }


    @Override
    public void run() {
        try{
            while (true) {
                long startTime = System.nanoTime();
                location.setBaseReferenceFrameOffset(
                        new Direction(
                                layer.getLayoutX(),
                                layer.getLayoutY()
                        )
                );
                if (isEnabled) {
                    runtime();
                }
                delay( (int) (System.nanoTime() - startTime) );
            }
        } catch (InterruptedException ignored) {}
    }

    protected abstract void runtime() throws InterruptedException;

    protected void delay(int execNanos) throws InterruptedException {
        int mills = Settings.milPerFrame - execNanos / 1000;
        int nanos = Settings.nanosPerFrame - execNanos % 1000;
        sleep(
                Math.max(mills, 0),
                Math.max(nanos, 0)
        );
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+ "{" +
                "\n\tlocation=" + location +
                "\n\tstartloc=" + startLocation +
                ",\n\tvelocity=" + velocity +
                ",\n\tacceleration=" + acceleration +
                ",\n\tsize=" + size +
                ",\n\tisEnabled=" + isEnabled +
                ",\n\tlayer=" + layer +
                ",\n\tview=" + view +
                '}';
    }
}
