package yorm.yormfxml;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import yorm.yormjava.Yorm;

/**
 * The main Yorm application.
 */
public class Main extends Application {
    private final Yorm yorm = new Yorm();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("YORM BOT");
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            fxmlLoader.<MainWindow>getController().setYorm(this.yorm); // inject the Yorm instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
