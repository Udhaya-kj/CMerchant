package life.corals.merchant.utils;

import life.corals.merchant.BuildConfig;

public class Constants {


    //key words
    public static final String AMOUNT = "AMOUNT";
    public static final String BONUS = "BONUS";
    public static final String AMOUNT_ONE = "AMOUNT_ONE";
    public static final String BONUS_ONE = "BONUS_ONE";
    public static final String AMOUNT_TWO = "AMOUNT_TWO";
    public static final String BONUS_TWO = "BONUS_TWO";
    public static final String AMOUNT_THREE = "AMOUNT_THREE";
    public static final String BONUS_THREE = "BONUS_THREE";
    public static final String REGISTRATION_CODE = "REGISTRATION_CODE";
    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String CUSTOMER_ID = "CUSTOMER_ID";
    public static final String CAMPAIGN_ID = "CAMPAIGN_ID";
    public static final String INTENT_TEMP_CODE = "INTENT_TEMP_CODE";
    public static final String CAMPAIGNS_LIST = "CAMPAIGNS_LIST";
    public static final String TIMER_COUNT = "TIMER_COUNT";
   // public static final String QR_STRING= "QR_STRING";
    public static final String TEMP_STR = "TEMP_STR";
    public static final String TEMP_CODE = "TEMP_CODE";
    public static final String DATE_TIME = "DATE_TIME";
    public static final String TITLE = "TITLE";
    public static final String MESSAGE = "MESSAGE";
    public static final String DATETIME = "DATETIME";
    public static final String ABSEND_DAYS = "ABSEND_DAYS";
    public static final String TYPE = "TYPE";
    public static final String VALUE = "VALUE";

    //Shared Preferences
    public static final String SECURITY_PIN_PREFERENCE = "SECURITY_PIN_PREFERENCE";
    public static final String SECURITY_PIN = "SECURITY_PIN";
    public static final String MERCHANT_DETAILS_PREFERENCE = "MERCHANT_DETAILS_PREFERENCE";
    public static final String MERCHANT_DEVICE_PREFERENCE = "MERCHANT_DEVICE_PREFERENCE";
    public static final String MERCHANT_INSIGHTS_PREFERENCE = "MERCHANT_INSIGHTS_PREFERENCE";
    public static final String MERCHANT_HOME_INSIGHTS_PREFERENCE = "MERCHANT_HOME_INSIGHTS_PREFERENCE";
    public static final String MERCHANT_ID = "MERCHANT_ID";

    public static final String DEVICE_ID = "DEVICE_ID";
    public static final String OUTLET_ID = "OUTLET_ID";
    public static final String TOTAL_CUSTOMERS = "TOTAL_CUSTOMERS";
    public static final String DISPLAY_BUSINESS_NAME = "DISPLAY_BUSINESS_NAME";
    public static final String CURRENCY_SYMBOL = "CURRENCY_SYMBOL";
    public static final String IS_ADMIN = "IS_ADMIN";
    public static final String MERCHANT_COUNTRY = "MERCHANT_COUNTRY";
    public static final String PILOT_END_DATE = "PILOT_END_DATE";
    public static final String PAYMENT_DUE_AMOUNT = "PAYMENT_DUE_AMOUNT";
    public static final String SESSION_TOKEN = "SESSION_TOKEN";
    public static final String TOKEN_EXPIRY= "TOKEN_EXPIRY";
    public static final String CAMPAIGN_LIST= "CAMPAIGN_LIST";
    public static final String OUTLET_LIST= "OUTLET_LIST";
    public static final String TRANSACTION_TYPE = "TRANSACTION_TYPE";
    public static final String COUNTRY_CODE= "COUNTRY_CODE";
    public static final String MERCHANT_WALLET_BAL= "MERCHANT_WALLET_BAL";
    public static final String MERCHANT_LAST_LOGIN_TIME= "MERCHANT_LAST_LOGIN_TIME";
    public static final String INTENT_DESCRIPTION = "INTENT_DESCRIPTION";
    public static final String ACTIVATION_CODE_TRY_STRNG ="ACTIVATION_CODE_TRY_STRNG";
    public static final String DEVICE_REGISTER ="DEVICE_REGISTER";
    public static final String IS_LOADED ="IS_LOADED";
    public static final String QR_CODE ="QR_CODE";
    public static final String UNIQUE_ID ="UNIQUE_ID";
    public static final int ACTIVATION_CODE_TRY =0;
    public final static int APP_TIME_OUT = 69000;

