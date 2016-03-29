package hu.unideb.snapszer.tests.operators;

import hu.unideb.snapszer.model.*;
import hu.unideb.snapszer.model.operators.Say40Operator;
import hu.unideb.snapszer.model.player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by User on 2016. 03. 20..
 */
public class Say40OperatorTest {

    private final ObservableList<ICard> deckContains40;
    private final ObservableList<ICard> deckContains40_2;
    private final ObservableList<ICard> deckNotContains40;
    private final ObservableList<ICard> deckNotContains40_2;
    private Player currentPlayer;
    private GameMatch gameMatchMock;
    private Player nextPlayer;

    public Say40OperatorTest() {
        HungarianCardSuit.PIROS.setTrump(true);
        deckContains40 = FXCollections.observableArrayList(
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.MAKK),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        deckContains40_2 = FXCollections.observableArrayList(
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.MAKK),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        deckNotContains40 = FXCollections.observableArrayList(
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.MAKK),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        deckNotContains40_2 = FXCollections.observableArrayList(
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.MAKK),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
    }

    @Before
    public void setUp() {
        gameMatchMock = Mockito.mock(GameMatch.class);
        when(gameMatchMock.getTrumpCard()).thenReturn(
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS));
        currentPlayer = Mockito.mock(Player.class);
        nextPlayer = Mockito.mock(Player.class);
    }

    @Test
    public void applicableTest() {

        when(currentPlayer.getCards()).thenReturn(deckContains40);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say40Operator say40Operator = new Say40Operator(currentPlayer);
        assertTrue(say40Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void applicableTest2() {

        when(currentPlayer.getCards()).thenReturn(deckContains40_2);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say40Operator say40Operator = new Say40Operator(currentPlayer);
        assertTrue(say40Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest() {
        when(currentPlayer.getCards()).thenReturn(deckNotContains40);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say40Operator say40Operator = new Say40Operator(currentPlayer);
        assertFalse(say40Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest2() {
        when(currentPlayer.getCards()).thenReturn(deckContains40);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say40Operator say40Operator = new Say40Operator(nextPlayer);
        assertFalse(say40Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest3() {
        when(currentPlayer.getCards()).thenReturn(deckContains40);
        when(currentPlayer.isSaid20()).thenReturn(true);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say40Operator say40Operator = new Say40Operator(currentPlayer);
        assertFalse(say40Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest4() {
        when(currentPlayer.getCards()).thenReturn(deckContains40);
        when(currentPlayer.isSaid40()).thenReturn(true);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say40Operator say40Operator = new Say40Operator(currentPlayer);
        assertFalse(say40Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest5() {
        when(currentPlayer.getCards()).thenReturn(deckNotContains40_2);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say40Operator say40Operator = new Say40Operator(currentPlayer);
        assertFalse(say40Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void applyTest() {
        when(currentPlayer.getCards()).thenReturn(deckContains40);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say40Operator say40Operator = new Say40Operator(currentPlayer);
        say40Operator.apply(gameMatchMock);
        verify(currentPlayer).setSaid40(eq(true));
        verify(currentPlayer).addScore(eq(40));
    }
}
