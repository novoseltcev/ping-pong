package com.ping_pong.model;

import com.ping_pong.utils.Vector2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.GRAY;


public abstract class Stick extends Sprite{
    protected int score = 0;
    protected final Ball ball;

    protected Stick(Pane layer, Ball ball) {
        super(layer, new Vector2D(Settings.User.startX, Settings.User.startY), Vector2D.zero(), Vector2D.zero(), Settings.User.width, Settings.User.height, Settings.User.speed);
        this.ball = ball;
        setBoundary(
                new Vector2D(15, 0),
                new Vector2D(470 - width - 15, 35)
        );
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    @Override
    protected Node createView() {
        if (view == null) {
            Rectangle rec =  new Rectangle(
                    width,
                    height
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
}
