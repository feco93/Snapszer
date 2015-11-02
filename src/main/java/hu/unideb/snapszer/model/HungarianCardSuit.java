/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

/**
 * The representation of the Hungarian card's suit property.
 *
 * @author Feco
 */
public enum HungarianCardSuit implements ISuit{

    /**
     * The Tök suit.
     */
    TOK(1),
    /**
     * The Makk suit.
     */
    MAKK(2),
    /**
     * The Zöld suit.
     */
    ZOLD(3),
    /**
     * The Piros suit.
     */
    PIROS(4);

    private final int value;
    private boolean trump;

    HungarianCardSuit(int point) {
        this.value = point;
    }

    public int getValue() {
        return value;
    }

    public boolean isTrump() {
        return trump;
    }

    public void setTrump(boolean trump) {
        this.trump = trump;
    }

}
