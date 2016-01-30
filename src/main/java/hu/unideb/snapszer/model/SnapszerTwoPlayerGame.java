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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author Fecó
 */
public class SnapszerTwoPlayerGame extends Task<Void> {

    private final Logger logger = LoggerFactory.getLogger(SnapszerTwoPlayerGame.class);
    private Deck deck;
    private Player currentPlayer;
    private Player otherPlayer;
    private ObservableList<HungarianCard> cardsOnTable;
    private ObservableList<HungarianCard> playedCards;
    private ObjectProperty<HungarianCard> trumpCard;
    private boolean cover;
    private boolean snapszer;
    private Player sayerPlayer;

    public SnapszerTwoPlayerGame(Player playerOne, Player playerTwo, Deck deck) {
        playerOne.initPlayer();
        playerTwo.initPlayer();
        currentPlayer = playerOne;
        otherPlayer = playerTwo;
        cover = false;
        snapszer = false;
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

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
        this.sayerPlayer = getCurrentPlayer();
    }

    public boolean isSnapszer() {
        return snapszer;
    }

    public void setSnapszer(boolean snapszer) {
        this.snapszer = snapszer;
        this.sayerPlayer = getCurrentPlayer();
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
        logger.trace("Beat Phase");
        int index = cardsOnTable.indexOf(getHighestCard());
        if (index > 0) {
            swapPlayers();
        }
        for (HungarianCard card :
                cardsOnTable) {
            currentPlayer.addScore(card.getPoints());
        }
        currentPlayer.setBeatsCounter(currentPlayer.getBeatsCounter() + 1);
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
        if (isCover()) {
            Player notSayerPlayer = getPlayers().stream().
                    filter(player -> !player.equals(sayerPlayer)).findFirst().get();
            if (sayerPlayer.getScore() < 66) {
                notSayerPlayer.addPoints(1);
            } else {
                if (notSayerPlayer.getBeatsCounter() == 0) {
                    sayerPlayer.addPoints(3);
                } else if (notSayerPlayer.getScore() < 33) {
                    sayerPlayer.addPoints(2);
                } else {
                    sayerPlayer.addPoints(1);
                }
            }
        } else if (isSnapszer()) {
            Player notSayerPlayer = getPlayers().stream().
                    filter(player -> !player.equals(sayerPlayer)).findFirst().get();
            if (sayerPlayer.getScore() < 66 || notSayerPlayer.getBeatsCounter() > 0) {
                notSayerPlayer.addPoints(6);
            } else {
                sayerPlayer.addPoints(6);
            }
        } else {
            if (loserPlayer.getBeatsCounter() == 0)
                winnerPlayer.addPoints(3);
            else if (loserPlayer.getScore() < 33)
                winnerPlayer.addPoints(2);
            else {
                winnerPlayer.addPoints(1);
            }
        }
        return null;
    }

    private Player playMatch() {
        while (true) {
            if (currentPlayer.cards.isEmpty() &&
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