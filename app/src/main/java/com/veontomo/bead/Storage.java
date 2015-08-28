package com.veontomo.bead;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.veontomo.bead.api.Bead;
import com.veontomo.bead.api.Location;

import java.util.List;

/**
 * Reads and write data into db
 *
 * @since 0.1
 */
public class Storage extends SQLiteOpenHelper {
    /**
     * current version of the database
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Name of database that contains tables of the application
     */
    private static final String DATABASE_NAME = "BeadDB";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LocationTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Constructor
     *
     * @param context application context
     * @since 0.1
     */
    public Storage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Inserts into db given list of locations.
     */
    public void saveBeads(List<Bead> beads) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values;
        int size = beads.size();
        Location loc;
        Bead bead;
        for (int i = 0; i < size; i++) {
            values = new ContentValues();
            bead = beads.get(i);
            loc = bead.loc;
            if (loc != null) {
                values.put(LocationTable.COLOR_CODE_NAME, bead.colorCode);
                values.put(LocationTable.WING_NAME, loc.wing);
                values.put(LocationTable.ROW_NAME, loc.row);
                values.put(LocationTable.COL_NAME, loc.col);
                db.insert(LocationTable.TABLE_NAME, null, values);
            }
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    /**
     * Checks whether given table is present in the database.
     *
     * @since 0.1
     */
    public boolean tableExists(final String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        if (cursor == null) {
            return false;
        }
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

    /**
     * Returns a Bead instance corresponding to given color code.
     *
     * @param code color code
     */
    public Bead beadByColor(String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        Location loc = null;
        Cursor cursor =
                db.query(LocationTable.TABLE_NAME,
                        new String[]{LocationTable.WING_NAME, LocationTable.COL_NAME, LocationTable.ROW_NAME},
                        LocationTable.COLOR_CODE_NAME + " = ?",
                        new String[]{String.valueOf(code)},
                        null,
                        null,
                        null,
                        null);

        if (cursor.moveToFirst()) {
            String wing = cursor.getString(cursor.getColumnIndex(LocationTable.WING_NAME));
            int row = cursor.getInt(cursor.getColumnIndex(LocationTable.ROW_NAME));
            int col = cursor.getInt(cursor.getColumnIndex(LocationTable.COL_NAME));
            loc = new Location(wing, row, col);
        }
        Bead bead = new Bead(code, loc);
        cursor.close();
        db.close();
        return bead;
    }

    /**
     * Returns array of color codes of all beads.
     *
     * @return array of strings
     */

    public String[] getColorCodes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(LocationTable.TABLE_NAME,
                        new String[]{LocationTable.COLOR_CODE_NAME},
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        int size = cursor.getCount(),
                pointer = 0,
                index = cursor.getColumnIndex(LocationTable.COLOR_CODE_NAME);

        String[] codes = new String[size];
        while (cursor.moveToNext()) {
            codes[pointer] = cursor.getString(index);
            pointer++;
        }
        cursor.close();
        db.close();
        return codes;
    }

    public abstract class LocationTable implements BaseColumns {
        public static final String TABLE_NAME = "LocationTable";
        public static final String COLOR_CODE_NAME = "code";
        public static final String WING_NAME = "wing";
        public static final String ROW_NAME = "row";
        public static final String COL_NAME = "col";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", " + COLOR_CODE_NAME + " TEXT NOT NULL UNIQUE" +
                ", " + WING_NAME + " TEXT NOT NULL" +
                ", " + ROW_NAME + " TEXT NOT NULL" +
                ", " + COL_NAME + " TEXT NOT NULL" +
                ", UNIQUE (" + WING_NAME + ", " + ROW_NAME + ", " + COL_NAME + ")" +
                ")";

    }


}
