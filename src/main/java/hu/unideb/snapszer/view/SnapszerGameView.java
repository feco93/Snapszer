package hu.unideb.snapszer.view;

import hu.unideb.snapszer.model.*;
import hu.unideb.snapszer.model.operators.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fecó on 2015.12.09..
 */
public class SnapszerGameView extends Group {

    public final ObservableList<HungarianCardView> hungarianCardViews;
    private final double distanceBetweenCards = 0.5;
    private final List<PlayerView> playerViews;
    private final CalledCardsView calledCardsView;
    private final PlayedCardsView playedCardsView;
    private final MySequentialTransition sequentialTransition;
    private PlayerView humanPlayerView;
    private ObjectProperty<HungarianCardView> trumpCardView;

    public SnapszerGameView(GameMatch game) {
        playerViews = new ArrayList<>();
        for (Player player :
                game.getPlayers()) {
            PlayerView playerView;
            if (player instanceof Human) {
                playerView = new PlayerView(player);
                humanPlayerView = playerView;
            } else {
                playerView = new PlayerView(player);
            }
            playerViews.add(playerView);

        }
        calledCardsView = new CalledCardsView();
        playedCardsView = new PlayedCardsView(game.getPlayedCards(), calledCardsView.cards);
        sequentialTransition = new MySequentialTransition();
        trumpCardView = new SimpleObjectProperty<>();
        hungarianCardViews = FXCollections.observableArrayList();
        double index = -3;
        for (ICard card : game.getDeck()) {
            HungarianCardView cardView
                    = new HungarianCardView(
                    (HungarianCard) card,
                    new Point3D(80, index, 0));
            AmbientLight light = new AmbientLight(Color.WHITE);
            light.getScope().add(cardView);
            getChildren().add(light);
            getChildren().add(cardView);
            index -= distanceBetweenCards;
            hungarianCardViews.add(cardView);
        }
        Operator.clearListeners();
        Operator.addListener(this::onOperatorApplied);
        game.trumpCardProperty().addListener(observable -> {
            trumpCardView.setValue(hungarianCardViews.get(
                    hungarianCardViews.indexOf(game.trumpCardProperty().getValue())));
            sequentialTransition.addAnimation(
                    trumpCardView.getValue().setTrump(60, -1));
            sequentialTransition.playAnimationSynchronous();
        });
    }

    private void onOperatorApplied(Operator operator) {
        if (operator instanceof DrawOperator) {
            onDrawOperatorApplied((DrawOperator) operator);
        }
        if (operator instanceof CallOperator) {
            onCallOperatorApplied((CallOperator) operator);
        }
        if (operator instanceof SwapTrumpOperator) {
            onSwapTrumpOperatorApplied((SwapTrumpOperator) operator);
        }
        if (operator instanceof CoverOperator) {
            onCoverOperatorApplied((CoverOperator) operator);
        }
    }

    private void onCoverOperatorApplied(CoverOperator operator) {
        sequentialTransition.addAnimation(
                trumpCardView.getValue().coverOrSnapszer(60, -20));
        sequentialTransition.playAnimationSynchronous();
    }

    private void onSwapTrumpOperatorApplied(SwapTrumpOperator operator) {
        PlayerView player = playerViews.stream().filter(
                playerView -> playerView.getPlayer().equals(operator.getPlayer())).findFirst().get();
        HungarianCardView newTrumpCard = player.removeCard(operator.getNewTrumpCard());
        HungarianCardView oldTrumpCard = hungarianCardViews.stream().
                filter(cardView ->
                        cardView.getCard().equals(operator.getOldTrumpCard())).
                findFirst().get();
        player.drawCard(oldTrumpCard);
    }

    private void onCallOperatorApplied(CallOperator operator) {
        PlayerView player = playerViews.stream().filter(
                playerView -> playerView.getPlayer().equals(operator.getPlayer())).findFirst().get();
        HungarianCardView calledCard = player.callCard(operator.getCard());
        calledCardsView.add(calledCard);
    }

    private void onDrawOperatorApplied(DrawOperator operator) {
        PlayerView player = playerViews.stream().filter(
                playerView -> playerView.getPlayer().equals(operator.getPlayer())).findFirst().get();
        HungarianCardView card = hungarianCardViews.stream().
                filter(cardView ->
                        cardView.getCard().equals(operator.getCard())).
                findFirst().get();
        player.drawCard(card);
    }

    public HungarianCardView getTrumpCardView() {
        return trumpCardView.get();
    }

    public ObjectProperty<HungarianCardView> trumpCardViewProperty() {
        return trumpCardView;
    }

    public PlayerView getHumanPlayerView() {
        return humanPlayerView;
    }
}
