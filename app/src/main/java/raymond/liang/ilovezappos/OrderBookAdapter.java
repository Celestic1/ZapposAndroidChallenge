package raymond.liang.ilovezappos;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;


public class OrderBookAdapter extends RecyclerView.Adapter<OrderBookAdapter.MyViewHolder> {

    private List<String> orderBookList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView orderBookInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderBookInfo = itemView.findViewById(R.id.orderBookInfo);
        }
    }

    public OrderBookAdapter(List<String> orderBookList) {
        this.orderBookList = orderBookList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orderbook_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.orderBookInfo.setText(orderBookList.get(i));
    }

    @Override
    public int getItemCount() {
        return orderBookList.size();
    }

}
