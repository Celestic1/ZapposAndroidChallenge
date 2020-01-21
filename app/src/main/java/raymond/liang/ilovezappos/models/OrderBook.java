package raymond.liang.ilovezappos.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderBook {

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("bids")
    private List<List<String>> bids;

    @SerializedName("asks")
    private List<List<String>> asks;

    public OrderBook(){
    }

    public OrderBook(String timestamp, List<List<String>> bids, List<List<String>> asks) {
        this.timestamp = timestamp;
        this.bids = bids;
        this.asks = asks;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<List<String>> getBids() {
        return bids;
    }

    public List<List<String>> getAsks() {
        return asks;
    }

    public int getBidsListSize() { return bids.size(); }

    public int getAsksListSize() { return asks.size(); }

    @Override
    public String toString() {
        return "OrderBook{" +
                "timestamp='" + timestamp + '\'' +
                ", bids=" + bids +
                ", asks=" + asks +
                '}';
    }
}
