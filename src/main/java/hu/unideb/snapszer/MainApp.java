package hu.unideb.snapszer;

import hu.unideb.snapszer.controller.MainFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {


    public static void main(String[] args) {
        /*GameKernel kernel = new GameKernel(GameMode.GUI);*/
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/mainForm.fxml"));

            Parent root = loader.load();
            MainFormController controller = loader.getController();
            controller.setStage(primaryStage);

            Scene scene = new Scene(root, 640, 480);
            primaryStage.setTitle("Snapszer");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {

        }
    }
}
