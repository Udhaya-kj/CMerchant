package life.corals.merchant.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.adapter.getCampaignlistAdapter;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.ClientApisApi;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body12;
import life.corals.merchant.client.model.Body13;
import life.corals.merchant.client.model.InlineResponse20010;
import life.corals.merchant.client.model.InlineResponse20010Cashcampaigns;
import life.corals.merchant.client.model.MerchantcampaignppcupdatemCashcampaigns;
import life.corals.merchant.utils.AlertAddCampigns;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertDialogYesNo;
import life.corals.merchant.utils.AlertSuccess;
import life.corals.merchant.utils.AppTimeOutManagerUtil;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.MySharedPreference;
import life.corals.merchant.utils.RecyclerTouchListener;
import life.corals.merchant.utils.Util;

import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.IS_ADMIN;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_INSIGHTS_PREFERENCE;


public class CashCampaigns extends AppCompatActivity {

    private getCampaignlistAdapter campaignListAdapter;

    private Body13 body13 = new Body13();
    private Body12 body12 = new Body12();
    private MySharedPreference sharedPreference;
    private RecyclerView activeRecyclerView;

    private AlertDialogYesNo AlertDialog;
    private SharedPreferences preferences;
    private IntermediateAlertDialog intermediateAlertDialog;
    private AlertAddCampigns alertAddCampigns;
    private AlertDialog.Builder builder;
    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    private RoundedImageView addCampaigns;
    private List<MerchantcampaignppcupdatemCashcampaigns> cashcampaignsList;
    private LinearLayout permissionDenied;

    private List<InlineResponse20010Cashcampaigns> topup;

