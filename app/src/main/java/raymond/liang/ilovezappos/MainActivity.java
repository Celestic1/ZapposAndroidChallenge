package raymond.liang.ilovezappos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String spinnerSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button transactionHistory = findViewById(R.id.transactionHistory);
        transactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TransactionHistoryActivity.class);
                i.putExtra("currency_pair", spinnerSelection);
                startActivity(i);
            }
        });

        Button orderBook = findViewById(R.id.orderBook);
        orderBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OrderBookActivity.class);
                i.putExtra("currency_pair", spinnerSelection);
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

        Spinner spinner = findViewById(R.id.currency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.currency_pair,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerSelection = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), spinnerSelection, Toast.LENGTH_LONG);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        spinnerSelection = parent.getItemAtPosition(0).toString();
    }
}
