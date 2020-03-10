package life.corals.merchant.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.adapter.ViewPagerGraph;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AppTimeOutManagerUtil;

import static life.corals.merchant.utils.Constants.INTENT_TEMP_CODE;

public class PerformanceGraphHomePage extends AppCompatActivity {

    private ViewPager viewPager;
    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    private int code=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_graph_home_page);
        appTimeOutManagerUtil = new AppTimeOutManagerUtil(PerformanceGraphHomePage.this);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        LinearLayout mainLayout = findViewById(R.id.main_layout);
        mainLayout.setBackgroundColor(getResources().getColor(R.color.white));
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        Toolbar toolbar = findViewById(R.id.toolbar_home_graph);
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            code= extras.getInt(INTENT_TEMP_CODE);
            String tool;
            if (code==1){
                tool = "Reward Points";
            }else  if (code==2){
                tool = "Top Ups";
            }else  if (code==3){
                tool ="Receive Payments";
            }else  if (code==4){
                tool ="Customer Visits";
            }else {
                tool = "Home";
            }
            toolbar.setTitle(tool);
        } else {
            new AlertDialogFailure(PerformanceGraphHomePage.this, "Something went wrong", "OK", "sorry") {

                @Override
                public void onButtonClick() {
                    startActivity(new Intent(PerformanceGraphHomePage.this,Homenew.class));
                    finish();
                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                }
            };
        }

        findView();
    }



    private void findView() {
        TabLayout tabLayout = findViewById(R.id.tabLayout_graph);
        viewPager = findViewById(R.id.viewPager_graph);

        tabLayout.addTab(tabLayout.newTab().setText("WEEK"));
        tabLayout.addTab(tabLayout.newTab().setText("MONTH"));

        final ViewPagerGraph adapter = new ViewPagerGraph(getSupportFragmentManager(), tabLayout.getTabCount(),code);
        viewPager.setAdapter(adapter);

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

   /* @Override
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

    }*/

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PerformanceGraphHomePage.this, PerformanceActivity.class));
        finish();
        ///overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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
