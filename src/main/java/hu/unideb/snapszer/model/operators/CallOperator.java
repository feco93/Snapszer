package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.*;

/**
 * Created by Fecó on 2015.12.05..
 */
public class CallOperator implements Operator {

    private Player player;
    private HungarianCard card;

    public CallOperator(Player player, HungarianCard card) {
        this.player = player;
        this.card = card;
    }

    @Override
    public boolean isApplicable(Game game) {
        if (game.isCover() && !game.getCardsOnTable().isEmpty()) {
            return canCall(game.getCardsOnTable().get(0));
        }
        return canCall(game.getTrumpCard().getSuit());
    }

    @Override
    public void apply(Game game) {
        player.setSaid20(false);
        player.setSaid40(false);
        player.removeCard(card);
        game.getCardsOnTable().add(card);
    }


    public boolean canCall(ICard calledCard) {
        return player.cards.stream().filter(card ->
                card.getSuit() == calledCard.getSuit()).max((a, b) ->
                a.compareTo(b)).equals(card);
    }

    public boolean canCall(HungarianCardSuit suit) {
        if (!player.isSaid20() && !player.isSaid40()) {
            return true;
        }
        if (player.isSaid20()
                && (card.getRank() == HungarianCardRank.FELSO
                || card.getRank() == HungarianCardRank.KIRALY)) {
            return true;
        }
        if (player.isSaid40() && card.getSuit() == suit
                && (card.getRank() == HungarianCardRank.FELSO
                || card.getRank() == HungarianCardRank.KIRALY)) {
            return true;
        }
        return false;
    }
}
