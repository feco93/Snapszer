/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.tests;

import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.HungarianCardRank;
import hu.unideb.snapszer.model.HungarianCardSuit;
import org.junit.*;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author Fecó
 */
public class HungarianCardTest {
    
    private final HungarianCard tokAlso;
    private final HungarianCard tokFelso;
    private final HungarianCard pirosAsz;
    private final HungarianCard zoldTiz;

    public HungarianCardTest() {
        tokAlso = new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK);
        tokFelso = new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.TOK);
        pirosAsz = new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.PIROS);
        zoldTiz = new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.ZOLD);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void compareRanks() {
        int result = tokAlso.compareTo(tokFelso);
        assertTrue(result < 0);
        result = tokFelso.compareTo(tokAlso);
        assertTrue(result > 0);
        result = tokAlso.compareTo(tokAlso);
        assertTrue(result == 0);
    }
    
    @Test
    public void compareTrumpNotTrumpCards() {
        HungarianCardSuit.TOK.setTrump(true);
        int result = tokAlso.compareTo(zoldTiz);
        assertTrue(result > 0);
        HungarianCardSuit.TOK.setTrump(false);
        HungarianCardSuit.PIROS.setTrump(true);
        result = pirosAsz.compareTo(tokAlso);
        assertTrue(result > 0);
    }

    @Test
    public void compareTrumpNotTrumpCards2() {
        HungarianCardSuit.PIROS.setTrump(false);
        HungarianCardSuit.TOK.setTrump(true);
        int result = pirosAsz.compareTo(tokAlso);
        assertTrue(result < 0);
    }
}
