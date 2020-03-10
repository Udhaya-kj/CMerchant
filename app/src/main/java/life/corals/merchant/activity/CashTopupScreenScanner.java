package life.corals.merchant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import life.corals.merchant.R;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiClient;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.ClientApisApi;
import life.corals.merchant.client.model.Body7;
import life.corals.merchant.client.model.InlineResponse2007;
import life.corals.merchant.client.model.InlineResponse200Cashcampaigns;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AppTimeOutManagerUtil;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.MerchantToast;
import life.corals.merchant.utils.MySharedPreference;
import life.corals.merchant.utils.QRTopUpNoteBottomSheet;
import life.corals.merchant.utils.TopUpQRScannerUtils;
import life.corals.merchant.utils.Util;

import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;

public class CashTopupScreenScanner extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CashTopupScreenScanner";

    private static final String node1 = "26";

    private static final String node2 = "92";
    private SharedPreferences preferences;
    private List<InlineResponse200Cashcampaigns> topup;
    private static final String OUTLET_ID = "93";
    private MySharedPreference sharedPreference;
    SurfaceView surfaceView;

    private TextView scanResult;

    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    private IntermediateAlertDialog intermediateAlertDialog;

    private String merchantId = null;
    private String campaignID = null;
    private String topUpAmount = null;
    private String bonusAmount = null;
    private String outletID = null;
    private String authTokenVAlue = null;
    private CoordinatorLayout layout;

    private QRTopUpNoteBottomSheet qrTopUpNoteBottomSheet;
    private Gson gson;
    private TopUpQRScannerUtils topUpQRScannerUtils;

    private MerchantToast coralsToast;

    private InlineResponse200Cashcampaigns cashcampaign = null;

    // private AppTimeOutManagerUtil appTimeOutManagerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_topup_screen_two);

        appTimeOutManagerUtil = new AppTimeOutManagerUtil(CashTopupScreenScanner.this);
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black_color));
        }
        preferences = getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        sharedPreference = new MySharedPreference(getApplicationContext());
        merchantId = preferences.getString(MERCHANT_ID, "");
        outletID = preferences.getString(OUTLET_ID, "");
        hideActionBar();
        gson = new Gson();
        layout = findViewById(R.id.topupscreen_two);
        surfaceView = findViewById(R.id.surfaceView);
        scanResult = findViewById(R.id.result_top_up_qr_scan);
        MaterialButton button = findViewById(R.id.cancel_scan);
        button.setOnClickListener(this);


        qrTopUpNoteBottomSheet = new QRTopUpNoteBottomSheet(this, topUpAmount, bonusAmount) {
            @Override
            public void onCancelButtonCLick() {
                onBackPressed();
            }

            @Override
            public void onProceedButtonClick() {
                callApi();
            }
        };

        topUpQRScannerUtils = new TopUpQRScannerUtils(surfaceView, this, merchantId) {
            @Override
            public void parsedScanData(HashMap<String, String> stringHashMap, String outletId, String authToken, String campaignId) {
                outletID = outletId;
                campaignID = campaignId;
                authTokenVAlue = authToken;
                String jsonScore = sharedPreference.getCampaignList();
                Type type = new TypeToken<List<InlineResponse200Cashcampaigns>>() {
                }.getType();
                topup = gson.fromJson(jsonScore, type);
                if (topup == null || topup.size() <= 0) {
                    new AlertDialogFailure(CashTopupScreenScanner.this, "Campaigns not found", "OK", "Failed") {
                        @Override
                        public void onButtonClick() {
                            onBackPressed();
                        }
                    };
                } else {
                    for (InlineResponse200Cashcampaigns inlineResponse200Cashcampaigns : topup) {
                        if (inlineResponse200Cashcampaigns.getCampaignid().equals(campaignId)) {
                            cashcampaign = inlineResponse200Cashcampaigns;
                        }
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        topUpAmount = cashcampaign.getTopupamt();
                        bonusAmount = cashcampaign.getTopupbonus();
                        qrTopUpNoteBottomSheet.showQRTopUpNoteAlertDialog(topUpAmount, bonusAmount);
                    }
                });
            }

            @Override
            public void onFailureScan(final String result) {
                qrTopUpNoteBottomSheet.dismissOnProceed();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(CashTopupScreenScanner.this, result, "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                onBackPressed();
                            }
                        };
                    }
                });
            }

        };
    }

    private void callApi() {
        if (Util.getConnectivityStatusString(CashTopupScreenScanner.this)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (outletID != null && authTokenVAlue != null) {
                        qrTopUpNoteBottomSheet.dismissOnProceed();
                        callWebService(outletID, authTokenVAlue);
                    } else {
                        coralsToast = new MerchantToast(CashTopupScreenScanner.this);
                        coralsToast.showMessage("Invalid QR code - 9", null);
                    }
                }
            });

        } else {
            new AlertDialogFailure(CashTopupScreenScanner.this, "Please turn ON your data connection", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callApi();
                }
            };
        }
    }

    private void hideActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void callWebService(final String outletId, final String authToken) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    intermediateAlertDialog = new IntermediateAlertDialog(CashTopupScreenScanner.this);
                    callTopUpApi(outletId, authToken);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CashTopupScreenScanner.this, Homenew.class));
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }

    private void callTopUpApi(String outletId, String authToken) throws ApiException {


        SharedPreferences preferences = getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);

        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(this);
        ApiClient apiClient = okHttpApiClient.getApiClient();
        if (apiClient == null) {
            return;
        }
        ClientApisApi clientApisApi = new ClientApisApi();
        clientApisApi.setApiClient(apiClient);

        Body7 body7 = new Body7();
        body7.setCampaignId(campaignID);
        body7.setCustId("");
        body7.setMerId(merchantId);
        body7.setOutletId(outletId);
        body7.setTopupAmount("");
        body7.setTopupBonus("");
        body7.setSessiontoken("");
        body7.setDeviceId(preferences.getString(DEVICE_ID, null));
        body7.setTokenValue(authToken);


        clientApisApi.customerTopupPostAsync(body7, new ApiCallback<InlineResponse2007>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {

                runOnUiThread(() -> new AlertDialogFailure(CashTopupScreenScanner.this, "Please try again later", "OK", "Top up failed") {
                    @Override
                    public void onButtonClick() {
                        onBackPressed();
                    }
                });
            }

            @Override
            public void onSuccess(InlineResponse2007 result, int statusCode, Map<String, List<String>> responseHeaders) {
                if (statusCode == 200) {
                    if (result != null) {
                        successIntent(result.getWalletBal());
                    } else {
                        new AlertDialogFailure(CashTopupScreenScanner.this, "Please try again later", "OK", "Top up failed") {
                            @Override
                            public void onButtonClick() {
                                onBackPressed();
                            }
                        };
                    }

                } else {
                    new AlertDialogFailure(CashTopupScreenScanner.this, "Please try again later", "OK", "Top up failed") {
                        @Override
                        public void onButtonClick() {
                            finish();
                        }
                    };
                }
            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {
                if (done && intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
            }
        });

    }

    private void successIntent(String wallBal) {
        startActivity(new Intent(this, CashTopupScreenSuccess.class)
                .putExtra("WAL-BAL-AMOUNT", wallBal)
                .putExtra("TOP-UP-AMOUNT", topUpAmount)
                .putExtra("BONUS-AMOUNT", bonusAmount)
        );
        finish();
        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
    }

    @Override
    protected void onPause() {
        super.onPause();
        appTimeOutManagerUtil.onPauseApp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        appTimeOutManagerUtil.onResumeApp();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_scan) {
            super.onBackPressed();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
