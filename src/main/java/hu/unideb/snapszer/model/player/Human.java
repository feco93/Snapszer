/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.operators.Operator;

/**
 * @author Fecó
 */
public class Human extends Player {

    private Operator chosenOperator;

    public Human(String name) {
        super(name);
    }

    @Override
    public synchronized void setChosenOperator(Operator operator) {
        chosenOperator = operator;
        notify();
    }

    @Override
    public synchronized Operator chooseOperator(GameMatch game) {
        try {
            wait();
            return chosenOperator;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
