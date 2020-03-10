package life.corals.merchant.utils;

import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateFormatter {

    @SuppressLint("SimpleDateFormat")
    public String formatDateFromString(String dateString, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(Date.parse(dateString));
    }

    @SuppressLint("SimpleDateFormat")
    public String formatTimeFromString(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(Date.parse(dateString));
    }

    @SuppressLint("SimpleDateFormat")
    public String getCurrentDateTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm a");
        return sdf.format(new Date());
    }

    public  Timestamp convertStringToTimestamp(String strDate) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            // you can change format of date
            Date date = formatter.parse(strDate);
            Timestamp timeStampDate = new Timestamp(date.getTime());

            return timeStampDate;
        } catch (ParseException e) {
            return null;
        }
    }
}
