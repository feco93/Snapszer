/* 
 * Copyright (C) 2015 Feco
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package hu.unideb.snapszer.model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model abstract class for a player.
 *
 * @author Feco
 */
public abstract class Player {

    /**
     * Cards in this player hand.
     */
    public ObservableList<ICard> cards;
    /**
     * Score of this player.
     */
    private int score;
    /**
     * Chosen card for call.
     */
    protected ICard chosenCard;

    private boolean said20;
    private boolean said40;
    private HungarianCardSuit trump;

    /**
     * Constructs a new Player object.
     */
    public Player() {
        cards = FXCollections.observableArrayList();
        score = 0;
        said20 = false;
        said40 = false;
    }

    /**
     * Add the specified score to the score of this player.
     *
     * @param score how many score to be added to the score of this player
     */
    public void addScore(int score) {
        this.score += score;
    }

    public int cardsInHand() {
        return cards.size();
    }

    /**
     * Draws the specified number of cards from the deck.
     *
     * @param cards
     */
    public void drawCards(List<ICard> cards) {
        this.cards.addAll(cards);
    }

    /**
     * Draws a card.
     *
     * @param card
     */
    public void drawCard(ICard card) {
        cards.add(card);
    }

    /**
     * Gets the chosen card.
     *
     * @return chosen card
     */
    public ICard getChosenCard() {
        return chosenCard;
    }

    /**
     * Abstract method for set chosen card.
     *
     * @param card card to be chosen
     */
    public void setChosenCard(ICard card) {
        chosenCard = card;
        said20 = false;
        said40 = false;
    }

    /**
     * Gets the value of score.
     *
     * @return the score of this player
     */
    public int getScore() {
        return score;
    }

    public HungarianCardSuit getTrump() {
        return trump;
    }

    public void setTrump(HungarianCardSuit trump) {
        this.trump = trump;
    }

    /**
     * Removes and returns the chosen card from the deck.
     *
     * @return chosen card
     */
    public ICard putCard() {
        int i = 0;
        for (ICard card : cards) {
            if (card.equals(getChosenCard())) {
                return cards.remove(i);
            }
            ++i;
        }
        return null;
    }

    public boolean canSay20() {
        for (ICard card : cards) {
            if (card.getRank() == HungarianCardRank.KIRALY) {
                if (cards.stream().anyMatch((ICard item) -> {
                    return card.getSuit() == item.getSuit() && item.getRank() == HungarianCardRank.FELSO;
                })) {
                    return true;
                }
            }
            if (card.getRank() == HungarianCardRank.FELSO) {
                if (cards.stream().anyMatch((ICard item) -> {
                    return card.getSuit() == item.getSuit() && item.getRank() == HungarianCardRank.KIRALY;
                })) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canSay40(HungarianCardSuit suit) {
        return cards.stream().anyMatch((ICard card) -> {
            return card.getSuit() == suit && card.getRank() == HungarianCardRank.FELSO;
        }) && cards.stream().anyMatch((ICard card) -> {
            return card.getSuit() == suit && card.getRank() == HungarianCardRank.KIRALY;
        });
    }

    public boolean canSayEnd() {
        return score >= 66;
    }

    public void say20() {
        score += 20;
        said20 = true;
    }

    public void say40() {
        score += 40;
        said40 = true;
    }

    public boolean isValidChosenCard(ICard card) {
        if (!said20 && !said40) {
            return true;
        }
        if (said20
                && (card.getRank() == HungarianCardRank.FELSO
                || card.getRank() == HungarianCardRank.KIRALY)) {
            return true;
        }
        if (said40 && card.getSuit() == getTrump()
                && (card.getRank() == HungarianCardRank.FELSO
                || card.getRank() == HungarianCardRank.KIRALY)) {
            return true;
        }
        return false;
    }

    public boolean isValidChosenCard(ICard calledCard, ICard chosenCard) {
        return cards.stream().filter(card -> {
            return card.getSuit() == calledCard.getSuit();
        }).max((a, b) -> a.compareTo(b)).equals(chosenCard);
    }

}
