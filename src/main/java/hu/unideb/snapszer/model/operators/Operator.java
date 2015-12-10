package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.Game;

/**
 * Created by Fecó on 2015.12.05..
 */
public interface Operator {

    boolean isApplicable(Game game);

    void apply(Game game);
}
