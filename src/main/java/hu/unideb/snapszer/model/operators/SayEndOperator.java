package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.player.Player;

/**
 * Created by Fecó on 2015.12.06..
 */
public class SayEndOperator extends Operator {

    public SayEndOperator(Player player) {
        super(player);
    }

    @Override
    public boolean isApplicable(GameMatch game) {
        return player.equals(game.getCurrentPlayer()) && player.getScore() >= 66;
    }

    @Override
    public void onApply(GameMatch game) {

    }

}
