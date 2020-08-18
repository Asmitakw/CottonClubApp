package com.cottonclub.fragments.ui.admin.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cottonclub.R;
import com.cottonclub.activities.admin.ViewJobCardDetails;
import com.cottonclub.activities.admin.ViewOrderDetails;
import com.cottonclub.adapters.JobCardAdapter;
import com.cottonclub.adapters.OrderAdapter;
import com.cottonclub.interfaces.RecyclerViewClickListener;
import com.cottonclub.models.JobCardItem;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ViewJobCardFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rvViewJobCard;
    private JobCardItem jobCardItem;
    private ArrayList<JobCardItem> jobCardList = new ArrayList<>();
    private JobCardAdapter jobCardAdapter;
    private Dialog mDialog;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference ordersRef = mRootRef.child("JobCard");
    private LinearLayout llFilterLayout, llFilterBy;
    private ArrayList<JobCardItem> filterList = new ArrayList<>();
    private EditText etBrandName, etOrderNumberFilter;
    private String[] brandArray;
    private Menu customizedMenu;
    private String selectedBrand;
    private String selectedOrderNumber;
    private boolean isFilterByBrand = false;
    private boolean isFilterByOrderNumber = false;
    private ImageView ivOrderFilter;
    private ArrayList<JobCardItem> partyList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_job_card, container, false);
        initialise(root);
        setHasOptionsMenu(true);
        return root;
    }

    private void initialise(View view) {
        mDialog = Helper.showProgressDialog(getActivity());
        rvViewJobCard = view.findViewById(R.id.rvViewJobCard);

        etBrandName = view.findViewById(R.id.etBrandName);
        etBrandName.setOnClickListener(this);
        ivOrderFilter = view.findViewById(R.id.ivOrderFilter);
        ivOrderFilter.setOnClickListener(this);
        etOrderNumberFilter = view.findViewById(R.id.etOrderNumberFilter);
        etOrderNumberFilter.setOnClickListener(this);
        llFilterLayout = view.findViewById(R.id.llFilterLayout);
        llFilterBy = view.findViewById(R.id.llFilterBy);

        if (brandArray == null)
            brandArray = getResources().getStringArray(R.array.brand);

        etOrderNumberFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etOrderNumberFilter.getText().toString().isEmpty()) {
                    isFilterByOrderNumber = false;
                    onStart();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setAdapter() {
        jobCardList.clear();
        if (filterList.size() == 0) {
            Snackbar.make(getView(), getString(R.string.no_records), 3000).show();
        } else {
            Collections.reverse(filterList);
            jobCardAdapter = new JobCardAdapter(getActivity(), filterList, new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("jobCard", filterList.get(position));
                    bundle.putString("designCode", filterList.get(position).getDesignCode());
                    bundle.putParcelable("size", filterList.get(position).getSizeItem());

                    Intent order_details_intent = new Intent(getActivity(), ViewJobCardDetails.class);
                    order_details_intent.putExtra("extraWithOrder", bundle);
                    startActivity(order_details_intent);

                    clearData();
                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvViewJobCard.setLayoutManager(mLayoutManager);
            rvViewJobCard.setItemAnimator(new DefaultItemAnimator());
            rvViewJobCard.setAdapter(jobCardAdapter);
        }

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
                Collections.reverse(jobCardList);
                jobCardAdapter = new JobCardAdapter(getActivity(), jobCardList, new RecyclerViewClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("jobCard", jobCardList.get(position));
                        bundle.putString("designCode", jobCardList.get(position).getDesignCode());
                        bundle.putParcelable("size", jobCardList.get(position).getSizeItem());

                        Intent order_details_intent = new Intent(getActivity(), ViewJobCardDetails.class);
                        order_details_intent.putExtra("extraWithOrder", bundle);
                        startActivity(order_details_intent);

                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                rvViewJobCard.setLayoutManager(mLayoutManager);
                rvViewJobCard.setItemAnimator(new DefaultItemAnimator());
                rvViewJobCard.setAdapter(jobCardAdapter);
                if (isFilterByBrand) {
                    setFilterByBrand(selectedBrand);
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

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);
        customizedMenu = menu;
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void setFilterByBrand(String brandName) {
        isFilterByBrand = false;
        for (int i = 0; i < jobCardList.size(); i++) {
            if (jobCardList.get(i).getBrand().equals(brandName)) {

                filterList.add(jobCardList.get(i));
            }
        }
        setAdapter();
    }


    private void setFilterByOrderNumber(String partyName) {
        isFilterByOrderNumber = false;
        partyList.clear();

        for (int i = 0; i < filterList.size(); i++) {
            if (filterList.get(i).getJobCardNumber().equalsIgnoreCase(partyName)) {

                partyList.add(filterList.get(i));
            }
        }
        setPartyNameAdapter();

    }

    private void setPartyNameAdapter() {
        jobCardAdapter = new JobCardAdapter(getActivity(), partyList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("jobCard", jobCardList.get(position));
                bundle.putString("designCode", jobCardList.get(position).getDesignCode());
                bundle.putParcelable("size", jobCardList.get(position).getSizeItem());

                Intent order_details_intent = new Intent(getActivity(), ViewJobCardDetails.class);
                order_details_intent.putExtra("extraWithOrder", bundle);
                startActivity(order_details_intent);
                clearData();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvViewJobCard.setLayoutManager(mLayoutManager);
        rvViewJobCard.setItemAnimator(new DefaultItemAnimator());
        rvViewJobCard.setAdapter(jobCardAdapter);
        Helper.hideKeyboardFrom(getActivity(), etOrderNumberFilter);
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
                isFilterByOrderNumber = false;
                onStart();
                etBrandName.setText("");
                etOrderNumberFilter.setText("");
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
                        etOrderNumberFilter.setText("");
                        etOrderNumberFilter.setText("");
                        etBrandName.setText(brandArray[position]);
                        llFilterBy.setVisibility(View.VISIBLE);
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

            case R.id.ivOrderFilter:
                if (etOrderNumberFilter.getText().toString().isEmpty()) {
                    Helper.showOkDialog(getActivity(), getString(R.string.please_enter_job_card_number));
                    return;
                }
                isFilterByOrderNumber = true;
                selectedOrderNumber = etOrderNumberFilter.getText().toString();
                onStart();
                break;
        }
    }

    private void clearData() {
        llFilterLayout.setVisibility(View.GONE);
        customizedMenu.findItem(R.id.filter_menu).setVisible(true);
        customizedMenu.findItem(R.id.cancel_menu).setVisible(false);
        etBrandName.setText("");
        etOrderNumberFilter.setText("");
    }
}