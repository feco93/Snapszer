package hu.unideb.snapszer;

import hu.unideb.snapszer.model.SnapszerTwoPlayerGame;
import hu.unideb.snapszer.model.player.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Created by Fecó Sipos on 2016. 01. 31..
 */
public class ConsoleGame implements Game {

    private static final Logger logger = LogManager.getLogger(ConsoleGame.class);
    private final Service<Player> gameService;
    private final Player playerOne;
    private final Player playerTwo;
    private SnapszerTwoPlayerGame game;
    private int numberOfGames;

    public ConsoleGame() {
        playerOne = new ComputerRand();
        playerTwo = new ComputerExpert();
        gameService = new Service<Player>() {

            @Override
            protected Task<Player> createTask() {
                return new Task<Player>() {
                    @Override
                    protected Player call() throws Exception {
                        game = new SnapszerTwoPlayerGame(playerOne, playerTwo);
                        game.run();
                        return game.getWinnerPlayer();
                    }
                };
            }
        };
        getGameService().setOnSucceeded(event ->
                {
                    Player player = (Player) event.getSource().getValue();
                    player.incWonGame();

                    playGame();
                }
        );
    }

    public Service<Player> getGameService() {
        return gameService;
    }

    private void playGame() {
        if (numberOfGames > 0) {
            this.numberOfGames--;
            gameService.restart();
        } else {
            for (Player player :
                    Arrays.asList(playerOne, playerTwo)) {
                logger.info(
                        String.format("The %s has won %d game.", player.getName(), player.getWonGame()));
            }
        }
    }

    @Override
    public void Start(int numberOfGames) {
        this.numberOfGames = numberOfGames;
        playGame();
    }
}
