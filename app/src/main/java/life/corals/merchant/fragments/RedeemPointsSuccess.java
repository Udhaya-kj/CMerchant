package life.corals.merchant.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;

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
    private String referral_reward;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_redeem_points_success, container, false);

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        cus_name = Objects.requireNonNull(getArguments()).getString("cus_name");
        cust_id = Objects.requireNonNull(getArguments()).getString("cust_id");
        redeem_message = Objects.requireNonNull(getArguments()).getString("redeem_message");
        status_message = Objects.requireNonNull(getArguments()).getString("status_message");
        referral_reward = Objects.requireNonNull(getArguments()).getString("referral_reward");
        findView();
        return rootView;
    }

    private void findView() {
        TextView tv_cust_name = rootView.findViewById(R.id.points_redeem_deduct);
        TextView tv_cust_id = rootView.findViewById(R.id.points_redeem_customerID);
        TextView tv_cust_refferal_award = rootView.findViewById(R.id.points_redeem_award);
        TextView tv_redeem_message = rootView.findViewById(R.id.tv_redeemMessage);

        MaterialButton okayButton = rootView.findViewById(R.id.redeem_success_okay_button);

        tv_cust_name.setText(cus_name);
        tv_cust_id.setText(cust_id);
        tv_cust_refferal_award.setText(referral_reward);
        tv_redeem_message.setText(redeem_message);

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
