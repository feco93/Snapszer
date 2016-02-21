package hu.unideb.snapszer.controller;

import hu.unideb.snapszer.model.Player;
import hu.unideb.snapszer.model.operators.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Created by Fecó on 2015.12.20..
 */

public class HumanPlayerController {

    private Player player;

    @FXML
    private VBox controlPanel;

    @FXML
    private Button snapszerBtn;

    @FXML
    private Button say40Btn;

    @FXML
    private Button say20Btn;

    @FXML
    private Button finishBtn;

    @FXML
    private Button coverBtn;

    public HumanPlayerController() {
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @FXML
    void onFinishBtnClicked(MouseEvent event) {
        player.setChosenOperator(new SayEndOperator(player));
    }

    @FXML
    void onSnapszerBtnClicked(MouseEvent event) {
        player.setChosenOperator(new SnapszerOperator(player));
    }

    @FXML
    void onCoverBtnClicked(MouseEvent event) {
        player.setChosenOperator(new CoverOperator(player));
    }

    @FXML
    void onSay20BtnClicked(MouseEvent event) {
        player.setChosenOperator(new Say20Operator(player));
    }

    @FXML
    void onSay40Clicked(MouseEvent event) {
        player.setChosenOperator(new Say40Operator(player));
    }

    public Node getControlPanel() {
        return controlPanel;
    }

}
