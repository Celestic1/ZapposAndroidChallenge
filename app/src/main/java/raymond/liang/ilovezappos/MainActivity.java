package raymond.liang.ilovezappos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button transactionHistory = findViewById(R.id.transactionHistory);
        transactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TransactionHistoryActivity.class);
                startActivity(i);
            }
        });

        Button orderBook = findViewById(R.id.orderBook);
        orderBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OrderBookActivity.class);
                startActivity(i);
            }
        });

        Button priceAlert = findViewById(R.id.priceAlert);
        priceAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PriceAlertActivity.class);
                startActivity(i);
            }
        });
    }
}
