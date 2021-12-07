package com.ping_pong.controller;

import com.ping_pong.app.CustomApp;


public abstract class Controller {
    protected CustomApp app;
    protected boolean initialized = false;

    public void setApp(CustomApp app) {
        this.app = app;
        run();
    }

    protected void run() {
        if (!initialized) {
            initialize();
        }
    }

    protected void initialize() {
        assert !initialized;
        initialized = true;
        System.out.println("INIT");
    };
}
