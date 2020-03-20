package life.corals.merchant.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.HistoryHomePage;
import life.corals.merchant.activity.Homenew;
import life.corals.merchant.adapter.IssuePointsListAdapter;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body24;
import life.corals.merchant.client.model.InlineResponse20013Customersrec;
import life.corals.merchant.client.model.InlineResponse20018;
import life.corals.merchant.client.model.InlineResponse20018Customers;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.RecyclerTouchListener;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.DISPLAY_BUSINESS_NAME;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;

public class HistryIssuePointsToday extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    private SharedPreferences preferences;
    Body24 body = new Body24();
    private IntermediateAlertDialog intermediateAlertDialog;
    private IssuePointsListAdapter issuePointsListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private TextView empty;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_history_issue_points_history_today, container, false);

        Activity activity = this.getActivity();
        swipeRefreshLayout = rootView.findViewById(R.id.issue_points_today_history_swipe_refresh);
        preferences = Objects.requireNonNull(activity).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);

        recyclerView = rootView.findViewById(R.id.issue_points_history_today_recycler);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        empty = rootView.findViewById(R.id.empty);

        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                InlineResponse20018Customers issueList = issuePointsListAdapter.getTopupList().get(position);
                String sms = preferences.getString(DISPLAY_BUSINESS_NAME, "") + "- " + issueList.getPointsIssued() + " points issued. Total Points: " + issueList.getPointsBal() + " (Exp.: " + issueList.getExpiryDate() + "). Redeem points with our app https://www.corals.life/get/";//The message you want to text to the phone
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", issueList.getMobileNo(), null));
                smsIntent.putExtra("sms_body", sms);
                startActivity(smsIntent);
            }
        }));
        callApi();
        return rootView;
    }

    private void callApi() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            getRequestBody();
            try {
                issuePointsHistory(body);
            } catch (ApiException e) {
                intermediateAlertDialog.dismissAlertDialog();
                e.printStackTrace();
            }
        } else {
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(getActivity(), "Please turn ON data connection", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callApi();
                }
            };
        }

    }

    private void getRequestBody() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date c = cal.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayfromdatetime = df.format(c);
        Date now = Calendar.getInstance().getTime();
        String todaytodatetime = df.format(now);

        body.setCustMobileNo("");
        body.setDeviceId(preferences.getString(DEVICE_ID, ""));
        body.setMerId(preferences.getString(MERCHANT_ID, ""));
        body.setSessiontoken("");
        body.setFromDate(todayfromdatetime);
        body.setToDate(todaytodatetime);
        body.setCustMobileNo("9");
        body.setMerId(preferences.getString(MERCHANT_ID, ""));

    }

    private void issuePointsHistory(Body24 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.genericCbpointsGethistoryPostAsync(requestBody, new ApiCallback<InlineResponse20018>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("issue_points_today", "run: "+e+" "+statusCode);
                        intermediateAlertDialog.dismissAlertDialog();
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if (statusCode == 404) {
                            new AlertDialogFailure(getActivity(), "Customers not found", "OK", "") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), HistoryHomePage.class));
                                    getActivity().finish();
                                    getActivity().overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        } else if (statusCode == 501) {
                            new AlertDialogFailure(getActivity(), "Your are  not issued cashback", "OK", "") {
                                @Override
                                public void onButtonClick() {

                                    startActivity(new Intent(getActivity(), HistoryHomePage.class));
                                    getActivity().finish();
                                    getActivity().overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        } else {
                            new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), HistoryHomePage.class));
                                    getActivity().finish();
                                    getActivity().overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        }

                    }

                });
            }

            @Override
            public void onSuccess(final InlineResponse20018 result, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    intermediateAlertDialog.dismissAlertDialog();
                    if (statusCode == 200) {
                        if (result != null) {
                            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.getCustomers() != null) {
                                        setToRecyclerView(result.getCustomers());
                                    } else {
                                        empty.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);
                                    }

                                }
                            });

                        } else {
                            empty.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } else {
                        new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                                Objects.requireNonNull(getActivity()).finish();
                                getActivity().overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
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

    private void setToRecyclerView(List<InlineResponse20018Customers> transactionList) {
        issuePointsListAdapter = new IssuePointsListAdapter(transactionList, getActivity());
        recyclerView.setAdapter(issuePointsListAdapter);
        issuePointsListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getActivity(), HistoryHomePage.class));
        Objects.requireNonNull(getActivity()).finish();
        getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        return true;
    }

    @Override
    public void onRefresh() {
        try {
            issuePointsHistory(body);
        } catch (ApiException e) {
            e.printStackTrace();
            new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Something went wrong") {
                @Override
                public void onButtonClick() {
                    startActivity(new Intent(getActivity(), HistoryHomePage.class));
                    Objects.requireNonNull(getActivity()).finish();
                    getActivity().overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                }
            };
        }
    }
}
