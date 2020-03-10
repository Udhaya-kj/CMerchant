package life.corals.merchant.activity;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.fragments.BaseFragment;
import life.corals.merchant.fragments.IntroFragmentOne;
import life.corals.merchant.utils.AppTimeOutManagerUtil;


public class IntroActivity extends AppCompatActivity {

    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intropage);
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("C Merchant");
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        setSupportActionBar(toolbar);
        appTimeOutManagerUtil = new AppTimeOutManagerUtil(IntroActivity.this);
        Fragment newFragment = new IntroFragmentOne();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout_intro_activity, newFragment, newFragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
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
