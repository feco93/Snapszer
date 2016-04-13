package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.HungarianCardRank;
import hu.unideb.snapszer.model.SnapszerDeck;
import hu.unideb.snapszer.model.operators.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by User on 2016. 04. 02..
 */
public class ComputerExpert extends Computer {

    public ComputerExpert() {
        this("Computer (Expert)");
    }

    public ComputerExpert(String name) {
        super(name);
    }

    @Override
    public Operator chooseOperator(GameMatch game) {
        return getAllApplicableOperators(game).stream().max(
                (o1, o2) -> getGoodness(o1, game).compareTo(getGoodness(o2, game))).get();
    }

    private Integer getGoodness(Operator op, GameMatch game) {
        if (op instanceof SayEndOperator)
            return Integer.MAX_VALUE;
        if (op instanceof SwapTrumpOperator)
            return Integer.MAX_VALUE - 1;
        if (op instanceof Say40Operator)
            return 40;
        if (op instanceof Say20Operator)
            return 20;
        if (op instanceof PlayCardOperator) {
            PlayCardOperator playCardOperator = (PlayCardOperator) op;
            return getGoodness(playCardOperator, game);
        }
        if (op instanceof SnapszerOperator) {
            Say40Operator say40Operator = new Say40Operator(this);
            if (say40Operator.isApplicable(game) &&
                    cards.stream().anyMatch(iCard -> {
                        HungarianCard card = iCard;
                        return card.getRank() == HungarianCardRank.ASZ &&
                                card.getSuit().isTrump();
                    }) &&
                    cards.stream().anyMatch(iCard -> {
                        HungarianCard card = iCard;
                        return card.getRank() == HungarianCardRank.TIZ &&
                                card.getSuit().isTrump();
                    }))
                return Integer.MAX_VALUE;
        }
        return Integer.MIN_VALUE;
    }

    private Integer getGoodness(PlayCardOperator playCardOperator, GameMatch game) {
        if (!game.getCardsOnTable().isEmpty()) {
            if (game.getCardsOnTable().get(0).compareTo(playCardOperator.getCard()) < 0) {
                if (!playCardOperator.getCard().getSuit().isTrump())
                    return (HungarianCardRank.ASZ.getValue() - playCardOperator.getCard().getPoints()) * 2;
                else
                    return HungarianCardRank.ASZ.getValue() - playCardOperator.getCard().getPoints();
            } else {
                return -1 * playCardOperator.getCard().getPoints();
            }
        } else {
            if (!higherCardInSameSuit(playCardOperator.getCard(), game.getPlayedCards()))
                if (!playCardOperator.getCard().getSuit().isTrump())
                    return playCardOperator.getCard().getPoints() * 2;
                else
                    return playCardOperator.getCard().getPoints();
            else {
                if (playCardOperator.getCard().getSuit().isTrump())
                    return -2 * playCardOperator.getCard().getPoints();
                else
                    return -1 * playCardOperator.getCard().getPoints();
            }
        }
    }

    private boolean higherCardInSameSuit(HungarianCard card, List<HungarianCard> playedCards) {
        List<HungarianCard> higherCards = higherCards(card);
        return !playedCards.containsAll(higherCards);
    }

    private List<HungarianCard> higherCards(HungarianCard card) {
        return SnapszerDeck.getSampleDeck().cards.stream().filter(
                otherCard -> card.getSuit() == otherCard.getSuit() &&
                        otherCard.getRank().compareTo(card.getRank()) > 0).collect(Collectors.toList());
    }
}
