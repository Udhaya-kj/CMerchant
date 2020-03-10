package life.corals.merchant.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.HistoryHomePage;

public class HistorySearchBase extends BaseFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.history_search_base, container, false);

        Fragment HistorySearchFragment = new HistorySearch();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.history_base_frame_layout, HistorySearchFragment);
        fragmentTransaction.commit();
        return rootView;
    }
    @Override
    public boolean onBackPressed() {
        startActivity(new Intent(getActivity(), HistoryHomePage.class) .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        Objects.requireNonNull(getActivity()).finish();
        return true;
    }
}