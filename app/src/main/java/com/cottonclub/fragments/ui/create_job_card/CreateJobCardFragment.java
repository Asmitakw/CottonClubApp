package com.cottonclub.fragments.ui.create_job_card;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.cottonclub.R;
import com.cottonclub.models.JobCardItem;
import com.cottonclub.utilities.Helper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class CreateJobCardFragment extends Fragment implements View.OnClickListener {

    private EditText etBrandName, etDesignNumber, etQuantity, etJobCardNumber, etSelectSize,
            etFabricType, etFabricConsumed, etMasterName, etCuttingIssueDate;

    private TextView tvDateOrderCreation;

    private Button btnCreateJobCard;
    private long maxId = 0;
    private JobCardItem jobCardItem;
    private String[] brandArray;
    private String selectedBrand;
    private String[] kidsMagicSizesArray;
    private String[] bbabySizesArray;
    private String[] cottonBlueArray;
    private Dialog mDialog;
    private DatePickerDialog datePickerDialog;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference jobCardRef = mRootRef.child("JobCard");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_create_job_card, container, false);
        initialise(root);
        return root;
    }

    private void initialise(View view) {
        if (brandArray == null)
            brandArray = getResources().getStringArray(R.array.brand);

        if (kidsMagicSizesArray == null)
            kidsMagicSizesArray = getResources().getStringArray(R.array.kidsMagicSizes);

        if (bbabySizesArray == null)
            bbabySizesArray = getResources().getStringArray(R.array.bBabySizes);

        if (cottonBlueArray == null)
            cottonBlueArray = getResources().getStringArray(R.array.cottonBlueSizes);

        tvDateOrderCreation = view.findViewById(R.id.tvDateOrderCreation);
        tvDateOrderCreation.setText(Helper.getCurrentTime());

        etBrandName = view.findViewById(R.id.etBrandName);
        etBrandName.setOnClickListener(this);

        etDesignNumber = view.findViewById(R.id.etDesignNumber);
        etQuantity = view.findViewById(R.id.etQuantity);
        etJobCardNumber = view.findViewById(R.id.etJobCardNumber);

        etSelectSize = view.findViewById(R.id.etSelectSize);
        etSelectSize.setOnClickListener(this);

        etFabricType = view.findViewById(R.id.etFabricType);
        etFabricConsumed = view.findViewById(R.id.etFabricConsumed);

        etMasterName = view.findViewById(R.id.etMasterName);

        etCuttingIssueDate = view.findViewById(R.id.etCuttingIssueDate);
        etCuttingIssueDate.setOnClickListener(this);

        btnCreateJobCard = view.findViewById(R.id.btnCreateJobCard);
        jobCardItem = new JobCardItem();
    }

    @Override
    public void onStart() {
        super.onStart();
        btnCreateJobCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        jobCardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    maxId = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void validate() {
        if (TextUtils.isEmpty(etBrandName.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_brand_name));
            etBrandName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etDesignNumber.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_design_number));
            etDesignNumber.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etQuantity.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_quantity));
            etQuantity.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etJobCardNumber.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_job_card_number));
            etJobCardNumber.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etSelectSize.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_select_size));
            etSelectSize.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etFabricType.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_fabric_type));
            etFabricType.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etFabricConsumed.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_fabric_consumed));
            etFabricConsumed.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etMasterName.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_master_name));
            etMasterName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etCuttingIssueDate.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_cutting_issue_date));
            etCuttingIssueDate.requestFocus();
            return;
        }

        sendJobCardDetails();
    }

    private void sendJobCardDetails() {
        mDialog = Helper.showProgressDialog(getActivity());
        String jobCardCreationDate = tvDateOrderCreation.getText().toString();
        String brandName = etBrandName.getText().toString();
        String designNumber = etDesignNumber.getText().toString();
        String quantity = etQuantity.getText().toString();
        String jobCardNumber = etJobCardNumber.getText().toString();
        String selectSize = etSelectSize.getText().toString();
        String fabricType = etFabricType.getText().toString();
        String fabricConsumed = etFabricConsumed.getText().toString();
        String masterName = etMasterName.getText().toString();
        String cuttingIssueDate = etCuttingIssueDate.getText().toString();

        jobCardItem.setJobCardCreateDate(jobCardCreationDate);
        jobCardItem.setBrand(brandName);
        jobCardItem.setDesignNumber(designNumber);
        jobCardItem.setQuantity(quantity);
        jobCardItem.setJobCardNumber(jobCardNumber);
        jobCardItem.setSize(selectSize);
        jobCardItem.setFabricType(fabricType);
        jobCardItem.setFabricConsumed(fabricConsumed);
        jobCardItem.setMasterName(masterName);
        jobCardItem.setCuttingIssueDate(cuttingIssueDate);

        jobCardRef.child(String.valueOf(maxId + 1)).setValue(jobCardItem, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                mDialog.dismiss();
                Helper.showOkDialog(getActivity(), getString(R.string.job_card_created_successfully));
                etBrandName.setText("");
                etDesignNumber.setText("");
                etQuantity.setText("");
                etJobCardNumber.setText("");
                etSelectSize.setText("");
                etFabricType.setText("");
                etFabricConsumed.setText("");
                etMasterName.setText("");
                etCuttingIssueDate.setText("");
                etDesignNumber.requestFocus();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.etBrandName:
                Helper.showDropDown(etBrandName, new ArrayAdapter<>(requireActivity(),
                        android.R.layout.simple_list_item_1, brandArray), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        etBrandName.setText(brandArray[position]);
                        etSelectSize.setText("");
                        if (position == 0) {
                            selectedBrand = "KidsMagic";
                        } else if (position == 1) {
                            selectedBrand = "BBaby";
                        } else {
                            selectedBrand = "CottonBlue";
                        }
                    }
                });
                break;

            case R.id.etCuttingIssueDate:
                final Calendar c = Calendar.getInstance();
                int year, month, day;
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(requireActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                etCuttingIssueDate.setText(dayOfMonth + "/" + (monthOfYear + 1)
                                        + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System
                        .currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;

            case R.id.etSelectSize:
                if (!selectedBrand.equals("")) {
                    if (selectedBrand.equals("KidsMagic")) {
                        //Kids Magic
                        Helper.showDropDown(etSelectSize, new ArrayAdapter<>(requireActivity(),
                                android.R.layout.simple_list_item_1, kidsMagicSizesArray), new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                etSelectSize.setText(kidsMagicSizesArray[position]);
                            }
                        });
                    } else if (selectedBrand.equals("BBaby")) {
                        //BBbay
                        Helper.showDropDown(etSelectSize, new ArrayAdapter<>(requireActivity(),
                                android.R.layout.simple_list_item_1, bbabySizesArray), new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                etSelectSize.setText(bbabySizesArray[position]);
                            }
                        });
                    } else {
                        //Cotton Blue
                        Helper.showDropDown(etSelectSize, new ArrayAdapter<>(requireActivity(),
                                android.R.layout.simple_list_item_1, cottonBlueArray), new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                etSelectSize.setText(cottonBlueArray[position]);
                            }
                        });
                    }
                }


                break;
        }


    }
}