package life.corals.merchant.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body25;
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
import static life.corals.merchant.utils.Constants.TEMP_CODE;
import static life.corals.merchant.utils.Constants.TITLE;
import static life.corals.merchant.utils.Constants.TOTAL_CUSTOMERS;

public class NotificationPreview extends BaseFragment {
    private IntermediateAlertDialog intermediateAlertDialog;
    private String title;
    private Body25 body25 = new Body25();
    private Body27 body27 = new Body27();
    private String msg;
    private String ansendDays = "";

    private String scheduldatetime = "";
    private String group = "";
    private String isSent = "";
    private int tempCode = 0;

    private SharedPreferences preferences;
    private TextView typeofCustomertv;

    private TextView msgSendTimetv;
    private TextView titleEt;
    private TextView msgEt;


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_notification_preview, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar_preview);
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

        msgSendTimetv = rootView.findViewById(R.id.msg_send_time);
        titleEt = rootView.findViewById(R.id.notification_title);
        msgEt = rootView.findViewById(R.id.notification_content);
        MaterialButton confirm = rootView.findViewById(R.id.confirm_button);
        MaterialButton close = rootView.findViewById(R.id.close_button);

        titleEt.setEnabled(false);
        msgEt.setEnabled(false);

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);

        typeofCustomertv = rootView.findViewById(R.id.type_of_customer);

        TextView name = (TextView) rootView.findViewById(R.id.notification_dialog_title);

        tempCode = Objects.requireNonNull(getArguments()).getInt(TEMP_CODE);
        if (tempCode == 1) {
            title = Objects.requireNonNull(getArguments()).getString(TITLE);
            msg = Objects.requireNonNull(getArguments()).getString(MESSAGE);
            ansendDays = Objects.requireNonNull(getArguments()).getString(ABSEND_DAYS);
            String msg = "Message set up for after ::ansence:: days of from last visit";
            typeofCustomertv.setText(msg.replace("::ansence::", ansendDays));
            msgSendTimetv.setText("This Message will be send every day");
        } else if (tempCode == 11) {
            title = Objects.requireNonNull(getArguments()).getString(TITLE);
            msg = Objects.requireNonNull(getArguments()).getString(MESSAGE);
            ansendDays = Objects.requireNonNull(getArguments()).getString(ABSEND_DAYS);
            String msg = "Message set up for after ::ansence:: days of from last visit";
            typeofCustomertv.setText(msg.replace("::ansence::", ansendDays));
            msgSendTimetv.setText("This Message will be send every day");
        } else {
            scheduldatetime = Objects.requireNonNull(getArguments()).getString(DATE_TIME);
            title = Objects.requireNonNull(getArguments()).getString(TITLE);
            msg = Objects.requireNonNull(getArguments()).getString(MESSAGE);
            typeofCustomertv.setText("Will be send to All Customers");
            msgSendTimetv.setText("This message will be send on " + scheduldatetime);
        }

        name.setText(preferences.getString(DISPLAY_BUSINESS_NAME, ""));
        titleEt.setText(title);
        msgEt.setText(msg);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempCode == 1) {
                    callAbsenceCreateAPI();
                } else if (tempCode == 11) {
                    callAbsenceCreateAPI();
                } else if (tempCode == 2) {
                    callAllCustomerCreateAPI();
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

        close.setOnClickListener(view -> new AlertDialogYesNo(getActivity(), "Confirmation", "Are you sure want to Cancel?", "YES", "NO") {
            @Override
            public void onOKButtonClick() {
                NotificationHome notificationHome = new NotificationHome();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.replace(R.id.send_notification_frame_layout, notificationHome);
                fragmentTransaction.commit();
            }

            @Override
            public void onCancelButtonClick() {

            }
        });

        return rootView;
    }

    private void callAllCustomerCreateAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                getAllCustomerCreateRequestBody();
                createSchedule(body25);
            } catch (Exception e) {
                intermediateAlertDialog.dismissAlertDialog();
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
        } else {
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(getActivity(), "Please turn ON your data connection ", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callAllCustomerCreateAPI();
                }
            };
        }
    }

    private void getAllCustomerCreateRequestBody() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date c = cal.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDt = df.format(c);
        Log.d("SimpleDateFormat", "getAllCustomerCreateRequestBody: "+currentDt);
        body25.setReqType("L");
        body25.setReqValue("All");
        body25.setDeviceId(preferences.getString(DEVICE_ID, ""));
        body25.setMerId(preferences.getString(MERCHANT_ID, ""));
        body25.setSessiontoken("notoken");
        body25.setSendDt(currentDt);
        body25.setTitle(titleEt.getText().toString());
        body25.setText(msgEt.getText().toString());
    }

    private void callAbsenceCreateAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                getAbsenceCreateRequestBody();
                createSchedule(body25);
            } catch (Exception e) {
                intermediateAlertDialog.dismissAlertDialog();
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
        } else {
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(getActivity(), "Please turn ON your data connection ", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callAbsenceCreateAPI();
                }
            };
        }
    }

    private void getAbsenceCreateRequestBody() {
        Date c = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String sendTime = df.format(c) + " 07:00:00";
        body25.setReqType("A");
        body25.setReqValue(ansendDays);
        body25.setDeviceId(preferences.getString(DEVICE_ID, ""));
        body25.setMerId(preferences.getString(MERCHANT_ID, ""));
        body25.setSessiontoken("notoken");
        body25.setSendDt(sendTime);
        body25.setTitle(title);
        body25.setText(msg);
    }

    private void createSchedule(Body25 requestBody) throws ApiException {
        Log.d("createScheduleee", "createSchedule: "+requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());
        merchantApisApi.merchantCreateSchedulenotificationPostAsync(requestBody, new ApiCallback<Void>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("createSchedule", "onFailure: " + e + "--" + statusCode);
                        intermediateAlertDialog.dismissAlertDialog();
                        if (statusCode == 404) {
                            new AlertDialogFailure(getActivity(), "valid Merchant", "OK", "You are not") {
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
                        } else if (statusCode == 405) {
                            new AlertDialogFailure(getActivity(), "You have reached maximum limit(5)", "OK", "") {
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
                            new AlertDialogFailure(getActivity(), "Something went wrong", "OK", "") {
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
            public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("createSchedule", "onSuccess: " + result + "--" + statusCode);
                        intermediateAlertDialog.dismissAlertDialog();
                        String aa;
                        if (ansendDays.equals("By Absense")) {
                            aa = "Message scheduled for not visited more than " + group.replace(">", "") + " customers";
                        } else if (ansendDays.equals("All Customers")) {
                            aa = "Message scheduled for all customers";
                        } else {
                            aa = "Notification Message scheduled successfully";
                        }
                        if (statusCode == 200) {
                            new AlertSuccess(getActivity(), "Done !", aa) {
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

    /* private void callUpdateAPI() {
         intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
         if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
             try {
                 getUpdateRequestBody();
                 updateScheduleMessage(body27);
             } catch (Exception e) {
                 intermediateAlertDialog.dismissAlertDialog();
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
         } else {
             intermediateAlertDialog.dismissAlertDialog();
             new AlertDialogFailure(getActivity(), "Please turn ON your Internet connection ", "Retry", "No internet Connection !") {
                 @Override
                 public void onButtonClick() {
                     callUpdateAPI();
                 }
             };
         }
     }

     private void getUpdateRequestBody() {
         body27.setDeviceId(preferences.getString(DEVICE_ID, ""));
         body27.setMerId(preferences.getString(MERCHANT_ID, ""));
         body27.setIsActive(true);
         body27.setSessiontoken("");
         body27.setMsgText(msg);
         body27.setMsgTitle(title);
         body27.setSendDatetime("");
         body27.setCreateDatetime("");
         if (isSent.equals("true")) {
             body27.setIsSent(true);
         } else {
             body27.setIsSent(false);
         }
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
                             new AlertDialogFailure(getActivity(), "You are not  valid Merchant", "OK", "Failed") {
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
                             new AlertDialogFailure(getActivity(), "Notify Id not Match", "OK", "Failed") {
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
                             new AlertDialogFailure(getActivity(), "Something went wrong", "OK", "Failed") {
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
             public void onSuccess(Void result, int statusCode, Map<String, List<String>> responseHeaders) {
                 Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         intermediateAlertDialog.dismissAlertDialog();
                         if (statusCode == 200) {
                             String a;
                             a = "Message successfully saved";

                             new AlertSuccess(getActivity(), "Done !", a) {
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
 */
    @Override
    public boolean onBackPressed() {
        if (tempCode == 1 || tempCode == 2) {
            Bundle bundle = new Bundle();
            bundle.putString(TITLE, title);
            bundle.putString(MESSAGE, msg);
            bundle.putString(MESSAGE, msg);
            bundle.putString(ABSEND_DAYS, ansendDays);
            bundle.putString(DATE_TIME, scheduldatetime);
            bundle.putInt(TEMP_CODE, tempCode);
            NotificationEnterPage TemplateList = new NotificationEnterPage();
            TemplateList.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.send_notification_frame_layout, TemplateList);
            fragmentTransaction.commit();
        }/* else if (tempCode == 11) {
            Bundle bundle = new Bundle();
            bundle.putString(ABSEND_DAYS, ansendDays);
            NotificationEnterPage TemplateList = new NotificationEnterPage();
            TemplateList.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.send_notification_frame_layout, TemplateList);
            fragmentTransaction.commit();
        }*/ else {
            NotificationHome TemplateList = new NotificationHome();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.send_notification_frame_layout, TemplateList);
            fragmentTransaction.commit();
        }


        return true;
    }
}
