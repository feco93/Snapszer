/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.unideb.snapszer.controller;

import hu.unideb.snapszer.model.Human;
import hu.unideb.snapszer.model.operators.CallOperator;
import hu.unideb.snapszer.model.operators.SwapTrumpOperator;
import hu.unideb.snapszer.view.HungarianCardView;
import hu.unideb.snapszer.view.PlayerView;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Group;


/**
 * @author Fecó
 */

public class GameController {


    private final ObservableList<HungarianCardView> handView;

    public GameController(PlayerView player, ObjectProperty<HungarianCardView> trumpCard, Group root) {
        this.handView = player.getCardsInHand();
        handView.addListener((ListChangeListener.Change<? extends HungarianCardView> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (HungarianCardView cardView :
                            c.getAddedSubList()) {
                        cardView.setOnMouseClicked(event -> {
                            Human human = (Human) player.getPlayer();
                            human.setChosenOperator(new CallOperator(human, cardView.getCard()));
                        });
                        cardView.setOnMouseEntered(event -> root.setCursor(Cursor.HAND));
                        cardView.setOnMouseExited(event -> root.setCursor(Cursor.DEFAULT));
                    }
                }
                if (c.wasRemoved()) {
                    for (HungarianCardView cardView : c.getRemoved()) {
                        cardView.setOnMouseClicked(null);
                        cardView.setOnMouseEntered(null);
                        cardView.setOnMouseExited(null);
                    }
                }
            }
        });
        trumpCard.addListener(observable -> {
            trumpCard.getValue().setOnMouseEntered(event -> root.setCursor(Cursor.HAND));
            trumpCard.getValue().setOnMouseExited(event -> root.setCursor(Cursor.DEFAULT));
            trumpCard.getValue().setOnMouseClicked(event -> {
                Human human = (Human) player.getPlayer();
                human.setChosenOperator(new SwapTrumpOperator(human));
            });
        });
    }
}

