package life.corals.merchant.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.adapter.ViewSlider_RecyclerAdapter;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.FetchRedeemVoucherListRequestBody;
import life.corals.merchant.client.model.SetUpRedemptionList;
import life.corals.merchant.client.model.SetUpVoucherList;
import life.corals.merchant.client.model.SetUpVoucherResponse;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertDialogYesNo;
import life.corals.merchant.utils.AlertSuccess;
import life.corals.merchant.utils.AlertVoucherSuccess;
import life.corals.merchant.utils.AppTimeOutManagerUtil;
import life.corals.merchant.utils.IntermediateAlertDialog;

import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;

public class VoucherSetupPreview extends AppCompatActivity {

    private MaterialButton ok_btn, cancel_btn, perf_btn, edit_btn;
    TextView textView_settings1, textView_settings2, text_ask_create_update;
    private List<SetUpRedemptionList> voucher_list;
    String type_code, back_press_code, voucher_type, actual_title, title, desc, lead_title, lead_desc, pur_amount, s_date, e_date, bg_color, st_time, e_time, getActDays, points, sharable, wallet, voucher_count, voucher_id, mer_cb_redeem_id, terms_conditions, ref_reward_points;
    int daysCount = 0;
    private SharedPreferences sharedpreferences_add_redeem;
    public static final String MyPREFERENCES_ADD_VOUCHER = "MyPREFERENCES_ADD_VOUCHER";
    public static final String ADD_VOUCHER = "ADD_VOUCHER";
    private Gson gson;
    private ArrayList<String> daysList;
    private LinearLayout layout_settings2, layout_settings3;
    ImageView imageView_delete;
    private String[] colors;
    private float initialX;
    ScrollView scrollView_voucher;
    private String merId;
    ViewPager2 viewPager2;
    private ArrayList<String> title_list, desc_list, validity_list, colors_list, sharable_list;
    private AlertDialogYesNo alertDialogYesNo;
    boolean isSharable = false, isActive = true;
    LinearLayout layout_et_del, layout_create;
    private IntermediateAlertDialog intermediateAlertDialog;
    FetchRedeemVoucherListRequestBody fetchRedeemVoucherListRequestBody;
    String create_update_code = null;
    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    private SharedPreferences preferences;
    LinearLayout layout_preview_edit;
    public TextView tv_title, tv_desc, tv_val;
    CardView cardView;
    View view;
    LinearLayout layout_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_setup_preview);

        appTimeOutManagerUtil = new AppTimeOutManagerUtil(VoucherSetupPreview.this);
        Toolbar toolbar = findViewById(R.id.toolbar_voucher_preview);
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        preferences = getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);

        sharedpreferences_add_redeem = getSharedPreferences(MyPREFERENCES_ADD_VOUCHER, Context.MODE_PRIVATE);
        daysList = new ArrayList<>();
        gson = new Gson();
        title_list = new ArrayList<>();
        desc_list = new ArrayList<>();
        validity_list = new ArrayList<>();
        colors_list = new ArrayList<>();
        sharable_list = new ArrayList<>();
        voucher_list = new ArrayList<>();

        colors_list.add("#E84646");
        colors_list.add("#1581FE");
        colors_list.add("#F1C40F");
        colors_list.add("#939292");
        colors_list.add("#12C06A");
        colors_list.add("#FF4081");

        layout_share = (LinearLayout) findViewById(R.id.layout_share);
        view = (View) findViewById(R.id.view_top);
        tv_title = (TextView) findViewById(R.id.text_title);
        tv_desc = (TextView) findViewById(R.id.text_desc);
        tv_val = (TextView) findViewById(R.id.text_terms_conditions);
        cardView = (CardView) findViewById(R.id.cardview_voucher);

        layout_preview_edit = findViewById(R.id.layout_voucher_preview);
        layout_et_del = (LinearLayout) findViewById(R.id.layout_edit_delete);
        layout_create = (LinearLayout) findViewById(R.id.layout_create_voucher);
        scrollView_voucher = (ScrollView) findViewById(R.id.voucher_scrollview);
        layout_settings2 = findViewById(R.id.layout_settings2);
        layout_settings3 = (LinearLayout) findViewById(R.id.layout_settings3);
        cancel_btn = (MaterialButton) findViewById(R.id.cancel_button);
        ok_btn = (MaterialButton) findViewById(R.id.ok_button);
        perf_btn = (MaterialButton) findViewById(R.id.view_perf_button);
        edit_btn = (MaterialButton) findViewById(R.id.edit_button);
        imageView_delete = (ImageView) findViewById(R.id.image_delete_voucher);

        textView_settings1 = (TextView) findViewById(R.id.text_settings1);
        textView_settings2 = (TextView) findViewById(R.id.text_settings2);
        text_ask_create_update = (TextView) findViewById(R.id.text_ask_create_update);
        //viewpager2
        viewPager2 = (ViewPager2) findViewById(R.id.viewpager);
        colors = getResources().getStringArray(R.array.voucher_colors);

     /*   scrollView_voucher.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                simpleViewFlipper.getParent().requestDisallowInterceptTouchEvent(false);
                //listView_open_hr.getParent().requestDisallowInterceptTouchEvent(false);


                //mapFragment.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        simpleViewFlipper.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                simpleViewFlipper.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });*/

        if (getIntent().getExtras() != null) {
            type_code = getIntent().getStringExtra("type_code");
            voucher_type = getIntent().getStringExtra("redeem_type");
            back_press_code = getIntent().getStringExtra("back_press_code");
            create_update_code = getIntent().getStringExtra("create_update_code");
            Log.d("Preview----", "preview: " + voucher_type + "," + type_code + "," + create_update_code);
            if (create_update_code.equals("0")) {
                text_ask_create_update.setText("Do you want to update this voucher?");
                layout_settings3.setVisibility(View.GONE);
            }
            if (type_code.equals("1")) {
                Log.d("Preview----", "code: 1");
                layout_et_del.setVisibility(View.GONE);
                imageView_delete.setVisibility(View.GONE);
                layout_create.setVisibility(View.VISIBLE);
                viewPager2.setVisibility(View.VISIBLE);
                layout_preview_edit.setVisibility(View.GONE);
                layout_settings3.setVisibility(View.VISIBLE);

            } else if (type_code.equals("0")) {
                layout_et_del.setVisibility(View.VISIBLE);
                imageView_delete.setVisibility(View.VISIBLE);
                layout_create.setVisibility(View.GONE);
                viewPager2.setVisibility(View.GONE);
                layout_preview_edit.setVisibility(View.VISIBLE);
                layout_settings3.setVisibility(View.GONE);

            }
            if (voucher_type.equals("P")) {
                title = getIntent().getStringExtra("title");
                desc = getIntent().getStringExtra("desc");
                points = getIntent().getStringExtra("points");
                s_date = getIntent().getStringExtra("s_date");
                e_date = getIntent().getStringExtra("e_date");
                st_time = getIntent().getStringExtra("s_time");
                e_time = getIntent().getStringExtra("e_time");
                bg_color = getIntent().getStringExtra("bg_color");
                getActDays = getIntent().getStringExtra("act_days");
                sharable = getIntent().getStringExtra("sharable");
                mer_cb_redeem_id = getIntent().getStringExtra("mer_cd_redeem_id");
                isActive = getIntent().getBooleanExtra("isActive", true);
                terms_conditions = getIntent().getStringExtra("terms_conditions");
                if (!TextUtils.isEmpty(sharable) && sharable.equals("1")) {
                    isSharable = true;
                }
                voucherReview(title, desc, s_date, e_date, st_time, e_time, sharable, getActDays, terms_conditions, "P", points, bg_color);

                Log.d("Preview----", "type 1=p: " + title + "," + desc + "," + s_date + "," + e_date + "," + st_time + "," + e_time + "," + sharable + "," + getActDays + "," + mer_cb_redeem_id + "," + isActive + "," + terms_conditions+ "," + bg_color);
            } else if (voucher_type.equals("B")) {
                title = getIntent().getStringExtra("title");
                desc = getIntent().getStringExtra("desc");
                wallet = getIntent().getStringExtra("wallet");
                points = getIntent().getStringExtra("points");
                s_date = getIntent().getStringExtra("s_date");
                e_date = getIntent().getStringExtra("e_date");
                st_time = getIntent().getStringExtra("s_time");
                bg_color = getIntent().getStringExtra("bg_color");
                e_time = getIntent().getStringExtra("e_time");
                getActDays = getIntent().getStringExtra("act_days");
                sharable = getIntent().getStringExtra("sharable");
                mer_cb_redeem_id = getIntent().getStringExtra("mer_cd_redeem_id");
                isActive = getIntent().getBooleanExtra("isActive", true);
                terms_conditions = getIntent().getStringExtra("terms_conditions");
                if (!TextUtils.isEmpty(sharable) && sharable.equals("1")) {
                    isSharable = true;
                }
                voucherReview(title, desc, s_date, e_date, st_time, e_time, sharable, getActDays, terms_conditions, "B", points, bg_color);
                Log.d("Preview----", "type 1=b: " + title + "," + desc + "," + s_date + "," + e_date + "," + st_time + "," + e_time + "," + sharable + "," + getActDays + "," + mer_cb_redeem_id + "," + isActive + "," + terms_conditions);

            } else if (voucher_type.equals("U")) {
                title = getIntent().getStringExtra("title");
                desc = getIntent().getStringExtra("desc");
                pur_amount = getIntent().getStringExtra("pur_amount");
                voucher_count = getIntent().getStringExtra("v_count");
                points = getIntent().getStringExtra("points");
                voucher_id = getIntent().getStringExtra("v_id");
                bg_color = getIntent().getStringExtra("bg_color");
                s_date = getIntent().getStringExtra("s_date");
                e_date = getIntent().getStringExtra("e_date");
                st_time = getIntent().getStringExtra("s_time");
                e_time = getIntent().getStringExtra("e_time");
                getActDays = getIntent().getStringExtra("act_days");
                mer_cb_redeem_id = getIntent().getStringExtra("mer_cd_redeem_id");
                isActive = getIntent().getBooleanExtra("isActive", true);
                terms_conditions = getIntent().getStringExtra("terms_conditions");
                voucherReview(title, desc, s_date, e_date, st_time, e_time, "", getActDays, terms_conditions, "U", points, bg_color);
                Log.d("Preview----", "type 1=u: " + title + "," + desc + "," + s_date + "," + e_date + "," + st_time + "," + e_time + "," + sharable + "," + getActDays + "," + voucher_id + "," + mer_cb_redeem_id + "," + isActive + "," + terms_conditions);

            } else if (voucher_type.equals("Z")) {
                title = getIntent().getStringExtra("title");
                desc = getIntent().getStringExtra("desc");
                s_date = getIntent().getStringExtra("s_date");
                e_date = getIntent().getStringExtra("e_date");
                sharable = getIntent().getStringExtra("sharable");
                bg_color = getIntent().getStringExtra("bg_color");
                mer_cb_redeem_id = getIntent().getStringExtra("mer_cd_redeem_id");
                isActive = getIntent().getBooleanExtra("isActive", true);
                terms_conditions = getIntent().getStringExtra("terms_conditions");
                if (!TextUtils.isEmpty(sharable) && sharable.equals("1")) {
                    isSharable = true;
                }
                voucherReview(title, desc, s_date, e_date, "", "", sharable, "", terms_conditions, "Z", "", bg_color);
                Log.d("Preview----", "type 1=z: " + title + "," + desc + "," + s_date + "," + e_date + "," + st_time + "," + e_time + "," + sharable + "," + getActDays + "," + mer_cb_redeem_id + "," + isActive + "," + terms_conditions + "," + points);

            } else if (voucher_type.equals("M")) {

                title = getIntent().getStringExtra("title");
                desc = getIntent().getStringExtra("desc");
                lead_title = getIntent().getStringExtra("lead_title");
                lead_desc = getIntent().getStringExtra("lead_desc");
                s_date = getIntent().getStringExtra("s_date");
                e_date = getIntent().getStringExtra("e_date");
                sharable = getIntent().getStringExtra("sharable");
                bg_color = getIntent().getStringExtra("bg_color");
                mer_cb_redeem_id = getIntent().getStringExtra("mer_cd_redeem_id");
                isActive = getIntent().getBooleanExtra("isActive", true);
                terms_conditions = getIntent().getStringExtra("terms_conditions");
                ref_reward_points = getIntent().getStringExtra("ref_reward_points");
                if (!TextUtils.isEmpty(sharable) && sharable.equals("1")) {
                    isSharable = true;
                }
                voucherReview(title, desc, s_date, e_date, "", "", sharable, "", terms_conditions, "M", ref_reward_points, bg_color);
                Log.d("Preview----", "type 1=m: " + title + "," + desc + "," + s_date + "," + e_date + "," + st_time + "," + e_time + "," + sharable + "," + getActDays + "," + lead_title + "," + lead_desc + "," + mer_cb_redeem_id + "," + isActive + "," + terms_conditions + "," + points);
            }
        }

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(create_update_code) && create_update_code.equals("0")) {
                    //update voucher
                    callUpdateAPI();
                } else if (!TextUtils.isEmpty(create_update_code) && create_update_code.equals("1")) {
                    //create voucher
                    intermediateAlertDialog = new IntermediateAlertDialog(VoucherSetupPreview.this);
                    SetUpRedemptionList setUpRedemptionList = new SetUpRedemptionList();
                    setUpRedemptionList.setRedeemTitle(title);
                    setUpRedemptionList.setRedeemDescription(desc);
                    setUpRedemptionList.setRedeemActivedt(s_date);
                    setUpRedemptionList.setRedeemExpdt(e_date);
                    setUpRedemptionList.setRedeemActtime(st_time);
                    setUpRedemptionList.setRedeemEndtime(e_time);
                    setUpRedemptionList.setVoucherPurchaseAmount(pur_amount);
                    if (voucher_type.equals("M")) {
                        setUpRedemptionList.setReferralRewardPoints(ref_reward_points);
                    } else {
                        setUpRedemptionList.setRedeemPoints(points);
                    }
                    setUpRedemptionList.setRedeemType(voucher_type);
                    setUpRedemptionList.setVoucherBg(bg_color);
                    setUpRedemptionList.setAssignedVoucherCount(voucher_count);
                    setUpRedemptionList.setRedeemActivedays(getActDays);
                    if (voucher_type.equals("U")) {
                        setUpRedemptionList.setAssignedVoucherId(voucher_id);
                    } else {
                        setUpRedemptionList.setAssignedVoucherId("0");
                    }
                    setUpRedemptionList.setMaxRedemptionAllowed("9999");
                    setUpRedemptionList.setIsCustSharable(isSharable);
                    setUpRedemptionList.setRedeemDepositAmt(wallet);
                    setUpRedemptionList.setLeadTitle(title);
                    setUpRedemptionList.setLeadDescription(desc);

                    voucher_list.add(setUpRedemptionList);
                    SetUpVoucherList setUpVoucherList = new SetUpVoucherList();
                    setUpVoucherList.setMerId(preferences.getString(MERCHANT_ID, ""));
                    setUpVoucherList.setMerDeviceId(preferences.getString(DEVICE_ID, ""));
                    setUpVoucherList.setVoucherList(voucher_list);
                    setUpVoucherList.setRequestCode("C");
                    Log.d("Voucher--->", "onClick: " + voucher_list);
                    try {
                        uploadVoucher(setUpVoucherList);
                    } catch (ApiException e) {
                        e.printStackTrace();
                        Log.d("Voucher--->", "onClick: " + e.getMessage());
                    }

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupPreview.this, " Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupPreview.this, VoucherSetupHome.class));
                                    finish();
                                }
                            };
                        }
                    });

                }
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupPreview.this, VoucherSetupHome.class);
                startActivity(in);
                finish();
            }
        });
        imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("isActive--->", "onClick: " + isActive);
                if (isActive) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialogYesNo = new AlertDialogYesNo(VoucherSetupPreview.this, "Delete?", "Are you sure want to delete this voucher?", "OK", "CANCEL") {
                                @Override
                                public void onOKButtonClick() {
                                    callDeleteAPI();
                                }

                                @Override
                                public void onCancelButtonClick() {

                                }
                            };
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupPreview.this, "Voucher is deactivated. Voucher has redemption(s) and cannot be deleted", "OK", "") {
                                @Override
                                public void onButtonClick() {
                                }
                            };
                        }
                    });

                }

            }
        });
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupPreview.this, VoucherSetupCreate.class);
                in.putExtra("type_code", "0");
                in.putExtra("redeem_type", voucher_type);
                in.putExtra("title", title);
                in.putExtra("desc", desc);
                in.putExtra("lead_title", lead_title);
                in.putExtra("lead_desc", lead_desc);
                in.putExtra("points", points);
                in.putExtra("pur_amount", pur_amount);
                in.putExtra("s_date", s_date);
                in.putExtra("s_time", st_time);
                in.putExtra("e_date", e_date);
                in.putExtra("e_time", e_time);
                in.putExtra("act_days", getActDays);
                in.putExtra("bg_color", bg_color);
                in.putExtra("sharable", sharable);
                in.putExtra("wallet", wallet);
                in.putExtra("v_id", voucher_id);
                in.putExtra("v_count", voucher_count);
                in.putExtra("mer_cd_redeem_id", mer_cb_redeem_id);
                in.putExtra("create_update_code", "0");
                in.putExtra("terms_conditions", terms_conditions);
                in.putExtra("ref_reward_points", ref_reward_points);
                Log.d("Adapter---->", actual_title + "," + voucher_type + "," + title + "," + desc + "," + wallet + "," + points + " " + s_date + "," + st_time + " " + e_date + "," + e_time + "," + getActDays + "," + sharable + "," + bg_color + "," + voucher_count + "," + voucher_id);
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
        perf_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupPreview.this, VoucherPerformanceActivity.class);
                in.putExtra("title", title);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
    }

    public void voucherReview(String title, String desc, String s_date, String e_date, String s_time, String e_time, String sharable, String weekdays, String t_conditions, String v_type, String points, String v_bg_color) {

        String startDate_conv = null, endDate_conv = null;
        if (!TextUtils.isEmpty(s_date) && !TextUtils.isEmpty(e_date)) {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat d = new SimpleDateFormat("dd-MMM-yyyy");
            try {
                Date convertedDate_S = inputFormat.parse(s_date);
                Date convertedDate_E = inputFormat.parse(e_date);
                startDate_conv = d.format(convertedDate_S);
                endDate_conv = d.format(convertedDate_E);

            } catch (ParseException e) {
                Log.d("Date--->", "" + e.getMessage());
            }
        }
        Log.d("Aaaaa----", "voucherReview: "+title+","+desc+","+sharable+","+t_conditions+","+v_bg_color);
        if (StringUtils.isNotBlank(title) && StringUtils.isNotBlank(desc) && StringUtils.isNotBlank(t_conditions) && StringUtils.isNotBlank(v_bg_color)) {
            Log.d("Aaaaa----", "voucherReview: "+title+","+desc+","+sharable+","+t_conditions);
            if (v_type.equals("M")) {

                tv_title.setText("Refer and earn " + points + " points. Your friend Offer : " + "\""+title+"\"");
                tv_desc.setText("Share this voucher and earn " + points + " points when your friend redeems. Your referral offer is : " + "\""+desc+"\"");

               if(sharable.equals("1")){
                   layout_share.setVisibility(View.VISIBLE);
               }
               else {
                   layout_share.setVisibility(View.INVISIBLE);
               }
                tv_val.setText(t_conditions);
                cardView.setCardBackgroundColor(Color.parseColor(v_bg_color));

            } else {
                Log.d("Aaaaa----", "voucherReview: "+title+","+desc+","+sharable+","+t_conditions);
                if (StringUtils.isNotBlank(points)) {
                    tv_title.setText(title + " for " + points + " points");
                } else {
                    tv_title.setText(title);
                }
                tv_desc.setText(desc);
                tv_val.setText(t_conditions);
                if(sharable.equals("1")){
                    layout_share.setVisibility(View.VISIBLE);
                }
                else {
                    layout_share.setVisibility(View.INVISIBLE);
                }
                cardView.setCardBackgroundColor(Color.parseColor(v_bg_color));
            }
        }


        if (!TextUtils.isEmpty(weekdays)) {
            char get1 = weekdays.charAt(0);
            char get2 = weekdays.charAt(1);
            char get3 = weekdays.charAt(2);
            char get4 = weekdays.charAt(3);
            char get5 = weekdays.charAt(4);
            char get6 = weekdays.charAt(5);
            char get7 = weekdays.charAt(6);
            Log.d("ActDays===>", "" + get1 + "," + get2 + "," + get3 + "," + get4 + "," + get5 + "," + get6 + "," + get7);

            daysList.clear();
            //Sunday
            if (String.valueOf(get1).equals("y")) {
                daysCount = 1;
                daysList.add("Sunday");
            }

            //Monday
            if (String.valueOf(get2).equals("y")) {
                daysCount += 1;
                daysList.add("Monday");
            }

            //Tuesday
            if (String.valueOf(get3).equals("y")) {
                daysCount += 1;
                daysList.add("Tuesday");
            }

            //Wednesday
            if (String.valueOf(get4).equals("y")) {
                daysCount += 1;
                daysList.add("Wednesday");
            }
            //Thursday
            if (String.valueOf(get5).equals("y")) {
                daysCount += 1;
                daysList.add("Thursday");
            }

            //Friday
            if (String.valueOf(get6).equals("y")) {
                daysCount += 1;
                daysList.add("Friday");
            }

            //Saturday
            if (String.valueOf(get7).equals("y")) {
                daysCount += 1;
                daysList.add("Saturday");
            }

            Log.d("Days====>", "" + daysCount + " , " + daysList.size());

            String days = "";
            for (int i = 0; i < daysList.size(); i++) {
                if (i == 0) {
                    days = daysList.get(i);
                } else {
                    days += ", " + daysList.get(i);
                }

                /*else if (i == (daysList.size() - 1)) {
                    days += " and " + daysList.get(i);
                }*/
                Log.d("Days====>", "List=== " + days + " , " + daysCount);
            }

            if (daysCount == 7 && !TextUtils.isEmpty(s_time) && !TextUtils.isEmpty(e_time)) {
                textView_settings1.setText("Voucher is validity between " + startDate_conv + " to " + endDate_conv);
                textView_settings2.setText("Voucher will be displayed to customer on  all days and redeemable between " + s_time + " - " + e_time);

            } else if (daysCount != 7 && !TextUtils.isEmpty(s_time) && !TextUtils.isEmpty(e_time)) {

                textView_settings1.setText("Voucher is validity between " + startDate_conv + " to " + endDate_conv);
                textView_settings2.setText("Voucher will be displayed to customer on " + days + " and redeemable between " + s_time + " - " + e_time);

            } else if (TextUtils.isEmpty(String.valueOf(daysCount)) && TextUtils.isEmpty(s_time) && TextUtils.isEmpty(e_time)) {

                textView_settings1.setText("Voucher is validity between " + startDate_conv + " to " + endDate_conv);
                layout_settings2.setVisibility(View.GONE);
            }

        } else {
            textView_settings1.setText("Voucher is validity between " + startDate_conv + " to " + endDate_conv);
            layout_settings2.setVisibility(View.GONE);
        }

        for (int i = 0; i < 6; i++) {

            if (v_type.equals("M")) {
                title_list.add("Refer and earn " + points + " points. Your friend Offer : "+"\""+title+"\"");
                desc_list.add("Share this voucher and earn " + points + " points when your friend redeems. Your referral offer is : " +"\""+ desc+"\"");
            } else {
                if (StringUtils.isEmpty(points)) {
                    title_list.add(title);
                } else {
                    title_list.add(title + " for " + points + " points");
                }
                desc_list.add(desc);
            }

            validity_list.add("Validity Period: " + startDate_conv + " to " + endDate_conv + ". Applicable for one redemption only." +
                    " Not valid with other offers. Merchant decision is final. Terms & conditions apply.");

            sharable_list.add(sharable);
        }
        int bg_color_pos = 0;
        if (StringUtils.isNotBlank(v_bg_color)) {
            bg_color_pos = colors_list.indexOf(v_bg_color);
        }
        Log.d("Color_Pos---->", "voucherReview: " + bg_color_pos + "," + bg_color + "," + title_list.size());
        viewPager2.setAdapter(new ViewSlider_RecyclerAdapter(title_list, desc_list, validity_list, colors_list, sharable_list));
        viewPager2.setCurrentItem(bg_color_pos);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(10));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(final int position) {
                super.onPageSelected(position);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bg_color = colors_list.get(position);
                    }
                });

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("Back---", "onBackPressed: " + type_code);
        if (!TextUtils.isEmpty(back_press_code)) {
            Intent in = new Intent(VoucherSetupPreview.this, VoucherSetupHome.class);
            in.putExtra("redeem_type", voucher_type);
            in.putExtra("type_code", "2");
            startActivity(in);
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
            finish();
        } else {
            Intent in = new Intent(VoucherSetupPreview.this, VoucherSetupCreate.class);
            in.putExtra("redeem_type", voucher_type);
            in.putExtra("type_code", "2");
            in.putExtra("create_update_code", create_update_code);
            startActivity(in);
            overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
            finish();
        }
    }

    private void uploadVoucher(SetUpVoucherList requestBody) throws ApiException {

        Log.d("Voucher---", "uploadVoucher: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(VoucherSetupPreview.this));
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantVoucherSetupAsync(requestBody, new ApiCallback<SetUpVoucherResponse>() {
            @Override
            public void onFailure(final ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("Failedddd--->", "Code : 1 " + e.toString() + "," + statusCode);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (statusCode == 500) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupPreview.this, "Merchant does not exist or Inactive", "OK", "") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupPreview.this, VoucherSetupHome.class));
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
                            new AlertDialogFailure(VoucherSetupPreview.this, ". Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupPreview.this, VoucherSetupHome.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            };
                        }
                    });
                }

                Log.d("onFailure--->", "" + e.getMessage());
                //Toast.makeText(Review_Activity.this, ""+e.getMessage().toString()+","+statusCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(SetUpVoucherResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("Failed--->", "Code :" + result.getStatusCode() + "," + result);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 406) {
                    Log.d("Failed--->", "Code : 2");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupPreview.this, "Merchant does not exist or Inactive", "OK", "") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupPreview.this, VoucherSetupHome.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            };
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 200) {
                    if (voucher_type.equals("P")) {
                        SharedPreferences preferences_p = getSharedPreferences(VoucherSetupCreate.MyPREFERENCES_REDEEM_P, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_bt = preferences_p.edit();
                        editor_bt.clear();
                        editor_bt.commit();

                    } else if (voucher_type.equals("B")) {
                        SharedPreferences preferences_p = getSharedPreferences(VoucherSetupCreate.MyPREFERENCES_REDEEM_B, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_bt = preferences_p.edit();
                        editor_bt.clear();
                        editor_bt.commit();


                    } else if (voucher_type.equals("U")) {
                        SharedPreferences preferences_p = getSharedPreferences(VoucherSetupCreate.MyPREFERENCES_REDEEM_U, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_bt = preferences_p.edit();
                        editor_bt.clear();
                        editor_bt.commit();

                    } else if (voucher_type.equals("Z")) {
                        SharedPreferences preferences_p = getSharedPreferences(VoucherSetupCreate.MyPREFERENCES_REDEEM_Z, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_bt = preferences_p.edit();
                        editor_bt.clear();
                        editor_bt.commit();


                    } else if (voucher_type.equals("M")) {
                        SharedPreferences preferences_p = getSharedPreferences(VoucherSetupCreate.MyPREFERENCES_REDEEM_M, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_bt = preferences_p.edit();
                        editor_bt.clear();
                        editor_bt.commit();

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("getStatusMsg---", "run: " + result.getStatusMsg());
                            LayoutInflater factory = LayoutInflater.from(VoucherSetupPreview.this);
                            final View deleteDialogView = factory.inflate(R.layout.success_dialog, null);
                            final AlertDialog uploadDialog = new AlertDialog.Builder(VoucherSetupPreview.this).create();
                            uploadDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            uploadDialog.setView(deleteDialogView);
                            uploadDialog.setCancelable(false);
                            TextView textView_title = (TextView) deleteDialogView.findViewById(R.id.tv_title);
                            textView_title.setText("Done!");
                            TextView textView = (TextView) deleteDialogView.findViewById(R.id.text_dialog);
                            textView.setText(result.getStatusMsg());
                            uploadDialog.show();
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(Objects.requireNonNull(uploadDialog.getWindow()).getAttributes());
                            lp.width = getDisplayWidth(uploadDialog);
                            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            lp.x = 0;
                            lp.y = 0;
                            uploadDialog.getWindow().setAttributes(lp);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadDialog.dismiss();
                                    Intent in = new Intent(VoucherSetupPreview.this, VoucherSetupHome.class);
                                    startActivity(in);
                                    finish();
                                }
                            }, 3000);

                        }
                    });

                } else {
                    Log.d("Failed--->", "Code : 3");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupPreview.this, " Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupPreview.this, VoucherSetupHome.class));
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

    private void deleteVoucher(SetUpVoucherList requestBody) throws ApiException {
        Log.d("Voucher---", "deleteVoucher: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(VoucherSetupPreview.this));
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantVoucherSetupAsync(requestBody, new ApiCallback<SetUpVoucherResponse>() {
            @Override
            public void onFailure(final ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                Log.d("Failedddd--->", "Code : 1 " + e.toString() + "," + statusCode);
                if (statusCode == 404) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupPreview.this, " Please try again later!", "OK", "Voucher not found") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupPreview.this, VoucherSetupHome.class));
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
                            new AlertDialogFailure(VoucherSetupPreview.this, " Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupPreview.this, VoucherSetupHome.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            };
                        }
                    });

                }

                Log.d("onFailure--->", "" + e.getMessage());
                //Toast.makeText(Review_Activity.this, ""+e.getMessage().toString()+","+statusCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(SetUpVoucherResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("Failed--->", "Code :" + result.getStatusCode() + "," + result);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    Log.d("delete---", "onSuccess: " + Integer.parseInt(result.getStatusCode()));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.getStatusMsg().equals("Voucher deleted successfully")) {
                                LayoutInflater factory = LayoutInflater.from(VoucherSetupPreview.this);
                                final View deleteDialogView = factory.inflate(R.layout.success_dialog, null);
                                final AlertDialog deleteDialog = new AlertDialog.Builder(VoucherSetupPreview.this).create();
                                deleteDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                TextView textView_delete = (TextView) deleteDialogView.findViewById(R.id.text_dialog);
                                textView_delete.setText(result.getStatusMsg());
                                TextView textView_title = (TextView) deleteDialogView.findViewById(R.id.tv_title);
                                textView_title.setText("Done!");
                                deleteDialog.setView(deleteDialogView);
                                deleteDialog.setCancelable(false);
                                deleteDialog.show();
                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(Objects.requireNonNull(deleteDialog.getWindow()).getAttributes());
                                lp.width = getDisplayWidth(deleteDialog);
                                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                lp.x = 0;
                                lp.y = 0;
                                deleteDialog.getWindow().setAttributes(lp);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        deleteDialog.dismiss();
                                        Intent in = new Intent(VoucherSetupPreview.this, VoucherSetupHome.class);
                                        startActivity(in);
                                        finish();
                                        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                    }
                                }, 3000);
                            } else {
                                new AlertSuccess(VoucherSetupPreview.this, "Done!", result.getStatusMsg()) {
                                    @Override
                                    public void onButtonClick() {
                                        Intent in = new Intent(VoucherSetupPreview.this, VoucherSetupHome.class);
                                        startActivity(in);
                                        finish();
                                        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                    }
                                };
                            }

                        }
                    });

                } else {
                    Log.d("Failed--->", "Code : 3");
                    //showAlertDialog("Merchant R.egistration Failed.Please Try Again!", false);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupPreview.this, " Please try again later!", "", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupPreview.this, VoucherSetupHome.class));
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

    private void updateVoucher(SetUpVoucherList requestBody) throws ApiException {
        Log.d("Voucher---", "updateVoucher: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(VoucherSetupPreview.this));
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.merchantVoucherSetupAsync(requestBody, new ApiCallback<SetUpVoucherResponse>() {
            @Override
            public void onFailure(final ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                Log.d("Failedddd--->", "Code : 1 " + e.toString() + "," + statusCode);
                if (statusCode == 500) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupPreview.this, "Merchant does not exist or Inactive", "OK", "") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupPreview.this, VoucherSetupHome.class));
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
                            new AlertDialogFailure(VoucherSetupPreview.this, " Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupPreview.this, VoucherSetupHome.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            };
                        }
                    });

                }

                Log.d("onFailure--->", "" + e.getMessage());
                //Toast.makeText(Review_Activity.this, ""+e.getMessage().toString()+","+statusCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(SetUpVoucherResponse result, int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("Failed--->", "Code :" + result.getStatusCode() + "," + result);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 406) {
                    Log.d("Failed--->", "Code : 2");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupPreview.this, "Merchant does not exist or Inactive", "OK", "") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupPreview.this, VoucherSetupHome.class));
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            };
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 200) {

                    Log.d("delete---", "onSuccess: " + Integer.parseInt(result.getStatusCode()));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (voucher_type.equals("P")) {
                                SharedPreferences preferences_p = getSharedPreferences(VoucherSetupCreate.MyPREFERENCES_REDEEM_P, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_bt = preferences_p.edit();
                                editor_bt.clear();
                                editor_bt.commit();

                            } else if (voucher_type.equals("B")) {
                                SharedPreferences preferences_p = getSharedPreferences(VoucherSetupCreate.MyPREFERENCES_REDEEM_B, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_bt = preferences_p.edit();
                                editor_bt.clear();
                                editor_bt.commit();


                            } else if (voucher_type.equals("U")) {
                                SharedPreferences preferences_p = getSharedPreferences(VoucherSetupCreate.MyPREFERENCES_REDEEM_U, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_bt = preferences_p.edit();
                                editor_bt.clear();
                                editor_bt.commit();

                            } else if (voucher_type.equals("Z")) {
                                SharedPreferences preferences_p = getSharedPreferences(VoucherSetupCreate.MyPREFERENCES_REDEEM_Z, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_bt = preferences_p.edit();
                                editor_bt.clear();
                                editor_bt.commit();


                            } else if (voucher_type.equals("M")) {
                                SharedPreferences preferences_p = getSharedPreferences(VoucherSetupCreate.MyPREFERENCES_REDEEM_M, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_bt = preferences_p.edit();
                                editor_bt.clear();
                                editor_bt.commit();

                            }



                            LayoutInflater factory = LayoutInflater.from(VoucherSetupPreview.this);
                            final View deleteDialogView = factory.inflate(R.layout.success_dialog, null);
                            final AlertDialog deleteDialog = new AlertDialog.Builder(VoucherSetupPreview.this).create();
                            deleteDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            TextView textView_delete = (TextView) deleteDialogView.findViewById(R.id.text_dialog);
                            textView_delete.setText("Voucher updated successfully");
                            deleteDialog.setView(deleteDialogView);
                            deleteDialog.setCancelable(false);
                            deleteDialog.show();
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(Objects.requireNonNull(deleteDialog.getWindow()).getAttributes());
                            lp.width = getDisplayWidth(deleteDialog);
                            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            lp.x = 0;
                            lp.y = 0;
                            deleteDialog.getWindow().setAttributes(lp);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    deleteDialog.dismiss();
                                    Intent in = new Intent(VoucherSetupPreview.this, VoucherSetupHome.class);
                                    startActivity(in);
                                    finish();
                                    overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
                                }
                            }, 3000);

                        }
                    });

                } else {
                    Log.d("Failed--->", "Code : 3");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new AlertDialogFailure(VoucherSetupPreview.this, " Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupPreview.this, VoucherSetupHome.class));
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
    protected void onStop() {
        super.onStop();

        if (alertDialogYesNo != null) {
            alertDialogYesNo.onCancelButtonClick();
        }
    }

    private int getDisplayWidth(AlertDialog alertDialog) {
        int width = 600;
        if (alertDialog.getWindow() != null) {
            Display display = alertDialog.getWindow().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            if (size.x > 720) {
                width = size.x - 200;
            }
        }
        return width;
    }

    private void callDeleteAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(VoucherSetupPreview.this);
        SetUpRedemptionList setUpRedemptionList = new SetUpRedemptionList();
        setUpRedemptionList.setMerCbRedeemId(mer_cb_redeem_id);
        voucher_list.add(setUpRedemptionList);
        SetUpVoucherList setUpVoucherList = new SetUpVoucherList();
        setUpVoucherList.setMerId(preferences.getString(MERCHANT_ID, ""));
        setUpVoucherList.setMerDeviceId(preferences.getString(DEVICE_ID, ""));
        setUpVoucherList.setVoucherList(voucher_list);
        setUpVoucherList.setRequestCode("D");

        Log.d("Voucher--->", "onClick: " + voucher_list);
        try {
            deleteVoucher(setUpVoucherList);
        } catch (ApiException e) {
            e.printStackTrace();
            Log.d("Voucher--->", "onClick: " + e.getMessage());
        }
    }

    private void callUpdateAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(VoucherSetupPreview.this);
        SetUpRedemptionList setUpRedemptionList = new SetUpRedemptionList();
        setUpRedemptionList.setRedeemTitle(title);
        setUpRedemptionList.setRedeemDescription(desc);
        setUpRedemptionList.setRedeemActivedt(s_date);
        setUpRedemptionList.setRedeemExpdt(e_date);
        setUpRedemptionList.setRedeemActtime(st_time);
        setUpRedemptionList.setRedeemEndtime(e_time);
        setUpRedemptionList.setVoucherPurchaseAmount(pur_amount);
        if (voucher_type.equals("M")) {
            setUpRedemptionList.setReferralRewardPoints(ref_reward_points);
        } else {
            setUpRedemptionList.setRedeemPoints(points);
        }
        setUpRedemptionList.setRedeemType(voucher_type);
        setUpRedemptionList.setVoucherBg(bg_color);
        setUpRedemptionList.setAssignedVoucherCount(voucher_count);
        setUpRedemptionList.setRedeemActivedays(getActDays);
        if (voucher_type.equals("U")) {
            setUpRedemptionList.setAssignedVoucherId(voucher_id);
        } else {
            setUpRedemptionList.setAssignedVoucherId("0");
        }
        setUpRedemptionList.setMaxRedemptionAllowed("9999");
        setUpRedemptionList.setIsCustSharable(isSharable);
        setUpRedemptionList.setMerCbRedeemId(mer_cb_redeem_id);
        setUpRedemptionList.setRedeemDepositAmt(wallet);
        setUpRedemptionList.setLeadTitle(lead_title);
        setUpRedemptionList.setLeadDescription(lead_desc);

        voucher_list.add(setUpRedemptionList);
        SetUpVoucherList setUpVoucherList = new SetUpVoucherList();
        setUpVoucherList.setMerId(preferences.getString(MERCHANT_ID, ""));
        setUpVoucherList.setMerDeviceId(preferences.getString(DEVICE_ID, ""));
        setUpVoucherList.setVoucherList(voucher_list);
        setUpVoucherList.setRequestCode("U");

        Log.d("Voucher--->", "onClick: " + voucher_list);
        try {
            updateVoucher(setUpVoucherList);
        } catch (ApiException e) {
            e.printStackTrace();
            Log.d("Voucher--->", "onClick: " + e.getMessage());
        }
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

