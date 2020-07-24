package com.cottonclub.fragments.ui.alter_request;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.cottonclub.R;
import com.cottonclub.models.AlterRequestItem;
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


public class AlterRequestFragment extends Fragment implements View.OnClickListener {

    private EditText etDesignNumber, etAlterQuantity, etMasterName, etCuttingIssueDate;

    private TextView tvDateOrderCreation;

    private Button btnCreateAlterRequest;
    private long maxId = 0;
    private AlterRequestItem alterRequestItem;
    private Dialog mDialog;
    private DatePickerDialog datePickerDialog;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference alterRequestRef = mRootRef.child("AlterRequest");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_alter_request, container, false);
        initialise(root);
        return root;
    }

    private void initialise(View view) {

        tvDateOrderCreation = view.findViewById(R.id.tvDateOrderCreation);
        tvDateOrderCreation.setText(Helper.getCurrentTime());

        etDesignNumber = view.findViewById(R.id.etDesignNumber);
        etAlterQuantity = view.findViewById(R.id.etAlterQuantity);

        etMasterName = view.findViewById(R.id.etMasterName);

        etCuttingIssueDate = view.findViewById(R.id.etCuttingIssueDate);
        etCuttingIssueDate.setOnClickListener(this);

        btnCreateAlterRequest = view.findViewById(R.id.btnCreateAlterRequest);
        alterRequestItem = new AlterRequestItem();
    }

    @Override
    public void onStart() {
        super.onStart();
        btnCreateAlterRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        alterRequestRef.addValueEventListener(new ValueEventListener() {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
        }

    }

    private void validate() {
        if (TextUtils.isEmpty(etDesignNumber.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_design_number));
            etDesignNumber.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etAlterQuantity.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_alter_quantity));
            etAlterQuantity.requestFocus();
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

        sendAlterRequestDetails();
    }

    private void sendAlterRequestDetails() {
        mDialog = Helper.showProgressDialog(getActivity());
        String jobCardCreationDate = tvDateOrderCreation.getText().toString();
        String designNumber = etDesignNumber.getText().toString();
        String alterQuantity = etAlterQuantity.getText().toString();
        String masterName = etMasterName.getText().toString();
        String cuttingIssueDate = etCuttingIssueDate.getText().toString();

        alterRequestItem.setAlterRequestCreationDate(jobCardCreationDate);
        alterRequestItem.setDesignNumber(designNumber);
        alterRequestItem.setAlterQuantity(alterQuantity);
        alterRequestItem.setMaster(masterName);
        alterRequestItem.setCuttingIssueDate(cuttingIssueDate);

        alterRequestRef.child(String.valueOf(maxId + 1)).setValue(alterRequestItem,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        mDialog.dismiss();
                        Helper.showOkDialog(getActivity(), getString(R.string.alter_request_created_successfully));
                        etDesignNumber.setText("");
                        etAlterQuantity.setText("");
                        etMasterName.setText("");
                        etCuttingIssueDate.setText("");
                        etDesignNumber.requestFocus();
                    }
                });
    }


}