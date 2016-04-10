package hu.unideb.snapszer.tests.operators;

import hu.unideb.snapszer.model.*;
import hu.unideb.snapszer.model.operators.SnapszerOperator;
import hu.unideb.snapszer.model.operators.SwapTrumpOperator;
import hu.unideb.snapszer.model.player.Player;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by User on 2016. 03. 20..
 */
public class SwapTrumpOperatorTest {

    private final GameMatch gameMatchMock;
    private final Player currentPlayer;
    private final Deck<HungarianCard> sampleDeck;
    private final ObservableList<HungarianCard> cards;
    private final Player nextPlayer;

    public SwapTrumpOperatorTest()
    {
        gameMatchMock = Mockito.mock(GameMatch.class);
        currentPlayer = Mockito.mock(Player.class);
        sampleDeck = new Deck<>(Arrays.asList(
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.TOK),
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.MAKK),
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.PIROS)
        ));
        cards = FXCollections.observableArrayList(
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        nextPlayer = Mockito.mock(Player.class);
    }

    @Before
    public void setUp()
    {
        when(currentPlayer.getCards()).thenReturn(cards);
    }

    @Test
    public void applicableTest()
    {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(gameMatchMock.getTrumpCard()).thenReturn(
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.ZOLD));
        when(gameMatchMock.getDeck()).thenReturn(sampleDeck);
        SwapTrumpOperator swapTrumpOperator = new SwapTrumpOperator(currentPlayer);
        assertTrue(swapTrumpOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest()
    {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(nextPlayer);
        when(gameMatchMock.getTrumpCard()).thenReturn(
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.ZOLD));
        when(gameMatchMock.getDeck()).thenReturn(sampleDeck);
        SwapTrumpOperator swapTrumpOperator = new SwapTrumpOperator(currentPlayer);
        assertFalse(swapTrumpOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest2()
    {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(gameMatchMock.getTrumpCard()).thenReturn(
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.PIROS));
        when(gameMatchMock.getDeck()).thenReturn(sampleDeck);
        SwapTrumpOperator swapTrumpOperator = new SwapTrumpOperator(currentPlayer);
        assertFalse(swapTrumpOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest3()
    {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(gameMatchMock.getTrumpCard()).thenReturn(
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.ZOLD));
        when(gameMatchMock.getDeck()).thenReturn(sampleDeck);
        when(gameMatchMock.isCover()).thenReturn(true);
        SwapTrumpOperator swapTrumpOperator = new SwapTrumpOperator(currentPlayer);
        assertFalse(swapTrumpOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applyTest()
    {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(gameMatchMock.getTrumpCard()).thenReturn(
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.ZOLD));
        when(gameMatchMock.getDeck()).thenReturn(sampleDeck);
        when(gameMatchMock.trumpCardProperty()).thenReturn(
                new SimpleObjectProperty<>(
                        new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.ZOLD)));
        SwapTrumpOperator swapTrumpOperator = new SwapTrumpOperator(currentPlayer);
        swapTrumpOperator.apply(gameMatchMock);
    }
}
