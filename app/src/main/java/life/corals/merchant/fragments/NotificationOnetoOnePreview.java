package life.corals.merchant.fragments;

import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.Homenew;
import life.corals.merchant.activity.MyCustomers;
import life.corals.merchant.activity.SendNotificationActivity;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body16;
import life.corals.merchant.client.model.Body23;
import life.corals.merchant.client.model.InlineResponse20017;
import life.corals.merchant.client.model.InlineResponse20017NotifyStatus;
import life.corals.merchant.client.model.InlineResponseSummary;
import life.corals.merchant.client.model.MerchantnotificationsendNotifyTo;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertDialogYesNo;
import life.corals.merchant.utils.AlertSuccess;
import life.corals.merchant.utils.Constants;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.DISPLAY_BUSINESS_NAME;
import static life.corals.merchant.utils.Constants.INSIGHTS_HOME_LOAD_COUNT_STR;
import static life.corals.merchant.utils.Constants.MERCHANT_COUNTRY;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.TOT_CUST_STATUS;
import static life.corals.merchant.utils.Constants.TOT_SALE;
import static life.corals.merchant.utils.Constants.TOT_SALE_AMOUNT;
import static life.corals.merchant.utils.Constants.TOT_SALE_STATUS;
import static life.corals.merchant.utils.Constants.TOT_TRAFFIC;
import static life.corals.merchant.utils.Constants.TOT_TRAFFIC_STATUS;

public class NotificationOnetoOnePreview extends BaseFragment {
    private IntermediateAlertDialog intermediateAlertDialog;

    private View rootView;
    private String title;

    private String msg;
    private String type;
    private String notifytoken;
    private String datetime;
    private String group;
    private String isSent;
    private String reqId;
    private Body23 body = new Body23();


