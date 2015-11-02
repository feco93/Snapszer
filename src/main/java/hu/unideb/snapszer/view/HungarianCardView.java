/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.view;

import hu.unideb.snapszer.model.HungarianCard;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 *
 * @author Fecó
 */
public class HungarianCardView extends MeshView {

    private static final int WIDTH = 112;
    private static final int HEIGHT = 186;
    private static final int DEPTH = 1;
    private TriangleMesh cardMesh;
    private static final Image backImage =
                new Image(HungarianCardView.class.getResourceAsStream("/images/hatlap.jpg"));

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

    public HungarianCardView(HungarianCard card) {
        initCardMesh();
        setMesh(cardMesh);
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
}
