package life.corals.merchant.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import life.corals.merchant.R;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;

public  abstract  class RedeemProductBottomSheet implements DialogInterface.OnCancelListener, View.OnClickListener {

    private BottomSheetDialog bottomSheetDialog;

    private View view;

    private LinearLayout peekLayout;

    private LinearLayout bottomLayout;

    private BottomSheetBehavior mBehavior;

    private TextView msgTextView;

    private ProgressBar progressBar;
    private SharedPreferences preferences;
    private Context mCtx;

    private TextView issueAmounttv;
    private TextView mobileTxtvw;

    public RedeemProductBottomSheet(@NonNull Context mCtx) {
        this.mCtx = mCtx;
        initializeAlertDialog(mCtx);
    }

    private void initializeAlertDialog(Context mCtx) {
        preferences = mCtx.getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        bottomSheetDialog = new BottomSheetDialog(mCtx, R.style.SheetDialog);
        view = LayoutInflater.from(mCtx).inflate(R.layout.bottom_sheet_redeem_product_note_alert_dialog, null, false);
        peekLayout = view.findViewById(R.id.top_up_note_peek_layout);
        bottomLayout = view.findViewById(R.id.top_up_note_bottom_layout);
        msgTextView = view.findViewById(R.id.top_up_note_peek_layout_msg_txt_view);
        progressBar = view.findViewById(R.id.top_up_note_peek_layout_progress_bar);

        MaterialButton peekLayoutCancelButton = view.findViewById(R.id.top_up_note_peek_layout_cancel_button);
        MaterialButton proceedButton = view.findViewById(R.id.top_up_note_proceed_button);
        MaterialButton cancelButton = view.findViewById(R.id.top_up_note_cancel_button);
        peekLayoutCancelButton.setOnClickListener(this);
        proceedButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        issueAmounttv = view.findViewById(R.id.top_up_note_top_up_amount);
        mobileTxtvw = view.findViewById(R.id.top_up_note_top_up_bonus_amount);
        // issueAmounttv.setText(issueamnt);
        //mobileTxtvw.setText(mobile);

        issueAmounttv.setText("");
        mobileTxtvw.setText("");

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

    public void showRedeemproductQRNoteAlertDialog(String issueAmountStr, String mobilestr) {
        if (bottomSheetDialog != null) {
            issueAmounttv.setText(issueAmountStr);
            mobileTxtvw.setText(mobilestr);
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

    public void dismissNoteAlertDialog() {
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
            dismissNoteAlertDialog();


        }

        if (v.getId() == R.id.top_up_note_proceed_button) {

            onProceedButtonClick();
        }

    }
}
