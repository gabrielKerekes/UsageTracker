package gabrielk.usagetracker.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;
import java.util.List;

import gabrielk.usagetracker.BR;
import gabrielk.usagetracker.ScreenStateListAdapter;
import gabrielk.usagetracker.ScreenStateType;
import gabrielk.usagetracker.TrackerDateUtils;
import gabrielk.usagetracker.model.ScreenState;

/**
 * Created by GabrielK on 13-Mar-18.
 */

// todo: use in recycler view
public class ScreenStateViewModel extends BaseObservable {
    private Context context;

    private List<ScreenState> data;
    private ScreenStateListAdapter adapter;

    public ScreenStateViewModel(Context context) {
        data = new ArrayList<>();

        data.add(new ScreenState(ScreenStateType.ON, TrackerDateUtils.getNormalizedTimeUtc(), 1));
        data.add(new ScreenState(ScreenStateType.ON, TrackerDateUtils.getNormalizedTimeUtc(), 2));

        adapter = new ScreenStateListAdapter(context);
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
