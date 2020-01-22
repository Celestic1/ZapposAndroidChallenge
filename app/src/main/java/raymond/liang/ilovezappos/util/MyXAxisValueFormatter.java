package raymond.liang.ilovezappos.util;


import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyXAxisValueFormatter extends ValueFormatter {

    private long referenceTimestamp; // minimum timestamp in your data set
    private DateFormat mDataFormat;
    private Date mDate;

    public MyXAxisValueFormatter() {
        this.mDataFormat = new SimpleDateFormat("mm:ss", Locale.UK);
        this.mDate = new Date();
    }

    @Override
    public String getFormattedValue(float value) {
        long convertedTimestamp = (long) value;
        long originalTimestamp = referenceTimestamp;
        return getHour(originalTimestamp);
    }

    private String getHour(long timestamp){
        try{
            mDate.setTime(timestamp*1000L);
            return mDataFormat.format(referenceTimestamp);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}
