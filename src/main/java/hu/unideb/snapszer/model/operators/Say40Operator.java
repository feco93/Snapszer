package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.Game;
import hu.unideb.snapszer.model.Player;

/**
 * Created by Fecó on 2015.12.06..
 */
public class Say40Operator extends Operator {

    public Say40Operator(Player player) {
        super(player);
    }

    @Override
    public boolean isApplicable(Game game) {
        if (!player.equals(game.getCurrentPlayer()))
            return false;
        return player.canSay40(game.getTrumpCard().getSuit());
    }

    @Override
    public void onApply(Game game) {
        player.say40();
    }
}
