package app;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class App extends Application {
    
    private static final int WIDTH = 1300;
    private static final int HEIGHT = 420;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Mainframe.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Scene scene = new Scene(root);
            SoundPlayer soundPlayer = SoundPlayer.getSoundPlayer();

            scene.setOnKeyPressed((EventHandler<? super KeyEvent>)new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    soundPlayer.playSound(event);
                    
                }
            });
            
            primaryStage.setTitle("KeyboardFX");
            primaryStage.setScene(scene);
            primaryStage.setWidth(WIDTH);
            primaryStage.setHeight(HEIGHT);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}