package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameState;
import hu.unideb.snapszer.model.operators.*;

import java.util.List;

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
    public Operator chooseOperator(List<Operator> operators, GameState gameState) {
        return operators.stream().max(
                (o1, o2) -> getGoodness(o1, gameState).compareTo(getGoodness(o2, gameState))).get();
    }

    private Integer getGoodness(Operator op, GameState gameState) {
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
            if (!gameState.tableIsEmpty()) {
                if (gameState.getCardsOnTable().get(0).compareTo(playCardOperator.getCard()) < 0) {
                    return playCardOperator.getCard().getScore();
                } else {
                    return -1 * playCardOperator.getCard().getScore();
                }
            } else {
                return playCardOperator.getCard().getScore();
            }
        }
        return Integer.MIN_VALUE;
    }
}
