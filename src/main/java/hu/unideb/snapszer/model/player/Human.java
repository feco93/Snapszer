/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model.player;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.operators.Operator;
import org.apache.logging.log4j.Level;

/**
 * @author Fecó
 */
public class Human extends Player {

    private Operator chosenOperator;

    public Human(String name) {
        super(name);
    }

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
            logger.log(Level.INFO, "The game has been stopped.");
        }
        return null;
    }
}
