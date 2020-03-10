package life.corals.merchant.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.SplashScreen;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.INTENT_TEMP_CODE;
import static life.corals.merchant.utils.Constants.IS_ADMIN;
import static life.corals.merchant.utils.Constants.MERCHANT_DEVICE_PREFERENCE;


public class IntroFragmentFour extends Fragment {

    private View rootView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.intro_fragment_four, container, false);

        SharedPreferences preferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences(MERCHANT_DEVICE_PREFERENCE, MODE_PRIVATE);
        String  isAdmin = preferences.getString(IS_ADMIN,"");

        findView(isAdmin);
        return rootView;
    }

    private void findView(String admin) {
        MaterialButton okButton = rootView.findViewById(R.id.ok_btn);
        MaterialButton terms = rootView.findViewById(R.id.terms_conditions_introfrag_one);
        MaterialButton privacy = rootView.findViewById(R.id.privacy_policy_introfrag_one);
        TextView text1 = rootView.findViewById(R.id.admin_text_one);
        TextView text2 = rootView.findViewById(R.id.admin_text_two);
        //TextView text3 = rootView.findViewById(R.id.admin_text_three);

        if (admin.equals("true")){
            text1.setText(R.string.admin_text_one);
            text2.setText(R.string.admin_text_two);
           // text3.setText(R.string.admin_text_three);
            text1.setVisibility(View.VISIBLE);
            text2.setVisibility(View.VISIBLE);
           // text3.setVisibility(View.VISIBLE);
        }

        okButton.setOnClickListener(v -> {
           startActivity(new Intent(getActivity(), SplashScreen.class));
           Objects.requireNonNull(getActivity()).finish();
        });

        terms.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(INTENT_TEMP_CODE, 2);
            TermsAndConditions fragment1 = new TermsAndConditions();
            fragment1.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
            fragmentTransaction.commit();
        });

        privacy.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(INTENT_TEMP_CODE, 2);
            PrivacyPolicy fragment1 = new PrivacyPolicy();
            fragment1.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
            fragmentTransaction.commit();
        });

    }


}
