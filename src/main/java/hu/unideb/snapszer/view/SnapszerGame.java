/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.view;

import hu.unideb.snapszer.model.Deck;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.ICard;
import hu.unideb.snapszer.model.Player;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;

/**
 *
 * @author Fecó
 */
public class SnapszerGame extends Group {

    private Node table;
    private final ObservableList<HungarianCardView> cardsInDeck;
    private final ObservableList<HungarianCardView> cardsOnTable;
    private final ObservableList<HungarianCardView> cardsInPlayerOneHand;
    private final ObservableList<HungarianCardView> cardsInPlayerTwoHand;
    private final double distanceBetweenCards = 0.5;
    private SequentialTransition sequence = new SequentialTransition();
    private boolean started;

    public SnapszerGame(Deck deck, Player playerOne, Player playerTwo) {
        started = false;
        sequence.getChildren().addListener((ListChangeListener.Change<? extends Animation> c) -> {
            if (c.getList().size() == 11 || (started && c.getList().size() == 2)) {
                started = true;
                sequence.play();
                sequence = new SequentialTransition();
            }
        });
        cardsInDeck = FXCollections.observableArrayList();
        cardsOnTable = FXCollections.observableArrayList();
        cardsInPlayerOneHand = FXCollections.observableArrayList();
        cardsInPlayerTwoHand = FXCollections.observableArrayList();
        initTable();
        initCards(deck);
        initPlayers(playerOne, playerTwo);
    }

    private void initPlayers(Player playerOne, Player playerTwo) {
        cardsInPlayerOneHand.addListener((ListChangeListener.Change<? extends HungarianCardView> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (HungarianCardView card : c.getAddedSubList()) {
                        int n = c.getList().size();
                        Timeline tl = card.drawCard(-90 + 30 * n, -30, -150, -30);
                        sequence.getChildren().add(tl);
                    }
                }
            }
        });
        cardsInPlayerTwoHand.addListener((ListChangeListener.Change<? extends HungarianCardView> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (HungarianCardView card : c.getAddedSubList()) {
                        int n = c.getList().size();
                        Timeline tl = card.drawCard(-90 + 30 * n, -30, 120, 150);
                        sequence.getChildren().add(tl);
                    }
                }
            }
        });
        playerOne.cards.addListener((ListChangeListener.Change<? extends ICard> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (ICard card : c.getAddedSubList()) {
                        card = (HungarianCard) card;
                        cardsInPlayerOneHand.add(cardsInDeck.remove(cardsInDeck.indexOf(card)));
                    }
                }
                if (c.wasRemoved()) {
                    for (ICard card : c.getRemoved()) {
                        card = (HungarianCard) card;
                        cardsOnTable.add(cardsInPlayerOneHand.remove(cardsInPlayerOneHand.indexOf(card)));
                    }
                }
            }
        });
        playerTwo.cards.addListener((ListChangeListener.Change<? extends ICard> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (ICard card : c.getAddedSubList()) {
                        card = (HungarianCard) card;
                        cardsInPlayerTwoHand.add(cardsInDeck.remove(cardsInDeck.indexOf(card)));
                    }
                }
                if (c.wasRemoved()) {
                    for (ICard card : c.getRemoved()) {
                        card = (HungarianCard) card;
                        cardsOnTable.add(cardsInPlayerTwoHand.remove(cardsInPlayerTwoHand.indexOf(card)));
                    }
                }
            }
        });
    }

    private void initTable() {
        Table tableView = new Table();
        table = tableView.getTableView();
        getChildren().addAll(table);
        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateY(-200);
        pointLight.getScope().addAll(table);
        getChildren().add(pointLight);
    }

    private void initCards(Deck deck) {
        double index = -1;
        for (ICard card : deck) {
            HungarianCardView cardView
                    = new HungarianCardView(
                            (HungarianCard) card,
                            new Point3D(80, index, 0));
            AmbientLight light = new AmbientLight(Color.WHITE);
            light.getScope().add(cardView);
            getChildren().add(light);
            getChildren().add(cardView);
            index -= distanceBetweenCards;
            cardsInDeck.add(cardView);
        }
        deck.cards.addListener((ListChangeListener.Change<? extends ICard> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {                    
                    HungarianCard card = (HungarianCard) c.getAddedSubList().get(0);
                    sequence.getChildren().add(cardsInDeck.get(cardsInDeck.indexOf(card)).setTrump(60, -0.5));
                }
            }
        });
    }
}
