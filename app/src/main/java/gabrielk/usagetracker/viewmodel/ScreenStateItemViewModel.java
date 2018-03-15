package gabrielk.usagetracker.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.format.DateUtils;

import java.util.Date;

import gabrielk.usagetracker.BR;
import gabrielk.usagetracker.ScreenStateType;
import gabrielk.usagetracker.TrackerDateUtils;
import gabrielk.usagetracker.model.ScreenState;

/**
 * Created by GabrielK on 11-Mar-18.
 */

public class ScreenStateItemViewModel extends BaseObservable {
    private ScreenState screenState;

    public ScreenStateItemViewModel(ScreenState screenState) {
        this.screenState = screenState;
    }

    @Bindable
    public String getCount() { return String.valueOf(screenState.getCount()); }
    public void setCount(int count) {
        screenState.setCount(count);
        notifyPropertyChanged(BR.count);
    }

    @Bindable
    public String getType() { return String.valueOf(screenState.getStateType()); }
    public void setType(ScreenStateType type) {
        screenState.setStateType(type);
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public String getNormalizedTimeUtc() { return TrackerDateUtils.getFriendlyDateString(screenState.getNormalizedTimeUtc(), false); }
    public void setNormalizedTimeUtc(long normalizedTimeUtc) {
        screenState.setNormalizedTimeUtc(normalizedTimeUtc);
        notifyPropertyChanged(BR.normalizedTimeUtc);
    }
}
