package hu.unideb.snapszer.tests.operators;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.operators.SayEndOperator;
import hu.unideb.snapszer.model.player.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by User on 2016. 03. 20..
 */
public class SayEndOperatorTest {

    private final Player nextPlayer;
    private GameMatch gameMatchMock;
    private Player currentPlayer;

    public SayEndOperatorTest()
    {
        gameMatchMock = Mockito.mock(GameMatch.class);
        currentPlayer = Mockito.mock(Player.class);
        nextPlayer = Mockito.mock(Player.class);
    }

    @Test
    public void applicableTest()
    {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(currentPlayer.getScore()).thenReturn(66);
        SayEndOperator sayEndOperator = new SayEndOperator(currentPlayer);
        assertTrue(sayEndOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applicableTest2()
    {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(currentPlayer.getScore()).thenReturn(70);
        SayEndOperator sayEndOperator = new SayEndOperator(currentPlayer);
        assertTrue(sayEndOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest()
    {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(currentPlayer);
        when(currentPlayer.getScore()).thenReturn(50);
        SayEndOperator sayEndOperator = new SayEndOperator(currentPlayer);
        assertFalse(sayEndOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest2()
    {
        when(gameMatchMock.getCurrentPlayer()).thenReturn(nextPlayer);
        SayEndOperator sayEndOperator = new SayEndOperator(currentPlayer);
        assertFalse(sayEndOperator.isApplicable(gameMatchMock));
    }

    @Before
    public void setUp() {

    }
}
