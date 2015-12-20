/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

import hu.unideb.snapszer.model.operators.CallOperator;
import hu.unideb.snapszer.model.operators.Operator;
import hu.unideb.snapszer.model.operators.SayEndOperator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Fecó
 */
public class Game implements Runnable {

    public Deck getDeck() {
        return deck;
    }

    private Deck deck;
    private Player currentPlayer;
    private Player nextPlayer;

    public Player getNextPlayer() {
        return nextPlayer;
    }

    private ObservableList<HungarianCard> cardsOnTable;
    private ObservableList<HungarianCard> playedCards;
    private ObjectProperty<HungarianCard> trumpCard;
    private boolean cover;

    public Game(Player playerOne, Player playerTwo, Deck deck) {
        currentPlayer = playerOne;
        nextPlayer = playerTwo;
        this.deck = deck;
        this.cardsOnTable = FXCollections.observableArrayList();
        this.playedCards = FXCollections.observableArrayList();
    }

    private void initGame() {
        currentPlayer.drawCards(deck.drawCards(3));
        nextPlayer.drawCards(deck.drawCards(3));
        trumpCard = new SimpleObjectProperty<>();
        trumpCard.set((HungarianCard) deck.drawCard());
        deck.insertCard(trumpCard.get(), 0);
        trumpCard.get().getSuit().setTrump(true);
        currentPlayer.drawCards(deck.drawCards(2));
        nextPlayer.drawCards(deck.drawCards(2));
    }

    public void run() {
        initGame();
        while (true) {
            for (int i = 0; i < 2; ++i) {
                while (true) {
                    Operator op = currentPlayer.chooseOperator(this);
                    if (op.isApplicable(this)) {
                        op.apply(this);
                        if (op instanceof CallOperator) {
                            break;
                        }
                        if (op instanceof SayEndOperator) {
                            return;
                        }
                    }
                }
                swapPlayers();
            }
            beatPhase();
            drawPhase();
        }
    }

    public boolean canCover() {
        return deck.size() >= 4;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public HungarianCard getTrumpCard() {
        return trumpCard.get();
    }

    public ObservableList<HungarianCard> getCardsOnTable() {
        return cardsOnTable;
    }

    public ObservableList<HungarianCard> getPlayedCards() {
        return playedCards;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    private void beatPhase() {
        if (cardsOnTable.get(1).compareTo(cardsOnTable.get(0)) > 0) {
            swapPlayers();
        }
        for (HungarianCard card :
                cardsOnTable) {
            currentPlayer.addScore(card.getPoints());
        }
        playedCards.addAll(cardsOnTable);
        cardsOnTable.clear();
    }

    private void drawPhase() {
        if (!isCover() && !deck.isEmpty()) {
            currentPlayer.drawCard(deck.drawCard());
            nextPlayer.drawCard(deck.drawCard());
        }
    }

    private void swapPlayers() {
        Player temp = currentPlayer;
        currentPlayer = nextPlayer;
        nextPlayer = temp;
    }

}
