package gabrielk.usagetracker;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by GabrielK on 31-Jan-17.
 */

public class UpdateService extends Service {
    private IntentFilter mIntentFilter;
    private BroadcastReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeScreenIntentFilter();

        mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, mIntentFilter);
    }

    private void initializeScreenIntentFilter() {
        // register receiver that handles screen on and screen off logic
        mIntentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        mIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mIntentFilter.addAction(Intent.ACTION_USER_PRESENT);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mReceiver);
    }
}
