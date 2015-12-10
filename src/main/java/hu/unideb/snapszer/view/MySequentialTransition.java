package hu.unideb.snapszer.view;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;

/**
 * Created by Fecó on 2015.12.09..
 */
public class MySequentialTransition {

    private SequentialTransition sequentialTransition;

    public MySequentialTransition() {
        sequentialTransition = new SequentialTransition();
        sequentialTransition.setOnFinished(this::endAnimation);
    }

    public void addAnimation(Animation animation) {
        sequentialTransition.getChildren().add(animation);
    }

    public synchronized void playAnimationSynchronous() {
        sequentialTransition.play();
        try {
            this.wait();
            sequentialTransition.getChildren().clear();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void endAnimation(ActionEvent actionEvent) {
        this.notify();
    }
}
