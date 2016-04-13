package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.operators.*;

import java.util.List;

/**
 * Created by User on 2016. 04. 02..
 */
public class ComputerAdvanced extends Computer {

    public ComputerAdvanced() {
        this("Computer (Advanced)");
    }

    public ComputerAdvanced(String name) {
        super(name);
    }

    @Override
    public Operator chooseOperator(GameMatch game) {
        return chooseGoodOperator(game);
    }

    private Operator chooseGoodOperator(GameMatch game) {
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
            if (!game.getCardsOnTable().isEmpty()) {
                if (game.getCardsOnTable().get(0).compareTo(playCardOperator.getCard()) < 0) {
                    return playCardOperator.getCard().getScore();
                } else {
                    return -1 * playCardOperator.getCard().getScore();
                }
            } else {
                List<HungarianCard> knownCards = game.getPlayedCards();
                knownCards.addAll(cards);
                if (higherCardInGame(playCardOperator.getCard(), knownCards)) {
                    return -1 * playCardOperator.getCard().getScore();
                }
                return playCardOperator.getCard().getScore();
            }
        }
        return Integer.MIN_VALUE;
    }


}
