package gabrielk.usagetracker.view;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import gabrielk.usagetracker.R;
import gabrielk.usagetracker.ScreenStateListAdapter;
import gabrielk.usagetracker.ScreenStateType;
import gabrielk.usagetracker.TrackerDateUtils;
import gabrielk.usagetracker.UpdateService;
import gabrielk.usagetracker.databinding.ActivityHomeBinding;
import gabrielk.usagetracker.db.ScreenStateContract;
import gabrielk.usagetracker.model.ScreenState;

public class HomeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    //private static final String TAG = MainActivity.class.getSimpleName();
    private static final int SCREEN_STATE_LOADER_ID = 0;

    public static final String[] SCREEN_STATE_PROJECTION = {
        ScreenStateContract.ScreenStateEntry.COLUMN_STATE,
        ScreenStateContract.ScreenStateEntry.COLUMN_DATE,
    };

    private ScreenStateListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // todo: mozno tiez do viewmodel ... porozmyslat
        startService(new Intent(this, UpdateService.class));

        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.screenStateRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ScreenState> data = new ArrayList<>();

        data.add(new ScreenState(ScreenStateType.ON, TrackerDateUtils.getNormalizedTimeUtc(), 1));
        data.add(new ScreenState(ScreenStateType.ON, TrackerDateUtils.getNormalizedTimeUtc(), 2));

        adapter = new ScreenStateListAdapter(this/*, data*/);
        binding.screenStateRecyclerView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(SCREEN_STATE_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // todo: should be moved to ScreenStateViewModel
    public void resetCounters(View v) {
        getContentResolver().delete(ScreenStateContract.ScreenStateEntry.CONTENT_URI, null, null);
    }

    // todo: somehow the day maybe also needs to be selected or smth
    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        switch (loaderId) {
            case SCREEN_STATE_LOADER_ID:
                Uri screenStateQueryUri = ScreenStateContract.ScreenStateEntry.CONTENT_URI;
                String sortOrder = ScreenStateContract.ScreenStateEntry.COLUMN_DATE + " DESC";
                String selection = ScreenStateContract.ScreenStateEntry.getSqlSelectorForToday();

                return new CursorLoader(this,
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

//        updateActivity(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
