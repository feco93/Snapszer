/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

import java.util.List;

/**
 *
 * @author Fecó
 */
public interface IDeck {
    List<ICard> drawCards(int count);
    ICard drawCard();
    void insertCard(ICard card, int index);
    Boolean isEmpty();
    void shuffle();
    int size();
}
