package life.corals.merchant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body16;
import life.corals.merchant.client.model.InlineResponseSummary;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AppTimeOutManagerUtil;
import life.corals.merchant.utils.BottomSheetDialogg;
import life.corals.merchant.utils.BusinessQRGenerator;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.ParamProperties;
import life.corals.merchant.utils.Util;

import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.DISPLAY_BUSINESS_NAME;
import static life.corals.merchant.utils.Constants.INSIGHTS_HOME_LOAD_COUNT;
import static life.corals.merchant.utils.Constants.INSIGHTS_HOME_LOAD_COUNT_STR;
import static life.corals.merchant.utils.Constants.MERCHANT_COUNTRY;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_HOME_INSIGHTS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.OUTLET_ID;
import static life.corals.merchant.utils.Constants.QR_CODE;
import static life.corals.merchant.utils.Constants.TOTAL_CUSTOMERS;
import static life.corals.merchant.utils.Constants.TOT_CUST_STATUS;
import static life.corals.merchant.utils.Constants.TOT_SALE;
import static life.corals.merchant.utils.Constants.TOT_SALE_AMOUNT;
import static life.corals.merchant.utils.Constants.TOT_SALE_STATUS;
import static life.corals.merchant.utils.Constants.TOT_TRAFFIC;
import static life.corals.merchant.utils.Constants.TOT_TRAFFIC_STATUS;
import static life.corals.merchant.utils.Constants.UNIQUE_ID;

