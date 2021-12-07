package com.ping_pong.controller;


import com.jgame.interfaces.IGameEngine;
import com.ping_pong.model.Settings;
import javafx.fxml.FXML;

public class PauseController extends Controller{
    IGameEngine game;
    @Override
    protected void initialize() {
        super.initialize();
    }



    @FXML
    protected void resume() {
        app.prevScene();
        app.setBoundary(
                Settings.Stage.width, Settings.Stage.height,
                Settings.Stage.width, Settings.Stage.height
        );
        game = (IGameEngine) app.getCurrentController();
        game.resume();
    }

    @FXML
    protected void restart() {
        app.prevScene();
        app.setBoundary(
                Settings.Stage.width, Settings.Stage.height,
                Settings.Stage.width, Settings.Stage.height
        );
        game = (IGameEngine) app.getCurrentController();
        game.restart();
    }

    @FXML
    protected void exit() {
        app.getStage().close();
        Thread.currentThread().interrupt();
    }
}
