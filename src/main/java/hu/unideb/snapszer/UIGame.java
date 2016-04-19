package hu.unideb.snapszer;

import hu.unideb.snapszer.controller.GameController;
import hu.unideb.snapszer.controller.HumanPlayerController;
import hu.unideb.snapszer.model.SnapszerTwoPlayerGame;
import hu.unideb.snapszer.model.player.Computer;
import hu.unideb.snapszer.model.player.ComputerAdvanced;
import hu.unideb.snapszer.model.player.Human;
import hu.unideb.snapszer.view.SnapszerGameView;
import hu.unideb.snapszer.view.TableView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Rotate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by Fecó Sipos on 2016. 01. 31..
 */
public class UIGame implements Game {

    private StackPane root;
    private SnapszerTwoPlayerGame game;
    public static Type computerType = ComputerAdvanced.class;
    private static final Logger logger = LogManager.getLogger(UIGame.class);

    public UIGame(double width, double height) {
        root = new StackPane();
        Group gameView = new Group();
        TableView tableView = new TableView();
        gameView.getChildren().add(tableView);
        SubScene game3d = new SubScene(gameView, width, height, true, SceneAntialiasing.BALANCED);
        addCamera(game3d);
        root.getChildren().add(game3d);

        Human playerOne = new Human("Player");
        Computer playerTwo = null;
        try {
            playerTwo = (Computer) Class.forName(computerType.getTypeName()).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        logger.info(computerType.getTypeName());
        initGameInfo(playerOne, playerTwo);
        game = new SnapszerTwoPlayerGame(playerOne, playerTwo);

        game.gameMatchProperty().addListener((event) ->
        {
            SnapszerGameView snapszerGameView = new SnapszerGameView(game.getGameMatch());
            GameController gameController = new GameController(
                    snapszerGameView.getHumanPlayerView(),
                    snapszerGameView.trumpCardViewProperty(),
                    gameView);
            Platform.runLater(() -> {
                gameView.getChildren().clear();
                gameView.getChildren().addAll(tableView, snapszerGameView);
            });

        });
    }

    public StackPane getRoot() {
        return root;
    }

    @Override
    public void Start(int numberOfGame) {
        Thread gameThread = new Thread(game);
        gameThread.setDaemon(true);
        gameThread.start();
    }

    public Task getGame() {
        return game;
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

    private void initGameInfo(Human playerOne, Computer playerTwo) {
        AnchorPane gameInfo = new AnchorPane();
        TextFlow scoreContainer = new TextFlow();
        scoreContainer.setTextAlignment(TextAlignment.LEFT);
        Text playerOneScore = new Text("Player: 0\n");
        playerOneScore.setFill(Color.RED);
        playerOneScore.setFont(new Font("Arial", 32));
        Text playerTwoScore = new Text("Computer: 0");
        playerTwoScore.setFill(Color.RED);
        playerTwoScore.setFont(new Font("Arial", 32));
        scoreContainer.getChildren().addAll(playerOneScore, playerTwoScore);
        AnchorPane.setTopAnchor(scoreContainer, 10.0);
        AnchorPane.setLeftAnchor(scoreContainer, 10.0);
        TextFlow pointsContainer = new TextFlow();
        pointsContainer.setTextAlignment(TextAlignment.LEFT);
        Text pointsInfo = new Text("Points:\n");
        pointsInfo.setFill(Color.RED);
        pointsInfo.setFont(new Font("Arial", 32));
        Text playerOnePoints = new Text("Player: 0\n");
        playerOnePoints.setFill(Color.RED);
        playerOnePoints.setFont(new Font("Arial", 32));
        Text playerTwoPoints = new Text("Computer: 0");
        playerTwoPoints.setFill(Color.RED);
        playerTwoPoints.setFont(new Font("Arial", 32));
        pointsContainer.getChildren().addAll(pointsInfo, playerOnePoints, playerTwoPoints);
        AnchorPane.setLeftAnchor(pointsContainer, 10.0);
        AnchorPane.setBottomAnchor(pointsContainer, 10.0);

        Node controlPanel = null;
        FXMLLoader loader =
                new FXMLLoader(HumanPlayerController.class.getResource("/fxml/controlPanel.fxml"));
        try {
            controlPanel = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HumanPlayerController controller = loader.getController();
        controller.setPlayer(playerOne);
        AnchorPane.setBottomAnchor(controlPanel, 70.0);
        AnchorPane.setRightAnchor(controlPanel, 50.0);

        gameInfo.getChildren().addAll(scoreContainer, pointsContainer, controlPanel);
        playerOne.scoreProperty().addListener((observable, oldValue, newValue) -> {
            playerOneScore.setText(String.format("Player: %d\n", newValue.intValue()));
        });
        playerOne.pointsProperty().addListener((observable, oldValue, newValue) -> {
            playerOnePoints.setText(String.format("Player: %d\n", newValue.intValue()));
        });

        playerTwo.scoreProperty().addListener((observable, oldValue, newValue) -> {
            playerTwoScore.setText(String.format("Computer: %d", newValue.intValue()));
        });
        playerTwo.pointsProperty().addListener((observable, oldValue, newValue) -> {
            playerTwoPoints.setText(String.format("Computer: %d", newValue.intValue()));
        });
        root.getChildren().add(0, gameInfo);
    }

}