public class Homenew extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SharedPreferences preferences;
    private SharedPreferences insightsHomePreferences;
    private Body16 body = new Body16();
    private IntermediateAlertDialog intermediateAlertDialog;
    private AppTimeOutManagerUtil appTimeOutManagerUtil;

    private TextView totalCustomers, totalCustomersSts, totalSaleStatus, trafficStatus;
    private TextView trafficImage, totalSaleImage;
    // private TextView totalSaleAmnt;
    private LinearLayout purchaseLayout;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homenew);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        findView();
    }

    private void findView() {
        preferences = getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);

        insightsHomePreferences = getSharedPreferences(MERCHANT_HOME_INSIGHTS_PREFERENCE, MODE_PRIVATE);
        //  totalSaleAmnt = findViewById(R.id.saleAmnt);
        mSwipeRefreshLayout = findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        purchaseLayout = findViewById(R.id.purchaseLayout);

        totalCustomers = findViewById(R.id.totalCustomers);
        totalCustomersSts = findViewById(R.id.totalCustomersStatus);
        totalSaleStatus = findViewById(R.id.totalPurchaseStatus);
        totalSaleImage = findViewById(R.id.saleImage);
        trafficStatus = findViewById(R.id.trafficStatus);
        trafficImage = findViewById(R.id.trafficeImage);

        appTimeOutManagerUtil = new AppTimeOutManagerUtil(Homenew.this);

        LinearLayout mainLayout = findViewById(R.id.main_layout);
        mainLayout.setBackgroundColor(getResources().getColor(R.color.white));
        RoundedImageView menu = findViewById(R.id.menu);
        LinearLayout menuLayout = findViewById(R.id.menu_layout);
        menuLayout.setBackgroundColor(getResources().getColor(R.color.white));
        LinearLayout issue = findViewById(R.id.issue);
        LinearLayout topup = findViewById(R.id.topup);
        LinearLayout redeem = findViewById(R.id.redeem);
        LinearLayout receive = findViewById(R.id.receive);

        TextView merchantName = findViewById(R.id.merchant_name_title);
        merchantName.setText(preferences.getString(DISPLAY_BUSINESS_NAME, ""));
        AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // issue.startAnimation(buttonClick);
                BottomSheetDialogg coralsBottomSheetDialog = new BottomSheetDialogg(Homenew.this);
                coralsBottomSheetDialog.showBottomSheetDialog();
            }
        });

        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // issue.startAnimation(buttonClick);
                startActivity(new Intent(Homenew.this, AwardCashbackActivity.class));
                finish();
            }
        });
        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // issue.startAnimation(buttonClick);
                startActivity(new Intent(Homenew.this, CashTopupScreenScanner.class));
                finish();
            }
        });

        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // issue.startAnimation(buttonClick);
                startActivity(new Intent(Homenew.this, RedeeemPoints.class));
                finish();
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // issue.startAnimation(buttonClick);
                startActivity(new Intent(Homenew.this, ReceivePaymentScreenOne.class));
                finish();
            }
        });

        menuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogg coralsBottomSheetDialog = new BottomSheetDialogg(Homenew.this);
                coralsBottomSheetDialog.showBottomSheetDialog();
            }
        });

        String cust;
        if (preferences.getString(TOTAL_CUSTOMERS, "").length() == 1) {
            cust = "00" + preferences.getString(TOTAL_CUSTOMERS, "");
        } else if (preferences.getString(TOTAL_CUSTOMERS, "").length() == 2) {
            cust = "0" + preferences.getString(TOTAL_CUSTOMERS, "");
        } else {
            cust = preferences.getString(TOTAL_CUSTOMERS, "");
        }
        totalCustomers.setText(cust);

        int count = insightsHomePreferences.getInt(INSIGHTS_HOME_LOAD_COUNT_STR, INSIGHTS_HOME_LOAD_COUNT);
        if (count <= 0) {
            intermediateAlertDialog = new IntermediateAlertDialog(Homenew.this);
            callAPI();
        } else {
            // totalSaleAmnt.setText(insightsHomePreferences.getString(TOT_SALE_AMOUNT, ""));
            totalCustomersSts.setText(insightsHomePreferences.getString(TOT_CUST_STATUS, ""));
            trafficStatus.setText(insightsHomePreferences.getString(TOT_TRAFFIC_STATUS, ""));
            totalSaleStatus.setText(insightsHomePreferences.getString(TOT_SALE_STATUS, ""));

            if (insightsHomePreferences.getString(TOT_TRAFFIC_STATUS, "").equals("Increasing") ||
                    insightsHomePreferences.getString(TOT_TRAFFIC_STATUS, "").equals("Maintaining")) {
                trafficImage.setBackground(getDrawable(R.drawable.ic_arrow_up));
            } else if (insightsHomePreferences.getString(TOT_TRAFFIC_STATUS, "").equals("Decreasing")) {
                trafficImage.setBackground(getDrawable(R.drawable.ic_arrow_down));
            } else {
                trafficImage.setText("-");
            }

            if (insightsHomePreferences.getString(TOT_SALE_STATUS, "").equals("Increasing") ||
                    insightsHomePreferences.getString(TOT_SALE_STATUS, "").equals("Maintaining")) {
                totalSaleImage.setBackground(getDrawable(R.drawable.ic_arrow_up));
            } else if (insightsHomePreferences.getString(TOT_SALE_STATUS, "").equals("Decreasing")) {
                totalSaleImage.setBackground(getDrawable(R.drawable.ic_arrow_down));
            } else {
                totalSaleImage.setText("-");
            }

        }
        Log.d("Id-----", "findView: "+ preferences.getString(UNIQUE_ID, "")+","+preferences.getString(OUTLET_ID, ""));
            ParamProperties paramProperties = new ParamProperties();
            String countryName = paramProperties.getProperty(preferences.getString(MERCHANT_COUNTRY, ""), ParamProperties.COUNTRY_NAME);
            BusinessQRGenerator GenQr = null;
           Log.d("Par__amProperties", "findView: "+ preferences.getString(UNIQUE_ID, ""));
            try {
                GenQr = new BusinessQRGenerator(countryName
                        , preferences.getString(MERCHANT_COUNTRY, "")
                        , preferences.getString(DISPLAY_BUSINESS_NAME, "")
                        , preferences.getString(OUTLET_ID, "")
                        , preferences.getString(UNIQUE_ID, ""));
            } catch (Exception e) {
                e.printStackTrace();
            }

            SharedPreferences.Editor editor1 = preferences.edit();
            editor1.putString(QR_CODE, Objects.requireNonNull(GenQr).getQR_CODE_STRING());
            editor1.apply();


    }

    @Override
    public void onRefresh() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //intermediateAlertDialog = new IntermediateAlertDialog(Homenew.this);
                callAPI();
            }
        }, 2000);
    }

    private void callAPI() {

        if (Util.getConnectivityStatusString(this)) {
            try {
                getRequestBody();
                getInsightsSummary(body);
            } catch (Exception e) {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                intermediateAlertDialog.dismissAlertDialog();
                new AlertDialogFailure(this, "Please try again later", "OK", "Something went wrong") {
                    @Override
                    public void onButtonClick() {
                        startActivity(new Intent(Homenew.this, Homenew.class));
                        finish();
                    }
                };
            }
        } else {
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(Homenew.this, "Please turn ON your data connection ", "Retry", "No internet Connection !") {
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
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (statusCode == 404) {
                        new AlertDialogFailure(Homenew.this, "Please try again later", "Close", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                                //startActivity(new Intent(Homenew.this, PerformanceActivity.class));
                                finish();
                            }
                        };
                    } else if (statusCode == 406) {
                        new AlertDialogFailure(Homenew.this, "Please try again later", "Close", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                               // startActivity(new Intent(Homenew.this, PerformanceActivity.class));
                                finish();
                            }
                        };
                    } else {
                        new AlertDialogFailure(Homenew.this, "Please try again later", "Close", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                                //startActivity(new Intent(Homenew.this, PerformanceActivity.class));
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
                    if (intermediateAlertDialog!=null){
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    String Crntdate = Util.getCountryCurrentTimestamp(preferences.getString(MERCHANT_COUNTRY, ""));

                    //customer calculation
                    long oldCust = Long.parseLong(result.getCustomersOld());
                    long new1Cust = Long.parseLong(result.getCustomersNew());
                    long lastMonthresult = oldCust / 30;
                    long currentResult = new1Cust / Integer.parseInt(Crntdate);
                    String CustStatus;
                    if (lastMonthresult == 0 || currentResult == 0) {
                        CustStatus = "No enough data";
                    } else if (lastMonthresult > currentResult) {
                        CustStatus = "Decreasing";
                    } else if (lastMonthresult < currentResult) {
                        CustStatus = "Increasing";
                    } else if (lastMonthresult == currentResult) {
                        CustStatus = "Maintaining";
                    } else {
                        CustStatus = "no enough data";
                    }
                    totalCustomersSts.setText(CustStatus);

                    //customer visit calculation
                    String TrafficStatus;
                    long oldCustVt = Long.parseLong(result.getCustomersVisitOld());
                    long new1CustVt = Long.parseLong(result.getCustomersVisitNew());
                    long lastMonthresultCV = oldCustVt / 30;
                    long currentResultCV = new1CustVt / Integer.parseInt(Crntdate);

                    if (lastMonthresultCV == 0 || currentResultCV == 0) {
                        TrafficStatus = "No enough data";
                        trafficImage.setText("-");
                    } else if (lastMonthresultCV > currentResultCV) {
                        TrafficStatus = "Decreasing";
                        trafficImage.setBackground(getDrawable(R.drawable.ic_arrow_down));
                    } else if (lastMonthresultCV < currentResultCV) {
                        TrafficStatus = "Increasing";
                        trafficImage.setBackground(getDrawable(R.drawable.ic_arrow_up));
                    } else if (lastMonthresultCV == currentResultCV) {
                        TrafficStatus = "Maintaining";
                        trafficImage.setBackground(getDrawable(R.drawable.ic_arrow_up));
                    } else {
                        TrafficStatus = "no enough data";
                        trafficImage.setText("-");
                    }
                    trafficStatus.setText(TrafficStatus);


                    //issue points calculation
                    String saleStatus;

                    long oldIp = Long.parseLong(result.getIpointsOld());
                    long new1Ip = Long.parseLong(result.getIpointsNew());
                    long lastMonthresultIp = oldIp / 30;
                    long currentResultIp = new1Ip / Integer.parseInt(Crntdate);
                    //  totalSaleAmnt.setText(result.getIpointsNew());
                    if (lastMonthresultIp == 0 || currentResultIp == 0) {
                        saleStatus = "No enough data";
                        totalSaleImage.setText("-");
                    } else if (lastMonthresultIp > currentResultIp) {
                        saleStatus = "Decreasing";
                        totalSaleImage.setBackground(getDrawable(R.drawable.ic_arrow_down));
                    } else if (lastMonthresultIp < currentResultIp) {
                        saleStatus = "Increasing";
                        totalSaleImage.setBackground(getDrawable(R.drawable.ic_arrow_up));
                    } else if (lastMonthresultIp == currentResultIp) {
                        saleStatus = "Maintaining";
                        totalSaleImage.setBackground(getDrawable(R.drawable.ic_arrow_up));
                    } else {
                        saleStatus = "No enough data";
                        totalSaleImage.setText("-");
                    }
                    totalSaleStatus.setText(saleStatus);

                    SharedPreferences.Editor editor = insightsHomePreferences.edit();
                    editor.putString(TOT_CUST_STATUS, CustStatus);
                    editor.putString(TOT_TRAFFIC, String.valueOf(lastMonthresultCV));
                    editor.putString(TOT_TRAFFIC_STATUS, TrafficStatus);
                    editor.putString(TOT_SALE, String.valueOf(currentResultIp));
                    editor.putString(TOT_SALE_STATUS, saleStatus);
                    editor.putString(TOT_SALE_AMOUNT, result.getIpointsNew());
                    editor.putInt(INSIGHTS_HOME_LOAD_COUNT_STR, 1);
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
    protected void onResume() {
        super.onResume();
        appTimeOutManagerUtil.onResumeApp();
    }

    @Override
    protected void onPause() {
        super.onPause();
        appTimeOutManagerUtil.onPauseApp();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
