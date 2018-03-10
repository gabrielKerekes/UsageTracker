package gabrielk.usagetracker.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import gabrielk.usagetracker.TrackerDateUtils;
import gabrielk.usagetracker.db.ScreenStateContract.*;
import gabrielk.usagetracker.model.ScreenState;

/**
 * Created by GabrielK on 10-Feb-18.
 */

public class ScreenStateDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "screenState.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    public ScreenStateDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold waitlist data
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + ScreenStateEntry.TABLE_NAME + " (" +
                ScreenStateEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ScreenStateEntry.COLUMN_STATE + " INT NOT NULL, " +
                ScreenStateEntry.COLUMN_DATE + " INT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    // todo: alter table instead of dropping it
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ScreenStateEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    // todo: refactor - method CreateContentValues from screen state should be created and put somewhere
    public void insert(ScreenState screenState) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ScreenStateContract.ScreenStateEntry.COLUMN_STATE, screenState.getStateTypeAsInt());
        contentValues.put(ScreenStateContract.ScreenStateEntry.COLUMN_DATE, TrackerDateUtils.getNormalizedTimeUtc());

        context.getContentResolver().insert(ScreenStateContract.ScreenStateEntry.CONTENT_URI, contentValues);
    }

    public void deleteAll() {
        context.getContentResolver().delete(ScreenStateContract.ScreenStateEntry.CONTENT_URI, null, null);
    }
}
