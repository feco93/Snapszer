package hu.unideb.snapszer;

import hu.unideb.snapszer.view.PreLoaderView;

public class MainApp {


    public static void main(String[] args) {
        /*GameKernel kernel = new GameKernel(GameMode.GUI);*/
        PreLoaderView preLoaderView = new PreLoaderView();
        preLoaderView.display();
    }

}
