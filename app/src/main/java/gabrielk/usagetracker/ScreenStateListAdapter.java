package gabrielk.usagetracker;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gabrielk.usagetracker.db.ScreenStateContract;

/**
 * Created by GabrielK on 10-Feb-18.
 */

// todo: checks if cursor is null probably shouldn't be needed - something is wrong
public class ScreenStateListAdapter  extends RecyclerView.Adapter<ScreenStateListAdapter.ScreenStateViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public ScreenStateListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ScreenStateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.screen_state_list_item, parent, false);

        return new ScreenStateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScreenStateViewHolder holder, int position) {
        if (mCursor == null) {
            return;
        }

        mCursor.moveToPosition(position);

        long date = mCursor.getLong(mCursor.getColumnIndex(ScreenStateContract.ScreenStateEntry.COLUMN_DATE));
        String type = mCursor.getString(mCursor.getColumnIndex(ScreenStateContract.ScreenStateEntry.COLUMN_STATE));

        holder.countTextView.setText(Integer.toString(mCursor.getCount() - position));
        holder.typeTextView.setText(type);
        holder.dateTextView.setText(TrackerDateUtils.getFriendlyDateString(mContext, date, true));

        if (type.equals(String.valueOf(ScreenStateType.ON.ordinal()))) {
            holder.itemView.setBackgroundColor(Color.RED);
        } else if (type.equals(String.valueOf(ScreenStateType.UNLOCKED.ordinal()))) {
            holder.itemView.setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }

        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (mCursor == newCursor) {
            return null;
        }

        Cursor oldCursor = mCursor;
        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }

        return oldCursor;
    }

    class ScreenStateViewHolder extends RecyclerView.ViewHolder {
        TextView countTextView;
        TextView typeTextView;
        TextView dateTextView;

        public ScreenStateViewHolder(View itemView) {
            super(itemView);
            countTextView = (TextView) itemView.findViewById(R.id.count_text_view);
            typeTextView = (TextView) itemView.findViewById(R.id.type_text_view);
            dateTextView = (TextView) itemView.findViewById(R.id.date_text_view);
        }
    }
}
