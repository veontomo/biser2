package com.veontomo.bead.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.veontomo.bead.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to display information about beads
 *
 */
public final class BeadAdapter extends BaseAdapter {
    private static final int LAYOUT_BEAD_PRESENT = R.layout.row_bead_present;
    private static final int LAYOUT_BEAD_ABSENT = R.layout.row_bead_absent;
    private final Context mContext;
    /**
     * Array of beads to display
     */
    private final List<Bead> mItems;

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

    public BeadAdapter(Context context, ArrayList<Bead> items){
        this.mContext = context;
        this.mItems = items;
    }


    @Override
    public int getCount() {
        return this.mItems.size();
    }

    @Override
    public Bead getItem(int position) {
        return this.mItems.get(position);
    }

    @Override
    public int getViewTypeCount() {
        return NUM_OF_TYPES;
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
        return getItem(index).loc == null ? TYPE_ABSENT : TYPE_PRESENT;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int type = getItemViewType(position);
        Bead item = this.getItem(position);

        switch (type) {
            case TYPE_PRESENT:
                if (row == null) {
                    row = inflater.inflate(LAYOUT_BEAD_PRESENT, parent, false);
                    HolderBeadPresent holder = new HolderBeadPresent();
                    holder.text = (TextView) row.findViewById(R.id.bead_present_color_code);
                    holder.location = (TextView) row.findViewById(R.id.bead_present_location);
                    row.setTag(holder);
                }
                this.inflateBeadPresent((HolderBeadPresent) row.getTag(), item);
                break;
            case TYPE_ABSENT:
                if (row == null) {
                    row = inflater.inflate(LAYOUT_BEAD_ABSENT, parent, false);
                    HolderBeadAbsent holder = new HolderBeadAbsent();
                    holder.text = (TextView) row.findViewById(R.id.bead_absent_color_code);
                    row.setTag(holder);
                }
                this.inflateBeadAbsent((HolderBeadAbsent) row.getTag(), item);
                break;
            default:
                row = null;
        }
        return row;

    }

    /**
     * Set up views stored in given tag using information stored in the Bead instance
     * @param tag
     * @param bead
     */
    private void inflateBeadPresent(HolderBeadPresent tag, Bead bead) {
        tag.text.setText(bead.colorCode);
        tag.location.setText(bead.loc.toString());
    }

    /**
     * Set up views stored in given tag using information stored in the Bead instance
     * @param tag
     * @param bead
     */
    private void inflateBeadAbsent(HolderBeadAbsent tag, Bead bead) {
        tag.text.setText(bead.colorCode);

    }

    /**
     * Inserts items at the beginning of {@link #mItems list of existing items}.
     */
    public void prependItems(List<Bead> beads) {
        this.mItems.clear();
        this.mItems.addAll(0, beads);
        notifyDataSetChanged();
    }

    /**
     * Returns color codes stored in {@link #mItems adapter items}.
     * @return
     */
    public ArrayList<String> colorCodes() {
        ArrayList<String> result = new ArrayList<>();
        for (Bead item: this.mItems){
            result.add(item.colorCode);
        }
        return result;
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
