package com.cottonclub.fragments.ui.admin.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cottonclub.R;
import com.cottonclub.activities.admin.ViewOrderDetails;
import com.cottonclub.adapters.OrderAdapter;
import com.cottonclub.interfaces.RecyclerViewClickListener;
import com.cottonclub.models.OrderItem;
import com.cottonclub.utilities.Constants;
import com.cottonclub.utilities.Helper;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ViewOrderFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rvViewOrder;
    private OrderItem orderItem;
    private ArrayList<OrderItem> orderList = new ArrayList<>();
    private ArrayList<OrderItem> filterList = new ArrayList<>();
    private ArrayList<OrderItem> partyList = new ArrayList<>();
    private ArrayList<OrderItem> filteredPartyList = new ArrayList<>();
    private ArrayList<String> hashPartyNameList = new ArrayList<>();
    private ArrayList<String> hashOrderNumberList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private Dialog mDialog;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference ordersRef = mRootRef.child("Orders");
    private EditText etBrandName, etFilter, etSubFilter, etOrderNumberFilter;
    private String[] filterArray;
    private String[] brandArray;
    private String selectedBrand;
    private String selectedPartyName;
    private String selectedOrderNumber;
    private long totalList;
    private LinearLayout llFilterLayout;
    private Menu customizedMenu;
    private boolean isFilterByBrand = false;
    private boolean isFilterByPartyName = false;
    private boolean isFilterByOrderNumber = false;
    private View viewOrderFilter, viewSubFilter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_order, container, false);
        setHasOptionsMenu(true);
        initialise(root);
        return root;
    }

    private void initialise(View view) {
        mDialog = Helper.showProgressDialog(getActivity());
        rvViewOrder = view.findViewById(R.id.rvViewOrder);
        etBrandName = view.findViewById(R.id.etBrandName);
        etBrandName.setOnClickListener(this);
        etFilter = view.findViewById(R.id.etFilter);
        etFilter.setOnClickListener(this);
        etSubFilter = view.findViewById(R.id.etSubFilter);
        etSubFilter.setOnClickListener(this);
        etOrderNumberFilter = view.findViewById(R.id.etOrderNumberFilter);
        etOrderNumberFilter.setOnClickListener(this);
        llFilterLayout = view.findViewById(R.id.llFilterLayout);
        viewOrderFilter = view.findViewById(R.id.viewOrderFilter);
        viewSubFilter = view.findViewById(R.id.viewSubFilter);

        if (brandArray == null)
            brandArray = getResources().getStringArray(R.array.brand);

        if (filterArray == null)
            filterArray = getResources().getStringArray(R.array.filter);

    }

    private void setAdapter() {
        orderList.clear();
        if (filterList.size() == 0) {
            Snackbar.make(getView(), getString(R.string.no_records), 3000).show();
        } else {
            Collections.reverse(filterList);
            orderAdapter = new OrderAdapter(getActivity(), filterList,  new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("order", filterList.get(position));
                    bundle.putString("designCode", filterList.get(position).getDesignCode());
                    bundle.putParcelable("size", filterList.get(position).getSizeItem());

                    Intent order_details_intent = new Intent(getActivity(), ViewOrderDetails.class);
                    order_details_intent.putExtra("extraWithOrder", bundle);
                    startActivity(order_details_intent);

                    clearData();
                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvViewOrder.setLayoutManager(mLayoutManager);
            rvViewOrder.setItemAnimator(new DefaultItemAnimator());
            rvViewOrder.setAdapter(orderAdapter);
        }

    }

    private void setPartyNameAdapter() {
        orderAdapter = new OrderAdapter(getActivity(), partyList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("order", partyList.get(position));
                bundle.putString("designCode", partyList.get(position).getDesignCode());
                bundle.putParcelable("size", partyList.get(position).getSizeItem());

                Intent order_details_intent = new Intent(getActivity(), ViewOrderDetails.class);
                order_details_intent.putExtra("extraWithOrder", bundle);
                startActivity(order_details_intent);

                clearData();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvViewOrder.setLayoutManager(mLayoutManager);
        rvViewOrder.setItemAnimator(new DefaultItemAnimator());
        rvViewOrder.setAdapter(orderAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                totalList = snapshot.getChildrenCount();
                if (orderList.size() == 0) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        orderItem = dataSnapshot.getValue(OrderItem.class);
                        orderList.add(orderItem);
                    }
                }
                Collections.reverse(orderList);

                orderAdapter = new OrderAdapter(getActivity(), orderList, new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("order", orderList.get(position));
                        bundle.putString("designCode", orderList.get(position).getDesignCode());
                        bundle.putParcelable("size", orderList.get(position).getSizeItem());

                        Intent order_details_intent = new Intent(getActivity(), ViewOrderDetails.class);
                        order_details_intent.putExtra("extraWithOrder", bundle);
                        startActivity(order_details_intent);

                        clearData();

                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                rvViewOrder.setLayoutManager(mLayoutManager);
                rvViewOrder.setItemAnimator(new DefaultItemAnimator());
                rvViewOrder.setAdapter(orderAdapter);
                if (isFilterByBrand) {
                    setFilterByBrand(selectedBrand);
                } else if (isFilterByPartyName) {
                    setFilterByPartyName(selectedPartyName);
                } else if (isFilterByOrderNumber) {
                    setFilterByOrderNumber(selectedOrderNumber);
                }
                mDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);
        customizedMenu = menu;
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void setFilterByBrand(String brandName) {
        isFilterByBrand = false;
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getBrandName().equals(brandName)) {

                filterList.add(orderList.get(i));
            }
        }
        filteredPartyList.addAll(filterList);
        setAdapter();
    }

    private void setFilterByPartyName(String partyName) {
        isFilterByPartyName = false;
        partyList.clear();

        for (int i = 0; i < filterList.size(); i++) {
            if (filterList.get(i).getPartyName().equals(partyName)) {

                partyList.add(filterList.get(i));
            }
        }
        setPartyNameAdapter();

    }

    private void setFilterByOrderNumber(String partyName) {
        isFilterByOrderNumber = false;
        partyList.clear();

        for (int i = 0; i < filteredPartyList.size(); i++) {
            if (filteredPartyList.get(i).getOrderNumber().equals(partyName)) {

                partyList.add(filteredPartyList.get(i));
            }
        }
        setPartyNameAdapter();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_menu:
                llFilterLayout.setVisibility(View.VISIBLE);
                customizedMenu.findItem(R.id.filter_menu).setVisible(false);
                customizedMenu.findItem(R.id.cancel_menu).setVisible(true);
                return true;

            case R.id.cancel_menu:
                llFilterLayout.setVisibility(View.GONE);
                customizedMenu.findItem(R.id.cancel_menu).setVisible(false);
                customizedMenu.findItem(R.id.filter_menu).setVisible(true);
                if (filterList.size() != 0) {
                    filterList.clear();
                }
                isFilterByBrand = false;
                isFilterByPartyName = false;
                isFilterByOrderNumber = false;
                onStart();
                etBrandName.setText("");
                etFilter.setText("");
                etSubFilter.setText("");
                etOrderNumberFilter.setText("");
                return true;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etBrandName:
                Helper.showDropDown(etBrandName, new ArrayAdapter<>(requireActivity(),
                        android.R.layout.simple_list_item_1, brandArray), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        etFilter.setText("");
                        etSubFilter.setText("");
                        etOrderNumberFilter.setText("");
                        etBrandName.setText(brandArray[position]);
                        if (position == 0) {
                            filterList.clear();
                            isFilterByBrand = true;
                            selectedBrand = Constants.KIDS_MAGIC;
                            onStart();
                        } else if (position == 1) {
                            filterList.clear();
                            isFilterByBrand = true;
                            selectedBrand = Constants.BBABY;
                            onStart();
                        } else if (position == 2) {

                            selectedBrand = Constants.COTTON_BLUE;
                            filterList.clear();
                            isFilterByBrand = true;
                            onStart();
                        }
                    }
                });
                break;

            case R.id.etFilter:
                Helper.showDropDown(etFilter, new ArrayAdapter<>(requireActivity(),
                        android.R.layout.simple_list_item_1, filterArray), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        etFilter.setText(filterArray[position]);
                        if (position == 0) {
                            isFilterByPartyName = true;
                            etSubFilter.setVisibility(View.VISIBLE);
                            viewSubFilter.setVisibility(View.VISIBLE);

                            etOrderNumberFilter.setVisibility(View.GONE);
                            viewOrderFilter.setVisibility(View.GONE);

                        } else if (position == 1) {
                            isFilterByOrderNumber = true;
                            etSubFilter.setVisibility(View.GONE);
                            viewSubFilter.setVisibility(View.GONE);

                            etOrderNumberFilter.setVisibility(View.VISIBLE);
                            viewOrderFilter.setVisibility(View.VISIBLE);
                        }

                    }
                });
                break;

            case R.id.etSubFilter:

                if (hashPartyNameList.size() == 0) {
                    HashSet hashSet = new HashSet();
                    for (int i = 0; i < filterList.size(); i++) {
                        OrderItem orderItem = filterList.get(i);
                        hashSet.add(orderItem.getPartyName());
                    }
                    hashPartyNameList.addAll(hashSet);
                }

                Helper.showDropDown(etSubFilter, new ArrayAdapter<>(requireActivity(),
                        android.R.layout.simple_list_item_1, hashPartyNameList), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        isFilterByPartyName = true;
                        etSubFilter.setText(hashPartyNameList.get(position));
                        selectedPartyName = etSubFilter.getText().toString();
                        hashPartyNameList.clear();
                        onStart();
                    }
                });
                break;

            case R.id.etOrderNumberFilter:

                if(hashOrderNumberList.size() == 0) {
                    HashSet order_hashSet = new HashSet();
                    for (int i = 0; i < filterList.size(); i++) {
                        OrderItem orderItem = filterList.get(i);
                        order_hashSet.add(orderItem.getOrderNumber());
                    }
                    hashOrderNumberList.addAll(order_hashSet);
                }
                Helper.showDropDown(etOrderNumberFilter, new ArrayAdapter<>(requireActivity(),
                        android.R.layout.simple_list_item_1, hashOrderNumberList), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        isFilterByOrderNumber = true;
                        etSubFilter.setText(hashOrderNumberList.get(position));
                        etOrderNumberFilter.setText(hashOrderNumberList.get(position));
                        selectedOrderNumber = etOrderNumberFilter.getText().toString();
                        hashOrderNumberList.clear();
                        onStart();
                    }
                });
                break;
        }
    }

    private void clearData() {
        llFilterLayout.setVisibility(View.GONE);
        customizedMenu.findItem(R.id.filter_menu).setVisible(true);
        customizedMenu.findItem(R.id.cancel_menu).setVisible(false);
        etBrandName.setText("");
        etFilter.setText("");
        etSubFilter.setText("");
        etOrderNumberFilter.setText("");
    }
}