package life.corals.merchant.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

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
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertSuccess;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.ABSEND_DAYS;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.TEMP_CODE;

public class NotificationHome extends BaseFragment implements AdapterView.OnItemSelectedListener {
    private View rootview;

    private SharedPreferences preferences;
    Body26 body = new Body26();
    private IntermediateAlertDialog intermediateAlertDialog;

    private MaterialButton setUpforAll;
    private TextView maxLimitInfo1;
    private TextView maxLimitInfo2;
    private  Spinner spinner;
    private TextView daysTv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.frag_notification_home, container, false);
        Toolbar toolbar = rootview.findViewById(R.id.toolbar_home);
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
        findView();
        return rootview;
    }

    private void findView() {
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        maxLimitInfo1 = rootview.findViewById(R.id.last_setup_info_all);
        maxLimitInfo2 = rootview.findViewById(R.id.last_setup_info_ab);
        MaterialButton viewScheduleList = rootview.findViewById(R.id.view_schedules_list);
         spinner = rootview.findViewById(R.id.spinner_select_days);
        spinner.setPrompt("Select days");
        String days[] = {"Select days", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
                "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, days);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        daysTv = rootview.findViewById(R.id.days);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
         setUpforAll = rootview.findViewById(R.id.setup_btn);
        viewScheduleList.setOnClickListener(v -> {
            NotificationSchedulesList schedulesList = new NotificationSchedulesList();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.send_notification_frame_layout, schedulesList);
            fragmentTransaction.commit();
        });

        setUpforAll.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(TEMP_CODE, 2);
            NotificationEnterPage fragment1 = new NotificationEnterPage();
            fragment1.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.send_notification_frame_layout, fragment1);
            fragmentTransaction.commit();
        });

        callAPI();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (position != 0) {
            String item = parent.getItemAtPosition(position).toString();
            Bundle bundle = new Bundle();
            bundle.putString(ABSEND_DAYS, item);
            bundle.putInt(TEMP_CODE, 1);
            NotificationEnterPage enterPage = new NotificationEnterPage();
            enterPage.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.send_notification_frame_layout, enterPage);
            fragmentTransaction.commit();
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getActivity(), Homenew.class));
        Objects.requireNonNull(getActivity()).finish();
        getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        return true;
    }

    private void callAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                getRequestBody();
                getListofScheduleMessage(body);
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

                            new AlertDialogFailure(getActivity(), "Something went wrong", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), Homenew.class));
                                    getActivity().finish();
                                }
                            };
                    }
                });

            }

            @Override
            public void onSuccess(InlineResponse20019 result, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("InlineResponse20019", "run: "+result);
                        intermediateAlertDialog.dismissAlertDialog();

                        if (statusCode == 200) {
                            if(result!=null){
                                if (result.getListofmessages().size()>=5){
                                    setUpforAll.setClickable(false);
                                    setUpforAll.setAlpha(0.4f);
                                    maxLimitInfo1.setVisibility(View.VISIBLE);
                                    maxLimitInfo1.setText("Reached your max limit");
                                    spinner.setEnabled(false);
                                    spinner.setAlpha(0.4f);
                                    daysTv.setAlpha(0.4f);
                                    maxLimitInfo2.setVisibility(View.VISIBLE);
                                    maxLimitInfo2.setText("Reached your max limit");
                                }
                            }

                        } else {
                            new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), Homenew.class));
                                    getActivity().finish();
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
