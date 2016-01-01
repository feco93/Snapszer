package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.Game;
import hu.unideb.snapszer.model.Player;

/**
 * Created by Fecó on 2016.01.01..
 */
public class DrawOperator extends Operator {

    public DrawOperator(Player player) {
        super(player);
    }

    @Override
    public boolean isApplicable(Game game) {
        if (!game.isCover() && !game.getDeck().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onApply(Game game) {
        player.drawCard(game.getDeck().drawCard());
    }
}
