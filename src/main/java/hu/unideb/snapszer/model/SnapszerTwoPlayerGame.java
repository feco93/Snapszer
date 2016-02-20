/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * @author Fecó
 */
public class SnapszerTwoPlayerGame extends Task<Void> {

    private static final Logger logger = LogManager.getLogger(SnapszerTwoPlayerGame.class);
    private Deck deck;
    private Player playerOne;
    private Player playerTwo;
    private ObjectProperty<GameMatch> gameMatch;


    public SnapszerTwoPlayerGame(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        gameMatch = new SimpleObjectProperty<>();
    }

    public ObjectProperty<GameMatch> gameMatchProperty() {
        return gameMatch;
    }

    public GameMatch getGameMatch() {
        return gameMatch.get();
    }

    private boolean isGameOver() {
        return playerOne.pointsProperty().get() >= 7 || playerTwo.pointsProperty().get() >= 7;
    }

    private void updatePoints(Player winnerPlayer) {
        GameMatch gameMatch = getGameMatch();
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
            deck = SnapszerDeck.getNewDeck();
            playerOne.initPlayer();
            playerTwo.initPlayer();
            gameMatch.setValue(new GameMatch(Arrays.asList(playerOne, playerTwo), deck));
            logger.trace("The match is starting...");
            Player winnerPlayer = getGameMatch().play();
            logger.trace("The match has been finished");
            updatePoints(winnerPlayer);
        }
        return null;
    }

}
