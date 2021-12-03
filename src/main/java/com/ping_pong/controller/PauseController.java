package com.ping_pong.controller;


import com.ping_pong.model.Settings;

public class PauseController extends Controller{

    @Override
    protected void initialize() {}

    public void resume() throws Exception {
        app.prevScene();
        app.setBoundary(
                Settings.Stage.width, Settings.Stage.height,
                Settings.Stage.width, Settings.Stage.height);
        ((GameController) app.getCurrentController()).resume();
    }

    public void restart() throws Exception {
        app.prevScene();
        app.setBoundary(
                Settings.Stage.width, Settings.Stage.height,
                Settings.Stage.width, Settings.Stage.height);
        ((GameController) app.getCurrentController()).restart();
    }

    public void exit() {
        app.getStage().close();
        Thread.currentThread().interrupt();
    }
}
