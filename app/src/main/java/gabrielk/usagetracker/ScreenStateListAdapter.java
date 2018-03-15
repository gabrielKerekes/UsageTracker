package gabrielk.usagetracker;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import gabrielk.usagetracker.databinding.ScreenStateListItemBinding;
import gabrielk.usagetracker.db.ScreenStateContentProvider;
import gabrielk.usagetracker.db.ScreenStateContract;
import gabrielk.usagetracker.db.ScreenStateDbHelper;
import gabrielk.usagetracker.model.ScreenState;
import gabrielk.usagetracker.viewmodel.ScreenStateItemViewModel;

/**
 * Created by GabrielK on 10-Feb-18.
 */

// todo: checks if cursor is null probably shouldn't be needed - something is wrong
public class ScreenStateListAdapter  extends RecyclerView.Adapter<ScreenStateListAdapter.ScreenStateViewHolder> {
    private Context context;
    private Cursor screenStates;

    public ScreenStateListAdapter(Context context/*, Cursor screenStates*/) {
        this.context = context;
        //this.screenStates = screenStates;
    }

    @Override
    public ScreenStateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ScreenStateListItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.screen_state_list_item, parent, false);

        return new ScreenStateViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ScreenStateViewHolder holder, int position) {
        ScreenStateListItemBinding binding = holder.binding;
        screenStates.moveToPosition(position);

        // todo: cursor to screenState by niekde malo byt
        long date = screenStates.getLong(screenStates.getColumnIndex(ScreenStateContract.ScreenStateEntry.COLUMN_DATE));
        String type = screenStates.getString(screenStates.getColumnIndex(ScreenStateContract.ScreenStateEntry.COLUMN_STATE));
        int count = screenStates.getCount() - position;

        ScreenState screenState = new ScreenState(ScreenStateType.values()[Integer.valueOf(type)], date, count);

        binding.setViewModel(new ScreenStateItemViewModel(screenState));
    }

    @Override
    public int getItemCount() {
        if (screenStates == null) {
            return 0;
        }

        return screenStates.getCount();
    }

    public void swapCursor(Cursor cursor) {
        screenStates = cursor;
        if (screenStates != null) {
            notifyDataSetChanged();
        }
    }

    class ScreenStateViewHolder extends RecyclerView.ViewHolder {
        private ScreenStateListItemBinding binding;

        private ScreenStateViewHolder(ScreenStateListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}