package hu.unideb.snapszer.model;

import java.util.List;

/**
 * Created by Fecó Sipos on 2016. 01. 31..
 */

public class GameState {

    private List<HungarianCard> cardsOnTable;

    private List<HungarianCard> playedCards;

    private HungarianCard trumpCard;

    public GameState(HungarianCard trumpCard, List<HungarianCard> cardsOnTable, List<HungarianCard> playedCards)
    {
        this.trumpCard = trumpCard;
        this.cardsOnTable = cardsOnTable;
        this.playedCards = playedCards;
    }

    public boolean tableIsEmpty()
    {
        return cardsOnTable.isEmpty();
    }

    public List<HungarianCard> getCardsOnTable() {
        return cardsOnTable;
    }

    public List<HungarianCard> getPlayedCards() {
        return playedCards;
    }

    public HungarianCard getTrumpCard() {
        return trumpCard;
    }
}
