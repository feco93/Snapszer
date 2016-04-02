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
    IRank getRank();

    /**
     * Gets the value of the property Suit.
     *
     * @return the value of the property Suit
     */
    ISuit getSuit();
}
