package com.cottonclub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cottonclub.R;
import com.cottonclub.interfaces.RecyclerViewClickListener;
import com.cottonclub.models.OrderItem;
import com.cottonclub.utilities.Constants;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Asmita on 10-08-2016.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<OrderItem> orderList;
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener;

    public OrderAdapter(Context context, List<OrderItem> orderList,
                        RecyclerViewClickListener recyclerViewClickListener) {
        this.orderList = orderList;
        this.context = context;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvViewDOCMonth, tvViewDOCDate, tvOrderNumber, tvBrand, tvPartyName, tvDesignNumber,
                tvDeliveryDate;
        CardView cvViewOrderDetails;

        //set onclick listener to the entire view
        public ViewHolder(View view) {
            super(view);
            tvViewDOCMonth = view.findViewById(R.id.tvViewDOCMonth);
            tvViewDOCDate = view.findViewById(R.id.tvViewDOCDate);
            tvOrderNumber = view.findViewById(R.id.tvOrderNumber);
            tvBrand = view.findViewById(R.id.tvBrand);
            tvDesignNumber = view.findViewById(R.id.tvDesignNumber);
            tvPartyName = view.findViewById(R.id.tvPartyName);
            tvDeliveryDate = view.findViewById(R.id.tvDeliveryDate);
            cvViewOrderDetails = view.findViewById(R.id.cvViewOrderDetails);
        }

    }

    //provide holder with onclick listener method.
    //here we perform our actions on Items of recycler view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_order_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrderItem orderItem = orderList.get(position);
        String date = orderItem.getOrderDate();

        String day = date.substring(0, date.length() / 3);
        String month = date.substring(date.length() / 2);

        holder.tvViewDOCDate.setText(day);
        holder.tvViewDOCMonth.setText(month);
        holder.tvOrderNumber.setText(String.format("%s%s%s%s", context.getString(R.string.order_number)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, orderItem.getOrderNumber()));
        holder.tvBrand.setText(String.format("%s%s%s%s", context.getString(R.string.brand_name)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, orderItem.getBrandName()));
        holder.tvPartyName.setText(String.format("%s%s%s%s", context.getString(R.string.party_name)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, orderItem.getPartyName()));
        holder.tvDesignNumber.setText(String.format("%s%s%s%s", context.getString(R.string.design_number)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, orderItem.getDesignCode() + orderItem.getDesignNumber()));
        holder.tvDeliveryDate.setText(String.format("%s%s%s%s", context.getString(R.string.delivery_date)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, orderItem.getDeliveryDate()));

        holder.cvViewOrderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewClickListener.onClick(view, position);

            }
        });
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
