package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.HungarianCardRank;
import hu.unideb.snapszer.model.ICard;
import hu.unideb.snapszer.model.player.Player;

/**
 * Created by Fecó on 2015.12.06..
 */
public class Say20Operator extends Operator {

    public Say20Operator(Player player) {
        super(player);
    }

    @Override
    public boolean isApplicable(GameMatch game) {
        return player.equals(game.getCurrentPlayer()) &&
                canSay20();
    }

    public boolean canSay20() {
        if (player.isSaid20() || player.isSaid40()) {
            return false;
        }
        for (ICard card : player.getCards()) {
            if (card.getRank() == HungarianCardRank.KIRALY) {
                if (player.getCards().stream().anyMatch((ICard item) -> card.getSuit() == item.getSuit()
                        && item.getRank() == HungarianCardRank.FELSO)) {
                    return true;
                }
            }
            if (card.getRank() == HungarianCardRank.FELSO) {
                if (player.getCards().stream().anyMatch((ICard item) -> card.getSuit() == item.getSuit()
                        && item.getRank() == HungarianCardRank.KIRALY)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void say20() {
        player.addScore(20);
        player.setSaid20(true);
    }

    @Override
    public void onApply(GameMatch game) {
        say20();
    }
}
