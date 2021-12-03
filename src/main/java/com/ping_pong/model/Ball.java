package com.ping_pong.model;


import com.ping_pong.utils.Vector2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Ball extends Sprite implements Runnable {
    public Ball(Pane layout) {
        super(layout, new Vector2D(Settings.Ball.startX, Settings.Ball.startY),
                Vector2D.getVelocityByAngle(
                (Math.random() * 90) + 180 + 45,
                      Settings.Ball.speed
                ), Vector2D.zero(),
                Settings.Ball.width, Settings.Ball.height, Settings.Ball.speed);

        setBoundary(
                new Vector2D(15, -50),
                new Vector2D(470 - radius - 15, 450)
        );
    }

    @Override
    protected Node createView() {
        if (view == null) {
            Circle circle = new Circle(
                    centerX,
                    centerY,
                    radius,
                    Color.ORANGE
            );
            circle.setStroke(Color.GRAY);
            view = circle;
        } return view;
    }

    @Override
    protected void runtime() {
        move();
        limit();
    }

    @Override
    protected void limit() {
        if (location.getX() < leftTopCorner.getX() || location.getX() > rightBottomCorner.getX() ) {
            verticalReflect();
        }
    }

    public void horizontalReflect() {
        stop();
        velocity = new Vector2D(velocity.getX(), -velocity.getY());
        double ang = velocity.heading2D() * 180 / Math.PI;
        velocity = Vector2D.getVelocityByAngle(
                ang + (Math.random() - .5) * 25,
                velocity.magnitude()
        );
        start();
    }

    public void verticalReflect() {
        velocity = new Vector2D(-velocity.getX(), velocity.getY());
    }

    @Override
    public String toString() {
        return "Ball" + super.toString().split("Sprite")[1];
    }
}
