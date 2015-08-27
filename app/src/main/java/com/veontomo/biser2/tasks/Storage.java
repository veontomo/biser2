package com.veontomo.biser2.Tasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.veontomo.biser2.api.Location;

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
    private static final String DATABASE_NAME = "Bead";

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
    public void insertLocations(List<Location> locations) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values;
        int size = locations.size();
        Location loc;
        for (int i = 0; i < size; i++) {
            values = new ContentValues();
            loc = locations.get(i);
            values.put(LocationTable.COLOR_CODE_NAME, loc.color);
            values.put(LocationTable.WING_NAME, loc.wing);
            values.put(LocationTable.ROW_NAME, loc.row);
            values.put(LocationTable.COL_NAME, loc.col);
            db.insert(LocationTable.TABLE_NAME, null, values);
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
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = ?;", new String[]{tableName});
        if (cursor == null) {
            return false;
        }
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
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
