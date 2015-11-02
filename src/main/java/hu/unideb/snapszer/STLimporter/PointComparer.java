/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.STLimporter;

import java.util.Comparator;
import javafx.geometry.Point3D;

/**
 *
 * @author Fecó
 */
public class PointComparer implements Comparator<Point3D> {

    @Override
    public int compare(Point3D o1, Point3D o2) {
        Double temp = o1.getX();
        if (temp.compareTo(o2.getX()) == 0) {
            temp = o1.getY();
            if (temp.compareTo(o2.getY()) == 0) {
                temp = o1.getZ();
                return temp.compareTo(o2.getZ());
            }
            return temp.compareTo(o2.getY());
        }
        return temp.compareTo(o2.getX());
    }

}
