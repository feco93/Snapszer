package hu.unideb.snapszer.controller;

import hu.unideb.snapszer.ConsoleGame;
import hu.unideb.snapszer.view.PreLoaderView;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Created by Fecó Sipos on 2016. 02. 07..
 */
public class MainFormController {

    @FXML
    protected TextField numberOfGamesTextField;
    private Stage mainStage;

    @FXML
    public void onPlayerVSComputerClicked(MouseEvent event) {
        PreLoaderView preLoaderView = new PreLoaderView();
        mainStage.close();
        preLoaderView.start();
    }

    @FXML
    public void onComputerVSComputerClicked(MouseEvent event) {
        ConsoleGame game = new ConsoleGame();
        int numberOfGames = Integer.valueOf(numberOfGamesTextField.getText());
        game.Start(numberOfGames);
    }

    public void setStage(Stage stage) {
        mainStage = stage;
    }
}
