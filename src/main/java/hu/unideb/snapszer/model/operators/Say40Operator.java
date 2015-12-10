package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.Game;

/**
 * Created by Fecó on 2015.12.06..
 */
public class Say40Operator implements Operator {
    @Override
    public boolean isApplicable(Game game) {
        return game.getCurrentPlayer().canSay40(game.getTrumpCard().getSuit());
    }

    @Override
    public void apply(Game game) {
        game.getCurrentPlayer().say40();
    }
}
