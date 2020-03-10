package life.corals.merchant.adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import life.corals.merchant.fragments.MonthDetails;
import life.corals.merchant.fragments.WeekDetails;

import static life.corals.merchant.utils.Constants.INTENT_TEMP_CODE;

public class ViewPagerGraph extends FragmentPagerAdapter {

    int totalTabs;
    int code;

    public ViewPagerGraph(FragmentManager fm, int totalTabs, int code) {
        super(fm);
        this.totalTabs = totalTabs;
        this.code = code;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.d("fragment1", "getItem: "+code);
                Bundle bundle1 = new Bundle();
                bundle1.putInt(INTENT_TEMP_CODE, code);
                WeekDetails fragment1 = new WeekDetails();
                fragment1.setArguments(bundle1);
                return fragment1;
            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putInt(INTENT_TEMP_CODE, code);
                MonthDetails fragment2 = new MonthDetails();
                fragment2.setArguments(bundle2);
                return fragment2;
            default:
                return null;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }

}