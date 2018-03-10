package gabrielk.usagetracker.db;

import android.net.Uri;
import android.provider.BaseColumns;

import gabrielk.usagetracker.TrackerDateUtils;

/**
 * Created by GabrielK on 10-Feb-18.
 */

// todo: maybe other columns - location, ???
public class ScreenStateContract {
    public static final String AUTHORITY = "gabrielk.usagetracker";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_SCREEN_STATE = "screenState";

    public static final class ScreenStateEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCREEN_STATE).build();

        public static final String TABLE_NAME = "screenState";

        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_DATE = "date";

        // todo: implement
        public static String getSqlSelectorForToday() {
            long normalizedUtcNow = TrackerDateUtils.normalizeDate(System.currentTimeMillis());
            return ScreenStateEntry.COLUMN_DATE + " >= " + 0;
        }
    }
}
