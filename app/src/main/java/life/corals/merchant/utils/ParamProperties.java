package life.corals.merchant.utils;

public class ParamProperties {

    private static final String CURRENCY_SYMBOL = "currency_symbol";
    public static final String COUNTRY_CODE = "country_code";
    public static final String COUNTRY_TIME_ZONE = "country_time_zone";
    public static final String COUNTRY_NAME = "country_name";
    public static final String MOBILE_MAX_LENGTH = "mobile_max_length";
    public static final String MOBILE_START_DIGITS = "mobile_start_digits";
    public static final String MOBILE_CODE = "mobile_code";

    public String getProperty(String countryCode, String PARAM_SUFFIX) {

        String PARAM_COUNTRY = "param_country_" + countryCode.toLowerCase() + "_".concat(PARAM_SUFFIX);

        String val = "";
        switch (PARAM_COUNTRY) {
            case "param_country_sg_currency_symbol":
                val = "$";
                break;
            case "param_country_sg_country_code":
                val = "SG";
                break;
            case "param_country_sg_country_time_zone":
                val = "Asia/Singapore";
                break;
            case "param_country_sg_country_name":
                val = "Singapore";
                break;
            case "param_country_sg_mobile_max_length":
                val = "8";
                break;
            case "param_country_sg_mobile_start_digits":
                val = "[8,9]";
                break;
            case "param_country_in_currency_symbol":
                val = "\u20B9";
                break;
            case "param_country_in_country_code":
                val = "IN";
                break;
            case "param_country_in_country_time_zone":
                val = "Asia/Kolkata";
                break;
            case "param_country_in_country_name":
                val = "India";
                break;
            case "param_country_in_mobile_max_length":
                val = "10";
                break;
            case "param_country_in_mobile_start_digits":
                val = "[6,7,8,9]";
                break;
            case "param_country_my_currency_symbol":
                val = "RM";
                break;
            case "param_country_my_country_code":
                val = "MY";
                break;
            case "param_country_my_country_time_zone":
                val = "Asia/Kuala_Lumpur";
                break;
            case "param_country_my_country_name":
                val = "Malaysia";
                break;
            case "param_country_my_mobile_max_length":
                val = "11";
                break;
            case "param_country_my_mobile_start_digits":
                val = "[0]";
                break;
            case "param_country_sg_mobile_code":
                val = "65";
                break;
            case "param_country_in_mobile_code":
                val = "91";
                break;
            case "param_country_my_mobile_code":
                val = "60";
                break;
        }
        return val;

    }
}
