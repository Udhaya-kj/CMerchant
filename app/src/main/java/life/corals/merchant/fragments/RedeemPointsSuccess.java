package life.corals.merchant.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.Homenew;


import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;

public class RedeemPointsSuccess extends BaseFragment {

    private View rootView;
    private SharedPreferences preferences;
    private String cus_name;
    private String cust_id;
    private String redeem_message;
    private String status_message;
    private String referral_reward, cus_wallet_bal, wallet_balance_exp_date, redeem_deposit_amount, points_bal, points_exp_dt;
    LinearLayout layout_ref_award, layout_status_msg, layout_cus_wallet_bal, layout_wallet_balance_exp_date, layout_redeem_deposit_amount, layout_points_bal, layout_points_exp_dt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_redeem_points_success, container, false);

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        cus_name = Objects.requireNonNull(getArguments()).getString("cus_name");
        cust_id = Objects.requireNonNull(getArguments()).getString("cust_id");
        redeem_message = Objects.requireNonNull(getArguments()).getString("redeem_message");
        status_message = Objects.requireNonNull(getArguments()).getString("status_message");
        referral_reward = Objects.requireNonNull(getArguments()).getString("referral_reward");

        cus_wallet_bal = Objects.requireNonNull(getArguments()).getString("cus_wallet_bal");
        wallet_balance_exp_date = Objects.requireNonNull(getArguments()).getString("wallet_balance_exp_date");
        redeem_deposit_amount = Objects.requireNonNull(getArguments()).getString("redeem_deposit_amount");
        points_bal = Objects.requireNonNull(getArguments()).getString("points_bal");
        points_exp_dt = Objects.requireNonNull(getArguments()).getString("points_exp_dt");
        Log.d("v_data---", "onCreateView: " + cus_name + "," + cust_id + "," + redeem_message + "," + status_message + "," + referral_reward + "," + cus_wallet_bal + "," + wallet_balance_exp_date + "," + redeem_deposit_amount + "," + points_bal + "," + points_exp_dt);

        findView();
        return rootView;
    }

    private void findView() {
        TextView tv_cust_name = rootView.findViewById(R.id.points_redeem_deduct);
        TextView tv_cust_id = rootView.findViewById(R.id.points_redeem_customerID);
        TextView tv_cust_refferal_award = rootView.findViewById(R.id.points_redeem_award);
        TextView tv_redeem_message = rootView.findViewById(R.id.tv_redeemMessage);

        TextView tv_cus_wallet_bal = rootView.findViewById(R.id.tv_cus_wallet_bal);
        TextView tv_wallet_balance_exp_date = rootView.findViewById(R.id.tv_wallet_balance_exp_date);
        TextView tv_redeem_deposit_amount = rootView.findViewById(R.id.tv_redeem_deposit_amount);
        TextView tv_points_bal = rootView.findViewById(R.id.tv_points_bal);
        TextView tv_points_exp_dt = rootView.findViewById(R.id.tv_points_exp_dt);
        layout_ref_award = (LinearLayout) rootView.findViewById(R.id.layout_ref_award);
        layout_status_msg = (LinearLayout) rootView.findViewById(R.id.layout_status_msg);
        layout_cus_wallet_bal = (LinearLayout) rootView.findViewById(R.id.layout_cus_wallet_bal);
        layout_wallet_balance_exp_date = (LinearLayout) rootView.findViewById(R.id.layout_wallet_balance_exp_date);
        layout_redeem_deposit_amount = (LinearLayout) rootView.findViewById(R.id.layout_redeem_deposit_amount);
        layout_points_bal = (LinearLayout) rootView.findViewById(R.id.layout_points_bal);
        layout_points_exp_dt = (LinearLayout) rootView.findViewById(R.id.layout_points_exp_dt);

        MaterialButton okayButton = rootView.findViewById(R.id.redeem_success_okay_button);

        if (StringUtils.isNotBlank(cus_name)) {
            tv_cust_name.setText(cus_name);
        } else {
            tv_cust_name.setText("-");
        }

        if (StringUtils.isNotBlank(cust_id)) {
            tv_cust_id.setText(cust_id);
        } else {
            tv_cust_id.setText("-");
        }

        if (StringUtils.isNotBlank(referral_reward)) {
            tv_cust_refferal_award.setText(referral_reward);
        } else {
            layout_ref_award.setVisibility(View.GONE);
        }

        if (StringUtils.isNotBlank(cus_wallet_bal)) {
            tv_cus_wallet_bal.setText(cus_wallet_bal);
        } else {
            layout_cus_wallet_bal.setVisibility(View.GONE);
        }

        if (StringUtils.isNotBlank(wallet_balance_exp_date)) {
            tv_wallet_balance_exp_date.setText(wallet_balance_exp_date);
        } else {
            layout_wallet_balance_exp_date.setVisibility(View.GONE);
        }

        if (StringUtils.isNotBlank(redeem_deposit_amount)) {
            tv_redeem_deposit_amount.setText(redeem_deposit_amount);
        } else {
            layout_redeem_deposit_amount.setVisibility(View.GONE);
        }

        if (StringUtils.isNotBlank(points_bal)) {
            tv_points_bal.setText(points_bal + " points");
        } else {
            layout_points_bal.setVisibility(View.GONE);
        }

        if (StringUtils.isNotBlank(points_exp_dt)) {
            tv_points_exp_dt.setText(points_exp_dt);
        } else {
            layout_points_exp_dt.setVisibility(View.GONE);
        }

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Homenew.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                Objects.requireNonNull(getActivity()).finish();
                getActivity().overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

    }
}
