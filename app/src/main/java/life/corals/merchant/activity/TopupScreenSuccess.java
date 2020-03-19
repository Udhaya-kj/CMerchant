package life.corals.merchant.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.utils.AppTimeOutManagerUtil;


import static life.corals.merchant.utils.Constants.CURRENCY_SYMBOL;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;

public class TopupScreenSuccess extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = TopupScreenSuccess.class.getName();

    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    MaterialButton okayButton;


    TextView topUpAmountTxtView;


    TextView bonusAmountTxtView;

    TextView walAmountTxtView;
    TextView topupSuccessfull;


    private double walletBalance = 0.0;
    private double topUpAmount = 0.0;
    private double bonusAmount = 0.0;
    private SharedPreferences preferences;


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_topup_screen_three);

        preferences = getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);

        appTimeOutManagerUtil = new AppTimeOutManagerUtil(TopupScreenSuccess.this);
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_color));
        }
        try {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                topUpAmount = Double.parseDouble(Objects.requireNonNull(bundle.getString("TOP-UP-AMOUNT")));
                bonusAmount = Double.parseDouble(Objects.requireNonNull(bundle.getString("BONUS-AMOUNT")));
                walletBalance = Double.parseDouble(Objects.requireNonNull(bundle.getString("WAL-BAL-AMOUNT")));

            } else {
                homePageIntent();
            }
            topupSuccessfull = findViewById(R.id.topup_successfull);
            walAmountTxtView = findViewById(R.id.wall_txt_view);
            bonusAmountTxtView = findViewById(R.id.bonus_amount_txt_view);
            okayButton = findViewById(R.id.wallet_top_up_success_okay_button);
            okayButton.setOnClickListener(this);

            topUpAmountTxtView = findViewById(R.id.top_up_amount_txt_view);

            String topUpAmountTemplate = "Top Up:  ".concat(preferences.getString(CURRENCY_SYMBOL, "")) + String.format("%.2f", topUpAmount);
            String bonusAmountTemplate = "Bonus:  ".concat(preferences.getString(CURRENCY_SYMBOL, "")) + String.format("%.2f", bonusAmount);
            String walAmountTemplate = "Wallet Balance:  ".concat(preferences.getString(CURRENCY_SYMBOL, "")) + String.format("%.2f", walletBalance);

            topUpAmountTxtView.setText(topUpAmountTemplate);
            bonusAmountTxtView.setText(bonusAmountTemplate);
            walAmountTxtView.setText(walAmountTemplate);

            Animation startAnimation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation1);
            topupSuccessfull.startAnimation(startAnimation1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    topupSuccessfull.clearAnimation();
                }
            }, 3000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.wallet_top_up_success_okay_button) {
            homePageIntent();
        }

    }

    private void homePageIntent() {
        startActivity(new Intent(this, Homenew.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
          finish();
        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
