package com.ping_pong.controller;

import com.ping_pong.model.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class StartController extends Controller {
    @FXML
    protected TextField usernameField;

    @Override
    protected void initialize() {}

    public void confirmUsername() throws Exception {
        String text = usernameField.getText();
        if (!text.isEmpty()) {
            app.store("username", text);
            app.nextScene("game-view.fxml");
            app.setBoundary(
                    Settings.Stage.width, Settings.Stage.height,
                    Settings.Stage.width, Settings.Stage.height
            );
        }
    }
}
