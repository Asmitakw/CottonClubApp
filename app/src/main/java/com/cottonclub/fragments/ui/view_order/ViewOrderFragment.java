package com.cottonclub.fragments.ui.view_order;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cottonclub.R;
import com.cottonclub.adapters.OrderAdapter;
import com.cottonclub.models.OrderItem;
import com.cottonclub.utilities.Helper;
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


public class ViewOrderFragment extends Fragment {

    private RecyclerView rvViewOrder;
    private OrderItem orderItem;
    private ArrayList<OrderItem> orderList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private Dialog mDialog;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference ordersRef = mRootRef.child("Orders");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_order, container, false);
        initialise(root);
        return root;
    }

    private void initialise(View view) {
        mDialog = Helper.showProgressDialog(getActivity());
        rvViewOrder = view.findViewById(R.id.rvViewOrder);
    }

    @Override
    public void onStart() {
        super.onStart();

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mDialog.dismiss();
                if (orderList.size() == 0) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        orderItem = dataSnapshot.getValue(OrderItem.class);
                        orderList.add(orderItem);
                    }
                }

                orderAdapter = new OrderAdapter(getActivity(), orderList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                rvViewOrder.setLayoutManager(mLayoutManager);
                rvViewOrder.setItemAnimator(new DefaultItemAnimator());
                rvViewOrder.setAdapter(orderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}