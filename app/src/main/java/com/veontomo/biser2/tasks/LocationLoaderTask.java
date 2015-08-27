package com.veontomo.biser2.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.veontomo.biser2.Config;
import com.veontomo.biser2.api.Bead;
import com.veontomo.biser2.api.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author veontomo@gmail.com
 */
public class LocationLoaderTask extends AsyncTask<String, Void, Void> {
    /**
     * name of the file that contains beads of the beads
     */
    private final Context mContext;
    private final Storage mStorage;

    /**
     * Current wing of the bead stand
     */
    private String mCurrentWing;

    /**
     * Current row of the bead stand
     */
    private int mCurrentRow = 1;

    private ArrayList<Bead> beads = new ArrayList<>();

    public LocationLoaderTask(Context context) {
        this.mContext = context;
        this.mStorage = new Storage(context);
    }

    @Override
    protected Void doInBackground(String... filenames) {
        if (!this.mStorage.tableExists(Storage.LocationTable.TABLE_NAME)) {
            Log.i(Config.TAG, "table  NOT exists");
            int size = filenames.length;
            for (int i = 0; i < size; i++) {
                load(filenames[i]);
            }
            this.mStorage.saveBeads(this.beads);
        } else {
            Log.i(Config.TAG, "table exists");
        }
        return null;
    }

    /**
     * Reads a file with given name line by line and uses newly read line to update {@link #beads}
     * list.
     *
     * @param filename file name from the assets folder
     */
    private void load(@NonNull String filename) {
        InputStream stream;
        try {
            stream = this.mContext.getAssets().open(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                updateLocations(line);

            }
        } catch (IOException e) {
            Log.e(Config.TAG, "exception in getting a line");
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            Log.e(Config.TAG, "exception in closing a buffered reader");
            e.printStackTrace();
        }


    }

    /**
     * Updates {@link #beads beads} by information stored in given string.
     * The method uses {@link #mCurrentWing mCurrentWing} and {@link #mCurrentRow} as
     * external parameters that store correspondingly the name of wing and row index which
     * given string refers to.
     * <br>
     * If the argument is a single-word string, then this word is treated as a new wing name.
     * Otherwise it is considered as a content of a new line.
     */
    private void updateLocations(@NonNull String line) {
        if (line.isEmpty()) {
            return;
        }
        String[] codes = line.split("\\s+");
        int codeLen = codes.length;
        Bead bead;
        if (codeLen == 1) {
            // this is a new wing
            this.mCurrentWing = codes[0];
            this.mCurrentRow = 1;
            return;
        }
        for (int i = 0; i < codeLen; i++) {
            bead = new Bead(codes[i], new Location(mCurrentWing, this.mCurrentRow, i));
            this.beads.add(bead);
            this.mCurrentRow++;
        }
    }
}
