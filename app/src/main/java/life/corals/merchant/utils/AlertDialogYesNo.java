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

public abstract class AlertDialogYesNo {
    protected AlertDialogYesNo(Context mcntx, String title,String msg,String btn1,String btn2) {
        AlertDialog(mcntx,title,msg,btn1,btn2);
    }

    private void AlertDialog(Context mcntx,String title,String msg,String btn1,String btn2) {
        AlertDialog.Builder builder;
        AlertDialog alertDialog = null;
        View view = LayoutInflater.from(mcntx).inflate(R.layout.alertdialog_yes_no, null, false);
        MaterialButton btnOK = view.findViewById(R.id.button_ok);
        MaterialButton btnCancel = view.findViewById(R.id.button_cancel);

        TextView titletv = view.findViewById(R.id.title_alert_yes_no);
        TextView msgtv = view.findViewById(R.id.msg_body);
        titletv.setText(title);
        msgtv.setText(msg);
        btnOK.setText(btn1);
        btnCancel.setText(btn2);
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

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalAlertDialog1.dismiss();
                onOKButtonClick();
            }

        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalAlertDialog1.dismiss();
                onCancelButtonClick();
            }

        });
    }

    public abstract void onOKButtonClick();
    public abstract void onCancelButtonClick();

    private int getDisplayWidth(AlertDialog alertDialog) {
        int width = 600;
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
}
