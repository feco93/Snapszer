package hu.unideb.snapszer.view;

import hu.unideb.snapszer.model.Player;
import hu.unideb.snapszer.model.operators.Say20Operator;
import hu.unideb.snapszer.model.operators.Say40Operator;
import hu.unideb.snapszer.model.operators.SayEndOperator;
import javafx.scene.control.Button;

/**
 * Created by Fecó on 2015.12.20..
 */
public class HumanView extends PlayerView {

    private final Button say20Btn;
    private final Button say40Btn;
    private final Button sayFinishBtn;

    public HumanView(Player player, DeckView deckView, CalledCardsView calledCardsView) {
        super(player, deckView, calledCardsView);
        say20Btn = new Button("20");
        say20Btn.setOnMouseClicked(event -> {
            player.setChosenOperator(new Say20Operator());
        });
        say40Btn = new Button("40");
        say40Btn.setOnMouseClicked(event -> {
            player.setChosenOperator(new Say40Operator());
        });
        sayFinishBtn = new Button("Finish");
        sayFinishBtn.setOnMouseClicked(event -> {
            player.setChosenOperator(new SayEndOperator());
        });
    }

    public Button getSay20Btn() {
        return say20Btn;
    }

    public Button getSay40Btn() {
        return say40Btn;
    }

    public Button getSayFinishBtn() {
        return sayFinishBtn;
    }
}
