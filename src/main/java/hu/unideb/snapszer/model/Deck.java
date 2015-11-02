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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Model class for a deck.
 *
 * @author Feco
 */
public class Deck implements IDeck {

    /**
     * Cards in this deck.
     */
    private final Stack<ICard> cards;
    /**
     * How many draw occurred on this deck.
     */
    private int drawcounter;

    /**
     * Constructs a new Deck object.
     *
     * @param cards
     */
    public Deck(Collection<? extends ICard> cards) {
        this.cards = new Stack<>();
        this.cards.addAll(cards);
        drawcounter = 0;
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
        drawcounter++;
        if (drawcounter % 2 == 1) {
            if (count * 2 >= size()) {
                count = size() / 2;
            }
        } else {
            if (count > size()) {
                count = size();
            }
        }

        for (int cardIndex = 0; cardIndex < count; ++cardIndex) {
            toDraw.add(cards.pop());
        }
        return toDraw;
    }

    @Override
    public Boolean isEmpty() {
        return cards.empty();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public void insertCard(ICard card, int index) {
        cards.insertElementAt(card, index);
    }

    @Override
    public ICard drawCard() {
        return drawCards(1).get(0);
    }
}
