package raymond.liang.ilovezappos;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import raymond.liang.ilovezappos.jobmanager.PriceAlertJobService;

public class PriceAlertActivity extends AppCompatActivity {

    private EditText priceAlertEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_alert);

        priceAlertEditText = findViewById(R.id.priceAlertET);
        float val = PreferenceManager.getDefaultSharedPreferences(PriceAlertActivity.this).getFloat(PriceAlertJobService.ALERT_KEY, Float.MIN_VALUE);
        if (val != Float.MIN_VALUE) {
            priceAlertEditText.setText(String.valueOf(val));
        }
        Button priceAlertButton = findViewById(R.id.priceAlertButton);

        priceAlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(PriceAlertActivity.this);
                sharedPreferences.edit().putFloat(PriceAlertJobService.ALERT_KEY, Float.valueOf(priceAlertEditText.getText().toString())).apply();
            }
        });
    }
}
