package life.corals.merchant.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.HistoryHomePage;
import life.corals.merchant.activity.Homenew;
import life.corals.merchant.adapter.HistoryListAdapter;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body15;
import life.corals.merchant.client.model.InlineResponse20012;
import life.corals.merchant.client.model.InlineResponse20012Transactions;
import life.corals.merchant.client.model.InlineResponse200Outlets;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.MySharedPreference;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;

public class HistoryPrevious extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private HistoryListAdapter mAdapter;
    private Body15 body = new Body15();
    private FrameLayout frameLayout;
    private RecyclerView recyclerView;
    private String deviceId;
    private MySharedPreference sharedPreference;
    private SharedPreferences preferences;
    private TextView noTransactions;
    private Gson gson;
    private List<InlineResponse20012Transactions> transactionsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HashMap<String, String> cashcampaignHashmap = new HashMap<>();
    private IntermediateAlertDialog intermediateAlertDialog;
    private Activity activity;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.history_previous, container, false);
        activity = this.getActivity();
        preferences = Objects.requireNonNull(activity).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        gson = new Gson();
        preferences = activity.getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        noTransactions = rootView.findViewById(R.id.no_transactions);
        sharedPreference = new MySharedPreference(activity.getApplicationContext());
        intermediateAlertDialog = new IntermediateAlertDialog(activity);
        swipeRefreshLayout = rootView.findViewById(R.id.transaction_previous_history_layout);
        frameLayout = rootView.findViewById(R.id.frame_layout_history_previos);

        getRequestBody();
        callApi();
        swipeRefreshLayout.setOnRefreshListener(this);
        return rootView;
    }

    private void callApi() {
        if (Util.getConnectivityStatusString(activity)) {
            try {
                transactionHistory(body);
            } catch (ApiException e) {
                intermediateAlertDialog.dismissAlertDialog();
                e.printStackTrace();
            }
        } else {
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(getActivity(), "Please turn ON your data connection", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callApi();
                }
            };
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getRequestBody() {
      /*  Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = df.format(c);

        LocalDate now = LocalDate.now();
        LocalDate earlier = now.minusYears(1);
        String lastYearDate = earlier.getYear() + "-" + earlier.getMonthValue() + "-" + earlier.getDayOfMonth() + " 00:00:01";
*/
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = df.format(c);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, -1);
        Date previousyear = cal.getTime();
        String lastYearDate = df.format(previousyear);

        body.setCustomerid("");
        body.setDeviceid(preferences.getString(DEVICE_ID, ""));
        body.setFromdate(lastYearDate);
        body.setTodate(currentDate);
        body.setMerId(preferences.getString(MERCHANT_ID, ""));
        body.setMobileNumber("9");
        body.setRestrictbycustomer(false);
        body.setRestrictCount(BigDecimal.valueOf(50));
        body.setSessiontoken("");
        body.callertype("m");
        body.iscbredeemlist(false);
        body.setRedeemId("");

    }

    private void transactionHistory(Body15 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(activity);
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.genericTransactionsFetchPostAsync(requestBody, new ApiCallback<InlineResponse20012>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        new AlertDialogFailure(activity, "Please try again later", "OK", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(activity, Homenew.class));
                                activity.finish();
                            }
                        };
                    }
                });

            }

            @Override
            public void onSuccess(final InlineResponse20012 result, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (statusCode == 200) {
                            if (result != null) {
                                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (swipeRefreshLayout.isRefreshing()) {
                                            swipeRefreshLayout.setRefreshing(false);
                                        }
                                        intermediateAlertDialog.dismissAlertDialog();
                                        transactionsList = result.getTransactions();
                                        if (transactionsList != null) {
                                            List<InlineResponse200Outlets> outLets = getOutlets();
                                            for (int a = 0; a < transactionsList.size(); a++) {
                                                InlineResponse20012Transactions transactionsListFromResponse = transactionsList.get(a);
                                                for (int i = 0; i < outLets.size(); i++) {
                                                    InlineResponse200Outlets inlineResponse200Outlets = outLets.get(i);
                                                    if (inlineResponse200Outlets.getOutletid().equals(transactionsListFromResponse.getOutletid())) {
                                                        cashcampaignHashmap.put(transactionsListFromResponse.getOutletid(), inlineResponse200Outlets.getOutletname());
                                                    }
                                                }
                                            }
                                            setToRecyclerView(result.getTransactions());
                                        } else {
                                            noTransactions.setVisibility(View.VISIBLE);
                                            recyclerView.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            } else {
                                noTransactions.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                        } else {
                            new AlertDialogFailure(activity, "Please try again later", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(activity, Homenew.class));
                                    activity.finish();
                                }
                            };
                        }

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

    private void setToRecyclerView(List<InlineResponse20012Transactions> transactionList) {
        mAdapter = new HistoryListAdapter(getActivity(), transactionList, cashcampaignHashmap);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private List<InlineResponse200Outlets> getOutlets() {
        String jsonScore1 = sharedPreference.getOutletList();
        Type type = new TypeToken<List<InlineResponse200Outlets>>() {
        }.getType();
        List<InlineResponse200Outlets> outletList1 = gson.fromJson(jsonScore1, type);
        if (outletList1 == null) {
            outletList1 = new ArrayList<>();
        }

        return outletList1;
    }

    @Override
    public void onRefresh() {
        try {
            transactionHistory(body);
        } catch (ApiException e) {
            e.printStackTrace();
            new AlertDialogFailure(activity, "Please try again later", "OK", "Something went wrong") {
                @Override
                public void onButtonClick() {
                    startActivity(new Intent(activity, HistoryHomePage.class));
                    activity.finish();
                }
            };
        }
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getActivity(), HistoryHomePage.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        Objects.requireNonNull(getActivity()).finish();
        return true;
    }
}
