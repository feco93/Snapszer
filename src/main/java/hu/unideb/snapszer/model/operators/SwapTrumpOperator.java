package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.HungarianCardRank;
import hu.unideb.snapszer.model.Player;

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
    public boolean isApplicable(GameMatch game) {
        if (!player.equals(game.getCurrentPlayer()) ||
                game.getDeck().size() < 4 || game.isCover())
            return false;
        return player.cards.stream().anyMatch(iCard ->
                iCard.getSuit() == game.getTrumpCard().getSuit() &&
                        iCard.getRank() == HungarianCardRank.ALSO);
    }

    @Override
    public void onApply(GameMatch game) {
        oldTrumpCard = game.getTrumpCard();
        game.getDeck().cards.remove(oldTrumpCard);
        newTrumpCard = (HungarianCard) player.cards.stream().filter(iCard ->
                iCard.getSuit() == game.getTrumpCard().getSuit() &&
                        iCard.getRank() == HungarianCardRank.ALSO).findFirst().get();
        game.trumpCardProperty().setValue(newTrumpCard);
        player.cards.set(player.cards.indexOf(newTrumpCard), oldTrumpCard);
    }
}
