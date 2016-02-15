package hu.unideb.snapszer.controller;

import hu.unideb.snapszer.model.Player;
import hu.unideb.snapszer.model.operators.*;
import javafx.scene.control.Button;

/**
 * Created by Fecó on 2015.12.20..
 */
public class HumanPlayerController {

    private final Button say20Btn;
    private final Button say40Btn;
    private final Button sayFinishBtn;
    private final Button snapszerBtn;
    private final Button coverBtn;

    public HumanPlayerController(Player player) {
        say20Btn = new Button("20");
        say20Btn.setOnMouseClicked(event -> {
            player.setChosenOperator(new Say20Operator(player));
        });
        say40Btn = new Button("40");
        say40Btn.setOnMouseClicked(event -> {
            player.setChosenOperator(new Say40Operator(player));
        });
        snapszerBtn = new Button("Snapszer");
        snapszerBtn.setOnMouseClicked(event -> {
            player.setChosenOperator(new SnapszerOperator(player));
        });
        coverBtn = new Button("Cover");
        coverBtn.setOnMouseClicked(event -> {
            player.setChosenOperator(new CoverOperator(player));
        });
        sayFinishBtn = new Button("Finish");
        sayFinishBtn.setOnMouseClicked(event -> {
            player.setChosenOperator(new SayEndOperator(player));
        });
    }

    public Button getSay20Btn() {
        return say20Btn;
    }

    public Button getSay40Btn() {
        return say40Btn;
    }

    public Button getSnapszerBtn() {
        return snapszerBtn;
    }

    public Button getCoverBtn() {
        return coverBtn;
    }

    public Button getSayFinishBtn() {
        return sayFinishBtn;
    }
}
