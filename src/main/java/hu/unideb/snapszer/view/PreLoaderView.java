package hu.unideb.snapszer.view;

import hu.unideb.snapszer.UIGame;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * Created by Fecó Sipos on 2016. 02. 07..
 */
public class PreLoaderView {
    private Group backGround;
    private TableView tableView;
    private Scene scene;
    private StackPane root;
    private SubScene backGroundScene;
    private VBox controls;
    private Button playGameBtn;
    private Stage primaryStage;

    public PreLoaderView() {
        backGround = new Group();
        tableView = new TableView();
        this.backGround.getChildren().add(tableView);
        backGroundScene = new SubScene(this.backGround, 0, 0, true, SceneAntialiasing.BALANCED);

        playGameBtn = new Button("Play game");
        playGameBtn.setOnMouseClicked(event1 -> {
            UIGame game = new UIGame(primaryStage.getWidth(), primaryStage.getHeight());
            primaryStage.setScene(game.getScene());
            primaryStage.setFullScreen(true);
            game.Start();
        });

        controls = new VBox();
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().add(playGameBtn);

        root = new StackPane();
        root.getChildren().addAll(backGroundScene, controls);
        scene = new Scene(root, 0, 0, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.rgb(10, 10, 40));
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });
    }

    public void start() {
        primaryStage = new Stage();
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
