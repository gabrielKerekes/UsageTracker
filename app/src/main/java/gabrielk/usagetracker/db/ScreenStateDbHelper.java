package gabrielk.usagetracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import gabrielk.usagetracker.db.ScreenStateContract.*;

/**
 * Created by GabrielK on 10-Feb-18.
 */

public class ScreenStateDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "screenState.db";
    private static final int DATABASE_VERSION = 1;

    public ScreenStateDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold waitlist data
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + ScreenStateEntry.TABLE_NAME + " (" +
                ScreenStateEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ScreenStateEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    // todo: alter table instead of dropping it
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ScreenStateEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
