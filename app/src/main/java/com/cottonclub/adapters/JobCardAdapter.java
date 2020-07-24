package com.cottonclub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cottonclub.R;
import com.cottonclub.models.JobCardItem;
import com.cottonclub.models.OrderItem;
import com.cottonclub.utilities.Constants;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Asmita on 10-08-2016.
 */
public class JobCardAdapter extends RecyclerView.Adapter<JobCardAdapter.ViewHolder> {
    private List<JobCardItem> jobCardList;
    private Context context;

    public JobCardAdapter(Context context, List<JobCardItem> jobCardList) {
        this.jobCardList = jobCardList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJobCardNumber, tvBrandName, tvDesignNumber, tvMasterName, tvCuttingIssueDate,
                tvCallCuttingInCharge, tvJobCardReminder;

        //set onclick listener to the entire view
        public ViewHolder(View view) {
            super(view);
            tvJobCardNumber = view.findViewById(R.id.tvJobCardNumber);
            tvBrandName = view.findViewById(R.id.tvBrandName);
            tvDesignNumber = view.findViewById(R.id.tvDesignNumber);
            tvMasterName = view.findViewById(R.id.tvMasterName);
            tvCuttingIssueDate = view.findViewById(R.id.tvCuttingIssueDate);
            tvCallCuttingInCharge = view.findViewById(R.id.tvCallCuttingInCharge);
            tvJobCardReminder = view.findViewById(R.id.tvJobCardReminder);
        }

    }

    //provide holder with onclick listener method.
    //here we perform our actions on Items of recycler view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_card_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final JobCardItem jobCardItem = jobCardList.get(position);
        holder.tvJobCardNumber.setText(String.format("%s%s%s%s", context.getString(R.string.job_card_number)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, jobCardItem.getJobCardNumber()));
        holder.tvBrandName.setText(String.format("%s%s%s%s", context.getString(R.string.brand_name)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, jobCardItem.getBrand()));
        holder.tvDesignNumber.setText(String.format("%s%s%s%s", context.getString(R.string.design_number)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, jobCardItem.getDesignNumber()));
        holder.tvMasterName.setText(String.format("%s%s%s%s", context.getString(R.string.master_name)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, jobCardItem.getMasterName()));
        holder.tvCuttingIssueDate.setText(String.format("%s%s%s%s", context.getString(R.string.cutting_issue_date)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, jobCardItem.getCuttingIssueDate()));

    }


    @Override
    public int getItemCount() {
        return jobCardList.size();
    }
}
