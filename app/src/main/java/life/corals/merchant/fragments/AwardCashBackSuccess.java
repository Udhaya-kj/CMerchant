package life.corals.merchant.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.Homenew;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DISPLAY_BUSINESS_NAME;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MOBILE_NUMBER;

public class AwardCashBackSuccess extends BaseFragment {
    private View rootView;
    private String mble;
    private String ammnt;
    private String isNewcustomer;
    private String customerId;
    private String customerNotAppUse;
    private String pointsExpiry;
    private String totalPoints;
    private String points;
    private SharedPreferences preferences;
    private TextView pointstv;
    private TextView totalPointstv;
    private TextView pointsExpirytv;
    private TextView mobilenumbertv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_award_cashback_success, container, false);
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(rootView.getWindowToken(), 0);
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        mble = Objects.requireNonNull(getArguments()).getString(MOBILE_NUMBER);
        totalPoints = Objects.requireNonNull(getArguments()).getString("TOTAL_POINTS");
        points = Objects.requireNonNull(getArguments()).getString("POINTS");
        pointsExpiry = Objects.requireNonNull(getArguments()).getString("POINTS_EXPIRY");
        customerNotAppUse = Objects.requireNonNull(getArguments()).getString("CUSTOMER_APP_USE");
        customerId = Objects.requireNonNull(getArguments()).getString("CUSTOMER_ID");
        isNewcustomer = Objects.requireNonNull(getArguments()).getString("IS_NEW_CUSTOMER");
        pointstv = rootView.findViewById(R.id.points_issued);
        totalPointstv = rootView.findViewById(R.id.total_points);
        pointsExpirytv = rootView.findViewById(R.id.expiry_date);
        mobilenumbertv = rootView.findViewById(R.id.Mobile_number_cashback_two);
        MaterialButton okBtn = rootView.findViewById(R.id.cashback_two_okay_button);
        TextView sendSmsBtn = rootView.findViewById(R.id.send_sms_button);

        if (isNewcustomer.equals("true")) {
            sendSmsBtn.setVisibility(View.VISIBLE);
            sendSmsBtn.setText(R.string.notify_to_new_user);
        } else if (customerNotAppUse.equals("true")) {
            sendSmsBtn.setVisibility(View.VISIBLE);
            sendSmsBtn.setText(R.string.notify_to_existing_user);
        }else{
            sendSmsBtn.setVisibility(View.GONE);
        }
        pointstv.setText(points);
        totalPointstv.setText(totalPoints);
        pointsExpirytv.setText(pointsExpiry);
        mobilenumbertv.setText(mble);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Homenew.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                Objects.requireNonNull(getActivity()).finish();
                getActivity().overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });
        sendSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sms = preferences.getString(DISPLAY_BUSINESS_NAME,"")+"\nDear Member, You have "+ totalPoints+" points.\n(Exp.: " + pointsExpiry + "). \nTo claim voucher download our app. Visit : https://www.corals.life/get/";//The message you want to text to the phone
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", mble, null));
                smsIntent.putExtra("sms_body", sms);
                startActivity(smsIntent);
            }
        });

        return rootView;
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getActivity(), Homenew.class));
        Objects.requireNonNull(getActivity()).finish();
        getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        return true;
    }
}
