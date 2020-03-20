package life.corals.merchant.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import life.corals.merchant.BuildConfig;
import life.corals.merchant.R;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body;
import life.corals.merchant.client.model.InlineResponse200;
import life.corals.merchant.client.model.InlineResponse200Cashcampaigns;
import life.corals.merchant.client.model.InlineResponse200Outlets;
import life.corals.merchant.fcm.FcmConstants;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertNotificationDialog;
import life.corals.merchant.utils.MerchantUpdateModel;
import life.corals.merchant.utils.MySharedPreference;
import life.corals.merchant.utils.ServerMaintainanceModel;
import life.corals.merchant.utils.Util;

import static life.corals.merchant.utils.Constants.CURRENCY_SYMBOL;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.DISPLAY_BUSINESS_NAME;
import static life.corals.merchant.utils.Constants.IS_ADMIN;
import static life.corals.merchant.utils.Constants.IS_LOADED;
import static life.corals.merchant.utils.Constants.MERCHANT_COUNTRY;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_DEVICE_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_HOME_INSIGHTS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_WALLET_BAL;
import static life.corals.merchant.utils.Constants.OUTLET_ID;
import static life.corals.merchant.utils.Constants.TOTAL_CUSTOMERS;
import static life.corals.merchant.utils.Constants.UNIQUE_ID;

public class SplashScreen extends AppCompatActivity {


