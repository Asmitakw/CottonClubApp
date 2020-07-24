package com.cottonclub.fragments.ui.view_job_card;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cottonclub.R;
import com.cottonclub.adapters.JobCardAdapter;
import com.cottonclub.models.JobCardItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ViewJobCardFragment extends Fragment {

    private RecyclerView rvViewJobCard;
    private JobCardItem jobCardItem;
    private ArrayList<JobCardItem> jobCardList = new ArrayList<>();
    private JobCardAdapter jobCardAdapter;

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference ordersRef = mRootRef.child("JobCard");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_job_card, container, false);
        initialise(root);
        return root;
    }

    private void initialise(View view) {
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
                        jobCardList.add(jobCardItem);
                    }
                }

                jobCardAdapter = new JobCardAdapter(getActivity(), jobCardList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                rvViewJobCard.setLayoutManager(mLayoutManager);
                rvViewJobCard.setItemAnimator(new DefaultItemAnimator());
                rvViewJobCard.setAdapter(jobCardAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}