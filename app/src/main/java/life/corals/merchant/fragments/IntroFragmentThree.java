package life.corals.merchant.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body1;
import life.corals.merchant.client.model.InlineResponse2001;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.MerchantToast;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.ACTIVATION_CODE_TRY_STRNG;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.DEVICE_REGISTER;
import static life.corals.merchant.utils.Constants.IS_ADMIN;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_DEVICE_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.REGISTRATION_CODE;

@RequiresApi(api = Build.VERSION_CODES.M)
public class IntroFragmentThree extends BaseFragment {

    private View rootView;
    private MaterialButton agreeContinue;
    private String registrationCode;
    private Body1 body1 = new Body1();
    private SharedPreferences preferences;
    private IntermediateAlertDialog intermediateAlertDialog;
    private ScrollView scrollView;
    private int scrollY = 0;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.intro_fragment_three, container, false);

        preferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);

        registrationCode = Objects.requireNonNull(getArguments()).getString(REGISTRATION_CODE);

        findView();

        if (!trycount()) {
            new AlertDialogFailure(getActivity(), "You reached maximum limit please re-install your app and continue", "OK", "Sorry !") {
                @Override
                public void onButtonClick() {
                    IntroFragmentOne fragment1 = new IntroFragmentOne();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
                    fragmentTransaction.commit();
                }
            };
        }

        MerchantToast merchantToast = new MerchantToast(getActivity());
        merchantToast.showMessage(" Scroll down to agree & continue ", getResources().getDrawable(R.drawable.ic_arrow_downward_black_24dp));
        return rootView;
    }

    private void findView() {
        scrollView = rootView.findViewById(R.id.scroll_view);
        agreeContinue = rootView.findViewById(R.id.agree_continue);

        agreeContinue.setEnabled(false);

        agreeContinue.setAlpha(0.4f);

        TextView termsAndconditions = rootView.findViewById(R.id.terms_and_conditions_txtvw);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                scrollY = scrollView.getScrollY();
                if (scrollY > 50) {
                    agreeContinue.setEnabled(true);
                    agreeContinue.setVisibility(View.VISIBLE);
                    agreeContinue.setAlpha(1);
                }
            }
        });

        termsAndconditions.setText(Html.fromHtml(getResources().getString(R.string.termsconditonslongvalue)));
        agreeContinue.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                callApi();
            }
        });

    }

    private boolean trycount() {
        String a = preferences.getString(ACTIVATION_CODE_TRY_STRNG, String.valueOf(1));
        int b = Integer.parseInt(a);
        return b <= 5;
    }

    private void callApi() {
        trycount();
        agreeContinue.setClickable(false);
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (!trycount()) {
            new AlertDialogFailure(getActivity(), "Reached your maximum limit please re-install your app and continue", "Ok", "Oops!") {
                @Override
                public void onButtonClick() {
                    IntroFragmentOne fragment1 = new IntroFragmentOne();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
                    fragmentTransaction.commit();
                }
            };
        } else {
            if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
                getRequestBody();
                try {
                    registerDevice(body1);
                } catch (ApiException e) {
                    agreeContinue.setClickable(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    e.printStackTrace();
                }
            } else {
                agreeContinue.setClickable(true);
                intermediateAlertDialog.dismissAlertDialog();
                new AlertDialogFailure(getActivity(), "Please turn ON your data connection", "Retry", "No internet Connection !") {
                    @Override
                    public void onButtonClick() {
                        callApi();
                    }
                };

            }
        }

    }

    private void getRequestBody() {
        body1.setRegistrationCode(registrationCode);
        body1.setRequestDeviceId("noid");
        body1.setNotifyInstanceId("");

    }

    private void registerDevice(Body1 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.merchantDeviceRegisterPostAsync(requestBody, new ApiCallback<InlineResponse2001>() {
            @Override
            public void onFailure(final ApiException e, final int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    Log.d("MerchanRegister", "onFailure: "+e+"  "+statusCode);
                    intermediateAlertDialog.dismissAlertDialog();
                    agreeContinue.setClickable(true);
                    String a = preferences.getString(ACTIVATION_CODE_TRY_STRNG, String.valueOf(1));
                    int count = Integer.parseInt(a) + 1;
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(ACTIVATION_CODE_TRY_STRNG, String.valueOf(count));
                    editor.apply();

                    if (statusCode==404){
                        new AlertDialogFailure(getActivity(), "Wrong activation code", "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                IntroFragmentOne fragment1 = new IntroFragmentOne();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
                                fragmentTransaction.commit();
                            }
                        };
                    }else if (statusCode==406){
                        new AlertDialogFailure(getActivity(), "Your code already activated", "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                IntroFragmentOne fragment1 = new IntroFragmentOne();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
                                fragmentTransaction.commit();
                            }
                        };
                    }else {
                        new AlertDialogFailure(getActivity(), "Unable to register your device", "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                IntroFragmentOne fragment1 = new IntroFragmentOne();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
                                fragmentTransaction.commit();
                            }
                        };
                    }

                });

            }


            @Override
            public void onSuccess(InlineResponse2001 result, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (statusCode == 200) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.remove(ACTIVATION_CODE_TRY_STRNG);
                            editor.apply();

                            SharedPreferences preferences1 = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DEVICE_PREFERENCE, MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = preferences1.edit();
                            editor1.putString(IS_ADMIN, String.valueOf(result.isIsadmin()));
                            editor1.putString(DEVICE_ID,String.valueOf(result.getDeviceId()) );
                            editor1.putString(MERCHANT_ID, String.valueOf(result.getMerId()));
                            editor1.apply();
                            editor1.apply();
                            intermediateAlertDialog.dismissAlertDialog();

                            successIntent();
                        } else {
                            new AlertDialogFailure(getActivity(), "Unable to register your device", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    IntroFragmentOne fragment1 = new IntroFragmentOne();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                    fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onBackPressed() {
        FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frame_layout_intro_activity, new IntroFragmentOne());
        transaction.commit();
        return true;
    }

    private void successIntent() {
        IntroFragmentFour fragment1 = new IntroFragmentFour();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
        fragmentTransaction.commit();
    }

}

