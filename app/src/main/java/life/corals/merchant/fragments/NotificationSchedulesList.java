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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.Homenew;
import life.corals.merchant.adapter.NotificationListAdapter;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body26;
import life.corals.merchant.client.model.InlineResponse20019;
import life.corals.merchant.client.model.InlineResponse20019Listofmessages;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertSuccess;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.RecyclerTouchListener;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;

public class NotificationSchedulesList extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private Toolbar toolbar;
    Body26 body = new Body26();
    private IntermediateAlertDialog intermediateAlertDialog;

    private SharedPreferences preferences;
    private NotificationListAdapter mAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_view_sent_notification, container, false);
        toolbar = rootView.findViewById(R.id.toolbar_enter_msg);
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);

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
        swipeRefreshLayout = rootView.findViewById(R.id.schedule_list_swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = rootView.findViewById(R.id.view_sent_notification);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                InlineResponse20019Listofmessages List = mAdapter.getScheduledList().get(position);
                Timestamp createtime = Timestamp.valueOf(List.getCreatedDttm());
                Timestamp sendtime = Timestamp.valueOf(List.getSendDatetime());
                String isACtive = String.valueOf(List.isIsActive());
                String isSent = List.isIsSent().toString();
                Bundle bundle = new Bundle();
                bundle.putString("SEND_DT", List.getSendDatetime());
                bundle.putString("TYPE", List.getRequestType());
                bundle.putString("VALUE", List.getRequestValue());
                bundle.putString("CREATE_DT", List.getCreatedDttm());
                bundle.putString("TITLE", List.getMsgTitle());
                bundle.putString("MSG_TEXT", List.getMsgText());
                bundle.putString("NOTIFY_REQ_ID", List.getNotifyReqid());
                bundle.putString("IMAGE_URL", List.getMsgImgUrl());
                bundle.putString("IS_ACTIVE", isACtive);
                bundle.putString("IS_SENT", isSent);
                NotificationViewShceduledMsg fragment1 = new NotificationViewShceduledMsg();
                fragment1.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.replace(R.id.send_notification_frame_layout, fragment1);
                fragmentTransaction.commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        callAPI();
        return rootView;
    }

    private void callAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                getRequestBody();
                getListofScheduleMessage(body);
            } catch (Exception e) {

                intermediateAlertDialog.dismissAlertDialog();
                new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Something went wrong") {
                    @Override
                    public void onButtonClick() {
                        startActivity(new Intent(getActivity(), Homenew.class));
                        Objects.requireNonNull(getActivity()).finish();
                    }
                };
            }
        } else {
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(getActivity(), "Please turn ON your data connection ", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callAPI();
                }
            };
        }
    }

    private void getRequestBody() {
        body.setDeviceId(preferences.getString(DEVICE_ID, ""));
        body.setMerId(preferences.getString(MERCHANT_ID, ""));
        body.setIsSent(true);
        body.setSessiontoken("");
    }

    private void getListofScheduleMessage(Body26 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());
        merchantApisApi.merchantFetchSchedulednotificationPostAsync(requestBody, new ApiCallback<InlineResponse20019>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        intermediateAlertDialog.dismissAlertDialog();
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        if (statusCode == 404) {
                            new AlertDialogFailure(getActivity(), "You are not  valid Merchant \n", "OK", "") {
                                @Override
                                public void onButtonClick() {
                                    NotificationHome searchresult = new NotificationHome();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                    fragmentTransaction.replace(R.id.send_notification_frame_layout, searchresult);
                                    fragmentTransaction.commit();
                                }
                            };
                        } else if (statusCode == 406) {
                            new AlertDialogFailure(getActivity(), "Invalid Title or Message", "OK", "") {
                                @Override
                                public void onButtonClick() {
                                    NotificationHome searchresult = new NotificationHome();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                    fragmentTransaction.replace(R.id.send_notification_frame_layout, searchresult);
                                    fragmentTransaction.commit();
                                }
                            };
                        } else {
                            new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    NotificationHome searchresult = new NotificationHome();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                    fragmentTransaction.replace(R.id.send_notification_frame_layout, searchresult);
                                    fragmentTransaction.commit();
                                }
                            };
                        }
                    }
                });

            }

            @Override
            public void onSuccess(InlineResponse20019 result, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Inline__sponse20019", "run: "+result);
                        intermediateAlertDialog.dismissAlertDialog();
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if (statusCode == 200) {
                            if (result != null) {
                                mAdapter = new NotificationListAdapter(getActivity(), result.getListofmessages());
                                recyclerView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                new AlertSuccess(getActivity(), "Sorry !", "No Scheduled Messages available") {
                                    @Override
                                    public void onButtonClick() {
                                        NotificationHome searchresult = new NotificationHome();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                        fragmentTransaction.replace(R.id.send_notification_frame_layout, searchresult);
                                        fragmentTransaction.commit();
                                    }
                                };
                            }
                        } else {
                            new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    NotificationHome searchresult = new NotificationHome();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                    fragmentTransaction.replace(R.id.send_notification_frame_layout, searchresult);
                                    fragmentTransaction.commit();
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
        NotificationHome fragment1 = new NotificationHome();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.send_notification_frame_layout, fragment1);
        fragmentTransaction.commit();
        return true;
    }

    @Override
    public void onRefresh() {
        try {
            getRequestBody();
            getListofScheduleMessage(body);
        } catch (Exception e) {
            new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Something went wrong") {
                @Override
                public void onButtonClick() {
                    NotificationHome fragment1 = new NotificationHome();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                    fragmentTransaction.replace(R.id.send_notification_frame_layout, fragment1);
                    fragmentTransaction.commit();
                }
            };
        }
    }
}
