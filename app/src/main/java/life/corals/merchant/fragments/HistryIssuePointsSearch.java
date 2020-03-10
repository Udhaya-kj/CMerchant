package life.corals.merchant.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.HistoryHomePage;
import life.corals.merchant.client.model.Body24;

import static life.corals.merchant.utils.Constants.MOBILE_NUMBER;

public class HistryIssuePointsSearch extends BaseFragment {


    Body24 body = new Body24();
    private View rootView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_history_issue_points_history_search, container, false);

        TextInputEditText mbleEdt = rootView.findViewById(R.id.search_by_mobilenumber);
        MaterialButton searchBtn = rootView.findViewById(R.id.search_btn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mble = Objects.requireNonNull(mbleEdt.getText()).toString();
                if (mble.isEmpty() || mble.length() < 7) {
                    mbleEdt.setError("Enter valid mobile number");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(MOBILE_NUMBER, mble);
                    HistryIssuePointsSearchResult fragment1 = new HistryIssuePointsSearchResult();
                    fragment1.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                    fragmentTransaction.replace(R.id.issue_points_base_frame_layout, fragment1);
                    fragmentTransaction.commit();
                    Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
                }
            }
        });
        return rootView;
    }

    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getActivity(), HistoryHomePage.class));
        Objects.requireNonNull(getActivity()).finish();
        getActivity().overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
        return true;
    }
}
