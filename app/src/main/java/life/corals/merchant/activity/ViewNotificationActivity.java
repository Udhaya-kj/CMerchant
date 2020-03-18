package life.corals.merchant.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import life.corals.merchant.fcm.FcmConstants;
import life.corals.merchant.utils.AlertNotificationDialog;

public class ViewNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        Bundle bundle = getIntent().getExtras();
        try {
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    try {
                        Object value = bundle.getString(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (bundle != null && bundle.containsKey(FcmConstants.TITLE)
                    && bundle.containsKey(FcmConstants.MESSAGE_BODY)
                    && bundle.get(FcmConstants.TITLE) != null) {

                String imgUrl = null;
                if (bundle.get(FcmConstants.IMAGE) != null) {
                    imgUrl = bundle.get(FcmConstants.IMAGE).toString();
                }

                AlertNotificationDialog coralsNotificationDialog =
                        new AlertNotificationDialog(ViewNotificationActivity.this, false, 0) {
                            @Override
                            public void onNotifyAlertDismiss() {
                                onBackPressed();
                            }
                        };

                if (bundle.containsKey(FcmConstants.MERCHANT_NAME)) {
                    coralsNotificationDialog.setAlertDialogTitle(bundle.get(FcmConstants.MERCHANT_NAME).toString());
                } else {
                    coralsNotificationDialog.setAlertDialogTitle(null);
                }
                coralsNotificationDialog.setNotifyBody(bundle.get(FcmConstants.MESSAGE_BODY).toString());
                coralsNotificationDialog.setNotifyTitle(bundle.get(FcmConstants.TITLE).toString());
                coralsNotificationDialog.setNotifyImage(imgUrl);
                coralsNotificationDialog.showNotificationDialog();
            } else {
                onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}