package life.corals.merchant.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.HistoryHomePage;

import static life.corals.merchant.utils.Constants.MOBILE_NUMBER;


public class HistorySearch extends BaseFragment {
    private View rootView;
    private TextInputEditText mobilenumberEdt;
    private MaterialButton searchBtn;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.history_search, container, false);
        findView();

        return rootView;
    }

    private void findView() {
        mobilenumberEdt = rootView.findViewById(R.id.mobilenumber);
        searchBtn= rootView.findViewById(R.id.search_btn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Assert")
            @Override
            public void onClick(View v) {
                String Mobile = Objects.requireNonNull(mobilenumberEdt.getText()).toString();
                if (Mobile.length()>7) {
                        Bundle bundle=new Bundle();
                        bundle.putString(MOBILE_NUMBER, Mobile);
                        InputMethodManager inputManager = (InputMethodManager) Objects.requireNonNull(getActivity())
                                .getSystemService(Context.INPUT_METHOD_SERVICE);

                        rootView= ((Activity) getActivity()).getCurrentFocus();
                        if (v == null) {
                            assert false;
                            Objects.requireNonNull(inputManager).hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                        HistorySearchResult searchresult = new HistorySearchResult();
                        searchresult.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                        fragmentTransaction.replace(R.id.history_base_frame_layout, searchresult);
                        fragmentTransaction.commit();
                }else {
                    mobilenumberEdt.setError("Enter valid mobile number");
                }
            }

        });
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getActivity(), HistoryHomePage.class) .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        Objects.requireNonNull(getActivity()).finish();
        return true;
    }

}
