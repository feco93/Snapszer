package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.*;

/**
 * Created by Fecó on 2015.12.06..
 */
public class SwapTrumpOperator extends Operator {


    private HungarianCard oldTrumpCard;
    private HungarianCard newTrumpCard;

    public SwapTrumpOperator(Player player) {
        super(player);
    }

    public HungarianCard getOldTrumpCard() {
        return oldTrumpCard;
    }

    public HungarianCard getNewTrumpCard() {
        return newTrumpCard;
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
        oldTrumpCard = game.getTrumpCard();
        game.getDeck().cards.remove(oldTrumpCard);
        newTrumpCard = (HungarianCard) player.cards.stream().filter(iCard ->
                iCard.getSuit() == game.getTrumpCard().getSuit() &&
                        iCard.getRank() == HungarianCardRank.ALSO).findFirst().get();
        game.trumpCardProperty().setValue(newTrumpCard);
        player.cards.set(player.cards.indexOf(newTrumpCard), oldTrumpCard);
    }
}
