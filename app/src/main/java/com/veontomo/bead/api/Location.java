package com.veontomo.bead.api;

import android.support.annotation.NonNull;

/**
 * Describes loc of the bead
 */
public class Location {
    private final static String SEPARATOR = "-";
    public final String wing;
    public final int row;
    public final int col;

    public Location(String wing, int row, int col) {
        this.wing = wing;
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return this.wing + SEPARATOR + String.valueOf(this.col) + SEPARATOR + String.valueOf(this.row);
    }


    /**
     * Creates a Location instance from a string.
     * <p>If the input string does not contain all info required to construct an instance, a null
     * is returned.</p>
     * <p>Inverse to {@link #toString()} method</p>
     *
     * @param str a string of the form that {@link #toString()}  produces
     * @return a Location instance or null
     */
    public static Location fromString(@NonNull String str) {
        String[] blocks = str.split(SEPARATOR);
        if (blocks.length == 3) {
            return new Location(blocks[0], Integer.valueOf(blocks[2]), Integer.valueOf(blocks[1]));
        }
        return null;
    }
}
