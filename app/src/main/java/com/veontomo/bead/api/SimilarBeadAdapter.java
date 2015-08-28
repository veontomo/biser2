package com.veontomo.bead.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.veontomo.bead.R;

import java.util.List;

/**
 * An adapter to display beads
 */
public class SimilarBeadAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<String> mItems;

    public SimilarBeadAdapter(Context context, List<String> items){
        this.mContext = context;
        this.mItems = items;
    }
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (row == null) {
            row = inflater.inflate(R.layout.row_bead_with_search, parent, false);
            Holder holder = new Holder();
            holder.text = (TextView) row.findViewById(R.id.bead_similar_color_code);
            row.setTag(holder);
        }
        Holder tag = (Holder) row.getTag();
        tag.text.setText(getItem(position));
        return row;

    }

    /**
     * Prepends list to existing {@link #mItems items}
     */
    public void prepend(List<String> items) {
        this.mItems.addAll(0, items);
        notifyDataSetChanged();
    }

    public void setItems(List<String> list) {
        this.mItems.clear();
        this.mItems.addAll(0, list);
        notifyDataSetChanged();
    }

    /**
     * class that implements view holder pattern
     */
    static class Holder{
        public TextView text;
    }
}
