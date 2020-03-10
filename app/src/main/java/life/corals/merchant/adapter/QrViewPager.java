package life.corals.merchant.adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import life.corals.merchant.fragments.QRShowApplink;
import life.corals.merchant.fragments.QRShowRegister;

public class QrViewPager  extends FragmentPagerAdapter {

    int totalTabs;


    public QrViewPager(FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle1 = new Bundle();
                QRShowRegister fragment1 = new QRShowRegister();
                fragment1.setArguments(bundle1);
                return fragment1;
            case 1:
                Bundle bundle2 = new Bundle();
                QRShowApplink fragment2 = new QRShowApplink();
                fragment2.setArguments(bundle2);
                return fragment2;
            default:
                Bundle bundle3 = new Bundle();
                QRShowRegister fragment3 = new QRShowRegister();
                fragment3.setArguments(bundle3);
                return fragment3;
        }
    }

    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }

}