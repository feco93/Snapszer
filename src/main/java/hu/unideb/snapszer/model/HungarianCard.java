/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

import java.util.Objects;

/**
 * @author Fecó
 */
public class HungarianCard implements ICard {

    private final HungarianCardRank rank;
    private final HungarianCardSuit suit;

    public HungarianCard(HungarianCardRank rank, HungarianCardSuit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getScore() {
        return rank.getValue();
    }

    @Override
    public HungarianCardRank getRank() {
        return rank;
    }

    @Override
    public HungarianCardSuit getSuit() {
        return suit;
    }


    @Override
    public int compareTo(ICard o) {
        HungarianCard other = (HungarianCard) o;
        if (!suit.isTrump() && other.suit.isTrump()) {
            return -1;
        }
        if (suit == other.suit) {
            return rank.compareTo(other.rank);
        }
        return 1;
    }

    /**
     * Returns the string representation of this card.
     *
     * @return the string representation of this card in the form
     * <span><em>suit</em><code>_</code><em>rank</em></span>
     */
    @Override
    public String toString() {
        return suit.toString().toLowerCase() + "_" + rank.toString().toLowerCase();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        return obj.hashCode() == hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.rank);
        hash = 97 * hash + Objects.hashCode(this.suit);
        return hash;
    }

}
