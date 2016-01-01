package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.*;

/**
 * Created by Fecó on 2015.12.06..
 */
public class SwapTrumpOperator extends Operator {


    public SwapTrumpOperator(Player player) {
        super(player);
    }

    @Override
    public boolean isApplicable(Game game) {
        if (!player.equals(game.getCurrentPlayer()))
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
    public void onApply(Game game) {
        HungarianCard oldTrumpCard = game.getTrumpCard();
        HungarianCard newTrumpCard = (HungarianCard) player.cards.stream().filter(iCard ->
                iCard.getSuit() == game.getTrumpCard().getSuit() &&
                        iCard.getRank() == HungarianCardRank.ALSO).findFirst().get();
        game.trumpCardProperty().setValue(newTrumpCard);
        player.cards.set(player.cards.indexOf(newTrumpCard), oldTrumpCard);
    }
}
