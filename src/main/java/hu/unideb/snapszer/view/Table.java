/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.view;

import com.interactivemesh.jfx.importer.tds.TdsModelImporter;
import javafx.scene.Node;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

/**
 *
 * @author Fecó
 */
public class Table {
    
    private static final double MODEL_SCALE_FACTOR = 5;
    private static final String directory = "/3ds";
    private static final String tableResource = "/3ds/POKER+TABLE.3ds";
    private final Node table;

    public Table() {
        TdsModelImporter importer = new TdsModelImporter();
        importer.setResourceBaseUrl(getClass().getResource(directory));
        importer.read(getClass().getResource(tableResource));
        Node[] nodes = importer.getImport();
        table = nodes[0];
        double x = (table.getBoundsInLocal().getMaxX() + table.getBoundsInLocal().getMinX()) / 2;
        double y = (table.getBoundsInLocal().getMaxY() + table.getBoundsInLocal().getMinY()) / 2;
        double z = (table.getBoundsInLocal().getMaxZ() + table.getBoundsInLocal().getMinZ()) / 2;
        table.getTransforms().addAll(
                new Scale(MODEL_SCALE_FACTOR, MODEL_SCALE_FACTOR, MODEL_SCALE_FACTOR),
                new Translate(-x, -y, -z)
        );

        y = table.getBoundsInParent().getMaxY();
        table.setTranslateY(y);
    }

    public Node getTableView() {
        return table;
    }   
}
