package com.cottonclub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cottonclub.R;
import com.cottonclub.interfaces.RecyclerViewClickListener;
import com.cottonclub.models.AlterRequestItem;
import com.cottonclub.models.OrderItem;
import com.cottonclub.utilities.Constants;

import java.util.List;


/**
 * Created by Asmita on 10-08-2016.
 */
public class AlterRequestAdapter extends RecyclerView.Adapter<AlterRequestAdapter.ViewHolder> {
    private List<AlterRequestItem> alterRequestList;
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener;

    public AlterRequestAdapter(Context context, List<AlterRequestItem> alterRequestList,
                               RecyclerViewClickListener recyclerViewClickListener) {
        this.alterRequestList = alterRequestList;
        this.context = context;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvViewDOCMonth, tvViewDOCDate, tvOrderNumber, tvBrandName, tvDesignNumber,
                tvParts, tvOtherParts, tvAlterQuantity, tvTotalPieces, tvCuttingIssueDate;
        CardView cvViewAlterRequestDetails;

        //set onclick listener to the entire view
        public ViewHolder(View view) {
            super(view);
            tvViewDOCMonth = view.findViewById(R.id.tvViewDOCMonth);
            tvViewDOCDate = view.findViewById(R.id.tvViewDOCDate);
            tvOrderNumber = view.findViewById(R.id.tvOrderNumber);
            tvBrandName = view.findViewById(R.id.tvBrandName);
            tvDesignNumber = view.findViewById(R.id.tvDesignNumber);
            tvParts = view.findViewById(R.id.tvParts);
            tvOtherParts = view.findViewById(R.id.tvOtherParts);
            tvAlterQuantity = view.findViewById(R.id.tvAlterQuantity);
            tvTotalPieces = view.findViewById(R.id.tvTotalPieces);
            tvCuttingIssueDate = view.findViewById(R.id.tvCuttingIssueDate);
            cvViewAlterRequestDetails = view.findViewById(R.id.cvViewAlterRequestDetails);
        }

    }

    //provide holder with onclick listener method.
    //here we perform our actions on Items of recycler view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_alter_request_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AlterRequestItem alterRequestItem = alterRequestList.get(position);
        String date = alterRequestItem.getAlterRequestDate();

        String day = date.substring(0, date.length() / 3);
        String month = date.substring(date.length() / 2);

        holder.tvViewDOCDate.setText(day);
        holder.tvViewDOCMonth.setText(month);

        holder.tvBrandName.setText(String.format("%s%s%s%s", context.getString(R.string.brand_name)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, alterRequestItem.getBrandName()));

        holder.tvDesignNumber.setText(String.format("%s%s%s%s", context.getString(R.string.design_number)
                , context.getString(R.string.colon), Constants.EMPTY_STRING,
                alterRequestItem.getDesignCode() + "-" + alterRequestItem.getDesignNumber()));

        holder.tvParts.setText(String.format("%s%s%s%s", context.getString(R.string.parts)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, alterRequestItem.getSelectedParts()));

        holder.tvAlterQuantity.setText(String.format("%s%s%s%s", context.getString(R.string.alter_quantity)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, alterRequestItem.getAlterQuantity()));

        holder.tvTotalPieces.setText(alterRequestItem.getTotalPieces());

        if (!alterRequestItem.getOtherParts().isEmpty()) {
            holder.tvOtherParts.setVisibility(View.VISIBLE);
            holder.tvOtherParts.setText(String.format("%s%s%s%s", context.getString(R.string.delivery_date)
                    , context.getString(R.string.colon), Constants.EMPTY_STRING, alterRequestItem.getOtherParts()));
        } else {
            holder.tvOtherParts.setVisibility(View.GONE);
        }
        holder.tvCuttingIssueDate.setText(String.format("%s%s%s%s", context.getString(R.string.cutting_issue_date)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, alterRequestItem.getCuttingIssueDate()));

        holder.cvViewAlterRequestDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewClickListener.onClick(view, position);

            }
        });
    }


    @Override
    public int getItemCount() {
        return alterRequestList.size();
    }
}
