package life.corals.merchant.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.Homenew;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body27;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertDialogYesNo;
import life.corals.merchant.utils.AlertSuccess;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.ABSEND_DAYS;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.DISPLAY_BUSINESS_NAME;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.MESSAGE;
import static life.corals.merchant.utils.Constants.TITLE;
import static life.corals.merchant.utils.Constants.TYPE;

public class NotificationViewShceduledMsg extends BaseFragment {

    private View rootView;
    private Toolbar toolbar;
    private String type = "", value = "", createdt = "", title = "", msgtext = "", reqId = "", sendDt = "", imageUrl = "", isActive = "", isSent = "";
    private TextView reqvaluetv, createDtv, titleTvw, textBodyTvw, sendDtTv;

    private RoundedImageView msgIcon;

    private MaterialButton deactivate, edit;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String dateStr;
    private String timestr;
    Body27 body = new Body27();
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences preferences;
    private String newTitle = "";
    private String newMsgBody = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_notification_make_operations, container, false);
        toolbar = rootView.findViewById(R.id.toolbar_view_make_opeation);
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

        type = Objects.requireNonNull(getArguments()).getString("TYPE");
        value = Objects.requireNonNull(getArguments()).getString("VALUE");
        createdt = Objects.requireNonNull(getArguments()).getString("CREATE_DT");
        title = Objects.requireNonNull(getArguments()).getString("TITLE");
        msgtext = Objects.requireNonNull(getArguments()).getString("MSG_TEXT");
        imageUrl = Objects.requireNonNull(getArguments()).getString("IMAGE_URL");
        reqId = Objects.requireNonNull(getArguments()).getString("NOTIFY_REQ_ID");
        sendDt = Objects.requireNonNull(getArguments()).getString("SEND_DT");
        isSent = Objects.requireNonNull(getArguments()).getString("IS_SENT");
        isActive = Objects.requireNonNull(getArguments()).getString("IS_ACTIVE");
       // int maxLength = 50;
       // et_add1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        reqvaluetv = rootView.findViewById(R.id.req_value);
        edit = rootView.findViewById(R.id.edit_btn);
        deactivate = rootView.findViewById(R.id.deactivate_btn);
        createDtv = rootView.findViewById(R.id.create_date);
        titleTvw = rootView.findViewById(R.id.notification_title);
        textBodyTvw = rootView.findViewById(R.id.notification_content);
        sendDtTv = rootView.findViewById(R.id.send_date);
        TextView notificationName = rootView.findViewById(R.id.notification_dialog_title);
        notificationName.setText(preferences.getString(DISPLAY_BUSINESS_NAME, ""));


        // titleTvw.setScroller(new Scroller(getActivity()));
        //  titleTvw.setMaxLines(2);
        //  titleTvw.setVerticalScrollBarEnabled(true);
        //  titleTvw.setMovementMethod(new ScrollingMovementMethod());

        //  textBodyTvw.setScroller(new Scroller(getActivity()));
        // textBodyTvw.setMaxLines(8);
        // textBodyTvw.setVerticalScrollBarEnabled(true);
        //  textBodyTvw.setMovementMethod(new ScrollingMovementMethod());

        msgIcon = rootView.findViewById(R.id.msg_icon);
        if (imageUrl != null) {
            msgIcon.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(imageUrl)
                    .centerCrop()
                    .placeholder(this.getResources().getDrawable(R.drawable.ic_failed))
                    .into(msgIcon);
        }

        if (type.equals("A")) {
            String a = value.replace(">", "");
            reqvaluetv.setText("Not visited for more than " + a + " days");
        } else if (type.equals("L")) {
            reqvaluetv.setText("All Customers");
        } else {
            reqvaluetv.setVisibility(View.GONE);
        }

        if (isActive.equals("true")) {
            deactivate.setVisibility(View.VISIBLE);
        } else {
            deactivate.setVisibility(View.GONE);
        }

        titleTvw.setText(title);
        textBodyTvw.setText(msgtext);
        Timestamp time = Timestamp.valueOf(createdt);
        Timestamp sndtime = Timestamp.valueOf(sendDt);
        SimpleDateFormat createDate = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        SimpleDateFormat scheduledDate = new SimpleDateFormat("dd MMM yyyy");
        createDtv.setText("Created Date: " + createDate.format(time));

        sendDtTv.setText("Scheduled Date: " + scheduledDate.format(sndtime));
        deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a;
                if (isActive.equals("true")) {
                    a = "Are you sure want  to Deactivate this Message?";
                } else {
                    a = "Are you sure want  to Activate this Message?";
                }
                new AlertDialogYesNo(getActivity(), "Confirmation", a, "No", "Yes") {
                    @Override
                    public void onOKButtonClick() {
                        callUpdateAPI();
                    }

                    @Override
                    public void onCancelButtonClick() {

                    }
                };

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(TITLE, title);
                bundle.putString(MESSAGE, msgtext);
                bundle.putString(ABSEND_DAYS, value);
                bundle.putString(TYPE, type);
                bundle.putString("SEND_DT", sendDt);
                bundle.putString("IS_ACTIVE", isActive);
                bundle.putString("IS_SENT", isSent);
                bundle.putString("NOTIFY_REQ_ID", reqId);
                bundle.putString("CREATE_DT", createdt);
                bundle.putString("IMAGE_URL", imageUrl);
                NotificationEditMessage preview = new NotificationEditMessage();
                preview.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.replace(R.id.send_notification_frame_layout, preview);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

    private void callUpdateAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                getRequestBody();
                updateScheduleMessage(body);
            } catch (Exception e) {
                intermediateAlertDialog.dismissAlertDialog();
                new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
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
                    callUpdateAPI();
                }
            };
        }
    }

    private void getRequestBody() {
        if (isActive.equals("true")) {
            body.setIsActive(false);
        } else {
            body.setIsActive(true);
        }
        body.setDeviceId(preferences.getString(DEVICE_ID, ""));
        body.setMerId(preferences.getString(MERCHANT_ID, ""));
        body.setSessiontoken("no");
        body.setMsgText(msgtext);
        body.setMsgTitle(title);
        body.setIsSent(true);
        body.setNotifyReqid(reqId);
        body.setSendDatetime(sendDt);
        body.setCreateDatetime(createdt);
    }

    private void updateScheduleMessage(Body27 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());
        merchantApisApi.merchantUpdateSchedulednotificationPostAsync(requestBody, new ApiCallback<Void>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        intermediateAlertDialog.dismissAlertDialog();
                        if (statusCode == 404) {
                            new AlertDialogFailure(getActivity(), "You are not a valid Merchant", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    NotificationSchedulesList searchresult = new NotificationSchedulesList();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                    fragmentTransaction.replace(R.id.send_notification_frame_layout, searchresult);
                                    fragmentTransaction.commit();
                                }
                            };
                        } else if (statusCode == 406) {
                            new AlertDialogFailure(getActivity(), "Notify Instance Id not Match", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    NotificationSchedulesList searchresult = new NotificationSchedulesList();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                    fragmentTransaction.replace(R.id.send_notification_frame_layout, searchresult);
                                    fragmentTransaction.commit();
                                }
                            };
                        } else {
                            new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    NotificationSchedulesList searchresult = new NotificationSchedulesList();
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
            public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        intermediateAlertDialog.dismissAlertDialog();
                        if (statusCode == 200) {

                            new AlertSuccess(getActivity(), "Done !", "Successfully Deactivated") {
                                @Override
                                public void onButtonClick() {
                                    NotificationSchedulesList searchresult = new NotificationSchedulesList();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                    fragmentTransaction.replace(R.id.send_notification_frame_layout, searchresult);
                                    fragmentTransaction.commit();
                                }
                            };
                        } else {
                            new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    NotificationSchedulesList searchresult = new NotificationSchedulesList();
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
        NotificationSchedulesList fragment1 = new NotificationSchedulesList();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.send_notification_frame_layout, fragment1);
        fragmentTransaction.commit();
        return true;
    }

}
