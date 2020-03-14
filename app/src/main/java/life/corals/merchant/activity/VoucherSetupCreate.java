package life.corals.merchant.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import life.corals.merchant.R;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.FetchRedeemVoucherListRequestBody;
import life.corals.merchant.client.model.RedeemVoucher;
import life.corals.merchant.client.model.RedeemVoucherListResponse;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertDialogYesNo;
import life.corals.merchant.utils.AppTimeOutManagerUtil;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.Util;

import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;

public class VoucherSetupCreate extends AppCompatActivity {

    LinearLayout linearLayout_P, linearLayout_B, linearLayout_U, linearLayout_Z, linearLayout_M, sub_layout_P, sub_layout_B, sub_layout_U, sub_layout_Z, sub_layout_M;
    Spinner spinner_assg_voucher;
    ToggleSwitch toggleSwitch_P, toggleSwitch_B, toggleSwitch_Z, toggleSwitch_M;
    Switch aSwitch_p, aSwitch_b, aSwitch_z, aSwitch_m;
    Button button_p, button_b, button_u, button_z, button_m;
    EditText editText_title_P, editText_desc_P, editText_points_P, editText_title_B, editText_desc_B, editText_points_B, editText_wallet_B, editText_title_U, editText_desc_U, editText_vouchers_count_U, editText_vouchers_pur_amt_U, editText_title_Z, editText_desc_Z, editText_title_M, editText_desc_M, editText_points_M, editText_title_lead_M, editText_desc_lead_M;
    TextView tv_start_date_P, tv_end_date_P, tv_start_time_P, tv_end_time_P, tv_start_date_B, tv_end_date_B, tv_start_time_B, tv_end_time_B, tv_start_date_U, tv_end_date_U, tv_start_time_U, tv_end_time_U, tv_start_date_Z, tv_end_date_Z, tv_start_date_M, tv_end_date_M;
    int sharable_p = 0, sharable_b = 0, sharable_z = 0, sharable_m = 0;
    int sharable_p1 = 1, sharable_b1 = 1, sharable_z1 = 1, sharable_m1 = 1;
    ArrayList<String> assigned_id_list, assigned_voucher_list;
    String activeDays_p = null, activeDays_b = null, activeDays_u = null;
    boolean isActiveSunday_p = true, isActiveMonday_p = true, isActiveTuesday_p = true, isActiveWednesday_p = true, isActiveThursday_p = true, isActiveFriday_p = true, isActiveSaturday_p = true, isActiveSunday_b = true, isActiveMonday_b = true, isActiveTuesday_b = true, isActiveWednesday_b = true, isActiveThursday_b = true, isActiveFriday_b = true, isActiveSaturday_b = true, isActiveSunday_u = true, isActiveMonday_u = true, isActiveTuesday_u = true, isActiveWednesday_u = true, isActiveThursday_u = true, isActiveFriday_u = true, isActiveSaturday_u = true;
    Button btn_yes_sday_p, btn_yes_mnday_p, btn_yes_tsday_p, btn_yes_wedday_p, btn_yes_trsday_p, btn_yes_fdday_p, btn_yes_strday_p, btn_yes_sday_b, btn_yes_mnday_b, btn_yes_tsday_b, btn_yes_wedday_b, btn_yes_trsday_b, btn_yes_fdday_b, btn_yes_strday_b, btn_yes_sday_u, btn_yes_mnday_u, btn_yes_tsday_u, btn_yes_wedday_u, btn_yes_trsday_u, btn_yes_fdday_u, btn_yes_strday_u;
    private int mYear, mMonth, mDay, mHour, mMinute, mSeconds;
    DatePickerDialog datePickerDialog;
    private String start_date_p, end_date_p, start_time_p, end_time_p, start_date_b, end_date_b, start_time_b, end_time_b, start_date_u, end_date_u, start_time_u, end_time_u, start_date_z, end_date_z, start_time_z, end_time_z, start_date_m, end_date_m, start_time_m, end_time_m;
    TextView textView_no_vouchers, textView_no_assigned_vouchers;
    public RadioGroup radioGroup;
    RadioButton rbn;
    FetchRedeemVoucherListRequestBody fetchRedeemVoucherListRequestBody;
    private AlertDialogYesNo alertDialogYesNo;
    String redeem_type, redeem_id, bg_color;
    String selected_radio_voucher = null, selected_voucher_id = null;
    TextView tv_min_title_p, tv_min_title_b, tv_min_title_u, tv_min_title_z, tv_min_title_m, tv_min_lead_title_m, tv_min_desc_p, tv_min_desc_b, tv_min_desc_u, tv_min_desc_z, tv_min_desc_m, tv_min_lead_desc_m;
    //SP for update redeem
    public static final String MyPREFERENCES_REDEEM_P = "MyPREFERENCES_REDEEM_P";
    public static final String MyPREFERENCES_REDEEM_B = "MyPREFERENCES_REDEEM_B";
    public static final String MyPREFERENCES_REDEEM_U = "MyPREFERENCES_REDEEM_U";
    public static final String MyPREFERENCES_REDEEM_Z = "MyPREFERENCES_REDEEM_Z";
    public static final String MyPREFERENCES_REDEEM_M = "MyPREFERENCES_REDEEM_M";
    //P Type
    public static final String REDEEM_TITLE_P = "REDEEM_TITLE_P";
    public static final String REDEEM_DESCRIPTION_P = "REDEEM_DESCRIPTION_P";
    public static final String REDEEM_POINT_P = "REDEEM_POINT_P";
    public static final String REDEEM_START_DATE_P = "REDEEM_START_DATE_P";
    public static final String REDEEM_START_TIME_P = "REDEEM_START_TIME_P";
    public static final String REDEEM_EXPIRY_DATE_P = "REDEEM_EXPIRY_DATE_P";
    public static final String REDEEM_EXPIRY_TIME_P = "REDEEM_EXPIRY_TIME_P";
    public static final String REDEEM_ACTIVE_DAYS_P = "REDEEM_ACTIVE_DAYS_P";
    public static final String REDEEM_IS_SHARABLE_P = "REDEEM_IS_SHARABLE_P";
    public static final String REDEEM_BG_COLOR_P = "REDEEM_BG_COLOR_P";
    public static final String REDEEM_MER_CB_REDEEM_ID_P = "REDEEM_MER_CB_REDEEM_ID_P";
    public static final String REDEEM_TERMS_CONDITIONS_P = "REDEEM_TERMS_CONDITIONS_P";
    //B Type
    public static final String REDEEM_TITLE_B = "REDEEM_TITLE_B";
    public static final String REDEEM_DESCRIPTION_B = "REDEEM_DESCRIPTION_B";
    public static final String REDEEM_POINT_B = "REDEEM_POINT_B";
    public static final String REDEEM_AMOUNT_B = "REDEEM_AMOUNT_B";
    public static final String REDEEM_START_DATE_B = "REDEEM_START_DATE_B";
    public static final String REDEEM_START_TIME_B = "REDEEM_START_TIME_B";
    public static final String REDEEM_EXPIRY_DATE_B = "REDEEM_EXPIRY_DATE_B";
    public static final String REDEEM_EXPIRY_TIME_B = "REDEEM_EXPIRY_TIME_B";
    public static final String REDEEM_ACTIVE_DAYS_B = "REDEEM_ACTIVE_DAYS_B";
    public static final String REDEEM_IS_SHARABLE_B = "REDEEM_IS_SHARABLE_B";
    public static final String REDEEM_BG_COLOR_B = "REDEEM_BG_COLOR_B";
    public static final String REDEEM_MER_CB_REDEEM_ID_B = "REDEEM_MER_CB_REDEEM_ID_B";
    public static final String REDEEM_TERMS_CONDITIONS_B = "REDEEM_TERMS_CONDITIONS_B";
    //U Type
    public static final String REDEEM_TITLE_U = "REDEEM_TITLE_U";
    public static final String REDEEM_DESCRIPTION_U = "REDEEM_DESCRIPTION_U";
    public static final String REDEEM_START_DATE_U = "REDEEM_START_DATE_U";
    public static final String REDEEM_START_TIME_U = "REDEEM_START_TIME_U";
    public static final String REDEEM_EXPIRY_DATE_U = "REDEEM_EXPIRY_DATE_U";
    public static final String REDEEM_EXPIRY_TIME_U = "REDEEM_EXPIRY_TIME_U";
    public static final String REDEEM_ACTIVE_DAYS_U = "REDEEM_ACTIVE_DAYS_U";
    public static final String REDEEM_VOUCHER_PUR_AMOUNT_U = "REDEEM_VOUCHER_PUR_AMOUNT_U";
    public static final String REDEEM_VOUCHER_COUNT_U = "REDEEM_VOUCHER_COUNT_U";
    public static final String REDEEM_VOUCHER_ID_U = "REDEEM_VOUCHER_ID_U";
    public static final String REDEEM_ASSIGNED_VOUCHER_LIST_U = "REDEEM_ASSIGNED_VOUCHER_LIST_U";
    public static final String REDEEM_ASSIGNED_VOUCHER_ID_LIST_U = "REDEEM_ASSIGNED_VOUCHER_ID_LIST_U";
    public static final String REDEEM_BG_COLOR_U = "REDEEM_BG_COLOR_U";
    public static final String REDEEM_MER_CB_REDEEM_ID_U = "REDEEM_MER_CB_REDEEM_ID_U";
    public static final String REDEEM_TERMS_CONDITIONS_U = "REDEEM_TERMS_CONDITIONS_U";
    //Z Type
    public static final String REDEEM_TITLE_Z = "REDEEM_TITLE_Z";
    public static final String REDEEM_DESCRIPTION_Z = "REDEEM_DESCRIPTION_Z";
    public static final String REDEEM_START_DATE_Z = "REDEEM_START_DATE_Z";
    public static final String REDEEM_EXPIRY_DATE_Z = "REDEEM_EXPIRY_DATE_Z";
    public static final String REDEEM_IS_SHARABLE_Z = "REDEEM_IS_SHARABLE_Z";
    public static final String REDEEM_BG_COLOR_Z = "REDEEM_BG_COLOR_Z";
    public static final String REDEEM_MER_CB_REDEEM_ID_Z = "REDEEM_MER_CB_REDEEM_ID_Z";
    public static final String REDEEM_TERMS_CONDITIONS_Z = "REDEEM_TERMS_CONDITIONS_Z";
    //M Type
    public static final String REDEEM_TITLE_M = "REDEEM_TITLE_M";
    public static final String REDEEM_DESCRIPTION_M = "REDEEM_DESCRIPTION_M";
    public static final String REDEEM_LEAD_TITLE_M = "REDEEM_LEAD_TITLE_M";
    public static final String REDEEM_LEAD_DESCRIPTION_M = "REDEEM_LEAD_DESCRIPTION_M";
    public static final String REDEEM_POINT_M = "REDEEM_POINT_M";
    public static final String REDEEM_START_DATE_M = "REDEEM_START_DATE_M";
    public static final String REDEEM_EXPIRY_DATE_M = "REDEEM_EXPIRY_DATE_M";
    public static final String REDEEM_IS_SHARABLE_M = "REDEEM_IS_SHARABLE_M";
    public static final String REDEEM_BG_COLOR_M = "REDEEM_BG_COLOR_M";
    public static final String REDEEM_MER_CB_REDEEM_ID_M = "REDEEM_MER_CB_REDEEM_ID_M";
    public static final String REDEEM_TERMS_CONDITIONS_M = "REDEEM_TERMS_CONDITIONS_M";
    private SharedPreferences sharedpreferences_p_voucher, sharedpreferences_b_voucher, sharedpreferences_u_voucher, sharedpreferences_z_voucher, sharedpreferences_m_voucher;
    // voucher request code
    public static final int GET_VOUCHER_LIST = 0;
    public static final int GET_SINGLE_VOUCHER_DETAILS = 1;
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences preferences;
    TextView tv_count_title_p, tv_count_desc_p, tv_count_title_b, tv_count_desc_b, tv_count_title_u, tv_count_desc_u, tv_count_title_z, tv_count_desc_z, tv_count_title_m, tv_count_desc_m, tv_count_lead_title_m, tv_count_lead_desc_m;
    String create_update_code = null, mer_cd_redeem_id = null, r_voucher_id = null, terms_conditions = null;
    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    TextView tv_share_yes_p, tv_share_yes_b, tv_share_yes_z, tv_share_yes_m, tv_share_no_p, tv_share_no_b, tv_share_no_z, tv_share_no_m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_setup_create);

        Toolbar toolbar = findViewById(R.id.toolbar_voucher_create);
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

        appTimeOutManagerUtil = new AppTimeOutManagerUtil(VoucherSetupCreate.this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        preferences = getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        assigned_voucher_list = new ArrayList<>();
        assigned_id_list = new ArrayList<>();

        tv_share_yes_p = findViewById(R.id.sharable_yes_p);
        tv_share_yes_b = findViewById(R.id.sharable_yes_b);
        tv_share_yes_z = findViewById(R.id.sharable_yes_z);
        tv_share_yes_m = findViewById(R.id.sharable_yes_m);
        tv_share_no_p = findViewById(R.id.sharable_no_p);
        tv_share_no_b = findViewById(R.id.sharable_no_b);
        tv_share_no_z = findViewById(R.id.sharable_no_z);
        tv_share_no_m = findViewById(R.id.sharable_no_m);

        tv_count_title_p = findViewById(R.id.title_p_watcher);
        tv_count_desc_p = findViewById(R.id.desc_p_watcher);
        tv_count_title_b = findViewById(R.id.title_b_watcher);
        tv_count_desc_b = findViewById(R.id.desc_b_watcher);
        tv_count_title_u = findViewById(R.id.title_u_watcher);
        tv_count_desc_u = findViewById(R.id.desc_u_watcher);
        tv_count_title_z = findViewById(R.id.title_z_watcher);
        tv_count_desc_z = findViewById(R.id.desc_z_watcher);
        tv_count_title_m = findViewById(R.id.title_m_watcher);
        tv_count_desc_m = findViewById(R.id.desc_m_watcher);
        tv_count_lead_title_m = findViewById(R.id.title_lead_m_watcher);
        tv_count_lead_desc_m = findViewById(R.id.desc_lead_m_watcher);

        tv_min_title_p = findViewById(R.id.tv_min_title_p);
        tv_min_title_b = findViewById(R.id.tv_min_title_b);
        tv_min_title_u = findViewById(R.id.tv_min_title_u);
        tv_min_title_z = findViewById(R.id.tv_min_title_z);
        tv_min_title_m = findViewById(R.id.tv_min_title_m);
        tv_min_lead_title_m = findViewById(R.id.tv_min_lead_title_m);
        tv_min_desc_p = findViewById(R.id.tv_min_desc_p);
        tv_min_desc_b = findViewById(R.id.tv_min_desc_b);
        tv_min_desc_u = findViewById(R.id.tv_min_desc_u);
        tv_min_desc_z = findViewById(R.id.tv_min_desc_z);
        tv_min_desc_m = findViewById(R.id.tv_min_desc_m);
        tv_min_lead_desc_m = findViewById(R.id.tv_min_lead_desc_m);

        radioGroup = (RadioGroup) findViewById(R.id.assign_rgp);
        sharedpreferences_p_voucher = getSharedPreferences(MyPREFERENCES_REDEEM_P, Context.MODE_PRIVATE);
        sharedpreferences_b_voucher = getSharedPreferences(MyPREFERENCES_REDEEM_B, Context.MODE_PRIVATE);
        sharedpreferences_u_voucher = getSharedPreferences(MyPREFERENCES_REDEEM_U, Context.MODE_PRIVATE);
        sharedpreferences_z_voucher = getSharedPreferences(MyPREFERENCES_REDEEM_Z, Context.MODE_PRIVATE);
        sharedpreferences_m_voucher = getSharedPreferences(MyPREFERENCES_REDEEM_M, Context.MODE_PRIVATE);
        textView_no_vouchers = findViewById(R.id.text_no_vouchers);
        textView_no_assigned_vouchers = findViewById(R.id.text_no_assigned);
        linearLayout_P = (LinearLayout) findViewById(R.id.parent_layout_type_P);
        linearLayout_B = (LinearLayout) findViewById(R.id.parent_layout_type_B);
        linearLayout_U = (LinearLayout) findViewById(R.id.parent_layout_type_U);
        linearLayout_Z = (LinearLayout) findViewById(R.id.parent_layout_type_Z);
        linearLayout_M = (LinearLayout) findViewById(R.id.parent_layout_type_M);

        sub_layout_P = (LinearLayout) findViewById(R.id.layout_type_P);
        sub_layout_B = (LinearLayout) findViewById(R.id.layout_type_B);
        sub_layout_U = (LinearLayout) findViewById(R.id.layout_type_U);
        sub_layout_Z = (LinearLayout) findViewById(R.id.layout_type_Z);
        sub_layout_M = (LinearLayout) findViewById(R.id.layout_type_M);

        toggleSwitch_P = (ToggleSwitch) findViewById(R.id.toggle_P);
        toggleSwitch_B = (ToggleSwitch) findViewById(R.id.toggle_B);
        toggleSwitch_Z = (ToggleSwitch) findViewById(R.id.toggle_Z);
        toggleSwitch_M = (ToggleSwitch) findViewById(R.id.toggle_M);

        aSwitch_p = (Switch) findViewById(R.id.switch_p);
        aSwitch_b = (Switch) findViewById(R.id.switch_b);
        aSwitch_z = (Switch) findViewById(R.id.switch_z);
        aSwitch_m = (Switch) findViewById(R.id.switch_m);

        aSwitch_p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    aSwitch_p.setTextOn("Yes");
                } else {
                    aSwitch_p.setTextOff("No");
                }
            }
        });

        button_p = (Button) findViewById(R.id.button_p);
        button_b = (Button) findViewById(R.id.button_b);
        button_u = (Button) findViewById(R.id.button_u);
        button_z = (Button) findViewById(R.id.button_z);
        button_m = (Button) findViewById(R.id.button_m);

        editText_title_P = findViewById(R.id.edit_redeem_title_P);
        editText_title_B = findViewById(R.id.edit_redeem_title_B);
        editText_title_U = findViewById(R.id.edit_redeem_title_U);
        editText_title_Z = findViewById(R.id.edit_redeem_title_Z);
        editText_title_M = findViewById(R.id.edit_redeem_title_M);
        editText_title_lead_M = findViewById(R.id.edit_redeem_title_lead_M);

        int maxLength = 100;
        editText_title_P.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        editText_title_B.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        editText_title_U.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        editText_title_Z.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        editText_title_M.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        editText_title_lead_M.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        editText_desc_P = findViewById(R.id.edit_redeem_desc_P);
        editText_desc_B = findViewById(R.id.edit_redeem_desc_B);
        editText_desc_U = findViewById(R.id.edit_redeem_desc_U);
        editText_desc_Z = findViewById(R.id.edit_redeem_desc_Z);
        editText_desc_M = findViewById(R.id.edit_redeem_desc_M);
        editText_desc_lead_M = findViewById(R.id.edit_redeem_desc_lead_M);

        int maxLength_desc = 200;
        editText_desc_P.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength_desc)});
        editText_desc_B.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength_desc)});
        editText_desc_U.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength_desc)});
        editText_desc_Z.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength_desc)});
        editText_desc_M.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength_desc)});
        editText_desc_lead_M.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength_desc)});

        editText_points_P = findViewById(R.id.edit_redeem_points_P);
        editText_points_B = findViewById(R.id.edit_redeem_points_B);
        editText_points_M = findViewById(R.id.edit_redeem_points_M);

        editText_wallet_B = findViewById(R.id.edit_wallet_amount_B);
        editText_vouchers_count_U = findViewById(R.id.edit_vouchers_issue_count_U);
        editText_vouchers_pur_amt_U = findViewById(R.id.edit_vouchers_purchase_amt_U);
        spinner_assg_voucher = findViewById(R.id.spinner_assigned_voucher);

        tv_start_date_P = findViewById(R.id.edit_start_date_P);
        tv_start_date_B = findViewById(R.id.edit_start_date_B);
        tv_start_date_U = findViewById(R.id.edit_start_date_U);
        tv_start_date_Z = findViewById(R.id.edit_start_date_Z);
        tv_start_date_M = findViewById(R.id.edit_start_date_M);

        tv_end_date_P = findViewById(R.id.edit_expiry_date_P);
        tv_end_date_B = findViewById(R.id.edit_expiry_date_B);
        tv_end_date_U = findViewById(R.id.edit_expiry_date_U);
        tv_end_date_Z = findViewById(R.id.edit_expiry_date_Z);
        tv_end_date_M = findViewById(R.id.edit_expiry_date_M);

        tv_start_time_P = findViewById(R.id.edit_start_time_P);
        tv_start_time_B = findViewById(R.id.edit_start_time_B);
        tv_start_time_U = findViewById(R.id.edit_start_time_U);
        tv_end_time_P = findViewById(R.id.edit_expiry_time_P);
        tv_end_time_B = findViewById(R.id.edit_expiry_time_B);
        tv_end_time_U = findViewById(R.id.edit_expiry_time_U);

        btn_yes_sday_p = (Button) findViewById(R.id.btn_yes_sunday_P);
        btn_yes_mnday_p = (Button) findViewById(R.id.btn_yes_monday_P);
        btn_yes_tsday_p = (Button) findViewById(R.id.btn_yes_tuesday_P);
        btn_yes_wedday_p = (Button) findViewById(R.id.btn_yes_wedsnesday_P);
        btn_yes_trsday_p = (Button) findViewById(R.id.btn_yes_thursday_P);
        btn_yes_fdday_p = (Button) findViewById(R.id.btn_yes_friday_P);
        btn_yes_strday_p = (Button) findViewById(R.id.btn_yes_saturday_P);

        btn_yes_sday_b = (Button) findViewById(R.id.btn_yes_sunday_B);
        btn_yes_mnday_b = (Button) findViewById(R.id.btn_yes_monday_B);
        btn_yes_tsday_b = (Button) findViewById(R.id.btn_yes_tuesday_B);
        btn_yes_wedday_b = (Button) findViewById(R.id.btn_yes_wedsnesday_B);
        btn_yes_trsday_b = (Button) findViewById(R.id.btn_yes_thursday_B);
        btn_yes_fdday_b = (Button) findViewById(R.id.btn_yes_friday_B);
        btn_yes_strday_b = (Button) findViewById(R.id.btn_yes_saturday_B);

        btn_yes_sday_u = (Button) findViewById(R.id.btn_yes_sunday_U);
        btn_yes_mnday_u = (Button) findViewById(R.id.btn_yes_monday_U);
        btn_yes_tsday_u = (Button) findViewById(R.id.btn_yes_tuesday_U);
        btn_yes_wedday_u = (Button) findViewById(R.id.btn_yes_wedsnesday_U);
        btn_yes_trsday_u = (Button) findViewById(R.id.btn_yes_thursday_U);
        btn_yes_fdday_u = (Button) findViewById(R.id.btn_yes_friday_U);
        btn_yes_strday_u = (Button) findViewById(R.id.btn_yes_saturday_U);

        tv_start_date_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Activation Date" + "</u>  </font>"));
        tv_start_date_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Activation Date" + "</u>  </font>"));
        tv_start_date_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Activation Date" + "</u>  </font>"));
        tv_start_date_Z.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Activation Date" + "</u>  </font>"));
        tv_start_date_M.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Activation Date" + "</u>  </font>"));

        tv_end_date_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Expiry Date" + "</u>  </font>"));
        tv_end_date_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Expiry Date" + "</u>  </font>"));
        tv_end_date_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Expiry Date" + "</u>  </font>"));
        tv_end_date_Z.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Expiry Date" + "</u>  </font>"));
        tv_end_date_M.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "Select Expiry Date" + "</u>  </font>"));

        tv_start_time_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "8:00" + "</u>  </font>"));
        tv_start_time_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "8:00" + "</u>  </font>"));
        tv_start_time_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "8:00" + "</u>  </font>"));

        tv_end_time_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "23:00" + "</u>  </font>"));
        tv_end_time_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "23:00" + "</u>  </font>"));
        tv_end_time_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + "23:00" + "</u>  </font>"));

        editText_title_P.addTextChangedListener(textWatcher_title_p);
        editText_desc_P.addTextChangedListener(textWatcher_desc_p);

        editText_title_B.addTextChangedListener(textWatcher_title_b);
        editText_desc_B.addTextChangedListener(textWatcher_desc_b);

        editText_title_U.addTextChangedListener(textWatcher_title_u);
        editText_desc_U.addTextChangedListener(textWatcher_desc_u);

        editText_title_Z.addTextChangedListener(textWatcher_title_z);
        editText_desc_Z.addTextChangedListener(textWatcher_desc_z);

        editText_title_M.addTextChangedListener(textWatcher_title_m);
        editText_desc_M.addTextChangedListener(textWatcher_desc_m);

        editText_title_lead_M.addTextChangedListener(textWatcher_lead_title_m);
        editText_desc_lead_M.addTextChangedListener(textWatcher_lead_desc_m);

        aSwitch_p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    sharable_p = 1;
                } else {
                    // The toggle is disabled
                    sharable_p = 0;
                }
            }
        });
        aSwitch_b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    sharable_b = 1;
                } else {
                    // The toggle is disabled
                    sharable_b = 0;
                }
            }
        });

        aSwitch_z.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    sharable_z = 1;
                } else {
                    // The toggle is disabled
                    sharable_z = 0;
                }
            }
        });
        aSwitch_m.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    sharable_m = 1;
                } else {
                    // The toggle is disabled
                    sharable_m = 0;
                }
            }
        });

        //shatable p
        tv_share_yes_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_share_yes_p.setTextColor(getResources().getColor(R.color.white));
                tv_share_no_p.setTextColor(getResources().getColor(R.color.black));
                tv_share_yes_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                tv_share_no_p.setBackgroundColor(getResources().getColor(R.color.grey));
                sharable_p1 = 1;
                //Toast.makeText(VoucherSetupCreate.this, "" + sharable_p1, Toast.LENGTH_SHORT).show();
            }
        });

        tv_share_no_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_share_yes_p.setTextColor(getResources().getColor(R.color.black));
                tv_share_no_p.setTextColor(getResources().getColor(R.color.white));
                tv_share_yes_p.setBackgroundColor(getResources().getColor(R.color.grey));
                tv_share_no_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                sharable_p1 = 0;
                //Toast.makeText(VoucherSetupCreate.this, ""+sharable_p1, Toast.LENGTH_SHORT).show();

            }
        });

        //shatable b
        tv_share_yes_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_share_yes_b.setTextColor(getResources().getColor(R.color.white));
                tv_share_no_b.setTextColor(getResources().getColor(R.color.black));
                tv_share_yes_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                tv_share_no_b.setBackgroundColor(getResources().getColor(R.color.grey));
                sharable_b1 = 1;
                //Toast.makeText(VoucherSetupCreate.this, ""+sharable_b1, Toast.LENGTH_SHORT).show();
            }
        });

        tv_share_no_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_share_yes_b.setTextColor(getResources().getColor(R.color.black));
                tv_share_no_b.setTextColor(getResources().getColor(R.color.white));
                tv_share_yes_b.setBackgroundColor(getResources().getColor(R.color.grey));
                tv_share_no_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                sharable_b1 = 0;
                //Toast.makeText(VoucherSetupCreate.this, ""+sharable_b1, Toast.LENGTH_SHORT).show();

            }
        });

        //shatable z

        tv_share_yes_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_share_yes_z.setTextColor(getResources().getColor(R.color.white));
                tv_share_no_z.setTextColor(getResources().getColor(R.color.black));
                tv_share_yes_z.setBackgroundColor(getResources().getColor(R.color.green_hase));
                tv_share_no_z.setBackgroundColor(getResources().getColor(R.color.grey));
                sharable_z1 = 1;
                //Toast.makeText(VoucherSetupCreate.this, ""+sharable_z1, Toast.LENGTH_SHORT).show();
            }
        });

        tv_share_no_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_share_yes_z.setTextColor(getResources().getColor(R.color.black));
                tv_share_no_z.setTextColor(getResources().getColor(R.color.white));
                tv_share_yes_z.setBackgroundColor(getResources().getColor(R.color.grey));
                tv_share_no_z.setBackgroundColor(getResources().getColor(R.color.green_hase));
                sharable_z1 = 0;
                // Toast.makeText(VoucherSetupCreate.this, ""+sharable_z1, Toast.LENGTH_SHORT).show();

            }
        });

        //shatable m
        tv_share_yes_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_share_yes_m.setTextColor(getResources().getColor(R.color.white));
                tv_share_no_m.setTextColor(getResources().getColor(R.color.black));
                tv_share_yes_m.setBackgroundColor(getResources().getColor(R.color.green_hase));
                tv_share_no_m.setBackgroundColor(getResources().getColor(R.color.grey));
                sharable_m1 = 1;
                //Toast.makeText(VoucherSetupCreate.this, ""+sharable_m1, Toast.LENGTH_SHORT).show();
            }
        });

        tv_share_no_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_share_yes_m.setTextColor(getResources().getColor(R.color.black));
                tv_share_no_m.setTextColor(getResources().getColor(R.color.white));
                tv_share_yes_m.setBackgroundColor(getResources().getColor(R.color.grey));
                tv_share_no_m.setBackgroundColor(getResources().getColor(R.color.green_hase));
                sharable_m1 = 0;
                //Toast.makeText(VoucherSetupCreate.this, ""+sharable_m1, Toast.LENGTH_SHORT).show();

            }
        });

        if (getIntent().getExtras() != null) {
            redeem_id = getIntent().getStringExtra("type_code");
            redeem_type = getIntent().getStringExtra("redeem_type");
            create_update_code = getIntent().getStringExtra("create_update_code");
            Log.d("redeem_type---", "onCreate: " + redeem_type + "," + redeem_id + "," + create_update_code);
            //redeem_id==0 (update voucher from voucher list)
            //redeem_id==1 (create voucher)
            //redeem_id==2 (update voucher from preview)
            if (redeem_id.equals("2")) {
                if (redeem_type.equals("P")) {
                    //setProductVoucher();

                    String r_title = sharedpreferences_p_voucher.getString(REDEEM_TITLE_P, "");
                    String r_desc = sharedpreferences_p_voucher.getString(REDEEM_DESCRIPTION_P, "");
                    String r_point = sharedpreferences_p_voucher.getString(REDEEM_POINT_P, "");
                    String r_a_date = sharedpreferences_p_voucher.getString(REDEEM_START_DATE_P, "");
                    String r_a_time = sharedpreferences_p_voucher.getString(REDEEM_START_TIME_P, "");
                    String r_e_date = sharedpreferences_p_voucher.getString(REDEEM_EXPIRY_DATE_P, "");
                    String r_e_time = sharedpreferences_p_voucher.getString(REDEEM_EXPIRY_TIME_P, "");
                    String r_week_days = sharedpreferences_p_voucher.getString(REDEEM_ACTIVE_DAYS_P, "");
                    String r_is_sharable = sharedpreferences_p_voucher.getString(REDEEM_IS_SHARABLE_P, "");
                    bg_color = sharedpreferences_p_voucher.getString(REDEEM_BG_COLOR_P, "");
                    mer_cd_redeem_id = sharedpreferences_p_voucher.getString(REDEEM_MER_CB_REDEEM_ID_P, "");
                    terms_conditions = sharedpreferences_p_voucher.getString(REDEEM_TERMS_CONDITIONS_P, "");
                    Log.d("Redeem_Shared_data--->", "onCreate_P: " + r_title + "," + r_desc + "," + r_point + "," + r_a_date + "," + r_a_time + "," + r_e_date + "," + r_e_time + "," + r_week_days + "," + r_is_sharable);

                    linearLayout_P.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(r_title) && !TextUtils.isEmpty(r_desc) && !TextUtils.isEmpty(r_point) && !TextUtils.isEmpty(r_a_date) && !TextUtils.isEmpty(r_a_time) && !TextUtils.isEmpty(r_e_date) && !TextUtils.isEmpty(r_e_time) && !TextUtils.isEmpty(r_week_days) && !TextUtils.isEmpty(r_is_sharable)) {
                        editText_title_P.setText(r_title);
                        editText_title_P.setSelection(r_title.length());
                        editText_desc_P.setText(r_desc);
                        editText_points_P.setText(r_point);
                        editText_points_P.setEnabled(false);
                        editText_points_P.setAlpha(0.5f);
                        tv_start_date_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_a_date + "</u>  </font>"));
                        tv_end_date_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_e_date + "</u>  </font>"));
                        tv_start_time_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_a_time + "</u>  </font>"));
                        tv_end_time_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_e_time + "</u>  </font>"));
                        // toggleSwitch_P.setCheckedTogglePosition(Integer.parseInt(r_is_sharable));

                        if (!TextUtils.isEmpty(r_is_sharable) && r_is_sharable.equals("1")) {
                            // aSwitch_p.setChecked(true);
                            tv_share_yes_p.setTextColor(getResources().getColor(R.color.white));
                            tv_share_no_p.setTextColor(getResources().getColor(R.color.black));
                            tv_share_yes_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                            tv_share_no_p.setBackgroundColor(getResources().getColor(R.color.grey));
                            sharable_p1 = 1;
                        } else {
                            // aSwitch_p.setChecked(false);
                            tv_share_yes_p.setTextColor(getResources().getColor(R.color.black));
                            tv_share_no_p.setTextColor(getResources().getColor(R.color.white));
                            tv_share_yes_p.setBackgroundColor(getResources().getColor(R.color.grey));
                            tv_share_no_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                            sharable_p1 = 0;
                        }
                        activeWeekDays_p(r_week_days);
                    }

                } else if (redeem_type.equals("B")) {
                    //setWalletVoucher();

                    String r_title = sharedpreferences_b_voucher.getString(REDEEM_TITLE_B, "");
                    String r_desc = sharedpreferences_b_voucher.getString(REDEEM_DESCRIPTION_B, "");
                    String r_amount = sharedpreferences_b_voucher.getString(REDEEM_AMOUNT_B, "");
                    String r_point = sharedpreferences_b_voucher.getString(REDEEM_POINT_B, "");
                    String r_a_date = sharedpreferences_b_voucher.getString(REDEEM_START_DATE_B, "");
                    String r_a_time = sharedpreferences_b_voucher.getString(REDEEM_START_TIME_B, "");
                    String r_e_date = sharedpreferences_b_voucher.getString(REDEEM_EXPIRY_DATE_B, "");
                    String r_e_time = sharedpreferences_b_voucher.getString(REDEEM_EXPIRY_TIME_B, "");
                    String r_week_days = sharedpreferences_b_voucher.getString(REDEEM_ACTIVE_DAYS_B, "");
                    String r_is_sharable = sharedpreferences_b_voucher.getString(REDEEM_IS_SHARABLE_B, "");
                    bg_color = sharedpreferences_b_voucher.getString(REDEEM_BG_COLOR_B, "");
                    mer_cd_redeem_id = sharedpreferences_b_voucher.getString(REDEEM_MER_CB_REDEEM_ID_B, "");
                    terms_conditions = sharedpreferences_b_voucher.getString(REDEEM_TERMS_CONDITIONS_B, "");
                    Log.d("Redeem_Shared_data--->", "onCreate_B: " + r_title + "," + r_desc + "," + r_amount + "," + r_point + "," + "," + r_a_date + "," + r_a_time + "," + r_e_date + "," + r_e_time + "," + r_week_days + "," + r_is_sharable);

                    linearLayout_B.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(r_title) && !TextUtils.isEmpty(r_desc) && !TextUtils.isEmpty(r_amount) && !TextUtils.isEmpty(r_point) && !TextUtils.isEmpty(r_a_date) && !TextUtils.isEmpty(r_a_time) && !TextUtils.isEmpty(r_e_date) && !TextUtils.isEmpty(r_e_time) && !TextUtils.isEmpty(r_week_days) && !TextUtils.isEmpty(r_is_sharable)) {
                        editText_title_B.setText(r_title);
                        editText_title_B.setSelection(r_title.length());
                        editText_desc_B.setText(r_desc);
                        editText_wallet_B.setText(r_amount);
                        editText_points_B.setText(r_point);
                        editText_points_B.setEnabled(false);
                        editText_points_B.setAlpha(0.5f);
                        tv_start_date_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_a_date + "</u>  </font>"));
                        tv_end_date_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_e_date + "</u>  </font>"));
                        tv_start_time_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_a_time + "</u>  </font>"));
                        tv_end_time_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_e_time + "</u>  </font>"));
                        // toggleSwitch_B.setCheckedTogglePosition(Integer.parseInt(r_is_sharable));
                        if (!TextUtils.isEmpty(r_is_sharable) && r_is_sharable.equals("1")) {
                            // aSwitch_b.setChecked(true);
                            tv_share_yes_b.setTextColor(getResources().getColor(R.color.white));
                            tv_share_no_b.setTextColor(getResources().getColor(R.color.black));
                            tv_share_yes_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                            tv_share_no_b.setBackgroundColor(getResources().getColor(R.color.grey));
                            sharable_b1 = 1;
                        } else {
                            // aSwitch_b.setChecked(false);
                            tv_share_yes_b.setTextColor(getResources().getColor(R.color.black));
                            tv_share_no_b.setTextColor(getResources().getColor(R.color.white));
                            tv_share_yes_b.setBackgroundColor(getResources().getColor(R.color.grey));
                            tv_share_no_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                            sharable_b1 = 0;
                        }
                        activeWeekDays_b(r_week_days);
                    }

                } else if (redeem_type.equals("U")) {
                    //setBulkVoucher();
                    linearLayout_U.setVisibility(View.VISIBLE);
                    ArrayList<String> list_assigned = new ArrayList<>();
                    ArrayList<String> list_assigned_id = new ArrayList<>();
                    String r_title = sharedpreferences_u_voucher.getString(REDEEM_TITLE_U, "");
                    String r_desc = sharedpreferences_u_voucher.getString(REDEEM_DESCRIPTION_U, "");
                    String pur_amount = sharedpreferences_u_voucher.getString(REDEEM_VOUCHER_PUR_AMOUNT_U, "");
                    String r_voucher_count = sharedpreferences_u_voucher.getString(REDEEM_VOUCHER_COUNT_U, "");
                    r_voucher_id = sharedpreferences_u_voucher.getString(REDEEM_VOUCHER_ID_U, "");
                    String r_a_date = sharedpreferences_u_voucher.getString(REDEEM_START_DATE_U, "");
                    String r_a_time = sharedpreferences_u_voucher.getString(REDEEM_START_TIME_U, "");
                    String r_e_date = sharedpreferences_u_voucher.getString(REDEEM_EXPIRY_DATE_U, "");
                    String r_e_time = sharedpreferences_u_voucher.getString(REDEEM_EXPIRY_TIME_U, "");
                    String r_week_days = sharedpreferences_u_voucher.getString(REDEEM_ACTIVE_DAYS_U, "");
                    String assigned_vouchers = sharedpreferences_u_voucher.getString(REDEEM_ASSIGNED_VOUCHER_LIST_U, "");
                    String assigned_voucher_id = sharedpreferences_u_voucher.getString(REDEEM_ASSIGNED_VOUCHER_ID_LIST_U, "");
                    bg_color = sharedpreferences_u_voucher.getString(REDEEM_BG_COLOR_U, "");
                    mer_cd_redeem_id = sharedpreferences_u_voucher.getString(REDEEM_MER_CB_REDEEM_ID_U, "");
                    terms_conditions = sharedpreferences_u_voucher.getString(REDEEM_TERMS_CONDITIONS_U, "");
                    Log.d("Redeem_Shared_data--->", "onCreate_U: " + r_title + "," + r_desc + "," + "," + r_voucher_count + "," + r_voucher_id + "," + r_a_date + "," + r_a_time + "," + r_e_date + "," + r_e_time + "," + r_week_days);

                    if (!TextUtils.isEmpty(assigned_vouchers) && !TextUtils.isEmpty(assigned_voucher_id)) {
                        list_assigned = new Gson().fromJson(assigned_vouchers, new TypeToken<ArrayList<String>>() {
                        }.getType());
                        list_assigned_id = new Gson().fromJson(assigned_voucher_id, new TypeToken<ArrayList<String>>() {
                        }.getType());
                    }
                    if (list_assigned.size() != 0) {
                        textView_no_vouchers.setVisibility(View.GONE);
                        textView_no_assigned_vouchers.setVisibility(View.GONE);
                        spinner_assg_voucher.setVisibility(View.VISIBLE);
                        ArrayAdapter a = new ArrayAdapter(VoucherSetupCreate.this, android.R.layout.simple_spinner_item, list_assigned);
                        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_assg_voucher.setAdapter(a);
                        spinner_assg_voucher.setPrompt("Select Assigned Voucher");
                        int assg_id_pos = list_assigned_id.indexOf(r_voucher_id);
                        String assg_value = list_assigned.get(assg_id_pos);
                        Log.d("Assg_value---", "onCreate: " + assg_id_pos + "," + assg_value + "," + r_voucher_id);
                        if (assg_value != null) {
                            selected_voucher_id = r_voucher_id;
                            int spinnerPosition = a.getPosition(assg_value);
                            spinner_assg_voucher.setSelection(spinnerPosition);
                            a.notifyDataSetChanged();
                        }
                        ArrayList<String> finalList_assigned_id = list_assigned_id;
                        spinner_assg_voucher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selected_voucher_id = finalList_assigned_id.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                    if (!TextUtils.isEmpty(r_title) && !TextUtils.isEmpty(r_desc) && !TextUtils.isEmpty(pur_amount) && !TextUtils.isEmpty(r_voucher_count) && !TextUtils.isEmpty(r_a_date) && !TextUtils.isEmpty(r_a_time) && !TextUtils.isEmpty(r_e_date) && !TextUtils.isEmpty(r_e_time) && !TextUtils.isEmpty(r_week_days)) {
                        editText_title_U.setText(r_title);
                        editText_title_U.setSelection(r_title.length());
                        editText_desc_U.setText(r_desc);
                        editText_vouchers_pur_amt_U.setText(pur_amount);
                        editText_vouchers_count_U.setText(r_voucher_count);
                        tv_start_date_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_a_date + "</u>  </font>"));
                        tv_end_date_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_e_date + "</u>  </font>"));
                        tv_start_time_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_a_time + "</u>  </font>"));
                        tv_end_time_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_e_time + "</u>  </font>"));
                        activeWeekDays_u(r_week_days);
                    }

                } else if (redeem_type.equals("Z")) {
                    //setAssignedVoucher();

                    String r_title = sharedpreferences_z_voucher.getString(REDEEM_TITLE_Z, "");
                    String r_desc = sharedpreferences_z_voucher.getString(REDEEM_DESCRIPTION_Z, "");
                    String r_a_date = sharedpreferences_z_voucher.getString(REDEEM_START_DATE_Z, "");
                    String r_e_date = sharedpreferences_z_voucher.getString(REDEEM_EXPIRY_DATE_Z, "");
                    String r_is_sharable = sharedpreferences_z_voucher.getString(REDEEM_IS_SHARABLE_Z, "");
                    bg_color = sharedpreferences_z_voucher.getString(REDEEM_BG_COLOR_Z, "");
                    mer_cd_redeem_id = sharedpreferences_z_voucher.getString(REDEEM_MER_CB_REDEEM_ID_Z, "");
                    terms_conditions = sharedpreferences_z_voucher.getString(REDEEM_TERMS_CONDITIONS_Z, "");
                    Log.d("Redeem_Shared_data--->", "onCreate_Z: " + r_title + "," + r_desc + "," + r_a_date + "," + r_e_date + "," + r_is_sharable);

                    linearLayout_Z.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(r_title) && !TextUtils.isEmpty(r_desc) && !TextUtils.isEmpty(r_a_date) && !TextUtils.isEmpty(r_e_date) && !TextUtils.isEmpty(r_is_sharable)) {
                        editText_title_Z.setText(r_title);
                        editText_title_Z.setSelection(r_title.length());
                        editText_desc_Z.setText(r_desc);
                        tv_start_date_Z.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_a_date + "</u>  </font>"));
                        tv_end_date_Z.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_e_date + "</u>  </font>"));
                        // toggleSwitch_Z.setCheckedTogglePosition(Integer.parseInt(r_is_sharable));
                        if (!TextUtils.isEmpty(r_is_sharable) && r_is_sharable.equals("1")) {
                            //aSwitch_z.setChecked(true);
                            tv_share_yes_z.setTextColor(getResources().getColor(R.color.white));
                            tv_share_no_z.setTextColor(getResources().getColor(R.color.black));
                            tv_share_yes_z.setBackgroundColor(getResources().getColor(R.color.green_hase));
                            tv_share_no_z.setBackgroundColor(getResources().getColor(R.color.grey));
                            sharable_z1 = 1;
                        } else {
                            // aSwitch_z.setChecked(false);
                            tv_share_yes_z.setTextColor(getResources().getColor(R.color.black));
                            tv_share_no_z.setTextColor(getResources().getColor(R.color.white));
                            tv_share_yes_z.setBackgroundColor(getResources().getColor(R.color.grey));
                            tv_share_no_z.setBackgroundColor(getResources().getColor(R.color.green_hase));
                            sharable_z1 = 0;
                        }
                    }

                } else if (redeem_type.equals("M")) {
                    // setMGMVoucher();

                    String r_title = sharedpreferences_m_voucher.getString(REDEEM_TITLE_M, "");
                    String r_desc = sharedpreferences_m_voucher.getString(REDEEM_DESCRIPTION_M, "");
                    String r_lead_title = sharedpreferences_m_voucher.getString(REDEEM_LEAD_TITLE_M, "");
                    String r_lead_desc = sharedpreferences_m_voucher.getString(REDEEM_LEAD_DESCRIPTION_M, "");
                    String r_point = sharedpreferences_m_voucher.getString(REDEEM_POINT_M, "");
                    String r_a_date = sharedpreferences_m_voucher.getString(REDEEM_START_DATE_M, "");
                    String r_e_date = sharedpreferences_m_voucher.getString(REDEEM_EXPIRY_DATE_M, "");
                    String r_is_sharable = sharedpreferences_m_voucher.getString(REDEEM_IS_SHARABLE_M, "");
                    bg_color = sharedpreferences_m_voucher.getString(REDEEM_BG_COLOR_M, "");
                    mer_cd_redeem_id = sharedpreferences_m_voucher.getString(REDEEM_MER_CB_REDEEM_ID_M, "");
                    terms_conditions = sharedpreferences_m_voucher.getString(REDEEM_TERMS_CONDITIONS_M, "");
                    Log.d("Redeem_Shared_data--->", "onCreate_M: " + r_title + "," + r_desc + "," + r_point + "," + r_a_date + "," + r_e_date + "," + r_is_sharable);

                    linearLayout_M.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(r_title) && !TextUtils.isEmpty(r_desc) && !TextUtils.isEmpty(r_point) && !TextUtils.isEmpty(r_a_date) && !TextUtils.isEmpty(r_e_date) && !TextUtils.isEmpty(r_is_sharable)) {
                        editText_title_M.setText(r_title);
                        editText_title_M.setSelection(r_title.length());
                        editText_desc_M.setText(r_desc);
                        editText_title_lead_M.setText(r_lead_title);
                        editText_desc_lead_M.setText(r_lead_desc);
                        editText_points_M.setText(r_point);
                        editText_points_M.setEnabled(false);
                        editText_points_M.setAlpha(0.5f);
                        tv_start_date_M.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_a_date + "</u>  </font>"));
                        tv_end_date_M.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + r_e_date + "</u>  </font>"));
                        //toggleSwitch_M.setCheckedTogglePosition(Integer.parseInt(r_is_sharable));
                        if (!TextUtils.isEmpty(r_is_sharable) && r_is_sharable.equals("1")) {
                            //aSwitch_m.setChecked(true);
                            tv_share_yes_m.setTextColor(getResources().getColor(R.color.white));
                            tv_share_no_m.setTextColor(getResources().getColor(R.color.black));
                            tv_share_yes_m.setBackgroundColor(getResources().getColor(R.color.green_hase));
                            tv_share_no_m.setBackgroundColor(getResources().getColor(R.color.grey));
                            sharable_m1 = 1;
                        } else {
                            //aSwitch_m.setChecked(false);
                            tv_share_yes_m.setTextColor(getResources().getColor(R.color.black));
                            tv_share_no_m.setTextColor(getResources().getColor(R.color.white));
                            tv_share_yes_m.setBackgroundColor(getResources().getColor(R.color.grey));
                            tv_share_no_m.setBackgroundColor(getResources().getColor(R.color.green_hase));
                            sharable_m1 = 0;
                        }
                    }
                }
            } else if (redeem_id.equals("0")) {
                //create_update_code="0";
                String title = getIntent().getStringExtra("title");
                String desc = getIntent().getStringExtra("desc");
                String lead_title = getIntent().getStringExtra("lead_title");
                String lead_desc = getIntent().getStringExtra("lead_desc");
                String points = getIntent().getStringExtra("points");
                String pur_amount = getIntent().getStringExtra("pur_amount");
                String act_date = getIntent().getStringExtra("s_date");
                String exp_date = getIntent().getStringExtra("e_date");
                String act_time = getIntent().getStringExtra("s_time");
                String exp_time = getIntent().getStringExtra("e_time");
                String getActDays = getIntent().getStringExtra("act_days");
                bg_color = getIntent().getStringExtra("bg_color");
                String wallet = getIntent().getStringExtra("wallet");
                r_voucher_id = getIntent().getStringExtra("v_id");
                String assgn_vouher_count = getIntent().getStringExtra("v_count");
                mer_cd_redeem_id = getIntent().getStringExtra("mer_cd_redeem_id");
                terms_conditions = getIntent().getStringExtra("terms_conditions");
                String get_share = getIntent().getStringExtra("sharable");
                String ref_reward_points = getIntent().getStringExtra("ref_reward_points");
                boolean sharable = true;
                if (!TextUtils.isEmpty(get_share) && get_share.equals("1")) {
                    sharable = true;
                } else {
                    sharable = false;
                }
                Log.d("Preview----", "onCreate: " + ref_reward_points + "," + title + "," + desc + "," + points + "," + act_date + "," + exp_date + "," + act_time + "," + exp_time + "," + getActDays + "," + redeem_type + "," + sharable + "," + bg_color + "," + lead_title + "," + lead_desc + "," + assgn_vouher_count + "," + r_voucher_id);


                if (redeem_type.equals("P")) {
                    linearLayout_P.setVisibility(View.VISIBLE);
                    editText_title_P.setText(title);
                    editText_title_P.setSelection(title.length());
                    editText_desc_P.setText(desc);
                    editText_points_P.setText(points);
                    editText_points_P.setEnabled(false);
                    editText_points_P.setAlpha(0.5f);
                    tv_start_date_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + act_date + "</u>  </font>"));
                    tv_end_date_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + exp_date + "</u>  </font>"));
                    tv_start_time_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + act_time + "</u>  </font>"));
                    tv_end_time_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + exp_time + "</u>  </font>"));

                    if (sharable) {
                        //sharable_p = 1;
                        //aSwitch_p.setChecked(true);
                        tv_share_yes_p.setTextColor(getResources().getColor(R.color.white));
                        tv_share_no_p.setTextColor(getResources().getColor(R.color.black));
                        tv_share_yes_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                        tv_share_no_p.setBackgroundColor(getResources().getColor(R.color.grey));
                        sharable_p1 = 1;
                    } else {
                        // aSwitch_p.setChecked(false);
                        tv_share_yes_p.setTextColor(getResources().getColor(R.color.black));
                        tv_share_no_p.setTextColor(getResources().getColor(R.color.white));
                        tv_share_yes_p.setBackgroundColor(getResources().getColor(R.color.grey));
                        tv_share_no_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                        sharable_p1 = 0;
                    }
                    //toggleSwitch_P.setCheckedTogglePosition(sharable_p);
                    //weekdays
                    activeWeekDays_p(getActDays);

                } else if (redeem_type.equals("B")) {
                    linearLayout_B.setVisibility(View.VISIBLE);
                    editText_title_B.setText(title);
                    editText_title_B.setSelection(title.length());
                    editText_desc_B.setText(desc);
                    editText_points_B.setText(points);
                    editText_points_B.setEnabled(false);
                    editText_points_B.setAlpha(0.5f);
                    editText_wallet_B.setText(wallet);
                    tv_start_date_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + act_date + "</u>  </font>"));
                    tv_end_date_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + exp_date + "</u>  </font>"));
                    tv_start_time_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + act_time + "</u>  </font>"));
                    tv_end_time_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + exp_time + "</u>  </font>"));

                    //toggleSwitch_B.setCheckedTogglePosition(sharable_b);
                    if (sharable) {
                        //sharable_p = 1;
                        //aSwitch_b.setChecked(true);
                        tv_share_yes_b.setTextColor(getResources().getColor(R.color.white));
                        tv_share_no_b.setTextColor(getResources().getColor(R.color.black));
                        tv_share_yes_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                        tv_share_no_b.setBackgroundColor(getResources().getColor(R.color.grey));
                        sharable_b1 = 1;
                    } else {
                        //aSwitch_b.setChecked(false);
                        tv_share_yes_b.setTextColor(getResources().getColor(R.color.black));
                        tv_share_no_b.setTextColor(getResources().getColor(R.color.white));
                        tv_share_yes_b.setBackgroundColor(getResources().getColor(R.color.grey));
                        tv_share_no_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                        sharable_b1 = 0;
                    }
                    //weekdays
                    activeWeekDays_b(getActDays);


                } else if (redeem_type.equals("U")) {
                    linearLayout_U.setVisibility(View.VISIBLE);
                    fetchRedeemVoucherListRequestBody = new FetchRedeemVoucherListRequestBody();
                    fetchRedeemVoucherListRequestBody.setCallerType("m");
                    fetchRedeemVoucherListRequestBody.setMerId(preferences.getString(MERCHANT_ID, ""));
                    fetchRedeemVoucherListRequestBody.setDeviceId(preferences.getString(DEVICE_ID, ""));
                    fetchRedeemVoucherListRequestBody.setSessionToken("No-Token");
                    fetchRedeemVoucherListRequestBody.setVoucherType("Z");
                    fetchRedeemVoucherListRequestBody.setRequestCode(String.valueOf(GET_VOUCHER_LIST));
                    try {
                        callAPI();
                    } catch (Exception e) {
                        Log.d("GET-U-Type----", "onCreate: " + e.getMessage());
                    }

                    editText_title_U.setText(title);
                    editText_title_U.setSelection(title.length());
                    editText_desc_U.setText(desc);
                    editText_vouchers_pur_amt_U.setText(pur_amount);
                    editText_vouchers_count_U.setText(assgn_vouher_count);
                    tv_start_date_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + act_date + "</u>  </font>"));
                    tv_end_date_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + exp_date + "</u>  </font>"));
                    tv_start_time_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + act_time + "</u>  </font>"));
                    tv_end_time_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + exp_time + "</u>  </font>"));
                    activeWeekDays_u(getActDays);

                } else if (redeem_type.equals("Z")) {
                    linearLayout_Z.setVisibility(View.VISIBLE);
                    editText_title_Z.setText(title);
                    editText_title_Z.setSelection(title.length());
                    editText_desc_Z.setText(desc);
                    tv_start_date_Z.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + act_date + "</u>  </font>"));
                    tv_end_date_Z.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + exp_date + "</u>  </font>"));
                  /*  if (sharable) {
                        sharable_z = 1;
                    }
                    toggleSwitch_Z.setCheckedTogglePosition(sharable_z);*/

                    if (sharable) {
                        //sharable_p = 1;
                        //aSwitch_z.setChecked(true);
                        tv_share_yes_z.setTextColor(getResources().getColor(R.color.white));
                        tv_share_no_z.setTextColor(getResources().getColor(R.color.black));
                        tv_share_yes_z.setBackgroundColor(getResources().getColor(R.color.green_hase));
                        tv_share_no_z.setBackgroundColor(getResources().getColor(R.color.grey));
                        sharable_z1 = 1;
                    } else {
                        //aSwitch_z.setChecked(false);
                        tv_share_yes_z.setTextColor(getResources().getColor(R.color.black));
                        tv_share_no_z.setTextColor(getResources().getColor(R.color.white));
                        tv_share_yes_z.setBackgroundColor(getResources().getColor(R.color.grey));
                        tv_share_no_z.setBackgroundColor(getResources().getColor(R.color.green_hase));
                        sharable_z1 = 0;
                    }

                } else if (redeem_type.equals("M")) {
                    linearLayout_M.setVisibility(View.VISIBLE);
                    editText_title_M.setText(title);
                    editText_title_M.setSelection(title.length());
                    editText_desc_M.setText(desc);
                    editText_title_lead_M.setText(lead_title);
                    editText_desc_lead_M.setText(lead_desc);
                    editText_points_M.setText(ref_reward_points);
                    editText_points_M.setEnabled(false);
                    editText_points_M.setAlpha(0.5f);
                    tv_start_date_M.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + act_date + "</u>  </font>"));
                    tv_end_date_M.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + exp_date + "</u>  </font>"));
                /*    if (sharable) {
                        sharable_m = 1;
                    }
                    toggleSwitch_M.setCheckedTogglePosition(sharable_m);*/

                    if (sharable) {
                        //sharable_p = 1;
                        //aSwitch_m.setChecked(true);
                        tv_share_yes_m.setTextColor(getResources().getColor(R.color.white));
                        tv_share_no_m.setTextColor(getResources().getColor(R.color.black));
                        tv_share_yes_m.setBackgroundColor(getResources().getColor(R.color.green_hase));
                        tv_share_no_m.setBackgroundColor(getResources().getColor(R.color.grey));
                        sharable_m1 = 1;
                    } else {
                        //aSwitch_m.setChecked(false);

                        tv_share_yes_m.setTextColor(getResources().getColor(R.color.black));
                        tv_share_no_m.setTextColor(getResources().getColor(R.color.white));
                        tv_share_yes_m.setBackgroundColor(getResources().getColor(R.color.grey));
                        tv_share_no_m.setBackgroundColor(getResources().getColor(R.color.green_hase));
                        sharable_m1 = 0;
                    }
                }

            } else {
                //create_update_code="1";
                if (redeem_type.equals("P")) {
                    linearLayout_P.setVisibility(View.VISIBLE);
                    // setProductVoucher();
                } else if (redeem_type.equals("B")) {
                    linearLayout_B.setVisibility(View.VISIBLE);
                    //setWalletVoucher();
                } else if (redeem_type.equals("U")) {
                    linearLayout_U.setVisibility(View.VISIBLE);
                    fetchRedeemVoucherListRequestBody = new FetchRedeemVoucherListRequestBody();
                    fetchRedeemVoucherListRequestBody.setCallerType("m");
                    fetchRedeemVoucherListRequestBody.setMerId(preferences.getString(MERCHANT_ID, ""));
                    fetchRedeemVoucherListRequestBody.setDeviceId(preferences.getString(DEVICE_ID, ""));
                    fetchRedeemVoucherListRequestBody.setSessionToken("No-Token");
                    fetchRedeemVoucherListRequestBody.setVoucherType("Z");
                    fetchRedeemVoucherListRequestBody.setRequestCode(String.valueOf(GET_VOUCHER_LIST));
                    try {
                        callAPI();
                    } catch (Exception e) {
                        Log.d("GET-U-Type----", "onCreate: " + e.getMessage());
                    }

                    //setBulkVoucher();

                } else if (redeem_type.equals("Z")) {
                    linearLayout_Z.setVisibility(View.VISIBLE);
                    //setAssignedVoucher();
                } else if (redeem_type.equals("M")) {
                    linearLayout_M.setVisibility(View.VISIBLE);
                    //setMGMVoucher();
                }
            }
        }

     /*   for (int i = 0; i < assigned_list.size(); i++) {
            rbn = new RadioButton(this);
            //rbn.setId(View.generateViewId());
            rbn.setText(assigned_list.get(i));
            final int finalI = i;
            rbn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String radiobtn_value = assigned_list.get(finalI);
                    Toast.makeText(VoucherSetupCreate.this, "" + radiobtn_value, Toast.LENGTH_SHORT).show();
                  *//*  Intent intent = new Intent("custom-message");
                    intent.putExtra("item",radiobtn_value);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*//*
                }
            });
            radioGroup.addView(rbn);
        }*/


        toggleSwitch_P.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {

            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                sharable_p = position;
            }
        });
        toggleSwitch_B.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {

            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                sharable_b = position;
            }
        });
        toggleSwitch_Z.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {

            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                sharable_z = position;
            }
        });
        toggleSwitch_M.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {

            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                sharable_m = position;
            }
        });

        //Points
        btn_yes_sday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //s_status = "1";
                if (isActiveSunday_p) {
                    btn_yes_sday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_sday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveSunday_p = false;
                } else {
                    btn_yes_sday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_sday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveSunday_p = true;
                }
            }
        });

        btn_yes_mnday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  mn_status = "1";
                btn_yes_mnday.setBackgroundResource(R.drawable.yes_select_selector);*/
                if (isActiveMonday_p) {
                    btn_yes_mnday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_mnday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveMonday_p = false;
                } else {
                    btn_yes_mnday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_mnday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveMonday_p = true;
                }
            }
        });


        btn_yes_tsday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* ts_status = "1";
                btn_yes_tsday.setBackgroundResource(R.drawable.yes_select_selector);*/
                if (isActiveTuesday_p) {
                    btn_yes_tsday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_tsday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveTuesday_p = false;
                } else {
                    btn_yes_tsday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_tsday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveTuesday_p = true;
                }
            }
        });


        btn_yes_wedday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*wed_status = "1";
                btn_yes_wedday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveWednesday_p) {
                    btn_yes_wedday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_wedday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveWednesday_p = false;
                } else {
                    btn_yes_wedday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_wedday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveWednesday_p = true;
                }

            }
        });


        btn_yes_trsday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* thrs_status = "1";
                btn_yes_trsday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveThursday_p) {
                    btn_yes_trsday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_trsday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveThursday_p = false;
                } else {
                    btn_yes_trsday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_trsday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveThursday_p = true;
                }

            }
        });


        btn_yes_fdday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* fr_status = "1";
                btn_yes_fdday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveFriday_p) {
                    btn_yes_fdday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_fdday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveFriday_p = false;
                } else {
                    btn_yes_fdday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_fdday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveFriday_p = true;
                }

            }
        });


        btn_yes_strday_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  st_status = "1";
                btn_yes_strday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveSaturday_p) {
                    btn_yes_strday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_strday_p.setTextColor(getResources().getColor(R.color.black));
                    isActiveSaturday_p = false;
                } else {
                    btn_yes_strday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_strday_p.setTextColor(getResources().getColor(R.color.white));
                    isActiveSaturday_p = true;
                }
            }
        });


        //Wallet
        btn_yes_sday_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //s_status = "1";
                if (isActiveSunday_b) {
                    btn_yes_sday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_sday_b.setTextColor(getResources().getColor(R.color.black));
                    isActiveSunday_b = false;
                } else {
                    btn_yes_sday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_sday_b.setTextColor(getResources().getColor(R.color.white));
                    isActiveSunday_b = true;
                }
            }
        });

        btn_yes_mnday_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  mn_status = "1";
                btn_yes_mnday.setBackgroundResource(R.drawable.yes_select_selector);*/
                if (isActiveMonday_b) {
                    btn_yes_mnday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_mnday_b.setTextColor(getResources().getColor(R.color.black));
                    isActiveMonday_b = false;
                } else {
                    btn_yes_mnday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_mnday_b.setTextColor(getResources().getColor(R.color.white));
                    isActiveMonday_b = true;
                }
            }
        });


        btn_yes_tsday_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* ts_status = "1";
                btn_yes_tsday.setBackgroundResource(R.drawable.yes_select_selector);*/
                if (isActiveTuesday_b) {
                    btn_yes_tsday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_tsday_b.setTextColor(getResources().getColor(R.color.black));
                    isActiveTuesday_b = false;
                } else {
                    btn_yes_tsday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_tsday_b.setTextColor(getResources().getColor(R.color.white));
                    isActiveTuesday_b = true;
                }
            }
        });


        btn_yes_wedday_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*wed_status = "1";
                btn_yes_wedday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveWednesday_b) {
                    btn_yes_wedday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_wedday_b.setTextColor(getResources().getColor(R.color.black));
                    isActiveWednesday_b = false;
                } else {
                    btn_yes_wedday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_wedday_b.setTextColor(getResources().getColor(R.color.white));
                    isActiveWednesday_b = true;
                }

            }
        });


        btn_yes_trsday_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* thrs_status = "1";
                btn_yes_trsday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveThursday_b) {
                    btn_yes_trsday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_trsday_b.setTextColor(getResources().getColor(R.color.black));
                    isActiveThursday_b = false;
                } else {
                    btn_yes_trsday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_trsday_b.setTextColor(getResources().getColor(R.color.white));
                    isActiveThursday_b = true;
                }

            }
        });


        btn_yes_fdday_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* fr_status = "1";
                btn_yes_fdday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveFriday_b) {
                    btn_yes_fdday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_fdday_b.setTextColor(getResources().getColor(R.color.black));
                    isActiveFriday_b = false;
                } else {
                    btn_yes_fdday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_fdday_b.setTextColor(getResources().getColor(R.color.white));
                    isActiveFriday_b = true;
                }

            }
        });


        btn_yes_strday_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  st_status = "1";
                btn_yes_strday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveSaturday_b) {
                    btn_yes_strday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_strday_b.setTextColor(getResources().getColor(R.color.black));
                    isActiveSaturday_b = false;
                } else {
                    btn_yes_strday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_strday_b.setTextColor(getResources().getColor(R.color.white));
                    isActiveSaturday_b = true;
                }
            }
        });


        //Bulk offer
        btn_yes_sday_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //s_status = "1";
                if (isActiveSunday_u) {
                    btn_yes_sday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_sday_u.setTextColor(getResources().getColor(R.color.black));
                    isActiveSunday_u = false;
                } else {
                    btn_yes_sday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_sday_u.setTextColor(getResources().getColor(R.color.white));
                    isActiveSunday_u = true;
                }
            }
        });

        btn_yes_mnday_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  mn_status = "1";
                btn_yes_mnday.setBackgroundResource(R.drawable.yes_select_selector);*/
                if (isActiveMonday_u) {
                    btn_yes_mnday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_mnday_u.setTextColor(getResources().getColor(R.color.black));
                    isActiveMonday_u = false;
                } else {
                    btn_yes_mnday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_mnday_u.setTextColor(getResources().getColor(R.color.white));
                    isActiveMonday_u = true;
                }
            }
        });


        btn_yes_tsday_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* ts_status = "1";
                btn_yes_tsday.setBackgroundResource(R.drawable.yes_select_selector);*/
                if (isActiveTuesday_u) {
                    btn_yes_tsday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_tsday_u.setTextColor(getResources().getColor(R.color.black));
                    isActiveTuesday_u = false;
                } else {
                    btn_yes_tsday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_tsday_u.setTextColor(getResources().getColor(R.color.white));
                    isActiveTuesday_u = true;
                }
            }
        });


        btn_yes_wedday_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*wed_status = "1";
                btn_yes_wedday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveWednesday_u) {
                    btn_yes_wedday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_wedday_u.setTextColor(getResources().getColor(R.color.black));
                    isActiveWednesday_u = false;
                } else {
                    btn_yes_wedday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_wedday_u.setTextColor(getResources().getColor(R.color.white));
                    isActiveWednesday_u = true;
                }

            }
        });


        btn_yes_trsday_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* thrs_status = "1";
                btn_yes_trsday.setBackgroundResource(R.drawable.yes_select_selector);*/
                if (isActiveThursday_u) {
                    btn_yes_trsday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_trsday_u.setTextColor(getResources().getColor(R.color.black));
                    isActiveThursday_u = false;
                } else {
                    btn_yes_trsday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_trsday_u.setTextColor(getResources().getColor(R.color.white));
                    isActiveThursday_u = true;
                }

            }
        });


        btn_yes_fdday_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* fr_status = "1";
                btn_yes_fdday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveFriday_u) {
                    btn_yes_fdday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_fdday_u.setTextColor(getResources().getColor(R.color.black));
                    isActiveFriday_u = false;
                } else {
                    btn_yes_fdday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_fdday_u.setTextColor(getResources().getColor(R.color.white));
                    isActiveFriday_u = true;
                }

            }
        });


        btn_yes_strday_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  st_status = "1";
                btn_yes_strday.setBackgroundResource(R.drawable.yes_select_selector);*/

                if (isActiveSaturday_u) {
                    btn_yes_strday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                    btn_yes_strday_u.setTextColor(getResources().getColor(R.color.black));
                    isActiveSaturday_u = false;
                } else {
                    btn_yes_strday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                    btn_yes_strday_u.setTextColor(getResources().getColor(R.color.white));
                    isActiveSaturday_u = true;
                }
            }
        });


        button_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointsVoucher();
            }
        });
        button_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletVoucher();
            }
        });
        button_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bulkVoucher();
            }
        });
        button_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignedVoucher();
            }
        });
        button_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MCMVoucher();
            }
        });

        //Redeem product/service voucher - start date
        tv_start_date_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(VoucherSetupCreate.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //date_active = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    String day = "0" + (dayOfMonth);
                                    start_date_p = year + "-" + mnth + "-" + day;
                                } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    start_date_p = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String day = "0" + (dayOfMonth);
                                    start_date_p = year + "-" + (monthOfYear + 1) + "-" + day;
                                } else {
                                    start_date_p = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }

                                // editText_start_dt.setText(date_active);
                                tv_start_date_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + start_date_p + "</u>  </font>"));
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //c.add(Calendar.YEAR, +5);
                c.add(Calendar.MONTH, +12);
                Date dOneMonthAgo = c.getTime();
                long oneMonthAgoMillis = dOneMonthAgo.getTime();
                datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
                datePickerDialog.show();

            }
        });
        //Redeem product/service voucher - end date
        tv_end_date_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(VoucherSetupCreate.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    String day = "0" + (dayOfMonth);
                                    end_date_p = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    end_date_p = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String day = "0" + (dayOfMonth);
                                    end_date_p = year + "-" + (monthOfYear + 1) + "-" + day;
                                } else {
                                    end_date_p = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }

                                //editText_expiry_dt.setText(date_expiry);
                                tv_end_date_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + end_date_p + "</u>  </font>"));
                            }
                        }, mYear, mMonth, mDay);


                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //c.add(Calendar.YEAR, +5);
                c.add(Calendar.MONTH, +12);
                Date dOneMonthAgo = c.getTime();
                long oneMonthAgoMillis = dOneMonthAgo.getTime();
                datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
                datePickerDialog.show();

            }
        });
        //Redeem product/service voucher - start time
        tv_start_time_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStartTime_P();
            }
        });
        //Redeem product/service voucher - end time
        tv_end_time_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExpiryTime_P();
            }
        });


        //Redeem wallet balance  voucher - start date
        tv_start_date_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(VoucherSetupCreate.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //date_active = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    String day = "0" + (dayOfMonth);
                                    start_date_b = year + "-" + mnth + "-" + day;
                                } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    start_date_b = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String day = "0" + (dayOfMonth);
                                    start_date_b = year + "-" + (monthOfYear + 1) + "-" + day;
                                } else {
                                    start_date_b = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }

                                // editText_start_dt.setText(date_active);
                                tv_start_date_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + start_date_b + "</u>  </font>"));
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //c.add(Calendar.YEAR, +5);
                c.add(Calendar.MONTH, +12);
                Date dOneMonthAgo = c.getTime();
                long oneMonthAgoMillis = dOneMonthAgo.getTime();
                datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
                datePickerDialog.show();

            }
        });
        //Redeem wallet balance  voucher - end date
        tv_end_date_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(VoucherSetupCreate.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    String day = "0" + (dayOfMonth);
                                    end_date_b = day + "-" + mnth + "-" + day;
                                } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    end_date_b = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String day = "0" + (dayOfMonth);
                                    end_date_b = year + "-" + (monthOfYear + 1) + "-" + day;
                                } else {
                                    end_date_b = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }

                                //editText_expiry_dt.setText(date_expiry);
                                tv_end_date_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + end_date_b + "</u>  </font>"));
                            }
                        }, mYear, mMonth, mDay);


                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //c.add(Calendar.YEAR, +5);
                c.add(Calendar.MONTH, +12);
                Date dOneMonthAgo = c.getTime();
                long oneMonthAgoMillis = dOneMonthAgo.getTime();
                datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
                datePickerDialog.show();

            }
        });
        //Redeem wallet balance  voucher - start time
        tv_start_time_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStartTime_B();
            }
        });

        //Redeem wallet balance  voucher - end time
        tv_end_time_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExpiryTime_B();
            }
        });

        //Bulk Offer voucher - start date
        tv_start_date_U.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(VoucherSetupCreate.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //date_active = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    String day = "0" + (dayOfMonth);
                                    start_date_u = year + "-" + mnth + "-" + day;
                                } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    start_date_u = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String day = "0" + (dayOfMonth);
                                    start_date_u = year + "-" + (monthOfYear + 1) + "-" + day;
                                } else {
                                    start_date_u = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }

                                // editText_start_dt.setText(date_active);
                                tv_start_date_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + start_date_u + "</u>  </font>"));
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //c.add(Calendar.YEAR, +5);
                c.add(Calendar.MONTH, +12);
                Date dOneMonthAgo = c.getTime();
                long oneMonthAgoMillis = dOneMonthAgo.getTime();
                datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
                datePickerDialog.show();

            }
        });

        //Bulk Offer voucher - end date
        tv_end_date_U.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(VoucherSetupCreate.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    String day = "0" + (dayOfMonth);
                                    end_date_u = year + "-" + mnth + "-" + day;
                                } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    end_date_u = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String day = "0" + (dayOfMonth);
                                    end_date_u = year + "-" + (monthOfYear + 1) + "-" + day;
                                } else {
                                    end_date_u = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }

                                //editText_expiry_dt.setText(date_expiry);
                                tv_end_date_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + end_date_u + "</u>  </font>"));
                            }
                        }, mYear, mMonth, mDay);


                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //c.add(Calendar.YEAR, +5);
                c.add(Calendar.MONTH, +12);
                Date dOneMonthAgo = c.getTime();
                long oneMonthAgoMillis = dOneMonthAgo.getTime();
                datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
                datePickerDialog.show();

            }
        });
        //Bulk Offer voucher - start time
        tv_start_time_U.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStartTime_U();
            }
        });

        //Bulk Offer voucher - end time
        tv_end_time_U.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExpiryTime_U();
            }
        });

        //Assigned voucher - start date
        tv_start_date_Z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(VoucherSetupCreate.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //date_active = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    String day = "0" + (dayOfMonth);
                                    start_date_z = year + "-" + mnth + "-" + day;
                                } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    start_date_z = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String day = "0" + (dayOfMonth);
                                    start_date_z = year + "-" + (monthOfYear + 1) + "-" + day;
                                } else {
                                    start_date_z = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }

                                // editText_start_dt.setText(date_active);
                                tv_start_date_Z.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + start_date_z + "</u>  </font>"));
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //c.add(Calendar.YEAR, +5);
                c.add(Calendar.MONTH, +12);
                Date dOneMonthAgo = c.getTime();
                long oneMonthAgoMillis = dOneMonthAgo.getTime();
                datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
                datePickerDialog.show();

            }
        });
        //Assigned voucher - end date
        tv_end_date_Z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(VoucherSetupCreate.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    String day = "0" + (dayOfMonth);
                                    end_date_z = year + "-" + mnth + "-" + day;
                                } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    end_date_z = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String day = "0" + (dayOfMonth);
                                    end_date_z = year + "-" + (monthOfYear + 1) + "-" + day;
                                } else {
                                    end_date_z = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }

                                //editText_expiry_dt.setText(date_expiry);
                                tv_end_date_Z.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + end_date_z + "</u>  </font>"));
                            }
                        }, mYear, mMonth, mDay);


                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //c.add(Calendar.YEAR, +5);
                c.add(Calendar.MONTH, +12);
                Date dOneMonthAgo = c.getTime();
                long oneMonthAgoMillis = dOneMonthAgo.getTime();
                datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
                datePickerDialog.show();

            }
        });

        //Member bring member voucher - start date
        tv_start_date_M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(VoucherSetupCreate.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //date_active = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    String day = "0" + (dayOfMonth);
                                    start_date_m = year + "-" + mnth + "-" + day;
                                } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    start_date_m = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String day = "0" + (dayOfMonth);
                                    start_date_m = year + "-" + (monthOfYear + 1) + "-" + day;
                                } else {
                                    start_date_m = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }

                                // editText_start_dt.setText(date_active);
                                tv_start_date_M.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + start_date_m + "</u>  </font>"));
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //c.add(Calendar.YEAR, +5);
                c.add(Calendar.MONTH, +12);
                Date dOneMonthAgo = c.getTime();
                long oneMonthAgoMillis = dOneMonthAgo.getTime();
                datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
                datePickerDialog.show();

            }
        });
        //Member bring member voucher - end date
        tv_end_date_M.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(VoucherSetupCreate.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    String day = "0" + (dayOfMonth);
                                    end_date_m = year + "-" + mnth + "-" + day;
                                } else if (String.valueOf(monthOfYear + 1).length() == 1 && String.valueOf(dayOfMonth).length() != 1) {
                                    String mnth = "0" + (monthOfYear + 1);
                                    end_date_m = year + "-" + mnth + "-" + dayOfMonth;
                                } else if (String.valueOf(monthOfYear + 1).length() != 1 && String.valueOf(dayOfMonth).length() == 1) {
                                    String day = "0" + (dayOfMonth);
                                    end_date_m = year + "-" + (monthOfYear + 1) + "-" + day;
                                } else {
                                    end_date_m = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }

                                //editText_expiry_dt.setText(date_expiry);
                                tv_end_date_M.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + end_date_m + "</u>  </font>"));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //c.add(Calendar.YEAR, +5);
                c.add(Calendar.MONTH, +12);
                Date dOneMonthAgo = c.getTime();
                long oneMonthAgoMillis = dOneMonthAgo.getTime();
                datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
                datePickerDialog.show();

            }
        });


    }


    public String getActDays_P() {
        if (isActiveSunday_p) {
            activeDays_p = "y";
        } else {
            activeDays_p = "n";
        }

        if (isActiveMonday_p) {
            activeDays_p = activeDays_p + "" + "y";
        } else {
            activeDays_p = activeDays_p + "" + "n";
        }

        if (isActiveTuesday_p) {
            activeDays_p = activeDays_p + "" + "y";
        } else {
            activeDays_p = activeDays_p + "" + "n";
        }

        if (isActiveWednesday_p) {
            activeDays_p = activeDays_p + "" + "y";
        } else {
            activeDays_p = activeDays_p + "" + "n";
        }

        if (isActiveThursday_p) {
            activeDays_p = activeDays_p + "" + "y";
        } else {
            activeDays_p = activeDays_p + "" + "n";
        }

        if (isActiveFriday_p) {
            activeDays_p = activeDays_p + "" + "y";
        } else {
            activeDays_p = activeDays_p + "" + "n";
        }

        if (isActiveSaturday_p) {
            activeDays_p = activeDays_p + "" + "y";
        } else {
            activeDays_p = activeDays_p + "" + "n";
        }

        return activeDays_p;
    }

    public String getActDays_B() {
        if (isActiveSunday_b) {
            activeDays_b = "y";
        } else {
            activeDays_b = "n";
        }

        if (isActiveMonday_b) {
            activeDays_b = activeDays_b + "" + "y";
        } else {
            activeDays_b = activeDays_b + "" + "n";
        }

        if (isActiveTuesday_b) {
            activeDays_b = activeDays_b + "" + "y";
        } else {
            activeDays_b = activeDays_b + "" + "n";
        }

        if (isActiveWednesday_b) {
            activeDays_b = activeDays_b + "" + "y";
        } else {
            activeDays_b = activeDays_b + "" + "n";
        }

        if (isActiveThursday_b) {
            activeDays_b = activeDays_b + "" + "y";
        } else {
            activeDays_b = activeDays_b + "" + "n";
        }

        if (isActiveFriday_b) {
            activeDays_b = activeDays_b + "" + "y";
        } else {
            activeDays_b = activeDays_b + "" + "n";
        }

        if (isActiveSaturday_b) {
            activeDays_b = activeDays_b + "" + "y";
        } else {
            activeDays_b = activeDays_b + "" + "n";
        }

        return activeDays_b;
    }

    public String getActDays_U() {
        if (isActiveSunday_u) {
            activeDays_u = "y";
        } else {
            activeDays_u = "n";
        }

        if (isActiveMonday_u) {
            activeDays_u = activeDays_u + "" + "y";
        } else {
            activeDays_u = activeDays_u + "" + "n";
        }

        if (isActiveTuesday_u) {
            activeDays_u = activeDays_u + "" + "y";
        } else {
            activeDays_u = activeDays_u + "" + "n";
        }

        if (isActiveWednesday_u) {
            activeDays_u = activeDays_u + "" + "y";
        } else {
            activeDays_u = activeDays_u + "" + "n";
        }

        if (isActiveThursday_u) {
            activeDays_u = activeDays_u + "" + "y";
        } else {
            activeDays_u = activeDays_u + "" + "n";
        }

        if (isActiveFriday_u) {
            activeDays_u = activeDays_u + "" + "y";
        } else {
            activeDays_u = activeDays_u + "" + "n";
        }

        if (isActiveSaturday_u) {
            activeDays_u = activeDays_u + "" + "y";
        } else {
            activeDays_u = activeDays_u + "" + "n";
        }

        return activeDays_u;
    }


    public void pointsVoucher() {
        final String title = editText_title_P.getText().toString();
        final String desc = editText_desc_P.getText().toString();
        final String points = editText_points_P.getText().toString();
        final String exp_date = tv_end_date_P.getText().toString().trim();
        final String start_date = tv_start_date_P.getText().toString().trim();
        final String exp_time = tv_end_time_P.getText().toString().trim();
        final String start_time = tv_start_time_P.getText().toString().trim();
        final String sharable = String.valueOf(sharable_p1);
        final String getActDays_p = getActDays_P();
        Log.i("app---->", start_date + " , " + exp_date + "," + start_time + " , " + exp_time + " , " + getActDays_p + " , " + sharable);
        //&& Integer.parseInt(bonus) >= 1 && Integer.parseInt(bonus) <= 999999
        if (!TextUtils.isEmpty(title)) {
            if (title.length() >= 10) {
                if (!TextUtils.isEmpty(desc)) {
                    if (desc.length() >= 15) {
                        if (!TextUtils.isEmpty(points)) {
                            if (!TextUtils.isEmpty(start_date) && !start_date.equals("Select Activate Date")) {
                                if (!TextUtils.isEmpty(exp_date) && !exp_date.equals("Select Expiry Date")) {
                                    if (!TextUtils.isEmpty(start_time)) {
                                        if (!TextUtils.isEmpty(exp_time)) {
                                            if (!getActDays_p.equals("nnnnnnn")) {
                                                try {
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                                    String act_dt = start_date;
                                                    String exp_dt = exp_date;
                                                    Date date1 = sdf.parse(act_dt);
                                                    Date date2 = sdf.parse(exp_dt);

                                                    if (date1.compareTo(date2) < 0) {
                                                        Log.i("app", "Date1 is before Date2");
                                                        SharedPreferences.Editor editor = sharedpreferences_p_voucher.edit();
                                                        editor.putString(REDEEM_POINT_P, points);
                                                        editor.putString(REDEEM_TITLE_P, title);
                                                        editor.putString(REDEEM_DESCRIPTION_P, desc);
                                                        editor.putString(REDEEM_START_DATE_P, start_date);
                                                        editor.putString(REDEEM_START_TIME_P, start_time);
                                                        editor.putString(REDEEM_EXPIRY_DATE_P, exp_date);
                                                        editor.putString(REDEEM_EXPIRY_TIME_P, exp_time);
                                                        editor.putString(REDEEM_ACTIVE_DAYS_P, getActDays_p);
                                                        editor.putString(REDEEM_IS_SHARABLE_P, sharable);
                                                        editor.putString(REDEEM_BG_COLOR_P, bg_color);
                                                        editor.putString(REDEEM_MER_CB_REDEEM_ID_P, mer_cd_redeem_id);
                                                        editor.commit();

                                                        Intent in = new Intent(VoucherSetupCreate.this, VoucherSetupPreview.class);
                                                        in.putExtra("type_code", "1");
                                                        in.putExtra("redeem_type", "P");
                                                        in.putExtra("title", title);
                                                        in.putExtra("desc", desc);
                                                        in.putExtra("points", points);
                                                        in.putExtra("s_date", start_date);
                                                        in.putExtra("e_date", exp_date);
                                                        in.putExtra("s_time", start_time);
                                                        in.putExtra("e_time", exp_time);
                                                        in.putExtra("act_days", getActDays_p);
                                                        in.putExtra("sharable", sharable);
                                                        in.putExtra("mer_cd_redeem_id", mer_cd_redeem_id);
                                                        in.putExtra("create_update_code", create_update_code);
                                                        in.putExtra("terms_conditions", terms_conditions);
                                                        if (!TextUtils.isEmpty(bg_color)) {
                                                            in.putExtra("bg_color", bg_color);
                                                        }
                                                        startActivity(in);
                                                        finish();
                                                        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                                                    } else {
                                                        Toast.makeText(VoucherSetupCreate.this, "Please select valid active & expiry date", Toast.LENGTH_LONG).show();
                                                    }

                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }

                                            } else {
                                                Toast.makeText(VoucherSetupCreate.this, "Please select active weekdays", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(VoucherSetupCreate.this, "Please select expiry time", Toast.LENGTH_LONG).show();
                                        }

                                    } else {
                                        Toast.makeText(VoucherSetupCreate.this, "Please select active time", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(VoucherSetupCreate.this, "Please select expiry date", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(VoucherSetupCreate.this, "Please select active date", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            editText_points_P.setError("Enter valid points");
                            editText_points_P.requestFocus();
                        }

                    } else {
                        editText_desc_P.setError("Minimum 15chars");
                        editText_desc_P.requestFocus();
                    }
                } else {
                    editText_desc_P.setError("Enter valid description");
                    editText_desc_P.requestFocus();
                }


            } else {
                editText_title_P.setError("Minimum 10chars");
                editText_title_P.requestFocus();
            }

        } else {
            editText_title_P.setError("Enter valid title");
            editText_title_P.requestFocus();
        }
    }

    public void walletVoucher() {

        final String title = editText_title_B.getText().toString();
        final String desc = editText_desc_B.getText().toString();
        final String points = editText_points_B.getText().toString();
        final String wallet = editText_wallet_B.getText().toString();
        final String exp_date = tv_end_date_B.getText().toString().trim();
        final String start_date = tv_start_date_B.getText().toString().trim();
        final String exp_time = tv_end_time_B.getText().toString().trim();
        final String start_time = tv_start_time_B.getText().toString().trim();
        final String sharable = String.valueOf(sharable_b1);
        final String getActDays_b = getActDays_B();
        Log.i("app---->", start_date + " , " + exp_date + "," + start_time + " , " + exp_time + " , " + getActDays_b);
        //&& Integer.parseInt(bonus) >= 1 && Integer.parseInt(bonus) <= 999999

        if (!TextUtils.isEmpty(title)) {
            if (title.length() >= 10) {
                if (!TextUtils.isEmpty(desc)) {
                    if (desc.length() >= 15) {
                        if (!TextUtils.isEmpty(wallet)) {
                            if (!TextUtils.isEmpty(points)) {
                                if (!TextUtils.isEmpty(start_date) && !start_date.equals("Select Activate Date")) {
                                    if (!TextUtils.isEmpty(exp_date) && !exp_date.equals("Select Expiry Date")) {
                                        if (!TextUtils.isEmpty(start_time)) {
                                            if (!TextUtils.isEmpty(exp_time)) {
                                                if (!getActDays_b.equals("nnnnnnn")) {
                                                    //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                                                    try {
                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                                        String act_dt = start_date;
                                                        String exp_dt = exp_date;
                                                        Date date1 = sdf.parse(act_dt);
                                                        Date date2 = sdf.parse(exp_dt);

                                                        if (date1.compareTo(date2) < 0) {
                                                            Log.i("app", "Date1 is before Date2");

                                                            SharedPreferences.Editor editor = sharedpreferences_b_voucher.edit();
                                                            editor.putString(REDEEM_TITLE_B, title);
                                                            editor.putString(REDEEM_DESCRIPTION_B, desc);
                                                            editor.putString(REDEEM_POINT_B, points);
                                                            editor.putString(REDEEM_AMOUNT_B, wallet);
                                                            editor.putString(REDEEM_START_DATE_B, start_date);
                                                            editor.putString(REDEEM_START_TIME_B, start_time);
                                                            editor.putString(REDEEM_EXPIRY_DATE_B, exp_date);
                                                            editor.putString(REDEEM_EXPIRY_TIME_B, exp_time);
                                                            editor.putString(REDEEM_ACTIVE_DAYS_B, getActDays_b);
                                                            editor.putString(REDEEM_IS_SHARABLE_B, sharable);
                                                            editor.putString(REDEEM_BG_COLOR_B, bg_color);
                                                            editor.commit();

                                                            Intent in = new Intent(VoucherSetupCreate.this, VoucherSetupPreview.class);
                                                            in.putExtra("type_code", "1");
                                                            in.putExtra("redeem_type", "B");
                                                            in.putExtra("title", title);
                                                            in.putExtra("desc", desc);
                                                            in.putExtra("wallet", wallet);
                                                            in.putExtra("points", points);
                                                            in.putExtra("s_date", start_date);
                                                            in.putExtra("e_date", exp_date);
                                                            in.putExtra("s_time", start_time);
                                                            in.putExtra("e_time", exp_time);
                                                            in.putExtra("act_days", getActDays_b);
                                                            in.putExtra("sharable", sharable);
                                                            in.putExtra("mer_cd_redeem_id", mer_cd_redeem_id);
                                                            in.putExtra("create_update_code", create_update_code);
                                                            in.putExtra("terms_conditions", terms_conditions);
                                                            editor.putString(REDEEM_MER_CB_REDEEM_ID_B, mer_cd_redeem_id);
                                                            if (!TextUtils.isEmpty(bg_color)) {
                                                                in.putExtra("bg_color", bg_color);
                                                            }
                                                            startActivity(in);
                                                            finish();
                                                            overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                                                        } else {
                                                            Toast.makeText(VoucherSetupCreate.this, "Please select valid active & expiry date", Toast.LENGTH_LONG).show();
                                                        }

                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }

                                                } else {
                                                    Toast.makeText(VoucherSetupCreate.this, "Please select active weekdays", Toast.LENGTH_LONG).show();
                                                }

                                            } else {
                                                Toast.makeText(VoucherSetupCreate.this, "Please select expiry time", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(VoucherSetupCreate.this, "Please select active time", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(VoucherSetupCreate.this, "Please select expiry date", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(VoucherSetupCreate.this, "Please select active date", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                editText_points_B.setError("Enter valid points");
                                editText_points_B.requestFocus();
                            }
                        } else {
                            editText_wallet_B.setError("Enter valid wallet amount");
                            editText_wallet_B.requestFocus();
                        }

                    } else {
                        editText_desc_B.setError("Minimum 15chars");
                        editText_desc_B.requestFocus();
                    }
                } else {
                    editText_desc_B.setError("Enter valid description");
                    editText_desc_B.requestFocus();
                }


            } else {
                editText_title_B.setError("Minimum 10chars");
                editText_title_B.requestFocus();
            }

        } else {
            editText_title_B.setError("Enter valid title");
            editText_title_B.requestFocus();
        }
    }

    public void bulkVoucher() {

        //Toast.makeText(this, ""+selected_voucher_id, Toast.LENGTH_SHORT).show();
        final String title = editText_title_U.getText().toString();
        final String desc = editText_desc_U.getText().toString();
        final String voucher_count = editText_vouchers_count_U.getText().toString();
        final String exp_date = tv_end_date_U.getText().toString().trim();
        final String start_date = tv_start_date_U.getText().toString().trim();
        final String exp_time = tv_end_time_U.getText().toString().trim();
        final String start_time = tv_start_time_U.getText().toString().trim();
        final String pur_amt = editText_vouchers_pur_amt_U.getText().toString().trim();
//        final String assigned_voucher_id = spinner_assg_voucher.getSelectedItem().toString();
        //final String sharable = String.valueOf(sharable_u);
        final String getActDays_u = getActDays_U();
        Log.i("app---->", start_date + " , " + exp_date + "," + start_time + " , " + exp_time + " , " + getActDays_u + " , " + selected_voucher_id);
        //&& Integer.parseInt(bonus) >= 1 && Integer.parseInt(bonus) <= 999999

        if (!TextUtils.isEmpty(title)) {
            if (title.length() >= 10) {
                if (!TextUtils.isEmpty(desc)) {
                    if (desc.length() >= 15) {
                        if (!TextUtils.isEmpty(pur_amt) && !pur_amt.startsWith("0")) {
                            if (!TextUtils.isEmpty(voucher_count) && !voucher_count.startsWith("0")) {
                                if (!TextUtils.isEmpty(start_date) && !start_date.equals("Select Activate Date")) {
                                    if (!TextUtils.isEmpty(exp_date) && !exp_date.equals("Select Expiry Date")) {
                                        if (!TextUtils.isEmpty(start_time)) {
                                            if (!TextUtils.isEmpty(exp_time)) {
                                                if (!getActDays_u.equals("nnnnnnn")) {
                                                    if (!TextUtils.isEmpty(selected_voucher_id)) {
                                                        //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                                                        try {
                                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                                            String act_dt = start_date;
                                                            String exp_dt = exp_date;
                                                            Date date1 = sdf.parse(act_dt);
                                                            Date date2 = sdf.parse(exp_dt);

                                                            if (date1.compareTo(date2) < 0) {
                                                                String assignedList = new Gson().toJson(assigned_voucher_list);
                                                                String idList = new Gson().toJson(assigned_id_list);

                                                                Log.i("app", "Date1 is before Date2");
                                                                SharedPreferences.Editor editor = sharedpreferences_u_voucher.edit();
                                                                editor.putString(REDEEM_TITLE_U, title);
                                                                editor.putString(REDEEM_DESCRIPTION_U, desc);
                                                                editor.putString(REDEEM_VOUCHER_PUR_AMOUNT_U, pur_amt);
                                                                editor.putString(REDEEM_VOUCHER_COUNT_U, voucher_count);
                                                                editor.putString(REDEEM_VOUCHER_ID_U, selected_voucher_id);
                                                                editor.putString(REDEEM_START_DATE_U, start_date);
                                                                editor.putString(REDEEM_START_TIME_U, start_time);
                                                                editor.putString(REDEEM_EXPIRY_DATE_U, exp_date);
                                                                editor.putString(REDEEM_EXPIRY_TIME_U, exp_time);
                                                                editor.putString(REDEEM_ACTIVE_DAYS_U, getActDays_u);
                                                                editor.putString(REDEEM_ASSIGNED_VOUCHER_LIST_U, assignedList);
                                                                editor.putString(REDEEM_ASSIGNED_VOUCHER_ID_LIST_U, idList);
                                                                editor.putString(REDEEM_BG_COLOR_U, bg_color);
                                                                editor.putString(REDEEM_MER_CB_REDEEM_ID_U, mer_cd_redeem_id);
                                                                editor.commit();

                                                                Intent in = new Intent(VoucherSetupCreate.this, VoucherSetupPreview.class);
                                                                in.putExtra("type_code", "1");
                                                                in.putExtra("redeem_type", "U");
                                                                in.putExtra("title", title);
                                                                in.putExtra("desc", desc);
                                                                in.putExtra("pur_amount", pur_amt);
                                                                in.putExtra("v_count", voucher_count);
                                                                in.putExtra("v_id", selected_voucher_id);
                                                                in.putExtra("points", "0");
                                                                in.putExtra("s_date", start_date);
                                                                in.putExtra("e_date", exp_date);
                                                                in.putExtra("s_time", start_time);
                                                                in.putExtra("e_time", exp_time);
                                                                in.putExtra("act_days", getActDays_u);
                                                                in.putExtra("mer_cd_redeem_id", mer_cd_redeem_id);
                                                                in.putExtra("create_update_code", create_update_code);
                                                                in.putExtra("terms_conditions", terms_conditions);
                                                                if (!TextUtils.isEmpty(bg_color)) {
                                                                    in.putExtra("bg_color", bg_color);
                                                                }
                                                                //in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                startActivity(in);
                                                                finish();
                                                                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                                                            } else {
                                                                Toast.makeText(VoucherSetupCreate.this, "Please select valid active & expiry date", Toast.LENGTH_LONG).show();
                                                            }

                                                        } catch (ParseException e) {
                                                            e.printStackTrace();
                                                        }
                                                    } else {
                                                        //Toast.makeText(VoucherSetupCreate.this, "Please create assigned vouchers. Then try again!", Toast.LENGTH_LONG).show();

                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                AlertDialogFailure alertDialogFailure = new AlertDialogFailure(VoucherSetupCreate.this, "You should have a assigned voucher to create bulk offer voucher", "OK", "") {
                                                                    @Override
                                                                    public void onButtonClick() {
                                                                        startActivity(new Intent(VoucherSetupCreate.this, VoucherSetupHome.class));
                                                                        finish();
                                                                    }
                                                                };
                                                            }
                                                        });

                                                    }

                                                } else {
                                                    Toast.makeText(VoucherSetupCreate.this, "Please select active weekdays", Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                Toast.makeText(VoucherSetupCreate.this, "Please select expiry time", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(VoucherSetupCreate.this, "Please select active time", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(VoucherSetupCreate.this, "Please select expiry date", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(VoucherSetupCreate.this, "Please select active date", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                editText_vouchers_count_U.setError("Enter valid no.of vouchers");
                                editText_vouchers_count_U.requestFocus();
                            }
                        } else {
                            editText_vouchers_pur_amt_U.setError("Enter valid amount");
                            editText_vouchers_pur_amt_U.requestFocus();
                        }
                    } else {
                        editText_desc_U.setError("Minimum 15chars");
                        editText_desc_U.requestFocus();
                    }
                } else {
                    editText_desc_U.setError("Enter valid description");
                    editText_desc_U.requestFocus();
                }


            } else {
                editText_title_U.setError("Minimum 10chars");
                editText_title_U.requestFocus();
            }

        } else {
            editText_title_U.setError("Enter valid title");
            editText_title_U.requestFocus();
        }
    }

    public void assignedVoucher() {
        final String title = editText_title_Z.getText().toString();
        final String desc = editText_desc_Z.getText().toString();
        final String exp_date = tv_end_date_Z.getText().toString().trim();
        final String start_date = tv_start_date_Z.getText().toString().trim();
        final String sharable = String.valueOf(sharable_z1);
        Log.i("app---->", start_date + " , " + exp_date);
        //&& Integer.parseInt(bonus) >= 1 && Integer.parseInt(bonus) <= 999999

        if (!TextUtils.isEmpty(title)) {
            if (title.length() >= 10) {
                if (!TextUtils.isEmpty(desc)) {
                    if (desc.length() >= 15) {
                        if (!TextUtils.isEmpty(start_date) && !start_date.equals("Select Activate Date")) {
                            if (!TextUtils.isEmpty(exp_date) && !exp_date.equals("Select Expiry Date")) {
                                //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    String act_dt = start_date;
                                    String exp_dt = exp_date;
                                    Date date1 = sdf.parse(act_dt);
                                    Date date2 = sdf.parse(exp_dt);

                                    if (date1.compareTo(date2) < 0) {
                                        Log.i("app", "Date1 is before Date2");
                                        SharedPreferences.Editor editor = sharedpreferences_z_voucher.edit();
                                        editor.putString(REDEEM_TITLE_Z, title);
                                        editor.putString(REDEEM_DESCRIPTION_Z, desc);
                                        editor.putString(REDEEM_START_DATE_Z, start_date);
                                        editor.putString(REDEEM_EXPIRY_DATE_Z, exp_date);
                                        editor.putString(REDEEM_IS_SHARABLE_Z, sharable);
                                        editor.putString(REDEEM_BG_COLOR_Z, bg_color);
                                        editor.putString(REDEEM_MER_CB_REDEEM_ID_Z, mer_cd_redeem_id);
                                        editor.commit();

                                        Intent in = new Intent(VoucherSetupCreate.this, VoucherSetupPreview.class);
                                        in.putExtra("type_code", "1");
                                        in.putExtra("redeem_type", "Z");
                                        in.putExtra("title", title);
                                        in.putExtra("desc", desc);
                                        in.putExtra("sharable", sharable);
                                        in.putExtra("s_date", start_date);
                                        in.putExtra("e_date", exp_date);
                                        in.putExtra("mer_cd_redeem_id", mer_cd_redeem_id);
                                        in.putExtra("create_update_code", create_update_code);
                                        in.putExtra("terms_conditions", terms_conditions);
                                        if (!TextUtils.isEmpty(bg_color)) {
                                            in.putExtra("bg_color", bg_color);
                                        }
                                        startActivity(in);
                                        finish();
                                        overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                                    } else {
                                        Toast.makeText(VoucherSetupCreate.this, "Please select valid active & expiry date", Toast.LENGTH_LONG).show();
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            } else {
                                Toast.makeText(VoucherSetupCreate.this, "Please select expiry date", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(VoucherSetupCreate.this, "Please select active date", Toast.LENGTH_LONG).show();
                        }


                    } else {
                        editText_desc_U.setError("Minimum 15chars");
                        editText_desc_U.requestFocus();
                    }
                } else {
                    editText_desc_U.setError("Enter valid description");
                    editText_desc_U.requestFocus();
                }


            } else {
                editText_title_U.setError("Minimum 10chars");
                editText_title_U.requestFocus();
            }

        } else {
            editText_title_U.setError("Enter valid title");
            editText_title_U.requestFocus();
        }
    }


    public void MCMVoucher() {

        final String title = editText_title_M.getText().toString();
        final String desc = editText_desc_M.getText().toString();
        final String lead_title = editText_title_lead_M.getText().toString();
        final String lead_desc = editText_desc_lead_M.getText().toString();
        final String exp_date = tv_end_date_M.getText().toString().trim();
        final String start_date = tv_start_date_M.getText().toString().trim();
        final String points = editText_points_M.getText().toString().trim();
        final String sharable = String.valueOf(sharable_m1);
        Log.i("app---->", start_date + " , " + exp_date);
        //&& Integer.parseInt(bonus) >= 1 && Integer.parseInt(bonus) <= 999999

        if (!TextUtils.isEmpty(title)) {
            if (title.length() >= 10) {
                if (!TextUtils.isEmpty(desc)) {
                    if (desc.length() >= 15) {
                        if (!TextUtils.isEmpty(lead_title)) {
                            if (lead_title.length() >= 10) {
                                if (!TextUtils.isEmpty(lead_desc)) {
                                    if (lead_desc.length() >= 15) {
                                        if (!TextUtils.isEmpty(points)) {
                                            if (!TextUtils.isEmpty(start_date) && !start_date.equals("Select Activate Date")) {
                                                if (!TextUtils.isEmpty(exp_date) && !exp_date.equals("Select Expiry Date")) {
                                                    //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                                                    try {
                                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                                        String act_dt = start_date;
                                                        String exp_dt = exp_date;
                                                        Date date1 = sdf.parse(act_dt);
                                                        Date date2 = sdf.parse(exp_dt);

                                                        if (date1.compareTo(date2) < 0) {
                                                            Log.i("app", "Date1 is before Date2");
                                                            SharedPreferences.Editor editor = sharedpreferences_m_voucher.edit();
                                                            editor.putString(REDEEM_TITLE_M, title);
                                                            editor.putString(REDEEM_DESCRIPTION_M, desc);
                                                            editor.putString(REDEEM_LEAD_TITLE_M, lead_title);
                                                            editor.putString(REDEEM_LEAD_DESCRIPTION_M, lead_desc);
                                                            editor.putString(REDEEM_POINT_M, points);
                                                            editor.putString(REDEEM_START_DATE_M, start_date);
                                                            editor.putString(REDEEM_EXPIRY_DATE_M, exp_date);
                                                            editor.putString(REDEEM_IS_SHARABLE_M, sharable);
                                                            editor.putString(REDEEM_BG_COLOR_M, bg_color);
                                                            editor.putString(REDEEM_MER_CB_REDEEM_ID_M, mer_cd_redeem_id);
                                                            editor.commit();
                                                            Intent in = new Intent(VoucherSetupCreate.this, VoucherSetupPreview.class);
                                                            in.putExtra("type_code", "1");
                                                            in.putExtra("redeem_type", "M");
                                                            in.putExtra("title", title);
                                                            in.putExtra("desc", desc);
                                                            in.putExtra("lead_title", lead_title);
                                                            in.putExtra("lead_desc", lead_desc);
                                                            in.putExtra("sharable", sharable);
                                                            in.putExtra("s_date", start_date);
                                                            in.putExtra("e_date", exp_date);
                                                            in.putExtra("mer_cd_redeem_id", mer_cd_redeem_id);
                                                            in.putExtra("create_update_code", create_update_code);
                                                            in.putExtra("terms_conditions", terms_conditions);
                                                            in.putExtra("ref_reward_points", points);
                                                            if (!TextUtils.isEmpty(bg_color)) {
                                                                in.putExtra("bg_color", bg_color);
                                                            }
                                                            startActivity(in);
                                                            finish();
                                                            overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

                                                        } else {
                                                            Toast.makeText(VoucherSetupCreate.this, "Please select valid active & expiry date", Toast.LENGTH_LONG).show();
                                                        }

                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }


                                                } else {
                                                    Toast.makeText(VoucherSetupCreate.this, "Please select expiry date", Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                Toast.makeText(VoucherSetupCreate.this, "Please select active date", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            editText_points_M.setError("Enter valid points");
                                            editText_points_M.requestFocus();
                                        }
                                    } else {
                                        editText_desc_lead_M.setError("Minimum 15chars");
                                        editText_desc_lead_M.requestFocus();
                                    }
                                } else {
                                    editText_desc_lead_M.setError("Enter valid new customer description");
                                    editText_desc_lead_M.requestFocus();
                                }
                            } else {
                                editText_title_lead_M.setError("Minimum 10chars");
                                editText_title_lead_M.requestFocus();
                            }
                        } else {
                            editText_title_lead_M.setError("Enter valid new customer title");
                            editText_title_lead_M.requestFocus();
                        }
                    } else {
                        editText_desc_U.setError("Minimum 15chars");
                        editText_desc_U.requestFocus();
                    }
                } else {
                    editText_desc_U.setError("Enter valid description");
                    editText_desc_U.requestFocus();
                }

            } else {
                editText_title_U.setError("Minimum 10chars");
                editText_title_U.requestFocus();
            }

        } else {
            editText_title_U.setError("Enter valid title");
            editText_title_U.requestFocus();
        }

    }

    public void getStartTime_P() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            start_time_p = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            start_time_p = hr + ":" + minute;

                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            start_time_p = hourOfDay + ":" + min;

                        } else {
                            start_time_p = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + start_time_p);
                        //editText_start_time.setText(time_active);
                        tv_start_time_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + start_time_p + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void getExpiryTime_P() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            end_time_p = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            end_time_p = hr + ":" + minute;

                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            end_time_p = hourOfDay + ":" + min;

                        } else {
                            end_time_p = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + end_time_p);
                        //editText_expiry_time.setText(time_expiry);
                        tv_end_time_P.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + end_time_p + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void getStartTime_B() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            start_time_b = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            start_time_b = hr + ":" + minute;

                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            start_time_b = hourOfDay + ":" + min;

                        } else {
                            start_time_b = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + start_time_b);
                        //editText_start_time.setText(time_active);
                        tv_start_time_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + start_time_b + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void getExpiryTime_B() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            end_time_b = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            end_time_b = hr + ":" + minute;

                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            end_time_b = hourOfDay + ":" + min;

                        } else {
                            end_time_b = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + end_time_b);
                        //editText_expiry_time.setText(time_expiry);
                        tv_end_time_B.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + end_time_b + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void getStartTime_U() {
        // Get Current Time*
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            start_time_u = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            start_time_u = hr + ":" + minute;

                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            start_time_u = hourOfDay + ":" + min;

                        } else {
                            start_time_u = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + start_time_u);
                        //editText_start_time.setText(time_active);
                        tv_start_time_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + start_time_u + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void getExpiryTime_U() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mSeconds = c.get(Calendar.SECOND);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        //view.setBackgroundColor(getResources().getColor(R.color.button_background));
                        String time_hr = String.valueOf(hourOfDay);
                        String time_min = String.valueOf(minute);
                        String min = null, hr = null;
                        if (time_hr.length() == 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            hr = "0" + "" + time_hr;
                            end_time_u = hr + ":" + min;
                        } else if (time_hr.length() == 1 && time_min.length() != 1) {
                            hr = "0" + "" + time_hr;
                            end_time_u = hr + ":" + minute;

                        } else if (time_hr.length() != 1 && time_min.length() == 1) {
                            min = "0" + "" + time_min;
                            end_time_u = hourOfDay + ":" + min;

                        } else {
                            end_time_u = hourOfDay + ":" + minute;
                        }
                        Log.d("Time===>", "" + end_time_u);
                        //editText_expiry_time.setText(time_expiry);
                        tv_end_time_U.setText(Html.fromHtml("<font color=#3B91CD>  <u>" + end_time_u + "</u>  </font>"));

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(VoucherSetupCreate.this, VoucherSetupHome.class));
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);

       /*     if(redeem_id.equals("0")) {
                Intent in = new Intent(VoucherSetupCreate.this, Add_Redemption_Activity.class);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }else {
                Intent in = new Intent(VoucherSetupCreate.this, Add_Redemption_Activity.class);
                startActivity(in);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }*/

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            selected_radio_voucher = intent.getStringExtra("item");
            // Toast.makeText(context, "" + selected_radio_voucher, Toast.LENGTH_SHORT).show();

        }
    };

    private void fetchVoucherList(FetchRedeemVoucherListRequestBody requestBody) throws ApiException {

        Log.d("Voucher---", "uploadVoucher: " + requestBody);
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(VoucherSetupCreate.this));
        MerchantApisApi webMerchantApisApi = new MerchantApisApi();
        webMerchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        webMerchantApisApi.genericVoucherGetListAsync(requestBody, new ApiCallback<RedeemVoucherListResponse>() {
            @Override
            public void onFailure(ApiException e, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("VoucherList---", "onFailure: " + e.getMessage() + "," + statusCode);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialogYesNo = new AlertDialogYesNo(VoucherSetupCreate.this, "", "Something went wrong. Please Try Again!", "YES", "OK") {
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

            @Override
            public void onSuccess(RedeemVoucherListResponse result, int statusCode, Map<String, List<String>> responseHeaders) {

                Log.d("VoucherList---", "onSuccess: " + result.getStatusCode() + " , " + statusCode);
                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                if (Integer.parseInt(result.getStatusCode()) == 200) {
                    List<RedeemVoucher> redeemVouchers = result.getRedeemVouchers();
                    Log.d("V_List--->", "onSuccess: " + redeemVouchers.size());
                    if (redeemVouchers.size() == 0) {
                        textView_no_vouchers.setVisibility(View.VISIBLE);
                        textView_no_assigned_vouchers.setVisibility(View.VISIBLE);
                        spinner_assg_voucher.setVisibility(View.GONE);
                    } else {
                        for (int t = 0; t < redeemVouchers.size(); t++) {
                            String title = redeemVouchers.get(t).getRedeemTitle();
                            String mer_cb_redeem_id = redeemVouchers.get(t).getMerCbRedeemId();
                            assigned_voucher_list.add(title);
                            assigned_id_list.add(mer_cb_redeem_id);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView_no_vouchers.setVisibility(View.GONE);
                                textView_no_assigned_vouchers.setVisibility(View.GONE);
                                spinner_assg_voucher.setVisibility(View.VISIBLE);

                                ArrayAdapter a = new ArrayAdapter(VoucherSetupCreate.this, android.R.layout.simple_spinner_item, assigned_voucher_list);
                                a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_assg_voucher.setAdapter(a);
                                spinner_assg_voucher.setPrompt("Select Assigned Voucher");

                                if (redeem_id.equals("0")) {
                                    Log.d("Assigned--->", "Assigned :0 " + r_voucher_id);
                                    int assg_id_pos = assigned_id_list.indexOf(r_voucher_id);
                                    String assg_value = assigned_voucher_list.get(assg_id_pos);
                                    Log.d("Assg_value---", "onCreate: " + assg_id_pos + "," + assg_value + "," + r_voucher_id);
                                    if (assg_value != null) {
                                        selected_voucher_id = r_voucher_id;
                                        int spinnerPosition = a.getPosition(assg_value);
                                        spinner_assg_voucher.setSelection(spinnerPosition);
                                        a.notifyDataSetChanged();
                                    }
                                    spinner_assg_voucher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            //Toast.makeText(VoucherSetupCreate.this, "" + assigned_id_list.get(position), Toast.LENGTH_SHORT).show();
                                            selected_voucher_id = assigned_id_list.get(position);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                } else {
                                    Log.d("Assigned--->", "Assigned :1");
                                    spinner_assg_voucher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            // Toast.makeText(VoucherSetupCreate.this, "" + assigned_id_list.get(position), Toast.LENGTH_SHORT).show();
                                            selected_voucher_id = assigned_id_list.get(position);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });
                                }


                            }
                        });
                    }

                } else if (Integer.parseInt(result.getStatusCode()) == 204) {
                    //No vouchers found
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView_no_vouchers.setVisibility(View.VISIBLE);
                            textView_no_assigned_vouchers.setVisibility(View.VISIBLE);
                            spinner_assg_voucher.setVisibility(View.GONE);
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 502) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialogFailure alertDialogFailure = new AlertDialogFailure(VoucherSetupCreate.this, "Something went wrong. Please try again!", "OK", "FAILED") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupCreate.this, VoucherSetupHome.class));
                                    finish();
                                }
                            };
                        }
                    });


                } else if (Integer.parseInt(result.getStatusCode()) == 506) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialogFailure alertDialogFailure = new AlertDialogFailure(VoucherSetupCreate.this, "Merchant device does not exist or Inactive or not an Admin device", "OK", "FAILED") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupCreate.this, VoucherSetupHome.class));
                                    finish();
                                }
                            };
                        }
                    });

                } else if (Integer.parseInt(result.getStatusCode()) == 510) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialogYesNo = new AlertDialogYesNo(VoucherSetupCreate.this, "", "Requested Voucher type is Invalid", "OK", "OK") {
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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialogFailure alertDialogFailure = new AlertDialogFailure(VoucherSetupCreate.this, "Something went wrong. Please try again!", "OK", "FAILED") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(VoucherSetupCreate.this, VoucherSetupHome.class));
                                    finish();
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

    public void activeWeekDays_p(String getActDays) {
        if (!TextUtils.isEmpty(getActDays)) {
            char get1 = getActDays.charAt(0);
            char get2 = getActDays.charAt(1);
            char get3 = getActDays.charAt(2);
            char get4 = getActDays.charAt(3);
            char get5 = getActDays.charAt(4);
            char get6 = getActDays.charAt(5);
            char get7 = getActDays.charAt(6);
            Log.d("ActDays===>", "" + get1 + "," + get2 + "," + get3 + "," + get4 + "," + get5 + "," + get6 + "," + get7);
            //Sunday
            if (String.valueOf(get1).equals("y")) {
                btn_yes_sday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_sday_p.setTextColor(getResources().getColor(R.color.white));
                isActiveSunday_p = true;
            } else {
                btn_yes_sday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_sday_p.setTextColor(getResources().getColor(R.color.black));
                isActiveSunday_p = false;
            }

            //Monday
            if (String.valueOf(get2).equals("y")) {
                btn_yes_mnday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_mnday_p.setTextColor(getResources().getColor(R.color.white));
                isActiveMonday_p = true;
            } else {
                btn_yes_mnday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_mnday_p.setTextColor(getResources().getColor(R.color.black));
                isActiveMonday_p = false;
            }

            //Tuesday
            if (String.valueOf(get3).equals("y")) {
                btn_yes_tsday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_tsday_p.setTextColor(getResources().getColor(R.color.white));
                isActiveTuesday_p = true;
            } else {
                btn_yes_tsday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_tsday_p.setTextColor(getResources().getColor(R.color.black));
                isActiveTuesday_p = false;
            }

            //Wednesday
            if (String.valueOf(get4).equals("y")) {
                btn_yes_wedday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_wedday_p.setTextColor(getResources().getColor(R.color.white));
                isActiveWednesday_p = true;
            } else {
                btn_yes_wedday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_wedday_p.setTextColor(getResources().getColor(R.color.black));
                isActiveWednesday_p = false;

            }

            //Thursday
            if (String.valueOf(get5).equals("y")) {
                btn_yes_trsday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_trsday_p.setTextColor(getResources().getColor(R.color.white));
                isActiveThursday_p = true;
            } else {
                btn_yes_trsday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_trsday_p.setTextColor(getResources().getColor(R.color.black));
                isActiveThursday_p = false;
            }

            //Friday
            if (String.valueOf(get6).equals("y")) {
                btn_yes_fdday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_fdday_p.setTextColor(getResources().getColor(R.color.white));
                isActiveFriday_p = true;
            } else {
                btn_yes_fdday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_fdday_p.setTextColor(getResources().getColor(R.color.black));
                isActiveFriday_p = false;
            }

            //Saturday
            if (String.valueOf(get7).equals("y")) {
                btn_yes_strday_p.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_strday_p.setTextColor(getResources().getColor(R.color.white));
                isActiveSaturday_p = true;
            } else {
                btn_yes_strday_p.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_strday_p.setTextColor(getResources().getColor(R.color.black));
                isActiveSaturday_p = false;
            }
        }


    }

    private void activeWeekDays_b(String getActDays) {
        if (!TextUtils.isEmpty(getActDays)) {
            char get1 = getActDays.charAt(0);
            char get2 = getActDays.charAt(1);
            char get3 = getActDays.charAt(2);
            char get4 = getActDays.charAt(3);
            char get5 = getActDays.charAt(4);
            char get6 = getActDays.charAt(5);
            char get7 = getActDays.charAt(6);
            Log.d("ActDays===>", "" + get1 + "," + get2 + "," + get3 + "," + get4 + "," + get5 + "," + get6 + "," + get7);
            //Sunday
            if (String.valueOf(get1).equals("y")) {
                btn_yes_sday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_sday_b.setTextColor(getResources().getColor(R.color.white));
                isActiveSunday_b = true;
            } else {
                btn_yes_sday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_sday_b.setTextColor(getResources().getColor(R.color.black));
                isActiveSunday_b = false;
            }

            //Monday
            if (String.valueOf(get2).equals("y")) {
                btn_yes_mnday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_mnday_b.setTextColor(getResources().getColor(R.color.white));
                isActiveMonday_b = true;
            } else {
                btn_yes_mnday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_mnday_b.setTextColor(getResources().getColor(R.color.black));
                isActiveMonday_b = false;
            }

            //Tuesday
            if (String.valueOf(get3).equals("y")) {
                btn_yes_tsday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_tsday_b.setTextColor(getResources().getColor(R.color.white));
                isActiveTuesday_b = true;
            } else {
                btn_yes_tsday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_tsday_b.setTextColor(getResources().getColor(R.color.black));
                isActiveTuesday_b = false;
            }

            //Wednesday
            if (String.valueOf(get4).equals("y")) {
                btn_yes_wedday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_wedday_b.setTextColor(getResources().getColor(R.color.white));
                isActiveWednesday_b = true;
            } else {
                btn_yes_wedday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_wedday_b.setTextColor(getResources().getColor(R.color.black));
                isActiveWednesday_b = false;
            }

            //Thursday
            if (String.valueOf(get5).equals("y")) {
                btn_yes_trsday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_trsday_b.setTextColor(getResources().getColor(R.color.white));
                isActiveThursday_b = true;
            } else {
                btn_yes_trsday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_trsday_b.setTextColor(getResources().getColor(R.color.black));
                isActiveThursday_b = false;
            }

            //Friday
            if (String.valueOf(get6).equals("y")) {
                btn_yes_fdday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_fdday_b.setTextColor(getResources().getColor(R.color.white));
                isActiveFriday_b = true;
            } else {
                btn_yes_fdday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_fdday_b.setTextColor(getResources().getColor(R.color.black));
                isActiveFriday_b = false;
            }

            //Saturday
            if (String.valueOf(get7).equals("y")) {
                btn_yes_strday_b.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_strday_b.setTextColor(getResources().getColor(R.color.white));
                isActiveSaturday_b = true;
            } else {
                btn_yes_strday_b.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_strday_b.setTextColor(getResources().getColor(R.color.black));
                isActiveSaturday_b = false;
            }
        }
    }

    private void activeWeekDays_u(String getActDays) {
        if (!TextUtils.isEmpty(getActDays)) {
            char get1 = getActDays.charAt(0);
            char get2 = getActDays.charAt(1);
            char get3 = getActDays.charAt(2);
            char get4 = getActDays.charAt(3);
            char get5 = getActDays.charAt(4);
            char get6 = getActDays.charAt(5);
            char get7 = getActDays.charAt(6);
            Log.d("ActDays===>", "" + get1 + "," + get2 + "," + get3 + "," + get4 + "," + get5 + "," + get6 + "," + get7);
            //Sunday
            if (String.valueOf(get1).equals("y")) {
                btn_yes_sday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_sday_u.setTextColor(getResources().getColor(R.color.white));
                isActiveSunday_u = true;
            } else {
                btn_yes_sday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_sday_u.setTextColor(getResources().getColor(R.color.black));
                isActiveSunday_u = false;
            }


            //Monday
            if (String.valueOf(get2).equals("y")) {
                btn_yes_mnday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_mnday_u.setTextColor(getResources().getColor(R.color.white));
                isActiveMonday_u = true;
            } else {
                btn_yes_mnday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_mnday_u.setTextColor(getResources().getColor(R.color.black));
                isActiveMonday_u = false;
            }

            //Tuesday
            if (String.valueOf(get3).equals("y")) {
                btn_yes_tsday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_tsday_u.setTextColor(getResources().getColor(R.color.white));
                isActiveTuesday_u = true;
            } else {
                btn_yes_tsday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_tsday_u.setTextColor(getResources().getColor(R.color.black));
                isActiveTuesday_u = false;
            }

            //Wednesday
            if (String.valueOf(get4).equals("y")) {
                btn_yes_wedday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_wedday_u.setTextColor(getResources().getColor(R.color.white));
                isActiveWednesday_u = true;
            } else {
                btn_yes_wedday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_wedday_u.setTextColor(getResources().getColor(R.color.black));
                isActiveWednesday_u = false;
            }

            //Thursday
            if (String.valueOf(get5).equals("y")) {
                btn_yes_trsday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_trsday_u.setTextColor(getResources().getColor(R.color.white));
                isActiveThursday_u = true;
            } else {
                btn_yes_trsday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_trsday_u.setTextColor(getResources().getColor(R.color.black));
                isActiveThursday_u = false;
            }

            //Friday
            if (String.valueOf(get6).equals("y")) {
                btn_yes_fdday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_fdday_u.setTextColor(getResources().getColor(R.color.white));
                isActiveFriday_u = true;
            } else {
                btn_yes_fdday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_fdday_u.setTextColor(getResources().getColor(R.color.black));
                isActiveFriday_u = false;
            }

            //Saturday
            if (String.valueOf(get7).equals("y")) {
                btn_yes_strday_u.setBackgroundColor(getResources().getColor(R.color.green_hase));
                btn_yes_strday_u.setTextColor(getResources().getColor(R.color.white));
                isActiveSaturday_u = true;
            } else {
                btn_yes_strday_u.setBackgroundColor(getResources().getColor(R.color.dark_grey));
                btn_yes_strday_u.setTextColor(getResources().getColor(R.color.black));
                isActiveSaturday_u = false;
            }
        }
    }

    private void callAPI() {
        intermediateAlertDialog = new IntermediateAlertDialog(VoucherSetupCreate.this);
        if (Util.getConnectivityStatusString(this)) {
            try {
                fetchVoucherList(fetchRedeemVoucherListRequestBody);
            } catch (Exception e) {

                if (intermediateAlertDialog != null) {
                    intermediateAlertDialog.dismissAlertDialog();
                }
                new AlertDialogFailure(this, "Please try again later", "OK", "Something went wrong") {
                    @Override
                    public void onButtonClick() {
                        startActivity(new Intent(VoucherSetupCreate.this, Homenew.class));
                        finish();
                    }
                };
            }
        } else {

            if (intermediateAlertDialog != null) {
                intermediateAlertDialog.dismissAlertDialog();
            }
            new AlertDialogFailure(VoucherSetupCreate.this, "Please turn ON your data connection ", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callAPI();
                }
            };
        }
    }


    private final TextWatcher textWatcher_title_p = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if (s.length() < 10) {
                tv_min_title_p.setVisibility(View.VISIBLE);
                tv_count_title_p.setText(String.valueOf(s.length() + "/100"));
                tv_count_title_p.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv_min_title_p.setVisibility(View.INVISIBLE);
                tv_count_title_p.setText(String.valueOf(s.length() + "/100"));
                tv_count_title_p.setTextColor(getResources().getColor(R.color.green2));
            }
        }

        public void afterTextChanged(Editable s) {

        }
    };

    private final TextWatcher textWatcher_desc_p = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if (s.length() < 15) {
                tv_min_desc_p.setVisibility(View.VISIBLE);
                tv_count_desc_p.setText(String.valueOf(s.length() + "/200"));
                tv_count_desc_p.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv_min_desc_p.setVisibility(View.INVISIBLE);
                tv_count_desc_p.setText(String.valueOf(s.length() + "/200"));
                tv_count_desc_p.setTextColor(getResources().getColor(R.color.green2));
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };
    private final TextWatcher textWatcher_title_b = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if (s.length() < 10) {
                tv_min_title_b.setVisibility(View.VISIBLE);
                tv_count_title_b.setText(String.valueOf(s.length() + "/100"));
                tv_count_title_b.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv_min_title_b.setVisibility(View.INVISIBLE);
                tv_count_title_b.setText(String.valueOf(s.length() + "/100"));
                tv_count_title_b.setTextColor(getResources().getColor(R.color.green2));
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    private final TextWatcher textWatcher_desc_b = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if (s.length() < 15) {
                tv_min_desc_b.setVisibility(View.VISIBLE);
                tv_count_desc_b.setText(String.valueOf(s.length() + "/200"));
                tv_count_desc_b.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv_min_desc_b.setVisibility(View.INVISIBLE);
                tv_count_desc_b.setText(String.valueOf(s.length() + "/200"));
                tv_count_desc_b.setTextColor(getResources().getColor(R.color.green2));
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };
    private final TextWatcher textWatcher_title_u = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if (s.length() < 10) {
                tv_min_title_u.setVisibility(View.VISIBLE);
                tv_count_title_u.setText(String.valueOf(s.length() + "/100"));
                tv_count_title_u.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv_min_title_u.setVisibility(View.INVISIBLE);
                tv_count_title_u.setText(String.valueOf(s.length() + "/100"));
                tv_count_title_u.setTextColor(getResources().getColor(R.color.green2));
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    private final TextWatcher textWatcher_desc_u = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if (s.length() < 15) {
                tv_min_desc_u.setVisibility(View.VISIBLE);
                tv_count_desc_u.setText(String.valueOf(s.length() + "/200"));
                tv_count_desc_u.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv_min_desc_u.setVisibility(View.INVISIBLE);
                tv_count_desc_u.setText(String.valueOf(s.length() + "/200"));
                tv_count_desc_u.setTextColor(getResources().getColor(R.color.green2));
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };
    private final TextWatcher textWatcher_title_z = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if (s.length() < 10) {
                tv_min_title_z.setVisibility(View.VISIBLE);
                tv_count_title_z.setText(String.valueOf(s.length() + "/100"));
                tv_count_title_z.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv_min_title_z.setVisibility(View.INVISIBLE);
                tv_count_title_z.setText(String.valueOf(s.length() + "/100"));
                tv_count_title_z.setTextColor(getResources().getColor(R.color.green2));
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    private final TextWatcher textWatcher_desc_z = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if (s.length() < 15) {
                tv_min_desc_z.setVisibility(View.VISIBLE);
                tv_count_desc_z.setText(String.valueOf(s.length() + "/200"));
                tv_count_desc_z.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv_min_desc_z.setVisibility(View.INVISIBLE);
                tv_count_desc_z.setText(String.valueOf(s.length() + "/200"));
                tv_count_desc_z.setTextColor(getResources().getColor(R.color.green2));
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };
    private final TextWatcher textWatcher_title_m = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length

            if (s.length() < 10) {
                tv_min_title_m.setVisibility(View.VISIBLE);
                tv_count_title_m.setText(String.valueOf(s.length() + "/100"));
                tv_count_title_m.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv_min_title_m.setVisibility(View.INVISIBLE);
                tv_count_title_m.setText(String.valueOf(s.length() + "/100"));
                tv_count_title_m.setTextColor(getResources().getColor(R.color.green2));
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    private final TextWatcher textWatcher_desc_m = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if (s.length() < 15) {
                tv_min_desc_m.setVisibility(View.VISIBLE);
                tv_count_desc_m.setText(String.valueOf(s.length() + "/200"));
                tv_count_desc_m.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv_min_desc_m.setVisibility(View.INVISIBLE);
                tv_count_desc_m.setText(String.valueOf(s.length() + "/200"));
                tv_count_desc_m.setTextColor(getResources().getColor(R.color.green2));
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };
    private final TextWatcher textWatcher_lead_title_m = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if (s.length() < 10) {
                tv_min_lead_title_m.setVisibility(View.VISIBLE);
                tv_count_lead_title_m.setText(String.valueOf(s.length() + "/100"));
                tv_count_lead_title_m.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv_min_lead_title_m.setVisibility(View.INVISIBLE);
                tv_count_lead_title_m.setText(String.valueOf(s.length() + "/100"));
                tv_count_lead_title_m.setTextColor(getResources().getColor(R.color.green2));
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    private final TextWatcher textWatcher_lead_desc_m = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            if (s.length() < 15) {
                tv_min_lead_desc_m.setVisibility(View.VISIBLE);
                tv_count_lead_desc_m.setText(String.valueOf(s.length() + "/200"));
                tv_count_lead_desc_m.setTextColor(getResources().getColor(R.color.red));
            } else {
                tv_min_lead_desc_m.setVisibility(View.INVISIBLE);
                tv_count_lead_desc_m.setText(String.valueOf(s.length() + "/200"));
                tv_count_lead_desc_m.setTextColor(getResources().getColor(R.color.green2));
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        appTimeOutManagerUtil.onResumeApp();
    }

    @Override
    protected void onPause() {
        super.onPause();
        appTimeOutManagerUtil.onPauseApp();

      /*  Log.d("redeem_type---", "onPause: " + redeem_type);
        if (redeem_type.equals("P")) {
            final String title = editText_title_P.getText().toString();
            final String desc = editText_desc_P.getText().toString();
            final String points = editText_points_P.getText().toString();
            final String exp_date = tv_end_date_P.getText().toString().trim();
            final String start_date = tv_start_date_P.getText().toString().trim();
            final String exp_time = tv_end_time_P.getText().toString().trim();
            final String start_time = tv_start_time_P.getText().toString().trim();
            final String sharable = String.valueOf(sharable_p1);
            final String getActDays_p = getActDays_P();

            SharedPreferences.Editor editor = sharedpreferences_p_voucher.edit();
            editor.putString(REDEEM_POINT_P, points);
            editor.putString(REDEEM_TITLE_P, title);
            editor.putString(REDEEM_DESCRIPTION_P, desc);
            editor.putString(REDEEM_START_DATE_P, start_date);
            editor.putString(REDEEM_START_TIME_P, start_time);
            editor.putString(REDEEM_EXPIRY_DATE_P, exp_date);
            editor.putString(REDEEM_EXPIRY_TIME_P, exp_time);
            editor.putString(REDEEM_ACTIVE_DAYS_P, getActDays_p);
            editor.putString(REDEEM_IS_SHARABLE_P, sharable);
            editor.putString(REDEEM_BG_COLOR_P, bg_color);
            editor.putString(REDEEM_MER_CB_REDEEM_ID_P, mer_cd_redeem_id);
            editor.commit();
        } else if (redeem_type.equals("B")) {
            final String title = editText_title_B.getText().toString();
            final String desc = editText_desc_B.getText().toString();
            final String points = editText_points_B.getText().toString();
            final String wallet = editText_wallet_B.getText().toString();
            final String exp_date = tv_end_date_B.getText().toString().trim();
            final String start_date = tv_start_date_B.getText().toString().trim();
            final String exp_time = tv_end_time_B.getText().toString().trim();
            final String start_time = tv_start_time_B.getText().toString().trim();
            final String sharable = String.valueOf(sharable_b1);
            final String getActDays_b = getActDays_B();

            SharedPreferences.Editor editor = sharedpreferences_b_voucher.edit();
            editor.putString(REDEEM_TITLE_B, title);
            editor.putString(REDEEM_DESCRIPTION_B, desc);
            editor.putString(REDEEM_POINT_B, points);
            editor.putString(REDEEM_AMOUNT_B, wallet);
            editor.putString(REDEEM_START_DATE_B, start_date);
            editor.putString(REDEEM_START_TIME_B, start_time);
            editor.putString(REDEEM_EXPIRY_DATE_B, exp_date);
            editor.putString(REDEEM_EXPIRY_TIME_B, exp_time);
            editor.putString(REDEEM_ACTIVE_DAYS_B, getActDays_b);
            editor.putString(REDEEM_IS_SHARABLE_B, sharable);
            editor.putString(REDEEM_BG_COLOR_B, bg_color);
            editor.commit();
        } else if (redeem_type.equals("U")) {
            final String title = editText_title_U.getText().toString();
            final String desc = editText_desc_U.getText().toString();
            final String voucher_count = editText_vouchers_count_U.getText().toString();
            final String exp_date = tv_end_date_U.getText().toString().trim();
            final String start_date = tv_start_date_U.getText().toString().trim();
            final String exp_time = tv_end_time_U.getText().toString().trim();
            final String start_time = tv_start_time_U.getText().toString().trim();
            final String getActDays_u = getActDays_U();
            String assignedList = new Gson().toJson(assigned_voucher_list);
            String idList = new Gson().toJson(assigned_id_list);


            SharedPreferences.Editor editor = sharedpreferences_u_voucher.edit();
            editor.putString(REDEEM_TITLE_U, title);
            editor.putString(REDEEM_DESCRIPTION_U, desc);
            editor.putString(REDEEM_VOUCHER_COUNT_U, voucher_count);
            editor.putString(REDEEM_VOUCHER_ID_U, selected_voucher_id);
            editor.putString(REDEEM_START_DATE_U, start_date);
            editor.putString(REDEEM_START_TIME_U, start_time);
            editor.putString(REDEEM_EXPIRY_DATE_U, exp_date);
            editor.putString(REDEEM_EXPIRY_TIME_U, exp_time);
            editor.putString(REDEEM_ACTIVE_DAYS_U, getActDays_u);
            editor.putString(REDEEM_ASSIGNED_VOUCHER_LIST_U, assignedList);
            editor.putString(REDEEM_ASSIGNED_VOUCHER_ID_LIST_U, idList);
            editor.putString(REDEEM_BG_COLOR_U, bg_color);
            editor.putString(REDEEM_MER_CB_REDEEM_ID_U, mer_cd_redeem_id);
            editor.commit();
        } else if (redeem_type.equals("Z")) {
            final String title = editText_title_Z.getText().toString();
            final String desc = editText_desc_Z.getText().toString();
            final String exp_date = tv_end_date_Z.getText().toString().trim();
            final String start_date = tv_start_date_Z.getText().toString().trim();
            final String sharable = String.valueOf(sharable_z1);

            SharedPreferences.Editor editor = sharedpreferences_z_voucher.edit();
            editor.putString(REDEEM_TITLE_Z, title);
            editor.putString(REDEEM_DESCRIPTION_Z, desc);
            editor.putString(REDEEM_START_DATE_Z, start_date);
            editor.putString(REDEEM_EXPIRY_DATE_Z, exp_date);
            editor.putString(REDEEM_IS_SHARABLE_Z, sharable);
            editor.putString(REDEEM_BG_COLOR_Z, bg_color);
            editor.putString(REDEEM_MER_CB_REDEEM_ID_Z, mer_cd_redeem_id);
            editor.commit();
        } else if (redeem_type.equals("M")) {
            final String title = editText_title_M.getText().toString();
            final String desc = editText_desc_M.getText().toString();
            final String lead_title = editText_title_lead_M.getText().toString();
            final String lead_desc = editText_desc_lead_M.getText().toString();
            final String exp_date = tv_end_date_M.getText().toString().trim();
            final String start_date = tv_start_date_M.getText().toString().trim();
            final String points = editText_points_M.getText().toString().trim();
            final String sharable = String.valueOf(sharable_m1);

            SharedPreferences.Editor editor = sharedpreferences_m_voucher.edit();
            editor.putString(REDEEM_TITLE_M, title);
            editor.putString(REDEEM_DESCRIPTION_M, desc);
            editor.putString(REDEEM_LEAD_TITLE_M, lead_title);
            editor.putString(REDEEM_LEAD_DESCRIPTION_M, lead_desc);
            editor.putString(REDEEM_POINT_M, points);
            editor.putString(REDEEM_START_DATE_M, start_date);
            editor.putString(REDEEM_EXPIRY_DATE_M, exp_date);
            editor.putString(REDEEM_IS_SHARABLE_M, sharable);
            editor.putString(REDEEM_BG_COLOR_M, bg_color);
            editor.putString(REDEEM_MER_CB_REDEEM_ID_M, mer_cd_redeem_id);
            editor.commit();
        }*/

    }

    public void setProductVoucher() {

    }

    public void setWalletVoucher() {


    }

    public void setBulkVoucher() {

    }

    public void setAssignedVoucher() {

    }

    public void setMGMVoucher() {

    }


}
