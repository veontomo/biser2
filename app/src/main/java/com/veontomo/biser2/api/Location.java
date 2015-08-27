package com.veontomo.biser2.api;

/**
 * Describes loc of the bead
 */
public class Location {
    public final String wing;
    public final int row;
    public final int col;

    public Location(String wing, int row, int col){
        this.wing = wing;
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString(){
        return this.wing + "-" + String.valueOf(this.row) + "-" + String.valueOf(this.col);
    }


}
