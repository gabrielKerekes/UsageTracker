package gabrielk.usagetracker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by GabrielK on 31-Jan-17.
 */

public class ScreenReceiver extends BroadcastReceiver {
    public static final int NOTIFICATION_ID = 2891;

    @Override
    public void onReceive(Context context, Intent intent) {
        // todo: http://stackoverflow.com/questions/3446202/android-detect-phone-unlock-event-not-screen-on
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            // todo: do smth here
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            ScreenStateCounter.IncrementCounter(context, ScreenStateCounter.SCREEN_ON_COUNTER);
        } else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            ScreenStateCounter.IncrementCounter(context, ScreenStateCounter.SCREEN_UNLOCK_COUNTER);
        }

        UpdateNotification(context);
    }

    public static void UpdateNotification(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent contentIntent =  PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        int screenOnCounter = ScreenStateCounter.GetCounter(context, ScreenStateCounter.SCREEN_ON_COUNTER);
        int screenUnlockCounter = ScreenStateCounter.GetCounter(context, ScreenStateCounter.SCREEN_UNLOCK_COUNTER);

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
