package hu.unideb.snapszer.view;

import hu.unideb.snapszer.model.Deck;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.ICard;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    private ObjectProperty<HungarianCardView> trumpCardView;
    private final MySequentialTransition sequentialTransition;

    public HungarianCardView getTrumpCardView() {
        return trumpCardView.get();
    }

    public ObjectProperty<HungarianCardView> trumpCardViewProperty() {
        return trumpCardView;
    }

    public DeckView(Deck deck, ObjectProperty<HungarianCard> trumpCard) {
        sequentialTransition = new MySequentialTransition();
        trumpCardView = new SimpleObjectProperty<>();
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
        trumpCard.addListener(observable -> {
            trumpCardView.setValue(cardsInDeck.get(
                    cardsInDeck.indexOf(trumpCard.getValue())));
            sequentialTransition.addAnimation(
                    trumpCardView.getValue().setTrump(60, -0.5));
            sequentialTransition.playAnimationSynchronous();
        });
    }
}
