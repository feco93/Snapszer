package hu.unideb.snapszer.tests.operators;

import hu.unideb.snapszer.model.Deck;
import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.SnapszerDeck;
import hu.unideb.snapszer.model.operators.CoverOperator;
import hu.unideb.snapszer.model.player.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by User on 2016. 03. 20..
 */
public class CoverOperatorTest {

    private Player currentPlayer;
    private GameMatch gameMatchMock;
    private Player nextPlayer;
    private Deck deck;

    @Before
    public void setUp() {
        gameMatchMock = Mockito.mock(GameMatch.class);
        currentPlayer = Mockito.mock(Player.class);
        nextPlayer = Mockito.mock(Player.class);
        deck = SnapszerDeck.getNewDeck();
        deck.drawCards(10);
    }

    @Test
    public void applicableTest() {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(gameMatchMock.isCover()).thenReturn(false);
        when(gameMatchMock.isSnapszer()).thenReturn(false);
        when(gameMatchMock.getDeck()).thenReturn(deck);
        CoverOperator coverOperator = new CoverOperator(currentPlayer);
        assertTrue(coverOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest() {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(gameMatchMock.isCover()).thenReturn(false);
        when(gameMatchMock.isSnapszer()).thenReturn(false);
        when(gameMatchMock.getDeck()).thenReturn(deck);
        CoverOperator coverOperator = new CoverOperator(nextPlayer);
        assertFalse(coverOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest2() {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(gameMatchMock.isCover()).thenReturn(true);
        when(gameMatchMock.isSnapszer()).thenReturn(false);
        when(gameMatchMock.getDeck()).thenReturn(deck);
        CoverOperator coverOperator = new CoverOperator(currentPlayer);
        assertFalse(coverOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest3() {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(gameMatchMock.isCover()).thenReturn(false);
        when(gameMatchMock.isSnapszer()).thenReturn(true);
        when(gameMatchMock.getDeck()).thenReturn(deck);
        CoverOperator coverOperator = new CoverOperator(currentPlayer);
        assertFalse(coverOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest4() {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(gameMatchMock.isCover()).thenReturn(false);
        when(gameMatchMock.isSnapszer()).thenReturn(false);
        deck.drawCards(8);
        when(gameMatchMock.getDeck()).thenReturn(deck);
        CoverOperator coverOperator = new CoverOperator(currentPlayer);
        assertFalse(coverOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applyTest() {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(gameMatchMock.isCover()).thenReturn(false);
        when(gameMatchMock.isSnapszer()).thenReturn(false);
        when(gameMatchMock.getDeck()).thenReturn(deck);
        CoverOperator coverOperator = new CoverOperator(currentPlayer);
        coverOperator.apply(gameMatchMock);
        verify(currentPlayer).setSaidCover(eq(true));
    }
}
