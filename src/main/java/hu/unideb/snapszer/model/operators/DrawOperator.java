package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.SnapszerTwoPlayerGame;
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
    public boolean isApplicable(SnapszerTwoPlayerGame game) {
        if (!game.isCover() && !game.getDeck().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onApply(SnapszerTwoPlayerGame game) {
        card = (HungarianCard) game.getDeck().drawCard();
        player.drawCard(card);
    }
}
