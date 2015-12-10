package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.Game;

/**
 * Created by Fecó on 2015.12.06..
 */
public class CoverOperator implements Operator {
    @Override
    public boolean isApplicable(Game game) {
        return game.canCover();
    }

    @Override
    public void apply(Game game) {
        game.setCover(true);
    }
}
