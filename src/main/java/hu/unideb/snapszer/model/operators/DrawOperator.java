package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.player.Player;

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
    public boolean isApplicable(GameMatch game) {
        return !game.isCover() && !game.getDeck().isEmpty();
    }

    @Override
    protected void onApply(GameMatch game) {
        card = (HungarianCard) game.getDeck().drawCard();
        player.drawCard(card);
    }
}
