package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.*;

/**
 * Created by Fecó on 2015.12.05..
 */
public class CallOperator extends Operator {

    private HungarianCard card;

    public CallOperator(Player player, HungarianCard card) {
        super(player);
        this.card = card;
    }

    public HungarianCard getCard() {
        return card;
    }

    @Override
    public boolean isApplicable(SnapszerTwoPlayerGame game) {
        if ((game.isCover() && !game.getCardsOnTable().isEmpty()) ||
                (game.getDeck().isEmpty() && !game.getCardsOnTable().isEmpty()) ||
                (game.isSnapszer() && !game.getCardsOnTable().isEmpty())) {
            return canCall(game.getCardsOnTable().get(0));
        }
        return canCall(game.getTrumpCard().getSuit());
    }

    @Override
    public void onApply(SnapszerTwoPlayerGame game) {
        player.setSaid20(false);
        player.setSaid40(false);
        player.removeCard(card);
        game.getCardsOnTable().add(card);
    }

    private boolean canCall(ICard calledCard) {
        boolean hasSameSuit =
                player.cards.stream().anyMatch(iCard -> iCard.getSuit().equals(calledCard.getSuit()));
        if (hasSameSuit) {
            return card.getSuit().equals(calledCard.getSuit());
        }
        boolean hasTrump =
                player.cards.stream().anyMatch(iCard -> {
                    HungarianCard hungarianCard = (HungarianCard) iCard;
                    return hungarianCard.getSuit().isTrump();
                });
        if (hasTrump) {
            return card.getSuit().isTrump();
        }
        return true;
    }

    public boolean canCall(HungarianCardSuit suit) {
        if (!player.isSaid20() && !player.isSaid40()) {
            return true;
        }
        if (player.isSaid20()) {
            if (card.getRank() == HungarianCardRank.KIRALY) {
                if (player.cards.stream().anyMatch((ICard item) -> card.getSuit() == item.getSuit()
                        && item.getRank() == HungarianCardRank.FELSO)) {
                    return true;
                }
            }
            if (card.getRank() == HungarianCardRank.FELSO) {
                if (player.cards.stream().anyMatch((ICard item) -> card.getSuit() == item.getSuit()
                        && item.getRank() == HungarianCardRank.KIRALY)) {
                    return true;
                }
            }
        }
        return player.isSaid40() && card.getSuit() == suit
                && (card.getRank() == HungarianCardRank.FELSO
                || card.getRank() == HungarianCardRank.KIRALY);
    }
}
