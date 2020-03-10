package life.corals.merchant.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Objects;

import life.corals.merchant.R;
import life.corals.merchant.utils.AlertDialogFailure;
import life.corals.merchant.utils.AlertShowQr;

import static life.corals.merchant.utils.Constants.DISPLAY_BUSINESS_NAME;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;
import static life.corals.merchant.utils.Constants.MERCHANT_DEVICE_PREFERENCE;
import static life.corals.merchant.utils.Constants.QR_CODE;

public class QrActivity extends AppCompatActivity {


    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        Toolbar toolbar = findViewById(R.id.toolbar_qr);
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        preferences = getSharedPreferences(MERCHANT_DETAILS_PREFERENCE, MODE_PRIVATE);
        FrameLayout register = findViewById(R.id.register);

        FrameLayout appLink = findViewById(R.id.app_link_qr);

       String registerQR = "";
       String customerAppQR = "";
        if (!preferences.getString(QR_CODE, "").equals("") && !preferences.getString(DISPLAY_BUSINESS_NAME, "").equals("")) {
            registerQR = preferences.getString(QR_CODE, "");
            customerAppQR = "https://www.corals.life/get/?b=" + preferences.getString(DISPLAY_BUSINESS_NAME, "");
        } else {
            new AlertDialogFailure(this, "Please try again later", "OK", "Something went wrong") {
                @Override
                public void onButtonClick() {
                    startActivity(new Intent(QrActivity.this, Homenew.class));
                    finish();
                }
            };
        }


        String finalRegisterQR = registerQR;
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertShowQr(QrActivity.this, "Membership Registration QR","scan this QR using corals customer app to register as member","" ,finalRegisterQR) {
                    @Override
                    public void onButtonClick() {

                    }
                };
            }
        });

        String finalCustomerAppQR = customerAppQR;
        appLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertShowQr(QrActivity.this, "Customer App download QR", "scan this QR using your mobile","Alternatively you can search for corals life App in Play Store or App Store", finalCustomerAppQR) {
                    @Override
                    public void onButtonClick() {

                    }
                };
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(QrActivity.this, Homenew.class));
        finish();
    }
}
