package life.corals.merchant.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import life.corals.merchant.R;
import life.corals.merchant.fragments.BaseFragment;
import life.corals.merchant.fragments.RedeemPointsScanner;
import life.corals.merchant.utils.AppTimeOutManagerUtil;

public class RedeeemPoints extends AppCompatActivity {

    private AppTimeOutManagerUtil appTimeOutManagerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_points);

        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        appTimeOutManagerUtil = new AppTimeOutManagerUtil(RedeeemPoints.this);
        Fragment newFragment = new RedeemPointsScanner();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.redeem_points_frame_layout, newFragment, newFragment.getClass().getSimpleName())
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
