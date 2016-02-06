package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.Player;

/**
 * Created by Fecó on 2015.12.06..
 */
public class SayEndOperator extends Operator {

    public SayEndOperator(Player player) {
        super(player);
    }

    @Override
    public boolean isApplicable(GameMatch game) {
        if (!player.equals(game.getCurrentPlayer()))
            return false;
        return player.canSayEnd();
    }

    @Override
    public void onApply(GameMatch game) {

    }

}
