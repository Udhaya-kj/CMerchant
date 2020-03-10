package life.corals.merchant.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.fragments.NotificationOnetoOnePreview;

public abstract class AlertDialogFailure {

    public AlertDialogFailure(Context mcntx, String msg, String btnTxt, String oopsmsg) {
        failureDiolog(mcntx, msg, btnTxt, oopsmsg);
    }

    public AlertDialogFailure(NotificationOnetoOnePreview notificationOnetoOnePreview, String msg, String ok, String oopsmsg) {
    }

    private void failureDiolog(Context mcntx, String msg, String btnTxt, String oopsmsg) {
        AlertDialog.Builder builder;
        AlertDialog alertDialog = null;
        View view = LayoutInflater.from(mcntx).inflate(R.layout.alert_diolog_failed, null, false);
        MaterialButton btn = view.findViewById(R.id.buttonOk);
        TextView txtvw = view.findViewById(R.id.alert_diolog_msg);
        TextView oops = view.findViewById(R.id.oops);
        if (oopsmsg.equals("")) {
            oops.setVisibility(View.GONE);
        }
        txtvw.setText(msg);
        btn.setText(btnTxt);
        oops.setText(oopsmsg);
        final android.app.AlertDialog finalAlertDialog = alertDialog;
        builder = new android.app.AlertDialog.Builder(mcntx);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(alertDialog.getWindow()).getAttributes());
        lp.width = getDisplayWidth(alertDialog);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.x = 0;
        lp.y = 0;
        alertDialog.getWindow().setAttributes(lp);

        android.app.AlertDialog finalAlertDialog1 = alertDialog;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalAlertDialog1.dismiss();
                onButtonClick();
            }

        });
    }

    private int getDisplayWidth(AlertDialog alertDialog) {
        int width = 500;
        if (alertDialog.getWindow() != null) {
            Display display = alertDialog.getWindow().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            if (size.x > 720) {
                width = size.x - 400;
            }
        }
        return width;
    }


    public abstract void onButtonClick();
}
