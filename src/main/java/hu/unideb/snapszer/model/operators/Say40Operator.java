package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.HungarianCardRank;
import hu.unideb.snapszer.model.HungarianCardSuit;
import hu.unideb.snapszer.model.ICard;
import hu.unideb.snapszer.model.player.Player;

/**
 * Created by Fecó on 2015.12.06..
 */
public class Say40Operator extends Operator {

    public Say40Operator(Player player) {
        super(player);
    }

    @Override
    public boolean isApplicable(GameMatch game) {
        return player.equals(game.getCurrentPlayer()) &&
                canSay40(game.getTrumpCard().getSuit());
    }

    private boolean canSay40(HungarianCardSuit suit) {
        return !(player.isSaid40() || player.isSaid20()) &&
                player.getCards().stream().anyMatch(
                        (ICard card) -> card.getSuit() == suit &&
                                card.getRank() == HungarianCardRank.FELSO) &&
                player.getCards().stream().anyMatch(
                        (ICard card) -> card.getSuit() == suit &&
                                card.getRank() == HungarianCardRank.KIRALY);
    }

    private void say40() {
        player.addScore(40);
        player.setSaid40(true);
    }

    @Override
    public void onApply(GameMatch game) {
        say40();
    }
}
