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
    private IntentFilter intentFilter;
    private BroadcastReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeScreenIntentFilter();
        initializeReceiver();
    }

    private void initializeScreenIntentFilter() {
        intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
    }

    private void initializeReceiver() {
        receiver = new ScreenReceiver();
        registerReceiver(receiver, intentFilter);
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
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);
    }
}
