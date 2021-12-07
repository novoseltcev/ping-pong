package com.ping_pong.model;


import com.jgame.Sprite;
import com.jgame.structs.*;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Ball extends Sprite {
    private final double REFLECT_MAX_RAND_DEGREE = 15;

    public Ball(Pane layout) {
        super(layout, Settings.Ball.startX, Settings.Ball.startY, Settings.Ball.speed, Settings.Ball.radius, Settings.Ball.radius);
        startLocation = new Location(0, 0);
        location.setLeftTopCorner(
                new Location(15, -45)
        );
        location.setRightBottomCorner(
                new Location(470 - getSize().getRadius() - 15, 460)
        );
    }

    @Override
    protected Node createView() {
        if (view == null) {
            Location center = size.getCenter();
            Circle circle = new Circle(
                    center.getX(),
                    center.getY(),
                    size.getRadius(),
                    Color.ORANGE
            );
            circle.setStroke(Color.GRAY);
            view = circle;
        } return view;
    }

    @Override
    public void respawn() {
        super.respawn();
        velocity = new Velocity(Angle.fromDegree(Math.random() * 360), velocity.getMaxSpeed());
    }

    @Override
    protected void runtime() {
        reflectFromWalls();
        move();
    }

    protected void reflectFromWalls() {
        if (location.getX() <= location.getLeftTopCorner().getX() || location.getX() >= location.getRightBottomCorner().getX()) {
            verticalReflect();
        }
//        if (location.getY() <= location.getLeftTopCorner().getY() || location.getY() >= location.getRightBottomCorner().getY()) {
//            horizontalReflect();
//        }
    }

    public void verticalReflect() {
        velocity.setX(-velocity.getX());
        location.add(velocity);
    }

    public void horizontalReflect() {
        velocity.setY(-velocity.getY());
//        System.out.println(velocity);
        Angle angle = velocity.getAngle();
        angle.rotateToDegree(
                (Math.random() - .5) * 2 * REFLECT_MAX_RAND_DEGREE
        );
        velocity = new Velocity(angle, velocity.getMaxSpeed());
    }
}
