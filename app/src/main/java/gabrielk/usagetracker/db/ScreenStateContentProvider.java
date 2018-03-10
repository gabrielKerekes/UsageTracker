package gabrielk.usagetracker.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import static gabrielk.usagetracker.db.ScreenStateContract.ScreenStateEntry.*;

/**
 * Created by GabrielK on 10-Feb-18.
 */

public class ScreenStateContentProvider extends ContentProvider {
    public static final int SCREEN_STATE = 100;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private ScreenStateDbHelper mScreenStateDbHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(ScreenStateContract.AUTHORITY, ScreenStateContract.PATH_SCREEN_STATE, SCREEN_STATE);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mScreenStateDbHelper = new ScreenStateDbHelper(context);
        return true;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mScreenStateDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned

        switch (match) {
            case SCREEN_STATE:
                long id = db.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(ScreenStateContract.ScreenStateEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase taskDb = mScreenStateDbHelper.getReadableDatabase();

        UriMatcher uriMatcher = sUriMatcher;
        if (uriMatcher.match(uri) != SCREEN_STATE) {
            return null;
        }

        Cursor cursor = taskDb.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase taskDb = mScreenStateDbHelper.getReadableDatabase();

        UriMatcher uriMatcher = sUriMatcher;
        if (uriMatcher.match(uri) != SCREEN_STATE) {
            return 0;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return taskDb.delete(TABLE_NAME, selection, selectionArgs);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
