/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fecó
 */
public class SnapszerTwoPlayerGame extends Task<Void> {

    private final static Logger logger = LoggerFactory.getLogger(SnapszerTwoPlayerGame.class);
    private Deck deck;
    private Player playerOne;
    private Player playerTwo;
    private ObjectProperty<GameMatch> gameMatchProperty;

    public SnapszerTwoPlayerGame(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        gameMatchProperty = new SimpleObjectProperty<>();
    }

    public GameMatch getGameMatchProperty() {
        return gameMatchProperty.get();
    }

    public ObjectProperty<GameMatch> gameMatchPropertyProperty() {
        return gameMatchProperty;
    }

    private boolean isGameOver() {
        return playerOne.pointsProperty().get() >= 7 || playerTwo.pointsProperty().get() >= 7;
    }

    private void updatePoints(Player winnerPlayer) {
        GameMatch gameMatch = gameMatchProperty.get();
        Player loserPlayer = gameMatch.getPlayers().stream().
                filter(player -> !player.equals(winnerPlayer)).findFirst().get();
        if (gameMatch.isCover()) {
            Player sayerPlayer = gameMatch.getSayerPlayer();
            Player notSayerPlayer = gameMatch.getPlayers().stream().
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
        } else if (gameMatch.isSnapszer()) {
            Player sayerPlayer = gameMatch.getSayerPlayer();
            Player notSayerPlayer = gameMatch.getPlayers().stream().
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
    }

    @Override
    protected Void call() throws Exception {
        while (!isGameOver()) {
            this.deck = SnapszerDeck.getNewDeck();
            gameMatchProperty.setValue(new GameMatch(playerOne, playerTwo, deck));
            GameMatch gameMatch = gameMatchProperty.get();
            Player winnerPlayer = gameMatch.play();
            updatePoints(winnerPlayer);
        }
        return null;
    }

}
