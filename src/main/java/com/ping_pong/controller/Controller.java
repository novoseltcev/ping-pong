package com.ping_pong.controller;

import com.ping_pong.app.CustomApp;


public abstract class Controller {
    protected CustomApp app;

    public void setApp(CustomApp app) throws Exception {
        this.app = app;
        initialize();
    }

    protected abstract void initialize() throws Exception;
}
