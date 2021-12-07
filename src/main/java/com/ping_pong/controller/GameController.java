package com.ping_pong.controller;

import com.jgame.Sprite;
import com.jgame.interfaces.IGameEngine;
import com.ping_pong.model.Ball;
import com.ping_pong.model.Bot;
import com.ping_pong.model.Settings;
import com.ping_pong.model.User;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.*;


public class GameController extends Controller implements IGameEngine {

    public Pane botLayer;
    public Pane userLayer;
    public Pane ballLayer;

    public Label userLabel;
    public Label userScoreLabel;
    public Label botScoreLabel;

    protected Ball ball;
    protected User user;
    protected Bot bot;
    protected HashMap<String, Sprite> sprites;

    protected ThreadGroup gameThreadGroup;
    protected Timeline viewUpdater;
    protected Timeline threadInterrupter;

    @Override
    protected void initialize() {
        super.initialize();
        userLabel.setText(
                app.load("username") + ": "
        );
        ball = new Ball(ballLayer);
        user = new User(userLayer, ball);
        bot = new Bot(botLayer, ball);

        sprites = new HashMap<>() {{
            put("ball", ball);
            put("user", user);
            put("bot",  bot);
        }};
    }

    @Override
    protected void run() {
        super.run();
        startThreads();
        startUpdater();
        startInterrupter();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {
        activateSpites(sprites.values());
        run();
    }

    @Override
    public void stop() {
        deactivateSprites(sprites.values());
        viewUpdater.stop();
        gameThreadGroup.interrupt();
    }

    @Override
    public void restart() {
        respawnSprites(sprites.values());
        run();
    }

    private void startThreads() {
        gameThreadGroup = new ThreadGroup("game");


        Thread botThread  = new Thread(gameThreadGroup, bot,  "bot");
        Thread userThread = new Thread(gameThreadGroup, user, "user");
        Thread ballThread = new Thread(gameThreadGroup, ball, "ball");

        botThread.start();
        userThread.start();
        ballThread.start();
    }

    private void startUpdater() {
        viewUpdater =
                new Timeline(new KeyFrame(Duration.millis(Settings.milPerFrame), e -> {
                    updateSprites(sprites.values());
                    botScoreLabel.setText(String.valueOf(user.getScore()));
                    userScoreLabel.setText(String.valueOf(bot.getScore()));
                }));
        viewUpdater.setCycleCount(Animation.INDEFINITE);
        viewUpdater.play();
    }

    private void startInterrupter() {
        threadInterrupter =
                new Timeline(new KeyFrame(Duration.millis(Settings.milPerFrame), e -> {
                    if (user.enteredKey == KeyCode.ESCAPE && gameThreadGroup.activeCount() > 0) {
                        user.enteredKey = null;
                        stop();
                        try {
                            app.nextScene("pause-view.fxml");
                            app.setBoundary(250, 250, 250, 250);
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }));
        threadInterrupter.setCycleCount(Animation.INDEFINITE);
        threadInterrupter.play();
    }
}
