package gabrielk.usagetracker.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import gabrielk.usagetracker.R;
import gabrielk.usagetracker.databinding.ActivityHomeBinding;
import gabrielk.usagetracker.viewmodel.ScreenStateViewModel;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setViewModel(new ScreenStateViewModel(this));
        binding.screenStateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.screenStateRecyclerView.setAdapter(binding.getViewModel().getAdapter());
    }
}
