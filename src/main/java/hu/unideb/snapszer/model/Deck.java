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

import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model class for a deck.
 *
 * @author Feco
 */
public class Deck implements IDeck, Iterable<ICard> {

    /**
     * Cards in this deck.
     */
    public final ObservableList<ICard> cards;

    /**
     * Constructs a new Deck object.
     *
     * @param cards
     */
    public Deck(Collection<? extends ICard> cards) {
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
    public List<ICard> drawCards(int count) {
        List<ICard> toDraw = new ArrayList<>();

        ListIterator li = cards.listIterator(cards.size());
        // Iterate in reverse.
        while (count > 0) {
            if (li.hasPrevious()) {
                toDraw.add((ICard) li.previous());
                li.remove();
            }
            --count;
        }

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
    public void insertCard(ICard card, int index) {
        cards.add(index, card);
    }

    @Override
    public ICard drawCard() {
        return drawCards(1).get(0);
    }

    @Override
    public Iterator<ICard> iterator() {
        return new Iterator<ICard>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size();
            }

            @Override
            public ICard next() {
                return cards.get(currentIndex++);
            }
        };
    }
}
