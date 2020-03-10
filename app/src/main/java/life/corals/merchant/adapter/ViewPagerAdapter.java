package life.corals.merchant.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import life.corals.merchant.fragments.HistoryPrevious;
import life.corals.merchant.fragments.HistorySearchBase;
import life.corals.merchant.fragments.HistoryToday;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    int tabCount;
    public ViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
              HistoryToday tab1 = new HistoryToday();
                return tab1;
            case 1:
                HistoryPrevious tab2 = new HistoryPrevious();
                return tab2;
            case 2:
                HistorySearchBase tab3 = new HistorySearchBase();
                return tab3;
            default:
               return  null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}