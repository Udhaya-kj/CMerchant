package life.corals.merchant.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.Homenew;
import life.corals.merchant.activity.VoucherSetupHome;
import life.corals.merchant.adapter.Vouchers_RecyclerviewAdapter;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.ClientApisApi;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body15;
import life.corals.merchant.client.model.Body22;
import life.corals.merchant.client.model.FetchRedeemVoucherListRequestBody;
import life.corals.merchant.client.model.InlineResponse20012;
import life.corals.merchant.client.model.InlineResponse20012Merredeemlist;
import life.corals.merchant.client.model.InlineResponse20012MerredeemlistRedemptionList;
import life.corals.merchant.client.model.InlineResponse20016;
import life.corals.merchant.client.model.RedeemVoucher;
import life.corals.merchant.client.model.RedeemVoucherListResponse;
import life.corals.merchant.client.model.SetUpRedemptionList;
import life.corals.merchant.client.model.VoucherManageBody;
import life.corals.merchant.client.model.VoucherManageResponse;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertDialogYesNo;
import life.corals.merchant.utils.AppTimeOutManagerUtil;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.MerchantToast;
import life.corals.merchant.utils.MySharedPreference;
import life.corals.merchant.utils.RedeemPointsQRScannerUtils;
import life.corals.merchant.utils.RedeemProductBottomSheet;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;

public class RedeemPointsScanner extends BaseFragment implements View.OnClickListener {

    private View rootView;

    private SharedPreferences preferences;

    private SurfaceView surfaceView;

    private Body15 body15 = new Body15();
    private Body22 body22 = new Body22();
    private VoucherManageBody voucherManageBody = new VoucherManageBody();

    private IntermediateAlertDialog intermediateAlertDialog;

    private String redeemId = null;
    private String custId = null;
    private String deviceId = null;
    private String productAmnt;

    private RedeemProductBottomSheet redeemProductBottomSheet;
    private String startTime = "";
    private String endtime = "";

