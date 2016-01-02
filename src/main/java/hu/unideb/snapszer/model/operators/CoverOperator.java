package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.Player;
import hu.unideb.snapszer.model.SnapszerTwoPlayerGame;

/**
 * Created by Fecó on 2015.12.06..
 */
public class CoverOperator extends Operator {

    public CoverOperator(Player player) {
        super(player);
    }

    @Override
    public boolean isApplicable(SnapszerTwoPlayerGame game) {
        if (!player.equals(game.getCurrentPlayer()))
            return false;
        return game.getDeck().size() >= 4;
    }

    @Override
    public void onApply(SnapszerTwoPlayerGame game) {
        game.setCover(true);
    }
}
