package raymond.liang.ilovezappos;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        setTitle("Order Book");
        //orderBookViewModel = new ViewModelProvider(this.getViewModelStore(), ViewModelProvider.AndroidViewModelFactory).get(OrderBookViewModel.class);
        dateTextView = findViewById(R.id.dateTextView);
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        bidsRecyclerView = findViewById(R.id.bidsRecyclerView);
        asksRecyclerView = findViewById(R.id.asksRecyclerView);
        bidList = new ArrayList<>();
        askList = new ArrayList<>();

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bidsRecyclerView.removeAllViewsInLayout();
                asksRecyclerView.removeAllViewsInLayout();
                bidList.clear();
                askList.clear();
                createView();
            }
        });
        createView();

    }

    private void retrofitRequest(){
        swipeRefreshLayout.setRefreshing(true);
        OrderBookApi orderBookApi = ServiceGenerator.getOrderBookApi();
        Call<OrderBook> call = orderBookApi.getOrderBook("btcusd");

        call.enqueue(new Callback<OrderBook>() {
            @Override
            public void onResponse(Call<OrderBook> call, Response<OrderBook> response) {
                if(response.code() == 200){

                    OrderBook result = response.body();
                    int timeStamp = Integer.parseInt(result.getTimestamp());
                    String date = epochToDate(timeStamp);
                    dateTextView.setText(date);

                    //Log.d(TAG, "bids list size: " + result.getBidsListSize());
                    //Log.d(TAG, "asks list size: " + result.getAsksListSize());

                    for(List<String> bids : result.getBids()){
                        float price = Float.parseFloat(bids.get(0));
                        float amount = Float.parseFloat(bids.get(1));
                        bidList.add(Float.toString(price*amount));
                        bidList.add(bids.get(1));
                        bidList.add(bids.get(0));
                    }

                    //Log.d(TAG, "bidList size: " + bidList.size());
                    //Log.d(TAG, "bidList size: " + askList.size());

                    //Log.d(TAG, "bidList size: " + bidList);

                    for(List<String> asks : result.getAsks()){
                        askList.add(asks.get(0));
                        askList.add(asks.get(1));
                        float price = Float.parseFloat(asks.get(0));
                        float amount = Float.parseFloat(asks.get(1));
                        askList.add(Float.toString(price*amount));


                    }

                    //Log.d(TAG, "bidList size: " + askList);
                    bidsAdapter.notifyDataSetChanged();
                    asksAdapter.notifyDataSetChanged();
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

    private void createView(){
        retrofitRequest();
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
    }

    private String epochToDate(int epoch){
        String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.UK).format(new java.util.Date (epoch*1000L));
        return date + " (UTC-8)";
    }

}
