/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.tests;

import hu.unideb.snapszer.model.Human;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.HungarianCardRank;
import hu.unideb.snapszer.model.HungarianCardSuit;
import hu.unideb.snapszer.model.ICard;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fecó
 */
public class HumanPlayerTest {

    private Human human;
    private static List<ICard> cardsInHand_1;
    private static List<ICard> cardsInHand_2;
    private static List<ICard> cardsInHand_3;
    private static List<ICard> cardsInHand_4;

    public HumanPlayerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        cardsInHand_1 = new ArrayList<>(5);
        cardsInHand_1.add(new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        cardsInHand_1.add(new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.TOK));
        cardsInHand_1.add(new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.PIROS));
        cardsInHand_1.add(new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.ZOLD));
        cardsInHand_1.add(new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.MAKK));

        cardsInHand_2 = new ArrayList<>(5);
        cardsInHand_2.add(new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS));
        cardsInHand_2.add(new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.TOK));
        cardsInHand_2.add(new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.PIROS));
        cardsInHand_2.add(new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.ZOLD));
        cardsInHand_2.add(new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.MAKK));

        cardsInHand_3 = new ArrayList<>(5);
        cardsInHand_3.add(new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.TOK));
        cardsInHand_3.add(new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.TOK));
        cardsInHand_3.add(new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.PIROS));
        cardsInHand_3.add(new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.ZOLD));
        cardsInHand_3.add(new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.MAKK));

        cardsInHand_4 = new ArrayList<>(5);
        cardsInHand_4.add(new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.MAKK));
        cardsInHand_4.add(new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS));
        cardsInHand_4.add(new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.PIROS));
        cardsInHand_4.add(new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.ZOLD));
        cardsInHand_4.add(new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.MAKK));
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        human = new Human();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDraw() {
        List<ICard> cards = new ArrayList<>();
        cards.add(new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        cards.add(new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.TOK));
        human.drawCards(cards);
        assertEquals(2, human.cardsInHand());
    }

    @Test
    public void testCanSay20_3() {
        human.drawCards(cardsInHand_3);
        assertTrue(human.canSay20());
    }

    @Test
    public void testCanSay20_4() {
        human.drawCards(cardsInHand_4);
        assertTrue(human.canSay20());
    }

    @Test
    public void testCannotSay20_1() {
        human.drawCards(cardsInHand_1);
        assertFalse(human.canSay20());
    }

    @Test
    public void testCannotSay20_2() {
        human.drawCards(cardsInHand_2);
        assertFalse(human.canSay20());
    }

    @Test
    public void testCannotSay40_1() {
        human.drawCards(cardsInHand_1);
        assertFalse(human.canSay40(HungarianCardSuit.TOK));
    }

    @Test
    public void testCannotSay40_3() {
        human.drawCards(cardsInHand_3);
        assertFalse(human.canSay40(HungarianCardSuit.PIROS));
    }

    @Test
    public void testCanSay40_3() {
        human.drawCards(cardsInHand_3);
        assertTrue(human.canSay40(HungarianCardSuit.TOK));
    }

    @Test
    public void testCanSay40_4() {
        human.drawCards(cardsInHand_4);
        assertTrue(human.canSay40(HungarianCardSuit.PIROS));
    }

    @Test
    public void testIsValidChoose_40() {
        human.setTrump(HungarianCardSuit.PIROS);
        human.say40();
        assertTrue(human.isValidChosenCard(new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.PIROS)));
        assertTrue(human.isValidChosenCard(new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS)));
        assertFalse(human.isValidChosenCard(new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.ZOLD)));
        assertFalse(human.isValidChosenCard(new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.TOK)));
        assertFalse(human.isValidChosenCard(new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.PIROS)));
    }
    
    @Test
    public void testIsValidChoose_20() {
        human.say20();
        assertTrue(human.isValidChosenCard(new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.ZOLD)));
        assertTrue(human.isValidChosenCard(new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS)));
        assertFalse(human.isValidChosenCard(new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.ZOLD)));
        assertTrue(human.isValidChosenCard(new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.TOK)));
        assertFalse(human.isValidChosenCard(new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.PIROS)));
    }
}
