package life.corals.merchant.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.HistoryHomePage;

public class HistryIssuePointsSearchBase extends BaseFragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_history_issue_points_history_base, container, false);

        HistryIssuePointsSearch fragment1 = new HistryIssuePointsSearch();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.issue_points_base_frame_layout, fragment1);
        fragmentTransaction.commit();
        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

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
