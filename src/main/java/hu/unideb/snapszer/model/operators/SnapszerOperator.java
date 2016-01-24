package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.Player;
import hu.unideb.snapszer.model.SnapszerTwoPlayerGame;

/**
 * Created by Fecó on 2016.01.02..
 */
public class SnapszerOperator extends Operator {

    public SnapszerOperator(Player player) {
        super(player);
    }

    @Override
    public boolean isApplicable(SnapszerTwoPlayerGame game) {
        return game.getDeck().size() == 10;
    }

    @Override
    protected void onApply(SnapszerTwoPlayerGame game) {
        game.setSnapszer(true);
    }
}
