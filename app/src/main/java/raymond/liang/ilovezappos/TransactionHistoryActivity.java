package raymond.liang.ilovezappos;

import android.app.Service;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import raymond.liang.ilovezappos.models.Transaction;
import raymond.liang.ilovezappos.requests.ServiceGenerator;
import raymond.liang.ilovezappos.requests.TransactionHistoryApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionHistoryActivity extends AppCompatActivity {

    private static final String TAG = "TransactionHistoryActiv";
    private LineChart chart;

    List<Entry> entries = new ArrayList<Entry>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        setTitle("Transaction History");

        retrofitRequest();


    }

    private void retrofitRequest() {

        TransactionHistoryApi transactionHistoryApi = ServiceGenerator.getTransactionHistoryApi();
        Call<List<Transaction>> call = transactionHistoryApi.getTransactionHistory("btcusd");

        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                Log.d(TAG, "onResponse: " + response.toString());
                if(response.code() == 200){
                    //Log.d(TAG, "onResponse: " + response.body());
                    List<Transaction> transactions = response.body();

                    for(Transaction transaction : transactions){
                        //Log.d(TAG, "transaction: " + transaction.toString());
                        //float price = Float.parseFloat(transaction.getPrice());
                        //float date = Float.parseFloat(transaction.getDate());
                        //entries.add(new Entry(date, price));
                    }
                }
                else{
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error fetching Transaction History data", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

}
