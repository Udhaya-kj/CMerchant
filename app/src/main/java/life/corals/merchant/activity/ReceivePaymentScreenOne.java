package life.corals.merchant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.utils.AlertDialogYesNo;
import life.corals.merchant.utils.AppTimeOutManagerUtil;

import static life.corals.merchant.utils.Constants.AMOUNT;
import static life.corals.merchant.utils.Constants.CURRENCY_SYMBOL;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;

public class ReceivePaymentScreenOne extends AppCompatActivity {

    private EditText topupAmountEdt;
    private String topupAmount;
    private TextView currency;
    private SwipeButton swipeButton;
    private AlertDialogYesNo AlertDialog;
    private String DecimalAmnt;
    private SharedPreferences preferences;
    private AppTimeOutManagerUtil appTimeOutManagerUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_payment_one);
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar_receive_payment_one);
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        toolbar.setTitle("Receive Payments");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findView();
    }

    private void findView() {
        appTimeOutManagerUtil = new AppTimeOutManagerUtil(ReceivePaymentScreenOne.this);
        preferences = getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        currency = findViewById(R.id.payment_curr);
        currency.setText(preferences.getString(CURRENCY_SYMBOL, ""));
        topupAmountEdt = findViewById(R.id.topup_amount);
        swipeButton = findViewById(R.id.swipeButton);
        topupAmountEdt.setCursorVisible(true);

        topupAmountEdt.setSelection(topupAmountEdt.getText().length());
        topupAmountEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topupAmountEdt.getText() != null) {
                    topupAmountEdt.setSelection(topupAmountEdt.getText().toString().length());
                }
            }
        });

        topupAmountEdt.post(() -> topupAmountEdt.setSelection(topupAmountEdt.getText().toString().length()));
        topupAmountEdt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                topupAmountEdt.setCursorVisible(false);
                topupAmount = topupAmountEdt.getText().toString();
                if (!topupAmount.isEmpty()){
                    topupAmountEdt.removeTextChangedListener(this);
                    String cleanString = topupAmount.replaceAll("[$,.]", "");
                    BigDecimal bigDecimal = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
                    String converted = NumberFormat.getCurrencyInstance().format(bigDecimal);
                    DecimalAmnt = converted.replaceAll("[$,£]", "");
                    topupAmountEdt.setText(DecimalAmnt);
                    topupAmountEdt.addTextChangedListener(this);
                    topupAmountEdt.post(() -> topupAmountEdt.setSelection(topupAmountEdt.getText().toString().length()));
                    BigDecimal a = new BigDecimal(DecimalAmnt);
                    BigDecimal b = new BigDecimal(0.00);
                    if (a.compareTo(b) <= 0) {
                        swipeButton.setVisibility(View.GONE);
                    } else {
                        swipeButton.setVisibility(View.VISIBLE);
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                topupAmount = topupAmountEdt.getText().toString();
                topupAmountEdt.post(() -> topupAmountEdt.setSelection(topupAmountEdt.getText().toString().length()));
                topupAmountEdt.setCursorVisible(false);
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                topupAmountEdt.setCursorVisible(false);
            }
        });

        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                if (active) {
                    if (topupAmount == null || topupAmount.isEmpty()) {
                        swipeButton.setActivated(false);
                        swipeButton.setFocusable(false);
                        swipeButton.setClickable(false);
                        swipeButton.setEnabled(false);
                        swipeButton.setPressed(false);
                        Toast.makeText(ReceivePaymentScreenOne.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(ReceivePaymentScreenOne.this, ReceivePaymentScreenTwo.class).putExtra(AMOUNT, DecimalAmnt));
                        finish();
                    }

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ReceivePaymentScreenOne.this, Homenew.class));
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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
