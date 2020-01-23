package raymond.liang.ilovezappos;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import raymond.liang.ilovezappos.models.OrderBook;
import raymond.liang.ilovezappos.requests.OrderBookApi;
import raymond.liang.ilovezappos.requests.ServiceGenerator;
import raymond.liang.ilovezappos.viewmodels.OrderBookViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderBookActivity extends AppCompatActivity {

    private static final String TAG = "OrderBookActivity";
    private String currency_pair = "btcusd";
    private TextView dateTextView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<String> bidList;
    private List<String> askList;
    private RecyclerView bidsRecyclerView;
    private RecyclerView asksRecyclerView;
    private OrderBookAdapter bidsAdapter;
    private OrderBookAdapter asksAdapter;
    private OrderBookViewModel orderBookViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_book);

        getSupportActionBar().setElevation(0);

        setTitle("Order Book - " + currency_pair);

        orderBookViewModel = new ViewModelProvider(this.getViewModelStore(),
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(OrderBookViewModel.class);

        dateTextView = findViewById(R.id.dateTextView);
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        bidsRecyclerView = findViewById(R.id.bidsRecyclerView);
        asksRecyclerView = findViewById(R.id.asksRecyclerView);


        bidList = new ArrayList<>();
        askList = new ArrayList<>();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bidsRecyclerView.removeAllViewsInLayout();
                asksRecyclerView.removeAllViewsInLayout();
                bidList.clear();
                askList.clear();
                retrofitRequest();
            }
        });

        swipeRefreshLayout.setRefreshing(true);
        createView();

    }

    private void retrofitRequest(){

        OrderBookApi orderBookApi = ServiceGenerator.getOrderBookApi();
        Call<OrderBook> call = orderBookApi.getOrderBook(currency_pair);

        call.enqueue(new Callback<OrderBook>() {
            @Override
            public void onResponse(Call<OrderBook> call, Response<OrderBook> response) {
                if(response.code() == 200){
                    orderBookViewModel.setOrderBook(response.body());
                    setBidsAndAsksFromOrderBook();
                }
                else{
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<OrderBook> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error fetching Order Book data", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void setBidsAndAsksFromOrderBook() {
        if(swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        int timeStamp = Integer.parseInt(orderBookViewModel.getOrderBook().getTimestamp());
        String date = epochToDate(timeStamp);
        dateTextView.setText(date);

        for(List<String> bids : orderBookViewModel.getOrderBook().getBids()){
            float price = Float.parseFloat(bids.get(0));
            float amount = Float.parseFloat(bids.get(1));
            bidList.add(Float.toString(price*amount));
            bidList.add(bids.get(1));
            bidList.add(bids.get(0));
        }

        for(List<String> asks : orderBookViewModel.getOrderBook().getAsks()){
            askList.add(asks.get(0));
            askList.add(asks.get(1));
            float price = Float.parseFloat(asks.get(0));
            float amount = Float.parseFloat(asks.get(1));
            askList.add(Float.toString(price*amount));
        }
        bidsAdapter.notifyDataSetChanged();
        asksAdapter.notifyDataSetChanged();
    }

    private void createView(){
        GridLayoutManager bidsGridLayoutManager = new GridLayoutManager(
                this, 3);

        // bids recycler view
        bidsRecyclerView.setLayoutManager(bidsGridLayoutManager);
        bidsRecyclerView.setHasFixedSize(true);
        bidsRecyclerView.setItemViewCacheSize(20);
        bidsAdapter = new OrderBookAdapter(bidList, "bids");
        bidsRecyclerView.setAdapter(bidsAdapter);

        // asks recycler view
        GridLayoutManager asksGridLayoutManager = new GridLayoutManager(
                this, 3);
        asksRecyclerView.setLayoutManager(asksGridLayoutManager);
        asksRecyclerView.setHasFixedSize(true);
        asksRecyclerView.setItemViewCacheSize(20);
        asksAdapter = new OrderBookAdapter(askList, "asks");
        asksRecyclerView.setAdapter(asksAdapter);

        if(swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

        if (orderBookViewModel.getOrderBook() == null) {
            retrofitRequest();
        } else {
            setBidsAndAsksFromOrderBook();
        }
    }

    private String epochToDate(int epoch){
        String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.UK).format(new java.util.Date (epoch*1000L));
        return date + " (UTC-8)";
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
                        retrofitRequest();
                    }
                }, 1000);

                // Start the refresh background task.
                // This method calls setRefreshing(false) when it's finished.

                return true;
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item);
    }

}
