/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

import hu.unideb.snapszer.model.player.Player;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        this.playerOne.newGame();
        this.playerTwo.newGame();
        gameMatch = new SimpleObjectProperty<>();
    }

    public ObjectProperty<GameMatch> gameMatchProperty() {
        return gameMatch;
    }

    public GameMatch getGameMatch() {
        return gameMatch.get();
    }

    public Player getWinnerPlayer() {
        return playerOne.getPoints() >= 7 ? playerOne : playerTwo;
    }

    private boolean isGameOver() {
        return playerOne.getPoints() >= 7 || playerTwo.getPoints() >= 7;
    }

    @Override
    protected Void call() throws Exception {
        while (!isGameOver()) {
            deck = SnapszerDeck.getNewDeck();

            gameMatch.setValue(new GameMatch(playerOne, playerTwo, deck));
            logger.info("The match is starting...");
            getGameMatch().play();
            logger.info("The match has been finished");
            Player winnerPlayer = getGameMatch().getWinnerPlayer();
            int wonPoints = getGameMatch().getWonPoints();
            logger.info(String.format("The %s has won the match", winnerPlayer.getName()));
            logger.info(String.format("Won points: %d", wonPoints));
            winnerPlayer.addPoints(wonPoints);
        }
        Player winnerPlayer = getWinnerPlayer();
        logger.info("--------------------------------------------------------------");
        logger.info(String.format(
                "The %s has won the game",
                winnerPlayer.getName()));
        logger.info("--------------------------------------------------------------");
        return null;
    }

}
