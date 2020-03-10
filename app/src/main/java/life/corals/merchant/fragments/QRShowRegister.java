package life.corals.merchant.fragments;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Objects;

import life.corals.merchant.R;

import static android.content.Context.MODE_PRIVATE;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.QR_CODE;

public class QRShowRegister extends BaseFragment {


    private SharedPreferences preferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_qr_show, container, false);
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        String title = "Membership Registration QR";
        String qr = preferences.getString(QR_CODE, "");
        ;
        String help = "scan this QR using corals customer app to register as member";


        RoundedImageView img = rootView.findViewById(R.id.qr_code_imageview);
        TextView name = rootView.findViewById(R.id.qr_name);
        TextView helpText = rootView.findViewById(R.id.helptxt);
        TextView helpText2Tv = rootView.findViewById(R.id.helptxt2);
        name.setText(title);
        helpText.setText(help);
        helpText2Tv.setVisibility(View.GONE);
        generateQR(qr, img);
        return rootView;
    }

    private void generateQR(String qrString, ImageView imageView) {
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

}
