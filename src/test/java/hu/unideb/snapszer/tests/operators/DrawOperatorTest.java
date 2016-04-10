package hu.unideb.snapszer.tests.operators;

import hu.unideb.snapszer.model.*;
import hu.unideb.snapszer.model.operators.DrawOperator;
import hu.unideb.snapszer.model.operators.PlayCardOperator;
import hu.unideb.snapszer.model.player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by User on 2016. 03. 20..
 */
public class DrawOperatorTest {

    private final GameMatch gameMatchMock;
    private final Player currentPlayer;
    private final Deck<HungarianCard> sampleDeck;

    public DrawOperatorTest()
    {
        gameMatchMock = Mockito.mock(GameMatch.class);
        currentPlayer = Mockito.mock(Player.class);
        sampleDeck = new Deck<>(Arrays.asList(
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.TOK),
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.MAKK),
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.PIROS)
        ));
    }

    @Test
    public void applicableTest()
    {
        when(gameMatchMock.isCover()).thenReturn(false);
        when(gameMatchMock.getDeck()).thenReturn(sampleDeck);
        DrawOperator drawOperator = new DrawOperator(currentPlayer);
        assertTrue(drawOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest()
    {
        when(gameMatchMock.isCover()).thenReturn(true);
        when(gameMatchMock.getDeck()).thenReturn(sampleDeck);
        DrawOperator drawOperator = new DrawOperator(currentPlayer);
        assertFalse(drawOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest2()
    {
        when(gameMatchMock.isCover()).thenReturn(false);
        when(gameMatchMock.getDeck()).thenReturn(new Deck<>(Arrays.asList()));
        DrawOperator drawOperator = new DrawOperator(currentPlayer);
        assertFalse(drawOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applyTest()
    {
        when(gameMatchMock.isCover()).thenReturn(false);
        when(gameMatchMock.getDeck()).thenReturn(sampleDeck);
        DrawOperator drawOperator = new DrawOperator(currentPlayer);
        drawOperator.apply(gameMatchMock);
        verify(currentPlayer).drawCard(eq(new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.PIROS)));
    }

    @Before
    public void setUp() {}
}
