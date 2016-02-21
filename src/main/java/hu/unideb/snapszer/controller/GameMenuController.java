package hu.unideb.snapszer.controller;

import hu.unideb.snapszer.UIGame;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Fecó Sipos on 2016. 02. 21..
 */
public class GameMenuController {

    private final Scene menuScene;
    private UIGame game;
    @FXML
    private VBox MainMenu;

    private Stage primaryStage;

    public GameMenuController(Stage stage, Scene menuScene) {
        primaryStage = stage;
        this.menuScene = menuScene;
    }

    @FXML
    void onPlayBtnClicked(MouseEvent event) {
        game = new UIGame(primaryStage.getWidth(), primaryStage.getHeight());
        game.getGameTask().setOnSucceeded(event1 -> {
            primaryStage.setScene(menuScene);
            primaryStage.setFullScreen(true);
        });
        primaryStage.setScene(game.getScene());
        primaryStage.setFullScreen(true);
        game.Start(1);
    }

    @FXML
    void onExitBtnClicked(MouseEvent event) {
        System.exit(0);
    }

    public UIGame getGame() {
        return game;
    }

}
