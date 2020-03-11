package life.corals.merchant.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import life.corals.merchant.R;

public class VoucherPerformanceActivity extends AppCompatActivity {

   private TextView tv_title, tv_redemptions, tv_sharing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_performance);

        Toolbar toolbar = findViewById(R.id.toolbar_voucher_performance);
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
        tv_title = (TextView) findViewById(R.id.text_title_perf);
        tv_redemptions = (TextView)findViewById(R.id.text_redemptions);
        tv_sharing =(TextView) findViewById(R.id.text_sharings);

        if (getIntent().getExtras() != null) {
            String title = getIntent().getStringExtra("title");
            tv_title.setText(title);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.swipe_in_left, R.anim.swipe_in_left);
    }
}
