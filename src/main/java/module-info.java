module com.ping_pong {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    exports com.ping_pong.app;
    opens com.ping_pong.app to javafx.fxml;
    exports com.ping_pong.controller;
    opens com.ping_pong.controller to javafx.fxml;
    exports com.ping_pong.model;
    opens com.ping_pong.model to javafx.fxml;
    exports com.ping_pong.utils;
}