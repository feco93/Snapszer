package hu.unideb.snapszer.view;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;

import java.util.Collection;

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

    public void addAnimations(Collection<Animation> animations) {
        sequentialTransition.getChildren().addAll(animations);
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
