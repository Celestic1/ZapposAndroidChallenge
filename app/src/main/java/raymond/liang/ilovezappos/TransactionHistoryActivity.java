package raymond.liang.ilovezappos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import raymond.liang.ilovezappos.models.Transaction;
import raymond.liang.ilovezappos.requests.ServiceGenerator;
import raymond.liang.ilovezappos.requests.TransactionHistoryApi;
import raymond.liang.ilovezappos.util.MyXAxisValueFormatter;
import raymond.liang.ilovezappos.util.MyYAxisValueFormatter;
import raymond.liang.ilovezappos.viewmodels.TransactionHistoryViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionHistoryActivity extends AppCompatActivity {

    private static final String TAG = "TransactionHistoryActiv";
    private String currency_pair = "btcusd";
    private LineChart chart;
    private List<Entry> entries;
    private TransactionHistoryViewModel transactionHistoryViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        setTitle("Transaction History - " + currency_pair);

        transactionHistoryViewModel = new ViewModelProvider(this.getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(TransactionHistoryViewModel.class);

        chart = findViewById(R.id.chart);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        entries = new ArrayList<>();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chart.removeAllViewsInLayout();
                entries.clear();
                createChart();
                retrofitRequest();
            }
        });

        swipeRefreshLayout.setRefreshing(true);
        createChart();
        retrofitRequest();

    }

    private void retrofitRequest() {

        TransactionHistoryApi transactionHistoryApi = ServiceGenerator.getTransactionHistoryApi();
        Call<List<Transaction>> call = transactionHistoryApi.getTransactionHistory(currency_pair);

        call.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                Log.d(TAG, "onResponse: " + response.toString());
                if(response.code() == 200){
                    Log.d(TAG, "onResponse: " + response.body());
                    transactionHistoryViewModel.setTransaction(response.body());
                    setData();

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

    private void setData(){
        for(Transaction transaction : transactionHistoryViewModel.getTransaction()){
            //Log.d(TAG, "transaction: " + transaction.toString());
            float price = Float.valueOf(transaction.getPrice());
            float newDate = Float.valueOf(transaction.getDate());
            entries.add(new Entry(newDate, price));
        }
        if(transactionHistoryViewModel.getTransaction() == null){
            retrofitRequest();
        } else {
            setDataOnChart(entries);
        }
    }

    private void createChart(){

        chart = findViewById(R.id.chart);
        chart.setAutoScaleMinMaxEnabled(true);

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(true);
        chart.setMaxHighlightDistance(300);

        XAxis x = chart.getXAxis();
        x.setEnabled(true);
        x.setLabelCount(7, true);
        x.setTextColor(Color.BLACK);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setDrawGridLines(true);
        x.setAxisLineColor(Color.RED);
        x.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v) {
                return new java.text.SimpleDateFormat("HH:mm", Locale.UK)
                        .format(new java.util.Date (Math.round(v)*1000L));
            }
        });

        YAxis y = chart.getAxisLeft();
        //y.setLabelCount(6, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(true);
        y.setAxisLineColor(Color.RED);

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);

        chart.animateXY(2000, 2000);

        // don't forget to refresh the drawing
        chart.invalidate();
    }

    private void setDataOnChart(final List<Entry> values) {
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");

        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(true);
        set1.setDrawCircles(false);
        set1.setLineWidth(2f);
        set1.setCircleRadius(4f);
        set1.setCircleColor(Color.GREEN);
        set1.setColor(Color.GREEN);
        set1.setFillColor(Color.GREEN);
        set1.setFillAlpha(100);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return chart.getAxisLeft().getAxisMinimum();
            }
        });

        // create a data object with the data sets
        LineData data = new LineData(set1);
        data.setValueTextSize(9f);
        data.setDrawValues(false);

        // set data
        chart.setData(data);
        chart.invalidate();
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Check if user triggered a refresh:
            case R.id.menu_refresh:
                Log.i(TAG, "Refresh menu item selected");
                swipeRefreshLayout.setRefreshing(true);

                // Signal SwipeRefreshLayout to start the progress indicator
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        chart.removeAllViewsInLayout();
                        entries.clear();
                        createChart();
                        retrofitRequest();
                    }
                }, 3000);

                // Start the refresh background task.
                // This method calls setRefreshing(false) when it's finished.

                return true;
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item);
    }
}
