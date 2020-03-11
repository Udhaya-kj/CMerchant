package life.corals.merchant.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import life.corals.merchant.R;
import life.corals.merchant.activity.AwardCashbackActivity;
import life.corals.merchant.activity.Homenew;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body21;
import life.corals.merchant.client.model.InlineResponse20015;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.ParamProperties;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.CURRENCY_SYMBOL;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_COUNTRY;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.MOBILE_NUMBER;
import static life.corals.merchant.utils.Constants.OUTLET_ID;

public class AwardCashBackOne extends BaseFragment {

    private View rootView;
    private String mobile = "";
    private String amnt = "";
    private Body21 body = new Body21();
    private TextInputEditText amount;
    private SharedPreferences preferences;
    private TextInputEditText mobileNumber;
    private TextInputLayout mobileNumberLyayout;

    private MaterialButton submitBtn;
    private MaterialButton scanQr;
    private IntermediateAlertDialog intermediateAlertDialog;
    private TextView currency;
    private Toolbar toolbar;
    private MaterialButton selectMobilenumber;
    private LinearLayout slectOptionsLayout;
    private LinearLayout MobilenumberLayout;
    private boolean isValid = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_award_cashback_one, container, false);
        toolbar = rootView.findViewById(R.id.toolbar_cashback_one);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            if (getActivity() != null && activity.getSupportActionBar() != null && getActivity().getResources() != null) {
                activity.getSupportActionBar().setHomeButtonEnabled(true);
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activity.getSupportActionBar().setHomeAsUpIndicator(getActivity().getResources().getDrawable(R.drawable.ic_arrow_left));
                toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), Homenew.class));
                        Objects.requireNonNull(getActivity()).finish();
                        getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                    }
                });
            }
        }

        findView();
        return rootView;
    }

    private void findView() {
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        ParamProperties paramProperties = new ParamProperties();
        int MOB_MAX_LENGTH = Integer.parseInt(paramProperties.getProperty(preferences.getString(MERCHANT_COUNTRY, ""), ParamProperties.MOBILE_MAX_LENGTH));

        mobileNumberLyayout = rootView.findViewById(R.id.enter_mobile_number_layout);
        selectMobilenumber = rootView.findViewById(R.id.slect_mobile_number);
        slectOptionsLayout = rootView.findViewById(R.id.select_option_layout);
        MobilenumberLayout = rootView.findViewById(R.id.mobile_number_layout);
        mobileNumber = rootView.findViewById(R.id.Mobile_number_cashback_one);
        mobileNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MOB_MAX_LENGTH)});
        submitBtn = rootView.findViewById(R.id.submit_btn);
        scanQr = rootView.findViewById(R.id.scan_qr);
        amount = rootView.findViewById(R.id.cash_back_amnt);

        currency = rootView.findViewById(R.id.payment_curr);
        currency.setText(preferences.getString(CURRENCY_SYMBOL, ""));

        slectOptionsLayout.setVisibility(View.VISIBLE);
        submitBtn.setVisibility(View.GONE);
        selectMobilenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amnt == null || amnt.equals("0.00") || amnt.isEmpty()) {
                    submitBtn.setVisibility(View.GONE);
                    amount.setError("Enter valid Amount");
                    amount.requestFocus();
                } else {
                    submitBtn.setVisibility(View.VISIBLE);
                    mobileNumber.requestFocus();
                    slectOptionsLayout.setVisibility(View.GONE);
                    MobilenumberLayout.setVisibility(View.VISIBLE);
                    mobileNumberLyayout.setErrorEnabled(false);
                }
            }
        });
        scanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amnt == null || amnt.isEmpty() || amnt.equals("0.00")) {
                    submitBtn.setVisibility(View.GONE);
                    isValid = false;
                    amount.setError("Enter valid amount");
                    amount.requestFocus();
                } else {
                    submitBtn.setClickable(false);
                    Bundle bundle = new Bundle();
                    bundle.putString("amount", amnt);
                    AwardCashbackScanner fragment1 = new AwardCashbackScanner();
                    fragment1.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_cashback_activity, fragment1);
                    fragmentTransaction.commit();
                    Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                }
            }
        });

        amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount.setCursorVisible(true);
                amount.post(() -> amount.setSelection(Objects.requireNonNull(amount.getText()).toString().length()));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        amount.setCursorVisible(false);
                    }
                }, 1000);
            }
        });

        amount.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                mobileNumber.setEnabled(true);
                amount.setCursorVisible(false);
                amnt = Objects.requireNonNull(amount.getText()).toString();
                Log.d("cleanString", "afterTextChanged: "+amnt);
                if (!amnt.isEmpty()){
                    amount.removeTextChangedListener(this);
                    String cleanString = amnt.replaceAll("[$,.,₹]", "");
                    BigDecimal bigDecimal = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
                    String converted =String.valueOf(bigDecimal);
                    String DecimalAmnt = converted.replaceAll("[$,£,₹]", "");
                    amount.setText(DecimalAmnt);
                    amount.addTextChangedListener(this);
                    amount.post(() -> amount.setSelection(amount.getText().toString().length()));
                    BigDecimal a = new BigDecimal(DecimalAmnt);
                    amnt = String.valueOf(a);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                amnt = amount.getText().toString();
                amount.post(() -> amount.setSelection(amount.getText().toString().length()));
                amount.setCursorVisible(false);
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                amount.setCursorVisible(false);
            }
        });
        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mobileNumberLyayout.setErrorEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mobile = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mobile.isEmpty()) {
                    submitBtn.setVisibility(View.GONE);
                    mobileNumberLyayout.setErrorEnabled(false);
                    slectOptionsLayout.setVisibility(View.VISIBLE);
                    MobilenumberLayout.setVisibility(View.GONE);
                }
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;
                if (amnt.isEmpty() || amnt.equals("0.00")) {
                    amount.setError("Enter valid amount");
                    amount.requestFocus();
                } else {
                    if (slectOptionsLayout.getVisibility() == View.GONE) {
                        if (mobile == null || mobile.isEmpty()) {
                            isValid = false;
                            mobileNumber.setError("Enter Mobile number");
                            mobileNumber.requestFocus();
                        }
                        if (mobile.length() < 8) {
                            isValid = false;
                            mobileNumber.setError("Enter valid Mobile number");
                            mobileNumber.requestFocus();
                        } else {
                            if (!isValidCountryMobileNumber(mobile, preferences.getString(MERCHANT_COUNTRY, ""))) {
                                mobileNumber.setError("Enter valid mobile number");
                                mobileNumber.requestFocus();
                                isValid = false;
                            }
                        }
                        if (amnt == null || amnt.isEmpty() || amnt.equals("0.00")) {
                            isValid = false;
                            amount.setError("Enter valid amount");
                            amount.requestFocus();
                        }
                        if (isValid) {
                            submitBtn.setClickable(false);
                            intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
                            callAPI();
                        }
                    } else {
                        Animation startAnimation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.blinking_fast);
                        selectMobilenumber.startAnimation(startAnimation1);
                        scanQr.startAnimation(startAnimation1);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                selectMobilenumber.clearAnimation();
                                scanQr.clearAnimation();
                            }
                        }, 600);

                    }
                }

            }
        });

    }


    private void callAPI() {
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                submitBtn.setClickable(false);
                getRequestBody();
                cbIssuesPpoints(body);
            } catch (Exception e) {
                intermediateAlertDialog.dismissAlertDialog();
                submitBtn.setClickable(true);
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
            submitBtn.setClickable(true);
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
        body.setSessiontoken("notoken");
        body.setMobileNo(mobile);
        body.setPayAmt(amnt);
        body.setOutletId(preferences.getString(OUTLET_ID, ""));
    }

    private void cbIssuesPpoints(Body21 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.merchantCbissuePostAsync(requestBody, new ApiCallback<InlineResponse20015>() {

            @Override
            public void onFailure(ApiException e, final int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        intermediateAlertDialog.dismissAlertDialog();
                        if (statusCode == 501) {
                            new AlertDialogFailure(getActivity(), "You are not enrolled Cashback", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), AwardCashbackActivity.class));
                                    Objects.requireNonNull(getActivity()).finish();
                                    getActivity().overridePendingTransition(R.anim.fade_out, R.anim.fade_out);
                                }
                            };
                        } else if (statusCode == 406) {
                            new AlertDialogFailure(getActivity(), "Cashback not eligible for this purchase!", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), AwardCashbackActivity.class));
                                    Objects.requireNonNull(getActivity()).finish();
                                    getActivity().overridePendingTransition(R.anim.fade_out, R.anim.fade_out);
                                }
                            };
                        } else if (statusCode == 401) {
                            new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), Homenew.class));
                                    Objects.requireNonNull(getActivity()).finish();
                                    getActivity().overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        } else if (statusCode == 404) {
                            new AlertDialogFailure(getActivity(), "Customer is not a member of your program", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), Homenew.class));
                                    Objects.requireNonNull(getActivity()).finish();
                                    getActivity().overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        } else if (statusCode == 405) {
                            new AlertDialogFailure(getActivity(), "Cashback time expired ", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), Homenew.class));
                                    Objects.requireNonNull(getActivity()).finish();
                                    getActivity().overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        } else if (statusCode == 204) {
                            new AlertDialogFailure(getActivity(), "Customer cashback points record is not available", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), Homenew.class));
                                    Objects.requireNonNull(getActivity()).finish();
                                    getActivity().overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                }
                            };
                        } else {
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
            public void onSuccess(InlineResponse20015 result, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (statusCode == 200) {
                            if (result != null) {
                                intermediateAlertDialog.dismissAlertDialog();
                                String points = result.getPointsIssued();
                                String totalpoints = result.getPointsTotal();
                                String pointsExpiry = result.getPointsExpiry();
                                String customerAppUsing = result.getIsCustNotUsingapp();
                                String customerId = result.getCustId();
                                String isNewCustomer = result.getIsNewCustomer();
                                successIntent(points, totalpoints, pointsExpiry, customerAppUsing, customerId, isNewCustomer);
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

    private void successIntent(String points, String totalpoints, String pointsExpiry, String customerAppUsing, String customerId, String isNewCust) {
        Bundle bundle = new Bundle();
        bundle.putString(MOBILE_NUMBER, mobile);
        bundle.putString("TOTAL_POINTS", totalpoints);
        bundle.putString("POINTS", points);
        bundle.putString("POINTS_EXPIRY", pointsExpiry);
        bundle.putString("CUSTOMER_APP_USE", customerAppUsing);
        bundle.putString("CUSTOMER_ID", customerId);
        bundle.putString("IS_NEW_CUSTOMER", isNewCust);
        AwardCashBackSuccess fragment1 = new AwardCashBackSuccess();
        fragment1.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_cashback_activity, fragment1);
        fragmentTransaction.commit();
        fragmentTransaction.setCustomAnimations(R.anim.swipe_in_right, R.anim.swipe_in_right);
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getActivity(), Homenew.class));
        Objects.requireNonNull(getActivity()).finish();
        getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        return true;
    }

    private boolean isValidCountryMobileNumber(String mobileNumberStr, String country) {
        ParamProperties paramProperties = new ParamProperties();
        String COUNTRY_MOB_NUM_LENGTH = String.valueOf(Integer.parseInt(paramProperties.getProperty(country, ParamProperties.MOBILE_MAX_LENGTH)) - 1);
        String COUNTRY_MOB_START_DIGITS = paramProperties.getProperty(country, ParamProperties.MOBILE_START_DIGITS);
        if (country.equals("MY")) {
            COUNTRY_MOB_NUM_LENGTH = "9,".concat(COUNTRY_MOB_NUM_LENGTH);
        }
        String expression = "^" + COUNTRY_MOB_START_DIGITS + "\\d" + "{".concat(COUNTRY_MOB_NUM_LENGTH) + "}$";
        Log.d("CountryMobileNumbe", "onCreateView: " + expression);
        CharSequence inputString = mobileNumberStr;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
