package life.corals.merchant.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import life.corals.merchant.R;
import life.corals.merchant.activity.CashCampaigns;
import life.corals.merchant.activity.HistoryHomePage;
import life.corals.merchant.activity.MyCustomers;
import life.corals.merchant.activity.PerformanceActivity;
import life.corals.merchant.activity.PrivacyPolicy;
import life.corals.merchant.activity.QrActivity;
import life.corals.merchant.activity.SendNotificationActivity;
import life.corals.merchant.activity.ShowQrActivity;
import life.corals.merchant.activity.SplashScreen;
import life.corals.merchant.activity.TermsandConditions;
import life.corals.merchant.activity.VoucherSetupHome;


public class BottomSheetDialogg implements View.OnClickListener {
    private Context mCtx;
    private BottomSheetDialog bottomSheetDialog;

    public BottomSheetDialogg(Context mCtx) {
        this.mCtx = mCtx;
        setUpDialog();
    }

    private void setUpDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        bottomSheetDialog = new BottomSheetDialog(mCtx, R.style.SheetDialog1);

        View sheetView = layoutInflater.inflate(R.layout.bottom_alertdiolag_view, null, false);
        bottomSheetDialog.setContentView(sheetView);
        ((View) sheetView.getParent()).setBackgroundColor(mCtx.getResources().getColor(android.R.color.transparent));

        LinearLayout myCustomers = sheetView.findViewById(R.id.myCustomers);
        LinearLayout insights = sheetView.findViewById(R.id.sendmesaage);
        LinearLayout sendMessage = sheetView.findViewById(R.id.insights);
        LinearLayout campaign = sheetView.findViewById(R.id.campaigns);

        LinearLayout history = sheetView.findViewById(R.id.history);
        LinearLayout qr = sheetView.findViewById(R.id.qr);

        LinearLayout exit = sheetView.findViewById(R.id.exit);
       // LinearLayout qr = sheetView.findViewById(R.id.qr);
        LinearLayout vocuherHome = sheetView.findViewById(R.id.vocuher_home);

        MaterialButton terms = sheetView.findViewById(R.id.termsconditions);
        MaterialButton policy = sheetView.findViewById(R.id.perivacypolicy);


        myCustomers.setOnClickListener(this);
        insights.setOnClickListener(this);
        sendMessage.setOnClickListener(this);
        campaign.setOnClickListener(this);
        history.setOnClickListener(this);
        qr.setOnClickListener(this);
        terms.setOnClickListener(this);
        policy.setOnClickListener(this);
        vocuherHome.setOnClickListener(this);
        exit.setOnClickListener(this);


    }

    public void showBottomSheetDialog() {
        if (bottomSheetDialog != null && !bottomSheetDialog.isShowing())
            bottomSheetDialog.show();
    }

    public void dismissBottomSheetDialog() {
        if (bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        dismissBottomSheetDialog();
        if (v.getId() == R.id.myCustomers) {
            mCtx.startActivity(new Intent(((Activity) mCtx), MyCustomers.class));
            ((Activity) mCtx).finish();
        } else if (v.getId() == R.id.insights) {
            mCtx.startActivity(new Intent(((Activity) mCtx), PerformanceActivity.class));
            ((Activity) mCtx).finish();
        } else if (v.getId() == R.id.sendmesaage) {
            mCtx.startActivity(new Intent(((Activity) mCtx), SendNotificationActivity.class));
            ((Activity) mCtx).finish();
        } else if (v.getId() == R.id.campaigns) {
            mCtx.startActivity(new Intent(((Activity) mCtx), CashCampaigns.class));
            ((Activity) mCtx).finish();
        } else if (v.getId() == R.id.history) {
            mCtx.startActivity(new Intent(((Activity) mCtx), HistoryHomePage.class));
            ((Activity) mCtx).finish();
        } else if (v.getId() == R.id.termsconditions) {
            mCtx.startActivity(new Intent(((Activity) mCtx), TermsandConditions.class));
            ((Activity) mCtx).finish();
        } else if (v.getId() == R.id.perivacypolicy) {
            mCtx.startActivity(new Intent(((Activity) mCtx), PrivacyPolicy.class));
            ((Activity) mCtx).finish();
        } else if (v.getId() == R.id.qr) {
            mCtx.startActivity(new Intent(((Activity) mCtx), QrActivity.class));
            ((Activity) mCtx).finish();
        }else if (v.getId() == R.id.vocuher_home) {
            mCtx.startActivity(new Intent(((Activity) mCtx), VoucherSetupHome.class));
            ((Activity) mCtx).finish();
        }
        else if (v.getId() == R.id.exit) {
            //mCtx.startActivity(new Intent(((Activity) mCtx), VoucherSetupHome.class));
            bottomSheetDialog.cancel();
            ((Activity) mCtx).finish();
        }else {
            mCtx.startActivity(new Intent(((Activity) mCtx), SplashScreen.class));
            ((Activity) mCtx).finish();
        }
    }
    /*  private void showMyQrDialog() {
        ((Activity) mCtx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if ((Activity) mCtx != null) {
                        SharedPreferences preferences = mCtx.getSharedPreferences(CUSTOMER_PREFERENCES, MODE_PRIVATE);
                        String CUST_ID = preferences.getString(CUSTOMER_ID, null);
                        String DEV_ID = preferences.getString(DEVICE_ID, null);
                        String COUNTRY = preferences.getString(Constants.COUNTRY_CODE, null);
                        preferences = mCtx.getSharedPreferences(LOGIN_PREFERENCE, MODE_PRIVATE);
                        String MOB = preferences.getString(LOGIN_MOBILE_NUMBER, null);

                        GenererateQR genererateQR = new GenererateQR();
                        String MY_QR_STRING = genererateQR.generateCustMyQR(COUNTRY, CUST_ID, MOB, DEV_ID);
                        Bitmap MY_QR_BITMAP = genererateQR.generateQRBitmap(MY_QR_STRING);

                        MyQRCodeDialog myQRCodeDialog = new MyQRCodeDialog((Activity) mCtx);
                        myQRCodeDialog.showAlertDialog(MY_QR_BITMAP);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (((Activity) mCtx) != null) {
                        Snackbar.make(((Activity) mCtx).getWindow().getDecorView(), "Sorry unable to load myQR", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }*/
}
