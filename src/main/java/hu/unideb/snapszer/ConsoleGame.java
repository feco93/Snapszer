package hu.unideb.snapszer;

import hu.unideb.snapszer.model.Computer;
import hu.unideb.snapszer.model.Player;
import hu.unideb.snapszer.model.SnapszerTwoPlayerGame;

/**
 * Created by Fecó Sipos on 2016. 01. 31..
 */
public class ConsoleGame implements Game {
    @Override
    public void Start() {
        Player playerOne = new Computer();
        Player playerTwo = new Computer();
        SnapszerTwoPlayerGame game = new SnapszerTwoPlayerGame(playerOne, playerTwo);
        Thread gameThread = new Thread(game);
        gameThread.start();
    }
}
