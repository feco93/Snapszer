package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.SnapszerTwoPlayerGame;
import hu.unideb.snapszer.model.Player;

/**
 * Created by Fecó on 2015.12.06..
 */
public class CoverOperator extends Operator {

    protected CoverOperator(Player player) {
        super(player);
    }

    @Override
    public boolean isApplicable(SnapszerTwoPlayerGame game) {
        if (!player.equals(game.getCurrentPlayer()))
            return false;
        return game.canCover();
    }

    @Override
    public void onApply(SnapszerTwoPlayerGame game) {
        game.setCover(true);
    }
}
