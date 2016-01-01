package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.Player;

/**
 * Created by Fecó on 2016.01.01..
 */
@FunctionalInterface
public interface OperatorListener {
    void onOperatorApplied(Operator operator, Player player);
}
