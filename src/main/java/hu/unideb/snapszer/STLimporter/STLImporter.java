/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.STLimporter;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point3D;
import javafx.scene.shape.Mesh;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;
import org.apache.commons.io.EndianUtils;

/**
 *
 * @author Fecó
 */
public class STLImporter {

    private static final PointComparer comparer = new PointComparer();

    public Mesh importMesh(InputStream inStream) throws FileNotFoundException {
        TriangleMesh result = null;
        try {
            DataInputStream fStream = new DataInputStream(inStream);
            result = new TriangleMesh(VertexFormat.POINT_TEXCOORD);
            fStream.skipBytes(80);
            int numberOfFacets = EndianUtils.readSwappedInteger(fStream);
            TreeSet<Point3D> pointsSet = new TreeSet<>(comparer);
            ArrayList<Face> faces = new ArrayList<>(numberOfFacets);
            for (int i = 0; i < numberOfFacets; i++) {
                fStream.skipBytes(12);
                Point3D[] vertexes = new Point3D[3];
                for (int j = 0; j < 3; j++) {
                    Point3D vertex = new Point3D(
                            EndianUtils.readSwappedFloat(fStream),
                            EndianUtils.readSwappedFloat(fStream),
                            EndianUtils.readSwappedFloat(fStream));
                    vertexes[j] = vertex;
                    pointsSet.add(vertex);
                }
                Face face = new Face(vertexes);
                faces.add(face);
                fStream.skipBytes(2);
            }
            Point3D[] pointArray = new Point3D[pointsSet.size()];
            pointsSet.toArray(pointArray);
            float[] points = new float[pointArray.length * 3];
            for (int i = 0, j = 0; i < pointArray.length; ++i) {
                points[j++] = (float) pointArray[i].getX();
                points[j++] = (float) pointArray[i].getY();
                points[j++] = (float) pointArray[i].getZ();
            }
            result.getPoints().addAll(points);
            result.getTexCoords().addAll(0, 0);
            for (Face face : faces) {
                int a = Arrays.binarySearch(pointArray, face.getA(), comparer);
                int b = Arrays.binarySearch(pointArray, face.getB(), comparer);
                int c = Arrays.binarySearch(pointArray, face.getC(), comparer);
                result.getFaces().addAll(a, 0, b, 0, c, 0);
            }

        } catch (IOException ex) {
            Logger.getLogger(STLImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
