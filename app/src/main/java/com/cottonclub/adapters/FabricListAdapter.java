package com.cottonclub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cottonclub.R;
import com.cottonclub.interfaces.RecyclerViewClickListener;
import com.cottonclub.models.FabricListItem;
import com.cottonclub.utilities.AppSession;
import com.cottonclub.utilities.Constants;

import java.util.List;


/**
 * Created by Asmita on 10-08-2016.
 */
public class FabricListAdapter extends RecyclerView.Adapter<FabricListAdapter.ViewHolder> {
    private List<FabricListItem> fabricList;
    private Context context;

    public FabricListAdapter(Context context, List<FabricListItem> fabricList) {
        this.fabricList = fabricList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText etFabricItem, etFabricQuantity,etWastage,etWastageUnit,etWastagePercent;
        ImageView ivDelete;

        //set onclick listener to the entire view
        public ViewHolder(View view) {
            super(view);
            etFabricItem = view.findViewById(R.id.etFabricItem);
            etFabricQuantity = view.findViewById(R.id.etFabricQuantity);
            etWastage = view.findViewById(R.id.etWastage);
            etWastageUnit = view.findViewById(R.id.etWastageUnit);
            ivDelete = view.findViewById(R.id.ivDelete);
            etWastagePercent = view.findViewById(R.id.etWastagePercent);
        }

    }

    //provide holder with onclick listener method.
    //here we perform our actions on Items of recycler view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fabric_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final FabricListItem fabricListItem = fabricList.get(position);

        if (AppSession.getInstance().getIsUpdatedByCi(context).equals("true")) {

            if (AppSession.getInstance().getSaveLoggedInUser(context).equals(Constants.CUTTING_IN_CHARGE_KM)
                    || AppSession.getInstance().getSaveLoggedInUser(context).equals(Constants.CUTTING_IN_CHARGE_BB)
                    || AppSession.getInstance().getSaveLoggedInUser(context).equals(Constants.CUTTING_IN_CHARGE_CB)
                    || AppSession.getInstance().getSaveLoggedInUser(context).equals(Constants.PRODUCTION_MANAGER_KM)
                    || AppSession.getInstance().getSaveLoggedInUser(context).equals(Constants.PRODUCTION_MANAGER_BB)
                    || AppSession.getInstance().getSaveLoggedInUser(context).equals(Constants.PRODUCTION_MANAGER_CB)) {

                disableView(holder.etFabricItem);
                disableView(holder.etFabricQuantity);
                disableView(holder.etWastage);
                disableView(holder.etWastageUnit);
                holder.ivDelete.setEnabled(false);
                holder.etWastagePercent.setVisibility(View.VISIBLE);
                int wastage_percent = Math.round (Float.parseFloat((fabricListItem.getWastage()))
                / Float.parseFloat((fabricListItem.getFabricQuantity())) * 100);
                holder.etWastagePercent.setText(String.format("%s%s",
                        context.getString(R.string.wastage_percent), String.format("%s%s", String.valueOf(wastage_percent),
                        context.getString(R.string.percent))));
            }
        }
        holder.etFabricItem.setText(fabricListItem.getFabricCode());
        holder.etFabricQuantity.setText(String.format("%s%s", fabricListItem.getFabricQuantity(),
                fabricListItem.getFabricUnit()));
        holder.etWastage.setText(fabricListItem.getWastage());
        holder.etWastageUnit.setText(fabricListItem.getFabricUnit());

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabricList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    private void disableView(TextView view) {
        view.setEnabled(false);
        view.setTextColor(context.getResources().getColor(R.color.colorGreyDark));
    }

    @Override
    public int getItemCount() {
        return fabricList.size();
    }
}
