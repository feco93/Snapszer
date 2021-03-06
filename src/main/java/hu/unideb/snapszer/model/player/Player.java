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
package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.GameState;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.ICard;
import hu.unideb.snapszer.model.operators.Operator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Model abstract class for a player.
 *
 * @author Feco
 */
public abstract class Player {

    protected static final Logger logger = LogManager.getLogger(Player.class);

    private final String name;

    /**
     * Cards in this player hand.
     */
    protected ObservableList<HungarianCard> cards;
    /**
     * Score of this player.
     */
    private IntegerProperty score;

    private IntegerProperty points;

    private int wonGame;

    private boolean said20;
    private boolean said40;
    private boolean saidCover;
    private boolean saidSnapszer;

    private int beatsCounter;

    /**
     * Constructs a new Player object.
     */
    public Player(String name) {
        this.name = name;
        wonGame = 0;
        points = new SimpleIntegerProperty(0);
        score = new SimpleIntegerProperty(0);
    }

    public void incWonGame() {
        ++wonGame;
    }

    public int getWonGame() {
        return wonGame;
    }

    public int getBeatsCounter() {
        return beatsCounter;
    }

    public void setBeatsCounter(int beatsCounter) {
        this.beatsCounter = beatsCounter;
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public IntegerProperty pointsProperty() {
        return points;
    }

    public int getPoints() {
        return points.get();
    }

    public String getName() {
        return name;
    }

    public void newGame() {
        points.setValue(0);
    }

    public void newMatch() {
        beatsCounter = 0;
        cards = FXCollections.observableArrayList();
        score.setValue(0);
        said20 = false;
        said40 = false;
        saidCover = false;
        saidSnapszer = false;
    }

    public abstract Operator chooseOperator(List<Operator> operators, GameState gameState);

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
    public void drawCards(List<HungarianCard> cards) {
        this.cards.addAll(cards);
    }

    /**
     * Draws a card.
     *
     * @param card
     */
    public void drawCard(HungarianCard card) {
        cards.add(card);
    }

    public ObservableList<HungarianCard> getCards() {
        return cards;
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

    public boolean isSaid20() {
        return said20;
    }

    public void setSaid20(boolean said20) {
        this.said20 = said20;
    }

    public boolean isSaid40() {
        return said40;
    }

    public void setSaid40(boolean said40) {
        this.said40 = said40;
    }

    public boolean isSaidCover() {
        return saidCover;
    }

    public void setSaidCover(boolean saidCover) {
        this.saidCover = saidCover;
    }

    public boolean isSaidSnapszer() {
        return saidSnapszer;
    }

    public void setSaidSnapszer(boolean saidSnapszer) {
        this.saidSnapszer = saidSnapszer;
    }
}
