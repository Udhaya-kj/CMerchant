package life.corals.merchant.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
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
import life.corals.merchant.client.model.FetchRedeemVoucherListRequestBody;
import life.corals.merchant.client.model.RedeemVoucher;
import life.corals.merchant.client.model.RedeemVoucherListResponse;
import life.corals.merchant.client.model.VoucherManageBody;
import life.corals.merchant.client.model.VoucherManageResponse;
import life.corals.merchant.utils.AlertBonusRedeem;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertDialogYesNo;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.QRHandlerUtils;
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


    private IntermediateAlertDialog intermediateAlertDialog;
    private RedeemProductBottomSheet redeemProductBottomSheet;
    private String startTime = "";
    private String endtime = "";

    private FetchRedeemVoucherListRequestBody fetchRedeemVoucherListRequestBody = new FetchRedeemVoucherListRequestBody();
    private VoucherManageBody voucherManageBody = new VoucherManageBody();

    // voucher request code
    public static final String GET_VOUCHER_LIST = "0";
    private static final String GET_SINGLE_VOUCHER_DETAILS = "1";
    private AlertDialogYesNo alertDialogYesNo;

    private String voucherId = null;
    private String countryCode = null;
    private String voucherType = null;
    private String custId = null;
    private String QrMerID = null;
    private String ledgerId = null;
    private String cus_mob = null;

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

        redeemProductBottomSheet = new RedeemProductBottomSheet(getActivity()) {
            @Override
            public void onCancelButtonCLick() {
                onBackPressed();
            }

            @Override
            public void onProceedButtonClick() {
            }
        };

        new RedeemPointsQRScannerUtils(surfaceView, getActivity()) {
            @Override
            public void parsedScanData(HashMap<String, String> stringHashMap) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (stringHashMap != null) {
                            voucherId = stringHashMap.get(QRHandlerUtils.REDEEM_ID);
                            ledgerId = stringHashMap.get(QRHandlerUtils.LEDGER_ID);
                            countryCode = stringHashMap.get(QRHandlerUtils.COUNTRY_CODE);
                            custId = stringHashMap.get(QRHandlerUtils.CUSTOMER_ID);
                            QrMerID = stringHashMap.get(QRHandlerUtils.MERCHANT_ID);
                            voucherType = stringHashMap.get(QRHandlerUtils.REDEEM_TYPE);
                            Log.d("ledgerIdledgerId", "callRedeemAPI: 11111 " + ledgerId);

                            // if(voucherType.equals("B"))
                            if (!TextUtils.isEmpty(QrMerID) && !TextUtils.isEmpty(voucherId) && !TextUtils.isEmpty(custId) && !TextUtils.isEmpty(voucherType)) {
                                if (QrMerID.equals(preferences.getString(MERCHANT_ID, ""))) {
                                    callAPI_getRedeemDetails();

                                } else {
                                    new AlertDialogFailure(getActivity(), "This is not your redeem offer", "OK", "Failed") {
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
                Log.d("Redeem__PointsQRDEAt", "onFailureScan");
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


    private void callAPI_getRedeemDetails() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {

            try {
                Log.d("ledgerIdledgerId", "callRedeemAPI: 124 " + ledgerId);
                fetchRedeemVoucherListRequestBody.setCallerType("m");
                fetchRedeemVoucherListRequestBody.setMerId(preferences.getString(MERCHANT_ID, ""));
                fetchRedeemVoucherListRequestBody.setDeviceId(preferences.getString(DEVICE_ID, ""));
                fetchRedeemVoucherListRequestBody.setRequestCode(GET_SINGLE_VOUCHER_DETAILS);
                fetchRedeemVoucherListRequestBody.setVoucherType(voucherType);
                fetchRedeemVoucherListRequestBody.setCustId(custId);
                fetchRedeemVoucherListRequestBody.setVoucherId(voucherId);
                fetchVoucher(fetchRedeemVoucherListRequestBody);
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
                    callAPI_getRedeemDetails();
                }
            };
        }
    }


    private void fetchVoucher(FetchRedeemVoucherListRequestBody requestBody) throws ApiException {
        Log.d("Voucher---", "uploadVoucher: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.genericVoucherGetListAsync(requestBody, new ApiCallback<RedeemVoucherListResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("VoucherList---", "onFailure: " + e.getMessage() + "," + statusCode);
                intermediateAlertDialog.dismissAlertDialog();
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onSuccess(RedeemVoucherListResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (statusCode == 200) {
                            intermediateAlertDialog.dismissAlertDialog();
                            List<RedeemVoucher> redeemVouchers = result.getRedeemVouchers();
                            Log.d("VoucherList---", "onsuccess: " + result.getStatusCode() + " , " + statusCode + " , " + redeemVouchers.size());

                            if (Integer.parseInt(result.getStatusCode()) == 200) {
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
                                    Log.d("VoucherList---", "data--: " + title + "," + desc + "," + redeem_type + "," + lead_title + "," + lead_desc + "," + points + "," + wallet + "," + redeem_type);

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            if (redeem_type.equals("M")) {

                                                SpannableString spannableString = new SpannableString(lead_title);
                                                spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, lead_title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        alertDialogYesNo = new AlertDialogYesNo(getActivity(), "Voucher Details", spannableString + "\n" + lead_desc, "CONTINUE", "CANCEL") {
                                                            @Override
                                                            public void onOKButtonClick() {
                                                                callRedeemAPI();
                                                            }

                                                            @Override
                                                            public void onCancelButtonClick() {
                                                                startActivity(new Intent(getActivity(), Homenew.class));
                                                                Objects.requireNonNull(getActivity()).finish();
                                                            }
                                                        };
                                                    }
                                                });

                                            } else {

                                                SpannableString spannableString = new SpannableString(title);
                                                spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        alertDialogYesNo = new AlertDialogYesNo(getActivity(), "Voucher Details", spannableString + "\n" + desc, "CONTINUE", "CANCEL") {
                                                            @Override
                                                            public void onOKButtonClick() {
                                                                callRedeemAPI();
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

                                        }


                                    });
                                }
                            } else if (Integer.parseInt(result.getStatusCode()) == 204) {

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialogFailure(getActivity(), "Voucher already redeemed", "OK", "Failed") {
                                            @Override
                                            public void onButtonClick() {
                                                startActivity(new Intent(getActivity(), Homenew.class));
                                                Objects.requireNonNull(getActivity()).finish();
                                            }
                                        };
                                    }
                                });

                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialogFailure(getActivity(), result.getStatusMessage(), "OK", "") {
                                            @Override
                                            public void onButtonClick() {
                                                startActivity(new Intent(getActivity(), Homenew.class));
                                                Objects.requireNonNull(getActivity()).finish();
                                            }
                                        };
                                    }
                                });


                            }


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

    private void callRedeemAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                Log.d("ledgerIdledgerId", "callRedeemAPI: " + ledgerId);
                voucherManageBody.setCallerTye("m");
                if (ledgerId == null) {
                    voucherManageBody.setIsSharedVoucherTxn(false);
                } else voucherManageBody.setIsSharedVoucherTxn(true);
                voucherManageBody.setCbLedgerId(ledgerId);
                voucherManageBody.setCustId(custId);
                voucherManageBody.setDeviceId(preferences.getString(DEVICE_ID, ""));
                voucherManageBody.setMerId(preferences.getString(MERCHANT_ID, ""));
                voucherManageBody.setVoucherId(voucherId);
                voucherManageBody.setVoucherType(voucherType);
                voucherManageBody.setSessionToken("notoken");
                voucherManageBody.setMobileNo(cus_mob);
                getRedreemVocuher(voucherManageBody);
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
                    callRedeemAPI();
                }
            };
        }
    }

    private void getRedreemVocuher(VoucherManageBody requestBody) throws ApiException {
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
                        redeemProductBottomSheet.dismissOnProceed();
                        intermediateAlertDialog.dismissAlertDialog();
                        Log.d("Redeem__Dialog", "run: " + result);
                        if (statusCode == 200) {
                            if (Integer.parseInt(result.getStatusCode()) == 200) {
                                String cus_name = result.getCustName();
                                String cust_id = result.getCustId();
                                String redeem_message = result.getRedeemMessage();
                                String status_message = result.getStatusMessage();
                                String referral_reward = result.getReferralReward();
                                String status_code = result.getStatusCode();
                                String points_exp_dt = result.getRedeemPointExpDate();
                                String points_bal = result.getRedeemPointBalance();
                                String cus_wallet_bal = result.getCustWalletBalance();
                                String wallet_balance_exp_date = result.getWalletBalanceExpDate();
                                String redeem_deposit_amount = result.getRedeemDepositAmount();
                                Bundle bundle = new Bundle();
                                bundle.putString("cus_name", cus_name);
                                bundle.putString("cust_id", cust_id);
                                bundle.putString("redeem_message", redeem_message);
                                bundle.putString("status_message", status_message);
                                bundle.putString("referral_reward", referral_reward);
                                bundle.putString("cus_wallet_bal", cus_wallet_bal);
                                bundle.putString("wallet_balance_exp_date", wallet_balance_exp_date);
                                bundle.putString("redeem_deposit_amount", redeem_deposit_amount);
                                bundle.putString("points_bal", points_bal);
                                bundle.putString("points_exp_dt", points_exp_dt);
                                RedeemPointsSuccess fragment1 = new RedeemPointsSuccess();
                                fragment1.setArguments(bundle);
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                                fragmentTransaction.replace(R.id.redeem_points_frame_layout, fragment1);
                                fragmentTransaction.commit();
                                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                            } else
                                new AlertDialogFailure(getActivity(), result.getStatusMessage(), "OK", "") {
                                    @Override
                                    public void onButtonClick() {
                                        startActivity(new Intent(getActivity(), Homenew.class));
                                        Objects.requireNonNull(getActivity()).finish();
                                    }
                                };
                        } /*else if (Integer.parseInt(result.getStatusCode()) == 408) {
                                new AlertDialogFailure(getActivity(), "Voucher expired", "OK", "Oops !") {
                                    @Override
                                    public void onButtonClick() {
                                        startActivity(new Intent(getActivity(), Homenew.class));
                                        Objects.requireNonNull(getActivity()).finish();
                                    }
                                };
                            } else if (Integer.parseInt(result.getStatusCode()) == 403) {
                                new AlertDialogFailure(getActivity(), "Voucher not redeemable today", "OK", "Oops !") {
                                    @Override
                                    public void onButtonClick() {
                                        startActivity(new Intent(getActivity(), Homenew.class));
                                        Objects.requireNonNull(getActivity()).finish();
                                    }
                                };
                            } else if (Integer.parseInt(result.getStatusCode()) == 503) {
                                new AlertDialogFailure(getActivity(), result.getStatusMessage(), "OK", "Oops !") {
                                    @Override
                                    public void onButtonClick() {
                                        startActivity(new Intent(getActivity(), Homenew.class));
                                        Objects.requireNonNull(getActivity()).finish();
                                    }
                                };
                            } else if (Integer.parseInt(result.getStatusCode()) == 509) {
                                new AlertDialogFailure(getActivity(), "Points expired", "OK", "Oops !") {
                                    @Override
                                    public void onButtonClick() {
                                        startActivity(new Intent(getActivity(), Homenew.class));
                                        Objects.requireNonNull(getActivity()).finish();
                                    }
                                };
                            } else if (Integer.parseInt(result.getStatusCode()) == 410) {
                                new AlertDialogFailure(getActivity(), "Point balance is zero", "OK", "Oops !") {
                                    @Override
                                    public void onButtonClick() {
                                        startActivity(new Intent(getActivity(), Homenew.class));
                                        Objects.requireNonNull(getActivity()).finish();
                                    }
                                };
                            } else if (Integer.parseInt(result.getStatusCode()) == 507) {
                                new AlertDialogFailure(getActivity(), "Point balance is not enough to redeem this offer", "OK", "Oops !") {
                                    @Override
                                    public void onButtonClick() {
                                        startActivity(new Intent(getActivity(), Homenew.class));
                                        Objects.requireNonNull(getActivity()).finish();
                                    }
                                };
                            } else if (Integer.parseInt(result.getStatusCode()) == 502) {
                                new AlertDialogFailure(getActivity(), "Cashback data not found", "OK", "Oops !") {
                                    @Override
                                    public void onButtonClick() {
                                        startActivity(new Intent(getActivity(), Homenew.class));
                                        Objects.requireNonNull(getActivity()).finish();
                                    }
                                };
                            } else if (Integer.parseInt(result.getStatusCode()) == 409) {
                                new AlertDialogFailure(getActivity(), "Redeem voucher failed", "OK", "Oops !") {
                                    @Override
                                    public void onButtonClick() {
                                        startActivity(new Intent(getActivity(), Homenew.class));
                                        Objects.requireNonNull(getActivity()).finish();
                                    }
                                };
                            } else {
                                new AlertDialogFailure(getActivity(), "Something went wrong \n Please try again later", "OK", "Oops !") {
                                    @Override
                                    public void onButtonClick() {
                                        startActivity(new Intent(getActivity(), Homenew.class));
                                        Objects.requireNonNull(getActivity()).finish();
                                    }
                                };
                            }*/ else {
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

}
