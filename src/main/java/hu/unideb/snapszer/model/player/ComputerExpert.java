package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.HungarianCardRank;
import hu.unideb.snapszer.model.operators.*;

import java.util.List;

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
        return getAllApplicableOperators(game).stream().min(
                (o1, o2) -> getGoodness(o1, game).compareTo(getGoodness(o2, game))).get();
    }

    private int getRemainingScore()
    {
        return 66 - getScore();
    }

    private Integer getGoodness(Operator op, GameMatch game) {
        if (op instanceof SayEndOperator)
            return Integer.MIN_VALUE;
        if (op instanceof SwapTrumpOperator)
            return Integer.MIN_VALUE + 1;
        if (op instanceof Say40Operator)
            return getRemainingScore() - 40;
        if (op instanceof Say20Operator)
            return getRemainingScore() - 20;
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
                return Integer.MIN_VALUE;
        }
        return Integer.MAX_VALUE;
    }

    private Integer getGoodness(PlayCardOperator playCardOperator, GameMatch game) {
        if (!game.getCardsOnTable().isEmpty()) {
            HungarianCard firstCard = game.getCardsOnTable().get(0);
            if (firstCard.compareTo(playCardOperator.getCard()) < 0) {
                return getRemainingScore() - firstCard.getScore() - playCardOperator.getCard().getScore();
            } else {
                return getRemainingScore() + playCardOperator.getCard().getScore();
            }
        } else {
            List<HungarianCard> knownCards = game.getPlayedCards();
            knownCards.addAll(cards);
            if (higherCardInGame(playCardOperator.getCard(), knownCards)) {
                return getRemainingScore() + playCardOperator.getCard().getScore();
            }
            return getRemainingScore() - playCardOperator.getCard().getScore();
        }
    }
}
