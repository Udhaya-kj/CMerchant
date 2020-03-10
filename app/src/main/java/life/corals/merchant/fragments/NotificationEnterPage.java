package life.corals.merchant.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.client.model.Body25;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.ABSEND_DAYS;
import static life.corals.merchant.utils.Constants.DATE_TIME;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MESSAGE;
import static life.corals.merchant.utils.Constants.TEMP_CODE;
import static life.corals.merchant.utils.Constants.TITLE;
import static life.corals.merchant.utils.Constants.TOTAL_CUSTOMERS;

public class NotificationEnterPage extends BaseFragment {

    private TextInputEditText title;
    private TextInputEditText msg;
    Body25 body = new Body25();
    private String daysofAbsence = "";
    private int tempCode = 0;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private boolean valid;

   // private String timestrtimestr;
    private String dateStr = "";
  //  private String timestr = "";

   // private String datetimeStr = "";
    private TextView selectDateTimeError;
    private TextView selectDateTime;

    private String titleTxt = "";
    private String messageTxt = "";
    private String dateTime = "";

    private SharedPreferences preferences;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_send_notification_enter_msg, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar_enter_msg);
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        Objects.requireNonNull(getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            if (getActivity() != null && activity.getSupportActionBar() != null && getActivity().getResources() != null) {
                activity.getSupportActionBar().setHomeButtonEnabled(true);
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activity.getSupportActionBar().setHomeAsUpIndicator(getActivity().getResources().getDrawable(R.drawable.ic_arrow_left));
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        }
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);

        selectDateTimeError = rootView.findViewById(R.id.date_error);
        MaterialButton sendButton = rootView.findViewById(R.id.send_ok_button);
        title = rootView.findViewById(R.id.send_title);
        msg = rootView.findViewById(R.id.send_msg);
        TextView absenceInfo = rootView.findViewById(R.id.absence_info);

        // title.setScroller(new Scroller(getActivity()));
        // title.setVerticalScrollBarEnabled(true);
        //title.setMaxLines(3);
        // title.setMovementMethod(new ScrollingMovementMethod());

        // msg.setScroller(new Scroller(getActivity()));
        //msg.setMaxLines(10);
        //msg.setVerticalScrollBarEnabled(true);
        // msg.setMovementMethod(new ScrollingMovementMethod());

        selectDateTime = rootView.findViewById(R.id.scheduled_date);
        TextInputLayout titlelayout = rootView.findViewById(R.id.title_layout);
        TextInputLayout msglayout = rootView.findViewById(R.id.msg_layout);
        tempCode = Objects.requireNonNull(getArguments()).getInt(TEMP_CODE);

        titleTxt = Objects.requireNonNull(getArguments()).getString(TITLE);
        messageTxt = Objects.requireNonNull(getArguments()).getString(MESSAGE);
        dateTime = Objects.requireNonNull(getArguments()).getString(DATE_TIME);
        if(dateTime!= null){
            dateStr = dateTime;
            selectDateTime.setText(dateTime);
        }
        Log.d("setSoftInputMode ", "onCreateView: "+dateStr);
        if (tempCode == 1) {
            selectDateTime.setVisibility(View.GONE);
            daysofAbsence = Objects.requireNonNull(getArguments()).getString(ABSEND_DAYS);
            String msgTxt = "Message set up for After ::ansence:: days of from last visit";
            absenceInfo.setText(msgTxt.replace("::ansence::", daysofAbsence));
        } else if (tempCode == 11) {
            selectDateTime.setVisibility(View.GONE);
            daysofAbsence = Objects.requireNonNull(getArguments()).getString(ABSEND_DAYS);
            String msgTxt = "Message set up for After ::ansence:: days of from last visit";
            absenceInfo.setText(msgTxt.replace("::ansence::", daysofAbsence));
        } else if (tempCode == 12) {
            selectDateTime.setVisibility(View.GONE);
            absenceInfo.setText("This notification will be send to All Customers  at selected date and time");
            // daysofAbsence = Objects.requireNonNull(getArguments()).getString(ABSEND_DAYS);
            // String msgTxt = "Message set up for After ::ansence:: days of from last visit";
            // absenceInfo.setText(msgTxt.replace("::ansence::", daysofAbsence));
        } else {
            selectDateTime.setVisibility(View.VISIBLE);
            // absenceInfo.setText("All Customers");
            absenceInfo.setText("This notification will be send to All Customers  at selected date and time");
        }
        title.setText(titleTxt);
        msg.setText(messageTxt);

        sendButton.setOnClickListener(view -> validate());

        selectDateTime.setOnClickListener(v -> {
            selectDate(selectDateTime);
        });

        return rootView;
    }

    private void validate() {


        String titlestr = "";
        String msgstr = "";
        if (tempCode == 1) {
            boolean isValid = true;
            if (Objects.requireNonNull(msg.getText()).toString().isEmpty()) {
                msg.setError("Enter valid message");
                msg.requestFocus();
                isValid = false;
            } else {
                if (msg.getText().length() < 15) {
                    msg.setError("Enter minimum 15 characters");
                    msg.requestFocus();
                    isValid = false;
                }
            }
            if (Objects.requireNonNull(title.getText()).toString().isEmpty()) {
                title.setError("Enter valid title");
                title.requestFocus();
                isValid = false;
            } else {
                if (title.getText().length() < 5) {
                    isValid = false;
                    title.setError("Enter minimum 5 characters");
                    title.requestFocus();
                }
            }

            if (isValid) {
                titlestr = title.getText().toString();
                msgstr = msg.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString(ABSEND_DAYS, daysofAbsence);
                bundle.putString(TITLE, titlestr);
                bundle.putString(MESSAGE, msgstr);
                bundle.putInt(TEMP_CODE, tempCode);
                NotificationPreview fragment1 = new NotificationPreview();
                fragment1.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.replace(R.id.send_notification_frame_layout, fragment1);
                fragmentTransaction.commit();
            }

        } else {
            boolean isValid = true;
            if (dateStr.equals("")) {
                selectDateTimeError.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        selectDateTimeError.setVisibility(View.INVISIBLE);
                    }
                }, 2000);
                isValid = false;
            } else {
                selectDateTimeError.setVisibility(View.INVISIBLE);
            }
            if (Objects.requireNonNull(msg.getText()).toString().isEmpty()) {
                msg.setError("Enter valid message");
                msg.requestFocus();
                isValid = false;
            } else {
                if (msg.getText().length() < 15) {
                    msg.setError("Enter minimum 15 characters");
                    msg.requestFocus();
                    isValid = false;
                }
            }
            if (Objects.requireNonNull(title.getText()).toString().isEmpty()) {
                title.setError("Enter valid title");
                title.requestFocus();
                isValid = false;
            } else {
                if (title.getText().length() < 5) {
                    isValid = false;
                    title.setError("Enter minimum 5 characters");
                    title.requestFocus();
                }
            }

            if (isValid) {

                titlestr = title.getText().toString();
                msgstr = msg.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString(ABSEND_DAYS, daysofAbsence);
                bundle.putString(TITLE, titlestr);
                bundle.putString(MESSAGE, msgstr);
                bundle.putInt(TEMP_CODE, tempCode);
                bundle.putString(DATE_TIME, dateStr);
                NotificationPreview fragment1 = new NotificationPreview();
                fragment1.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                fragmentTransaction.replace(R.id.send_notification_frame_layout, fragment1);
                fragmentTransaction.commit();
            }

        }

    }

    @Override
    public boolean onBackPressed() {
        if (tempCode == 1) {
            Bundle bundle = new Bundle();
            bundle.putString(ABSEND_DAYS, daysofAbsence);
            NotificationHome fragment1 = new NotificationHome();
            fragment1.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.send_notification_frame_layout, fragment1);
            fragmentTransaction.commit();
        } else {
            NotificationHome fragment1 = new NotificationHome();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(fragmentManager).beginTransaction();
            fragmentTransaction.replace(R.id.send_notification_frame_layout, fragment1);
            fragmentTransaction.commit();
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectDate(TextView tv) {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()),
                (view, year, monthOfYear, dayOfMonth) -> {

                    String a = String.valueOf(monthOfYear + 1);
                    String b = a;
                    if (a.length() == 1) {
                        b = "0" + a;
                    }
                    dateStr = year + "-" + b + "-" + dayOfMonth;
                    tv.setText(dateStr);

                   /* valid = isAfterToday(year, monthOfYear, dayOfMonth);
                    if (valid) {
                        dateStr = year + "-" + b + "-" + dayOfMonth;
                        selectTime(tv);
                    } else {
                        selectDateTimeError.setText("Invalid Date");
                        selectDateTimeError.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(() -> selectDateTimeError.setVisibility(View.INVISIBLE), 2500);
                    }*/

                }, mYear, mMonth, mDay);
        c.add(Calendar.DATE, +1);
        Date mindte= c.getTime();
        long min = mindte.getTime();
        datePickerDialog.getDatePicker().setMinDate(min);
        c.add(Calendar.MONTH, +1);
        c.add(Calendar.DAY_OF_YEAR, +1);
        Date dOneMonthAgo = c.getTime();
        long oneMonthAgoMillis = dOneMonthAgo.getTime();
        datePickerDialog.getDatePicker().setMaxDate(oneMonthAgoMillis);
        datePickerDialog.show();
    }

   /* private void selectTime(TextView tv) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);

        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        Calendar datetime = Calendar.getInstance();
                        Calendar c = Calendar.getInstance();
                        c.add(Calendar.HOUR_OF_DAY, -2);
                        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        datetime.set(Calendar.MINUTE, minute);

                        if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
                            //it's after current
                            int hour = hourOfDay % 12;
                            // tv.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                            //      minute, hourOfDay < 12 ? "am" : "pm"));
                            String a = String.valueOf(minute);
                            if (a.length() == 1) {
                                a = "0" + a;
                            }
                            timestr = hourOfDay + ":" + a;
                            datetimeStr = dateStr + " " + timestr + ":00";
                            tv.setText(datetimeStr);
                            tv.setTextColor(getResources().getColor(R.color.btnGreen));
                           // isAfterToday(datetimeStr);
                        } else {
                            datetimeStr = "";
                            Animation startAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.blinking_animation1);
                            tv.startAnimation(startAnimation);
                            String a = "Invalid Time";
                            tv.setText(a);
                            tv.setTextColor(getResources().getColor(R.color.redbtn));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    tv.clearAnimation();
                                    String a = "Select Date & Time";
                                    tv.setText(a);
                                    tv.setTextColor(getResources().getColor(R.color.redbtn));
                                }
                            }, 2000);
                        }
                    }
                }, mHour, mMinute, false);

        timePickerDialog.show();

    }*/

   /*   private static boolean isAfterToday(String SelectedDate) {
        Calendar today = Calendar.getInstance();
        Calendar myDate = Calendar.getInstance();

        today.add(Calendar.HOUR_OF_DAY, -2);

        String dtStart = "2010-10-15T09:27:37Z";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date selectDt = null;
        try {
             selectDt = format.parse(SelectedDate);
           // System.out.println(date1);
            Log.d("timePickerDialog", "isAfterToday: mydate----  " + selectDt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (selectDt.before(today.getTime())){

        }
            Log.d("timePickerDialog", "isAfterToday: today---  " + today.getTime().toString());
        //   Log.d("timePickerDialog", "isAfterToday: mydate----  "+myDate.getTime() );

        // myDate.set(year, month, day);
      //  return !myDate.before(today);
        return !selectDt.before(today.getTime());
    }
  private static boolean isAfterToday(int year, int month, int day) {
        Calendar today = Calendar.getInstance();
        Calendar myDate = Calendar.getInstance();

        today.add(Calendar.HOUR_OF_DAY, -2);

        Log.d("timePickerDialog", "isAfterToday: today---  "+today.getTime() );
        Log.d("timePickerDialog", "isAfterToday: mydate----  "+myDate.getTime() );

        myDate.set(year, month, day);
        return !myDate.before(today);
    }*/
}
