package life.corals.merchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import life.corals.merchant.R;

public class AssignedSpinnerAdapter extends BaseAdapter {
    Context context;
   ArrayList<String> assigned_list;
    LayoutInflater inflter;

    public AssignedSpinnerAdapter(Context applicationContext, ArrayList<String> assigned_list) {
        this.context = applicationContext;
        this.assigned_list = assigned_list;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return assigned_list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.text_assigned);
        names.setText(assigned_list.get(i));
        return view;
    }
}
