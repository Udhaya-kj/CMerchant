package life.corals.merchant.utils;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

public  class MyBarDataSet extends BarDataSet {


    public MyBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    public int getColor(int index) {
        Log.d("get___Color", "getColor: "+getEntryForIndex(index).getX() );

        if(getEntryForIndex(index).getY() < 100)
            //red
            return  Color.parseColor("#E93D3D");
        else if(getEntryForIndex(index).getY() > 500)
            return  Color.parseColor("#39D639");
        else
            return  Color.parseColor("#1BB0C4");
    }

}