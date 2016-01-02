/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

import hu.unideb.snapszer.model.operators.*;

/**
 * @author Fecó
 */
public class Computer extends Player {
    @Override
    public Operator chooseOperator(SnapszerTwoPlayerGame game) {
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

    private CallOperator getFirstApplicableOperator(SnapszerTwoPlayerGame game) {
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
