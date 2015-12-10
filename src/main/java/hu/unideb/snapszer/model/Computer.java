/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

import hu.unideb.snapszer.model.operators.*;

/**
 *
 * @author Fecó
 */
public class Computer extends Player {
    @Override
    public Operator chooseOperator(Game game) {
        if (canSay20()) {
            return new Say20Operator();
        }
        if (canSay40(game.getTrumpCard().getSuit())) {
            return new Say40Operator();
        }
        if (canSayEnd()) {
            return new SayEndOperator();
        }
        if (isSaid20()) {
            for (ICard card :
                    cards) {
                if (card.getRank() == HungarianCardRank.FELSO || card.getRank() == HungarianCardRank.KIRALY)
                    return new CallOperator(this, (HungarianCard) card);
            }
        }
        if (isSaid40()) {
            for (ICard card :
                    cards) {
                if (card.getSuit() == game.getTrumpCard().getSuit() &&
                        (card.getRank() == HungarianCardRank.FELSO || card.getRank() == HungarianCardRank.KIRALY))
                    return new CallOperator(this, (HungarianCard) card);
            }
        }
        return new CallOperator(this, (HungarianCard) cards.get(0));
    }
}
