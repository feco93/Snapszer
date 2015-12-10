package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.Game;

/**
 * Created by Fecó on 2015.12.06..
 */
public class Say20Operator implements Operator {
    @Override
    public boolean isApplicable(Game game) {
        return game.getCurrentPlayer().canSay20();
    }

    @Override
    public void apply(Game game) {
        game.getCurrentPlayer().say20();
    }
}
