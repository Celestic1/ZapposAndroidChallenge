package raymond.liang.ilovezappos.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Transaction implements Parcelable {

    @SerializedName("date")
    private String date;

    @SerializedName("tid")
    private String tid;

    @SerializedName("price")
    private String price;

    @SerializedName("type")
    private String type;

    @SerializedName("amount")
    private String amount;

    public Transaction() {
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + date + '\'' +
                ", tid='" + tid + '\'' +
                ", price='" + price + '\'' +
                ", type='" + type + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    public Transaction(String date, String tid, String price, String type, String amount) {
        this.date = date;
        this.tid = tid;
        this.price = price;
        this.type = type;
        this.amount = amount;
    }

    protected Transaction(Parcel in) {
        date = in.readString();
        tid = in.readString();
        price = in.readString();
        type = in.readString();
        amount = in.readString();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public String getDate() {
        return date;
    }

    public String getTid() {
        return tid;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(tid);
        dest.writeString(price);
        dest.writeString(type);
        dest.writeString(amount);
    }
}
