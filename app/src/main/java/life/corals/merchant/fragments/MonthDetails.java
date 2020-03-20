package life.corals.merchant.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.math.BigDecimal;
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
import life.corals.merchant.utils.Util;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.DEVICE_ID;
import static life.corals.merchant.utils.Constants.INTENT_TEMP_CODE;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_ID;

public class MonthDetails extends BaseFragment {

    private int code = 0;
    private Body28 body = new Body28();
    private IntermediateAlertDialog intermediateAlertDialog;
    private SharedPreferences preferences;
    private TextView performanceMsg;
    private TextView tips;
    private TextView messageTv1;
    private TextView messageTv2;
    private String Message = "";
    private GraphView graph;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_graph_month_details, container, false);

        code = Objects.requireNonNull(getArguments()).getInt(INTENT_TEMP_CODE);

        if (code == 1) {
            Message = "Awarded";
        } else if (code == 2) {
            Message = "Topups";
        } else if (code == 3) {
            Message = "Received payments";
        } else if (code == 4) {
            Message = "Customer visits";
        } else {
            new AlertDialogFailure(getActivity(), "Something went wrong", "OK", "Sorry") {
                @Override
                public void onButtonClick() {
                    startActivity(new Intent(getActivity(), PerformanceActivity.class));
                    Objects.requireNonNull(getActivity()).finish();
                }
            };
        }

        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        messageTv1 = rootView.findViewById(R.id.message1);
        messageTv2 = rootView.findViewById(R.id.message2);

        performanceMsg = rootView.findViewById(R.id.job_msg);
        tips = rootView.findViewById(R.id.tips);
        //QualityMsg.setText(R.string.emoji_bad);

        graph = rootView.findViewById(R.id.graph);
/*    new LineGraphSeries<>(points);
        series.setColor(Color.GREEN);
        //graph.addSeries(series);


        //LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(points);

        // set manual X bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(700);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(36);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        //graph.addSeries(series1);


        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling*/

/*  new LineGraphSeries<>(points2);
        series2.setColor(Color.YELLOW);
        // graph.addSeries(series2);

        // Log.d("LineGraphSeries", "onCreateView: "+points.toString());
        //LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(points);

        //graph.setBackgroundColor(Color.argb(50, 50, 0, 200));
        // graph.setDrawBackground(true);
        //graph.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));

        // set manual X bounds

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(700);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(36);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        //graph.addSeries(series1);


        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling*/

        intermediateAlertDialog = new IntermediateAlertDialog(getActivity());
        callAPI();
        return rootView;
    }

    private void callAPI() {
        if (Util.getConnectivityStatusString(Objects.requireNonNull(getActivity()))) {
            try {
                getRequestBody();
                getWeekRewardPoints(body);
            } catch (Exception e) {
                Log.d("getWeekRewardPoints", "getRe---exception: " + e);
                intermediateAlertDialog.dismissAlertDialog();
                new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Something went wrong") {
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
        Log.d("getWeekRewardPoints", "getRequestBodymonth: " + body);
    }

    private void getWeekRewardPoints(Body28 requestBody) throws ApiException {
        OkHttpApiClient okHttpApiClient = new OkHttpApiClient(Objects.requireNonNull(getActivity()));
        MerchantApisApi merchantApisApi = new MerchantApisApi();
        merchantApisApi.setApiClient(okHttpApiClient.getApiClient());

        merchantApisApi.insightsMonthPostAsync(requestBody, new ApiCallback<InlineResponseInsigts>() {


            @Override
            public void onFailure(ApiException e, final int statusCode, Map<String, List<String>> responseHeaders) {
                Log.d("addToEntry_List","addToEntry: "+e+"   "+statusCode);
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    intermediateAlertDialog.dismissAlertDialog();
                    if (statusCode == 404) {
                        new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Invalid merchant id (or) Device id") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), PerformanceActivity.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    } else if (statusCode == 406) {
                        new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Invalid request type") {
                            @Override
                            public void onButtonClick() {
                                startActivity(new Intent(getActivity(), PerformanceActivity.class));
                                Objects.requireNonNull(getActivity()).finish();
                            }
                        };
                    } else {
                        new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Something went wrong") {
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
                Log.d("addToEntry_List", "addToEntry: " + result);

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        intermediateAlertDialog.dismissAlertDialog();
                        int point = 0;
                        List<InlineResponseInsigtsListofmessages> oldList = result.getListOld();
                        List<InlineResponseInsigtsListofmessages> newList = result.getListNew();
                        if (newList != null && oldList != null) {
                            addToEntry(result.getListNew(), getResources().getColor(R.color.bluehome));
                            addToEntry(result.getListOld(), getResources().getColor(R.color.btnGreen));
                        } else {
                            new AlertDialogFailure(getActivity(), "Please try again later!", "OK", "Something went wrong") {
                                @Override
                                public void onButtonClick() {
                                    startActivity(new Intent(getActivity(), PerformanceActivity.class));
                                    Objects.requireNonNull(getActivity()).finish();
                                }
                            };
                        }

                        if (result.getNewvalue() != null && result.getOldvalue() != null) {

                            String pointsMsg = "reduced";
                            if (new BigDecimal(result.getNewvalue()).equals(new BigDecimal(result.getOldvalue()))) {
                                //  pointsMsg = "no change";
                                ++point;
                            } else if (new BigDecimal(result.getNewvalue()).compareTo(new BigDecimal(result.getOldvalue())) > 0) {
                                pointsMsg = "increased";
                            }
                            String message1 = "This month you have " + Message + " " + result.getNewvalue() + " points " + pointsMsg + " so far compared to " + result.getOldvalue() + " last month.";
                            messageTv1.setText(message1);
                        } else {
                            messageTv1.setVisibility(View.GONE);
                        }
                        String customerMsg = "reduced";
                        if (new BigDecimal(result.getNewcustomer()).equals(new BigDecimal(result.getOldcustomer()))) {
                            customerMsg = "no change";
                            //  ++point;
                        } else if (new BigDecimal(result.getNewcustomer()).compareTo(new BigDecimal(result.getOldcustomer())) > 0) {
                            customerMsg = "increased";
                            ++point;
                        }
                        String message2 = "Number of customers has " + customerMsg + " by " + result.getNewcustomer() + " compare to " + result.getOldcustomer() + " last month.";
                        messageTv2.setText(message2);
                        String perform;
                        String tip;
                        if (point == 2) {
                            perform = getString(R.string.good_status)+ " month"+getString(R.string.good_emji);
                            tip =  getString(R.string.good_tip);
                        } else if (point == 1) {
                            perform = getString(R.string.equal_status)+ " month"+getString(R.string.equal_emji);
                            tip =  getString(R.string.equal_tip);
                        } else {
                            perform = getString(R.string.bad_status)+ " month"+getString(R.string.bad_emoji);
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

    private void addToEntry(List<InlineResponseInsigtsListofmessages> newList, int color) {


        DataPoint[] dataPoint = new DataPoint[newList.size()];
        int i = 0;
        for (InlineResponseInsigtsListofmessages listofmessages:newList){
            dataPoint[i] = new DataPoint(i, Float.parseFloat(listofmessages.getValue()));
            i++;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoint);

        new LineGraphSeries<>(dataPoint);
        //  series.set
        series.setColor(color);
        graph.addSeries(series);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(700);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(36);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling

    }
}
