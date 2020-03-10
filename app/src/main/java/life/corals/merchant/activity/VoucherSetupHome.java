package life.corals.merchant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.adapter.Vouchers_RecyclerviewAdapter;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.FetchRedeemVoucherListRequestBody;
import life.corals.merchant.client.model.RedeemVoucher;
import life.corals.merchant.client.model.RedeemVoucherListResponse;
import life.corals.merchant.client.model.SetUpRedemptionList;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AppTimeOutManagerUtil;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.Util;

public class VoucherSetupHome extends AppCompatActivity {

    CardView textView_add_redeem;
    FetchRedeemVoucherListRequestBody fetchRedeemVoucherListRequestBody;
    List<RedeemVoucher> redeemVouchers;
    RecyclerView recyclerView_P, recyclerView_B, recyclerView_U, recyclerView_Z, recyclerView_M;
    TextView tv_no_p, tv_no_b, tv_no_u, tv_no_z, tv_no_m;
    ImageView imageView_add_p, imageView_add_b, imageView_add_z, imageView_add_u, imageView_add_m;
    // voucher request code
    public static final int GET_VOUCHER_LIST = 0;
    public static final int GET_SINGLE_VOUCHER_DETAILS = 1;

    ArrayList<SetUpRedemptionList> setUpRedemptionLists_P;
    ArrayList<SetUpRedemptionList> setUpRedemptionLists_B;
    ArrayList<SetUpRedemptionList> setUpRedemptionLists_U;
    ArrayList<SetUpRedemptionList> setUpRedemptionLists_Z;
    ArrayList<SetUpRedemptionList> setUpRedemptionLists_M;
    CardView cardView_p, cardView_b, cardView_u, cardView_z, cardView_m;
    private IntermediateAlertDialog intermediateAlertDialog;
    private AppTimeOutManagerUtil appTimeOutManagerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_setup_home);

        Toolbar toolbar = findViewById(R.id.toolbar_voucher_setup);

        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        setSupportActionBar(toolbar);

        appTimeOutManagerUtil = new AppTimeOutManagerUtil(VoucherSetupHome.this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        redeemVouchers = new ArrayList<>();
        setUpRedemptionLists_P = new ArrayList<>();
        setUpRedemptionLists_B = new ArrayList<>();
        setUpRedemptionLists_U = new ArrayList<>();
        setUpRedemptionLists_Z = new ArrayList<>();
        setUpRedemptionLists_M = new ArrayList<>();

        tv_no_p = (TextView) findViewById(R.id.text_no_voucher_p);
        tv_no_b = (TextView) findViewById(R.id.text_no_voucher_b);
        tv_no_u = (TextView) findViewById(R.id.text_no_voucher_u);
        tv_no_z = (TextView) findViewById(R.id.text_no_voucher_z);
        tv_no_m = (TextView) findViewById(R.id.text_no_voucher_m);

        imageView_add_p = (ImageView) findViewById(R.id.img_add_p);
        imageView_add_b = (ImageView) findViewById(R.id.img_add_b);
        imageView_add_u = (ImageView) findViewById(R.id.img_add_u);
        imageView_add_z = (ImageView) findViewById(R.id.img_add_z);
        imageView_add_m = (ImageView) findViewById(R.id.img_add_m);

        cardView_p = (CardView) findViewById(R.id.card_no_voucher_p);
        cardView_b = (CardView) findViewById(R.id.card_no_voucher_b);
        cardView_u = (CardView) findViewById(R.id.card_no_voucher_u);
        cardView_z = (CardView) findViewById(R.id.card_no_voucher_z);
        cardView_m = (CardView) findViewById(R.id.card_no_voucher_m);

        textView_add_redeem = (CardView) findViewById(R.id.text_add_redeem);
        recyclerView_P = findViewById(R.id.recyclerview_redemptions_P);
        recyclerView_B = findViewById(R.id.recyclerview_redemptions_B);
        recyclerView_U = findViewById(R.id.recyclerview_redemptions_U);
        recyclerView_Z = findViewById(R.id.recyclerview_redemptions_Z);
        recyclerView_M = findViewById(R.id.recyclerview_redemptions_M);

        LinearLayoutManager li_p = new LinearLayoutManager(this);
        LinearLayoutManager li_b = new LinearLayoutManager(this);
        LinearLayoutManager li_u = new LinearLayoutManager(this);
        LinearLayoutManager li_z = new LinearLayoutManager(this);
        LinearLayoutManager li_m = new LinearLayoutManager(this);
        recyclerView_P.setLayoutManager(li_p);
        recyclerView_B.setLayoutManager(li_b);
        recyclerView_U.setLayoutManager(li_u);
        recyclerView_Z.setLayoutManager(li_z);
        recyclerView_M.setLayoutManager(li_m);

        fetchRedeemVoucherListRequestBody = new FetchRedeemVoucherListRequestBody();
        fetchRedeemVoucherListRequestBody.setCallerType("m");
        fetchRedeemVoucherListRequestBody.setMerId("120022732");
        fetchRedeemVoucherListRequestBody.setDeviceId("2002271337305070");
        fetchRedeemVoucherListRequestBody.setSessionToken("NoToken");
        fetchRedeemVoucherListRequestBody.setVoucherType("ALL");
        fetchRedeemVoucherListRequestBody.setRequestCode(String.valueOf(GET_VOUCHER_LIST));

        textView_add_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VoucherSetupHome.this, VoucherSetupTypes.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        imageView_add_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupHome.this, VoucherSetupCreate.class);
                in.putExtra("redeem_type", "P");
                in.putExtra("type_code", "1");
                in.putExtra("create_update_code", "1");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
        imageView_add_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupHome.this, VoucherSetupCreate.class);
                in.putExtra("redeem_type", "B");
                in.putExtra("type_code", "1");
                in.putExtra("create_update_code", "1");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
        imageView_add_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupHome.this, VoucherSetupCreate.class);
                in.putExtra("redeem_type", "U");
                in.putExtra("type_code", "1");
                in.putExtra("create_update_code", "1");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
        imageView_add_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupHome.this, VoucherSetupCreate.class);
                in.putExtra("redeem_type", "Z");
                in.putExtra("type_code", "1");
                in.putExtra("create_update_code", "1");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
        imageView_add_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupHome.this, VoucherSetupCreate.class);
                in.putExtra("redeem_type", "M");
                in.putExtra("type_code", "1");
                in.putExtra("create_update_code", "1");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        callAPI();

    }

    public void callAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(VoucherSetupHome.this);
        if (Util.getConnectivityStatusString(this)) {
            try {
                fetchVoucherList(fetchRedeemVoucherListRequestBody);
            } catch (Exception e) {

                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                new AlertDialogFailure(this, "Please try again later!", "OK", "Something went wrong") {
                    @Override
                    public void onButtonClick() {
                        startActivity(new Intent(VoucherSetupHome.this, Homenew.class));
                        finish();
                        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                    }
                };
            }
        } else {
            if (intermediateAlertDialog != null) {
                intermediateAlertDialog.dismissAlertDialog();
            }
            new AlertDialogFailure(VoucherSetupHome.this, "Please turn ON your data connection ", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callAPI();
                }
            };
        }
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(VoucherSetupHome.this, Homenew.class));
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }

    public void fetchVoucherList(FetchRedeemVoucherListRequestBody requestBody) throws ApiException {

        Log.d("Voucher---", "uploadVoucher: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(VoucherSetupHome.this));
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.genericVoucherGetListAsync(requestBody, new ApiCallback<RedeemVoucherListResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("VoucherList---", "onFailure: " + e.getMessage() + "," + statusCode);

                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (statusCode == 500) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupHome.this, "Merchant does not exist or Inactive", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupHome.this, Homenew.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            };
                        }
                    });

                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupHome.this, "Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupHome.this, Homenew.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            };
                        }
                    });

                }
            }

            @Override
            public void onSuccess(RedeemVoucherListResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                if (statusCode == 200) {
                    List<RedeemVoucher> redeemVouchers = result.getRedeemVouchers();
                    Log.d("redeemVouchers---", "onsuccess: " +redeemVouchers);
                    ;
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
                        boolean isActive = redeemVouchers.get(t).isIsActive();
                        String mer_cb_redeem_id = redeemVouchers.get(t).getMerCbRedeemId();
                        String terms_conditions = redeemVouchers.get(t).getTermsAndConditions();

                        Log.d("VoucherList---", "data--: " + title + "," + desc + "," + redeem_type + "," + lead_title + "," + lead_desc);

                        if (redeem_type.equals("P")) {
                            SetUpRedemptionList sp = new SetUpRedemptionList();
                            sp.setRedeemTitle(title);
                            sp.setRedeemDescription(desc);
                            sp.setRedeemActivedt(act_dt);
                            sp.setRedeemActtime(act_time);
                            sp.setRedeemExpdt(end_dt);
                            sp.setRedeemEndtime(exp_time);
                            sp.setRedeemActivedays(weekdays);
                            sp.setMaxRedemptionAllowed(max_redeem);
                            sp.setAssignedVoucherCount(assignedVoucherCount);
                            sp.setVoucherBg(voucher_bg);
                            sp.setRedeemPoints(points);
                            sp.setRedeemDepositAmt(wallet);
                            sp.setAssignedVoucherId(assgn_voucher_id);
                            sp.setRedeemType(redeem_type);
                            sp.setIsCustSharable(issharable);
                            sp.setMerCbRedeemId(mer_cb_redeem_id);
                            sp.setIsActive(isActive);
                            sp.setTermsConditions(terms_conditions);
                            setUpRedemptionLists_P.add(sp);

                        } else if (redeem_type.equals("B")) {
                            SetUpRedemptionList sb = new SetUpRedemptionList();
                            sb.setRedeemTitle(title);
                            sb.setRedeemDescription(desc);
                            sb.setRedeemActivedt(act_dt);
                            sb.setRedeemActtime(act_time);
                            sb.setRedeemExpdt(end_dt);
                            sb.setRedeemEndtime(exp_time);
                            sb.setRedeemActivedays(weekdays);
                            sb.setMaxRedemptionAllowed(max_redeem);
                            sb.setAssignedVoucherCount(assignedVoucherCount);
                            sb.setVoucherBg(voucher_bg);
                            sb.setRedeemPoints(points);
                            sb.setRedeemDepositAmt(wallet);
                            sb.setAssignedVoucherId(assgn_voucher_id);
                            sb.setRedeemType(redeem_type);
                            sb.setIsCustSharable(issharable);
                            sb.setMerCbRedeemId(mer_cb_redeem_id);
                            sb.setIsActive(isActive);
                            sb.setTermsConditions(terms_conditions);
                            setUpRedemptionLists_B.add(sb);

                        } else if (redeem_type.equals("U")) {
                            SetUpRedemptionList su = new SetUpRedemptionList();
                            su.setRedeemTitle(title);
                            su.setRedeemDescription(desc);
                            su.setRedeemActivedt(act_dt);
                            su.setRedeemActtime(act_time);
                            su.setRedeemExpdt(end_dt);
                            su.setRedeemEndtime(exp_time);
                            su.setRedeemActivedays(weekdays);
                            su.setMaxRedemptionAllowed(max_redeem);
                            su.setAssignedVoucherCount(assignedVoucherCount);
                            su.setVoucherBg(voucher_bg);
                            su.setRedeemPoints(points);
                            su.setRedeemDepositAmt(wallet);
                            su.setAssignedVoucherId(assgn_voucher_id);
                            su.setRedeemType(redeem_type);
                            su.setIsCustSharable(issharable);
                            su.setMerCbRedeemId(mer_cb_redeem_id);
                            su.setIsActive(isActive);
                            su.setTermsConditions(terms_conditions);
                            setUpRedemptionLists_U.add(su);

                        } else if (redeem_type.equals("Z")) {
                            SetUpRedemptionList sz = new SetUpRedemptionList();
                            sz.setRedeemTitle(title);
                            sz.setRedeemDescription(desc);
                            sz.setRedeemActivedt(act_dt);
                            sz.setRedeemActtime(act_time);
                            sz.setRedeemExpdt(end_dt);
                            sz.setRedeemEndtime(exp_time);
                            sz.setRedeemActivedays(weekdays);
                            sz.setMaxRedemptionAllowed(max_redeem);
                            sz.setAssignedVoucherCount(assignedVoucherCount);
                            sz.setVoucherBg(voucher_bg);
                            sz.setRedeemPoints(points);
                            sz.setRedeemDepositAmt(wallet);
                            sz.setAssignedVoucherId(assgn_voucher_id);
                            sz.setRedeemType(redeem_type);
                            sz.setIsCustSharable(issharable);
                            sz.setMerCbRedeemId(mer_cb_redeem_id);
                            sz.setIsActive(isActive);
                            sz.setTermsConditions(terms_conditions);
                            setUpRedemptionLists_Z.add(sz);


                        } else if (redeem_type.equals("M")) {
                            SetUpRedemptionList sm = new SetUpRedemptionList();
                            sm.setRedeemTitle(title);
                            sm.setRedeemDescription(desc);
                            sm.setLeadTitle(lead_title);
                            sm.setLeadDescription(lead_desc);
                            sm.setRedeemActivedt(act_dt);
                            sm.setRedeemActtime(act_time);
                            sm.setRedeemExpdt(end_dt);
                            sm.setRedeemEndtime(exp_time);
                            sm.setRedeemActivedays(weekdays);
                            sm.setMaxRedemptionAllowed(max_redeem);
                            sm.setAssignedVoucherCount(assignedVoucherCount);
                            sm.setVoucherBg(voucher_bg);
                            sm.setRedeemPoints(points);
                            sm.setRedeemDepositAmt(wallet);
                            sm.setAssignedVoucherId(assgn_voucher_id);
                            sm.setRedeemType(redeem_type);
                            sm.setIsCustSharable(issharable);
                            sm.setMerCbRedeemId(mer_cb_redeem_id);
                            sm.setIsActive(isActive);
                            sm.setTermsConditions(terms_conditions);
                            setUpRedemptionLists_M.add(sm);
                        }
                    }
                    Log.d("VoucherList---", "onSuccess: " + setUpRedemptionLists_P.size() + "," + setUpRedemptionLists_B.size() + "," + setUpRedemptionLists_U.size() + "," + setUpRedemptionLists_Z.size() + "," + setUpRedemptionLists_M.size());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (setUpRedemptionLists_P.size() != 0) {
                                recyclerView_P.setVisibility(View.VISIBLE);
                                cardView_p.setVisibility(View.GONE);
                                Vouchers_RecyclerviewAdapter vouchers_recyclerviewAdapter_p = new Vouchers_RecyclerviewAdapter(VoucherSetupHome.this, setUpRedemptionLists_P);
                                recyclerView_P.setAdapter(vouchers_recyclerviewAdapter_p);
                                vouchers_recyclerviewAdapter_p.notifyDataSetChanged();
                            } else {
                                recyclerView_P.setVisibility(View.GONE);
                                cardView_p.setVisibility(View.VISIBLE);
                            }

                            if (setUpRedemptionLists_B.size() != 0) {
                                recyclerView_B.setVisibility(View.VISIBLE);
                                cardView_b.setVisibility(View.GONE);
                                Vouchers_RecyclerviewAdapter vouchers_recyclerviewAdapter_b = new Vouchers_RecyclerviewAdapter(VoucherSetupHome.this, setUpRedemptionLists_B);
                                recyclerView_B.setAdapter(vouchers_recyclerviewAdapter_b);
                                vouchers_recyclerviewAdapter_b.notifyDataSetChanged();
                            } else {
                                recyclerView_B.setVisibility(View.GONE);
                                cardView_b.setVisibility(View.VISIBLE);
                            }

                            if (setUpRedemptionLists_U.size() != 0) {
                                recyclerView_U.setVisibility(View.VISIBLE);
                                cardView_u.setVisibility(View.GONE);
                                Vouchers_RecyclerviewAdapter vouchers_recyclerviewAdapter_u = new Vouchers_RecyclerviewAdapter(VoucherSetupHome.this, setUpRedemptionLists_U);
                                recyclerView_U.setAdapter(vouchers_recyclerviewAdapter_u);
                                vouchers_recyclerviewAdapter_u.notifyDataSetChanged();
                            } else {
                                recyclerView_U.setVisibility(View.GONE);
                                cardView_u.setVisibility(View.VISIBLE);
                            }

                            if (setUpRedemptionLists_Z.size() != 0) {
                                recyclerView_Z.setVisibility(View.VISIBLE);
                                cardView_z.setVisibility(View.GONE);
                                Vouchers_RecyclerviewAdapter vouchers_recyclerviewAdapter_z = new Vouchers_RecyclerviewAdapter(VoucherSetupHome.this, setUpRedemptionLists_Z);
                                recyclerView_Z.setAdapter(vouchers_recyclerviewAdapter_z);
                                vouchers_recyclerviewAdapter_z.notifyDataSetChanged();
                            } else {
                                recyclerView_Z.setVisibility(View.GONE);
                                cardView_z.setVisibility(View.VISIBLE);
                            }

                            if (setUpRedemptionLists_M.size() != 0) {
                                recyclerView_M.setVisibility(View.VISIBLE);
                                cardView_m.setVisibility(View.GONE);
                                Vouchers_RecyclerviewAdapter vouchers_recyclerviewAdapter_m = new Vouchers_RecyclerviewAdapter(VoucherSetupHome.this, setUpRedemptionLists_M);
                                recyclerView_M.setAdapter(vouchers_recyclerviewAdapter_m);
                                vouchers_recyclerviewAdapter_m.notifyDataSetChanged();
                            } else {
                                recyclerView_M.setVisibility(View.GONE);
                                cardView_m.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }


                } else if (statusCode == 500) {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupHome.this, "Merchant does not exist or Inactive", "OK", "Failed") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupHome.this, Homenew.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            };
                        }
                    });


                } else {
                    if (intermediateAlertDialog != null) {
                        intermediateAlertDialog.dismissAlertDialog();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupHome.this, "Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupHome.this, Homenew.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
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

    @Override
    protected void onResume() {
        super.onResume();
        appTimeOutManagerUtil.onResumeApp();
    }

    @Override
    protected void onPause() {
        super.onPause();
        appTimeOutManagerUtil.onPauseApp();
    }

}
