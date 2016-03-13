package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.player.Player;

/**
 * Created by Fecó on 2016.01.02..
 */
public class SnapszerOperator extends Operator {

    public SnapszerOperator(Player player) {
        super(player);
    }

    @Override
    public boolean isApplicable(GameMatch game) {
        return !game.isCover() &&
                !game.isSnapszer() &&
                game.getDeck().size() == 10;
    }

    @Override
    protected void onApply(GameMatch game) {
        game.setSnapszer(true);
        player.setSaidSnapszer(true);
    }
}