    FetchRedeemVoucherListRequestBody fetchRedeemVoucherListRequestBody;
    // voucher request code
    public static final int GET_VOUCHER_LIST = 0;
    public static final int GET_SINGLE_VOUCHER_DETAILS = 1;
    private AlertDialogYesNo alertDialogYesNo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_redeem_product_scanner, container, false);
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        findView();
        return rootView;
    }

    private void findView() {
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        surfaceView = rootView.findViewById(R.id.surfaceView);
        TextView scanResult = rootView.findViewById(R.id.result_top_up_qr_scan);
        MaterialButton button = rootView.findViewById(R.id.cancel_scan);
        button.setOnClickListener(this);

        fetchRedeemVoucherListRequestBody = new FetchRedeemVoucherListRequestBody();
        fetchRedeemVoucherListRequestBody.setCallerType("m");
        fetchRedeemVoucherListRequestBody.setMerId("120022732");
        fetchRedeemVoucherListRequestBody.setDeviceId("2002271337305070");
        fetchRedeemVoucherListRequestBody.setSessionToken("NoToken");
        fetchRedeemVoucherListRequestBody.setVoucherType("ALL");
        fetchRedeemVoucherListRequestBody.setVoucherId("6200305095140120");
        fetchRedeemVoucherListRequestBody.setRequestCode(String.valueOf(GET_SINGLE_VOUCHER_DETAILS));

        redeemProductBottomSheet = new RedeemProductBottomSheet(getActivity()) {
            @Override
            public void onCancelButtonCLick() {
                onBackPressed();
            }

            @Override
            public void onProceedButtonClick() {
            }
        };

        //callAPI();
       // callAPI_getRedeemId();

        new RedeemPointsQRScannerUtils(surfaceView, getActivity()) {
            @Override
            public void parsedScanData(HashMap<String, String> stringHashMap) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (stringHashMap != null) {
                            redeemProductBottomSheet.dismissOnProceed();
                            if (stringHashMap.get("redeem_id") != null) {
                                Log.d("Redeem__PointsQRDEAt", "dismisQRR" + stringHashMap);
                                redeemId = stringHashMap.get("redeem_id");
                                custId = stringHashMap.get("cust_id");
                                //callAPI();
                            } else {
                                new AlertDialogFailure(getActivity(), "Invalid QR", "OK", "Failed") {
                                    @Override
                                    public void onButtonClick() {
                                        onBackPressed();
                                    }
                                };
                            }

                        } else {
                            redeemProductBottomSheet.dismissOnProceed();
                            new AlertDialogFailure(getActivity(), "Invalid QR", "OK", "Failed") {
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
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        redeemProductBottomSheet.dismissNoteAlertDialog();
                        new AlertDialogFailure(getActivity(), result, "OK", "Failed") {
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
                getDataforredreemid(voucherManageBody);
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
            new AlertDialogFailure(getActivity(), "Please turn ON Data connection", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callAPI();
                }
            };
        }
    }

    private void getRequestBody() {
    /*    Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date c = cal.getTime();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayfromdatetime = df.format(c);
        Date now = Calendar.getInstance().getTime();
        String todaytodatetime = df.format(now);
        body15.setCustomerid(custId);
        body15.setDeviceid(preferences.getString(DEVICE_ID, ""));
        body15.setFromdate(todayfromdatetime);
        body15.setTodate(todaytodatetime);
        body15.setMerId(preferences.getString(MERCHANT_ID, ""));
        body15.setMobileNumber("9876");
        body15.setRestrictbycustomer(false);
        body15.setRestrictCount(BigDecimal.valueOf(50));
        body15.setSessiontoken("");
        body15.callertype("m");
        body15.iscbredeemlist(true);
        body15.setRedeemId(redeemId);
        Log.d("Redeem__PointsQRDEAt", "body15   " + body15);*/

        voucherManageBody.setMerId("120022732");
        voucherManageBody.setCustId("320010706194775");
        voucherManageBody.setDeviceId("2002271337305070");
        voucherManageBody.setCbLedgerId("");
        voucherManageBody.setIsSharedVoucherTxn(true);
        voucherManageBody.setVoucherType("P");
        voucherManageBody.setVoucherId("6200305095140120");

    }

    private void getDataforredreemid(VoucherManageBody requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.genericVoucherManageAsync(requestBody, new ApiCallback<VoucherManageResponse>() {

            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(final VoucherManageResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("Redeem---", "run: " + statusCode + "," + result.getStatusCode());
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        intermediateAlertDialog.dismissAlertDialog();
                        Log.d("Redeem---", "run: " + result);
                        if (statusCode == 200) {

                            String cus_name = result.getCustName();
                            String cust_id = result.getCustId();
                            String redeem_message = result.getRedeemMessage();
                            String status_message = result.getStatusMessage();
                            String referral_reward = result.getReferralReward();
                            String status_code = result.getStatusCode();
                            Log.d("Redeem---", "run: " + cus_name + "," + cust_id + "," + redeem_message + "," + status_message + "," + referral_reward + "," + status_code);
                            //dismiss bottom sheet dialog
                            redeemProductBottomSheet.dismissOnProceed();

                            Bundle bundle = new Bundle();
                            bundle.putString("cus_name", cus_name);
                            bundle.putString("cust_id", cust_id);
                            bundle.putString("redeem_message", redeem_message);
                            bundle.putString("status_message", status_message);
                            bundle.putString("referral_reward", referral_reward);
                            RedeemPointsSuccess fragment1 = new RedeemPointsSuccess();
                            fragment1.setArguments(bundle);
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                            fragmentTransaction.replace(R.id.redeem_points_frame_layout, fragment1);
                            fragmentTransaction.commit();
                            Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                          /*  new AlertDialogYesNo(getActivity(), "Redeem product", redeem_message, "No", "Yes") {
                                @Override
                                public void onOKButtonClick() {
                                    callRedeemAPI();
                                }

                                @Override
                                public void onCancelButtonClick() {
                                    onBackPressed();
                                }
                            };*/
                        } else {
                            new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), Homenew.class));
                                    Objects.requireNonNull(getActivity()).finish();
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
        startActivity(new Intent(getActivity(), Homenew.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        Objects.requireNonNull(getActivity()).finish();
        getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        return true;
    }

    private void callRedeemAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                getRequestBody22();
                ponitsReddem(body22);
            } catch (Exception e) {
                intermediateAlertDialog.dismissAlertDialog();
                new AlertDialogFailure(getActivity(), " Please try again later", "OK", "Something went wrong") {
                    @Override
                    public void onButtonClick() {
                        startActivity(new Intent(getActivity(), Homenew.class));
                        Objects.requireNonNull(getActivity()).finish();
                    }
                };
            }
        } else {
            new AlertDialogFailure(getActivity(), "Please turn ON your data connection", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callAPI();
                }
            };
        }
    }

    private void getRequestBody22() {
        body22.setCustId(custId);
        body22.setDeviceId(preferences.getString(DEVICE_ID, ""));
        body22.setMerCbredeemId(redeemId);
        body22.setMobileNo("");
        body22.setMerId(preferences.getString(MERCHANT_ID, ""));
        body22.setSessiontoken("");
        Log.d("Redeem__PointsQRDEAt", "body22  " + body22);
    }

    private void ponitsReddem(Body22 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        ClientApisApi clientApisApi = new ClientApisApi();
        clientApisApi.setApiClient(okHttpApiClient.getApiClient());

        clientApisApi.genericPntsRedeemPostAsync(requestBody, new ApiCallback<InlineResponse20016>() {

            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    intermediateAlertDialog.dismissAlertDialog();
                    if (statusCode == 406) {
                        new AlertDialogFailure(getActivity(), "Cashback point balance is 0 ", "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    } else if (statusCode == 417) {
                        String start = startTime.substring(0, 2)
                                .concat(":" + startTime.substring(2) + " ");
                        String end = endtime.substring(0, 2)
                                .concat(":" + endtime.substring(2) + " ");
                        new AlertDialogFailure(getActivity(), "Timed Special Offer\n\nThis offer is available for redemption\nonly between " + start + " to " + end, "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    } else if (statusCode == 404) {
                        new AlertDialogFailure(getActivity(), "Customer is not a member of this merchant !\n\n Please try again later", "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    } else if (statusCode == 204) {
                        new AlertDialogFailure(getActivity(), "Cashback points record is not available ! \n\n Please try again later", "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    } else if (statusCode == 405) {
                        new AlertDialogFailure(getActivity(), "Redeem offer expired \n", "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    } else if (statusCode == 403) {
                        new AlertDialogFailure(getActivity(), "Redeem offer is not active today \n", "OK", "Failed") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    } else {
                        new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    }

                });

            }

            @Override
            public void onSuccess(final InlineResponse20016 result, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    intermediateAlertDialog.dismissAlertDialog();
                    if (statusCode == 200) {
                        if (result != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("MOBILE_NUMBER", "9876");
                            bundle.putString("POINTS_DEDUCTED", productAmnt);
                            bundle.putString("PRODUCT_REDEEMED", result.getRedeemTitle());
                            bundle.putString("BALANCE_POINTS", result.getPointsNewBalance());
                            bundle.putString("CUSTOMER_ID", custId);
                            bundle.putString("REDEEM_ID", redeemId);
                            bundle.putString("CUSTOMER_POINTS_EXPIRY", result.getPointsExpiryDt());
                            RedeemPointsSuccess fragment1 = new RedeemPointsSuccess();
                            fragment1.setArguments(bundle);
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                            fragmentTransaction.replace(R.id.redeem_points_frame_layout, fragment1);
                            fragmentTransaction.commit();
                            Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                        } else {
                            new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), Homenew.class));
                                    Objects.requireNonNull(getActivity()).finish();
                                }
                            };
                        }
                    } else {
                        new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Customer does not have any points") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), Homenew.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
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

    private void callAPI_getRedeemId() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(getActivity())) {
            try {
                fetchVoucherList(fetchRedeemVoucherListRequestBody);
            } catch (Exception e) {

                intermediateAlertDialog.dismissAlertDialog();
                new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                    @Override
                    public void onButtonClick() {

                    }
                };
            }
        } else {

            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(getActivity(), "Please turn ON your data connection ", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callAPI_getRedeemId();
                }
            };
        }
    }


    private void fetchVoucherList(FetchRedeemVoucherListRequestBody requestBody) throws ApiException {
        Log.d("Voucher---", "uploadVoucher: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.genericVoucherGetListAsync(requestBody, new ApiCallback<RedeemVoucherListResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("VoucherList---", "onFailure: " + e.getMessage() + "," + statusCode);
                intermediateAlertDialog.dismissAlertDialog();
                if (statusCode == 500) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialogYesNo = new AlertDialogYesNo(getActivity(), "", "Merchant does not exist or Inactive", "OK", "CANCEL") {
                                @Override
                                public void onOKButtonClick() {

                                }

                                @Override
                                public void onCancelButtonClick() {

                                }
                            };
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialogYesNo = new AlertDialogYesNo(getActivity(), "", "Something went wrong. Please Try Again!", "YES", "OK") {
                                @Override
                                public void onOKButtonClick() {

                                }

                                @Override
                                public void onCancelButtonClick() {

                                }
                            };
                        }
                    });
                }
            }

            @Override
            public void onSuccess(RedeemVoucherListResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                if (statusCode == 200) {
                    intermediateAlertDialog.dismissAlertDialog();
                    List<RedeemVoucher> redeemVouchers = result.getRedeemVouchers();
                    Log.d("VoucherList---", "onsuccess: " + result.getStatusCode() + " , " + statusCode + " , " + redeemVouchers.size());
                    for (int t = 0; t < redeemVouchers.size(); t++) {
                        String title = redeemVouchers.get(t).getRedeemTitle();
                        String desc = redeemVouchers.get(t).getRedeemDescription();
                        String lead_title = redeemVouchers.get(t).getLeadTitle();
                        String lead_desc = redeemVouchers.get(t).getLeadDescription();
                        String act_dt = redeemVouchers.get(t).getRedeemActivedt();
                        String end_dt = redeemVouchers.get(t).getRedeemExpdt();
                        String act_time = redeemVouchers.get(t).getRedeemActtime();
                        String exp_time = redeemVouchers.get(t).getRedeemEndtime();
                        String weekdays = redeemVouchers.get(t).getRedeemActivedays();
                        String max_redeem = redeemVouchers.get(t).getMaxRedemptionAllowed();
                        String assignedVoucherCount = redeemVouchers.get(t).getAssignedVoucherCount();
                        String voucher_bg = redeemVouchers.get(t).getVoucherBg();
                        String points = redeemVouchers.get(t).getRedeemPoints();
                        String wallet = redeemVouchers.get(t).getRedeemDepositAmt();
                        String assgn_voucher_id = redeemVouchers.get(t).getAssignedVoucherId();
                        String redeem_type = redeemVouchers.get(t).getRedeemType();
                        boolean issharable = redeemVouchers.get(t).isIsCustSharable();
                        String mer_cb_redeem_id = redeemVouchers.get(t).getMerCbRedeemId();
                        Log.d("VoucherList---", "data--: " + title + "," + desc + "," + redeem_type + "," + lead_title + "," + lead_desc+ "," + points + "," + wallet+","+redeem_type);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialogYesNo = new AlertDialogYesNo(getActivity(), "Voucher Details", "Title : "+title, "OK", "CANCEL") {
                                    @Override
                                    public void onOKButtonClick() {
                                        callAPI();
                                    }

                                    @Override
                                    public void onCancelButtonClick() {
                                        startActivity(new Intent(getActivity(), Homenew.class));
                                        Objects.requireNonNull(getActivity()).finish();
                                    }
                                };
                            }
                        });
                    }

                } else if (statusCode == 500) {
                    intermediateAlertDialog.dismissAlertDialog();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialogYesNo = new AlertDialogYesNo(getActivity(), "", "Merchant does not exist or Inactive", "OK", "OK") {
                                @Override
                                public void onOKButtonClick() {

                                }

                                @Override
                                public void onCancelButtonClick() {

                                }
                            };
                        }
                    });
                } else {
                    intermediateAlertDialog.dismissAlertDialog();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialogYesNo = new AlertDialogYesNo(getActivity(), "", "Something went wrong. Please Try Again!", "YES", "OK") {
                                @Override
                                public void onOKButtonClick() {

                                }

                                @Override
                                public void onCancelButtonClick() {

                                }
                            };
                        }
                    });
                }

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
