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
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import gabrielk.usagetracker.R;
import gabrielk.usagetracker.ScreenStateListAdapter;
import gabrielk.usagetracker.ScreenStateType;
import gabrielk.usagetracker.UpdateService;
import gabrielk.usagetracker.databinding.ActivityHomeBinding;
import gabrielk.usagetracker.db.ScreenStateContract;
import gabrielk.usagetracker.viewmodel.ScreenStateViewModel;

public class HomeActivity extends AppCompatActivity {// implements LoaderManager.LoaderCallbacks<Cursor> {
//    private static final String TAG = MainActivity.class.getSimpleName();
//    private static final int SCREEN_STATE_LOADER_ID = 0;
//
//    public static final String[] SCREEN_STATE_PROJECTION = {
//        ScreenStateContract.ScreenStateEntry.COLUMN_STATE,
//        ScreenStateContract.ScreenStateEntry.COLUMN_DATE,
//    };

//    private TextView screenOnCounterTextView;
//    private TextView screenUnlockCounterTextView;

//    private RecyclerView mScreenStateRecyclerView;
//    private ScreenStateListAdapter mScreenStateAdapter;
//    private Cursor mCursor;

    private ScreenStateViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);

        // todo: mozno tiez do viewmodel ... porozmyslat
        startService(new Intent(this, UpdateService.class));

//        screenOnCounterTextView = (TextView) findViewById(R.id.screenOnCounterTextView);
//        screenUnlockCounterTextView = (TextView) findViewById(R.id.screenUnlockCounterTextView);

//        mScreenStateRecyclerView = (RecyclerView) findViewById(R.id.screen_state_recycler_view);
//        mScreenStateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mScreenStateAdapter = new ScreenStateListAdapter(this);
//        mScreenStateRecyclerView.setAdapter(mScreenStateAdapter);
//        mScreenStateRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        viewModel = new ScreenStateViewModel(this);
        viewModel.setUp();
        binding.setViewModel(viewModel);

//        getSupportLoaderManager().initLoader(SCREEN_STATE_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.setUp();
    }

//    private void updateActivity(Cursor data) {
//        int onCount = 0;
//        int unlockCount = 0;
//
//        if (data != null) {
//            while (data.moveToNext()) {
//                if (data.getString(data.getColumnIndex(ScreenStateContract.ScreenStateEntry.COLUMN_STATE)).equals(String.valueOf(ScreenStateType.ON.ordinal()))) {
//                    onCount++;
//                } else {
//                    unlockCount++;
//                }
//            }
//        }
//
//        screenOnCounterTextView.setText(Integer.toString(onCount));
//        screenUnlockCounterTextView.setText(Integer.toString(unlockCount));
//    }

    // todo: should be moved to ScreenStateViewModel
    public void resetCounters(View v) {
        getContentResolver().delete(ScreenStateContract.ScreenStateEntry.CONTENT_URI, null, null);
    }

    // todo: somehow the day maybe also needs to be selected or smth
//    @Override
//    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
//        switch (loaderId) {
//            case SCREEN_STATE_LOADER_ID:
//                Uri screenStateQueryUri = ScreenStateContract.ScreenStateEntry.CONTENT_URI;
//                String sortOrder = ScreenStateContract.ScreenStateEntry.COLUMN_DATE + " DESC";
//                String selection = ScreenStateContract.ScreenStateEntry.getSqlSelectorForToday();
//
//                return new CursorLoader(this,
//                        screenStateQueryUri,
//                        SCREEN_STATE_PROJECTION,
//                        selection,
//                        null,
//                        sortOrder);
//
//            default:
//                throw new RuntimeException("Loader Not Implemented: " + loaderId);
//        }
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        mScreenStateAdapter.swapCursor(data);
//        mCursor = data;
//        if (data == null) {
//            return;
//        }
//
//        updateActivity(data);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        mScreenStateAdapter.swapCursor(null);
//    }
}
