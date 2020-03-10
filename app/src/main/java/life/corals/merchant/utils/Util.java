package life.corals.merchant.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    private static final String SINGAPORE = "SG";

    private static final String INDIA = "IN";

    private static final String MALAISIA = "MY";

    private static final String ASIA_SINGAPORE = "Asia/Singapore";

    private static final String ASIA_KOLKATA = "Asia/Kolkata";

    private static final String ASIA_MALAISIA = "Asia/Kuala_Lumpur";

    public static boolean getConnectivityStatusString(Context context) {
        ConnectivityManager cm = (ConnectivityManager)           context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else return activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            return false;
        }
    }

    public static boolean isValidCountryMobileNumber(String mobileNumberStr, String country) {
        ParamProperties paramProperties = new ParamProperties();
        String COUNTRY_MOB_NUM_LENGTH = String.valueOf(Integer.parseInt(paramProperties.getProperty(country, ParamProperties.MOBILE_MAX_LENGTH)) - 1);
        String COUNTRY_MOB_START_DIGITS = paramProperties.getProperty(country, ParamProperties.MOBILE_START_DIGITS);
        if (country.equals("MY")){
            COUNTRY_MOB_NUM_LENGTH = COUNTRY_MOB_NUM_LENGTH.concat(",11");
        }
        String expression = "^" + COUNTRY_MOB_START_DIGITS + "\\d" + "{".concat(COUNTRY_MOB_NUM_LENGTH) + "}$";
        Log.d("ValidCountryMobi", "isValidCountryMobileNumber: " + expression);
        CharSequence inputString = mobileNumberStr;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getCountryCurrentTimestamp(String country) {
        String timeZone = null;
        if (SINGAPORE.equals(country)) {
            timeZone = ASIA_SINGAPORE;
        } else if (INDIA.equals(country)) {
            timeZone = ASIA_KOLKATA;
        } else if (MALAISIA.equals(country)) {
            timeZone = ASIA_MALAISIA;
        } else {
            timeZone = ASIA_SINGAPORE;
        }
        Calendar calendar = Calendar.getInstance();
        DateFormat DateFormat = new SimpleDateFormat("dd");
        DateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return DateFormat.format(calendar.getTime());
    }

}