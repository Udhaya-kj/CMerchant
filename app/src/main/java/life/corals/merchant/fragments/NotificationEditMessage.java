package life.corals.merchant.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

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
import static life.corals.merchant.utils.Constants.DATE_TIME;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.DISPLAY_BUSINESS_NAME;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.MESSAGE;
import static life.corals.merchant.utils.Constants.TITLE;
import static life.corals.merchant.utils.Constants.TYPE;

public class NotificationEditMessage extends BaseFragment {
    private String title;
    private String msg;
    private String ansendDays = "";
    private String type = "";

    private String scheduldatetime = "";
    private String group = "";
    private String isActive = "";
    private String reqId = "";
    private String createDt = "";
    private String imageUrl = "";
    private String isSent = "";


    Body27 body = new Body27();

    private SharedPreferences preferences;
    private TextView typeofCustomertv;
    private IntermediateAlertDialog intermediateAlertDialog;

    private EditText titleEdt;
    private EditText msgEdt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_notification_edit, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar_edit);
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            if (getActivity() != null && activity.getSupportActionBar() != null && getActivity().getResources() != null) {
                activity.getSupportActionBar().setHomeButtonEnabled(true);
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activity.getSupportActionBar().setHomeAsUpIndicator(getActivity().getResources().getDrawable(R.drawable.ic_arrow_left));
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            }
        }

        titleEdt = rootView.findViewById(R.id.notification_title);
        msgEdt = rootView.findViewById(R.id.notification_content);
        titleEdt.requestFocus();
        Button confirm = (Button) rootView.findViewById(R.id.confirm_button);

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);

        typeofCustomertv = rootView.findViewById(R.id.type_of_customer);
        TextView msgSendTimetv = rootView.findViewById(R.id.time_of_customer);
       // TextView name = (TextView) rootView.findViewById(R.id.notification_dialog_title);

        title = Objects.requireNonNull(getArguments()).getString(TITLE);
        msg = Objects.requireNonNull(getArguments()).getString(MESSAGE);
        ansendDays = Objects.requireNonNull(getArguments()).getString(ABSEND_DAYS);
        type = Objects.requireNonNull(getArguments()).getString(TYPE);
        scheduldatetime = Objects.requireNonNull(getArguments()).getString("SEND_DT");
        isActive = Objects.requireNonNull(getArguments()).getString("IS_ACTIVE");
        createDt = Objects.requireNonNull(getArguments()).getString("CREATE_DT");
        imageUrl = Objects.requireNonNull(getArguments()).getString("IMAGE_URL");
        isSent = Objects.requireNonNull(getArguments()).getString("IS_SENT");
        reqId = Objects.requireNonNull(getArguments()).getString("NOTIFY_REQ_ID");
        Log.d("scheduldatetime", "onCreateView: "+ isActive);
        titleEdt.setText(title);
        msgEdt.setText(msg);

        if (type.equals("A")) {
            String msg = "Message set up for After ::ansence:: days of from last visit";
            typeofCustomertv.setText(msg.replace("::ansence::", ansendDays));
            msgSendTimetv.setText("This Message will be send daily at 7.00 am");
        } else {
            typeofCustomertv.setText("All Customers");
            Timestamp time = Timestamp.valueOf(scheduldatetime);
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy hh:mm a");
            msgSendTimetv.setText("This message will be send at " + sdfDate.format(time));
        }
       // name.setText(preferences.getString(DISPLAY_BUSINESS_NAME, ""));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialogYesNo(getActivity(), "Confirmation", "Are you sure want to update message ?", "No", "Yes") {

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
            body.setIsActive(true);
        } else {
            body.setIsActive(false);
        }
        body.setIsSent(true);
        body.setIsSent(true);
        body.setDeviceId(preferences.getString(DEVICE_ID, ""));
        body.setMerId(preferences.getString(MERCHANT_ID, ""));
        body.setSessiontoken("no");
        body.setMsgText(msgEdt.getText().toString());
        body.setMsgTitle(titleEdt.getText().toString());
        body.setNotifyReqid(reqId);
        body.setSendDatetime(scheduldatetime);

        Log.d("updateSchedule", "getRequestBody: " + body);
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

                            new AlertSuccess(getActivity(), "Done !", "Successfully updated") {
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
       /* Bundle bundle = new Bundle();
        bundle.putString("TYPE", type);
        bundle.putString("CREATE_DT", createDt);
        bundle.putString("NOTIFY_REQ_ID", reqId);
        bundle.putString("IMAGE_URL", imageUrl);
        bundle.putString("IS_ACTIVE", isActive);
        bundle.putString("IS_SENT", isSent);
        bundle.putString("TITLE", title);
        bundle.putString("MSG_TEXT", msg);
        bundle.putString("VALUE", ansendDays);
        bundle.putString(DATE_TIME, scheduldatetime);*/
        NotificationSchedulesList editMessage = new NotificationSchedulesList();
       // editMessage.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.send_notification_frame_layout, editMessage);
        fragmentTransaction.commit();

        return true;
    }
}
