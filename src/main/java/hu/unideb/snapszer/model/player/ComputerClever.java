package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.operators.*;

/**
 * Created by Fecó Sipos on 2016. 02. 22..
 */
public class ComputerClever extends Computer {

    public ComputerClever() {
        this("Computer (Clever)");
    }

    public ComputerClever(String name) {
        super(name);
    }

    @Override
    public Operator chooseOperator(GameMatch game) {
        return chooseGoodOperator(game);
    }

    private Operator chooseGoodOperator(GameMatch game) {
        return getAllApplicableOperators(game).stream().min(
                (o1, o2) -> getHeuristic(o1).compareTo(getHeuristic(o2))).get();

    }

    private Integer getHeuristic(Operator op) {
        if (op instanceof SayEndOperator)
            return 0;
        if (op instanceof SwapTrumpOperator)
            return 1;
        if (op instanceof Say40Operator)
            return 66 - getScore() + 40;
        if (op instanceof Say20Operator)
            return 66 - getScore() + 20;
        if (op instanceof CallOperator) {
            CallOperator callOperator = (CallOperator) op;
            return 66 - getScore() + callOperator.getCard().getPoints();
        }
        return 100;
    }
}
