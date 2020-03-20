package life.corals.merchant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body16;
import life.corals.merchant.client.model.Body29;
import life.corals.merchant.client.model.InlineResponseSummary;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AppTimeOutManagerUtil;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.Util;

import static life.corals.merchant.utils.Constants.CURRENCY_SYMBOL;
import static life.corals.merchant.utils.Constants.CUSTOMER_NEW;
import static life.corals.merchant.utils.Constants.CUSTOMER_OLD;
import static life.corals.merchant.utils.Constants.CUSTOMER_VISIT_NEW;
import static life.corals.merchant.utils.Constants.CUSTOMER_VISIT_OLD;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.INSIGHTS_LOAD_COUNT;
import static life.corals.merchant.utils.Constants.INSIGHTS_LOAD_COUNT_STR;
import static life.corals.merchant.utils.Constants.INTENT_TEMP_CODE;
import static life.corals.merchant.utils.Constants.IPONTS_NEW;
import static life.corals.merchant.utils.Constants.IPONTS_OLD;
import static life.corals.merchant.utils.Constants.IS_ADMIN;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_INSIGHTS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_WALLET_BAL;
import static life.corals.merchant.utils.Constants.NOOF_PAYMENTS_NEW;
import static life.corals.merchant.utils.Constants.NOOF_PAYMENTS_OLD;
import static life.corals.merchant.utils.Constants.NOOF_TOPUP_NEW;
import static life.corals.merchant.utils.Constants.NOOF_TOPUP_OLD;
import static life.corals.merchant.utils.Constants.RPONTS_NEW;
import static life.corals.merchant.utils.Constants.RPONTS_OLD;
import static life.corals.merchant.utils.Constants.TOTAL_CUSTOMERS;
import static life.corals.merchant.utils.Constants.TOT_PAYMENTS_NEW;
import static life.corals.merchant.utils.Constants.TOT_PAYMENTS_OLD;
import static life.corals.merchant.utils.Constants.TOT_TOPUP_NEW;
import static life.corals.merchant.utils.Constants.TOT_TOPUP_OLD;

public class PerformanceActivity extends AppCompatActivity {

    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    private SharedPreferences preferences;
    private SharedPreferences insightsPreferences;
    private Body16 body = new Body16();
    private IntermediateAlertDialog intermediateAlertDialog;

    private TextView customersOld, customersVisitOld, iPointsOld, rPointsOld, noOfTxnOld, noOfPaymentOld, totTopupAmntOld, totPaymentAmntOld;
    private TextView customersNew, customersVisitNew, iPointsNew, rPointsNew, noOfTopupNew, noOfPaymentNew, totTopupAmntNew, totPaymentAmntNew;
    private TextView customersSts, customersVisitSts, iPointsSts, rPointsSts, noOfTopupSts, noOfPaymentSts, totTopupAmntSts, totPaymentAmntSts;
    private ImageButton refresh;
    private LinearLayout textLayout;

