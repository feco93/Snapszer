package hu.unideb.snapszer;

/**
 * Created by Fecó Sipos on 2016. 01. 31..
 */
public class GameKernel {

    private final Game game;

    public GameKernel(GameMode mode) {
        switch (mode) {
            case CONSOLE:
                game = new ConsoleGame();
                break;
            case GUI:
                game = new UIGame();
                break;
            default:
                game = new ConsoleGame();
                break;
        }
        game.Start();
    }
}
