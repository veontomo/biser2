package com.veontomo.biser2.api;

/**
 * Class to describe a bead
 *
 */
public class Bead {
    public final String colorCode;
    public final Location loc;

    public Bead(String colorCode, Location loc){
        this.colorCode = colorCode;
        this.loc = loc;
    }
}
