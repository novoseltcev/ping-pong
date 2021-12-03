package com.ping_pong.model;

import com.ping_pong.utils.Vector2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static java.lang.Thread.sleep;
import static javafx.scene.paint.Color.RED;


public class Bot extends Stick {
    public Bot(Pane layer, Ball ball) {
        super(layer, ball);
    }

    @Override
    protected Node createView() {
        Rectangle res = (Rectangle) super.createView();
        res.setFill(RED);
        return res;
    }

    @Override
    protected void runtime() throws InterruptedException {
        brain();
        checkRespawn();
        if (!ball.isEnabled) {
            respawnBall();
        }
        move();
        brake();
        limit();
    }

    private void brain() {
        Vector2D diff = getDiff(this.ball);
        if (diff.getY() < Settings.Stage.height / 4.) {
            if (diff.getX() > width) {
                acceleration = Vector2D.getVelocityByAngle(0, Settings.User.speed);
            }
            if (diff.getX() < 0){
                acceleration = Vector2D.getVelocityByAngle(180, Settings.User.speed);
            }
        }
    }

    protected void checkRespawn() throws InterruptedException {
        Vector2D diff = getDiff(this.ball);
        if (diff.getY() <= 0) {
            if (diff.getX() > 0 - ball.radius && diff.getX() < width + ball.radius) {
                ball.location.sub(new Vector2D(0, diff.getY()));
                ball.location.add(new Vector2D(0, ball.radius));

                ball.horizontalReflect();
            } else {
                incrementScore();
                respawnBall();
            }
        }
    }

    protected void respawnBall() throws InterruptedException {
        ball.stop();
        Vector2D newLoc = ball.goTo(this);
        newLoc.add(new Vector2D(0, height / 2 + ball.radius));
        ball.location = newLoc;

        sleep(1000);
        ball.velocity = Vector2D.getVelocityByAngle(
                (Math.random() * 90) + 45,
                (int) ball.maxSpeed
        );
        ball.start();
    }


}
