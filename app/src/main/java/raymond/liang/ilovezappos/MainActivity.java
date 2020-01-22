package raymond.liang.ilovezappos;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import raymond.liang.ilovezappos.jobmanager.PriceAlertJobService;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
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
                i.putExtra("currency_pair", spinnerSelection);
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

        setJobScheduler();
    }

    private void setJobScheduler() {
        ComponentName serviceName = new ComponentName(this, PriceAlertJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(120, serviceName)
                .setPeriodic(1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled successfully!");
        } else {
            Log.d(TAG, "Job not scheduled!");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerSelection = parent.getItemAtPosition(position).toString();
        if (spinnerSelection.equals("Select Currency")){
            spinnerSelection = "btcusd";
        }
        Toast.makeText(parent.getContext(), spinnerSelection, Toast.LENGTH_LONG);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        spinnerSelection = parent.getItemAtPosition(1).toString();
    }
}
