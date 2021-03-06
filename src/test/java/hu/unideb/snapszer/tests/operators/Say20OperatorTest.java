package hu.unideb.snapszer.tests.operators;

import hu.unideb.snapszer.model.*;
import hu.unideb.snapszer.model.operators.Say20Operator;
import hu.unideb.snapszer.model.player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by User on 2016. 03. 15..
 */
public class Say20OperatorTest {

    private final ObservableList<HungarianCard> deckContains20;
    private final ObservableList<HungarianCard> deckNotContains20;
    private final ObservableList<HungarianCard> deckContains20_2;
    private Player currentPlayer;
    private GameMatch gameMatchMock;
    private Player nextPlayer;

    public Say20OperatorTest() {
        deckContains20 = FXCollections.observableArrayList(
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.MAKK),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        deckContains20_2 = FXCollections.observableArrayList(
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.TOK),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.MAKK),
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.TOK));
        deckNotContains20 = FXCollections.observableArrayList(
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.MAKK),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
    }

    @Before
    public void setUp() {
        gameMatchMock = Mockito.mock(GameMatch.class);
        currentPlayer = Mockito.mock(Player.class);
        nextPlayer = Mockito.mock(Player.class);
    }

    @Test
    public void applicableTest() {

        when(currentPlayer.getCards()).thenReturn(deckContains20);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say20Operator say20Operator = new Say20Operator(currentPlayer);
        assertTrue(say20Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void applicableTest2() {

        when(currentPlayer.getCards()).thenReturn(deckContains20_2);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say20Operator say20Operator = new Say20Operator(currentPlayer);
        assertTrue(say20Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest() {
        when(currentPlayer.getCards()).thenReturn(deckNotContains20);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say20Operator say20Operator = new Say20Operator(currentPlayer);
        assertFalse(say20Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest2() {
        when(currentPlayer.getCards()).thenReturn(deckContains20);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say20Operator say20Operator = new Say20Operator(nextPlayer);
        assertFalse(say20Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest3() {
        when(currentPlayer.getCards()).thenReturn(deckContains20);
        when(currentPlayer.isSaid20()).thenReturn(true);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say20Operator say20Operator = new Say20Operator(currentPlayer);
        assertFalse(say20Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest4() {
        when(currentPlayer.getCards()).thenReturn(deckContains20);
        when(currentPlayer.isSaid40()).thenReturn(true);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say20Operator say20Operator = new Say20Operator(currentPlayer);
        assertFalse(say20Operator.isApplicable(gameMatchMock));
    }

    @Test
    public void applyTest() {
        when(currentPlayer.getCards()).thenReturn(deckContains20);
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        Say20Operator say20Operator = new Say20Operator(currentPlayer);
        say20Operator.apply(gameMatchMock);
        when(currentPlayer.isSaid20()).thenReturn(true);
        assertTrue(currentPlayer.isSaid20());
    }
}