    private List<MerchantcampaignppcupdatemCashcampaigns> campaignlist;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campaign_setup_one);
        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar_manage_campaign);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        toolbar.setTitle("Top Up Campaigns");
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        preferences = getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        addCampaigns = findViewById(R.id.add_campaigns);
        activeRecyclerView = findViewById(R.id.active_campaign_list_recycler_view);
        permissionDenied = findViewById(R.id.permission_denied);
        appTimeOutManagerUtil = new AppTimeOutManagerUtil(CashCampaigns.this);
        if (preferences.getString(IS_ADMIN, "").equals("false")) {
            addCampaigns.setVisibility(View.GONE);
            activeRecyclerView.setVisibility(View.GONE);
            permissionDenied.setVisibility(View.VISIBLE);
        } else {
            addCampaigns.setVisibility(View.VISIBLE);
            activeRecyclerView.setVisibility(View.VISIBLE);
            permissionDenied.setVisibility(View.GONE);
            findView();
        }
    }

    private void findView() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CashCampaigns.this);
        activeRecyclerView.setLayoutManager(mLayoutManager);
        activeRecyclerView.setItemAnimator(new DefaultItemAnimator());

        sharedPreference = new MySharedPreference(getApplicationContext());
        intermediateAlertDialog = new IntermediateAlertDialog(CashCampaigns.this);
        getlistcallApi();

        activeRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(CashCampaigns.this, activeRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        addCampaigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCampaigns.setClickable(false);
                new AlertAddCampigns(CashCampaigns.this) {
                    @Override
                    public void onOkButtonClick(String amnt, String bns) {
                        cashcampaignsList = new ArrayList<>();
                        MerchantcampaignppcupdatemCashcampaigns response200Cashcampaigns = new MerchantcampaignppcupdatemCashcampaigns();
                        response200Cashcampaigns.setCampaignid(" ");
                        response200Cashcampaigns.setTopupamt(amnt.replace(",", ""));
                        response200Cashcampaigns.setTopupbonus(bns.replace(",", ""));
                        // response200Cashcampaigns.setExpiryDate(date);
                        cashcampaignsList.add(response200Cashcampaigns);

                        new AlertDialogYesNo(CashCampaigns.this, "Confirmation", "Are you sure want to create new campaign?", "No", "Yes") {

                            @Override
                            public void onOKButtonClick() {
                                intermediateAlertDialog = new IntermediateAlertDialog(CashCampaigns.this);
                                updatecallApi();
                            }

                            @Override
                            public void onCancelButtonClick() {
                                addCampaigns.setClickable(true);
                            }
                        };

                    }

                    @Override
                    public void onCancelButtonClick() {
                        addCampaigns.setClickable(true);
                    }
                };
            }
        });
    }

    private void getlistcallApi() {
        if (Util.getConnectivityStatusString(this)) {
            try {
                getRequestBody12();
                getCampaignslist(body12);
            } catch (ApiException e) {
                intermediateAlertDialog.dismissAlertDialog();
                e.printStackTrace();
            }
        } else {
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(CashCampaigns.this, "Please turn ON your data connection", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    getlistcallApi();
                }
            };
        }
    }

    private void getRequestBody12() {
        body12.setCallertype("m");
        body12.setDeviceid(preferences.getString(DEVICE_ID, ""));
        body12.setMerId(preferences.getString(MERCHANT_ID, ""));
        body12.setSessiontoken("notoken");
    }

    private void getCampaignslist(Body12 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(this);
        ClientApisApi clientApisApi = new ClientApisApi();
        clientApisApi.setApiClient(okHttpApiClient.getApiClient());
        clientApisApi.genericCampaignPpcFetchPostAsync(requestBody, new ApiCallback<InlineResponse20010>() {

            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                runOnUiThread(() -> {
                    Log.d("InlineResponse20010", "onFailure: ");
                    intermediateAlertDialog.dismissAlertDialog();
                    new AlertDialogFailure(CashCampaigns.this, "Please try again later", "OK", "Failed") {
                        @Override
                        public void onButtonClick() {
                            startActivity(new Intent(CashCampaigns.this, CashCampaigns.class));
                            finish();
                        }
                    };
                });

            }

            @Override
            public void onSuccess(InlineResponse20010 result, int statusCode, Map<String, List<String>> responseHeaders) {
                runOnUiThread(() -> {
                    Log.d("InlineResponse20010", "onSuccess: ");
                    intermediateAlertDialog.dismissAlertDialog();
                    if (statusCode == 200) {
                        if (result != null) {
                            campaignListAdapter = new getCampaignlistAdapter(result.getCashcampaigns(), CashCampaigns.this);
                            activeRecyclerView.setAdapter(campaignListAdapter);
                            activeRecyclerView.setVisibility(View.VISIBLE);
                            campaignListAdapter.notifyDataSetChanged();
                        } else {
                            new AlertDialogFailure(CashCampaigns.this, "Please try again later", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(CashCampaigns.this, CashCampaigns.class));
                                    finish();
                                }
                            };
                        }


                    } else {
                        new AlertDialogFailure(CashCampaigns.this, "Please try again later", "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(CashCampaigns.this, CashCampaigns.class));
                                finish();
                            }
                        };
                    }

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

    private void updatecallApi() {
        if (Util.getConnectivityStatusString(this)) {
            try {
                getRequestBody13();
                   updateCampaigns(body13);
            } catch (ApiException e) {
                intermediateAlertDialog.dismissAlertDialog();
                e.printStackTrace();
            }
        } else {
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(CashCampaigns.this, "Please turn ON your data connection", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    updatecallApi();
                }
            };
        }
    }

    private void getRequestBody13() {
        body13.setUpdate(false);
        body13.setCashcampaigns(cashcampaignsList);
        body13.setDeviceid(preferences.getString(DEVICE_ID, ""));
        body13.setMerId(preferences.getString(MERCHANT_ID, ""));
        body13.setSessiontoken("notoken");
    }

    private void updateCampaigns(Body13 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(this);
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.merchantCampaignPpcUpdateMPostAsync(requestBody, new ApiCallback<Void>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                runOnUiThread(() -> {

                    intermediateAlertDialog.dismissAlertDialog();
                    if (statusCode == 405) {
                        new AlertDialogFailure(CashCampaigns.this, "Campaign already exist", "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(CashCampaigns.this, CashCampaigns.class));
                                finish();
                            }
                        };
                    } else {
                        new AlertDialogFailure(CashCampaigns.this, "Please try again later", "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(CashCampaigns.this, CashCampaigns.class));
                                finish();
                            }
                        };
                    }

                });

            }

            @Override
            public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
                runOnUiThread(() -> {
                    if (statusCode == 200) {
                        intermediateAlertDialog.dismissAlertDialog();
                        new AlertSuccess(CashCampaigns.this, "Done !", "Campaigns created successfully") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(CashCampaigns.this, CashCampaigns.class));
                                finish();
                            }
                        };

                    } else {
                        intermediateAlertDialog.dismissAlertDialog();
                        new AlertDialogFailure(CashCampaigns.this, "Please try again later", "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(CashCampaigns.this, CashCampaigns.class));
                                finish();
                            }
                        };
                    }

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
        startActivity(new Intent(CashCampaigns.this, Homenew.class));
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
