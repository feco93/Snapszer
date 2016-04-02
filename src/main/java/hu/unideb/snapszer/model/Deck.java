/* 
 * Copyright (C) 2015 Feco
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package hu.unideb.snapszer.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

/**
 * Model class for a deck.
 *
 * @author Feco
 */
public class Deck<T extends ICard> implements IDeck<T> {

    /**
     * Cards in this deck.
     */
    public final ObservableList<T> cards;

    /**
     * Constructs new deck with the given cards.
     * @param cards the initial cards of the deck
     */
    public Deck(Collection<T> cards) {
        this.cards = FXCollections.observableArrayList(cards);
    }

    /**
     * Gets the actual number of the cards in the deck.
     *
     * @return number of the cards
     */
    @Override
    public int size() {
        return cards.size();
    }

    @Override
    public List<T> drawCards(int count) {
        List<T> toDraw = new ArrayList<>(cards.subList(cards.size() - count, cards.size()));
        cards.removeAll(toDraw);
        return toDraw;
    }

    @Override
    public Boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public void insertCard(T card, int index) {
        cards.add(index, card);
    }

    @Override
    public T drawCard() {
        return drawCards(1).get(0);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size();
            }

            @Override
            public T next() {
                return cards.get(currentIndex++);
            }
        };
    }
}
