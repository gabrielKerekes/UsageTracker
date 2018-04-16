package gabrielk.usagetracker.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import gabrielk.usagetracker.R;
import gabrielk.usagetracker.databinding.ActivityHomeBinding;
import gabrielk.usagetracker.viewmodel.ScreenStateViewModel;

public class HomeActivity extends NavigationActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setViewModel(new ScreenStateViewModel(this));
        binding.screenStateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.screenStateRecyclerView.setAdapter(binding.getViewModel().getAdapter());
    }

    @Override
    public void onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                break;
            case R.id.navigation_reports:
                Intent i = new Intent(HomeActivity.this, ReportsActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.navigation_settings:
                break;
        }
    }
}
