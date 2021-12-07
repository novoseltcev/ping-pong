package com.ping_pong.model;

import com.jgame.structs.*;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static java.lang.Thread.sleep;
import static javafx.scene.paint.Color.RED;


public class Bot extends Entity {
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
    }

    private void brain() {
        Direction direction = location.getDirectionTo(this.ball.getLocation());
        if (direction.getY() < Settings.Stage.height / 4.5) {
            if (direction.getX() > size.getHeight()) {
                acceleration = new Acceleration(Angle.fromDegree(0), Settings.Entity.speed);
            }
            if (direction.getX() < 0){
                acceleration = new Acceleration(Angle.fromDegree(180), Settings.Entity.speed);
            }
        }
    }

    protected void checkRespawn() throws InterruptedException {
        Direction direction = location.getDirectionTo(this.ball.getLocation());
        if (direction.getY() <= 0) {
            double ballRadius = ball.getSize().getRadius();
            if (direction.getX() >= -ballRadius && direction.getX() <= size.getWidth() + ballRadius) {
                System.out.println(direction);
                ball.getLocation().sub(new Location(0, direction.getY()));
                ball.getLocation().add(new Location(0, size.getHeight()));
                ball.horizontalReflect();
            } else {
                incrementScore();
                respawnBall();
            }
        }
    }

    protected void respawnBall() throws InterruptedException {
        ball.deactivate();

        Location ballLocation = ball.getLocation();
        ballLocation.sub(location.getDirectionTo(ballLocation));
        ballLocation.sub(new Location(-size.getCenter().getX(), size.getCenter().getY() + ball.getSize().getRadius()));

        sleep(1000);
        Velocity tmpVelocity = new Velocity(
                Angle.fromDegree(
                        (Math.random() * 90) + 45
                ), ball.getVelocity().getMaxSpeed()
        );
        Velocity ballVelocity = ball.getVelocity();
        ballVelocity.setX(tmpVelocity.getX());
        ballVelocity.setY(tmpVelocity.getY());
        ball.activate();
    }


}
