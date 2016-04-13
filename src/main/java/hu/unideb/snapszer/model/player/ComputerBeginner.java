package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.operators.*;

/**
 * Created by Fecó Sipos on 2016. 02. 22..
 */
public class ComputerBeginner extends Computer {

    public ComputerBeginner() {
        this("Computer (Beginner)");
    }

    public ComputerBeginner(String name) {
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
            if (!game.getCardsOnTable().isEmpty()) {
                if (game.getCardsOnTable().get(0).compareTo(playCardOperator.getCard()) < 0) {
                    return playCardOperator.getCard().getPoints();
                } else {
                    return -1 * playCardOperator.getCard().getPoints();
                }
            } else {
                return playCardOperator.getCard().getPoints();
            }
        }
        return Integer.MIN_VALUE;
    }
}
