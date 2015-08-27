package com.veontomo.biser2.api;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to display information about beads
 *
 */
public class BeadAdapter extends BaseAdapter {
    /**
     * Array of bead color codes
     */
    private List<Location> mItems;

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

    public BeadAdapter(ArrayList<Location> items){
        this.mItems = items;
    }


    @Override
    public int getCount() {
        return this.mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mItems.get(position);
    }


    /**
     * Returns type of the item at given position.
     * <p>There are two possible outputs: <br>
     * 1. item is a non-null Location  instance<br>
     * 2. item is a null Location  instance<br>
     *
     * @param index item ordinal number
     * @return int item type
     */
    @Override
    public int getItemViewType(int index) {
        IProverbAd item = this.getItem(index);
        String name = item.getName();
        if (AdName.equals(name)) {
            return TYPE_AD;
        }
        if (ProverbName.equals(name)) {
            Proverb proverb = (Proverb) item;
            if (proverb.isFavorite()) {
                return TYPE_PROVERB_FAVORITE;
            }
        }
        return TYPE_PROVERB_DEFAULT;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    static class HolderBeadPresent {
        public TextView text;
        public TextView location;

    }

    static class HolderBeadAbsent {
        public TextView text;
        public ImageView image;
    }

}
