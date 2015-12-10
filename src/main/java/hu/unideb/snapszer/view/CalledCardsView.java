package hu.unideb.snapszer.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Fecó on 2015.12.09..
 */
public class CalledCardsView {

    public final ObservableList<HungarianCardView> cards;

    public CalledCardsView() {
        this.cards = FXCollections.observableArrayList();
    }

    public void add(HungarianCardView cardView) {
        cards.add(cardView);
    }

}
