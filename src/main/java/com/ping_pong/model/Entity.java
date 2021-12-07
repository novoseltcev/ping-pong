package com.ping_pong.model;

import com.jgame.Sprite;
import com.jgame.structs.Location;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.GRAY;


public abstract class Entity extends Sprite {
    protected int score = 0;
    protected final Ball ball;

    protected Entity(Pane layer, Ball ball) {
        super(layer, Settings.Entity.startX, Settings.Entity.startY, Settings.Entity.speed, Settings.Entity.width, Settings.Entity.height);
        this.ball = ball;
        location.setLeftTopCorner(
                new Location(0, 0)
        );
        location.setRightBottomCorner(
                new Location(470 - size.getWidth(), 35)
        );
    }

    public Ball getBall() {
        return ball;
    }

    public int getScore() { return score; }

    public void incrementScore() { score++; }

    @Override
    protected Node createView() {
        if (view == null) {
            Rectangle rec =  new Rectangle(
                    size.getWidth(),
                    size.getHeight()
            );
            rec.setStroke(GRAY);
            view = rec;
        } return view;
    }

    @Override
    public void respawn() {
        super.respawn();
        score = 0;
    }

    protected void brake() {
        acceleration.brake(velocity);
    }
}
