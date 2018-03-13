package gabrielk.usagetracker.model;

import gabrielk.usagetracker.ScreenStateType;

/**
 * Created by GabrielK on 10-Mar-18.
 */

public class ScreenState {
    private int id;
    private ScreenStateType stateType;
    private long normalizedTimeUtc;
    private int count;

    public ScreenState(ScreenStateType stateType, long normalizedTimeUtc) {
        this.stateType = stateType;
        this.normalizedTimeUtc = normalizedTimeUtc;
    }

    public ScreenState(ScreenStateType stateType, long normalizedTimeUtc, int count) {
        this.stateType = stateType;
        this.normalizedTimeUtc = normalizedTimeUtc;
        this.count = count;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public ScreenStateType getStateType() { return stateType; }
    public int getStateTypeAsInt() { return stateType.ordinal(); }
    public void setStateType(ScreenStateType stateType) { this.stateType = stateType; }

    public long getNormalizedTimeUtc() { return normalizedTimeUtc; }
    public void setNormalizedTimeUtc(long normalizedTimeUtc) { this.normalizedTimeUtc = normalizedTimeUtc; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}
