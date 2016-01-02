/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.view;

import hu.unideb.snapszer.model.HungarianCard;
import java.util.Objects;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

/**
 *
 * @author Fecó
 */
public class HungarianCardView extends MeshView {

    private static final Image backImage
            = new Image(HungarianCardView.class.getResourceAsStream("/images/hatlap.jpg"));
    private static final int WIDTH = 112;
    private static final int HEIGHT = 186;
    private static final int DEPTH = 1;
    private TriangleMesh cardMesh;
    private final Rotate rotateX;
    private final Rotate rotateY;
    private final Rotate rotateZ;
    private final Translate translate;

    public HungarianCard getCard() {
        return card;
    }

    private final HungarianCard card;

    public HungarianCardView(HungarianCard card, Point3D startPosition) {
        this.card = card;
        initCardMesh();
        setMesh(cardMesh);
        initMaterial();

        rotateX = new Rotate(90, Rotate.X_AXIS);
        rotateY = new Rotate(0, Rotate.Y_AXIS);
        rotateZ = new Rotate(0, Rotate.Z_AXIS);
        translate = new Translate(startPosition.getX(), startPosition.getY(), startPosition.getZ());
        getTransforms().addAll(
                translate,
                rotateX,
                rotateY,
                rotateZ,
                new Scale(0.3, 0.3, 0.3)
        );
    }

    public Timeline setTrump(double x, double y) {
        Timeline tl = new Timeline();
        KeyValue rz = new KeyValue(rotateZ.angleProperty(), 90);
        KeyValue rx = new KeyValue(rotateX.angleProperty(), -90);
        KeyValue tx = new KeyValue(translate.xProperty(), x);
        KeyValue ty = new KeyValue(translate.yProperty(), y);
        KeyValue tz = new KeyValue(translate.zProperty(), 0);
        KeyFrame kf = new KeyFrame(Duration.millis(600), tx, ty, tz);
        KeyFrame kf2 = new KeyFrame(Duration.millis(1000), rx, rz);
        tl.getKeyFrames().addAll(kf, kf2);
        return tl;
    }

    public Timeline beatCard(double y) {
        Timeline tl = new Timeline();
        KeyValue rz = new KeyValue(rotateZ.angleProperty(), 0);
        KeyValue ry = new KeyValue(rotateY.angleProperty(), 0);
        KeyValue rx = new KeyValue(rotateX.angleProperty(), 90);
        KeyValue tx = new KeyValue(translate.xProperty(), -80);
        KeyValue ty = new KeyValue(translate.yProperty(), y);
        KeyFrame kf = new KeyFrame(Duration.millis(1000), tx, ty);
        KeyFrame kf2 = new KeyFrame(Duration.millis(700), rx, ry, rz);
        tl.getKeyFrames().addAll(kf, kf2);
        return tl;
    }

    public Timeline moveCard(double x) {
        Timeline tl = new Timeline();
        KeyValue tx = new KeyValue(translate.xProperty(), x);
        KeyFrame kf = new KeyFrame(Duration.millis(60), tx);
        tl.getKeyFrames().add(kf);
        return tl;
    }

    public Timeline drawCard(double x, double y, double z, double rX) {
        Timeline tl = new Timeline();
        KeyValue rz = new KeyValue(rotateZ.angleProperty(), 0);
        KeyValue rx = new KeyValue(rotateX.angleProperty(), rX);
        KeyValue tx = new KeyValue(translate.xProperty(), x);
        KeyValue ty = new KeyValue(translate.yProperty(), y);
        KeyValue tz = new KeyValue(translate.zProperty(), z);
        KeyFrame kf = new KeyFrame(Duration.millis(60), ty, tz, tx);
        KeyFrame kf2 = new KeyFrame(Duration.millis(100), rz, rx);
        tl.getKeyFrames().addAll(kf, kf2);
        return tl;
    }

    public Timeline callCard(int angleX, int angleZ) {
        Timeline tl = new Timeline();
        KeyValue kv = new KeyValue(rotateX.angleProperty(), angleX);
        KeyValue kv2 = new KeyValue(rotateZ.angleProperty(), angleZ);
        KeyValue kv3 = new KeyValue(translate.xProperty(), 0);
        KeyValue kv4 = new KeyValue(translate.yProperty(), -4);
        KeyValue kv5 = new KeyValue(translate.zProperty(), 0);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv, kv2, kv3, kv4, kv5);
        tl.getKeyFrames().add(kf);
        return tl;
    }

    private void initCardMesh() {
        cardMesh = new TriangleMesh();
        float hw = WIDTH / 2f;
        float hh = HEIGHT / 2f;
        float hd = DEPTH / 2f;

        float points[] = {
            hw, hh, hd,
            hw, hh, -hd,
            hw, -hh, hd,
            hw, -hh, -hd,
            -hw, hh, hd,
            -hw, hh, -hd,
            -hw, -hh, hd,
            -hw, -hh, -hd
        };

        float texCoords[] = {
            0.0f, 0.0f,
            0.5f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            0.5f, 1.0f,
            1.0f, 1.0f
        };

        int faces[] = {
            0, 0, 2, 0, 1, 0,
            2, 0, 3, 0, 1, 0,
            4, 0, 5, 0, 6, 0,
            6, 0, 5, 0, 7, 0,
            0, 0, 1, 0, 4, 0,
            4, 0, 1, 0, 5, 0,
            2, 0, 6, 0, 3, 0,
            3, 0, 6, 0, 7, 0,
            0, 4, 4, 5, 2, 1,
            2, 1, 4, 5, 6, 2,
            1, 4, 3, 1, 5, 3,
            5, 3, 3, 1, 7, 0};

        cardMesh.getPoints().setAll(points);
        cardMesh.getTexCoords().setAll(texCoords);
        cardMesh.getFaces().setAll(faces);
    }

    private void initMaterial() {
        Image frontImage
                = new Image(
                        this.getClass().getResourceAsStream("/images/" + card + ".jpg"));
        WritableImage cardImage = new WritableImage(
                (int) (frontImage.getWidth() + backImage.getWidth()),
                (int) frontImage.getHeight());
        PixelWriter pixelWriter = cardImage.getPixelWriter();

        PixelReader pixelReader = frontImage.getPixelReader();
        pixelWriter.setPixels(0, 0, WIDTH, HEIGHT, pixelReader, 0, 0);
        pixelReader = backImage.getPixelReader();
        pixelWriter.setPixels(WIDTH, 0, WIDTH, HEIGHT, pixelReader, 0, 0);

        PhongMaterial cardMaterial = new PhongMaterial();
        cardMaterial.setDiffuseMap(cardImage);
        setMaterial(cardMaterial);
    }

    @Override
    public boolean equals(Object obj) {
        return card.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.card);
    }

}
