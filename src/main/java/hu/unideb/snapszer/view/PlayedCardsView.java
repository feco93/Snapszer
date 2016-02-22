package hu.unideb.snapszer.view;

import hu.unideb.snapszer.model.HungarianCard;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Created by Fecó on 2015.12.09..
 */
public class PlayedCardsView {

    private final MySequentialTransition sequentialTransition;
    private final ObservableList<HungarianCardView> cards;

    public PlayedCardsView(ObservableList<HungarianCard> cards, ObservableList<HungarianCardView> calledCards) {
        sequentialTransition = new MySequentialTransition();
        this.cards = FXCollections.observableArrayList();
        cards.addListener((ListChangeListener<? super HungarianCard>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (HungarianCard card :
                            c.getAddedSubList()) {
                        this.cards.add(calledCards.remove(calledCards.indexOf(card)));
                    }
                }
            }
        });
        this.cards.addListener((ListChangeListener<? super HungarianCardView>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (HungarianCardView cardView :
                            c.getAddedSubList()) {
                        sequentialTransition.addAnimation(cardView.beatCard(0));
                    }
                    sequentialTransition.playAnimationSynchronous();
                }
            }
        });
    }

}
