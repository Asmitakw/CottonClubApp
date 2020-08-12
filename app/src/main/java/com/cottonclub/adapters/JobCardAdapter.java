package com.cottonclub.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cottonclub.R;
import com.cottonclub.interfaces.RecyclerViewClickListener;
import com.cottonclub.models.JobCardItem;
import com.cottonclub.models.OrderItem;
import com.cottonclub.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Asmita on 10-08-2016.
 */
public class JobCardAdapter extends RecyclerView.Adapter<JobCardAdapter.ViewHolder> {
    private List<JobCardItem> jobCardList;
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener;
    boolean isImageShowing = true;

    public JobCardAdapter(Context context, List<JobCardItem> jobCardList,
                          RecyclerViewClickListener recyclerViewClickListener) {
        this.jobCardList = jobCardList;
        this.context = context;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJobCardNumber, tvBrandName, tvDesignNumber, tvQuantity, tvNoOfPieces,
                tvCallCuttingInCharge, tvJobCardReminder, tvViewDOCMonth, tvViewDOCDate;

        CardView cvJobCardDetails;

        ImageView ivJobCardFile;

        //set onclick listener to the entire view
        public ViewHolder(View view) {
            super(view);
            tvViewDOCMonth = view.findViewById(R.id.tvViewDOCMonth);
            tvViewDOCDate = view.findViewById(R.id.tvViewDOCDate);
            tvJobCardNumber = view.findViewById(R.id.tvJobCardNumber);
            tvBrandName = view.findViewById(R.id.tvBrandName);
            tvDesignNumber = view.findViewById(R.id.tvDesignNumber);
            tvQuantity = view.findViewById(R.id.tvQuantity);
            tvNoOfPieces = view.findViewById(R.id.tvNoOfPieces);
            tvCallCuttingInCharge = view.findViewById(R.id.tvCallCuttingInCharge);
            tvJobCardReminder = view.findViewById(R.id.tvJobCardReminder);
            ivJobCardFile = view.findViewById(R.id.ivJobCardFile);
            cvJobCardDetails = view.findViewById(R.id.cvJobCardDetails);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final JobCardItem jobCardItem = jobCardList.get(position);
        String date = jobCardItem.getJobCardDate();

        String day = date.substring(0, date.length() / 3);
        String month = date.substring(date.length() / 2);

        holder.tvViewDOCDate.setText(day);
        holder.tvViewDOCMonth.setText(month);

        holder.tvJobCardNumber.setText(String.format("%s%s%s%s", context.getString(R.string.job_card_number)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, jobCardItem.getJobCardNumber()));
        holder.tvBrandName.setText(String.format("%s%s%s%s", context.getString(R.string.brand_name)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, jobCardItem.getBrand()));
        holder.tvDesignNumber.setText(String.format("%s%s%s%s", context.getString(R.string.design_number)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, jobCardItem.getDesignCode() + "-" + jobCardItem.getDesignNumber()));
        holder.tvQuantity.setText(String.format("%s%s%s%s", context.getString(R.string.quantity)
                , context.getString(R.string.colon), Constants.EMPTY_STRING, jobCardItem.getQuantity()));
        holder.tvNoOfPieces.setText(jobCardItem.getTotalPieces());

        holder.cvJobCardDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewClickListener.onClick(view, position);
            }
        });

        Picasso.get()
                .load(jobCardItem.getJobCardFilePath())
                .placeholder(R.drawable.image_placeholder)
                .into(holder.ivJobCardFile);

        holder.ivJobCardFile.setOnClickListener(new clicker(position));

    }

    public class clicker implements View.OnClickListener {
        int position;

        public clicker(int pos) {
            position = pos;
        }

        @Override
        public void onClick(View v) {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));

            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));

            final JobCardItem jobCardItem = jobCardList.get(position);
            Picasso.get()
                    .load(jobCardItem.getJobCardFilePath())
                    .into(imageView);

            dialog.addContentView(imageView, new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            imageView.setPadding(50, 50, 50, 50);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(true);

            if (isImageShowing) {
                dialog.show();
                dialog.getWindow().setAttributes(lp);
                isImageShowing = false;
            }

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    dialogInterface.dismiss();
                    isImageShowing = true;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return jobCardList.size();
    }
}
