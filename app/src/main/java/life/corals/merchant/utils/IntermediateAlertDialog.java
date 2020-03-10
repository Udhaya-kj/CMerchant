package life.corals.merchant.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import life.corals.merchant.R;


public class IntermediateAlertDialog {

    private AlertDialog alertDialog;
    private Context context;

    public IntermediateAlertDialog(Context context) {
        this.context = context;
        initializeAlertDialog();
    }

    private void initializeAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.intermediate_alert_dialog_layout, null, false);
        builder.setView(view);
        alertDialog = builder.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        showAlertDialog();
    }

    private void showAlertDialog() {
        if (alertDialog != null && alertDialog.getWindow() != null) {
                alertDialog.show();

//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            lp.copyFrom(alertDialog.getWindow().getAttributes());
//            lp.width = 400;
//            lp.height = 300;
//            lp.x = 0;
//            lp.y = 0;
//            alertDialog.getWindow().setAttributes(lp);
        }
    }


    public void dismissAlertDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

}
