package life.corals.merchant.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import life.corals.merchant.R;

import static life.corals.merchant.utils.Constants.INTENT_TEMP_CODE;
import static life.corals.merchant.utils.Constants.REGISTRATION_CODE;


public class IntroFragmentOne extends BaseFragment {

    private View rootView;
    private TextInputEditText userIdeEdt;
    private String activationCode;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.intro_fragment_one, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        findView();


        return rootView;
    }

    private void findView() {
        MaterialButton privacyPolicy = rootView.findViewById(R.id.privacy_policy_introfrag_one);
        MaterialButton termsConditions = rootView.findViewById(R.id.terms_conditions_introfrag_one);
        userIdeEdt = rootView.findViewById(R.id.user_id);
        MaterialButton nextButton = rootView.findViewById(R.id.next_btn);

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(INTENT_TEMP_CODE, 1);
                PrivacyPolicy fragment1 = new PrivacyPolicy();
                fragment1.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
                fragmentTransaction.commit();
            }
        });
        termsConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(INTENT_TEMP_CODE, 1);
                TermsAndConditions fragment1 = new TermsAndConditions();
                fragment1.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
                fragmentTransaction.commit();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void validation() {
        activationCode = Objects.requireNonNull(userIdeEdt.getText()).toString().trim();

        if (activationCode.length() != 10) {
            userIdeEdt.setError("Enter valid activation code");
            userIdeEdt.requestFocus();
        } else {
            char[] chars = activationCode.toCharArray();
            String textNum = activationCode;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < textNum.length() - 1; i++) {
                sb.append(textNum.charAt(i));
            }
            String nineDigitTotal = getChkDigit(sb.toString());
            if (nineDigitTotal.equals(String.valueOf(chars[9]))) {
                IntentFragment();
            } else {
                userIdeEdt.setError("Invalid activation code");
            }
        }
    }

    private String getChkDigit(String _s1) {

        long _vsum = 0;

        try {

            long inputVal = Long.parseLong(_s1);

            while (inputVal > 0) {
                _vsum = _vsum + inputVal % 10;
                inputVal = inputVal / 10;
                if (inputVal == 0 && _vsum >= 10) {
                    inputVal = _vsum;
                    _vsum = 0;
                }
            }

        } catch (Exception e) {
            throw new IllegalArgumentException();
        }


        return Long.toString(_vsum);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void IntentFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(REGISTRATION_CODE, activationCode);
        IntroFragmentThree fragment1 = new IntroFragmentThree();
        fragment1.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
        fragmentTransaction.commit();
    }


}

