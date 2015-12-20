package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.*;

/**
 * Created by Fecó on 2015.12.06..
 */
public class SwapTrumpOperator implements Operator {

    private Player player;

    public SwapTrumpOperator(Player player) {

        this.player = player;
    }

    @Override
    public boolean isApplicable(Game game) {
        if (player != game.getCurrentPlayer())
            return false;
        if (game.getDeck().size() < 4 && game.isCover())
            return false;
        if (player.cards.stream().anyMatch(iCard ->
                iCard.getSuit() == game.getTrumpCard().getSuit() &&
                        iCard.getRank() == HungarianCardRank.ALSO)) {
            return true;
        }
        return false;
    }

    @Override
    public void apply(Game game) {
        player.cards.remove(player.cards.stream().filter(iCard ->
                iCard.getSuit() == game.getTrumpCard().getSuit() &&
                        iCard.getRank() == HungarianCardRank.ALSO).findFirst().get());
    }
}
