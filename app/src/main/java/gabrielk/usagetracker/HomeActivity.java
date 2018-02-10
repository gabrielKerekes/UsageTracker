package gabrielk.usagetracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import gabrielk.usagetracker.db.ScreenStateContract;

public class HomeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int SCREEN_STATE_LOADER_ID = 0;

    private TextView screenOnCounterTextView;
    private TextView screenUnlockCounterTextView;

    private RecyclerView mScreenStateRecyclerView;
    private ScreenStateListAdapter mScreenStateAdapter;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        startService(new Intent(this, UpdateService.class));

        screenOnCounterTextView = (TextView) findViewById(R.id.screenOnCounterTextView);
        screenUnlockCounterTextView = (TextView) findViewById(R.id.screenUnlockCounterTextView);

        mScreenStateRecyclerView = (RecyclerView) findViewById(R.id.screen_state_recycler_view);
        mScreenStateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mScreenStateAdapter = new ScreenStateListAdapter(this);
        mScreenStateRecyclerView.setAdapter(mScreenStateAdapter);

        updateActivity();

        getSupportLoaderManager().initLoader(SCREEN_STATE_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateActivity();
        getSupportLoaderManager().restartLoader(SCREEN_STATE_LOADER_ID, null, this);
    }

    private void updateActivity() {
        screenOnCounterTextView.setText(Integer.toString(ScreenStateCounter.GetCounter(this, ScreenStateCounter.SCREEN_ON_COUNTER)));
        screenUnlockCounterTextView.setText(Integer.toString(ScreenStateCounter.GetCounter(this, ScreenStateCounter.SCREEN_UNLOCK_COUNTER)));
    }

    public void resetCounters(View v) {
        ScreenStateCounter.ResetCounters(this);
        updateActivity();
        ScreenReceiver.UpdateNotification(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {
        return new AsyncTaskLoader<Cursor>(this) {
            Cursor mTaskData = null;

            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    deliverResult(mTaskData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                // todo: refactor
                try {
                    return getContentResolver().query(ScreenStateContract.ScreenStateEntry.CONTENT_URI,
                            new String[] { ScreenStateContract.ScreenStateEntry._ID, ScreenStateContract.ScreenStateEntry.COLUMN_TIMESTAMP} ,
                            null, null, ScreenStateContract.ScreenStateEntry.COLUMN_TIMESTAMP);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mScreenStateAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mScreenStateAdapter.swapCursor(null);
    }
}
