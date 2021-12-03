package com.ping_pong.model;

import com.ping_pong.utils.Vector2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.GREEN;


public class User extends Stick {
    public KeyCode enteredKey;
    public User(Pane layer, Ball ball) {
        super(layer, ball);
        setHandlers();
    }

    private void setHandlers() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> {
            enteredKey = event.getCode();
            switch (enteredKey) {
                case A, LEFT -> velocity = Vector2D.getVelocityByAngle(180, Settings.User.speed);
                case D, RIGHT -> velocity = Vector2D.getVelocityByAngle(0, Settings.User.speed);
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case A, LEFT, D, RIGHT -> brake();
            }
        });
    }

    @Override
    protected Node createView() {
        Rectangle res = (Rectangle) super.createView();
        res.setFill(GREEN);
        return res;
    }

    protected void checkRespawn() throws InterruptedException {
        Vector2D diff = getDiff(this.ball);
        if (diff.getY() >= 0) {
            if (diff.getX() > 0 - ball.radius && diff.getX() < width + ball.radius) {
                ball.location.sub(new Vector2D(0, diff.getY()));
                ball.location.sub(new Vector2D(0, ball.radius));
                ball.horizontalReflect();
            } else {
                incrementScore();
                respawnBall();
            }
        }
    }

    protected void respawnBall() throws InterruptedException {
        ball.stop();
        enteredKey = KeyCode.S;
        while (enteredKey != KeyCode.ENTER && enteredKey != KeyCode.SPACE) {
            long startTine = System.nanoTime();
            Vector2D newLoc = ball.goTo(this);
            newLoc.sub(new Vector2D(0, height / 2 + ball.radius));
            ball.location = newLoc;
            move();
//            brake();
            limit();
            delay( (int) (System.nanoTime() - startTine));
        }

        ball.velocity = Vector2D.getVelocityByAngle(
                (Math.random() * 90) + 45 + 180,
                (int) ball.maxSpeed
        );
        ball.start();
    }

    @Override
    protected void runtime() throws InterruptedException {
        checkRespawn();
        if (!ball.isEnabled) {
            respawnBall();
        }
        move();
//        brake();
        limit();
    }
}
