package life.corals.merchant.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.Homenew;
import life.corals.merchant.activity.MyCustomers;
import life.corals.merchant.adapter.CustomerListAdapter;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body16;
import life.corals.merchant.client.model.InlineResponse20013;
import life.corals.merchant.client.model.InlineResponse20013Customersrec;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertSuccess;
import life.corals.merchant.utils.DateUtils;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.RecyclerTouchListener;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;

public class CustomerListOne extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private SharedPreferences preferences;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    Body16 body = new Body16();

    private CustomerListAdapter customerListAdapter;
    private IntermediateAlertDialog intermediateAlertDialog;
    private String notifyId = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_customerlist_one, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar_mycustomers_one);
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
        recyclerView = rootView.findViewById(R.id.cutomer_list_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout = rootView.findViewById(R.id.my_customers_swipe_refresh);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                InlineResponse20013Customersrec customerList = customerListAdapter.getCustomerList().get(position);
                Bundle bundle = new Bundle();

                bundle.putString("Cust_name", customerList.getCustName());
                bundle.putString("Cust_id", customerList.getCustId());
                bundle.putString("Cust_country", customerList.getCustCountryCode());
                bundle.putString("Cust_wall_bal", customerList.getCustWalletBal());
                bundle.putString("Cust_wall_bal_exp", customerList.getCustWalletExpDt());
                bundle.putString("Cust_wall_cb_bal", customerList.getCustCbPointsBalance());
                bundle.putString("Cust_mobile", customerList.getCustMobile());
                bundle.putString("Cust_last_topup", customerList.getCustLastTopUp());
                bundle.putString("notify_id", customerList.getCustNotificationId());
                long mTimestamp = 0;

                String DATE_FORMAT = "  dd MMM yyyy HH:mm";
                try {
                    mTimestamp = DateUtils.getTimeStampFromDateTime(customerList.getCustJoinDt(), DATE_FORMAT);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String jdtv ="-";
                if (mTimestamp!=0){
                    jdtv = String.valueOf(mTimestamp);
                }
                bundle.putString("join_date", jdtv);


                bundle.putString("Cust_last_visit", customerList.getCustLastVisitDt());


                bundle.putString("Cust_wall_cb_bal_exp", customerList.getCustCbPointsExpdt());

                CustomerViewDetails fragment1 = new CustomerViewDetails();
                fragment1.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.replace(R.id.customer_frame_layout, fragment1);
                fragmentTransaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        swipeRefreshLayout.setOnRefreshListener(this);

        callAPI();
        return rootView;
    }

    private void callAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                getRequestBody();
                CustomerList(body);
            } catch (Exception e) {
                intermediateAlertDialog.dismissAlertDialog();
                new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                    @Override
                    public void onButtonClick() {
                        startActivity(new Intent(getActivity(), MyCustomers.class));
                        Objects.requireNonNull(getActivity()).finish();
                        getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                    }
                };
            }
        } else {
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(getActivity(), "Please turn ON your Internet connection ", "Retry", "No internet Connection !") {
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
    }

    private void CustomerList(Body16 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.merchantCustomersListPostAsync(requestBody, new ApiCallback<InlineResponse20013>() {

            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (intermediateAlertDialog != null) {
                            intermediateAlertDialog.dismissAlertDialog();
                        }
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                                Objects.requireNonNull(getActivity()).finish();
                                getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                            }
                        };
                    }
                });

            }

            @Override
            public void onSuccess(InlineResponse20013 result, int statusCode, Map<String, List<String>> responseHeaders) {
                if (statusCode == 200) {
                    if (result != null) {
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (intermediateAlertDialog != null) {
                                    intermediateAlertDialog.dismissAlertDialog();
                                }
                                if (swipeRefreshLayout.isRefreshing()) {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                                if (result.getCustomersrec() != null) {
                                    final List<InlineResponse20013Customersrec> customerList = result.getCustomersrec();
                                    customerListAdapter = new CustomerListAdapter(Objects.requireNonNull(getActivity()), customerList);
                                    recyclerView.setAdapter(customerListAdapter);
                                } else {
                                    new AlertSuccess(getActivity(), "No result found", "Does not have any Customers") {
                                        @Override
                                        public void onButtonClick() {
                                            startActivity(new Intent(getActivity(), Homenew.class));
                                            Objects.requireNonNull(getActivity()).finish();
                                            getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);

                                        }

                                    };
                                }

                            }
                        });
                    } else {
                        new AlertDialogFailure(getActivity(), "Does not have any Customers", "OK", "No result found") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                                Objects.requireNonNull(getActivity()).finish();
                                getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                            }
                        };

                    }
                } else {
                    new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                        @Override
                        public void onButtonClick() {
                            startActivity(new Intent(getActivity(), Homenew.class));
                            Objects.requireNonNull(getActivity()).finish();
                            getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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

    @Override
    public void onRefresh() {
        try {
            getRequestBody();
            CustomerList(body);
        } catch (Exception e) {
            new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                @Override
                public void onButtonClick() {
                    startActivity(new Intent(getActivity(), Homenew.class));
                    Objects.requireNonNull(getActivity()).finish();
                    getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                }
            };
        }
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getActivity(), Homenew.class));
        Objects.requireNonNull(getActivity()).finish();
       // getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        return true;
    }
}
