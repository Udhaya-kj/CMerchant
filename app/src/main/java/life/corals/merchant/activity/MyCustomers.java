package life.corals.merchant.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import life.corals.merchant.R;
import life.corals.merchant.fragments.BaseFragment;
import life.corals.merchant.fragments.CustomerListOne;
import life.corals.merchant.utils.AppTimeOutManagerUtil;

public class MyCustomers extends AppCompatActivity {

    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    private String tempcode = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customers);
        appTimeOutManagerUtil = new AppTimeOutManagerUtil(MyCustomers.this);
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tempcode = bundle.getString("temp");
        }
        Bundle bundle1 = new Bundle();
        CustomerListOne fragment1 = new CustomerListOne();
        bundle1.putString("temp", tempcode);
        fragment1.setArguments(bundle1);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.customer_frame_layout, fragment1, fragment1.getClass().getSimpleName())
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
