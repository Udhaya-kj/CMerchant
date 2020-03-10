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

public class TermsandConditions extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_terms_conditions);

        Toolbar toolbar = findViewById(R.id.toolbar_terms_conditions);
        toolbar.setTitle("Terms & Conditions");

        toolbar.setTitleTextColor(getResources().getColor(R.color.matt_black));

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
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView termsAndConditions = findViewById(R.id.terms_and_conditions_txtvw);
        termsAndConditions.setText(Html.fromHtml(getResources().getString(R.string.termsconditonslongvalue)));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TermsandConditions.this, Homenew.class));
        finish();
    }
}
