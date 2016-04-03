/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.unideb.snapszer.controller;

import hu.unideb.snapszer.model.operators.PlayCardOperator;
import hu.unideb.snapszer.model.operators.SwapTrumpOperator;
import hu.unideb.snapszer.model.player.Human;
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

    public GameController(PlayerView player, ObjectProperty<HungarianCardView> trumpCard, Group scene) {
        this.handView = player.getCardsInHand();
        handView.addListener((ListChangeListener.Change<? extends HungarianCardView> c) -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (HungarianCardView cardView :
                            c.getAddedSubList()) {
                        cardView.setOnMouseClicked(event -> {
                            Human human = (Human) player.getPlayer();
                            human.setChosenOperator(new PlayCardOperator(human, cardView.getCard()));
                        });
                        cardView.setOnMouseEntered(event -> scene.setCursor(Cursor.HAND));
                        cardView.setOnMouseExited(event -> scene.setCursor(Cursor.DEFAULT));
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
        trumpCard.addListener((observable1, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.setOnMouseEntered(null);
                oldValue.setOnMouseExited(null);
                oldValue.setOnMouseClicked(null);
            }

            newValue.setOnMouseEntered(event -> scene.setCursor(Cursor.HAND));
            newValue.setOnMouseExited(event -> scene.setCursor(Cursor.DEFAULT));
            newValue.setOnMouseClicked(event -> {
                Human human = (Human) player.getPlayer();
                human.setChosenOperator(new SwapTrumpOperator(human));
            });
        });
    }
}

