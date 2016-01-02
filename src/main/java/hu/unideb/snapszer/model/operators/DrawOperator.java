package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.Game;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.Player;

/**
 * Created by Fecó on 2016.01.01..
 */
public class DrawOperator extends Operator {

    private HungarianCard card;

    public DrawOperator(Player player) {
        super(player);
    }

    public HungarianCard getCard() {
        return card;
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
        card = (HungarianCard) game.getDeck().drawCard();
        player.drawCard(card);
    }
}
