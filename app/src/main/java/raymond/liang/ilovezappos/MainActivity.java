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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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

        setJobScheduler();
    }

    private void setJobScheduler() {
        ComponentName serviceName = new ComponentName(this, PriceAlertJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(120, serviceName)
                .setPeriodic(3600000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled successfully!");
        } else {
            Log.d(TAG, "Job not scheduled!");
        }
    }
}
