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

import hu.unideb.snapszer.model.operators.Operator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    private IntegerProperty score;

    private IntegerProperty points;

    private boolean said20;
    private boolean said40;

    private int beatsCounter;

    public int getBeatsCounter() {
        return beatsCounter;
    }

    public void setBeatsCounter(int beatsCounter) {
        this.beatsCounter = beatsCounter;
    }

    public void setSaid20(boolean said20) {
        this.said20 = said20;
    }

    public void setSaid40(boolean said40) {
        this.said40 = said40;
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public IntegerProperty pointsProperty() {
        return points;
    }

    /**
     * Constructs a new Player object.
     */
    public Player() {
        beatsCounter = 0;
        cards = FXCollections.observableArrayList();
        score = new SimpleIntegerProperty(0);
        points = new SimpleIntegerProperty(0);
        said20 = false;
        said40 = false;
    }

    public abstract Operator chooseOperator(SnapszerTwoPlayerGame game);

    /**
     * Add the specified score to the score of this player.
     *
     * @param score how many score to be added to the score of this player
     */
    public void addScore(int score) {

        this.score.set(this.score.get() + score);
    }

    public void addPoints(int points) {
        this.points.set(this.points.get() + points);
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
     * Gets the value of score.
     *
     * @return the score of this player
     */
    public int getScore() {
        return score.get();
    }

    public void removeCard(ICard card) {
        cards.remove(card);
    }

    public boolean canSay20() {
        if (said20) {
            return false;
        }
        for (ICard card : cards) {
            if (card.getRank() == HungarianCardRank.KIRALY) {
                if (cards.stream().anyMatch((ICard item) -> card.getSuit() == item.getSuit()
                        && item.getRank() == HungarianCardRank.FELSO)) {
                    return true;
                }
            }
            if (card.getRank() == HungarianCardRank.FELSO) {
                if (cards.stream().anyMatch((ICard item) -> card.getSuit() == item.getSuit()
                        && item.getRank() == HungarianCardRank.KIRALY)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canSay40(HungarianCardSuit suit) {
        if (said40) {
            return false;
        }
        return cards.stream().anyMatch((ICard card) -> {
            return card.getSuit() == suit && card.getRank() == HungarianCardRank.FELSO;
        }) && cards.stream().anyMatch((ICard card) -> {
            return card.getSuit() == suit && card.getRank() == HungarianCardRank.KIRALY;
        });
    }

    public boolean canSayEnd() {
        return getScore() >= 66;
    }

    public void say20() {
        this.score.set(this.score.get() + 20);
        said20 = true;
    }

    public void say40() {
        this.score.set(this.score.get() + 40);
        said40 = true;
    }

    public abstract void setChosenOperator(Operator operator);


    public boolean isSaid20() {
        return said20;
    }

    public boolean isSaid40() {
        return said40;
    }
}
