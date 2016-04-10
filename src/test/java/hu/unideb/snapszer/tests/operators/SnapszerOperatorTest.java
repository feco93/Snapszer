package hu.unideb.snapszer.tests.operators;

import hu.unideb.snapszer.model.Deck;
import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.SnapszerDeck;
import hu.unideb.snapszer.model.operators.SayEndOperator;
import hu.unideb.snapszer.model.operators.SnapszerOperator;
import hu.unideb.snapszer.model.player.Player;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by User on 2016. 03. 20..
 */
public class SnapszerOperatorTest {

    private final GameMatch gameMatchMock;
    private final Player currentPlayer;

    public SnapszerOperatorTest()
    {
        gameMatchMock = Mockito.mock(GameMatch.class);
        currentPlayer = Mockito.mock(Player.class);
    }

    @Test
    public void applicableTest()
    {
        Deck<HungarianCard> deck = SnapszerDeck.getNewDeck();
        deck.drawCards(10);
        when(gameMatchMock.getDeck()).thenReturn(deck);
        SnapszerOperator snapszerOperator = new SnapszerOperator(currentPlayer);
        assertTrue(snapszerOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest()
    {
        Deck<HungarianCard> deck = SnapszerDeck.getNewDeck();
        deck.drawCards(12);
        when(gameMatchMock.getDeck()).thenReturn(deck);
        SnapszerOperator snapszerOperator = new SnapszerOperator(currentPlayer);
        assertFalse(snapszerOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest2()
    {
        Deck<HungarianCard> deck = SnapszerDeck.getNewDeck();
        when(gameMatchMock.getDeck()).thenReturn(deck);
        when(gameMatchMock.isCover()).thenReturn(true);
        SnapszerOperator snapszerOperator = new SnapszerOperator(currentPlayer);
        assertFalse(snapszerOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest3()
    {
        Deck<HungarianCard> deck = SnapszerDeck.getNewDeck();
        when(gameMatchMock.getDeck()).thenReturn(deck);
        when(gameMatchMock.isSnapszer()).thenReturn(true);
        SnapszerOperator snapszerOperator = new SnapszerOperator(currentPlayer);
        assertFalse(snapszerOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applyTest()
    {
        Deck<HungarianCard> deck = SnapszerDeck.getNewDeck();
        deck.drawCards(10);
        when(gameMatchMock.getDeck()).thenReturn(deck);
        SnapszerOperator snapszerOperator = new SnapszerOperator(currentPlayer);
        snapszerOperator.apply(gameMatchMock);
        verify(currentPlayer).setSaidSnapszer(true);
    }
}
