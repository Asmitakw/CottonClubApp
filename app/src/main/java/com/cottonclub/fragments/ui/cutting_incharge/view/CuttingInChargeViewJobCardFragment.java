package com.cottonclub.fragments.ui.cutting_incharge.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cottonclub.R;
import com.cottonclub.activities.admin.ViewJobCardDetails;
import com.cottonclub.activities.cutting_in_charge.CuttingInChargeViewJobCardDetails;
import com.cottonclub.adapters.JobCardAdapter;
import com.cottonclub.interfaces.RecyclerViewClickListener;
import com.cottonclub.models.JobCardItem;
import com.cottonclub.utilities.Constants;
import com.cottonclub.utilities.Helper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CuttingInChargeViewJobCardFragment extends Fragment {

    private RecyclerView rvViewJobCard;
    private JobCardItem jobCardItem;
    private ArrayList<JobCardItem> jobCardList = new ArrayList<>();
    private JobCardAdapter jobCardAdapter;
    private Dialog mDialog;

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference ordersRef = mRootRef.child("JobCard");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_job_card, container, false);
        initialise(root);
        return root;
    }

    private void initialise(View view) {
        mDialog = Helper.showProgressDialog(getActivity());
        rvViewJobCard = view.findViewById(R.id.rvViewJobCard);
    }

    @Override
    public void onStart() {
        super.onStart();
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (jobCardList.size() == 0) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        jobCardItem = dataSnapshot.getValue(JobCardItem.class);
                        assert jobCardItem != null;
                        if(jobCardItem.getBrand().equals(Constants.KIDS_MAGIC)) {
                            jobCardList.add(jobCardItem);
                        }
                    }
                }
                jobCardAdapter = new JobCardAdapter(getActivity(), jobCardList, new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("jobCard", jobCardList.get(position));
                        bundle.putString("designCode", jobCardList.get(position).getDesignCode());
                        bundle.putParcelable("size", jobCardList.get(position).getSizeItem());

                        Intent order_details_intent = new Intent(getActivity(), CuttingInChargeViewJobCardDetails.class);
                        order_details_intent.putExtra("extraWithOrder", bundle);
                        startActivity(order_details_intent);

                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                rvViewJobCard.setLayoutManager(mLayoutManager);
                rvViewJobCard.setItemAnimator(new DefaultItemAnimator());
                rvViewJobCard.setAdapter(jobCardAdapter);

                mDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}