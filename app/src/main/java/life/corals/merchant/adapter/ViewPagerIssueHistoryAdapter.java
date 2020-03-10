package life.corals.merchant.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import life.corals.merchant.fragments.HistryIssuePointsPrevious;
import life.corals.merchant.fragments.HistryIssuePointsSearchBase;
import life.corals.merchant.fragments.HistryIssuePointsToday;

public class ViewPagerIssueHistoryAdapter  extends FragmentStatePagerAdapter {


    int tabCount;
    public ViewPagerIssueHistoryAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HistryIssuePointsToday();
            case 1:
                return new HistryIssuePointsPrevious();
            case 2:
                return new HistryIssuePointsSearchBase();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
