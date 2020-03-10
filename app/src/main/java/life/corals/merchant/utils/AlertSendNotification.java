package life.corals.merchant.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import life.corals.merchant.R;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;

public abstract class AlertSendNotification {
    protected AlertSendNotification(Context mcntx, String name) {
        SendNotification(mcntx, name);
    }

    private androidx.appcompat.app.AlertDialog alertDialog;

    private void SendNotification(Context mcntx, String name) {
        final String[] datestr = new String[1];
        final DatePickerDialog[] picker = new DatePickerDialog[1];
        SharedPreferences preferences = mcntx.getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        android.app.AlertDialog.Builder builder;
        android.app.AlertDialog alertDialog = null;
        View view = LayoutInflater.from(mcntx).inflate(R.layout.alert_send_notification, null, false);

        MaterialButton submit = view.findViewById(R.id.okay_button);
        TextView nameTv = view.findViewById(R.id.name_for_notification);
        //TextInputLayout titlelayout = view.findViewById(R.id.tle_layout);
        //TextInputLayout msglayout = view.findViewById(R.id.msg_layout);

        if (!name.isEmpty()) {
            nameTv.setText(name);
            nameTv.setVisibility(View.VISIBLE);
        } else {
            nameTv.setVisibility(View.GONE);
        }
        TextInputEditText title = view.findViewById(R.id.title);
        TextInputEditText msg = view.findViewById(R.id.msg);


        title.setScroller(new Scroller(mcntx));
        title.setVerticalScrollBarEnabled(true);
        title.setMaxLines(3);
        title.setMovementMethod(new ScrollingMovementMethod());

        msg.setScroller(new Scroller(mcntx));
        msg.setMaxLines(7);
        msg.setVerticalScrollBarEnabled(true);
        msg.setMovementMethod(new ScrollingMovementMethod());


        MaterialButton cancel = view.findViewById(R.id.cancel_btn);
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
        lp.width =ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.x = 0;
        lp.y = 0;
        alertDialog.getWindow().setAttributes(lp);

        android.app.AlertDialog finalAlertDialog1 = alertDialog;
        title.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        msg.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cancel.setOnClickListener(v -> {
            finalAlertDialog1.dismiss();
            onCancelButtonClick();
        });
        submit.setOnClickListener(view1 -> {
            boolean isValid = true;

            if (Objects.requireNonNull(msg.getText()).toString().isEmpty() || msg.getText() == null) {
                msg.setError("Invalid message");
                msg.requestFocus();
                isValid = false;
            } else {
                if (msg.getText().length() < 15) {
                    msg.setError("Enter minimum 15 characters");
                    msg.requestFocus();
                    isValid = false;
                }
            }

            if (Objects.requireNonNull(title.getText()).toString().isEmpty() || title.getText() == null) {
                title.setError("Invalid title");
                title.requestFocus();
            } else {
                if (title.getText().length() < 5) {
                    title.setError("Enter minimum 5 characters");
                    title.requestFocus();
                    isValid = false;
                }
            }

            if (isValid) {
                finalAlertDialog1.dismiss();
                onOkButtonClick(title.getText().toString(), msg.getText().toString());
            }
        });
    }

    private int getDisplayWidth(AlertDialog alertDialog) {
        int width = 800;
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

    public abstract void onOkButtonClick(String title, String msg);

    public abstract void onCancelButtonClick();


    public void dismissAlertDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}