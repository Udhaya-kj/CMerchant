package life.corals.merchant.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import life.corals.merchant.R;
import life.corals.merchant.fcm.FcmConstants;


public abstract class AlertNotificationDialog implements DialogInterface.OnCancelListener {

    private Context mCtx;

    private AlertDialog alertDialog;

    private TextView titleView;
    private TextView contentView;
    private TextView notificationDialogTitle;
    private RoundedImageView imgView;


    private String notifyTitle = null;
    private String notifyBody = null;
    private String notifyImage = null;
    private String alertDialogTitle = null;
    private int messagePosition = 0;


    private boolean isDialogCancelable = false;


    protected AlertNotificationDialog(Context mCtx, boolean isDialogCancelable, int messagePosition) {
        this.mCtx = mCtx;
        this.isDialogCancelable = isDialogCancelable;
        this.messagePosition = messagePosition;
        if (alertDialog == null) {
            initializeAlertDialog(mCtx);
        }
    }

    private void initializeAlertDialog(Context mCtx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setCancelable(isDialogCancelable);
        builder.setOnCancelListener(this);
        View view = LayoutInflater.from(mCtx).inflate(R.layout.notification_alert_dialog, null, false);

        titleView = view.findViewById(R.id.notification_title);
        contentView = view.findViewById(R.id.notification_content);
        imgView = view.findViewById(R.id.notification_image);
        notificationDialogTitle = view.findViewById(R.id.notification_dialog_title);

        builder.setView(view);
        alertDialog = builder.create();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    public void showNotificationDialog() {
        if (alertDialog != null && alertDialog.getWindow() != null) {
            alertDialog.show();

            titleView.setText(notifyTitle);
            contentView.setText(notifyBody);
            notificationDialogTitle.setText(alertDialogTitle);
            if (notifyImage != null) {
                imgView.setVisibility(View.VISIBLE);
                Glide.with(mCtx)
                        .load(notifyImage)
                        .centerCrop()
                        .into(imgView);
            }
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(alertDialog.getWindow().getAttributes());
            lp.width = getDisplayWidth();
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.x = 0;
            lp.y = 0;
            alertDialog.getWindow().setAttributes(lp);
            alertDialog.setCancelable(true);
            //updateNotificationStatus(messagePosition, FcmConstants.SEEN);
        }
    }

    private int getDisplayWidth() {
        int width = 500;
        if (alertDialog.getWindow() != null) {
            Display display = alertDialog.getWindow().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x - FcmConstants.APP_ALERT_MINUS_SIZE;
        }
        return width;
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        alertDialog.dismiss();
        onNotifyAlertDismiss();
    }

    public abstract void onNotifyAlertDismiss();

    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    public void setNotifyBody(String notifyBody) {
        this.notifyBody = notifyBody;
    }

    public void setNotifyImage(String notifyImage) {
        this.notifyImage = notifyImage;
    }

    public void setAlertDialogTitle(String alertDialogTitle) {
        if (alertDialogTitle != null && !alertDialogTitle.isEmpty()) {
            this.alertDialogTitle = alertDialogTitle;
        } else {
            this.alertDialogTitle = "Notification";
        }
    }
}