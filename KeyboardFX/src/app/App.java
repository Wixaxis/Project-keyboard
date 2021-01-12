package app;

import java.io.File;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class App extends Application {

    private static final int WIDTH = 1300;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Mainframe.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            SoundPlayer soundPlayer = SoundPlayer.getSoundPlayer();
            scene.setOnKeyPressed((EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    soundPlayer.playSound(event);
                }
            });
            primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("pianoicon.png")));
            primaryStage.setTitle("KeyboardFX");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(800);
            primaryStage.minHeightProperty().bind(scene.widthProperty().divide(1300.0 / 460.0));
            primaryStage.maxHeightProperty().bind(scene.widthProperty().divide(1300.0 / 460.0));
            primaryStage.setWidth(WIDTH);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}