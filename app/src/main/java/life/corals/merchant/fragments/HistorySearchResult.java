package life.corals.merchant.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
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
import life.corals.merchant.utils.AlertSuccess;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.MySharedPreference;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.MOBILE_NUMBER;

public class HistorySearchResult extends BaseFragment {

    private View rootView;
    private HistoryListAdapter mAdapter;
    Body15 body = new Body15();
    private RecyclerView recyclerView;
    private String mobilenumber;
    private String deviceId;
    private SwipeRefreshLayout mainLayout;
    private MySharedPreference sharedPreference;
    private SharedPreferences preferences;
    private TextView noTransactions;
    private TextView mobileNumberTvw;
    private Gson gson;
    private List<InlineResponse20012Transactions> cashcampaignsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HashMap<String, String> cashcampaignHashmap = new HashMap<>();
    private InputMethodManager imm;
    private IntermediateAlertDialog intermediateAlertDialog;
    private FrameLayout frameLayout;
    private LinearLayout mobileLayout;
    private TextView backtoSearch;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.history_search_result, container, false);
        imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);

        gson = new Gson();
        preferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        sharedPreference = new MySharedPreference(getActivity().getApplicationContext());
        noTransactions = rootView.findViewById(R.id.no_transactions);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        frameLayout = rootView.findViewById(R.id.frame_layout_history_search_result);
        swipeRefreshLayout = rootView.findViewById(R.id.transaction_search_history_layout);
        mobileNumberTvw = rootView.findViewById(R.id.mobile_number_history);
        backtoSearch = rootView.findViewById(R.id.back_to_search);
        mobileLayout = rootView.findViewById(R.id.mobile_layout);
        mobilenumber = Objects.requireNonNull(getArguments()).getString(MOBILE_NUMBER);
        String monbilenumberMsg = "HistoryTopup for ".concat(Objects.requireNonNull(mobilenumber));
        mobileNumberTvw.setText(monbilenumberMsg);

        backtoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistorySearch searchresult = new HistorySearch();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.replace(R.id.history_base_frame_layout, searchresult);
                fragmentTransaction.commit();
            }
        });
        callApi();

        return rootView;
    }

    private void callApi() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            getRequestBody();
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

    private void getRequestBody() {
        body.setDeviceid(preferences.getString(DEVICE_ID, ""));
        body.setFromdate("");
        body.setTodate("");
        body.setMerId(preferences.getString(MERCHANT_ID, ""));
        body.setMobileNumber(mobilenumber);
        body.setRestrictbycustomer(true);
        body.setRestrictCount(BigDecimal.valueOf(50));
        body.setSessiontoken("");
        body.callertype("m");
        body.iscbredeemlist(false);
        body.setRedeemId("");
    }

    private void transactionHistory(Body15 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
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
                        intermediateAlertDialog.dismissAlertDialog();
                        if (statusCode == 204) {
                            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    intermediateAlertDialog.dismissAlertDialog();
                                    new AlertSuccess(getActivity(), "This mobile number does not having any customer", "No result found") {
                                        @Override
                                        public void onButtonClick() {
                                            HistorySearch searchresult = new HistorySearch();
                                            FragmentManager fragmentManager = getFragmentManager();
                                            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                            fragmentTransaction.replace(R.id.history_base_frame_layout, searchresult);
                                            fragmentTransaction.commit();
                                        }
                                    };
                                }
                            });

                        } else {
                            new AlertDialogFailure(getActivity(), "Please try again later!", "Ok", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), Homenew.class));
                                }
                            };
                        }

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
                                noTransactions.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                mobileLayout.setVisibility(View.VISIBLE);
                                backtoSearch.setVisibility(View.VISIBLE);
                                if (swipeRefreshLayout.isRefreshing()) {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                                intermediateAlertDialog.dismissAlertDialog();

                                cashcampaignsList = result.getTransactions();
                                    List<InlineResponse20012Transactions> transactionsList = result.getTransactions();
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
                                        mobileLayout.setVisibility(View.GONE);
                                        backtoSearch.setVisibility(View.GONE);
                                        noTransactions.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);
                                        new AlertDialogFailure(getActivity(), "No transactions found for this number", "OK", "No results found") {
                                            @Override
                                            public void onButtonClick() {
                                                HistorySearch searchresult = new HistorySearch();
                                                FragmentManager fragmentManager = getFragmentManager();
                                                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                                fragmentTransaction.replace(R.id.history_base_frame_layout, searchresult);
                                                fragmentTransaction.commit();
                                            }
                                        };
                                    }

                            } else {
                                if (swipeRefreshLayout.isRefreshing()) {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                                intermediateAlertDialog.dismissAlertDialog();
                                noTransactions.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                                mobileLayout.setVisibility(View.GONE);
                                backtoSearch.setVisibility(View.GONE);

                                new AlertDialogFailure(getActivity(), "Customer Not found", "OK", "No results") {
                                    @Override
                                    public void onButtonClick() {
                                        HistorySearch searchresult = new HistorySearch();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                        fragmentTransaction.replace(R.id.history_base_frame_layout, searchresult);
                                        fragmentTransaction.commit();
                                    }
                                };

                            }

                        } else {
                            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (swipeRefreshLayout.isRefreshing()) {
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                    intermediateAlertDialog.dismissAlertDialog();
                                    new AlertDialogFailure(getActivity(), "Please try again later!", "Retry", "Something went wrong") {
                                        @Override
                                        public void onButtonClick() {
                                            callApi();
                                        }
                                    };
                                }

                            });

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
    public void onStart() {
        super.onStart();

        imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getActivity(), Homenew.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        Objects.requireNonNull(getActivity()).finish();
        return true;
    }
}
