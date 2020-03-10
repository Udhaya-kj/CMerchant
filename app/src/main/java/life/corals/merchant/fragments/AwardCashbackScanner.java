package life.corals.merchant.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.Homenew;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body21;
import life.corals.merchant.client.model.InlineResponse20015;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AppTimeOutManagerUtil;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.IssuePointsNoteBottomSheet;
import life.corals.merchant.utils.IssuePointsQRScannerUtils;
import life.corals.merchant.utils.MerchantToast;
import life.corals.merchant.utils.MySharedPreference;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;
import static life.corals.merchant.utils.Constants.MOBILE_NUMBER;

public class AwardCashbackScanner extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private Toolbar toolbar;
    private Body21 body = new Body21();
    private static final String TAG = "AwardCashbackScanner";

    private static final String node1 = "26";

    private static final String node2 = "92";
    private SharedPreferences preferences;

    private static final String OUTLET_ID = "93";
    private MySharedPreference sharedPreference;
    SurfaceView surfaceView;
    private Context context;
    private TextView scanResult;

    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    private IntermediateAlertDialog intermediateAlertDialog;

    private CoordinatorLayout layout;

    private IssuePointsNoteBottomSheet qrTopUpNoteBottomSheet;
    private IssuePointsQRScannerUtils IssuePointsQRScannerUtils;

    private MerchantToast coralsToast;


    private String issueamount = null;
    private String mobileNumberstr;
    private FragmentActivity mActivity;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_issue_points_scanner, container, false);
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(rootView.getWindowToken(), 0);
        issueamount = Objects.requireNonNull(getArguments()).getString("amount");
        context = getActivity();
        findView();
        return rootView;
    }

    private void findView() {
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        sharedPreference = new MySharedPreference(getActivity().getApplicationContext());

        layout = rootView.findViewById(R.id.topupscreen_two);
        surfaceView = rootView.findViewById(R.id.surfaceView);
        scanResult = rootView.findViewById(R.id.result_top_up_qr_scan);
        MaterialButton button = rootView.findViewById(R.id.cancel_scan);
        button.setOnClickListener(this);

        qrTopUpNoteBottomSheet = new IssuePointsNoteBottomSheet(getActivity(), issueamount, mobileNumberstr) {
            @Override
            public void onCancelButtonCLick() {
                onBackPressed();
            }

            @Override
            public void onProceedButtonClick() {
                callAPI();
            }
        };

        IssuePointsQRScannerUtils = new IssuePointsQRScannerUtils(surfaceView, getActivity()) {
            @Override
            public void parsedScanData(HashMap<String, String> stringHashMap) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (stringHashMap != null) {
                            if (stringHashMap.get("mobile_no") != null) {
                                mobileNumberstr = stringHashMap.get("mobile_no");
                                qrTopUpNoteBottomSheet.showQRNoteAlertDialog(issueamount, stringHashMap.get("mobile_no"));
                            } else {
                                qrTopUpNoteBottomSheet.dismissOnProceed();
                                new AlertDialogFailure(getActivity(), "Invalid QR", "Ok", "Failed") {
                                    @Override
                                    public void onButtonClick() {
                                        onBackPressed();
                                    }
                                };
                            }

                        } else {
                            qrTopUpNoteBottomSheet.dismissOnProceed();
                            new AlertDialogFailure(getActivity(), "Invalid QR", "Ok", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
                                }
                            };
                        }

                    }
                });
            }

            @Override
            public void onFailureScan(final String result) {
                qrTopUpNoteBottomSheet.dismissOnProceed();
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(getActivity(), result, "Ok", "Failed") {
                            @Override
                            public void onButtonClick() {
                                onBackPressed();
                            }
                        };
                    }
                });
            }
        };
    }

    @Override
    public void onClick(View view) {

    }

    private void callAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                getRequestBody();
                cbIssuesPpoints(body);
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
            new AlertDialogFailure(getActivity(), "Please turn ON your data connection", "Retry", "No internet Connection !") {
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
        body.setMobileNo(mobileNumberstr);
        body.setPayAmt(issueamount);
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
                        qrTopUpNoteBottomSheet.dismissNoteAlertDialog();
                        intermediateAlertDialog.dismissAlertDialog();
                        if (statusCode == 501) {
                            new AlertDialogFailure(getActivity(), "You have not activated cashback module! Contact support to activate.", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
                                }
                            };
                        } else if (statusCode == 409) {
                            new AlertDialogFailure(getActivity(), "Invalid Merchant", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
                                }
                            };
                        } else if (statusCode == 406) {
                            new AlertDialogFailure(getActivity(), "Cashback not eligible for this purchase!", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
                                }
                            };
                        } else if (statusCode == 401) {
                            new AlertDialogFailure(getActivity(), "Cashback issue failed \n ", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
                                }
                            };
                        } else if (statusCode == 404) {
                            new AlertDialogFailure(getActivity(), "Customer is not a member of your program", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
                                }
                            };
                        } else if (statusCode == 405) {
                            new AlertDialogFailure(getActivity(), "Cashback time expired", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
                                }
                            };
                        } else if (statusCode == 204) {
                            new AlertDialogFailure(getActivity(), "Customer cashback points record is not available", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
                                }
                            };
                        } else {
                            new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    onBackPressed();
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
                        qrTopUpNoteBottomSheet.dismissNoteAlertDialog();
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
        bundle.putString(MOBILE_NUMBER, mobileNumberstr);
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
        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
    }

    @Override
    public boolean onBackPressed() {
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_cashback_activity, new AwardCashBackOne());
        transaction.addToBackStack(null);
        transaction.commit();
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
