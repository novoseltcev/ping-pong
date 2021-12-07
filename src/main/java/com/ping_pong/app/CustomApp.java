package com.ping_pong.app;

import com.ping_pong.controller.Controller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;


public abstract class CustomApp extends Application {
    protected Stage stage;
    private ObservableList<Controller> controllers = FXCollections.observableArrayList();
    private ObservableList<Scene> scenes = FXCollections.observableArrayList();

    private final String startFxmlFilename;
    private final String title;
    private final int[] borders;

    protected HashMap<String, String> storedValues = new HashMap<>();

    protected CustomApp(String startFxmlFilename, String title, int ...borders) {
        super();

        this.startFxmlFilename = startFxmlFilename;
        this.title = title;

        int[] tmpBorders = new int[4];
        System.arraycopy(borders, 0, tmpBorders, 0, borders.length);

        switch (borders.length) {
            case(0):
                tmpBorders[0] = 300;

            case(1):
                tmpBorders[1] = 200;

            case(2):
                tmpBorders[2] = 1920;

            case(3):
                tmpBorders[3] = 1080;

            case(4):
                break;

            default:
                throw new AssertionError();
        }
        this.borders = tmpBorders;
    }

    public Stage getStage() { return stage; }

    public Scene getCurrentScene() { return scenes.get(scenes.size() - 1); }

    public Controller getCurrentController() { return controllers.get(controllers.size() - 1); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.stage.setTitle(title);
        if (borders.length == 2) {
            setBoundary(borders[0], 1920, borders[1], 1080);
        }
        else if (borders.length == 4) {
            setBoundary(borders[0], borders[1], borders[2], borders[3]);
        }
        nextScene(startFxmlFilename);
        show();
    }

    private void reloadScene() {
        stage.setScene(getCurrentScene());
    }

    public void nextScene(String fxmlFilename) throws Exception {
        URL resource = new File("src/main/resources/com/ping_pong/" + fxmlFilename).toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(resource);

        System.out.println(fxmlLoader.getLocation());
        Scene scene = new Scene(fxmlLoader.load());
        Controller controller = fxmlLoader.getController();
        scenes.add(scene);
        controllers.add(controller);

        controller.setApp(this);
        reloadScene();
    }

    public void prevScene() {
        int size = this.scenes.size() - 1;
        scenes.remove(size);
        controllers.remove(size);
        reloadScene();
    }

    public void setBoundary(int minWidth, int minHeight, int maxWidth,  int maxHeight) {
        this.stage.setMinWidth(minWidth);
        this.stage.setMinHeight(minHeight);
        this.stage.setMaxWidth(maxWidth);
        this.stage.setMaxHeight(maxHeight);
    }

    public void setPosition(double X, double Y) {
        this.stage.setX(X);
        this.stage.setY(Y);
    }

    public void setPositionToCentral() {
        setPosition(
                (int) (1920 - this.stage.getMinWidth()) >> 1,
                (int) (1080 - this.stage.getMinHeight()) >> 1
        );
    }

    protected abstract void show();

    public void store(String key, String value) {
        storedValues.put(key, value);
    }

    public String load(String key) {
        return storedValues.get(key);
    }
}
