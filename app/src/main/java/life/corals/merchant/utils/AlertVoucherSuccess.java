package life.corals.merchant.utils;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.activity.VoucherSetupHome;
import life.corals.merchant.activity.VoucherSetupPreview;

public abstract class AlertVoucherSuccess {

    public AlertVoucherSuccess(Context mcntx, String content1, String content2) {
        successDiolog(mcntx, content1, content2);
    }

    private void successDiolog(Context mcntx, String content1, String content2) {
        AlertDialog.Builder builder;
        AlertDialog alertDialog = null;
        View view = LayoutInflater.from(mcntx).inflate(R.layout.alert_success_voucher, null, false);
        TextView msg1 = view.findViewById(R.id.msg1);
        TextView msg2 = view.findViewById(R.id.msg2);
        msg1.setText(content1);
        msg2.setText(content2);
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
    public abstract void closeDialog();
}