    private SharedPreferences preferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_notification_preview, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar_preview);
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

       TextView msgSendTimetv = rootView.findViewById(R.id.msg_send_time);
        msgSendTimetv.setVisibility(View.GONE);
        title = Objects.requireNonNull(getArguments()).getString("title");
        msg = Objects.requireNonNull(getArguments()).getString("msg");
        notifytoken = Objects.requireNonNull(getArguments()).getString("token");
        String customerid = Objects.requireNonNull(getArguments()).getString("cust_id");
        String customerName= Objects.requireNonNull(getArguments()).getString("cust_name");
        String mobile= Objects.requireNonNull(getArguments()).getString("mobile");

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);


        TextView typeofCustomertv = rootView.findViewById(R.id.type_of_customer);
        TextView titletv = rootView.findViewById(R.id.notification_title);
        TextView msgtv = rootView.findViewById(R.id.notification_content);
        MaterialButton confirm =rootView.findViewById(R.id.confirm_button);
        MaterialButton close =rootView.findViewById(R.id.close_button);
        TextView namew = (TextView) rootView.findViewById(R.id.notification_dialog_title);
        namew.setText(preferences.getString(DISPLAY_BUSINESS_NAME, ""));
        titletv.setText(title);
        msgtv.setText(msg);

        String name=customerid;
        if (customerName!=null){
            name = customerName;
        }
        typeofCustomertv.setText("Send to "+customerid);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
                //sendFCMPush(title, msg, notifytoken);
                callAPI(title,msg,customerid,mobile);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

        return rootView;
    }

    private void sendFCMPush(String tle, String msgg, String token) {

        String Legacy_SERVER_KEY = Constants.CORALS_SERVER_KEY;
        JSONObject obj = null;
        JSONObject notification = null;
        JSONObject dataObj = null;

        try {
            obj = new JSONObject();
            notification = new JSONObject();

            notification.put("body", msgg);
            notification.put("title", tle);
            notification.put("sound", "default");
            // objData.put("icon", "icon_name"); //   icon_name image must be there in drawable
            // objData.put("tag", token);
            notification.put("priority", "high");


            dataObj = new JSONObject();
            dataObj.put("body", msgg);
            dataObj.put("title", tle);
            dataObj.put("merchantname", preferences.getString(DISPLAY_BUSINESS_NAME, ""));
            dataObj.put("notification-type", "NOTIFY");


            obj.put("to", token);
            //obj.put("priority", "high");

            obj.put("notification", notification);
            obj.put("data", dataObj);

        } catch (JSONException e) {
            intermediateAlertDialog.dismissAlertDialog();
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.FCM_URL, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        new AlertSuccess(getActivity(), "Done !", "Notification sent successfully") {
                            @Override
                            public void onButtonClick() {
                                intermediateAlertDialog.dismissAlertDialog();
                                startActivity(new Intent(getActivity(), MyCustomers.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        intermediateAlertDialog.dismissAlertDialog();
                        new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                                CustomerListOne fragment1 = new CustomerListOne();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                fragmentTransaction.replace(R.id.customer_frame_layout, fragment1);
                                fragmentTransaction.commit();
                            }
                        };
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + Legacy_SERVER_KEY);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }


    @Override
    public boolean onBackPressed() {
        new AlertDialogYesNo(getActivity(), "Confirmation", "Are you sure want to Cancel?", "YES", "NO") {
            @Override
            public void onOKButtonClick() {
                CustomerListOne fragment1 = new CustomerListOne();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.replace(R.id.customer_frame_layout, fragment1);
                fragmentTransaction.commit();
            }

            @Override
            public void onCancelButtonClick() {

            }
        };
        return true;
    }

    private void callAPI(String tle, String msgg, String custId, String mobile) {

        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                getRequestBody(tle,msgg,custId,mobile);
                sendNotiofication(body);
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
            new AlertDialogFailure(getActivity(), "Please turn ON your data connection", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callAPI(title,msg, custId,mobile);
                }
            };
        }
    }

    private void getRequestBody(String tle, String msgg, String cusID,String mobile) {
        MerchantnotificationsendNotifyTo notifyToken = new MerchantnotificationsendNotifyTo();
        notifyToken.setCustId(cusID);
        notifyToken.setMobileNo(mobile);
        List<MerchantnotificationsendNotifyTo> notifyTo = new ArrayList<>();
        notifyTo.add(notifyToken);
        body.setNotifyTitle(tle);
        body.setNotifyMessage(msgg);
        body.setIsBroadcast(true);
        body.setNotifyTo(notifyTo);
        body.setDeviceId(preferences.getString(DEVICE_ID, ""));
        body.setMerId(preferences.getString(MERCHANT_ID, ""));
        body.setSessiontoken("notoken");
        Log.d("getWeekRewardPoints", "getRequestBodymonth: " + body);
    }

    private void sendNotiofication(Body23 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.merchantNotificationSendPostAsync(requestBody, new ApiCallback<InlineResponse20017>() {

            @Override
            public void onFailure(ApiException e, final int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    Log.d("InlineResponse__Summary", "onFailure: " + e + "  " + statusCode);
                    intermediateAlertDialog.dismissAlertDialog();

                        new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Notification sending failed") {
                            @Override
                            public void onButtonClick() {
                                CustomerListOne fragment1 = new CustomerListOne();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                fragmentTransaction.replace(R.id.customer_frame_layout, fragment1);
                                fragmentTransaction.commit();
                            }
                        };

                });
            }

            @Override
            public void onSuccess(InlineResponse20017 result, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    Log.d("InlineResponse__Summary", "onSuccess: " + result);
                    intermediateAlertDialog.dismissAlertDialog();
                     if (statusCode==200){
                         if (result!=null){
                             new AlertSuccess(getActivity(), "Done !", "Notification send successfully") {
                                 @Override
                                 public void onButtonClick() {
                                     CustomerListOne fragment1 = new CustomerListOne();
                                     FragmentManager fragmentManager = getFragmentManager();
                                     FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                     fragmentTransaction.replace(R.id.customer_frame_layout, fragment1);
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
}
