package raymond.liang.ilovezappos.requests;

import raymond.liang.ilovezappos.util.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit retrofit =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static TransactionHistoryApi transactionHistoryApi = retrofit.create(TransactionHistoryApi.class);

    private static PriceAlertApi priceAlertApi = retrofit.create(PriceAlertApi.class);

    private static OrderBookApi orderBookApi = retrofit.create(OrderBookApi.class);

    public static TransactionHistoryApi getTransactionHistoryApi(){
        return transactionHistoryApi;
    }

    public static OrderBookApi getOrderBookApi() { return orderBookApi; }

    public static PriceAlertApi getPriceAlertApi() { return priceAlertApi; }
}
