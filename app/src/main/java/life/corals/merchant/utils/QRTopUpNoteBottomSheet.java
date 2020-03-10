package life.corals.merchant.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;


import life.corals.merchant.R;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.CURRENCY_SYMBOL;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;

public abstract class QRTopUpNoteBottomSheet implements DialogInterface.OnCancelListener, View.OnClickListener {

    private BottomSheetDialog bottomSheetDialog;

    private View view;

    private LinearLayout peekLayout;

    private LinearLayout bottomLayout;

    private BottomSheetBehavior mBehavior;

    private TextView msgTextView;

    private ProgressBar progressBar;
    private SharedPreferences preferences;
    private Context mCtx;

    private TextView topupAmount;
    private TextView bonusAmount;

    public QRTopUpNoteBottomSheet(@NonNull Context mCtx, String topupAmt, String bonusAmt) {
        this.mCtx = mCtx;
        initializeAlertDialog(mCtx, topupAmt, bonusAmt);
    }

    private void initializeAlertDialog(Context mCtx, String topupAmt, String bonusAmt) {
        preferences = mCtx.getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        bottomSheetDialog = new BottomSheetDialog(mCtx, R.style.SheetDialog);
        view = LayoutInflater.from(mCtx).inflate(R.layout.bottom_sheet_top_up_note_alert_dialog, null, false);
        peekLayout = view.findViewById(R.id.top_up_note_peek_layout);
        bottomLayout = view.findViewById(R.id.top_up_note_bottom_layout);
        msgTextView = view.findViewById(R.id.top_up_note_peek_layout_msg_txt_view);
        progressBar = view.findViewById(R.id.top_up_note_peek_layout_progress_bar);

        Button peekLayoutCancelButton = view.findViewById(R.id.top_up_note_peek_layout_cancel_button);
        Button proceedButton = view.findViewById(R.id.top_up_note_proceed_button);
        Button cancelButton = view.findViewById(R.id.top_up_note_cancel_button);
        peekLayoutCancelButton.setOnClickListener(this);
        proceedButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        topupAmount = view.findViewById(R.id.top_up_note_top_up_amount);
        bonusAmount = view.findViewById(R.id.top_up_note_top_up_bonus_amount);

        //String topup = preferences.getString(CURRENCY_SYMBOL,"").concat(topupAmt);
        //String bonus = preferences.getString(CURRENCY_SYMBOL,"").concat(bonusAmt);

        topupAmount.setText("");
        bonusAmount.setText("");

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return false;
            }
        });
        ((View) view.getParent()).setBackgroundColor(mCtx.getResources().getColor(android.R.color.transparent));

        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setHideable(false);

        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        params.setMargins(18, 0, 18, 0);

        bottomLayout.setVisibility(View.GONE);
    }

    public void showQRTopUpNoteAlertDialog(String topUpAmountStr, String bonusAmountStr) {
        if (bottomSheetDialog != null) {
             String topUpAmountStr1 = preferences.getString(CURRENCY_SYMBOL,"").concat(topUpAmountStr);
            String bonusAmountStr1 = preferences.getString(CURRENCY_SYMBOL,"").concat(bonusAmountStr);
            topupAmount.setText(topUpAmountStr1);
            bonusAmount.setText(bonusAmountStr1);
            bottomLayout.setVisibility(View.VISIBLE);
            peekLayout.setVisibility(View.GONE);
            mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public void changeScannerMessage(String message) {
        msgTextView.setText(message);
        if (message.equals(mCtx.getResources().getString(R.string.scanning_qr))) {
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    public void dismissQRTopUpNoteAlertDialog() {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
            onCancelButtonCLick();
        }
    }

    public void dismissOnProceed() {
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            bottomSheetDialog.dismiss();
        }
    }

    public abstract void onCancelButtonCLick();

    public abstract void onProceedButtonClick();

    @Override
    public void onCancel(DialogInterface dialog) {

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.top_up_note_cancel_button || v.getId() == R.id.top_up_note_peek_layout_cancel_button) {
            dismissQRTopUpNoteAlertDialog();


        }

        if (v.getId() == R.id.top_up_note_proceed_button) {
            onProceedButtonClick();
        }

    }
}
