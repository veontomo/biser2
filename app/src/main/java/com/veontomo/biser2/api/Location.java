package com.veontomo.biser2.api;

/**
 * Describes location of the bead
 */
public class Location {
    public final String wing;
    public final int row;
    public final int col;
    public final String color;

    public Location(String wing, int row, int col, String color){
        this.wing = wing;
        this.row = row;
        this.col = col;
        this.color = color;
    }

    @Override
    public String toString(){
        return this.wing + "-" + String.valueOf(this.row) + "-" + String.valueOf(this.col) + "-" + this.color;
    }


}
