package hu.unideb.snapszer.tests.operators;

import hu.unideb.snapszer.model.*;
import hu.unideb.snapszer.model.operators.PlayCardOperator;
import hu.unideb.snapszer.model.player.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by User on 2016. 03. 20..
 */
public class PlayCardOperatorTest {

    private Player currentPlayer;
    private GameMatch gameMatchMock;
    private final ObservableList<HungarianCard> cards;
    private final Deck<HungarianCard> sampleDeck;

    public PlayCardOperatorTest() {
        cards = FXCollections.observableArrayList(
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        sampleDeck = new Deck<>(Arrays.asList(
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.TOK),
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.PIROS),
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.MAKK),
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.ZOLD),
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.PIROS)
        ));
    }

    @Before
    public void setUp() {
        gameMatchMock = Mockito.mock(GameMatch.class);
        currentPlayer = Mockito.mock(Player.class);
        for (HungarianCardSuit suit :
                HungarianCardSuit.values()) {
            suit.setTrump(false);
        }
        HungarianCardSuit.PIROS.setTrump(true);
        when(gameMatchMock.getTrumpSuit()).thenReturn(HungarianCardSuit.PIROS);
        when(gameMatchMock.getDeck()).thenReturn(sampleDeck);
    }

    @Test
    public void notApplicableTest() {
        when(currentPlayer.getCards()).thenReturn(cards);
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.emptyObservableList());
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.ZOLD));
        assertFalse(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest2() {
        when(currentPlayer.getCards()).thenReturn(cards);
        when(gameMatchMock.isCover()).thenReturn(true);
        when(gameMatchMock.getCardsOnTable()).thenReturn(
                FXCollections.observableArrayList(
                        new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.ZOLD)));
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        assertFalse(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest3() {
        when(currentPlayer.getCards()).thenReturn(cards);
        when(gameMatchMock.isSnapszer()).thenReturn(true);
        when(gameMatchMock.getCardsOnTable()).thenReturn(
                FXCollections.observableArrayList(
                        new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.ZOLD)));
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        assertFalse(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest4() {
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.emptyObservableList());
        when(currentPlayer.getCards()).thenReturn(cards);
        when(currentPlayer.isSaid20()).thenReturn(true);
        when(currentPlayer.isSaid40()).thenReturn(false);
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.ZOLD));
        assertFalse(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest5() {
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.emptyObservableList());
        when(currentPlayer.getCards()).thenReturn(cards);
        when(currentPlayer.isSaid20()).thenReturn(true);
        when(currentPlayer.isSaid40()).thenReturn(false);
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.ZOLD));
        assertFalse(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest6() {
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.emptyObservableList());
        when(currentPlayer.getCards()).thenReturn(cards);
        when(currentPlayer.isSaid40()).thenReturn(true);
        when(currentPlayer.isSaid20()).thenReturn(false);
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.ASZ, HungarianCardSuit.ZOLD));
        assertFalse(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest7() {
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.emptyObservableList());
        when(currentPlayer.getCards()).thenReturn(cards);
        when(currentPlayer.isSaid40()).thenReturn(true);
        when(currentPlayer.isSaid20()).thenReturn(false);
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.ZOLD));
        assertFalse(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void notApplicableTest8() {
        when(currentPlayer.getCards()).thenReturn(cards);
        when(gameMatchMock.getDeck()).thenReturn(new Deck<>(Arrays.asList()));
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.observableArrayList(
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.MAKK)));
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        assertFalse(playCardOperator.isApplicable(gameMatchMock));
    }


    @Test
    public void applicableTest() {
        when(currentPlayer.getCards()).thenReturn(cards);
        when(currentPlayer.isSaid40()).thenReturn(false);
        when(currentPlayer.isSaid20()).thenReturn(false);
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.emptyObservableList());
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        assertTrue(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applicableTest2() {
        when(currentPlayer.getCards()).thenReturn(cards);
        when(gameMatchMock.getCardsOnTable()).thenReturn(
                FXCollections.observableArrayList(
                        new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.ZOLD)));
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        assertTrue(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applicableTest3() {
        when(currentPlayer.getCards()).thenReturn(cards);
        when(currentPlayer.isSaid20()).thenReturn(false);
        when(currentPlayer.isSaid40()).thenReturn(true);
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.emptyObservableList());
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.PIROS));
        assertTrue(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applicableTest4() {
        when(currentPlayer.getCards()).thenReturn(cards);
        when(currentPlayer.isSaid20()).thenReturn(false);
        when(currentPlayer.isSaid40()).thenReturn(true);
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.emptyObservableList());
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS));
        assertTrue(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applicableTest5() {
        HungarianCardSuit.PIROS.setTrump(false);
        when(gameMatchMock.getTrumpSuit()).thenReturn(HungarianCardSuit.ZOLD);
        when(currentPlayer.getCards()).thenReturn(cards);
        when(currentPlayer.isSaid20()).thenReturn(true);
        when(currentPlayer.isSaid40()).thenReturn(false);
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.emptyObservableList());
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.FELSO, HungarianCardSuit.PIROS));
        assertTrue(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applicableTest6() {
        HungarianCardSuit.PIROS.setTrump(false);
        when(gameMatchMock.getTrumpSuit()).thenReturn(HungarianCardSuit.ZOLD);
        when(currentPlayer.getCards()).thenReturn(cards);
        when(currentPlayer.isSaid20()).thenReturn(true);
        when(currentPlayer.isSaid40()).thenReturn(false);
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.emptyObservableList());
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS));
        assertTrue(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applicableTest7() {
        when(currentPlayer.getCards()).thenReturn(cards);
        when(currentPlayer.isSaid40()).thenReturn(false);
        when(currentPlayer.isSaid20()).thenReturn(false);
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.observableArrayList(
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.ZOLD)));
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        assertTrue(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applicableTest8() {
        when(currentPlayer.getCards()).thenReturn(cards);
        when(gameMatchMock.getDeck()).thenReturn(new Deck<>(Arrays.asList()));
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.observableArrayList(
                new HungarianCard(HungarianCardRank.TIZ, HungarianCardSuit.MAKK)));
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.KIRALY, HungarianCardSuit.PIROS));
        assertTrue(playCardOperator.isApplicable(gameMatchMock));
    }

    @Test
    public void applyTest(){
        when(gameMatchMock.getCardsOnTable()).thenReturn(FXCollections.observableArrayList());
        PlayCardOperator playCardOperator = new PlayCardOperator(
                currentPlayer,
                new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        playCardOperator.apply(gameMatchMock);
        verify(currentPlayer).removeCard(eq(new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK)));
    }

}
