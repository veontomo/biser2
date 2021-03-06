package com.veontomo.bead.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.veontomo.bead.Storage;
import com.veontomo.bead.api.Bead;
import com.veontomo.bead.api.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Loads bead tree into database
 */
public class BeadLoaderTask extends AsyncTask<String, Void, Void> {
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

    private final ArrayList<Bead> beads = new ArrayList<>();

    public BeadLoaderTask(Context context) {
        // Log.i(Config.TAG, "Loading beads");
        this.mContext = context;
        this.mStorage = new Storage(context);
    }

    @Override
    protected Void doInBackground(String... filenames) {
        if (!this.mStorage.beadTableExists()) {
            for (String filename : filenames) {
                load(filename);
            }
            this.mStorage.saveBeads(this.beads);
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
            //Log.e(Config.TAG, "exception in getting a line");
            //e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
//            Log.e(Config.TAG, "exception in closing a buffered reader");
//            e.printStackTrace();
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
        //    Log.i(Config.TAG, "Loading line " + line);
        if (line.isEmpty()) {
            return;
        }
        /*
      A string used to separate color codes in the file describing
      location of the beads on the stand
     */
        final String SEPARATOR = ",";
        String[] codes = line.split(SEPARATOR);
        int codeLen = codes.length;
        Bead bead;
        if (codeLen == 1) {
            // this is a new wing
            this.mCurrentWing = codes[0].trim();
            this.mCurrentRow = 1;
        } else {
            for (int i = 0; i < codeLen; i++) {
                bead = new Bead(codes[i].trim(), new Location(mCurrentWing, this.mCurrentRow, i + 1));
                this.beads.add(bead);
            }
            this.mCurrentRow++;
        }
    }
}
