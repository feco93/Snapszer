package hu.unideb.snapszer.view;

import hu.unideb.snapszer.model.Player;

/**
 * Created by Fecó on 2015.12.20..
 */
public class PlayerView {

    private final HandView cardsInHand;

    public PlayerView(Player player, DeckView deckView, CalledCardsView calledCardsView) {
        cardsInHand = new HandView(player, deckView, calledCardsView);
    }

    public HandView getCardsInHand() {
        return cardsInHand;
    }
}
