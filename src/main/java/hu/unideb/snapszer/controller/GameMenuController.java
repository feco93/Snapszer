package hu.unideb.snapszer.controller;

import hu.unideb.snapszer.UIGame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Created by Fecó Sipos on 2016. 02. 21..
 */
public class GameMenuController {

    private final Scene scene;
    private final Parent menu;
    private UIGame game;
    @FXML
    private VBox MainMenu;


    public GameMenuController(Scene menuScene, Parent preloaderView) {
        scene = menuScene;
        menu = preloaderView;
    }

    @FXML
    void onPlayBtnClicked(MouseEvent event) {
        game = new UIGame(scene.getWidth(), scene.getHeight());
        game.getGameTask().setOnSucceeded(event1 -> Platform.runLater(() ->
                scene.setRoot(menu)));
        scene.setRoot(game.getRoot());
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
