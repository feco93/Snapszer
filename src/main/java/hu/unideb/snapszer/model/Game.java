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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Fecó
 */
public class Game implements Runnable {

    public Deck getDeck() {
        return deck;
    }

    private Deck deck;
    private Player currentPlayer;
    private List<Player> players;

    private ObservableList<HungarianCard> cardsOnTable;
    private ObservableList<HungarianCard> playedCards;
    private ObjectProperty<HungarianCard> trumpCard;
    private boolean cover;

    public Game(Player playerOne, Player playerTwo, Deck deck) {
        currentPlayer = playerOne;
        Player nextPlayer = playerTwo;
        players = Arrays.asList(currentPlayer, nextPlayer);
        this.deck = deck;
        this.cardsOnTable = FXCollections.observableArrayList();
        this.playedCards = FXCollections.observableArrayList();
    }

    private void initGame() {
        for (Player player :
                players) {
            player.drawCards(deck.drawCards(3));
        }
        trumpCard = new SimpleObjectProperty<>();
        trumpCard.set((HungarianCard) deck.drawCard());
        deck.insertCard(trumpCard.get(), 0);
        trumpCard.get().getSuit().setTrump(true);
        for (Player player :
                players) {
            player.drawCards(deck.drawCards(2));
        }
    }

    public void run() {
        initGame();
        while (true) {
            for (Player player :
                    players) {
                while (true) {
                    Operator op = player.chooseOperator(this);
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

    private HungarianCard getHighestCard() {
        for (HungarianCard card :
                cardsOnTable) {
            boolean isHighest = true;
            for (HungarianCard card2 :
                    cardsOnTable) {
                if (card2.equals(card))
                    continue;
                if (card.compareTo(card2) < 0)
                    isHighest = false;
            }
            if (isHighest) {
                return card;
            }
        }
        return null;
    }

    private void beatPhase() {
        int index = cardsOnTable.indexOf(getHighestCard());
        if (index > 0) {
            swapPlayers(index);
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
            for (Player player :
                    players) {
                player.drawCard(deck.drawCard());
            }
        }
    }

    private void swapPlayers(int index) {
        currentPlayer = players.get(index);
        List<Player> temp = new ArrayList<>();
        temp.addAll(players.subList(index, players.size()));
        temp.addAll(players.subList(0, index));
        players = temp;
    }

}
