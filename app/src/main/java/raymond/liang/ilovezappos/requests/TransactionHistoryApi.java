package raymond.liang.ilovezappos.requests;

import java.util.List;

import raymond.liang.ilovezappos.models.Transaction;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TransactionHistoryApi {

    @GET("v2/transactions/{currency_pair}")
    Call<List<Transaction>> getTransactionHistory(
        @Path("currency_pair") String currency_pair // btcusd
    );
}
