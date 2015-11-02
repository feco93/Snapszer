/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fecó
 */
public class Game {

    private IDeck deck;
    private static Game game;
    private Player currentPlayer;
    private Player nextPlayer;
    private HungarianCard trumpCard;
    private List<HungarianCard> cardsOnTable;
    private boolean cover;

    public boolean canCover() {
        return deck.size() >= 4;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    private Game(int playerNumbers) {
        currentPlayer = new Human();
        if (playerNumbers == 2) {
            nextPlayer = new Human();
        } else {
            nextPlayer = new Computer();
        }
        initDeck();
        initPlayers();
    }

    public static Game startNewGame(int playerNumbers) {
        game = new Game(playerNumbers);
        return game;
    }

    private void initDeck() {
        List<ICard> cards = new ArrayList<>();
        for (HungarianCardSuit suit : HungarianCardSuit.values()) {
            for (HungarianCardRank rank : HungarianCardRank.values()) {
                if (rank == HungarianCardRank.KILENC || rank == HungarianCardRank.TIZ
                        || rank == HungarianCardRank.NYOLC) {
                    continue;
                }
                HungarianCard card = new HungarianCard(rank, suit);
                cards.add(card);
            }
        }
        deck = new Deck(cards);
        deck.shuffle();
    }

    private void initPlayers() {
        currentPlayer.drawCards(deck.drawCards(3));
        nextPlayer.drawCards(deck.drawCards(3));
        trumpCard = (HungarianCard)deck.drawCard();
        deck.insertCard(trumpCard, 0);
        trumpCard.getSuit().setTrump(true);
        currentPlayer.drawCards(deck.drawCards(2));
        nextPlayer.drawCards(deck.drawCards(2));
    }

    private void putCard() {
        cardsOnTable.add((HungarianCard)currentPlayer.getChosenCard());
        swapPlayers();
    }

    public boolean canSwapTrumpCard() {
        return deck.size() >= 4 && !cover;
    }

    public void swapTrumpCard() {
        for (int i = 0; i < currentPlayer.cards.size(); ++i) {
            if (currentPlayer.cards.get(i).getSuit() == trumpCard.getSuit()
                    && currentPlayer.cards.get(i).getRank() == HungarianCardRank.ALSO) {
                ICard temp = trumpCard;
                trumpCard = (HungarianCard)currentPlayer.cards.remove(i);
                currentPlayer.drawCard(temp);
            }
        }
    }

    private void beatPhase() {
        if (cardsOnTable.get(1).compareTo(cardsOnTable.get(0)) > 0) {
            swapPlayers();
        }
        currentPlayer.addScore(cardsOnTable.get(0).getPoints() + cardsOnTable.get(1).getPoints());
        cardsOnTable.clear();
    }

    private void drawPhase() {
        currentPlayer.drawCard(deck.drawCard());
        nextPlayer.drawCard(deck.drawCard());
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public IDeck getDeck() {
        return deck;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public void swapPlayers() {
        Player temp = currentPlayer;
        currentPlayer = nextPlayer;
        nextPlayer = temp;
    }

}
