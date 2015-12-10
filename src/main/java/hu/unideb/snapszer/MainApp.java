package hu.unideb.snapszer;

import hu.unideb.snapszer.controller.GameController;
import hu.unideb.snapszer.model.Computer;
import hu.unideb.snapszer.model.Deck;
import hu.unideb.snapszer.model.Game;
import hu.unideb.snapszer.model.Human;
import hu.unideb.snapszer.model.Player;
import hu.unideb.snapszer.model.SnapszerDeck;
import hu.unideb.snapszer.view.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

public class MainApp extends Application {

    private Scene scene;
    private Group root;
    private TableView tableView;
    private DeckView deckView;
    private HandView playerOneHandView;
    private HandView playerTwoHandView;
    private GameController controller;
    private PlayedCardsView playedCardsView;
    private CalledCardsView calledCardsView;

    private PerspectiveCamera addCamera(Scene scene) {
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(true);
        double NEAR = 0.01;
        double FAR = 1000;
        perspectiveCamera.setFarClip(FAR);
        perspectiveCamera.setNearClip(NEAR);
        perspectiveCamera.setTranslateY(-350);
        perspectiveCamera.setTranslateZ(-330);
        perspectiveCamera.setRotationAxis(Rotate.X_AXIS);
        perspectiveCamera.setRotate(-50);
        scene.setCamera(perspectiveCamera);
        return perspectiveCamera;
    }    

    @Override
    public void start(Stage primaryStage) {
        root = new Group();

        Deck deck = SnapszerDeck.getNewDeck();
        Player playerOne = new Human();
        Player playerTwo = new Computer();

        Game game = new Game(playerOne, playerTwo, deck);

        tableView = new TableView();
        deckView = new DeckView(game.getDeck());
        calledCardsView = new CalledCardsView();
        playedCardsView = new PlayedCardsView(game.getPlayedCards(), calledCardsView.cards);
        playerOneHandView = new HandView(game.getCurrentPlayer(), deckView, calledCardsView);
        playerTwoHandView = new HandView(game.getNextPlayer(), deckView, calledCardsView);


        root.getChildren().add(tableView);
        root.getChildren().add(deckView);
        controller = new GameController(playerOneHandView, root);
        
        scene = new Scene(root, 0, 0, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.rgb(10, 10, 40));
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });
        primaryStage.setFullScreenExitHint("");
        primaryStage.fullScreenExitKeyProperty().set(KeyCombination.NO_MATCH);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Snapszer");
        primaryStage.setScene(scene);
        addCamera(scene);
        primaryStage.show();
        Thread th = new Thread(game, "Game");
        th.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
