package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.Player;

/**
 * Created by Fecó on 2015.12.06..
 */
public class Say20Operator extends Operator {

    public Say20Operator(Player player) {
        super(player);
    }

    @Override
    public boolean isApplicable(GameMatch game) {
        if (!player.equals(game.getCurrentPlayer()))
            return false;
        return player.canSay20();
    }

    @Override
    public void onApply(GameMatch game) {
        player.say20();
    }
}
