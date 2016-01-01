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

    @Override
    public boolean isApplicable(Game game) {
        if ((game.isCover() && !game.getCardsOnTable().isEmpty()) ||
                game.getDeck().isEmpty() && !game.getCardsOnTable().isEmpty()) {
            return canCall(game.getCardsOnTable().get(0));
        }
        return canCall(game.getTrumpCard().getSuit());
    }

    @Override
    public void onApply(Game game) {
        player.setSaid20(false);
        player.setSaid40(false);
        player.removeCard(card);
        game.getCardsOnTable().add(card);
    }

    public boolean canCall(ICard calledCard) {
        ICard toCall = player.cards.stream().filter(card ->
                card.getSuit() == calledCard.getSuit()).max((a, b) ->
                a.compareTo(b)).orElse(null);
        if (toCall != null)
            return toCall.equals(card);
        toCall = player.cards.stream().filter(card -> {
            HungarianCard hungarianCard = (HungarianCard) card;
            return hungarianCard.getSuit().isTrump();
        }).findFirst().orElse(null);
        if (toCall != null) {
            return toCall.getSuit().equals(card.getSuit());
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
        if (player.isSaid40() && card.getSuit() == suit
                && (card.getRank() == HungarianCardRank.FELSO
                || card.getRank() == HungarianCardRank.KIRALY)) {
            return true;
        }
        return false;
    }
}
