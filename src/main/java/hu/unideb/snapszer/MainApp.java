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
    private Group gameView;
    private AnchorPane gameInfo;
    private SubScene game3d;
    private StackPane root;
    private TableView tableView;
    private DeckView deckView;
    private GameController controller;
    private PlayedCardsView playedCardsView;
    private CalledCardsView calledCardsView;
    private Text playerOneScore;
    private Text playerTwoScore;
    private Game game;
    private HumanView playerOneView;
    private PlayerView playerTwoView;
    private Human playerOne;
    private Computer playerTwo;

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

    private void initGameInfo() {
        TextFlow scoreContainer = new TextFlow();
        scoreContainer.setTextAlignment(TextAlignment.LEFT);
        playerOneScore = new Text("Player: 0\n");
        playerOneScore.setFill(Color.RED);
        playerOneScore.setFont(new Font("Arial", 32));
        playerTwoScore = new Text("Computer: 0");
        playerTwoScore.setFill(Color.RED);
        playerTwoScore.setFont(new Font("Arial", 32));
        scoreContainer.getChildren().addAll(playerOneScore, playerTwoScore);
        AnchorPane.setTopAnchor(scoreContainer, 10.0);
        AnchorPane.setLeftAnchor(scoreContainer, 10.0);
        Button say20 = playerOneView.getSay20Btn();
        AnchorPane.setBottomAnchor(say20, 100.0);
        AnchorPane.setRightAnchor(say20, 100.0);
        Button say40 = playerOneView.getSay40Btn();
        AnchorPane.setBottomAnchor(say40, 140.0);
        AnchorPane.setRightAnchor(say40, 100.0);
        Button sayEnd = playerOneView.getSayFinishBtn();
        AnchorPane.setBottomAnchor(sayEnd, 180.0);
        AnchorPane.setRightAnchor(sayEnd, 100.0);
        gameInfo.getChildren().addAll(scoreContainer, say20, say40, sayEnd);
    }

    private void initGame() {
        Deck deck = SnapszerDeck.getNewDeck();
        playerOne = new Human();
        playerTwo = new Computer();
        playerOne.scoreProperty().addListener((observable, oldValue, newValue) -> {
            playerOneScore.setText(String.format("Player: %d\n", newValue.intValue()));
        });

        playerTwo.scoreProperty().addListener((observable, oldValue, newValue) -> {
            playerTwoScore.setText(String.format("Computer: %d\n", newValue.intValue()));
        });
        game = new Game(playerOne, playerTwo, deck);

        tableView = new TableView();
        deckView = new DeckView(game.getDeck());
        calledCardsView = new CalledCardsView();
        playedCardsView = new PlayedCardsView(game.getPlayedCards(), calledCardsView.cards);
        playerOneView = new HumanView(playerOne, deckView, calledCardsView);
        playerTwoView = new PlayerView(playerTwo, deckView, calledCardsView);
        this.gameView.getChildren().addAll(tableView, deckView);
        controller = new GameController(playerOneView, this.gameView);
    }

    @Override
    public void start(Stage primaryStage) {
        gameView = new Group();
        gameInfo = new AnchorPane();
        game3d = new SubScene(this.gameView, 0, 0, true, SceneAntialiasing.BALANCED);
        root = new StackPane();
        root.getChildren().addAll(gameInfo, game3d);

        initGame();
        initGameInfo();

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
        game3d.setWidth(primaryStage.getWidth());
        game3d.setHeight(primaryStage.getHeight());
        addCamera(game3d);

        Thread th = new Thread(game, "Game");
        th.start();
       /* try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (playerOne.getScore() > playerTwo.getScore()) {
            System.out.println("A játékos győzött!");
        }
        else {
            System.out.println("A számítógép győzött!");
        }*/
    }

    public static void main(String[] args) {
        launch(args);
    }

}
