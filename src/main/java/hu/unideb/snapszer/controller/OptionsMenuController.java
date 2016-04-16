package hu.unideb.snapszer.controller;

import hu.unideb.snapszer.UIGame;
import hu.unideb.snapszer.model.player.ComputerAdvanced;
import hu.unideb.snapszer.model.player.ComputerBeginner;
import hu.unideb.snapszer.model.player.ComputerExpert;
import hu.unideb.snapszer.model.player.ComputerRand;
import hu.unideb.snapszer.view.PreLoaderView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class OptionsMenuController implements Initializable {

    private final PreLoaderView menu;
    @FXML
    private ChoiceBox<?> difficultyCb;

    private static List<Type> ComputerTypes = Arrays.asList(ComputerRand.class,
            ComputerBeginner.class,
            ComputerAdvanced.class,
            ComputerExpert.class);

    public OptionsMenuController(PreLoaderView preLoaderView) {
        menu = preLoaderView;
    }

    @FXML
    public void onBackBtnClicked(MouseEvent event) {
        menu.DisplayMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        difficultyCb.getSelectionModel().select(ComputerTypes.indexOf(UIGame.computerType));
        difficultyCb.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    UIGame.computerType = ComputerTypes.get(newValue.intValue());
                });
    }
}

