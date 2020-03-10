package life.corals.merchant.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.utils.AlertDialogFailure;

import static life.corals.merchant.utils.Constants.INTENT_TEMP_CODE;


public class PrivacyPolicy extends BaseFragment {

    private View rootView;
    private TextView privacyPolicy;
    private Toolbar toolbar;
    private int tempCode = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_privacy_policy, container, false);

        toolbar = rootView.findViewById(R.id.toolbar_privacy_policy);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            if (getActivity() != null && activity.getSupportActionBar() != null && getActivity().getResources() != null) {
                activity.getSupportActionBar().setHomeButtonEnabled(true);
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activity.getSupportActionBar().setHomeAsUpIndicator(getActivity().getResources().getDrawable(R.drawable.ic_left_arrow_white));
                toolbar.setTitle("Privacy Policy");

                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        }

        if (getArguments() != null) {
            tempCode = Objects.requireNonNull(getArguments()).getInt(INTENT_TEMP_CODE, 0);
            if (tempCode == 1 || tempCode==2) {
                toolbar.setVisibility(View.GONE);
            }
        }
        privacyPolicy = rootView.findViewById(R.id.privacy_policy_txtvw);
        privacyPolicy.setText(Html.fromHtml(getResources().getString(R.string.privacypolicylongvalue)));
        return rootView;
    }

    @Override
    public boolean onBackPressed() {
        if (tempCode == 1) {
            IntroFragmentOne fragment1 = new IntroFragmentOne();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
            fragmentTransaction.commit();
        } else if (tempCode == 2) {
            IntroFragmentFour fragment1 = new IntroFragmentFour();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_intro_activity, fragment1);
            fragmentTransaction.commit();
        } else {
            new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                @Override
                public void onButtonClick() {
                    Objects.requireNonNull(getActivity()).finish();
                }
            };
        }

        return true;
    }

    public Toolbar getSupportActionBar() {
        return toolbar;
    }

}
