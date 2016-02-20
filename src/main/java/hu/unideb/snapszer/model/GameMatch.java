package hu.unideb.snapszer.model;

import hu.unideb.snapszer.model.operators.CallOperator;
import hu.unideb.snapszer.model.operators.DrawOperator;
import hu.unideb.snapszer.model.operators.Operator;
import hu.unideb.snapszer.model.operators.SayEndOperator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.collections.ListUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by Fecó Sipos on 2016. 01. 31..
 */
public class GameMatch {

    private Deck deck;
    private List<Player> players;
    private ObservableList<HungarianCard> cardsOnTable;
    private ObservableList<HungarianCard> playedCards;
    private ObjectProperty<HungarianCard> trumpCard;
    private boolean cover;
    private boolean snapszer;
    private Player sayerPlayer;

    public GameMatch(List<Player> players, Deck deck) {
        this.players = players;
        cover = false;
        snapszer = false;
        this.deck = deck;
        this.cardsOnTable = FXCollections.observableArrayList();
        this.playedCards = FXCollections.observableArrayList();
        trumpCard = new SimpleObjectProperty<>();
        trumpCard.addListener((observable) -> {
            deck.insertCard(trumpCard.getValue(), 0);
            trumpCard.getValue().getSuit().setTrump(true);
        });
    }

    public ObjectProperty<HungarianCard> trumpCardProperty() {
        return trumpCard;
    }

    public HungarianCard getTrumpCard() {
        return trumpCard.get();
    }

    public ObservableList<HungarianCard> getCardsOnTable() {
        return cardsOnTable;
    }

    public ObservableList<HungarianCard> getPlayedCards() {
        return playedCards;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(0);
    }

    public Player getSayerPlayer() {
        return sayerPlayer;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
        this.sayerPlayer = getCurrentPlayer();
    }

    public boolean isSnapszer() {
        return snapszer;
    }

    public void setSnapszer(boolean snapszer) {
        this.snapszer = snapszer;
        this.sayerPlayer = getCurrentPlayer();
    }

    public Deck getDeck() {
        return deck;
    }

    private void shufflePlayers() {
        Collections.shuffle(players);
    }

    private void initGame() {
        shufflePlayers();
        drawPhase(3);
        trumpCard.set((HungarianCard) deck.drawCard());
        drawPhase(2);
    }

    private HungarianCard getHighestCard() {
        for (HungarianCard card :
                cardsOnTable) {
            boolean isHighest = true;
            for (HungarianCard card2 :
                    cardsOnTable) {
                if (card2.equals(card))
                    continue;
                if (card.compareTo(card2) < 0)
                    isHighest = false;
            }
            if (isHighest) {
                return card;
            }
        }
        return null;
    }

    private void beatPhase() {
        int index = cardsOnTable.indexOf(getHighestCard());
        if (index > 0) {
            swapPlayers(index);
        }
        for (HungarianCard card :
                cardsOnTable) {
            getCurrentPlayer().addScore(card.getPoints());
        }
        getCurrentPlayer().setBeatsCounter(getCurrentPlayer().getBeatsCounter() + 1);
        playedCards.addAll(cardsOnTable);
        cardsOnTable.clear();
    }

    private void drawPhase(int numberOfCards) {
        for (Player player :
                getPlayers()) {
            for (int i = 0; i < numberOfCards; ++i) {
                DrawOperator op = new DrawOperator(player);
                if (op.isApplicable(this)) {
                    op.apply(this);
                }
            }
        }
    }

    private void swapPlayers(int newCurrentPlayerIndex) {
        players =
                ListUtils.union(
                        players.subList(newCurrentPlayerIndex, players.size()),
                        players.subList(0, newCurrentPlayerIndex));
    }

    private boolean isMatchOver() {
        return getPlayers().stream().allMatch((player) -> player.cards.isEmpty()) &&
                deck.isEmpty();
    }

    public Player play() {
        initGame();
        while (true) {
            if (isMatchOver())
                break;
            for (Player player :
                    getPlayers()) {
                while (true) {
                    Operator op = player.chooseOperator(this);
                    if (op.isApplicable(this)) {
                        op.apply(this);
                        if (op instanceof CallOperator) {
                            break;
                        }
                        if (op instanceof SayEndOperator) {
                            return player;
                        }
                    }
                }
            }
            beatPhase();
            drawPhase(1);
        }
        return getCurrentPlayer();
    }
}
