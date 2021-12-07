package com.ping_pong.model;

import com.jgame.structs.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.GREEN;


public class User extends Entity {
    public KeyCode enteredKey;
    public User(Pane layer, Ball ball) {
        super(layer, ball);
    }

    private void setHandlers() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> {
            enteredKey = event.getCode();
            switch (enteredKey) {
                case A, LEFT  -> acceleration = new Acceleration(Angle.fromDegree(180), Settings.Entity.speed);
                case D, RIGHT -> acceleration = new Acceleration(Angle.fromDegree(0), Settings.Entity.speed);
            }
        });

//        scene.setOnKeyReleased(event -> {
//            switch (event.getCode()) {
//                case A, LEFT, D, RIGHT -> brake();
//            }
//        });
    }

    @Override
    protected Node createView() {
        Rectangle res = (Rectangle) super.createView();
        res.setFill(GREEN);
        return res;
    }

    protected void checkRespawn() throws InterruptedException {
        Direction direction = location.getDirectionTo(this.ball.getLocation());
        if (direction.getY() >= 0) {
            double ballRadius = ball.getSize().getRadius();
            if (direction.getX() >= -ballRadius && direction.getX() <= size.getWidth() + ballRadius) {
                ball.getLocation().sub(new Location(0, direction.getY()));
                ball.getLocation().sub(new Location(0, ballRadius));
                ball.horizontalReflect();
            } else {
                incrementScore();
                respawnBall();
            }
        }
    }

    protected void respawnBall() throws InterruptedException {
        ball.deactivate();
        enteredKey = KeyCode.S;
        while (enteredKey != KeyCode.ENTER && enteredKey != KeyCode.SPACE) {
            long startTine = System.nanoTime();

            Location ballLocation = ball.getLocation();
            ballLocation.sub(location.getDirectionTo(ballLocation));
            ballLocation.sub(new Location(-size.getCenter().getX() / 2, size.getCenter().getY() + ball.getSize().getRadius()));
            move();
            brake();
            delay( (int) (System.nanoTime() - startTine));
        }

        Velocity tmpVelocity = new Velocity(
                Angle.fromDegree(
                        (Math.random() * 90) + 45 + 180
                ), ball.getVelocity().getMaxSpeed()
        );
        Velocity ballVelocity = ball.getVelocity();
        ballVelocity.setX(tmpVelocity.getX());
        ballVelocity.setY(tmpVelocity.getY());
        ball.activate();
    }

    @Override
    protected void runtime() throws InterruptedException {
        checkRespawn();
        if (!ball.isEnabled) {
            respawnBall();
        }
        move();
        brake();
    }

    @Override
    public void run() {
        setHandlers();
        super.run();
    }
}
