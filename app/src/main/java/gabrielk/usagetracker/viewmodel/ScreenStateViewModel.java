package gabrielk.usagetracker.viewmodel;

import android.content.Context;
import android.database.ContentObserver;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gabrielk.usagetracker.BR;
import gabrielk.usagetracker.ScreenStateListAdapter;
import gabrielk.usagetracker.ScreenStateType;
import gabrielk.usagetracker.TrackerDateUtils;
import gabrielk.usagetracker.db.ScreenStateContract;
import gabrielk.usagetracker.model.ScreenState;

/**
 * Created by GabrielK on 13-Mar-18.
 */

// todo: use in recycler view
public class ScreenStateViewModel extends BaseObservable {
    public static final String[] SCREEN_STATE_PROJECTION = {
            ScreenStateContract.ScreenStateEntry.COLUMN_STATE,
            ScreenStateContract.ScreenStateEntry.COLUMN_DATE,
    };

    private Context context;

    private List<ScreenState> data;
    private ScreenStateListAdapter adapter;

    public ScreenStateViewModel(Context context) {
        this.context = context;

        data = new ArrayList<>();

        data.add(new ScreenState(ScreenStateType.ON, TrackerDateUtils.getNormalizedTimeUtc(), 1));
        data.add(new ScreenState(ScreenStateType.ON, TrackerDateUtils.getNormalizedTimeUtc(), 2));

        adapter = new ScreenStateListAdapter(context);

        final Uri screenStateQueryUri = ScreenStateContract.ScreenStateEntry.CONTENT_URI;
        final String sortOrder = ScreenStateContract.ScreenStateEntry.COLUMN_DATE + " DESC";
        final String selection = ScreenStateContract.ScreenStateEntry.getSqlSelectorForToday();

        adapter.swapCursor(context.getContentResolver().query(screenStateQueryUri, SCREEN_STATE_PROJECTION, selection, null, sortOrder));

        // todo: wtf, there must be a better way...at least it's not in the view
        context.getContentResolver().registerContentObserver(screenStateQueryUri, false, new ContentObserver(null) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Log.e("NSTL", "Changed");
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.swapCursor(ScreenStateViewModel.this.context.getContentResolver().query(screenStateQueryUri, SCREEN_STATE_PROJECTION, selection, null, sortOrder));
                    }
                });
            }
        });
    }

    @Bindable
    public List<ScreenState> getData() {
        return data;
    }
    public void setData(List<ScreenState> data) {
        this.data = data;
        notifyPropertyChanged(BR.data);
    }

    @Bindable
    public ScreenStateListAdapter getAdapter() { return adapter; }
    public void setAdapter(ScreenStateListAdapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }
}
