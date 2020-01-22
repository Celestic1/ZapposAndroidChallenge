package raymond.liang.ilovezappos.models;

import com.google.gson.annotations.SerializedName;

public class PriceAlert {

    @SerializedName("volume")
    private String volume;

    @SerializedName("high")
    private String high;

    @SerializedName("last")
    private String last;

    @SerializedName("low")
    private String low;

    @SerializedName("vwap")
    private String vwap;

    @SerializedName("ask")
    private String ask;

    @SerializedName("bid")
    private String bid;

    @SerializedName("open")
    private String open;

    @SerializedName("timestamp")
    private String timestamp;

    public PriceAlert(String volume, String high, String last, String low, String vwap, String ask, String bid, String open, String timestamp) {
        this.volume = volume;
        this.high = high;
        this.last = last;
        this.low = low;
        this.vwap = vwap;
        this.ask = ask;
        this.bid = bid;
        this.open = open;
        this.timestamp = timestamp;
    }

    public String getVolume() {
        return volume;
    }

    public String getHigh() {
        return high;
    }

    public String getLast() {
        return last;
    }

    public String getLow() {
        return low;
    }

    public String getVwap() {
        return vwap;
    }

    public String getAsk() {
        return ask;
    }

    public String getBid() {
        return bid;
    }

    public String getOpen() {
        return open;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
