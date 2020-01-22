package raymond.liang.ilovezappos;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;


public class OrderBookAdapter extends RecyclerView.Adapter<OrderBookAdapter.MyViewHolder> {

    private List<String> orderBookList;
    private String type;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView orderBookInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderBookInfo = itemView.findViewById(R.id.orderBookInfo);
        }
    }

    public OrderBookAdapter(List<String> orderBookList, String type) {
        this.orderBookList = orderBookList;
        this.type = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orderbook_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        if(type.equals("bids")) {
            if (getItemViewType(i + 1) % 3 == 0) {
                myViewHolder.orderBookInfo.setTextColor(Color.GREEN);
                myViewHolder.orderBookInfo.setText(orderBookList.get(i));
            } else {
                myViewHolder.orderBookInfo.setText(orderBookList.get(i));
            }
        }
        else{
            if (getItemViewType(i) % 3 == 0) {
                myViewHolder.orderBookInfo.setTextColor(Color.RED);
                myViewHolder.orderBookInfo.setText(orderBookList.get(i));
            } else {
                myViewHolder.orderBookInfo.setText(orderBookList.get(i));
            }
        }

    }

    @Override
    public int getItemCount() {
        return orderBookList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

}
