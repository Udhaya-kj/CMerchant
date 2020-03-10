package life.corals.merchant.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Objects;

import life.corals.merchant.R;

public abstract class AlertAddCampigns {
    protected AlertAddCampigns(Context mcntx) {
        addCampaigns(mcntx);
    }

    private androidx.appcompat.app.AlertDialog alertDialog;

    private void addCampaigns(Context mcntx) {
        final String[] datestr = new String[1];
        final DatePickerDialog[] picker = new DatePickerDialog[1];
        android.app.AlertDialog.Builder builder;
        android.app.AlertDialog alertDialog = null;
        View view = LayoutInflater.from(mcntx).inflate(R.layout.alert_add_campaigns, null, false);

        MaterialButton submit = view.findViewById(R.id.okay_button);

        TextInputEditText amnt = view.findViewById(R.id.add_camp_amount);
        TextInputEditText bns = view.findViewById(R.id.add_camp_bonus);

        MaterialButton cancelIcon = view.findViewById(R.id.cancel_btn);

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
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.x = 0;
        lp.y = 0;
        alertDialog.getWindow().setAttributes(lp);

        android.app.AlertDialog finalAlertDialog1 = alertDialog;
        amnt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String topupAmount;
                String DecimalAmnt;
                amnt.setCursorVisible(false);
                topupAmount = amnt.getText().toString();
                amnt.removeTextChangedListener(this);
                String cleanString = topupAmount.replaceAll("[$,.]", "");
                BigDecimal bigDecimal = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
                String converted = NumberFormat.getCurrencyInstance().format(bigDecimal);
                DecimalAmnt = converted.replaceAll("[$,£]", "");
                amnt.setText(DecimalAmnt);
                amnt.addTextChangedListener(this);
                amnt.post(() -> amnt.setSelection(amnt.getText().toString().length()));
                BigDecimal a = new BigDecimal(DecimalAmnt);
                BigDecimal b = new BigDecimal(0.00);
                if (bns.getText() != null && !bns.getText().toString().isEmpty()) {
                    b = new BigDecimal(bns.getText().toString());
                }

                if (a.compareTo(b) > 0) {

                   // bonusLayout.setErrorEnabled(false);
                    submit.setAlpha(1f);
                    submit.setClickable(true);

                }else if(a.equals(b)){
                    bns.setError("Bonus less than Amount is must");
                    submit.setAlpha(0.4f);
                    submit.setClickable(false);
                } else {

                    amnt.setError("Amount greater than bonus is must");
                    submit.setAlpha(0.4f);
                    submit.setClickable(false);
                }
            }
        });
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalAlertDialog1.dismiss();
                onCancelButtonClick();
            }
        });

        bns.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String topupAmount;
                String Decimabns;
                amnt.setCursorVisible(false);
                topupAmount = bns.getText().toString();
                bns.removeTextChangedListener(this);
                String cleanString = topupAmount.replaceAll("[$,.]", "");
                BigDecimal bigDecimal = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
                String converted = NumberFormat.getCurrencyInstance().format(bigDecimal);
                Decimabns = converted.replaceAll("[$,£]", "");
                bns.setText(Decimabns);
                bns.addTextChangedListener(this);
                bns.post(() -> bns.setSelection(bns.getText().toString().length()));
                BigDecimal a = new BigDecimal(Decimabns);
                BigDecimal b = new BigDecimal(0.00);

                if (amnt.getText() != null && !amnt.getText().toString().isEmpty()) {
                    b = new BigDecimal(amnt.getText().toString());
                }

                if (a.compareTo(b) < 0) {
                    //amntLayout.setErrorEnabled(false);
                    submit.setAlpha(1f);
                    submit.setClickable(true);
                }else if(a.equals(b)){
                    bns.setError("Bonus less than Amount is must");
                    submit.setAlpha(0.4f);
                    submit.setClickable(false);
                }else {
                    bns.setError("Bonus less than Amount is must");
                    submit.setAlpha(0.4f);
                    submit.setClickable(false);
                }
            }
        });
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalAlertDialog1.dismiss();
                onCancelButtonClick();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                if (Objects.requireNonNull(amnt.getText()).toString().isEmpty() || amnt.getText().toString().equals("0.00")) {
                    amnt.setError("Invalid amount");
                    isValid = false;
                }
                if (Objects.requireNonNull(bns.getText()).toString().isEmpty() || bns.getText().toString().equals("0.00")) {
                    bns.setError("Invalid bonus");
                    isValid = false;
                }

                if (isValid) {

                    finalAlertDialog1.dismiss();
                    onOkButtonClick(amnt.getText().toString(), bns.getText().toString());
                }
            }
        });
    }


    public abstract void onOkButtonClick(String amnt, String bns);

    public abstract void onCancelButtonClick();


    public void dismissAlertDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}