package com.cottonclub.fragments.ui.view_alter_request;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cottonclub.R;
import com.cottonclub.activities.ViewAlterRequestDetails;
import com.cottonclub.activities.ViewOrderDetails;
import com.cottonclub.adapters.AlterRequestAdapter;
import com.cottonclub.interfaces.RecyclerViewClickListener;
import com.cottonclub.models.AlterRequestItem;
import com.cottonclub.utilities.Helper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewAlterRequestFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rvViewAlterRequest;
    private AlterRequestItem alterRequestItem;
    private ArrayList<AlterRequestItem> alterRequestList = new ArrayList<>();
    private AlterRequestAdapter alterRequestAdapter;
    private Dialog mDialog;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference alterRequestRef = mRootRef.child("AlterRequest");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_alter_request, container, false);
        setHasOptionsMenu(true);
        initialise(root);
        return root;
    }

    private void initialise(View view) {
        mDialog = Helper.showProgressDialog(getActivity());
        rvViewAlterRequest = view.findViewById(R.id.rvViewAlterRequest);
    }

    @Override
    public void onStart() {
        super.onStart();
        alterRequestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (alterRequestList.size() == 0) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        alterRequestItem = dataSnapshot.getValue(AlterRequestItem.class);
                        alterRequestList.add(alterRequestItem);
                    }
                }

                alterRequestAdapter = new AlterRequestAdapter(getActivity(), alterRequestList, new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("alter", alterRequestList.get(position));
                        bundle.putString("designCode", alterRequestList.get(position).getDesignCode());
                        bundle.putParcelable("size", alterRequestList.get(position).getSizeListItem());

                        Intent order_details_intent = new Intent(getActivity(), ViewAlterRequestDetails.class);
                        order_details_intent.putExtra("extraWithOrder", bundle);
                        startActivity(order_details_intent);

                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                rvViewAlterRequest.setLayoutManager(mLayoutManager);
                rvViewAlterRequest.setItemAnimator(new DefaultItemAnimator());
                rvViewAlterRequest.setAdapter(alterRequestAdapter);
                mDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onClick(View view) {


    }
}