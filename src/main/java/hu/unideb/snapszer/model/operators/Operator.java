package hu.unideb.snapszer.model.operators;

import hu.unideb.snapszer.model.GameMatch;
import hu.unideb.snapszer.model.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by Fecó on 2015.12.05..
 */
public abstract class Operator {

    private static Logger logger = LogManager.getLogger(Operator.class);

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
        try {
            if (!isApplicable(game))
                throw new InvalidOperationApplyException(toString());
            onApply(game);
            for (OperatorListener listener :
                    listeners) {
                listener.onOperatorApplied(this);
            }
        } catch (InvalidOperationApplyException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
