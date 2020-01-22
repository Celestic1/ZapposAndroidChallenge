package raymond.liang.ilovezappos.viewmodels;

import androidx.lifecycle.ViewModel;
import raymond.liang.ilovezappos.models.OrderBook;

public class OrderBookViewModel extends ViewModel {

    private OrderBook orderBook;

    public OrderBookViewModel() {
    }

    public OrderBook getOrderBook(){
        return orderBook;
    }

    public void setOrderBook(OrderBook orderBook){
        this.orderBook = orderBook;
    }
}
