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
public interface IDeck<T extends ICard> extends Iterable<T> {
    List<T> drawCards(int count);

    T drawCard();

    void insertCard(T card, int index);
    Boolean isEmpty();
    void shuffle();
    int size();
}
