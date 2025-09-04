package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Main class for the application UI.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello, world!");
        Scene scene = new Scene(helloWorld);

        stage.setScene(scene);
        stage.show();
    }
}
