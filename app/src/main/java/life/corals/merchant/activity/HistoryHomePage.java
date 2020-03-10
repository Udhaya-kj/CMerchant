package life.corals.merchant.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.utils.AppTimeOutManagerUtil;

public class HistoryHomePage extends AppCompatActivity {



    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    private FrameLayout topUp ;
    private FrameLayout points;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_home_page);
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        Toolbar toolbar = findViewById(R.id.toolbar_history_home_page);
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        toolbar.setTitle("History");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
            }
        });
        appTimeOutManagerUtil = new AppTimeOutManagerUtil(HistoryHomePage.this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        topUp = findViewById(R.id.topup_layout);
        points = findViewById(R.id.points_layout);

        topUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryHomePage.this, HistoryTopup.class));
                finish();
            }
        });

        points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HistoryHomePage.this, HistoryIssuePoints.class));
                finish();

            }
        });


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

    @Override
    public void onBackPressed() {
       startActivity(new Intent(HistoryHomePage.this,Homenew.class));
       finish();
       //overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }
}