    public static final String RESTAURANT_IMAGE_DOWNLOAD_URL_PREFIX = BuildConfig.MERCHANT_IMAGE_URL;
    public static final String RESTAURANT_IMAGE_DOWNLOAD_URL_SUFFIX_512_X_248 = "-logo-512x248.png";
    public static final String RESTAURANT_IMAGE_DOWNLOAD_URL_SUFFIX_240_X_148 = "-logo-240x148.png";

    //Insights summary constants
    public static final int INSIGHTS_LOAD_COUNT = 0;
    public static final int INSIGHTS_HOME_LOAD_COUNT = 0;
    public static final String INSIGHTS_HOME_LOAD_COUNT_STR = "INSIGHTS_HOME_LOAD_COUNT_STR";
    public static final String INSIGHTS_LOAD_COUNT_STR = "INSIGHTS_LOAD_COUNT_STR";
    public static final String CUSTOMER_OLD = "CUSTOMER_OLD";
    public static final String CUSTOMER_NEW = "CUSTOMER_NEW";
    public static final String CUSTOMER_VISIT_NEW = "CUSTOMER_VISIT_NEW";
    public static final String CUSTOMER_VISIT_OLD = "CUSTOMER_VISIT_OLD";
    public static final String IPONTS_OLD = "IPONTS_OLD";
    public static final String IPONTS_NEW = "IPONTS_NEW";
    public static final String RPONTS_OLD = "RPONTS_OLD";
    public static final String RPONTS_NEW = "RPONTS_NEW";
    public static final String NOOF_TOPUP_OLD = "NOOF_TOPUP_OLD";
    public static final String NOOF_TOPUP_NEW = "NOOF_TOPUP_NEW";
    public static final String NOOF_PAYMENTS_OLD = "NOOF_PAYMENTS_OLD";
    public static final String NOOF_PAYMENTS_NEW = "NOOF_PAYMENTS_NEW";
    public static final String TOT_PAYMENTS_OLD = "TOT_PAYMENTS_OLD";
    public static final String TOT_PAYMENTS_NEW = "TOT_PAYMENTS_NEW";
    public static final String TOT_TOPUP_NEW = "TOT_TOPUP_NEW";
    public static final String TOT_TOPUP_OLD = "TOT_TOPUP_OLD";

    //Insights Home page summary constants
    public static final String TOT_CUST = "TOT_CUST";
    public static final String TOT_CUST_STATUS = "TOT_CUST_STATUS";
    public static final String TOT_TRAFFIC = "TOT_TRAFFIC";
    public static final String TOT_TRAFFIC_STATUS = "TOT_TRAFFIC_STATUS";
    public static final String TOT_SALE = "TOT_SALE";
    public static final String TOT_SALE_STATUS = "TOT_SALE_STATUS";
    public static final String TOT_SALE_AMOUNT= "TOT_SALE_AMOUNT";

    //fireBase notification types
    public static final String NOTIFICATION_TYPE = "notification-type";
    public static final String ACTION_NOTIFICATION = "ACTION";
    public static final String NOTIFY_NOTIFICATION = "NOTIFY";
    public static final String NOTIFY_AND_ACTION_NOTIFICATION = "NOTIFY_AND_ACTION";
    public static final String POST_WALLET_BAL = "POST_WALLET_BAL";
    public static final String POST_WALLET_EXPIRY_DATE = "POST_WALLET_EXPIRY_DATE";
    public static final String FCM_TITLE = "title";
    public static final String FCM_BODY = "body";
    public static final String FCM_IMAGE_URL = "img_url";
    //redeem constants
    public static final String REDEEM_BONUS = "B";
    public static final String REDEEM_PRODUCT = "P";

    public static final String CORALS_SERVER_KEY = "AAAA_dQNC7A:APA91bEygITgilpPiQphQBzbLY3Yk1HMngBF1GT3M0Ox4sKY_vE_HX8essrL-BFOI9aUFJbZ4R5ENaT7UFeSc7oyX2CTlRFmZTINYO_ikGTQEjIsnzekuhtWc9z1mJEYHZKJFYQwUPoT";
    public static final String CORALS_LEGACY_SERVER_KEY = "AIzaSyAm5ynu-FcBHrSLzPiF-mKxw8gvnMLnp-A";
    public static final String COLLAPSE_KEY = "message";

    public static final String receiver = "c6wLxCnorTo:APA91bEmE8rb6D_I-BhBdz1BTkuyfDwWrFuHxSqxgqw0saNShvyKZaNeaf6Oo2uZCTR1uGymGmLLHpQRA1hQuw-K7Vqt5IxrKDt4hVK7RqqRjNEuFF3axoNg6aUPBXZ_j2-KFXW6PaD3";
    final static public String FCM_URL = "https://fcm.googleapis.com/fcm/send";


}
