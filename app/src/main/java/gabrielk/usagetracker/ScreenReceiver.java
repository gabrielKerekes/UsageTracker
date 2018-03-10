package gabrielk.usagetracker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import gabrielk.usagetracker.db.ScreenStateContract;
import gabrielk.usagetracker.db.ScreenStateDbHelper;
import gabrielk.usagetracker.model.ScreenState;
import gabrielk.usagetracker.view.HomeActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by GabrielK on 31-Jan-17.
 */

public class ScreenReceiver extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 2891;

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        ScreenStateType screenStateType = ScreenStateType.ON;

        // todo: http://stackoverflow.com/questions/3446202/android-detect-phone-unlock-event-not-screen-on
        if (intentAction.equals(Intent.ACTION_SCREEN_OFF)) {
            // todo: record the time to enable timing of screen on
            return;
        } else if (intentAction.equals(Intent.ACTION_SCREEN_ON)) {
            screenStateType = ScreenStateType.ON;
        } else if (intentAction.equals(Intent.ACTION_USER_PRESENT)) {
            screenStateType = ScreenStateType.UNLOCKED;
        }

        ScreenState screenState = new ScreenState(screenStateType, TrackerDateUtils.getNormalizedTimeUtc());
        ScreenStateDbHelper dbHelper = new ScreenStateDbHelper(context);
        dbHelper.insert(screenState);

        // todo: refactor - Notification or smth like that
        UpdateNotification(context);
    }

    // todo: refactor - so it is called from some event of the content resolver update or smth
    public static void UpdateNotification(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent contentIntent =  PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//        int screenOnCounter = ScreenStateCounter.GetCounter(context, ScreenStateCounter.SCREEN_ON_COUNTER);
//        int screenUnlockCounter = ScreenStateCounter.GetCounter(context, ScreenStateCounter.SCREEN_UNLOCK_COUNTER);
        int screenOnCounter = 0;
        int screenUnlockCounter = 0;
        // todo: refactor
        try {
            screenOnCounter = context.getContentResolver().query(ScreenStateContract.ScreenStateEntry.CONTENT_URI,
                    new String[] { ScreenStateContract.ScreenStateEntry._ID, ScreenStateContract.ScreenStateEntry.COLUMN_STATE, ScreenStateContract.ScreenStateEntry.COLUMN_DATE },
                    ScreenStateContract.ScreenStateEntry.COLUMN_STATE + "=?", new String[] { String.valueOf(ScreenStateType.ON.ordinal()) }, ScreenStateContract.ScreenStateEntry.COLUMN_DATE).getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // todo: refactor
        try {
            screenUnlockCounter = context.getContentResolver().query(ScreenStateContract.ScreenStateEntry.CONTENT_URI,
                    new String[] { ScreenStateContract.ScreenStateEntry._ID, ScreenStateContract.ScreenStateEntry.COLUMN_STATE, ScreenStateContract.ScreenStateEntry.COLUMN_DATE },
                    ScreenStateContract.ScreenStateEntry.COLUMN_STATE + "=?", new String[] { String.valueOf(ScreenStateType.UNLOCKED.ordinal()) }, ScreenStateContract.ScreenStateEntry.COLUMN_DATE).getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        // todo: set icon
                        // todo: strings
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Display turn ons today: " + screenOnCounter)
                        .setContentText("Display unlocks today: " + screenUnlockCounter)
                        .setContentIntent(contentIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setOngoing(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
