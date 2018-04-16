package gabrielk.usagetracker.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import gabrielk.usagetracker.R;

public class ReportsActivity extends NavigationActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
    }

    @Override
    public void onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                Intent i = new Intent(ReportsActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.navigation_reports:
                break;
            case R.id.navigation_settings:
                break;
        }
    }
}
