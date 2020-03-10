package life.corals.merchant.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import life.corals.merchant.client.model.InlineResponse20018;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.MOBILE_NUMBER;

public class HistryIssuePointsSearchResult extends BaseFragment {
    private SharedPreferences preferences;
    Body24 body = new Body24();
    private IntermediateAlertDialog intermediateAlertDialog;
    private RecyclerView recyclerView;
    private IssuePointsListAdapter issuepointsListAdapter;
    private String mble;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_history_issue_points_history_search_result, container, false);

        Activity activity = this.getActivity();
        preferences = Objects.requireNonNull(activity).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        mble = Objects.requireNonNull(getArguments()).getString(MOBILE_NUMBER);
        recyclerView = rootView.findViewById(R.id.issue_points_history_recycler);

        TextView backtosearch = rootView.findViewById(R.id.back_to_search);

        backtosearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistryIssuePointsSearchBase fragment1 = new HistryIssuePointsSearchBase();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.replace(R.id.issue_points_base_frame_layout, fragment1);
                fragmentTransaction.commit();
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());

        callApi();
        return rootView;
    }

    private void callApi() {
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
           new AlertDialogFailure(getActivity(), "Please turn ON your data connection", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callApi();
                }
            };
        }

    }

    private void getRequestBody() {
        body.setCustMobileNo(mble);
        body.setDeviceId(preferences.getString(DEVICE_ID, ""));
        body.setMerId(preferences.getString(MERCHANT_ID, ""));
        body.setFromDate("");
        body.setToDate("");
        body.setSessiontoken("");
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
                        intermediateAlertDialog.dismissAlertDialog();

                        if (statusCode == 404) {
                           new AlertDialogFailure(getActivity(), "Customer not found", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    HistryIssuePointsSearchBase fragment1 = new HistryIssuePointsSearchBase();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                    fragmentTransaction.replace(R.id.issue_points_base_frame_layout, fragment1);
                                    fragmentTransaction.commit();
                                    Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                                }
                            };
                        } else if (statusCode == 501) {
                           new AlertDialogFailure(getActivity(), "Your are not issued cashback for this number", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    HistryIssuePointsSearchBase fragment1 = new HistryIssuePointsSearchBase();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                    fragmentTransaction.replace(R.id.issue_points_base_frame_layout, fragment1);
                                    fragmentTransaction.commit();
                                    Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                                }
                            };
                        } else {
                          new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
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

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (statusCode == 200) {
                            if (result != null) {
                                //backtosearch.setVisibility(View.VISIBLE);
                                intermediateAlertDialog.dismissAlertDialog();
                                issuepointsListAdapter = new IssuePointsListAdapter(result.getCustomers(), getActivity());
                                recyclerView.setAdapter(issuepointsListAdapter);
                                recyclerView.setVisibility(View.VISIBLE);
                                issuepointsListAdapter.notifyDataSetChanged();
                            } else {
                               // backtosearch.setVisibility(View.GONE);
                                intermediateAlertDialog.dismissAlertDialog();
                             new AlertDialogFailure(getActivity(), "No data found for this mobile number", "OK", "No results found") {
                                    @Override
                                    public void onButtonClick() {
                                        HistryIssuePointsSearchBase fragment1 = new HistryIssuePointsSearchBase();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                        fragmentTransaction.replace(R.id.issue_points_base_frame_layout, fragment1);
                                        fragmentTransaction.commit();
                                        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                                    }
                                };

                            }
                        } else {
                            intermediateAlertDialog.dismissAlertDialog();
                           new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), Homenew.class));
                                    Objects.requireNonNull(getActivity()).finish();
                                    getActivity().overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
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

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getActivity(), HistoryHomePage.class));
        Objects.requireNonNull(getActivity()).finish();
        getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        return true;
    }


}
