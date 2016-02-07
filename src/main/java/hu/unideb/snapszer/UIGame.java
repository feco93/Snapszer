package hu.unideb.snapszer;

import hu.unideb.snapszer.controller.GameController;
import hu.unideb.snapszer.model.Computer;
import hu.unideb.snapszer.model.Human;
import hu.unideb.snapszer.model.Player;
import hu.unideb.snapszer.model.SnapszerTwoPlayerGame;
import hu.unideb.snapszer.view.SnapszerGameView;
import hu.unideb.snapszer.view.TableView;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * Created by Fecó Sipos on 2016. 01. 31..
 */
public class UIGame implements Game {

    private Scene scene;
    private Group gameView;
    private AnchorPane gameInfo;
    private SubScene game3d;
    private StackPane root;
    private TableView tableView;
    private SnapszerGameView snapszerGameView;
    private GameController controller;
    private Text playerOneScore;
    private Text playerTwoScore;
    private Text playerOnePoints;
    private Text playerTwoPoints;
    private Text pointsInfo;
    private SnapszerTwoPlayerGame game;
    private Player playerOne;
    private Player playerTwo;

    public UIGame() {
        gameView = new Group();
        tableView = new TableView();
        this.gameView.getChildren().add(tableView);
        //initGameInfo();
        game3d = new SubScene(this.gameView, 0, 0, true, SceneAntialiasing.BALANCED);
        root = new StackPane();
        root.getChildren().addAll(/*gameInfo,*/ game3d);
        scene = new Scene(root, 0, 0, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.rgb(10, 10, 40));
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });
    }

    @Override
    public void Start() {
        playerOne = new Human();
        playerTwo = new Computer();
        game = new SnapszerTwoPlayerGame(playerOne, playerTwo);
        snapszerGameView = new SnapszerGameView(game.getGameMatch());
        this.gameView.getChildren().addAll(snapszerGameView);
        controller = new GameController(
                snapszerGameView.getHumanPlayerView(),
                snapszerGameView.trumpCardViewProperty()
                , this.gameView);
        Thread gameThread = new Thread(game);
        gameThread.start();
    }

    public void DisplayGame() {
        Stage primaryStage = new Stage();
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
        Start();
    }

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
        gameInfo = new AnchorPane();
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
        TextFlow pointsContainer = new TextFlow();
        pointsContainer.setTextAlignment(TextAlignment.LEFT);
        pointsInfo = new Text("Points:\n");
        pointsInfo.setFill(Color.RED);
        pointsInfo.setFont(new Font("Arial", 32));
        playerOnePoints = new Text("Player: 0\n");
        playerOnePoints.setFill(Color.RED);
        playerOnePoints.setFont(new Font("Arial", 32));
        playerTwoPoints = new Text("Computer: 0");
        playerTwoPoints.setFill(Color.RED);
        playerTwoPoints.setFont(new Font("Arial", 32));
        pointsContainer.getChildren().addAll(pointsInfo, playerOnePoints, playerTwoPoints);
        AnchorPane.setLeftAnchor(pointsContainer, 10.0);
        AnchorPane.setBottomAnchor(pointsContainer, 10.0);
        Button say20 = snapszerGameView.getHumanPlayerView().getSay20Btn();
        AnchorPane.setBottomAnchor(say20, 100.0);
        AnchorPane.setRightAnchor(say20, 100.0);
        Button say40 = snapszerGameView.getHumanPlayerView().getSay40Btn();
        AnchorPane.setBottomAnchor(say40, 140.0);
        AnchorPane.setRightAnchor(say40, 100.0);
        Button snapszer = snapszerGameView.getHumanPlayerView().getSnapszerBtn();
        AnchorPane.setBottomAnchor(snapszer, 220.0);
        AnchorPane.setRightAnchor(snapszer, 100.0);
        Button cover = snapszerGameView.getHumanPlayerView().getCoverBtn();
        AnchorPane.setBottomAnchor(cover, 260.0);
        AnchorPane.setRightAnchor(cover, 100.0);
        Button sayEnd = snapszerGameView.getHumanPlayerView().getSayFinishBtn();
        AnchorPane.setBottomAnchor(sayEnd, 180.0);
        AnchorPane.setRightAnchor(sayEnd, 100.0);
        gameInfo.getChildren().addAll(scoreContainer, pointsContainer, say20, say40, snapszer, cover, sayEnd);
        playerOne.scoreProperty().addListener((observable, oldValue, newValue) -> {
            playerOneScore.setText(String.format("Player: %d\n", newValue.intValue()));
        });
        playerOne.pointsProperty().addListener((observable, oldValue, newValue) -> {
            playerOnePoints.setText(String.format("Player: %d\n", newValue.intValue()));
        });

        playerTwo.scoreProperty().addListener((observable, oldValue, newValue) -> {
            playerTwoScore.setText(String.format("Computer: %d\n", newValue.intValue()));
        });
        playerTwo.pointsProperty().addListener((observable, oldValue, newValue) -> {
            playerTwoPoints.setText(String.format("Computer: %d\n", newValue.intValue()));
        });
    }

}