    private final static String TAG = "SplashScreen";
    private SharedPreferences preferences;
    private SharedPreferences preferences1;
    private MySharedPreference sharedPreference;
    private SharedPreferences insightsHomePreferences;
    private Gson gson;
    private Body body = new Body();
    private String latestVersion;
    private String minimumUpdateVersion;
    private String mesaage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // setTheme(R.style.);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        // getWindow().setStatusBarColor(getResources().getColor(R.color.oldBlue));
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /* getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        preferences = getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        preferences1 = getSharedPreferences(MERCHANT_DEVICE_PREFERENCE, MODE_PRIVATE);

        gson = new Gson();
        sharedPreference = new MySharedPreference(getApplicationContext());
        insightsHomePreferences = getSharedPreferences(MERCHANT_HOME_INSIGHTS_PREFERENCE, MODE_PRIVATE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(FcmConstants.TITLE)
                && bundle.containsKey(FcmConstants.MESSAGE_BODY)
                && bundle.getString(FcmConstants.MESSAGE_BODY) != null) {
            String imgUrl = null;
            if (bundle.get(FcmConstants.IMAGE) != null) {
                imgUrl = bundle.get(FcmConstants.IMAGE).toString();
            }
            AlertNotificationDialog coralsNotificationDialog =
                    new AlertNotificationDialog(SplashScreen.this,
                            false, 0) {

                        @Override
                        public void onNotifyAlertDismiss() {
                            ServerCallApi();
                        }
                    };
            if (bundle.containsKey(FcmConstants.MERCHANT_NAME)) {
                coralsNotificationDialog.setAlertDialogTitle(bundle.get(FcmConstants.MERCHANT_NAME).toString());
            } else {
                coralsNotificationDialog.setAlertDialogTitle(null);
            }
            coralsNotificationDialog.setNotifyBody(bundle.get(FcmConstants.MESSAGE_BODY).toString());
            coralsNotificationDialog.setNotifyTitle(bundle.get(FcmConstants.TITLE).toString());
            coralsNotificationDialog.setNotifyImage(imgUrl);
            coralsNotificationDialog.showNotificationDialog();
        } else {
            ServerCallApi();
        }
    }

    private void ServerCallApi() {
        if (Util.getConnectivityStatusString(this)) {
            try {
                serverMaintainance();
            } catch (Exception e) {
                versionUpdate();
            }
        } else {
            new AlertDialogFailure(SplashScreen.this, "Please turn ON your data connection", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    ServerCallApi();
                }
            };
        }
    }

    private void callApi() {
        if (Util.getConnectivityStatusString(this)) {
            if (preferences1.getString(DEVICE_ID, null) == null) {
                new AlertDialogFailure(SplashScreen.this, "Please contact corals support", "OK", "Device id invalid") {
                    @Override
                    public void onButtonClick() {
                        finish();
                    }
                };
            } else {
                getRequestBody();
                try {
                    loginMerchant(body);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }

        } else {
            new AlertDialogFailure(SplashScreen.this, "Please turn ON your data connection", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callApi();
                }
            };
        }
    }

    private void serverMaintainance() {
        Request request = new Request.Builder()
                .url("https://www.corals.life/configs/m-down.json")
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                versionUpdate();
            }

            @Override
            public void onResponse(final Response response) {
                new Thread(() -> {
                    try {
                        ServerMaintainanceModel serverMaintainanceModel = gson.fromJson(response.body().string(), ServerMaintainanceModel.class);
                        if (serverMaintainanceModel.isAppDown()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showServerDialog(serverMaintainanceModel.getMessageTitle(),
                                            serverMaintainanceModel.getMessageContent());
                                }
                            });
                        } else {
                            try {
                                versionUpdate();
                            } catch (Exception e) {
                                callApi();
                            }

                        }
                    } catch (IOException e) {
                        try {
                            versionUpdate();
                        } catch (Exception e1) {
                            callApi();
                        }
                    }

                }).start();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    public void showServerDialog(String title, String msg) {
        Dialog dialog;
        dialog = new Dialog(SplashScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.update_me_diolog);
        TextView titl = (TextView) dialog.findViewById(R.id.alert_title);
        TextView appname = (TextView) dialog.findViewById(R.id.app_name);
        appname.setVisibility(View.GONE);
        TextView cnt1 = (TextView) dialog.findViewById(R.id.content1);
        cnt1.setVisibility(View.GONE);
        TextView cnt2 = (TextView) dialog.findViewById(R.id.content2);
        titl.setText(title);
        titl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_failed, 0, 0, 0);
        cnt2.setText(msg);
        Button click = (Button) dialog.findViewById(R.id.button);
        click.setText("OK");
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog.show();
    }

    private void versionUpdate() {
        Request request = new Request.Builder()
                .url("https://www.corals.life/configs/m.json")
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callApi();
            }

            @Override
            public void onResponse(final Response response) {
                new Thread(() -> {
                    try {

                        MerchantUpdateModel updateModel = gson.fromJson(response.body().string(), MerchantUpdateModel.class);
                        latestVersion = updateModel.getUpdateVersion();
                        minimumUpdateVersion = updateModel.getMinimumSupportVersion();
                        mesaage = updateModel.getUpdateMessage();
                        String currentVersion = BuildConfig.VERSION_NAME;
                        Double latestDouble = Double.parseDouble(latestVersion);
                        Double currentDouble = Double.parseDouble(currentVersion);
                        Double minimumDouble = Double.parseDouble(minimumUpdateVersion);

                        if (currentDouble.equals(latestDouble)) {
                            //app uptodate
                            runOnUiThread(() -> {
                                //up date no need
                                if (preferences1.getString(DEVICE_ID, null) == null) {
                                    startActivity(new Intent(SplashScreen.this, IntroActivity.class));
                                    finish();
                                } else {
                                    callApi();
                                }
                            });
                        } else if (currentDouble > minimumDouble) {
                            runOnUiThread(() -> {
                                //up date no need
                                if (preferences1.getString(DEVICE_ID, null) == null) {
                                    startActivity(new Intent(SplashScreen.this, IntroActivity.class));
                                    finish();
                                } else {
                                    callApi();
                                }

                            });

                        } else if (currentDouble < minimumDouble) {
                            runOnUiThread(() -> {
                                //up date need
                                showUpdateDialog(mesaage, latestVersion);
                            });
                        } else if (currentDouble.equals(minimumDouble) && updateModel.isIsforceUpdate()) {
                            runOnUiThread(() -> {
                                //up date need
                                showUpdateDialog(mesaage, latestVersion);
                            });
                        } else if (currentDouble.equals(minimumDouble) && !updateModel.isIsforceUpdate()) {
                            runOnUiThread(() -> {
                                // no need to update . its a soft update
                                if (preferences1.getString(DEVICE_ID, null) == null) {
                                    startActivity(new Intent(SplashScreen.this, IntroActivity.class));
                                    finish();
                                } else {
                                    callApi();
                                }
                            });
                        } else {
                            runOnUiThread(() -> unknownversion(latestVersion));
                        }

                    } catch (IOException e) {
                        if (preferences1.getString(DEVICE_ID, null) == null) {
                            startActivity(new Intent(SplashScreen.this, IntroActivity.class));
                            finish();
                        } else {
                            callApi();
                        }
                    }
                }).start();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    public void showUpdateDialog(String msg, String version) {
        Context context = SplashScreen.this;
        Dialog dialog;
        dialog = new Dialog(SplashScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.update_me_diolog);
        TextView versn = dialog.findViewById(R.id.content1);
        versn.setText("Latest version " + version);
        TextView msgtxt = dialog.findViewById(R.id.content2);
        msgtxt.setText(msg);
        Button clickToUpdate = dialog.findViewById(R.id.button);
        String packageName = getApplicationContext().getPackageName();
        clickToUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName + "&hl=en"));
                    context.startActivity(intent);
                } catch (Exception e) {
                    finish();
                }

            }
        });
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    public void unknownversion(String version) {
        new AlertDialogFailure(SplashScreen.this, "Expected version " + version, "OK", "App version not matched") {
            @Override
            public void onButtonClick() {
                finish();
            }
        };
    }


    private String getFcmToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "FCM_Registration_Token: " + token + "  Okay");
        if (token == null) {
            token = FirebaseInstanceId.getInstance().getToken();
        }
        if (token == null) {
            token = FirebaseInstanceId.getInstance().getToken();
        }
        if (token == null) {
            token = FirebaseInstanceId.getInstance().getToken();
        }
        if (token == null) {
            token = FirebaseInstanceId.getInstance().getToken();
        }
        return token;
    }

    private void getRequestBody() {
        body.setDevicepin("4321");
        body.setDeviceid(preferences1.getString(DEVICE_ID, ""));
        body.isoutletsReqd(true);
        body.iscampaignsReqd(true);
        body.maxRecordCount(String.valueOf(1));
        body.notifyInstanceId(getFcmToken());
        Log.d("splas_screeenn", "getRequestBody:" + body);
    }

    private void loginMerchant(Body requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(this);
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.merchantLoginPostAsync(requestBody, new ApiCallback<InlineResponse200>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                runOnUiThread(() -> {
                    if (statusCode == 404) {
                        new AlertDialogFailure(SplashScreen.this, "Campaigns not found", "Close", "Login failed") {

                            @Override
                            public void onButtonClick() {
                                finish();
                            }
                        };
                    } else if (statusCode == 501) {
                        new AlertDialogFailure(SplashScreen.this, "Outlets not found", "Close", "Login failed") {

                            @Override
                            public void onButtonClick() {
                                finish();
                            }
                        };
                    } else {
                        new AlertDialogFailure(SplashScreen.this, "Something went wrong", "Close", "Login failed") {

                            @Override
                            public void onButtonClick() {
                                finish();
                            }
                        };
                    }
                    Log.d("splas_screeenn", "getRequestBody:" + statusCode + "  " + e);
                });
            }

            @Override
            public void onSuccess(InlineResponse200 result, int statusCode, Map<String, List<String>> responseHeaders) {
                if (statusCode == 200) {
                    if (result != null) {
                        SharedPreferences.Editor editor1 = preferences.edit();
                        editor1.clear();
                        editor1.apply();
                        List<InlineResponse200Cashcampaigns> cashcampaignsList = result.getCashcampaigns();
                        List<InlineResponse200Outlets> outletList = result.getOutlets();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(CURRENCY_SYMBOL, result.getCurrencysymbol());
                        editor.putString(MERCHANT_ID, result.getMerId());
                        editor.putString(MERCHANT_WALLET_BAL, result.getWalletBalance());
                        editor.putString(DISPLAY_BUSINESS_NAME, result.getBizdisplayname());
                        editor.putString(IS_ADMIN, String.valueOf(result.isIsadmin()));
                        editor.putString(MERCHANT_COUNTRY, result.getMerCountry());
                        editor.putString(OUTLET_ID, result.getOutletId());
                        editor.putString(TOTAL_CUSTOMERS, result.getTotCustomers());
                        editor.putString(MERCHANT_ID, result.getMerId());
                        Log.d("TOTAL_CUSTOMERS", "onSuccess: " + result.getUniqueId());
                        if (result.getUniqueId() != null) {
                            editor.putString(UNIQUE_ID, result.getUniqueId());
                        }
                        editor.putString(DEVICE_ID, preferences1.getString(DEVICE_ID, ""));
                        sharedPreference.removeCampaignList();
                        sharedPreference.removeOutletList();
                        saveCampaignListToSharedpreference(cashcampaignsList);
                        saveOutletListToSharedpreference(outletList);
                        editor.apply();

                        SharedPreferences.Editor homeLoadEditor = insightsHomePreferences.edit();
                        homeLoadEditor.clear();
                        homeLoadEditor.apply();

                        runOnUiThread(() -> {
                            try {
                                updateTransactionTypeCall();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        });
                    } else {
                        new AlertDialogFailure(SplashScreen.this, "Please try again later!", "Retry", "Login failed") {
                            @Override
                            public void onButtonClick() {
                                callApi();
                            }
                        };
                    }

                } else {
                    new AlertDialogFailure(SplashScreen.this, "Please try again later!", "Retry", "Login failed") {
                        @Override
                        public void onButtonClick() {
                            callApi();
                        }
                    };
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


    private void sucessIntent() {
        SharedPreferences.Editor editor1 = preferences.edit();
        editor1.putString(IS_LOADED, "no");
        editor1.apply();
        startActivity(new Intent(SplashScreen.this, Homenew.class));
        finish();
        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
    }

    private void saveCampaignListToSharedpreference(List<InlineResponse200Cashcampaigns> CampaignList) {
        String jsonScore = gson.toJson(CampaignList);
        sharedPreference.saveCampaignList(jsonScore);
    }

    private void saveOutletListToSharedpreference(List<InlineResponse200Outlets> OutletList) {
        String jsonScore = gson.toJson(OutletList);
        sharedPreference.saveOutletList(jsonScore);
    }

    private void updateTransactionTypeCall() {
        Request request = new Request.Builder()
                .url("https://www.corals.life/configs/params.json")
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sucessIntent();
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                new Thread(() -> {
                    try {
                        int transactionTypeDone = sharedPreference.updateTransactionType(response.body().string());
                        if (transactionTypeDone == 1) {
                            sucessIntent();
                        } else {
                            updateTransactionTypeCall();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });

    }

}