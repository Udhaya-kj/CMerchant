package life.corals.merchant.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import life.corals.merchant.R;

import static life.corals.merchant.utils.Constants.RESTAURANT_IMAGE_DOWNLOAD_URL_PREFIX;
import static life.corals.merchant.utils.Constants.RESTAURANT_IMAGE_DOWNLOAD_URL_SUFFIX_512_X_248;

public abstract class AlertProfileInfo {
    protected AlertProfileInfo(Context mcntx, String name, String id, String country, String admin) {
        AlertDialog(mcntx, name, id, country, admin);
    }

    private void AlertDialog(Context mcntx, String name, String id, String country, String admin) {
        android.app.AlertDialog.Builder builder;
        android.app.AlertDialog alertDialog = null;
        View view = LayoutInflater.from(mcntx).inflate(R.layout.alert_dioalog_profile_info, null, false);
        MaterialButton btnOK = view.findViewById(R.id.button_ok);
        ImageView profileImage = view.findViewById(R.id.profile_image);
        TextView nametv = view.findViewById(R.id.profile_name);
        TextView idtv = view.findViewById(R.id.profile_id);
        TextView countrytv = view.findViewById(R.id.profile_country);
        TextView admintv = view.findViewById(R.id.profile_admin);
        String name_s =name;
        String id_s = "Reference ID : " + id;
        if (country == null || country.isEmpty()) {
            countrytv.setVisibility(View.GONE);
        }

        String country_s = "Country : " + country;
        nametv.setText(name_s);
        idtv.setText(id_s);
        countrytv.setText(country_s);
        String adminstng ="";
        if (admin.equals("true")) {
            adminstng = "You are Admin";
        }else {
            admintv.setVisibility(View.GONE);
        }
        admintv.setText(adminstng);
        String IMAGE_DOWNLOAD_URL = RESTAURANT_IMAGE_DOWNLOAD_URL_PREFIX + id + RESTAURANT_IMAGE_DOWNLOAD_URL_SUFFIX_512_X_248;

        Glide.with(mcntx)
                .load(IMAGE_DOWNLOAD_URL)
                .centerCrop()
                .placeholder(mcntx.getResources().getDrawable(R.drawable.ic_icon_merchant))
                .into(profileImage);
     
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
    }

    private int getDisplayWidth(AlertDialog alertDialog) {
        int width = 300;
        if (alertDialog.getWindow() != null) {
            Display display = alertDialog.getWindow().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            if (size.x > 600) {
                width = size.x - 200;
            }
        }
        return width;
    }

    public abstract void onOKButtonClick();

}
