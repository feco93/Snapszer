package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.Game;
import hu.unideb.snapszer.model.Player;

import java.util.ArrayList;

/**
 * Created by Fecó on 2015.12.05..
 */
public abstract class Operator {

    private static ArrayList<OperatorListener> listeners;

    static {
        listeners = new ArrayList<>();
    }

    protected final Player player;

    protected Operator(Player player) {
        this.player = player;
    }

    abstract public boolean isApplicable(Game game);

    abstract protected void onApply(Game game);

    public void apply(Game game) {
        onApply(game);
        for (OperatorListener listener :
                listeners) {
            listener.onOperatorApplied(this, player);
        }
    }

    public static void addListener(OperatorListener listener) {
        listeners.add(listener);
    }
}
