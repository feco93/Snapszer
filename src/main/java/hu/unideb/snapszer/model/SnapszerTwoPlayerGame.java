/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

import hu.unideb.snapszer.model.operators.CallOperator;
import hu.unideb.snapszer.model.operators.DrawOperator;
import hu.unideb.snapszer.model.operators.Operator;
import hu.unideb.snapszer.model.operators.SayEndOperator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.Arrays;
import java.util.List;

/**
 * @author Fecó
 */
public class SnapszerTwoPlayerGame extends Task<Void> {

    private Deck deck;
    private Player currentPlayer;
    private Player otherPlayer;
    private ObservableList<HungarianCard> cardsOnTable;
    private ObservableList<HungarianCard> playedCards;
    private ObjectProperty<HungarianCard> trumpCard;
    private boolean cover;
    public SnapszerTwoPlayerGame(Player playerOne, Player playerTwo, Deck deck) {
        playerOne.initPlayer();
        playerTwo.initPlayer();
        currentPlayer = playerOne;
        otherPlayer = playerTwo;
        this.deck = deck;
        this.cardsOnTable = FXCollections.observableArrayList();
        this.playedCards = FXCollections.observableArrayList();
        trumpCard = new SimpleObjectProperty<>();
        trumpCard.addListener((observable) -> {
            deck.insertCard(trumpCard.getValue(), 0);
            trumpCard.getValue().getSuit().setTrump(true);
        });
    }

    public Deck getDeck() {
        return deck;
    }

    private void initGame() {
        drawPhase(3);
        trumpCard.set((HungarianCard) deck.drawCard());
        drawPhase(2);
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

    public ObjectProperty<HungarianCard> trumpCardProperty() {
        return trumpCard;
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

    public Player getOtherPlayer() {
        return otherPlayer;
    }

    public List<Player> getPlayers() {
        return Arrays.asList(currentPlayer, otherPlayer);
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
            otherPlayer.setBeatsCounter(otherPlayer.getBeatsCounter() + 1);
            swapPlayers();
        }
        for (HungarianCard card :
                cardsOnTable) {
            currentPlayer.addScore(card.getPoints());
        }
        playedCards.addAll(cardsOnTable);
        cardsOnTable.clear();
    }

    private void drawPhase(int numberOfCards) {
        for (Player player :
                Arrays.asList(currentPlayer, otherPlayer)) {
            for (int i = 0; i < numberOfCards; ++i) {
                DrawOperator op = new DrawOperator(player);
                if (op.isApplicable(this)) {
                    op.apply(this);
                }
            }
        }
    }

    private void swapPlayers() {
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }

    @Override
    protected Void call() throws Exception {
        initGame();
        Player winnerPlayer = playMatch();
        Player loserPlayer = getPlayers().stream().
                filter(player -> !player.equals(winnerPlayer)).findFirst().get();
        if (loserPlayer.getScore() < 33)
            if (loserPlayer.getBeatsCounter() == 0)
                winnerPlayer.addPoints(3);
            else {
                winnerPlayer.addPoints(2);
            }
        else {
            winnerPlayer.addPoints(1);
        }
        return null;
    }

    private Player playMatch() {
        while (true) {
            if (deck.isEmpty() && currentPlayer.cards.isEmpty() &&
                    otherPlayer.cards.isEmpty())
                break;
            for (Player player :
                    Arrays.asList(currentPlayer, otherPlayer)) {
                while (true) {
                    Operator op = player.chooseOperator(this);
                    if (op.isApplicable(this)) {
                        op.apply(this);
                        if (op instanceof CallOperator) {
                            break;
                        }
                        if (op instanceof SayEndOperator) {
                            return player;
                        }
                    }
                }
            }
            beatPhase();
            drawPhase(1);
        }
        return currentPlayer;
    }
}
