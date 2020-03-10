package life.corals.merchant.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.Homenew;
import life.corals.merchant.activity.PerformanceActivity;
import life.corals.merchant.client.ApiCallback;
import life.corals.merchant.client.ApiException;
import life.corals.merchant.client.OkHttpApiClient;
import life.corals.merchant.client.api.MerchantApisApi;
import life.corals.merchant.client.model.Body28;
import life.corals.merchant.client.model.InlineResponseInsigts;
import life.corals.merchant.client.model.InlineResponseInsigtsListofmessages;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.IntermediateAlertDialog;
import life.corals.merchant.utils.MyBarDataSet;
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.INTENT_TEMP_CODE;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;


public class WeekDetails extends BaseFragment {

    private BarChart barChart;


    private SharedPreferences preferences;
    private Body28 body = new Body28();
    private IntermediateAlertDialog intermediateAlertDialog;

    private TextView performanceMsg;
    private TextView tips;
    private TextView messageTv1;
    private TextView messageTv2;
    private int code = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_graph_week_details, container, false);

        code = Objects.requireNonNull(getArguments()).getInt(INTENT_TEMP_CODE);

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());


        messageTv1 = rootView.findViewById(R.id.message1);
        messageTv2 = rootView.findViewById(R.id.message2);

        performanceMsg = rootView.findViewById(R.id.job_msg);
        tips = rootView.findViewById(R.id.tips);

        barChart = (BarChart) rootView.findViewById(R.id.barChart);

        callAPI();
        //  BarDataSet barDataSet = new BarDataSet(getData(), "C Merchant");
       /* ImageButton save = rootView.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barChart.saveToGallery("mychart.jpg", 85);
                barChart.isSaveEnabled();
                barChart.setSaveEnabled(true);
            }
        });*/

      /*  BarDataSet barDataSet1 = new BarDataSet(getData1(), "C Merchant");
        barDataSet1.setBarBorderWidth(0.9f);
        // barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        BarData barData1 = new BarData(barDataSet1);
        XAxis xAxis1 = barChart.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
        final String[] months1 = new String[]{"Sun", "Mon", "Tue", "Wed", "Thr", "Fri","Sat"};
        IndexAxisValueFormatter formatter1 = new IndexAxisValueFormatter(months1);
        xAxis1.setGranularity(1f);
        xAxis1.setValueFormatter(formatter1);
        barChart.setData(barData1);
        barChart.setFitBars(true);
        barChart.animateXY(5000, 5000);
        barChart.invalidate();*/

        return rootView;
    }

    private void callAPI() {
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                getRequestBody();
                getWeekRewardPoints(body);
            } catch (Exception e) {
                intermediateAlertDialog.dismissAlertDialog();
                new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                    @Override
                    public void onButtonClick() {
                        startActivity(new Intent(getActivity(), Homenew.class));
                        Objects.requireNonNull(getActivity()).finish();
                    }
                };
            }
        } else {
            intermediateAlertDialog.dismissAlertDialog();
            new AlertDialogFailure(getActivity(), "Please turn ON your data connection ", "Retry", "No internet Connection !") {
                @Override
                public void onButtonClick() {
                    callAPI();
                }
            };
        }
    }

    private void getRequestBody() {
        String type = "";
        if (code == 1) {
            type = "RP";
        } else if (code == 2) {
            type = "TC";
        } else if (code == 3) {
            type = "PF";
        } else if (code == 4) {
            type = "CV";
        } else {
            new AlertDialogFailure(getActivity(), "Something went wrong", "OK", "Sorry") {
                @Override
                public void onButtonClick() {
                    startActivity(new Intent(getActivity(), PerformanceActivity.class));
                    Objects.requireNonNull(getActivity()).finish();
                }
            };
        }
        body.setDeviceid(preferences.getString(DEVICE_ID, ""));
        body.setMerId(preferences.getString(MERCHANT_ID, ""));
        body.setSessiontoken("notoken");
        body.setType(type);
        Log.d("getWeekRewardPoints", "getRequestBody: " + body);
    }

    private void getWeekRewardPoints(Body28 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.insightsWeekPostAsync(requestBody, new ApiCallback<InlineResponseInsigts>() {

            @Override
            public void onFailure(ApiException e, final int statusCode, Map<String, List<String>> responseHeaders) {
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    intermediateAlertDialog.dismissAlertDialog();
                    if (statusCode == 404) {
                        new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Invalid merchant id (or) Device id") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), PerformanceActivity.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    } else if (statusCode == 406) {
                        new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Invalid request type") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), PerformanceActivity.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    } else {
                        new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), PerformanceActivity.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    }
                });
            }

            @Override
            public void onSuccess(InlineResponseInsigts result, int statusCode, Map<String, List<String>> responseHeaders) {
                intermediateAlertDialog.dismissAlertDialog();
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int point = 0;
                        List<InlineResponseInsigtsListofmessages> oldList = result.getListOld();
                        List<InlineResponseInsigtsListofmessages> newList = result.getListNew();
                        if (oldList != null && newList != null) {
                            //addToEntery(oldList);
                           addToEntery(newList);
                        }else{
                            new AlertDialogFailure(getActivity(), "Please try again later", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), PerformanceActivity.class));
                                    Objects.requireNonNull(getActivity()).finish();
                                }
                            };
                        }

                        if (result.getNewvalue() != null && result.getOldvalue() != null) {

                            String pointsMsg = "reduced";
                            if (new BigDecimal(result.getNewvalue()).compareTo(new BigDecimal(result.getOldvalue())) > 0) {
                                pointsMsg = "increased";
                                ++point;
                            }
                            String message1 = "This week, you have awarded " + result.getNewvalue() + " points " + pointsMsg + " so far compared to " + result.getOldvalue() + " last week.";
                            messageTv1.setText(message1);

                        } else {
                            messageTv1.setVisibility(View.GONE);
                        }
                        if (result.getOldcustomer() != null && result.getNewcustomer() != null) {
                            String customerMsg = "reduced";
                            if (new BigDecimal(result.getNewcustomer()).equals(new BigDecimal(result.getOldcustomer()))) {
                                customerMsg = "no change";

                            }
                            else if (new BigDecimal(result.getNewcustomer()).compareTo(new BigDecimal(result.getOldcustomer())) > 0) {
                                customerMsg = "increased";
                                ++point;
                            }

                            String message2 = "Number of customers has " + customerMsg + " by " + result.getNewcustomer() + " compare to " + result.getOldcustomer() + " last week .";
                            messageTv2.setText(message2);
                        }else {
                            messageTv2.setVisibility(View.GONE);
                        }

                        String perform;
                        String tip;
                        if (point == 2) {
                            perform = getString(R.string.good_status)+ " week"+getString(R.string.good_emji);
                            tip =  getString(R.string.good_tip);
                        } else if (point == 1) {
                            perform = getString(R.string.equal_status)+ " week"+getString(R.string.equal_emji);
                            tip =  getString(R.string.equal_tip);
                        } else {
                            perform = getString(R.string.bad_status)+ " week"+getString(R.string.bad_emoji);
                            tip =  getString(R.string.bad_tip);
                        }

                        performanceMsg.setVisibility(View.VISIBLE);
                        performanceMsg.setText(perform);

                        tips.setVisibility(View.VISIBLE);
                        tips.setText(tip);
                    }

                });

            }

            @Override
            public void onUploadProgress(long bytesWritten, long contentLength, boolean done) {

            }

            @Override
            public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

            }
        });
    }

    private void addToEntery(List<InlineResponseInsigtsListofmessages> List) {
    BarDataSet barDataSet1 = new BarDataSet(getData(List), "Corals Merchant");
        barDataSet1.setBarBorderWidth(3f);
        // barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
       /* BarData barData1 = new BarData(barDataSet1);
        XAxis xAxis1 = barChart.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
        final String[] months1 = new String[]{"Sun", "Mon", "Tue", "Wed", "Thr", "Fri","Sat"};
        IndexAxisValueFormatter formatter1 = new IndexAxisValueFormatter(months1);
        xAxis1.setGranularity(1f);
        xAxis1.setValueFormatter(formatter1);
        barChart.setData(barData1);
        barChart.setFitBars(true);
        barChart.animateXY(5000, 5000);
        barChart.invalidate();*/

       // barDataSet.setBarBorderWidth(1f);
        //barDataSet.setBarBorderColor(getResources().getColor(R.color.btnGreen));
        MyBarDataSet barDataSet = new MyBarDataSet(getData(List), "Corals");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        final String[] months = new String[]{"Sun", "Mon", "Tue", "Wed", "Thr", "Fri", "Sat"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        //xAxis.setAxisLineColor(getResources().getColor(R.color.white));
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.animateXY(3000, 3000);
        barChart.invalidate();


        //barChart.setDescription(false);
        // barChart.setDescription("# of times Alice called Bob");

       /* LimitLine line = new LimitLine(9f);
        line.setLineColor(R.color.colorPrimaryDark);
        line.setLineWidth(3);
        xAxis.addLimitLine(line);*/


      //  LimitLine ll1 = new LimitLine(200f, "Target");
       // ll1.setLineWidth(1f);
       // ll1.enableDashedLine(0f, 0f, 0f);
       // ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
       // ll1.setTextSize(10f);
        //ll1.setTypeface(tf);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
       //leftAxis.addLimitLine(ll1);

    }

    private ArrayList<BarEntry> getData(List<InlineResponseInsigtsListofmessages> list) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, Float.parseFloat(list.get(0).getValue())));
        entries.add(new BarEntry(1f, Float.parseFloat(list.get(1).getValue())));
        entries.add(new BarEntry(2f,  Float.parseFloat(list.get(2).getValue())));
        entries.add(new BarEntry(3f,  Float.parseFloat(list.get(3).getValue())));
        entries.add(new BarEntry(4f,  Float.parseFloat(list.get(4).getValue())));
        entries.add(new BarEntry(5f,  Float.parseFloat(list.get(5).getValue())));
        entries.add(new BarEntry(6f,  Float.parseFloat(list.get(6).getValue())));

        return entries;
    }

   /* @Override
    public boolean onBackPressed() {
        Performance fragment1 = new Performance();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        fragmentTransaction.replace(R.id.performance_frame_layout, fragment1);
        fragmentTransaction.commit();

        return true;
    }*/
}
