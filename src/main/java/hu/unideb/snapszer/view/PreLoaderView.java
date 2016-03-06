package hu.unideb.snapszer.view;

import hu.unideb.snapszer.controller.GameMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Fecó Sipos on 2016. 02. 07..
 */
public class PreLoaderView {
    private Group backGround;
    private TableView tableView;
    private Scene scene;
    private StackPane root;
    private SubScene backGroundScene;
    private Stage primaryStage;

    public PreLoaderView() {
        primaryStage = new Stage();
        backGround = new Group();
        tableView = new TableView();
        this.backGround.getChildren().add(tableView);
        backGroundScene = new SubScene(this.backGround, 0, 0, true, SceneAntialiasing.BALANCED);

        root = new StackPane();
        scene = new Scene(root, 0, 0, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.rgb(10, 10, 40));
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });

        FXMLLoader loader = new FXMLLoader(PreLoaderView.class.getResource("/fxml/gameMenu.fxml"));
        loader.setController(new GameMenuController(scene, root));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Node mainMenu = loader.getRoot();
        root.getChildren().addAll(backGroundScene, mainMenu);
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
