/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fecó
 */
public class SnapszerDeck {

    public static Deck getNewDeck() {
        List<ICard> cards = new ArrayList<>();
        for (HungarianCardSuit suit : HungarianCardSuit.values()) {
            for (HungarianCardRank rank : HungarianCardRank.values()) {
                if (rank == HungarianCardRank.KILENC
                        || rank == HungarianCardRank.NYOLC
                        || rank == HungarianCardRank.HET) {
                    continue;
                }
                HungarianCard card = new HungarianCard(rank, suit);
                cards.add(card);
            }
        }
        Deck deck = new Deck(cards);
        deck.shuffle();
        return deck;
    }

}
