/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.view;

import hu.unideb.snapszer.model.HungarianCard;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
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
    private HungarianCard card;
    
    public HungarianCardView(HungarianCard card, Scene scene) {
        this.card = card;
        initCardMesh();
        setMesh(cardMesh);
        initMaterial();
        
        Rotate rx = new Rotate(0, Rotate.X_AXIS);
        Rotate ry = new Rotate(0, Rotate.Y_AXIS);
        Rotate rz = new Rotate(0, Rotate.Z_AXIS);
        Translate t = new Translate(0, -100, -150);
        getTransforms().addAll(
                t,
                rx,
                ry,
                rz,
                new Scale(0.3, 0.3, 0.3)
        );
        setOnMouseEntered((MouseEvent event) -> {
            scene.setCursor(Cursor.HAND);
        });
        setOnMouseExited((MouseEvent event) -> {
            scene.setCursor(Cursor.DEFAULT);
        });
        setOnMousePressed((MouseEvent event) -> {
            Timeline tl = new Timeline();
            KeyValue kv = new KeyValue(rx.angleProperty(), -90);
            KeyValue kv2 = new KeyValue(t.yProperty(), -3);
            KeyValue kv3 = new KeyValue(t.zProperty(), 0);
            KeyFrame kf = new KeyFrame(Duration.seconds(2), kv, kv2, kv3);
            tl.getKeyFrames().add(kf);
            tl.play();
        });
        
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
            0, 4, 4, 3, 2, 1,
            2, 1, 4, 3, 6, 0,
            1, 4, 3, 1, 5, 5,
            5, 5, 3, 1, 7, 2};

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

        PixelReader pixelReader = backImage.getPixelReader();
        pixelWriter.setPixels(0, 0, WIDTH, HEIGHT, pixelReader, 0, 0);
        pixelReader = frontImage.getPixelReader();
        pixelWriter.setPixels(WIDTH, 0, WIDTH, HEIGHT, pixelReader, 0, 0);

        PhongMaterial cardMaterial = new PhongMaterial();
        cardMaterial.setDiffuseMap(cardImage);
        setMaterial(cardMaterial);
    }
}
