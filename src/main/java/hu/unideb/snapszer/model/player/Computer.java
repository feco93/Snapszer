/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.ICard;
import hu.unideb.snapszer.model.SnapszerDeck;
import hu.unideb.snapszer.model.operators.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fecó
 */
public abstract class Computer extends Player {

    public Computer(String name) {
        super(name);
    }

    protected List<Operator> getAllOperators() {
        List<Operator> allOperators = new ArrayList<>(11);
        allOperators.addAll(Arrays.asList(new CoverOperator(this),
                new Say20Operator(this),
                new Say40Operator(this),
                new SayEndOperator(this),
                new SnapszerOperator(this),
                new SwapTrumpOperator(this)));
        for (HungarianCard card :
                getCards()) {
            PlayCardOperator op = new PlayCardOperator(this, card);
            allOperators.add(op);
        }
        return allOperators;
    }

    protected List<Operator> getAllApplicableOperators(GameMatch game) {
        return getAllOperators().stream().
                filter(op -> op.isApplicable(game)).
                collect(Collectors.toList());
    }

    protected boolean higherCardInGame(HungarianCard card, List<HungarianCard> knownCards) {
        return !knownCards.containsAll(higherCards(card));
    }

    protected List<HungarianCard> higherCards(HungarianCard card) {
        return SnapszerDeck.getSampleDeck().cards.stream().filter(
                otherCard -> card.getSuit() == otherCard.getSuit() &&
                        otherCard.getRank().compareTo(card.getRank()) > 0).collect(Collectors.toList());
    }
}
