package life.corals.merchant.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import life.corals.merchant.R;

public class PrivacyPolicy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_privacy_policy);
        Toolbar toolbar = findViewById(R.id.toolbar_privacy_policy);
        toolbar.setTitle("Privacy Policy");
        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(this.getResources().getDrawable(R.drawable.ic_arrow_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        TextView privacyPolicy = findViewById(R.id.privacy_policy_txtvw);
        privacyPolicy.setText(Html.fromHtml(getResources().getString(R.string.privacypolicylongvalue)));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PrivacyPolicy.this,Homenew.class));
        finish();
    }
}
