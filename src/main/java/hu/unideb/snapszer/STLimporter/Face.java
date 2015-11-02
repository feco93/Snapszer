/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.STLimporter;

import javafx.geometry.Point3D;

/**
 *
 * @author Fecó
 */
class Face {

    private final Point3D normal;
    private final Point3D a;
    private final Point3D b;
    private final Point3D c;

    public Face(Point3D a, Point3D b, Point3D c) {
        this(new Point3D(0, 0, 0), a, b, c);
    }

    public Face(Point3D normal, Point3D a, Point3D b, Point3D c) {
        this.normal = normal;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Face(Point3D normal, Point3D[] vertexes) {
        this(normal, vertexes[0], vertexes[1], vertexes[2]);
    }

    public Face(Point3D[] vertexes) {
        this(vertexes[0], vertexes[1], vertexes[2]);
    }

    public Point3D getNormal() {
        return normal;
    }

    public Point3D getA() {
        return a;
    }

    public Point3D getB() {
        return b;
    }

    public Point3D getC() {
        return c;
    }

}
