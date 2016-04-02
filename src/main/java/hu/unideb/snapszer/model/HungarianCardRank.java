/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.snapszer.model;

/**
 * The representation of the Hungarian card's number property.
 *
 * @author Feco
 */
public enum HungarianCardRank implements IRank {

    /**
     * The Alsó rank.
     */
    ALSO(2),
    /**
     * The Felső rank.
     */
    FELSO(3),
    /**
     * The Király rank.
     */
    KIRALY(4),
    /**
     * The VII rank.
     */
    HET(7),
    /**
     * The VIII rank.
     */
    NYOLC(8),
    /**
     * The IX rank.
     */
    KILENC(9),
    /**
     * The X rank.
     */
    TIZ(10),
    /**
     * The Ász rank.
     */
    ASZ(11);

    private final int value;

    HungarianCardRank(int point) {
        this.value = point;
    }

    @Override
    public int getValue() {
        return value;
    }

}
