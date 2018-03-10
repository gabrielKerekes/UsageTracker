package gabrielk.usagetracker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by GabrielK on 31-Jan-17.
 */

// todo: can probably be removed
public class ScreenStateCounter {
    private static final String SCREEN_STATE_SHARED_PREFS = "SCREEN_STATE_SHARED_PREFS";
    private static final String SCREEN_ON_COUNTER_SHARED_PREFS = "SCREEN_ON_COUNTER_SHARED_PREFS";
    private static final String SCREEN_UNLOCK_COUNTER_SHARED_PREFS = "SCREEN_UNLOCK_COUNTER_SHARED_PREFS";

    public static final int SCREEN_ON_COUNTER = 0;
    public static final int SCREEN_UNLOCK_COUNTER = 1;

    public static void IncrementCounter(Context context, int counterType) {
        String preferencesName;
        switch (counterType) {
            case SCREEN_ON_COUNTER:
            default:
                preferencesName = SCREEN_ON_COUNTER_SHARED_PREFS;
                break;
            case SCREEN_UNLOCK_COUNTER:
                preferencesName = SCREEN_UNLOCK_COUNTER_SHARED_PREFS;
                break;
        }

        SharedPreferences preferences = context.getSharedPreferences(SCREEN_STATE_SHARED_PREFS, Context.MODE_PRIVATE);
        int counter = preferences.getInt(preferencesName, 0);

        counter++;

        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putInt(preferencesName, counter);
        preferencesEditor.commit();
    }

    public static int GetCounter(Context context, int counterType) {
        String preferencesName;
        switch (counterType) {
            case SCREEN_ON_COUNTER:
            default:
                preferencesName = SCREEN_ON_COUNTER_SHARED_PREFS;
                break;
            case SCREEN_UNLOCK_COUNTER:
                preferencesName = SCREEN_UNLOCK_COUNTER_SHARED_PREFS;
                break;
        }

        SharedPreferences prefs = context.getSharedPreferences(SCREEN_STATE_SHARED_PREFS, Context.MODE_PRIVATE);
        return prefs.getInt(preferencesName, 0);
    }

    public static void ResetCounters(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SCREEN_STATE_SHARED_PREFS, Context.MODE_PRIVATE);

        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putInt(SCREEN_ON_COUNTER_SHARED_PREFS, 0);
        preferencesEditor.putInt(SCREEN_UNLOCK_COUNTER_SHARED_PREFS, 0);
        preferencesEditor.commit();
    }
}
