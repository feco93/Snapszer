package hu.unideb.snapszer.view;

import hu.unideb.snapszer.model.Deck;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.ICard;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.paint.Color;

/**
 * Created by Fecó on 2015.12.09..
 */
public class DeckView extends Group {

    private final double distanceBetweenCards = 0.5;
    public final ObservableList<HungarianCardView> cardsInDeck;
    private final MySequentialTransition sequentialTransition;

    public DeckView(Deck deck) {
        sequentialTransition = new MySequentialTransition();
        cardsInDeck = FXCollections.observableArrayList();
        double index = -1;
        for (ICard card : deck) {
            HungarianCardView cardView
                    = new HungarianCardView(
                    (HungarianCard) card,
                    new Point3D(80, index, 0));
            AmbientLight light = new AmbientLight(Color.WHITE);
            light.getScope().add(cardView);
            getChildren().add(light);
            getChildren().add(cardView);
            index -= distanceBetweenCards;
            cardsInDeck.add(cardView);
        }
        deck.cards.addListener((ListChangeListener.Change<? extends ICard> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    HungarianCard card = (HungarianCard) c.getAddedSubList().get(0);
                    sequentialTransition.addAnimation(cardsInDeck.get(cardsInDeck.indexOf(card)).setTrump(60, -0.5));
                    sequentialTransition.playAnimationSynchronous();
                }
            }
        });
    }
}
