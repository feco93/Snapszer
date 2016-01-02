package hu.unideb.snapszer.view;

import hu.unideb.snapszer.model.Computer;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.Player;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Created by Fecó on 2015.12.20..
 */
public class PlayerView {

    private final ObservableList<HungarianCardView> cardsInHand;
    private final MySequentialTransition sequentialTransition;
    private final Player player;

    public Player getPlayer() {
        return player;
    }


    public PlayerView(Player player) {
        sequentialTransition = new MySequentialTransition();
        cardsInHand = FXCollections.observableArrayList();
        this.player = player;

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
            }
        });
    }

    public ObservableList<HungarianCardView> getCardsInHand() {
        return cardsInHand;
    }

    public void drawCard(HungarianCardView hungarianCardView) {
        cardsInHand.add(hungarianCardView);
    }

    public HungarianCardView callCard(HungarianCard card) {
        HungarianCardView hungarianCardView = cardsInHand.stream().
                filter(cardView ->
                        cardView.getCard().equals(card)).
                findFirst().get();
        HungarianCardView toRet = cardsInHand.remove(cardsInHand.indexOf(hungarianCardView));
        Timeline tl;
        if (player instanceof Computer) {
            tl = toRet.callCard(-90, 90);
        } else {
            tl = toRet.callCard(-90, 0);
        }
        sequentialTransition.addAnimation(tl);
        sequentialTransition.playAnimationSynchronous();
        sortCards();
        return toRet;
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
