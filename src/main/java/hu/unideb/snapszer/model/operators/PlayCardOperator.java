package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.*;
import hu.unideb.snapszer.model.player.Player;

/**
 * Created by Fecó on 2015.12.05..
 */
public class PlayCardOperator extends Operator {

    private HungarianCard card;

    public PlayCardOperator(Player player, HungarianCard card) {
        super(player);
        this.card = card;
    }

    public HungarianCard getCard() {
        return card;
    }

    @Override
    public boolean isApplicable(GameMatch game) {
        if (!player.getCards().stream().anyMatch(hungarianCard -> hungarianCard.equals(card)))
            return false;
        if ((game.isCover() && !game.getCardsOnTable().isEmpty()) ||
                (game.getDeck().isEmpty() && !game.getCardsOnTable().isEmpty()) ||
                (game.isSnapszer() && !game.getCardsOnTable().isEmpty())) {
            return canCall(game.getCardsOnTable().get(0));
        }
        return canCall(game.getTrumpSuit());
    }

    @Override
    public void onApply(GameMatch game) {
        player.setSaid20(false);
        player.setSaid40(false);
        player.removeCard(card);
        game.getCardsOnTable().add(card);
    }

    private boolean canCall(ICard calledCard) {
        boolean hasSameSuit =
                player.getCards().stream().anyMatch(iCard -> iCard.getSuit().equals(calledCard.getSuit()));
        if (hasSameSuit) {
            return card.getSuit().equals(calledCard.getSuit());
        }
        boolean hasTrump =
                player.getCards().stream().anyMatch(iCard -> {
                    HungarianCard hungarianCard = iCard;
                    return hungarianCard.getSuit().isTrump();
                });
        return !hasTrump || card.getSuit().isTrump();
    }

    public boolean canCall(HungarianCardSuit suit) {
        if (!player.isSaid20() && !player.isSaid40()) {
            return true;
        }
        if (player.isSaid20()) {
            if (card.getRank() == HungarianCardRank.KIRALY) {
                return player.getCards().stream().anyMatch((ICard item) -> card.getSuit() == item.getSuit()
                        && item.getRank() == HungarianCardRank.FELSO);
            }
            if (card.getRank() == HungarianCardRank.FELSO) {
                return player.getCards().stream().anyMatch((ICard item) -> card.getSuit() == item.getSuit()
                        && item.getRank() == HungarianCardRank.KIRALY);
            }
        }

        return player.isSaid40() && card.getSuit() == suit
                && (card.getRank() == HungarianCardRank.FELSO
                || card.getRank() == HungarianCardRank.KIRALY);
    }
}
