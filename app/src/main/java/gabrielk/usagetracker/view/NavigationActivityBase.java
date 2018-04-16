package gabrielk.usagetracker.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import gabrielk.usagetracker.R;

/**
 * Created by GabrielK on 17-Mar-18.
 */

public abstract class NavigationActivityBase extends AppCompatActivity {
    private final String TAG = NavigationActivityBase.class.getName();

    protected DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            Log.e(TAG, "NavigationActivityBase child must be enclosed in a drawer layout");
            return;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        if (navigationView == null) {
            Log.e(TAG, "NavigationActivityBase child must have a navigation view");
            return;
        }

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        NavigationActivityBase.this.onNavigationItemSelected(menuItem);

                        return true;
                    }
                });
    }

    public abstract void onNavigationItemSelected(MenuItem menuItem);
}
