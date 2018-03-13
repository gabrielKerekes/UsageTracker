package gabrielk.usagetracker.viewmodel;

import android.content.Context;
import android.database.Cursor;
import android.databinding.Bindable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.List;

import gabrielk.usagetracker.ScreenStateListAdapter;
import gabrielk.usagetracker.db.ScreenStateContract;
import gabrielk.usagetracker.model.ScreenState;

/**
 * Created by GabrielK on 11-Mar-18.
 */
// todo: skoncene pri tomto
// http://blog.iamsuleiman.com/android-architecture-components-tutorial-room-livedata-viewmodel/
public class ScreenStateViewModel implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int SCREEN_STATE_LOADER_ID = 0;

    public static final String[] SCREEN_STATE_PROJECTION = {
            ScreenStateContract.ScreenStateEntry.COLUMN_STATE,
            ScreenStateContract.ScreenStateEntry.COLUMN_DATE,
    };

    private Context context;
    private ScreenStateListAdapter adapter;
    private List<ScreenState> data;

    public ScreenStateViewModel(Context context) {
        this.context = context;
        adapter = new ScreenStateListAdapter(context);
        data = new ArrayList<>();
    }

    public void setUp() {
    }

    public void tearDown() {

    }

    @Bindable
    public List<ScreenState> getData() {
        return data;
    }

    @Bindable
    public ScreenStateListAdapter getAdapter() {
        return adapter;
    }

    // todo: somehow the day maybe also needs to be selected or smth
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        switch (loaderId) {
            case SCREEN_STATE_LOADER_ID:
                Uri screenStateQueryUri = ScreenStateContract.ScreenStateEntry.CONTENT_URI;
                String sortOrder = ScreenStateContract.ScreenStateEntry.COLUMN_DATE + " DESC";
                String selection = ScreenStateContract.ScreenStateEntry.getSqlSelectorForToday();

                return new CursorLoader(context,
                        screenStateQueryUri,
                        SCREEN_STATE_PROJECTION,
                        selection,
                        null,
                        sortOrder);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        if (data == null) {
            return;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
