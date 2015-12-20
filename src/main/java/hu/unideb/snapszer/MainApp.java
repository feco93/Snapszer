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

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Rotate;

public class MainApp extends Application {

    private Scene scene;
    private Group game;
    private AnchorPane gameInfo;
    private SubScene asd;
    private StackPane root;
    private TableView tableView;
    private DeckView deckView;
    private HandView playerOneHandView;
    private HandView playerTwoHandView;
    private GameController controller;
    private PlayedCardsView playedCardsView;
    private CalledCardsView calledCardsView;
    private Text playerOneScore;
    private Text playerTwoScore;

    private PerspectiveCamera addCamera(SubScene scene) {
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
        game = new Group();
        gameInfo = new AnchorPane();
        asd = new SubScene(this.game, 0, 0, true, SceneAntialiasing.BALANCED);
        root = new StackPane();
        root.getChildren().addAll(gameInfo, asd);

        Deck deck = SnapszerDeck.getNewDeck();
        Player playerOne = new Human();
        Player playerTwo = new Computer();

        playerOne.scoreProperty().addListener((observable, oldValue, newValue) -> {
            playerOneScore.setText(String.format("Player: %d\n", newValue.intValue()));
        });

        playerTwo.scoreProperty().addListener((observable, oldValue, newValue) -> {
            playerTwoScore.setText(String.format("Computer: %d\n", newValue.intValue()));
        });


        TextFlow scoreContainer = new TextFlow();
        scoreContainer.setTextAlignment(TextAlignment.LEFT);
        playerOneScore = new Text("Player: 0\n");
        playerOneScore.setFill(Color.RED);
        playerOneScore.setFont(new Font("Arial", 32));
        playerTwoScore = new Text("Computer: 0");
        playerTwoScore.setFill(Color.RED);
        playerTwoScore.setFont(new Font("Arial", 32));
        scoreContainer.getChildren().addAll(playerOneScore, playerTwoScore);
        Button btn = new Button("ASD");
        AnchorPane.setTopAnchor(scoreContainer, 10.0);
        AnchorPane.setLeftAnchor(scoreContainer, 10.0);
        AnchorPane.setBottomAnchor(btn, 100.0);
        AnchorPane.setRightAnchor(btn, 100.0);
        gameInfo.getChildren().addAll(scoreContainer, btn);


        Game game = new Game(playerOne, playerTwo, deck);

        tableView = new TableView();
        deckView = new DeckView(game.getDeck());
        calledCardsView = new CalledCardsView();
        playedCardsView = new PlayedCardsView(game.getPlayedCards(), calledCardsView.cards);
        playerOneHandView = new HandView(game.getCurrentPlayer(), deckView, calledCardsView);
        playerTwoHandView = new HandView(game.getNextPlayer(), deckView, calledCardsView);


        this.game.getChildren().add(tableView);
        this.game.getChildren().add(deckView);
        controller = new GameController(playerOneHandView, this.game);

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
        primaryStage.show();
        asd.setWidth(primaryStage.getWidth());
        asd.setHeight(primaryStage.getHeight());
        addCamera(asd);

        Thread th = new Thread(game, "Game");
        th.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
