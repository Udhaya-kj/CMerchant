package life.corals.merchant.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.utils.AlertSendNotification;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.CURRENCY_SYMBOL;
import static life.corals.merchant.utils.Constants.CUSTOMER_ID;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MOBILE_NUMBER;

public class CustomerViewDetails extends BaseFragment {

    private View rootView;
    private Toolbar toolbar;
    private MaterialButton viewTransactions;
    private MaterialButton SendNotification;
    private SharedPreferences preferences;
    private String customerId;
    private String customerName;
    private String customerCuntryCode;
    private String customerWalletBal;
    private String customerWalletExpDate;
    private String CbpointsBal;
    private String CbpointsBalExpDate;
    private String mobileNumber;
    private String lastvisitDate;
    private String lastTopup;
    private String notifyId;
    private String joinDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_customer_view_details, container, false);
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
                        onBackPressed();
                    }
                });
            }
        }
        customerId = Objects.requireNonNull(getArguments()).getString("Cust_id");
        customerName = Objects.requireNonNull(getArguments()).getString("Cust_name");
        customerCuntryCode = Objects.requireNonNull(getArguments()).getString("Cust_country");
        customerWalletBal = Objects.requireNonNull(getArguments()).getString("Cust_wall_bal");
        customerWalletExpDate = Objects.requireNonNull(getArguments()).getString("Cust_wall_bal_exp");
        CbpointsBal = Objects.requireNonNull(getArguments()).getString("Cust_wall_cb_bal");
        CbpointsBalExpDate = Objects.requireNonNull(getArguments()).getString("Cust_wall_cb_bal_exp");
        mobileNumber = Objects.requireNonNull(getArguments()).getString("Cust_mobile");
        lastvisitDate = Objects.requireNonNull(getArguments()).getString("Cust_last_visit");
        lastTopup = Objects.requireNonNull(getArguments()).getString("Cust_last_topup");
        notifyId = Objects.requireNonNull(getArguments()).getString("notify_id");
        joinDate = Objects.requireNonNull(getArguments()).getString("join_date");
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        viewTransactions = rootView.findViewById(R.id.button_view);
        SendNotification = rootView.findViewById(R.id.send_notification);

        LinearLayout namelayout = rootView.findViewById(R.id.name_layout);
        LinearLayout lastVisitlayout = rootView.findViewById(R.id.last_visit_layout);
        LinearLayout cbExplayout = rootView.findViewById(R.id.points_exp_layout);
        LinearLayout JoinLayout = rootView.findViewById(R.id.join_date_layout);

        TextView nametv = rootView.findViewById(R.id.cust_name);
        TextView joinDt = rootView.findViewById(R.id.cust_join_date);
        TextView idtv = rootView.findViewById(R.id.cust_id);
        TextView wallettv = rootView.findViewById(R.id.cust_wall_bal);
        TextView walletExptv = rootView.findViewById(R.id.cust_wall_exp_dte);
        TextView cbpointstv = rootView.findViewById(R.id.cust_cb_bal);
        TextView cbpointsExptv = rootView.findViewById(R.id.cust_cb_bal_exp);
        TextView mobiletv = rootView.findViewById(R.id.cust_mobile);
        TextView lastvisittv = rootView.findViewById(R.id.cust_last_vist);
        TextView lasttopuptv = rootView.findViewById(R.id.cust_last_topup);
        TextView currency = rootView.findViewById(R.id.cust_curr);
        TextView currencytopup = rootView.findViewById(R.id.cust_curr_topup);

        currency.setText(preferences.getString(CURRENCY_SYMBOL, ""));
        currencytopup.setText(preferences.getString(CURRENCY_SYMBOL, ""));
        if (customerName == null || customerName.isEmpty()) {
            namelayout.setVisibility(View.GONE);
        }
        nametv.setText(customerName);
        idtv.setText(customerId);
        wallettv.setText(customerWalletBal);
        walletExptv.setText(customerWalletExpDate);
        cbpointstv.setText(CbpointsBal + " points");
        cbpointsExptv.setText(CbpointsBalExpDate);
        joinDt.setText(joinDate);

        if (joinDate.equals("-")) {
            JoinLayout.setVisibility(View.GONE);
        }
        if (CbpointsBalExpDate.equals("-")) {
            cbExplayout.setVisibility(View.GONE);
        }
        lastvisittv.setText(lastvisitDate);
        if (lastvisitDate.equals("-")) {
            lastVisitlayout.setVisibility(View.GONE);
        }
        mobiletv.setText(mobileNumber);
        lasttopuptv.setText(lastTopup);

       /* if (CbpointsBal.equals("0")){
            cbExplayout.setVisibility(View.GONE);
        }

        if (lastvisitDate.equals("")){
            lastVisitlayout.setVisibility(View.GONE);
        }*/
        if (notifyId==null) {
            SendNotification.setVisibility(View.GONE);
        }else {
            SendNotification.setVisibility(View.VISIBLE);
        }
        SendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertSendNotification(getActivity(), "") {
                    @Override
                    public void onOkButtonClick(String title, String msg) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title", title);
                        bundle.putString("msg", msg);
                        bundle.putString("token", notifyId);
                        bundle.putString("cust_name", customerName);
                        bundle.putString("cust_id", customerId);
                        bundle.putString("mobile", mobileNumber);
                        NotificationOnetoOnePreview fragment1 = new NotificationOnetoOnePreview();
                        fragment1.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                        fragmentTransaction.replace(R.id.customer_frame_layout, fragment1);
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void onCancelButtonClick() {

                    }
                };
            }
        });

        viewTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(CUSTOMER_ID, customerId);
                bundle.putString(MOBILE_NUMBER, mobileNumber);
                CustomerListTwo fragment1 = new CustomerListTwo();
                fragment1.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.replace(R.id.customer_frame_layout, fragment1);
                fragmentTransaction.commit();
            }
        });
        return rootView;
    }


    @Override
    public boolean onBackPressed() {
        CustomerListOne fragment1 = new CustomerListOne();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.customer_frame_layout, fragment1);
        fragmentTransaction.commit();
        return true;
    }
}
