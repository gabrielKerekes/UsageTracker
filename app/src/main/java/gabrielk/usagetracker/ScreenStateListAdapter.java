package gabrielk.usagetracker;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gabrielk.usagetracker.databinding.ScreenStateListItemBinding;
import gabrielk.usagetracker.db.ScreenStateContract;
import gabrielk.usagetracker.model.ScreenState;
import gabrielk.usagetracker.viewmodel.ScreenStateItemViewModel;

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
//        View view = inflater.inflate(R.layout.screen_state_list_item, parent, false);
        ScreenStateListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.screen_state_list_item, parent, false);
        return new ScreenStateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ScreenStateViewHolder holder, int position) {
        if (mCursor == null) {
            return;
        }

        mCursor.moveToPosition(position);

        // todo: cursor to screenState by niekde malo byt
        long date = mCursor.getLong(mCursor.getColumnIndex(ScreenStateContract.ScreenStateEntry.COLUMN_DATE));
        String type = mCursor.getString(mCursor.getColumnIndex(ScreenStateContract.ScreenStateEntry.COLUMN_STATE));
        int count = mCursor.getCount() - position;
//        holder.countTextView.setText(Integer.toString(mCursor.getCount() - position));
//        holder.typeTextView.setText(type);
//        holder.dateTextView.setText(TrackerDateUtils.getFriendlyDateString(mContext, date, true));

//        if (type.equals(String.valueOf(ScreenStateType.ON.ordinal()))) {
//            holder.itemView.setBackgroundColor(Color.RED);
//        } else if (type.equals(String.valueOf(ScreenStateType.UNLOCKED.ordinal()))) {
//            holder.itemView.setBackgroundColor(Color.GREEN);
//        }

        // todo: refactor
        ScreenState screenState = new ScreenState(ScreenStateType.values()[Integer.valueOf(type)], date, count);
        holder.bind(screenState);
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

    // todo: remove m prefix everywhere from class members (properties)
    private class ScreenStateViewHolder extends RecyclerView.ViewHolder {
        private ScreenStateListItemBinding binding;
//        TextView countTextView;
//        TextView typeTextView;
//        TextView dateTextView;

        private ScreenStateViewHolder(View itemView) {
            super(itemView);
//            mBinding = binding;
//            countTextView = (TextView) itemView.findViewById(R.id.count_text_view);
//            typeTextView = (TextView) itemView.findViewById(R.id.type_text_view);
//            dateTextView = (TextView) itemView.findViewById(R.id.date_text_view);
            bind();
        }

        private void bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView);
            }
        }

        private void unbind() {
            if (binding != null) {
                binding.unbind();
            }
        }

        public void setViewModel(ScreenStateItemViewModel viewModel) {
            if (binding != null) {
                binding.setViewModel(viewModel);
            }
        }
    }
}
