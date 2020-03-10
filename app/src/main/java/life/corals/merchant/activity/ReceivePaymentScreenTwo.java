package life.corals.merchant.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import life.corals.merchant.R;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body19;
import life.corals.merchant.client.model.GenerictokengenerateRedeem;
import life.corals.merchant.client.model.GenerictokengenerateTopup;
import life.corals.merchant.client.model.InlineResponse20014;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AppTimeOutManagerUtil;
import life.corals.merchant.utils.GenererateTopupQR;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.MySharedPreference;
import life.corals.merchant.utils.Util;

import static life.corals.merchant.utils.Constants.AMOUNT;
import static life.corals.merchant.utils.Constants.CURRENCY_SYMBOL;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.OUTLET_ID;

public class ReceivePaymentScreenTwo extends AppCompatActivity {


    private ImageView imageView;
    private MaterialButton ok;
    private TextView topupAmountTvw;
    private TextView topupAmountTvwCurr;
    private TextView timer;
    private String amount;
    private LinearLayout layout;
    private Body19 body = new Body19();
    private SharedPreferences preferences;
    private Gson gson;
    private AlertDialogFailure failureAlertDialog;
    private MySharedPreference sharedPreference;
    private String authToken;
    private IntermediateAlertDialog intermediateAlertDialog;
    private CountDownTimer countDownTimer;
    final Handler handler = new Handler();
    private AppTimeOutManagerUtil appTimeOutManagerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_payment_screen_two);
        appTimeOutManagerUtil = new AppTimeOutManagerUtil(ReceivePaymentScreenTwo.this);
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar_receive_payement_two);
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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            amount = bundle.getString(AMOUNT, null);
        } else {

            startActivity(new Intent(ReceivePaymentScreenTwo.this, ReceivePaymentScreenOne.class));
            finish();
        }
        if (amount == null) {
            startActivity(new Intent(ReceivePaymentScreenTwo.this, ReceivePaymentScreenOne.class));
            finish();
        }
        gson = new Gson();
        preferences = getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        sharedPreference = new MySharedPreference(getApplicationContext());
        findView();
        callApi();
    }

    private void findView() {
        layout = findViewById(R.id.receive_payment_two);
        timer = findViewById(R.id.timer);
        imageView = findViewById(R.id.qr_code_imageview);
        ok = findViewById(R.id.cancel);
        topupAmountTvw = findViewById(R.id.topup_amount);
        topupAmountTvwCurr = findViewById(R.id.topup_amount_curr);
        topupAmountTvw.setText(amount);
        topupAmountTvwCurr.setText(preferences.getString(CURRENCY_SYMBOL, ""));

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                startActivity(new Intent(ReceivePaymentScreenTwo.this, Homenew.class));
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(120000, 1000) {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            public void onTick(long millisUntilFinished) {

                String expiryTime = String.format("%dm %ds", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                timer.setText("QR code expires in " + String.format("%dm %ds", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                if (expiryTime.equals("0m 9s")) {
                    timer.setTextColor(getResources().getColor(R.color.redbtn));
                }
            }

            public void onFinish() {
                timer.setText("Time out");
                Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation1);
                timer.startAnimation(startAnimation);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        failureAlertDialog = new AlertDialogFailure(ReceivePaymentScreenTwo.this, "QR Code Time out", "OK", "Sorry") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(ReceivePaymentScreenTwo.this, ReceivePaymentScreenOne.class));
                                finish();
                            }
                        };

                    }
                }, 1500);

            }
        }.start();
    }

    private void callApi() {
        intermediateAlertDialog = new IntermediateAlertDialog(ReceivePaymentScreenTwo.this);
        if (Util.getConnectivityStatusString(this)) {
            ok.setVisibility(View.VISIBLE);
            getRequestBody();
            try {
                tokenGenerateAPIcall(body);
            } catch (ApiException e) {
                intermediateAlertDialog.dismissAlertDialog();
                new AlertDialogFailure(ReceivePaymentScreenTwo.this, "Please try again later", "OK", "Something went wrong") {
                    @Override
                    public void onButtonClick() {
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                        startActivity(new Intent(ReceivePaymentScreenTwo.this, ReceivePaymentScreenOne.class));
                        finish();
                    }
                };
            }
        } else {
            ok.setVisibility(View.GONE);
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(ReceivePaymentScreenTwo.this, "Please try again later", "Retry", "Something went wrong") {
                @Override
                public void onButtonClick() {
                    callApi();
                }
            };

        }
    }

    private void getRequestBody() {
        GenerictokengenerateTopup generictokengenerateTopup = new GenerictokengenerateTopup();
        GenerictokengenerateRedeem generictokengenerateRedeem = new GenerictokengenerateRedeem();
        body.setDeviceid(preferences.getString(DEVICE_ID, ""));
        body.setMerId(preferences.getString(MERCHANT_ID, ""));
        body.setRedeem(generictokengenerateRedeem.paymentamount(amount));
        body.setTokenPurpose(Body19.TokenPurposeEnum.P);
        body.setOutletId(preferences.getString(OUTLET_ID, ""));
        body.setSessiontoken("no");
        body.setCustId("1");
        generictokengenerateTopup.setTopupvalue("0");
        generictokengenerateTopup.setBonus("0");
        generictokengenerateTopup.setCampaignId("0");
        body.setTopup(generictokengenerateTopup);
    }

    private void tokenGenerateAPIcall(Body19 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(this);
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.genericTokenGeneratePostAsync(requestBody, new ApiCallback<InlineResponse20014>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        intermediateAlertDialog.dismissAlertDialog();
                        new AlertDialogFailure(ReceivePaymentScreenTwo.this, "Please try again later", "OK", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                }
                                startActivity(new Intent(ReceivePaymentScreenTwo.this, ReceivePaymentScreenOne.class));
                                finish();
                            }
                        };
                    }
                });

            }

            @Override
            public void onSuccess(InlineResponse20014 result, int statusCode, Map<String, List<String>> responseHeaders) {
                if (statusCode == 200) {
                    if (result != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                intermediateAlertDialog.dismissAlertDialog();
                                authToken = result.getAuthtoken();
                                GenererateTopupQR genQR = new GenererateTopupQR();
                                String qr = genQR.generateTopupQR("P", preferences.getString(MERCHANT_ID, ""), preferences.getString(OUTLET_ID, ""), "", authToken);
                                tokenGenerate(qr);
                                startTimer();
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                intermediateAlertDialog.dismissAlertDialog();
                                new AlertDialogFailure(ReceivePaymentScreenTwo.this, "Please try again later", "Retry", "Something went wrong") {
                                    @Override
                                    public void onButtonClick() {
                                        callApi();
                                    }
                                };
                            }
                        });

                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            intermediateAlertDialog.dismissAlertDialog();
                            new AlertDialogFailure(ReceivePaymentScreenTwo.this, "Please try again late", "Retry", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    callApi();
                                }
                            };
                        }
                    });
                }

            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

            }
        });
    }

    public void tokenGenerate(String qr) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(qr, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onBackPressed() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        startActivity(new Intent(ReceivePaymentScreenTwo.this, ReceivePaymentScreenOne.class));
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
