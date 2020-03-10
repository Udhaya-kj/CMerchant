package life.corals.merchant.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static life.corals.merchant.utils.Constants.CAMPAIGN_LIST;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.OUTLET_LIST;
import static life.corals.merchant.utils.Constants.TRANSACTION_TYPE;

public class MySharedPreference {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    int PRIVATE_MODE = 0;

    @SuppressLint("CommitPrefEdits")
    public MySharedPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveCampaignList(String campaign) {
        removeCampaignList();
        editor.putString(CAMPAIGN_LIST, campaign);
        editor.commit();
    }

    public void removeCampaignList() {
        editor.remove(CAMPAIGN_LIST);
        editor.apply();
    }

    public String getCampaignList() {
        return pref.getString(CAMPAIGN_LIST, "");
    }

    public void saveOutletList(String outlet) {
        removeOutletList();
        Log.d("pref_getString", outlet);
        editor.putString(OUTLET_LIST, outlet);
        editor.commit();
    }

    public void removeOutletList() {
        editor.remove(OUTLET_LIST);
        editor.apply();
    }

    public String getOutletList() {
        Log.d("pref_getString", "getOutletList"+pref.getString(OUTLET_LIST, ""));
        return pref.getString(OUTLET_LIST, "");
    }

    public int updateTransactionType(String jsonString) {
        removeTransactionType();
        editor.putString(TRANSACTION_TYPE, jsonString);
        editor.apply();
        return 1;
    }

    public String getTransactionType(String KEY) {
        if (!pref.contains(TRANSACTION_TYPE)) {
            return "";
        }
        String jsonString = pref.getString(TRANSACTION_TYPE, "");
        String transactionType = "";

        try {
            JSONObject object = new JSONObject(jsonString);
            JSONObject array = object.getJSONObject("txn_type");
            transactionType = array.get(KEY).toString();
        } catch (JSONException e) {
            e.getMessage();
        }
        return transactionType;
    }
    private void removeTransactionType() {
        editor.remove(TRANSACTION_TYPE);
        editor.apply();
    }

}