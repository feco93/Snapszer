package hu.unideb.snapszer.view;

import hu.unideb.snapszer.UIGame;
import hu.unideb.snapszer.controller.GameMenuController;
import hu.unideb.snapszer.controller.OptionsMenuController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by Fecó Sipos on 2016. 02. 07..
 */
public class PreLoaderView {
    private Scene scene;
    private StackPane root;
    private SubScene backGroundScene;
    private Stage primaryStage;
    private static final Logger logger = LogManager.getLogger(PreLoaderView.class);

    public PreLoaderView() {
        primaryStage = new Stage();
        TableView tableView = new TableView();
        backGroundScene = new SubScene(tableView, 0, 0, true, SceneAntialiasing.BALANCED);

        root = new StackPane();
        scene = new Scene(root, 0, 0, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.rgb(10, 10, 40));
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });
        DisplayMenu();
    }

    public void DisplayMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/gameMenu.fxml"));
        loader.setController(new GameMenuController(this));
        try {
            loader.load();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        Node mainMenu = loader.getRoot();
        root.getChildren().setAll(backGroundScene, mainMenu);
    }

    public void DisplayOptionsMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/optionsMenu.fxml"));
        loader.setController(new OptionsMenuController(this));
        try {
            loader.load();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        Node mainMenu = loader.getRoot();
        root.getChildren().setAll(backGroundScene, mainMenu);
    }

    public void StartGame() {
        UIGame game = new UIGame(scene.getWidth(), scene.getHeight());
        game.getGameTask().setOnSucceeded(event1 -> Platform.runLater(() -> {
            DisplayMenu();
        }));
        root.getChildren().setAll(game.getRoot().getChildren());
        game.Start(1);
    }

    public void start() {
        primaryStage.setFullScreenExitHint("");
        primaryStage.fullScreenExitKeyProperty().set(KeyCombination.NO_MATCH);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();

        backGroundScene.setWidth(primaryStage.getWidth());
        backGroundScene.setHeight(primaryStage.getHeight());
        addCamera(backGroundScene);
    }

    private PerspectiveCamera addCamera(SubScene scene) {
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(true);
        double NEAR = 0.01;
        double FAR = 1000;
        perspectiveCamera.setFarClip(FAR);
        perspectiveCamera.setNearClip(NEAR);
        perspectiveCamera.setTranslateY(-350);
        perspectiveCamera.setTranslateZ(-330);
        perspectiveCamera.setRotationAxis(Rotate.X_AXIS);
        perspectiveCamera.setRotate(-50);
        scene.setCamera(perspectiveCamera);
        return perspectiveCamera;
    }
}
