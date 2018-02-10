package gabrielk.usagetracker;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gabrielk.usagetracker.db.ScreenStateContract;

/**
 * Created by GabrielK on 10-Feb-18.
 */

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
        mCursor.moveToPosition(position);

        String date = mCursor.getString(mCursor.getColumnIndex(ScreenStateContract.ScreenStateEntry.COLUMN_TIMESTAMP));

        holder.countTextView.setText(Integer.toString(position));
        holder.dateTextView.setText(date);
    }

    @Override
    public int getItemCount() {
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
        TextView dateTextView;

        public ScreenStateViewHolder(View itemView) {
            super(itemView);
            countTextView = (TextView) itemView.findViewById(R.id.count_text_view);
            dateTextView = (TextView) itemView.findViewById(R.id.date_text_view);
        }
    }
}
