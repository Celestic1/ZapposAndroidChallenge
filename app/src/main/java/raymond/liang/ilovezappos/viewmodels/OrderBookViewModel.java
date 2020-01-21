package raymond.liang.ilovezappos.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import raymond.liang.ilovezappos.models.OrderBook;

public class OrderBookViewModel extends ViewModel {

    private MutableLiveData<List<OrderBook>> orderBooks = new MutableLiveData<>();

    public OrderBookViewModel() {
    }

    public LiveData<List<OrderBook>> getOrderBook(){
        return orderBooks;
    }
}
