package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.Game;

/**
 * Created by Fecó on 2015.12.06..
 */
public class SayEndOperator implements Operator {
    @Override
    public boolean isApplicable(Game game) {
        return game.getCurrentPlayer().canSayEnd();
    }

    @Override
    public void apply(Game game) {
    }
}
