/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.ICard;
import hu.unideb.snapszer.model.operators.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Fecó
 */
public class Computer extends Player {

    public Computer(String name) {
        super(name);
    }

    @Override
    public Operator chooseOperator(GameMatch game) {
        if (game.getCurrentPlayer() == this) {
            if (canSay20()) {
                return new Say20Operator(this);
            }
            if (canSay40(game.getTrumpCard().getSuit())) {
                return new Say40Operator(this);
            }
            if (canSayEnd()) {
                return new SayEndOperator(this);
            }
        }
        return getFirstApplicableOperator(game);
    }

    protected List<Operator> getAllOperators() {
        List<Operator> allOperators = new ArrayList<>(11);
        allOperators.addAll(Arrays.asList(new CoverOperator(this),
                new Say20Operator(this),
                new Say40Operator(this),
                new SayEndOperator(this),
                new SnapszerOperator(this),
                new SwapTrumpOperator(this)));
        for (ICard card :
                cards) {
            CallOperator op = new CallOperator(this, (HungarianCard) card);
            allOperators.add(op);
        }
        return allOperators;
    }

    protected List<Operator> getAllApplicableOperators(GameMatch game) {
        List<Operator> applicableOperators = new ArrayList<>();
        for (Operator op :
                getAllOperators()) {
            if (op.isApplicable(game))
                applicableOperators.add(op);
        }
        return applicableOperators;
    }

    private CallOperator getFirstApplicableOperator(GameMatch game) {
        for (ICard card :
                cards) {
            CallOperator op = new CallOperator(this, (HungarianCard) card);
            if (op.isApplicable(game))
                return op;
        }
        return null;
    }

    @Override
    public void setChosenOperator(Operator operator) {

    }
}
