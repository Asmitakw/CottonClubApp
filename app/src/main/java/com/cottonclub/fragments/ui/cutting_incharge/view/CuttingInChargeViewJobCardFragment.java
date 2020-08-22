package com.cottonclub.fragments.ui.cutting_incharge.view;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.cottonclub.utilities.AppSession;
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


public class CuttingInChargeViewJobCardFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rvViewJobCard;
    private JobCardItem jobCardItem;
    private ArrayList<JobCardItem> jobCardList = new ArrayList<>();
    private JobCardAdapter jobCardAdapter;
    private Dialog mDialog;

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference ordersRef = mRootRef.child("JobCard");
    private Menu customizedMenu;
    private EditText etBrandName, etOrderNumberFilter;
    private String[] brandArray;
    private String selectedBrand;
    private String selectedOrderNumber;
    private boolean isFilterByBrand = false;
    private boolean isFilterByJobCardNumber = false;
    private ImageView ivOrderFilter;
    private LinearLayout llFilterLayout, llFilterBy;
    private ArrayList<JobCardItem> filterList = new ArrayList<>();
    private ArrayList<JobCardItem> partyList = new ArrayList<>();
    private View viewFilter;
    private boolean isFilterByOrderSearched = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_view_job_card, container, false);
        setHasOptionsMenu(true);
        initialise(root);
        return root;
    }

    private void initialise(View view) {
        mDialog = Helper.showProgressDialog(getActivity());
        rvViewJobCard = view.findViewById(R.id.rvViewJobCard);

        viewFilter = view.findViewById(R.id.viewFilter);
        viewFilter.setVisibility(View.GONE);
        ivOrderFilter = view.findViewById(R.id.ivOrderFilter);
        ivOrderFilter.setOnClickListener(this);
        etBrandName = view.findViewById(R.id.etBrandName);
        etBrandName.setVisibility(View.GONE);
        etOrderNumberFilter = view.findViewById(R.id.etOrderNumberFilter);
        etOrderNumberFilter.setOnClickListener(this);
        etOrderNumberFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isFilterByOrderSearched) {
                    if (etOrderNumberFilter.getText().toString().isEmpty()) {
                        isFilterByOrderSearched = false;
                        isFilterByBrand = true;
                        filterList.clear();
                        onStart();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        llFilterLayout = view.findViewById(R.id.llFilterLayout);
        llFilterBy = view.findViewById(R.id.llFilterBy);

        if (brandArray == null)
            brandArray = getResources().getStringArray(R.array.brand);
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
                        if (AppSession.getInstance().getSaveLoggedInUser(requireActivity()).equals(Constants.CUTTING_IN_CHARGE_KM)) {
                            if (jobCardItem.getBrand().equals(Constants.KIDS_MAGIC)) {
                                jobCardList.add(jobCardItem);
                            }
                        } else if (AppSession.getInstance().getSaveLoggedInUser(requireActivity()).equals(Constants.CUTTING_IN_CHARGE_BB)) {
                            if (jobCardItem.getBrand().equals(Constants.BBABY)) {
                                jobCardList.add(jobCardItem);
                            }
                        } else if (AppSession.getInstance().getSaveLoggedInUser(requireActivity()).equals(Constants.CUTTING_IN_CHARGE_CB)) {
                            if (jobCardItem.getBrand().equals(Constants.COTTON_BLUE)) {
                                jobCardList.add(jobCardItem);
                            }
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
                        bundle.putParcelable("fabricConsumed", jobCardList.get(position).getFabricListItem());

                        Intent order_details_intent = new Intent(getActivity(), CuttingInChargeViewJobCardDetails.class);
                        order_details_intent.putExtra("extraWithOrder", bundle);
                        startActivity(order_details_intent);

                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                rvViewJobCard.setLayoutManager(mLayoutManager);
                rvViewJobCard.setItemAnimator(new DefaultItemAnimator());
                rvViewJobCard.setAdapter(jobCardAdapter);
                if (isFilterByJobCardNumber) {
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

    private void setFilterByOrderNumber(String partyName) {
        isFilterByJobCardNumber = false;
        partyList.clear();

        for (int i = 0; i < jobCardList.size(); i++) {
            if (jobCardList.get(i).getJobCardNumber().equalsIgnoreCase(partyName)) {

                filterList.add(jobCardList.get(i));
            }
        }
        setPartyNameAdapter();

    }

    private void setPartyNameAdapter() {
        jobCardList.clear();
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
        Helper.hideKeyboardFrom(getActivity(), etOrderNumberFilter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_menu:
                llFilterLayout.setVisibility(View.VISIBLE);
                llFilterBy.setVisibility(View.VISIBLE);
                customizedMenu.findItem(R.id.filter_menu).setVisible(false);
                customizedMenu.findItem(R.id.cancel_menu).setVisible(true);
                return true;

            case R.id.cancel_menu:
                llFilterLayout.setVisibility(View.GONE);
                llFilterBy.setVisibility(View.GONE);
                customizedMenu.findItem(R.id.cancel_menu).setVisible(false);
                customizedMenu.findItem(R.id.filter_menu).setVisible(true);
                if (filterList.size() != 0) {
                    filterList.clear();
                }
                isFilterByBrand = false;
                isFilterByJobCardNumber = false;
                onStart();
                etBrandName.setText("");
                etOrderNumberFilter.setText("");
                etOrderNumberFilter.setText("");
                return true;

        }
        return super.onOptionsItemSelected(item);

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
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ivOrderFilter:
                isFilterByOrderSearched = true;
                if (etOrderNumberFilter.getText().toString().isEmpty()) {
                    Helper.showOkDialog(getActivity(), getString(R.string.please_enter_job_card_number));
                    return;
                }
                isFilterByJobCardNumber = true;
                selectedOrderNumber = etOrderNumberFilter.getText().toString();
                onStart();
                break;
        }

    }

    private void clearData() {
        llFilterLayout.setVisibility(View.GONE);
        llFilterBy.setVisibility(View.GONE);
        customizedMenu.findItem(R.id.filter_menu).setVisible(true);
        customizedMenu.findItem(R.id.cancel_menu).setVisible(false);
        etOrderNumberFilter.setText("");
    }
}