package hu.unideb.snapszer;

import com.interactivemesh.jfx.importer.tds.TdsModelImporter;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.HungarianCardRank;
import hu.unideb.snapszer.model.HungarianCardSuit;
import hu.unideb.snapszer.view.HungarianCardView;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.AmbientLight;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class MainApp extends Application {

    private static final double MODEL_SCALE_FACTOR = 5;

    private Scene scene;
    private Group root;
    private PointLight pointLight;
    double anchorX, anchorY, anchorAngle;

    private void buildScene() {

        TdsModelImporter importer = new TdsModelImporter();
        importer.setResourceBaseUrl(getClass().getResource("/3ds"));
        importer.read(getClass().getResource("/3ds/POKER+TABLE.3ds"));
        Node[] nodes = importer.getImport();
        Node table = nodes[0];
        double x = (table.getBoundsInLocal().getMaxX() + table.getBoundsInLocal().getMinX()) / 2;
        double y = (table.getBoundsInLocal().getMaxY() + table.getBoundsInLocal().getMinY()) / 2;
        double z = (table.getBoundsInLocal().getMaxZ() + table.getBoundsInLocal().getMinZ()) / 2;
        table.getTransforms().addAll(
                new Scale(MODEL_SCALE_FACTOR, MODEL_SCALE_FACTOR, MODEL_SCALE_FACTOR),
                new Translate(-x, -y, -z)
        );
        
        y = table.getBoundsInParent().getMaxY();
        table.setTranslateY(y);
        root.getChildren().addAll(table);

        pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateY(-200);
        pointLight.getScope().addAll(table);
        root.getChildren().add(pointLight);
    }

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

        HungarianCardView cardView
                = new HungarianCardView(new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        cardView.getTransforms().addAll(
                new Rotate(-90, Rotate.X_AXIS),
                new Rotate(180, Rotate.Y_AXIS),
                new Rotate(0, Rotate.Z_AXIS),
                new Scale(0.3, 0.3, 0.3)
        );
        cardView.setTranslateY(-3);
        cardView.setOnMouseEntered((MouseEvent event) -> {
            scene.setCursor(Cursor.HAND);
        });
        cardView.setOnMouseExited((MouseEvent event) -> {
            scene.setCursor(Cursor.DEFAULT);
        });
        cardView.setRotationAxis(Rotate.Y_AXIS);
        cardView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                anchorAngle = cardView.getRotate();
            }
        });

        cardView.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = cardView.getTranslateX();
                double y = cardView.getTranslateY();
                double z = cardView.getTranslateZ();
                cardView.getTransforms().addAll(
                        new Translate(
                                0 - x,
                                0 - y,
                                0 - z)
                );
                cardView.setRotate(anchorAngle + anchorX - event.getSceneX());
                cardView.getTransforms().addAll(
                        new Translate(
                                x,
                                y,
                                z)
                );
            }
        });

        AmbientLight light = new AmbientLight(Color.WHITE);
        light.getScope().add(cardView);
        root.getChildren().add(light);

        buildScene();
        root.getChildren().add(cardView);
        scene = new Scene(root, 0, 0, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.rgb(10, 10, 40));
        scene.setOnKeyPressed((event) -> {
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
    }

    public static void main(String[] args) {
        launch(args);
    }

}
