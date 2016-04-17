/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.view;

import com.interactivemesh.jfx.importer.tds.TdsModelImporter;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 *
 * @author Fecó
 */
public class TableView extends Group {
    
    private static final double MODEL_SCALE_FACTOR = 5;
    private static final String directory = "/3ds";
    private static final String tableResource = "/3ds/POKER+TABLE.3ds";

    public TableView() {
        TdsModelImporter importer = new TdsModelImporter();
        importer.setResourceBaseUrl(getClass().getResource(directory));
        importer.read(getClass().getResource(tableResource));
        Node[] nodes = importer.getImport();
        importer.close();
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

        getChildren().addAll(table);
        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateY(-200);
        pointLight.getScope().addAll(table);
        getChildren().add(pointLight);
    }
}
