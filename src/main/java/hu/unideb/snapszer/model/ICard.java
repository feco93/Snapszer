/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

/**
 *
 * @author Fecó
 */
public interface ICard extends Comparable<ICard> {

    /**
     * Gets the value of the property Rank.
     *
     * @return the value of the property Number
     */
    public IRank getRank();

    /**
     * Gets the value of the property Suit.
     *
     * @return the value of the property Suit
     */
    public ISuit getSuit();

    /**
     * Sets the value of the property face up to false.
     *
     */
    public void turnDown();

    /**
     * Sets the value of the property face up to true.
     *
     */
    public void turnUp();

    /**
     * Gets the value of the property face up.
     *
     * @return indicates whether the card is face up
     */
    public boolean isFaceup();
}
