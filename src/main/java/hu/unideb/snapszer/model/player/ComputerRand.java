package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.operators.Operator;

import java.util.List;
import java.util.Random;

/**
 * Created by Fecó Sipos on 2016. 02. 22..
 */
public class ComputerRand extends Computer {

    public ComputerRand() {
        this("Computer (Random operator)");
    }

    public ComputerRand(String name) {
        super(name);
    }

    @Override
    public Operator chooseOperator(GameMatch game) {
        return chooseRandomOperator(game);
    }

    private Operator chooseRandomOperator(GameMatch game) {
        List<Operator> operators = getAllApplicableOperators(game);
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(operators.size());
        return operators.get(index);
    }
}
