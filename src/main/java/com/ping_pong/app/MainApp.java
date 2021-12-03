package com.ping_pong.app;


import com.ping_pong.model.Settings;

public class MainApp extends CustomApp {
    public MainApp() {
        super("start-view.fxml", "Tennis",
                300, 200, 300, 200);
    }

    @Override
    protected void show() {
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}