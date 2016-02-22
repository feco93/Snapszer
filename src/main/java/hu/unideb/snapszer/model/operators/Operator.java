package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.player.Player;

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

    public static void addListener(OperatorListener listener) {
        listeners.add(listener);
    }

    public static void clearListeners() {
        listeners.clear();
    }

    public Player getPlayer() {
        return player;
    }

    abstract public boolean isApplicable(GameMatch game);

    abstract protected void onApply(GameMatch game);

    public void apply(GameMatch game) {
        onApply(game);
        for (OperatorListener listener :
                listeners) {
            listener.onOperatorApplied(this);
        }
    }
}
