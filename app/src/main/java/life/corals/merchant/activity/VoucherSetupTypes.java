package life.corals.merchant.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.Objects;
import life.corals.merchant.R;
import life.corals.merchant.utils.AppTimeOutManagerUtil;
import static life.corals.merchant.utils.Constants.CURRENCY_SYMBOL;
import static life.corals.merchant.utils.Constants.MERCHANT_DETAILS_PREFERENCE;

public class VoucherSetupTypes extends AppCompatActivity {

    CardView cardView_p, cardView_b, cardView_u, cardView_z, cardView_m;
    TextView tv_p, tv_b, tv_u, tv_z, tv_m;
    private SharedPreferences preferences;
    private AppTimeOutManagerUtil appTimeOutManagerUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher1);
        Toolbar toolbar = findViewById(R.id.toolbar_voucher_get_List);

        appTimeOutManagerUtil = new AppTimeOutManagerUtil(VoucherSetupTypes.this);

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
        cardView_p = (CardView) findViewById(R.id.cardview_P);
        cardView_b = (CardView) findViewById(R.id.cardview_B);
        cardView_u = (CardView) findViewById(R.id.cardview_U);
        cardView_z = (CardView) findViewById(R.id.cardview_Z);
        cardView_m = (CardView) findViewById(R.id.cardview_M);

        tv_p = (TextView) findViewById(R.id.text_desc_p);
        tv_b = (TextView) findViewById(R.id.text_desc_b);
        tv_u = (TextView) findViewById(R.id.text_desc_u);
        tv_z = (TextView) findViewById(R.id.text_desc_z);
        tv_m = (TextView) findViewById(R.id.text_desc_m);
        tv_p.setText("Customer can use this voucher to redeem your product/ service with reward points. \ne.g: Get "+preferences.getString(CURRENCY_SYMBOL,"")+"10 off on your next bill with 1000 points");
        tv_b.setText("Customer can top up their loyalty prepaid wallet using their reward points \ne.g: Redeem "+preferences.getString(CURRENCY_SYMBOL,"")+"10 worth of wallet balance for 1000 points");

        cardView_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupTypes.this, VoucherSetupCreate.class);
                in.putExtra("redeem_type", "P");
                in.putExtra("type_code", "1");
                in.putExtra("create_update_code", "1");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        cardView_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupTypes.this, VoucherSetupCreate.class);
                in.putExtra("redeem_type", "B");
                in.putExtra("type_code", "1");
                in.putExtra("create_update_code", "1");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);

            }
        });

        cardView_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupTypes.this, VoucherSetupCreate.class);
                in.putExtra("redeem_type", "U");
                in.putExtra("type_code", "1");
                in.putExtra("create_update_code", "1");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        cardView_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupTypes.this, VoucherSetupCreate.class);
                in.putExtra("redeem_type", "Z");
                in.putExtra("type_code", "1");
                in.putExtra("create_update_code", "1");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });

        cardView_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VoucherSetupTypes.this, VoucherSetupCreate.class);
                in.putExtra("redeem_type", "M");
                in.putExtra("type_code", "1");
                in.putExtra("create_update_code", "1");
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.swipe_in_right, R.anim.swipe_in_right);
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(VoucherSetupTypes.this, VoucherSetupHome.class));
        finish();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appTimeOutManagerUtil.onResumeApp();
    }

    @Override
    protected void onPause() {
        super.onPause();
        appTimeOutManagerUtil.onPauseApp();
    }
}
