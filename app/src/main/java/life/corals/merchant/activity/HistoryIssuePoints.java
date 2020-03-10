package life.corals.merchant.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.adapter.ViewPagerIssueHistoryAdapter;
import life.corals.merchant.fragments.BaseFragment;
import life.corals.merchant.utils.AppTimeOutManagerUtil;

public class HistoryIssuePoints extends AppCompatActivity  implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AppTimeOutManagerUtil appTimeOutManagerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_points_history);

        Toolbar toolbar = findViewById(R.id.issue_points_history_dashboard);
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        toolbar.setTitle("Issued Points History");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryIssuePoints.this, HistoryHomePage.class));
                finish();
                overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
            }
        });
        appTimeOutManagerUtil = new AppTimeOutManagerUtil(HistoryIssuePoints.this);
        tabLayout = (TabLayout) findViewById(R.id.issue_history_tabLayout);
        viewPager = (ViewPager) findViewById(R.id.issue_history_viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("TODAY"));
        tabLayout.addTab(tabLayout.newTab().setText("PREVIOUS"));
        tabLayout.addTab(tabLayout.newTab().setText("SEARCH"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerIssueHistoryAdapter pagerAdapter = new ViewPagerIssueHistoryAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }


    @Override
    public void onBackPressed() {

        List fragmentList = getSupportFragmentManager().getFragments();
        boolean handled = false;
        for (Object f : fragmentList) {
            if (f instanceof BaseFragment) {
                handled = ((BaseFragment) f).onBackPressed();
            }
        }
        if (!handled) {
            finish();
        }

    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        appTimeOutManagerUtil.onResumeApp();
    }
    @Override
    protected void onPause() {
        super.onPause();
        appTimeOutManagerUtil.onPauseApp();
    }
}
