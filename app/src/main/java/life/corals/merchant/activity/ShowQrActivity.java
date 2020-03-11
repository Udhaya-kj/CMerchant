package life.corals.merchant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import java.util.Objects;
import life.corals.merchant.R;
import life.corals.merchant.adapter.QrViewPager;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AppTimeOutManagerUtil;
import me.relex.circleindicator.CircleIndicator;

import static life.corals.merchant.utils.Constants.DISPLAY_BUSINESS_NAME;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.QR_CODE;

public class ShowQrActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    private int code=0;
    private TextView[] dots;
    private SharedPreferences preferences;

    private View dotsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr);

        appTimeOutManagerUtil = new AppTimeOutManagerUtil(ShowQrActivity.this);
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

        findView();
    }
    private void findView() {
        TabLayout tabLayout = findViewById(R.id.tabLayout_graph);

        viewPager = findViewById(R.id.viewPager_graph);

        tabLayout.addTab(tabLayout.newTab().setText("REGISTER QR"));
        tabLayout.addTab(tabLayout.newTab().setText("APP QR"));


        final QrViewPager adapter = new QrViewPager(getSupportFragmentManager(), tabLayout.getTabCount());
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


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ShowQrActivity.this, PerformanceActivity.class));
        finish();
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
