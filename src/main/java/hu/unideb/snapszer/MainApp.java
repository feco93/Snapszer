package hu.unideb.snapszer;

import com.interactivemesh.jfx.importer.tds.TdsModelImporter;
import hu.unideb.snapszer.model.HungarianCard;
import hu.unideb.snapszer.model.HungarianCardRank;
import hu.unideb.snapszer.model.HungarianCardSuit;
import hu.unideb.snapszer.view.HungarianCardView;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.AmbientLight;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

public class MainApp extends Application {

    private static final double MODEL_SCALE_FACTOR = 20;
    private static final int HEIGHT = 900;
    private static final int WIDTH = 1500;

    private Scene scene;
    private Group root;

    double anchorX, anchorY, anchorAngle;

    private void buildScene() {

        TdsModelImporter importer = new TdsModelImporter();

        importer.read(MainApp.class.getResource("/stl/table.3ds"));
        Node[] table = importer.getImport();
        table[0].setScaleX(MODEL_SCALE_FACTOR);
        table[0].setScaleY(MODEL_SCALE_FACTOR);
        table[0].setScaleZ(MODEL_SCALE_FACTOR);

        Color ambientColor = Color.rgb(255, 255, 255, 0);
        AmbientLight ambient = new AmbientLight(ambientColor);
        ambient.setTranslateX(-1 * (WIDTH / 2));
        ambient.setTranslateY(-1 * (HEIGHT / 2) + 700);
        ambient.setTranslateZ(500);

        root.getChildren().addAll(table);
        root.getChildren().addAll(ambient);
    }

    private PerspectiveCamera addCamera(Scene scene) {
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera();
        double NEAR = 0.01;
        double FAR = 1000;
        perspectiveCamera.setFarClip(FAR);
        perspectiveCamera.setNearClip(NEAR);
        perspectiveCamera.setFieldOfView(60);
        perspectiveCamera.setRotationAxis(Rotate.X_AXIS);
        perspectiveCamera.setRotate(-55);
        perspectiveCamera.setTranslateX(-1 * (WIDTH / 2));
        perspectiveCamera.setTranslateY(-1 * (HEIGHT / 2) + 550);
        perspectiveCamera.setTranslateZ(400);
        scene.setCamera(perspectiveCamera);
        return perspectiveCamera;
    }

    @Override
    public void start(Stage primaryStage) {
        root = new Group();

        HungarianCardView cardView
                = new HungarianCardView(new HungarianCard(HungarianCardRank.ALSO, HungarianCardSuit.TOK));
        cardView.getTransforms().addAll(
                new Rotate(-50, Rotate.X_AXIS),
                new Rotate(180, Rotate.Y_AXIS),
                new Rotate(0, Rotate.Z_AXIS)
        );
        cardView.setTranslateY(300);
        cardView.setTranslateZ(-250);
        cardView.setOnMouseEntered((MouseEvent event) -> {
           scene.setCursor(Cursor.HAND);
        });
        cardView.setOnMouseExited((MouseEvent event) -> {
           scene.setCursor(Cursor.DEFAULT);
        });
//        cardView.setRotationAxis(Rotate.Y_AXIS);
//        cardView.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                anchorX = event.getSceneX();
//                anchorY = event.getSceneY();
//                anchorAngle = cardView.getRotate();
//            }
//        });
//
//        cardView.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                double x = cardView.getTranslateX();
//                double y = cardView.getTranslateY();
//                double z = cardView.getTranslateZ();
//                cardView.getTransforms().addAll(
//                new Translate(
//                        0 - x,
//                        0 - y,
//                        0 - z)
//                );
//                cardView.setRotate(anchorAngle + anchorX - event.getSceneX());
//                cardView.getTransforms().addAll(
//                new Translate(
//                        x,
//                        y,
//                        z)
//                );
//            }
//        });
        buildScene();
        root.getChildren().add(cardView);
        scene = new Scene(root, WIDTH, HEIGHT, true);
        scene.setFill(Color.rgb(10, 10, 40));
        addCamera(scene);
        primaryStage.setTitle("Snapszer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
