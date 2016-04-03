package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.SnapszerDeck;
import hu.unideb.snapszer.model.operators.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by User on 2016. 04. 02..
 */
public class ComputerWorldChampion extends Computer {

    public ComputerWorldChampion() {
        this("Computer (WorldChampion)");
    }

    public ComputerWorldChampion(String name) {
        super(name);
    }

    @Override
    public Operator chooseOperator(GameMatch game) {
        return chooseGoodOperator(game);
    }

    private Operator chooseGoodOperator(GameMatch game) {
        return getAllApplicableOperators(game).stream().min(
                (o1, o2) -> getHeuristic(o1, game).compareTo(getHeuristic(o2, game))).get();

    }

    private Integer getHeuristic(Operator op, GameMatch game) {
        if (op instanceof SayEndOperator)
            return -1;
        if (op instanceof SwapTrumpOperator)
            return 0;
        if (op instanceof Say40Operator)
            return Math.max(66 - getScore() - 40, 0);
        if (op instanceof Say20Operator)
            return Math.max(66 - getScore() - 20, 0);
        if (op instanceof PlayCardOperator) {
            PlayCardOperator playCardOperator = (PlayCardOperator) op;
            if (!game.getCardsOnTable().isEmpty()) {
                if (game.getCardsOnTable().get(0).compareTo(playCardOperator.getCard()) < 0) {
                    return Math.max(66 - getScore() - playCardOperator.getCard().getPoints(), 0);
                } else {
                    return 66 - getScore() + playCardOperator.getCard().getPoints();
                }
            } else {
                if (higherCardInGame(playCardOperator.getCard(), game.getPlayedCards())) {
                    return Math.max(66 - getScore() + playCardOperator.getCard().getPoints(), 0);
                }
                return Math.max(66 - getScore() - playCardOperator.getCard().getPoints(), 0);
            }
        }
        return 100;
    }

    private boolean higherCardInGame(HungarianCard card, List<HungarianCard> playedCards) {
        return !playedCards.containsAll(higherCards(card));
    }

    private List<HungarianCard> higherCards(HungarianCard card) {
        return SnapszerDeck.getNewDeck().cards.stream().filter(
                otherCard -> card.getSuit() == otherCard.getSuit() &&
                        otherCard.getRank().compareTo(otherCard.getRank()) > 0).collect(Collectors.toList());
    }

    private boolean lowerCardInGame(HungarianCard card, List<HungarianCard> playedCards) {
        return !playedCards.containsAll(lowerCards(card));
    }

    private List<HungarianCard> lowerCards(HungarianCard card) {
        return SnapszerDeck.getNewDeck().cards.stream().filter(
                otherCard -> card.getSuit() == otherCard.getSuit() &&
                        otherCard.getRank().compareTo(otherCard.getRank()) < 0).collect(Collectors.toList());
    }
}
