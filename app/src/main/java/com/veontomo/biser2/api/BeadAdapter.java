package com.veontomo.biser2.api;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Adapter to display information about beads
 *
 */
public class BeadAdapter extends BaseAdapter {
    /**
     * Array of bead color codes
     */
    private List<String> items;

    /**
     * Number of different layouts
     */
    private final short NUM_OF_TYPES = 2;

    /**
     * Identifier of the first layout
     */
    private final short TYPE_PRESENT = 0;

    /**
     * Identifier of the second layout
     */
    private final short TYPE_ABSENT = 1;


    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
