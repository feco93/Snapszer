package hu.unideb.snapszer.controller;

import hu.unideb.snapszer.view.PreLoaderView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Created by Fecó Sipos on 2016. 02. 21..
 */
public class GameMenuController {


    private final PreLoaderView menu;
    @FXML
    private VBox MainMenu;


    public GameMenuController(PreLoaderView preLoaderView) {
        menu = preLoaderView;
    }

    @FXML
    void onPlayBtnClicked(MouseEvent event) {
        menu.StartGame();
    }

    @FXML
    void onOptionsBtnClicked(MouseEvent event) {
        menu.DisplayOptionsMenu();
    }

    @FXML
    void onExitBtnClicked(MouseEvent event) {
        Platform.exit();
    }

}
