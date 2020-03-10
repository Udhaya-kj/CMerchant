package life.corals.merchant.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Objects;

import life.corals.merchant.R;

public abstract class AlertShowQr {

    public AlertShowQr(Context mcntx, String qrName,String helptxt,String helptxt2,String qrString) {
        showQrDiolog(mcntx, qrName, qrString,helptxt,helptxt2);
    }

    private void showQrDiolog(Context mcntx, String qrName, String qrString,String helptxt,String helptxt2) {
        AlertDialog.Builder builder;
        AlertDialog alertDialog = null;
        View view = LayoutInflater.from(mcntx).inflate(R.layout.alert_show_qr, null, false);

        MaterialButton btn = view.findViewById(R.id.buttonOk);
        RoundedImageView img = view.findViewById(R.id.qr_code_imageview);
        TextView name = view.findViewById(R.id.qr_name);
        TextView helpText = view.findViewById(R.id.helptxt);
        TextView helpText2Tv = view.findViewById(R.id.helptxt2);
        name.setText(qrName);
        helpText.setText(helptxt);
        helpText2Tv.setText(helptxt2);
        helpText2Tv.setVisibility(View.VISIBLE);
        if (helptxt2.equals("")){
            helpText2Tv.setVisibility(View.GONE);
        }

        final android.app.AlertDialog finalAlertDialog = alertDialog;
        builder = new android.app.AlertDialog.Builder(mcntx);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(alertDialog.getWindow()).getAttributes());
        lp.width =  ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.x = 0;
        lp.y = 0;
        alertDialog.getWindow().setAttributes(lp);

        android.app.AlertDialog finalAlertDialog1 = alertDialog;

        tokenGenerate(qrString,img);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalAlertDialog1.dismiss();
                onButtonClick();
            }
        });

    }

    private void tokenGenerate(String qrString, ImageView imageView) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(qrString, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();

        }
    }
    public abstract void onButtonClick();
}
