package hu.unideb.snapszer.model;

import hu.unideb.snapszer.model.operators.*;
import hu.unideb.snapszer.model.player.Player;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GameMatch {

    private static final Logger logger = LogManager.getLogger(GameMatch.class);
    private Deck<HungarianCard> deck;
    private ObservableList<HungarianCard> cardsOnTable;
    private ObservableList<HungarianCard> playedCards;
    private ObjectProperty<HungarianCard> trumpCard;
    private Player currentPlayer;
    private Player nextPlayer;

    public GameMatch(Player playerOne, Player playerTwo, Deck<HungarianCard> deck) {
        this.currentPlayer = playerOne;
        this.nextPlayer = playerTwo;
        this.currentPlayer.newMatch();
        this.nextPlayer.newMatch();
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

    public HungarianCardSuit getTrumpSuit() {
        for (HungarianCardSuit suit :
                HungarianCardSuit.values()) {
            if (suit.isTrump())
                return suit;
        }
        return null;
    }

    public ObservableList<HungarianCard> getCardsOnTable() {
        return cardsOnTable;
    }

    public ObservableList<HungarianCard> getPlayedCards() {
        return playedCards;
    }

    public List<Player> getPlayers() {
        return Arrays.asList(currentPlayer, nextPlayer);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getSayerPlayer() {
        if (isCover()) {
            return currentPlayer.isSaidCover() ? currentPlayer : nextPlayer;
        }
        return currentPlayer.isSaidSnapszer() ? currentPlayer : nextPlayer;
    }

    public Player getNotSayerPlayer() {
        if (isCover()) {
            return currentPlayer.isSaidCover() ? nextPlayer : currentPlayer;
        }
        return currentPlayer.isSaidSnapszer() ? nextPlayer : currentPlayer;
    }

    public boolean isCover() {
        return getPlayers().stream().anyMatch(Player::isSaidCover);
    }

    public boolean isSnapszer() {
        return getPlayers().stream().anyMatch(Player::isSaidSnapszer);
    }

    public Deck<HungarianCard> getDeck() {
        return deck;
    }

    private void shufflePlayers() {
        List<Player> tempList = getPlayers();
        Collections.shuffle(tempList);
        currentPlayer = tempList.get(0);
        nextPlayer = tempList.get(1);
    }

    private void initGame() {
        shufflePlayers();
        drawPhase(3);
        trumpCard.set(deck.drawCard());
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
            swapPlayers();
        }
        for (HungarianCard card :
                cardsOnTable) {
            getCurrentPlayer().addScore(card.getScore());
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

    private void swapPlayers() {
        Player temp = currentPlayer;
        currentPlayer = nextPlayer;
        nextPlayer = temp;
    }

    private boolean isMatchOver() {
        return (getPlayers().stream().allMatch((player) -> player.getCards().isEmpty())) ||
                (isSnapszer() && getNotSayerPlayer().getBeatsCounter() > 0);
    }

    public void play() {
        initGame();
        while (true) {
            if (isMatchOver())
                return;
            for (Player player :
                    getPlayers()) {
                while (true) {
                    Operator op = player.chooseOperator(getAllApplicableOperators(player),
                            new GameState(
                                    getTrumpCard(),
                                    new ArrayList<>(getCardsOnTable()),
                                    new ArrayList<>(getPlayedCards())));
                    if (op.isApplicable(this)) {
                        op.apply(this);
                        logger.trace(String.format(
                                "The %s player apply: %s",
                                player.getName(),
                                op.toString()));
                        if (op instanceof PlayCardOperator) {
                            break;
                        }
                        if (op instanceof SayEndOperator) {
                            return;
                        }
                    }
                }
            }
            beatPhase();
            drawPhase(1);
        }
    }

    protected List<Operator> getAllOperators(Player currentPlayer) {
        List<Operator> allOperators = new ArrayList<>(11);
        allOperators.addAll(Arrays.asList(new CoverOperator(currentPlayer),
                new Say20Operator(currentPlayer),
                new Say40Operator(currentPlayer),
                new SayEndOperator(currentPlayer),
                new SnapszerOperator(currentPlayer),
                new SwapTrumpOperator(currentPlayer)));
        for (HungarianCard card :
                currentPlayer.getCards()) {
            PlayCardOperator op = new PlayCardOperator(currentPlayer, card);
            allOperators.add(op);
        }
        return allOperators;
    }

    protected List<Operator> getAllApplicableOperators(Player currentPlayer) {
        return getAllOperators(currentPlayer).stream().
                filter(op -> op.isApplicable(this)).
                collect(Collectors.toList());
    }

    public Player getWinnerPlayer() {
        if (isCover()) {
            if (getSayerPlayer().getScore() < 66) {
                return getNotSayerPlayer();
            } else {
                return getSayerPlayer();
            }
        } else if (isSnapszer()) {
            if (getSayerPlayer().getScore() < 66 || getNotSayerPlayer().getBeatsCounter() > 0) {
                return getNotSayerPlayer();
            } else {
                return getSayerPlayer();
            }
        } else {
            return currentPlayer;
        }
    }

    public Player getLoserPlayer() {
        return getWinnerPlayer().equals(currentPlayer) ? nextPlayer : currentPlayer;
    }

    public int getWonPoints() {
        Player winnerPlayer = getWinnerPlayer();
        Player loserPlayer = getLoserPlayer();
        if (isCover()) {
            if (winnerPlayer.equals(getSayerPlayer())) {
                if (getNotSayerPlayer().getBeatsCounter() == 0) {
                    return 3;
                } else if (getNotSayerPlayer().getScore() < 33) {
                    return 2;
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        } else if (isSnapszer()) {
            return 6;
        } else {
            if (loserPlayer.getBeatsCounter() == 0)
                return 3;
            else if (loserPlayer.getScore() < 33)
                return 2;
            else {
                return 1;
            }
        }
    }
}
