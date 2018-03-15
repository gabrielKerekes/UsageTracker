package gabrielk.usagetracker;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by GabrielK on 10-Feb-18.
 */

// todo: refactor whole class, everything taken from the sunshine final app
public class TrackerDateUtils {
    public static final long DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1);

    public static long getNormalizedTimeUtc() {
        return System.currentTimeMillis();
    }

    public static String getFriendlyDateString(long normalizedUtc, boolean showFullDate) {
        DateFormat formatter = new SimpleDateFormat("dd. MM. YYYY HH:mm:ss");
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(normalizedUtc);
    }

    public static String getFriendlyDateString(Date date, boolean showFullDate) {
        return getFriendlyDateString(date.getTime(), showFullDate);
    }

    private static long elapsedDaysSinceEpoch(long utcDate) {
        return TimeUnit.MILLISECONDS.toDays(utcDate);
    }

    public static long normalizeDate(long date) {
        long daysSinceEpoch = elapsedDaysSinceEpoch(date);
        long millisFromEpochToTodayAtMidnightUtc = daysSinceEpoch * DAY_IN_MILLIS;
        return millisFromEpochToTodayAtMidnightUtc;
    }

//    public static boolean isDateNormalized(long millisSinceEpoch) {
//        boolean isDateNormalized = false;
//        if (millisSinceEpoch % DAY_IN_MILLIS == 0) {
//            isDateNormalized = true;
//        }
//
//        return isDateNormalized;
//    }
//
//    private static long getLocalMidnightFromNormalizedUtcDate(long normalizedUtcDate) {
//        TimeZone timeZone = TimeZone.getDefault();
//        long gmtOffset = timeZone.getOffset(normalizedUtcDate);
//        long localMidnightMillis = normalizedUtcDate - gmtOffset;
//        return localMidnightMillis;
//    }
//
//    private static long getLocalDateFromNormalizedUtcDate(long normalizedUtcDate) {
//        TimeZone timeZone = TimeZone.getDefault();
//        long gmtOffset = timeZone.getOffset(normalizedUtcDate);
//        long localMidnightMillis = normalizedUtcDate - gmtOffset;
//        return localMidnightMillis;
//    }
//
//    private static String getReadableDateString(Context context, long timeInMillis) {
//        int flags = DateUtils.FORMAT_SHOW_DATE
//                | DateUtils.FORMAT_NO_YEAR
//                | DateUtils.FORMAT_SHOW_WEEKDAY;
//
//        return DateUtils.formatDateTime(context, timeInMillis, flags);
//    }
//
//    private static String getDayName(Context context, long dateInMillis) {
//        long daysFromEpochToProvidedDate = elapsedDaysSinceEpoch(dateInMillis);
//        long daysFromEpochToToday = elapsedDaysSinceEpoch(System.currentTimeMillis());
//
//        int daysAfterToday = (int) (daysFromEpochToProvidedDate - daysFromEpochToToday);
//
//        switch (daysAfterToday) {
//            case 0:
//                return "Today";
//            case 1:
//                return "Tomorrow";
//
//            default:
//                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
//                return dayFormat.format(dateInMillis);
//        }
//    }
}
