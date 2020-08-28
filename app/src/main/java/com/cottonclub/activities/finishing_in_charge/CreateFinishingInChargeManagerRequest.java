package com.cottonclub.activities.finishing_in_charge;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cottonclub.R;
import com.cottonclub.models.JobCardItem;
import com.cottonclub.models.SizeListItem;
import com.cottonclub.utilities.Helper;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateFinishingInChargeManagerRequest extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmployeeName, etPrinterName, etPrinterIssueDate, etParts, etOtherParts,
            etPrinterReceiveDate, etShortageQuantity, etApprovedQuantityToEmbroidery, etAlterQuantity,
            etEmbroideryQuantityRcv, etCheckerName, etTotApprovedQuantity, etMakerIssueDate,etJobCardNumber;
    private TextInputLayout tlNumbering;
    private Button btnUpdateJobCard,btnViewJobCardDetails;
    private Dialog mDialog;
    private DatePickerDialog datePickerDialog;
    private String[] partsArray;
    private LinearLayout llOtherParts;
    private boolean isOtherPartsDetailsVisible = false;
    private JobCardItem jobCardItem;
    private SizeListItem sizeListItem;
    private String getQuantity, getDesignCode,selectedBrand,selectedDesignType;
    private ArrayList<JobCardItem> jobCardList = new ArrayList<>();
    private int position;
    private TextView tvDateOrderCreation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_production_manager_request);
        initialise();
        tlNumbering = findViewById(R.id.tlNumbering);
    }

    private void initialise() {
        Bundle bundle = getIntent().getBundleExtra("extraWithOrder");
        if (bundle != null) {
            jobCardItem = bundle.getParcelable("jobCard");
            sizeListItem = bundle.getParcelable("size");
            getQuantity = jobCardItem.getQuantity();
            position = bundle.getInt("position");
            getDesignCode = bundle.getString("designCode");
            selectedBrand = jobCardItem.getBrand();
            selectedDesignType = sizeListItem.getDesignType();
        }
        jobCardList.add(jobCardItem);
        if (partsArray == null)
            partsArray = getResources().getStringArray(R.array.parts);

        tvDateOrderCreation = findViewById(R.id.tvDateOrderCreation);
        tvDateOrderCreation.setText(Helper.getCurrentTime());

        etEmployeeName = findViewById(R.id.etEmployeeName);
        etPrinterName = findViewById(R.id.etPrinterName);
        etPrinterIssueDate = findViewById(R.id.etPrinterIssueDate);
        etPrinterIssueDate.setOnClickListener(this);
        etParts = findViewById(R.id.etParts);
        etParts.setOnClickListener(this);
        etJobCardNumber = findViewById(R.id.etJobCardNumber);
        etJobCardNumber.setText(String.format("%s:%s", getString(R.string.job_card_number), jobCardItem.getJobCardNumber()));

        llOtherParts = findViewById(R.id.llOtherParts);

        etOtherParts = findViewById(R.id.etOtherParts);
        etPrinterReceiveDate = findViewById(R.id.etPrinterReceiveDate);
        etPrinterReceiveDate.setOnClickListener(this);
        etShortageQuantity = findViewById(R.id.etShortageQuantity);
        etApprovedQuantityToEmbroidery = findViewById(R.id.etApprovedQuantityToEmbroidery);
        etAlterQuantity = findViewById(R.id.etAlterQuantity);
        etEmbroideryQuantityRcv = findViewById(R.id.etEmbroideryQuantityRcv);
        etCheckerName = findViewById(R.id.etCheckerName);
        etTotApprovedQuantity = findViewById(R.id.etTotApprovedQuantity);
        etMakerIssueDate = findViewById(R.id.etMakerIssueDate);
        etMakerIssueDate.setOnClickListener(this);

        btnUpdateJobCard = findViewById(R.id.btnUpdateJobCard);
        btnUpdateJobCard.setOnClickListener(this);

        btnViewJobCardDetails = findViewById(R.id.btnViewJobCardDetails);
        btnViewJobCardDetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.etPrinterIssueDate:
                final Calendar c = Calendar.getInstance();
                int year, month, day;
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(CreateFinishingInChargeManagerRequest.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                etPrinterIssueDate.setText(dayOfMonth + "/" + (monthOfYear + 1)
                                        + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System
                        .currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;

            case R.id.etPrinterReceiveDate:
                final Calendar c1 = Calendar.getInstance();
                int year1, month1, day1;
                year1 = c1.get(Calendar.YEAR);
                month1 = c1.get(Calendar.MONTH);
                day1 = c1.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(CreateFinishingInChargeManagerRequest.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                etPrinterReceiveDate.setText(dayOfMonth + "/" + (monthOfYear + 1)
                                        + "/" + year);
                            }
                        }, year1, month1, day1);
                datePickerDialog.getDatePicker().setMinDate(System
                        .currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;

            case R.id.etMakerIssueDate:
                final Calendar c2 = Calendar.getInstance();
                int year2, month2, day2;
                year2 = c2.get(Calendar.YEAR);
                month2 = c2.get(Calendar.MONTH);
                day2 = c2.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(CreateFinishingInChargeManagerRequest.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                etMakerIssueDate.setText(dayOfMonth + "/" + (monthOfYear + 1)
                                        + "/" + year);
                            }
                        }, year2, month2, day2);
                datePickerDialog.getDatePicker().setMinDate(System
                        .currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;

            case R.id.etParts:
                Helper.showDropDown(etParts, new ArrayAdapter<>(CreateFinishingInChargeManagerRequest.this,
                        android.R.layout.simple_list_item_1, partsArray), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        etParts.setText(partsArray[position]);
                        if (position == 3) {
                            isOtherPartsDetailsVisible = true;
                            llOtherParts.setVisibility(View.VISIBLE);
                        } else {
                            llOtherParts.setVisibility(View.GONE);
                            isOtherPartsDetailsVisible = false;
                        }
                    }
                });
                break;

            case R.id.btnUpdateJobCard:
                validate();
                break;

            case R.id.btnViewJobCardDetails:
                Bundle bundle = new Bundle();
                bundle.putParcelable("jobCard", jobCardList.get(position));
                bundle.putString("designCode", getDesignCode);
                bundle.putParcelable("size", jobCardList.get(position).getSizeItem());
                bundle.putParcelable("fabricConsumed", jobCardList.get(position).getFabricListItem());

                Intent order_details_intent = new Intent(this, FinishingInChargeJobCardDetails.class);
                order_details_intent.putExtra("extraWithOrder", bundle);
                startActivity(order_details_intent);
                break;
        }
    }

    private void validate(){
        if (TextUtils.isEmpty(etEmployeeName.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_employee_name));
            etEmployeeName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etPrinterName.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_printer_name));
            etPrinterName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etPrinterIssueDate.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_printer_issue_Date));
            etPrinterIssueDate.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etParts.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_select_parts));
            etParts.requestFocus();
            return;
        }

        if (isOtherPartsDetailsVisible) {
            if (TextUtils.isEmpty(etOtherParts.getText().toString().trim())) {
                Helper.showOkDialog(this, getString(R.string.please_select_other_parts));
                etOtherParts.requestFocus();
                return;
            }
        }

        if (TextUtils.isEmpty(etPrinterReceiveDate.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_printer_rcv_date));
            etPrinterReceiveDate.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etShortageQuantity.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_shortage_quantity));
            etShortageQuantity.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etApprovedQuantityToEmbroidery.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_shortage_quantity));
            etApprovedQuantityToEmbroidery.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etAlterQuantity.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_alter_quantity));
            etAlterQuantity.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etEmbroideryQuantityRcv.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_embroidery_quantity_rcv));
            etEmbroideryQuantityRcv.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etCheckerName.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_checker_name));
            etCheckerName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etTotApprovedQuantity.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_tot_aprv_qty_to_embroidery));
            etTotApprovedQuantity.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etMakerIssueDate.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_maker_issue_date));
            etMakerIssueDate.requestFocus();
            return;
        }

        updateJobCardByPM();

    }

    private void updateJobCardByPM(){

    }
}