package com.ping_pong.controller;

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

import static java.lang.Thread.sleep;


public class GameController extends Controller {

    public Pane botLayer;
    public Pane userLayer;
    public Pane ballLayer;

    public Label userLabel;
    public Label userScoreLabel;
    public Label botScoreLabel;

    protected Ball ball;
    protected User user;
    protected Bot bot;

    @Override
    protected void initialize() throws Exception {
        userLabel.setText(
                app.load("username") + ": "
        );
        ball = new Ball(ballLayer);
        user = new User(userLayer, ball);
        bot = new Bot(botLayer, ball);
        System.out.println("INIT");
        resume();
    }

    public void restart() throws InterruptedException {
        ball.respawn();
        user.respawn();
        bot.respawn();
        resume();
    }

    public void resume() throws InterruptedException {
        System.out.println("RESUME");
        sleep(1000);
        ThreadGroup gameGroup = new ThreadGroup("game");

        Thread userThread = new Thread(gameGroup, user, "user");
        Thread enemyThread = new Thread(gameGroup, bot, "bot");

        Thread ballThread = new Thread(gameGroup, ball, "ball");

        userThread.start();
        enemyThread.start();
        ballThread.start();

        Timeline timeline =
                new Timeline(new KeyFrame(Duration.millis(Settings.milPerFrame), e -> {
                    ball.updateView();
                    bot.updateView();
                    user.updateView();
                    botScoreLabel.setText(String.valueOf(user.getScore()));
                    userScoreLabel.setText(String.valueOf(bot.getScore()));
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Timeline timelineDop =
                new Timeline(new KeyFrame(Duration.millis(Settings.milPerFrame), e -> {
                    if (user.enteredKey == KeyCode.ESCAPE && gameGroup.activeCount() > 0) {
                        user.enteredKey = null;
                        System.out.println("GOTO pause");
                        try {
                            gameGroup.interrupt();
                            timeline.stop();
                            app.nextScene("pause-view.fxml");
                            app.setBoundary(250, 250, 250, 250);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }));
        timelineDop.setCycleCount(Animation.INDEFINITE);
        timelineDop.play();
    }
}