    private LinearLayout permissionDenied;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        appTimeOutManagerUtil = new AppTimeOutManagerUtil(PerformanceActivity.this);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        preferences = getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar_performance);
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
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        permissionDenied  = findViewById(R.id.permission_denied);
        scrollView  = findViewById(R.id.scrollview);
        if (preferences.getString(IS_ADMIN, "").equals("false")) {
            scrollView.setVisibility(View.GONE);
            permissionDenied.setVisibility(View.VISIBLE);
        } else {
            scrollView.setVisibility(View.VISIBLE);
            permissionDenied.setVisibility(View.GONE);
            insightsPreferences = getSharedPreferences(MERCHANT_INSIGHTS_PREFERENCE, MODE_PRIVATE);
            findView();
        }
    }

    private void findView() {

        textLayout = findViewById(R.id.text_layout);

        customersOld = findViewById(R.id.customer_old);
        customersVisitOld = findViewById(R.id.customer_visit_old);
        iPointsOld = findViewById(R.id.ipoints_old);
        rPointsOld = findViewById(R.id.rpoints_old);
        noOfTxnOld = findViewById(R.id.no_of_topup_old);
        noOfPaymentOld = findViewById(R.id.no_payment_amnt_old);
        totTopupAmntOld = findViewById(R.id.topup_amnt_old);
        totPaymentAmntOld = findViewById(R.id.tot_payments_amnt_old);

        totPaymentAmntNew = findViewById(R.id.tot_payment_amnt_new);
        totTopupAmntNew = findViewById(R.id.tot_topup_new);
        iPointsNew = findViewById(R.id.ipoints_new);
        customersNew = findViewById(R.id.customer_new);
        customersVisitNew = findViewById(R.id.customer_visit_new);
        rPointsNew = findViewById(R.id.rpoints_new);
        noOfTopupNew = findViewById(R.id.no_of_toppup_new);
        noOfPaymentNew = findViewById(R.id.no_of_payments_new);

        totPaymentAmntSts = findViewById(R.id.tot_payment_amnt_status);
        totTopupAmntSts = findViewById(R.id.tot_topup_status);
        iPointsSts = findViewById(R.id.ipoints_status);
        customersSts = findViewById(R.id.customer_status);
        customersVisitSts = findViewById(R.id.customer_visit_status);
        rPointsSts = findViewById(R.id.rpoints_status);
        noOfTopupSts = findViewById(R.id.no_of_toppup_status);
        noOfPaymentSts = findViewById(R.id.no_of_payments_status);


        refresh = findViewById(R.id.refresh);

        TextView currency = findViewById(R.id.currency_performance);
        TextView totWallet = findViewById(R.id.tot_wallet);
        TextView totCustomers = findViewById(R.id.tot_customers);
        currency.setText(preferences.getString(CURRENCY_SYMBOL, ""));
        totCustomers.setText(preferences.getString(TOTAL_CUSTOMERS, ""));
        totWallet.setText(preferences.getString(MERCHANT_WALLET_BAL, ""));
        if (preferences.getString(MERCHANT_WALLET_BAL, "").contains("-")) {
            totWallet.setText("0.00");
        }
        FrameLayout rewardPointsLt = findViewById(R.id.reward_points);
        FrameLayout topupLt = findViewById(R.id.top_up);
        FrameLayout paymentReceivedLt = findViewById(R.id.payment_received);
        FrameLayout customerVisitLt = findViewById(R.id.customer_visits);

        rewardPointsLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerformanceActivity.this, PerformanceGraphHomePage.class).putExtra(INTENT_TEMP_CODE, 1));
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        topupLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerformanceActivity.this, PerformanceGraphHomePage.class).putExtra(INTENT_TEMP_CODE, 2));
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        paymentReceivedLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerformanceActivity.this, PerformanceGraphHomePage.class).putExtra(INTENT_TEMP_CODE, 3));
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        customerVisitLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerformanceActivity.this, PerformanceGraphHomePage.class).putExtra(INTENT_TEMP_CODE, 4));
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intermediateAlertDialog = new IntermediateAlertDialog(PerformanceActivity.this);
                callAPI();
            }
        });


        int count = insightsPreferences.getInt(INSIGHTS_LOAD_COUNT_STR, INSIGHTS_LOAD_COUNT);


        if (count > 0) {
            customersOld.setText(insightsPreferences.getString(CUSTOMER_OLD, ""));
            customersNew.setText(insightsPreferences.getString(CUSTOMER_NEW, ""));
            if (Integer.parseInt(insightsPreferences.getString(CUSTOMER_OLD, "")) < Integer.parseInt(insightsPreferences.getString(CUSTOMER_NEW, ""))) {
                customersSts.setText(R.string.good_emji);
            } else if (Integer.parseInt(insightsPreferences.getString(CUSTOMER_OLD, "")) == Integer.parseInt(insightsPreferences.getString(CUSTOMER_NEW, ""))) {
                customersSts.setText(R.string.equal_emji);
            } else customersSts.setText(R.string.bad_emoji);

            customersVisitOld.setText(insightsPreferences.getString(CUSTOMER_VISIT_OLD, ""));
            customersVisitNew.setText(insightsPreferences.getString(CUSTOMER_VISIT_NEW, ""));
            if (Integer.parseInt(insightsPreferences.getString(CUSTOMER_VISIT_OLD, "")) < Integer.parseInt(insightsPreferences.getString(CUSTOMER_VISIT_NEW, ""))) {
                customersVisitSts.setText(R.string.good_emji);
            } else if (Integer.parseInt(insightsPreferences.getString(CUSTOMER_VISIT_OLD, "")) == Integer.parseInt(insightsPreferences.getString(CUSTOMER_VISIT_NEW, ""))) {
                customersVisitSts.setText(R.string.equal_emji);
            } else customersVisitSts.setText(R.string.bad_emoji);

            iPointsOld.setText(insightsPreferences.getString(IPONTS_OLD, ""));
            iPointsNew.setText(insightsPreferences.getString(IPONTS_NEW, ""));

            if (Integer.parseInt(insightsPreferences.getString(IPONTS_OLD, "")) < Integer.parseInt(insightsPreferences.getString(IPONTS_NEW, ""))) {
                iPointsSts.setText(R.string.good_emji);
            } else if (Integer.parseInt(insightsPreferences.getString(IPONTS_OLD, "")) == Integer.parseInt(insightsPreferences.getString(IPONTS_NEW, ""))) {
                iPointsSts.setText(R.string.equal_emji);
            } else iPointsSts.setText(R.string.bad_emoji);

            rPointsOld.setText(insightsPreferences.getString(RPONTS_OLD, ""));
            rPointsNew.setText(insightsPreferences.getString(RPONTS_NEW, ""));

            if (Integer.parseInt(insightsPreferences.getString(RPONTS_OLD, "")) < Integer.parseInt(insightsPreferences.getString(RPONTS_NEW, ""))) {
                rPointsSts.setText(R.string.good_emji);
            } else if (Integer.parseInt(insightsPreferences.getString(RPONTS_OLD, "")) == Integer.parseInt(insightsPreferences.getString(RPONTS_NEW, ""))) {
                rPointsSts.setText(R.string.equal_emji);
            } else rPointsSts.setText(R.string.bad_emoji);

            noOfTxnOld.setText(insightsPreferences.getString(NOOF_TOPUP_OLD, ""));
            noOfTopupNew.setText(insightsPreferences.getString(NOOF_TOPUP_NEW, ""));

            if (Integer.parseInt(insightsPreferences.getString(NOOF_TOPUP_OLD, "")) < Integer.parseInt(insightsPreferences.getString(NOOF_TOPUP_NEW, ""))) {
                noOfTopupSts.setText(R.string.good_emji);
            } else if (Integer.parseInt(insightsPreferences.getString(NOOF_TOPUP_OLD, "")) == Integer.parseInt(insightsPreferences.getString(NOOF_TOPUP_NEW, ""))) {
                noOfTopupSts.setText(R.string.equal_emji);
            } else noOfTopupSts.setText(R.string.bad_emoji);

            noOfPaymentOld.setText(insightsPreferences.getString(NOOF_PAYMENTS_OLD, ""));
            noOfPaymentNew.setText(insightsPreferences.getString(NOOF_PAYMENTS_NEW, ""));
            if (Integer.parseInt(insightsPreferences.getString(NOOF_PAYMENTS_OLD, "")) < Integer.parseInt(insightsPreferences.getString(NOOF_PAYMENTS_NEW, ""))) {
                noOfPaymentSts.setText(R.string.good_emji);
            } else if (Integer.parseInt(insightsPreferences.getString(NOOF_PAYMENTS_OLD, "")) == Integer.parseInt(insightsPreferences.getString(NOOF_PAYMENTS_NEW, ""))) {
                noOfPaymentSts.setText(R.string.equal_emji);
            } else noOfPaymentSts.setText(R.string.bad_emoji);

            totTopupAmntOld.setText(insightsPreferences.getString(TOT_TOPUP_OLD, ""));
            totTopupAmntNew.setText(insightsPreferences.getString(TOT_TOPUP_NEW, ""));

            if (new BigDecimal(insightsPreferences.getString(TOT_TOPUP_OLD, "")).equals(new BigDecimal(insightsPreferences.getString(TOT_TOPUP_NEW, "")))) {
                totTopupAmntSts.setText(R.string.equal_emji);
            } else if (new BigDecimal(insightsPreferences.getString(TOT_TOPUP_OLD, "")).compareTo(new BigDecimal(insightsPreferences.getString(TOT_TOPUP_NEW, ""))) < 1) {
                totTopupAmntSts.setText(R.string.good_emji);
            } else totTopupAmntSts.setText(R.string.bad_emoji);

            totPaymentAmntOld.setText(insightsPreferences.getString(TOT_PAYMENTS_OLD, ""));
            totPaymentAmntNew.setText(insightsPreferences.getString(TOT_PAYMENTS_NEW, ""));

            if (new BigDecimal(insightsPreferences.getString(TOT_PAYMENTS_OLD, "")).equals(new BigDecimal(insightsPreferences.getString(TOT_PAYMENTS_NEW, "")))) {
                totPaymentAmntSts.setText(R.string.equal_emji);
            } else if (new BigDecimal(insightsPreferences.getString(TOT_PAYMENTS_OLD, "")).compareTo(new BigDecimal(insightsPreferences.getString(TOT_PAYMENTS_NEW, ""))) < 0) {
                totPaymentAmntSts.setText(R.string.good_emji);
            } else {
                totPaymentAmntSts.setText(R.string.bad_emoji);
            }

            refresh.setVisibility(View.VISIBLE);
        } else {
            Log.d("insightsPreferences", "findView2: "+count);
            intermediateAlertDialog = new IntermediateAlertDialog(this);
            callAPI();
            SharedPreferences.Editor editor = insightsPreferences.edit();
            editor.putInt(INSIGHTS_LOAD_COUNT_STR, 1);
            editor.apply();
        }
    }

    private void callAPI() {
        if (Util.getConnectivityStatusString(this)) {
            try {
                getRequestBody();
                getInsightsSummary(body);
            } catch (Exception e) {
                intermediateAlertDialog.dismissAlertDialog();
                new AlertDialogFailure(this, "Please try again later!", "OK", "Something went wrong") {
                    @Override
                    public void onButtonClick() {
                        startActivity(new Intent(PerformanceActivity.this, Homenew.class));
                        finish();
                    }
                };
            }
        } else {
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(PerformanceActivity.this, "Please turn ON your data connection ", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callAPI();
                }
            };
        }
    }

    private void getRequestBody() {
        body.setDeviceid(preferences.getString(DEVICE_ID, ""));
        body.setMerId(preferences.getString(MERCHANT_ID, ""));
        body.setSessiontoken("notoken");
        Log.d("getWeekRewardPoints", "getRequestBodymonth: " + body);
    }

    private void getInsightsSummary(Body16 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(this);
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.insightsSummaryPostAsync(requestBody, new ApiCallback<InlineResponseSummary>() {

            @Override
            public void onFailure(ApiException e, final int statusCode, Map<String, List<String>> responseHeaders) {
                runOnUiThread(() -> {
                    Log.d("InlineResponseSummary", "onSuccess: " + e + "  " + statusCode);
                    intermediateAlertDialog.dismissAlertDialog();
                    if (statusCode == 404) {
                        new AlertDialogFailure(PerformanceActivity.this, "Please try again later!", "OK", "Invalid merchant id (or) Device id") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(PerformanceActivity.this, PerformanceActivity.class));
                                finish();
                            }
                        };
                    } else if (statusCode == 406) {
                        new AlertDialogFailure(PerformanceActivity.this, "Please try again later!", "OK", "Invalid request type") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(PerformanceActivity.this, PerformanceActivity.class));
                                finish();
                            }
                        };
                    } else {
                        new AlertDialogFailure(PerformanceActivity.this, "Please try again later!", "OK", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(PerformanceActivity.this, PerformanceActivity.class));
                                finish();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(InlineResponseSummary result, int statusCode, Map<String, List<String>> responseHeaders) {
                runOnUiThread(() -> {
                    Log.d("InlineResponseSummary", "onSuccess: " + result);
                    intermediateAlertDialog.dismissAlertDialog();

                    customersOld.setText(result.getCustomersOld());
                    customersNew.setText(result.getCustomersNew());

                    customersVisitOld.setText(result.getCustomersVisitOld());
                    customersVisitNew.setText(result.getCustomersVisitNew());

                    iPointsOld.setText(result.getIpointsOld());
                    iPointsNew.setText(result.getIpointsNew());

                    rPointsOld.setText(result.getRpointsOld());
                    rPointsNew.setText(result.getRpointsNew());

                    noOfTxnOld.setText(result.getNoOfTxnOld());
                    noOfTopupNew.setText(result.getNoOfTxnNew());


                    noOfPaymentOld.setText(result.getNoOfReceivesOld());
                    noOfPaymentNew.setText(result.getNoOfReceivesNew());

                    totTopupAmntOld.setText(result.getTotAmntTxnOld());
                    totTopupAmntNew.setText(result.getTotAmntTxnNew());

                    totPaymentAmntOld.setText(result.getTotAmntReceivesOld());
                    totPaymentAmntNew.setText(result.getTotAmntReceivesNew());

                    if (Integer.parseInt(result.getCustomersOld()) < Integer.parseInt(result.getCustomersNew())) {
                        customersSts.setText(R.string.good_emji);
                    } else if (Integer.parseInt(result.getCustomersOld()) == Integer.parseInt(result.getCustomersNew())) {
                        customersSts.setText(R.string.equal_emji);
                    } else customersSts.setText(R.string.bad_emoji);

                    if (Integer.parseInt(result.getCustomersVisitOld()) < Integer.parseInt(result.getCustomersVisitNew())) {
                        customersVisitSts.setText(R.string.good_emji);
                    } else if (Integer.parseInt(result.getCustomersVisitOld()) == Integer.parseInt(result.getCustomersVisitNew())) {
                        customersVisitSts.setText(R.string.equal_emji);
                    } else customersVisitSts.setText(R.string.bad_emoji);

                    if (Integer.parseInt(result.getIpointsOld()) < Integer.parseInt(result.getIpointsNew())) {
                        iPointsSts.setText(R.string.good_emji);
                    } else if (Integer.parseInt(result.getIpointsOld()) == Integer.parseInt(result.getIpointsNew())) {
                        iPointsSts.setText(R.string.equal_emji);
                    } else iPointsSts.setText(R.string.bad_emoji);

                    if (Integer.parseInt(result.getRpointsOld()) < Integer.parseInt(result.getRpointsNew())) {
                        rPointsSts.setText(R.string.good_emji);
                    } else if (Integer.parseInt(result.getRpointsOld()) == Integer.parseInt(result.getRpointsNew())) {
                        rPointsSts.setText(R.string.equal_emji);
                    } else rPointsSts.setText(R.string.bad_emoji);

                    if (Integer.parseInt(result.getNoOfTxnOld()) < Integer.parseInt(result.getNoOfTxnNew())) {
                        noOfTopupSts.setText(R.string.good_emji);
                    } else if (Integer.parseInt(result.getNoOfTxnOld()) == Integer.parseInt(result.getNoOfTxnNew())) {
                        noOfTopupSts.setText(R.string.equal_emji);
                    } else noOfTopupSts.setText(R.string.bad_emoji);

                    if (Integer.parseInt(result.getNoOfReceivesOld()) < Integer.parseInt(result.getNoOfReceivesNew())) {
                        noOfPaymentSts.setText(R.string.good_emji);
                    } else if (Integer.parseInt(result.getNoOfReceivesOld()) == Integer.parseInt(result.getNoOfReceivesNew())) {
                        noOfPaymentSts.setText(R.string.equal_emji);
                    } else noOfPaymentSts.setText(R.string.bad_emoji);

                    if (new BigDecimal(result.getTotAmntTxnOld()).equals(new BigDecimal(result.getTotAmntTxnNew()))) {
                        totTopupAmntSts.setText(R.string.equal_emji);
                    } else if (new BigDecimal(result.getTotAmntTxnOld()).compareTo(new BigDecimal(result.getTotAmntTxnNew())) < 1) {
                        totTopupAmntSts.setText(R.string.good_emji);
                    } else totTopupAmntSts.setText(R.string.bad_emoji);

                    if (new BigDecimal(result.getTotAmntReceivesOld()).equals(new BigDecimal(result.getTotAmntReceivesNew()))) {
                        totPaymentAmntSts.setText(R.string.equal_emji);
                    } else if (new BigDecimal(result.getTotAmntReceivesOld()).compareTo(new BigDecimal(result.getTotAmntReceivesNew())) < 0) {
                        totPaymentAmntSts.setText(R.string.good_emji);
                    } else {
                        totPaymentAmntSts.setText(R.string.bad_emoji);
                    }
                    textLayout.setVisibility(View.VISIBLE);

                    SharedPreferences.Editor editor = insightsPreferences.edit();
                    editor.putString(CUSTOMER_OLD, result.getCustomersOld());
                    editor.putString(CUSTOMER_NEW, result.getCustomersNew());
                    editor.putString(CUSTOMER_VISIT_OLD, result.getCustomersVisitOld());
                    editor.putString(CUSTOMER_VISIT_NEW, result.getCustomersVisitNew());

                    editor.putString(IPONTS_OLD, result.getIpointsOld());
                    editor.putString(IPONTS_NEW, result.getIpointsNew());
                    editor.putString(RPONTS_OLD, result.getRpointsOld());
                    editor.putString(RPONTS_NEW, result.getRpointsNew());

                    editor.putString(NOOF_TOPUP_OLD, result.getNoOfTxnOld());
                    editor.putString(NOOF_TOPUP_NEW, result.getNoOfTxnNew());
                    editor.putString(NOOF_PAYMENTS_OLD, result.getNoOfReceivesOld());
                    editor.putString(NOOF_PAYMENTS_NEW, result.getNoOfReceivesNew());
                    editor.putString(NOOF_PAYMENTS_NEW, result.getNoOfReceivesNew());

                    editor.putString(TOT_TOPUP_OLD, result.getTotAmntTxnOld());
                    editor.putString(TOT_TOPUP_NEW, result.getTotAmntTxnOld());
                    editor.putString(TOT_PAYMENTS_OLD, result.getTotAmntReceivesOld());
                    editor.putString(TOT_PAYMENTS_NEW, result.getTotAmntReceivesNew());
                    editor.apply();

                });
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {
            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(PerformanceActivity.this, Homenew.class));
        finish();
       // overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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
