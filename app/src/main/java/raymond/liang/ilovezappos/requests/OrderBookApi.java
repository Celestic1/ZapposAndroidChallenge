package raymond.liang.ilovezappos.requests;

import raymond.liang.ilovezappos.models.OrderBook;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderBookApi {

    @GET("v2/order_book/{currency_pair}")
    Call<OrderBook> getOrderBook(
            @Path("currency_pair") String currency_pair // btcusd
    );
}
