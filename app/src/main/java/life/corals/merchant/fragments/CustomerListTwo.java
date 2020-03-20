package life.corals.merchant.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import static life.corals.merchant.utils.Constants.CUSTOMER_ID;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.MOBILE_NUMBER;

public class CustomerListTwo extends BaseFragment {

    Body15 body = new Body15();
    private View rootView;
    private IntermediateAlertDialog intermediateAlertDialog;
    private String mobile;
    private SharedPreferences preferences;
    private FrameLayout frameLayout;
    private List<InlineResponse20012Transactions> cashcampaignsList;
    private HashMap<String, String> cashcampaignHashmap = new HashMap<>();
    private TextView noTransactions;
    private RecyclerView recyclerView;
    private String customerId;
    private InputMethodManager imm;
    private HistoryListAdapter mAdapter;
    private MySharedPreference sharedPreference;
    private Gson gson;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_customer_list_two, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar_mycustomers_two);
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            if (getActivity() != null && activity.getSupportActionBar() != null && getActivity().getResources() != null) {
                activity.getSupportActionBar().setHomeButtonEnabled(true);
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activity.getSupportActionBar().setHomeAsUpIndicator(getActivity().getResources().getDrawable(R.drawable.ic_arrow_left));
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        }

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        TextView custId = rootView.findViewById(R.id.customer_id_history);
        frameLayout = rootView.findViewById(R.id.frame_layout_customer_list);
        noTransactions = rootView.findViewById(R.id.no_transactions);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        gson = new Gson();
        preferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        sharedPreference = new MySharedPreference(getActivity().getApplicationContext());

        mobile = Objects.requireNonNull(getArguments()).getString(MOBILE_NUMBER);
        customerId = Objects.requireNonNull(getArguments()).getString(CUSTOMER_ID);

        custId.setText("Customer ID:  " + customerId);
        imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        callApi();
        return rootView;
    }

    private void callApi() {

        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            getRequestBody();
            try {
                intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
                transactionHistory(body);
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

        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = df.format(c);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, -1);
        Date previousyear = cal.getTime();
        String lastYearDate = df.format(previousyear);
        body.setDeviceid(preferences.getString(DEVICE_ID, ""));
        body.setFromdate(lastYearDate);
        body.setTodate(currentDate);
        body.setMerId(preferences.getString(MERCHANT_ID, ""));
        body.setMobileNumber(mobile);
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

                        intermediateAlertDialog.dismissAlertDialog();
                        new AlertDialogFailure(getActivity(), "Please try again later!", "Ok", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                            }
                        };
                    }
                });

            }

            @Override
            public void onSuccess(final InlineResponse20012 result, int statusCode, Map<String, List<String>> responseHeaders) {
                if (statusCode == 204) {
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            intermediateAlertDialog.dismissAlertDialog();

                        }
                    });

                } else if (statusCode == 200) {
                    if (result != null) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                intermediateAlertDialog.dismissAlertDialog();
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
                                    noTransactions.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.GONE);
                                }

                            }
                        });
                    } else {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                intermediateAlertDialog.dismissAlertDialog();

                                noTransactions.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                new AlertSuccess(getActivity(), "This Customer not having any transactions", "No result found") {
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
                    }
                } else {
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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
        Bundle bundle = new Bundle();
        CustomerListOne fragment1 = new CustomerListOne();
        fragment1.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.customer_frame_layout, fragment1);
        fragmentTransaction.commit();
        return true;
    }

}
