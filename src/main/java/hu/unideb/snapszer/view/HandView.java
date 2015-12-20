package hu.unideb.snapszer.view;

import hu.unideb.snapszer.model.Computer;
import hu.unideb.snapszer.model.ICard;
import hu.unideb.snapszer.model.Player;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Group;

/**
 * Created by Fecó on 2015.12.09..
 */
public class HandView {

    private final ObservableList<HungarianCardView> cardsInHand;
    private final MySequentialTransition sequentialTransition;
    private final Player player;

    public Player getPlayer() {
        return player;
    }

    public ObservableList<HungarianCardView> getCardsInHand() {
        return cardsInHand;
    }

    public HandView(Player player, DeckView deckView, CalledCardsView calledCardsView) {
        sequentialTransition = new MySequentialTransition();
        cardsInHand = FXCollections.observableArrayList();
        this.player = player;

        player.cards.addListener((ListChangeListener.Change<? extends ICard> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (ICard card : c.getAddedSubList()) {
                        cardsInHand.add(deckView.cardsInDeck.remove(deckView.cardsInDeck.indexOf(card)));
                    }
                }
                if (c.wasRemoved()) {
                    for (ICard card : c.getRemoved()) {
                        cardsInHand.remove(cardsInHand.indexOf(card));
                    }
                }
            }
        });

        cardsInHand.addListener((ListChangeListener.Change<? extends HungarianCardView> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (HungarianCardView cardView : c.getAddedSubList()) {
                        sortCards();
                        Timeline tl;
                        int index = c.getTo();
                        if (player instanceof Computer) {
                            tl = cardView.drawCard(-90 + 30 * index, -30, 120, 150);
                        } else {
                            tl = cardView.drawCard(-90 + 30 * index, -30, -150, -30);
                        }
                        sequentialTransition.addAnimation(tl);
                        sequentialTransition.playAnimationSynchronous();

                    }
                }
                if (c.wasRemoved()) {
                    for (HungarianCardView card :
                            c.getRemoved()) {
                        Timeline tl;
                        if (player instanceof Computer) {
                            tl = card.callCard(-90, 90);
                        } else {
                            tl = card.callCard(-90, 0);
                        }
                        sequentialTransition.addAnimation(tl);
                        sequentialTransition.playAnimationSynchronous();
                        calledCardsView.add(card);
                    }
                    sortCards();
                }
            }
        });
    }

    private void sortCards() {
        for (int i = 0; i < cardsInHand.size(); ++i) {
            Timeline tl;
            if (player instanceof Computer) {
                tl = cardsInHand.get(i).moveCard(-60 + 30 * i);
            } else {
                tl = cardsInHand.get(i).moveCard(-60 + 30 * i);
            }
            sequentialTransition.addAnimation(tl);
            sequentialTransition.playAnimationSynchronous();
        }
    }
}
