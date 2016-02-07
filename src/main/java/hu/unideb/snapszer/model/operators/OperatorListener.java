package hu.unideb.snapszer.model.operators;

/**
 * Created by Fecó on 2016.01.01..
 */
@FunctionalInterface
public interface OperatorListener {
    void onOperatorApplied(Operator operator);
}
