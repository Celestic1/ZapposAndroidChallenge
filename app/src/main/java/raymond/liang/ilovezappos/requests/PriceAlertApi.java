package raymond.liang.ilovezappos.requests;

import raymond.liang.ilovezappos.models.PriceAlert;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PriceAlertApi {

    @GET("v2/ticker_hour/{currency_pair}")
    Call<PriceAlert> getPriceAlert(
            @Path("currency_pair") String currency_pair // btcusd
    );
}
