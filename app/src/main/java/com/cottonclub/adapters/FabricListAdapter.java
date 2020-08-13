package com.cottonclub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.cottonclub.R;
import com.cottonclub.interfaces.RecyclerViewClickListener;
import com.cottonclub.models.FabricListItem;

import java.util.List;


/**
 * Created by Asmita on 10-08-2016.
 */
public class FabricListAdapter extends RecyclerView.Adapter<FabricListAdapter.ViewHolder> {
    private List<FabricListItem> fabricList;
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener;

    public FabricListAdapter(Context context, List<FabricListItem> fabricList) {
        this.fabricList = fabricList;
        this.context = context;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText etFabricItem, etFabricQuantity;
        ImageView ivDelete;

        //set onclick listener to the entire view
        public ViewHolder(View view) {
            super(view);
            etFabricItem = view.findViewById(R.id.etFabricItem);
            etFabricQuantity = view.findViewById(R.id.etFabricQuantity);
            ivDelete = view.findViewById(R.id.ivDelete);
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

        holder.etFabricItem.setText(fabricListItem.getFabricCode());
        holder.etFabricQuantity.setText(fabricListItem.getFabricQuantity());
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabricList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return fabricList.size();
    }
}
