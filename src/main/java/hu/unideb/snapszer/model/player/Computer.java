/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.SnapszerDeck;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fecó
 */
public abstract class Computer extends Player {

    public Computer(String name) {
        super(name);
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
