package com.cottonclub.activities.production_manager_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cottonclub.R;
import com.cottonclub.activities.cutting_in_charge.CuttingInChargeViewJobCardDetails;
import com.cottonclub.models.JobCardItem;
import com.cottonclub.models.SizeListItem;
import com.cottonclub.utilities.Helper;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateProductionManagerRequest extends AppCompatActivity implements View.OnClickListener {

    private EditText etQuantityReceivedFromAdmin, etPrinterName, etPrinterIssueDate, etParts, etOtherParts,
            etPrinterReceiveDate, etApprovedQuantityIssuedToEmbroidery,
            etCurrentAlterQuantityAfterIssuedToMaker,
            etCurrentAlterQuantity, etEmbroiderName, etReceivedQuantityToEmbroidery, etApprovedQuantityIssuedToMaker,
            etMakerName, etMakerIssueDate, etJobCardNumber;

    private TextInputLayout tlNumbering;
    private Button btnUpdateJobCard, btnViewJobCardDetails;
    private Dialog mDialog;
    private DatePickerDialog datePickerDialog;
    private String[] partsArray;
    private LinearLayout llOtherParts;
    private boolean isOtherPartsDetailsVisible = false;
    private JobCardItem jobCardItem;
    private SizeListItem sizeListItem;
    private String getQuantity, getDesignCode, selectedBrand, selectedDesignType;
    private ArrayList<JobCardItem> jobCardList = new ArrayList<>();
    private int position;
    private TextView tvDateOrderCreation;
    private TextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_production_manager_request);
        initialise();
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

        etJobCardNumber = findViewById(R.id.etJobCardNumber);
        etJobCardNumber.setText(String.format("%s:%s", getString(R.string.job_card_number), jobCardItem.getJobCardNumber()));

        tvDateOrderCreation = findViewById(R.id.tvDateOrderCreation);
        tvDateOrderCreation.setText(Helper.getCurrentTime());

        etQuantityReceivedFromAdmin = findViewById(R.id.etQuantityReceivedFromAdmin);
        etQuantityReceivedFromAdmin.setText(getQuantity);
        etQuantityReceivedFromAdmin.setEnabled(false);

        etPrinterName = findViewById(R.id.etPrinterName);

        etPrinterIssueDate = findViewById(R.id.etPrinterIssueDate);
        etPrinterIssueDate.setOnClickListener(this);

        etParts = findViewById(R.id.etParts);
        etParts.setOnClickListener(this);

        llOtherParts = findViewById(R.id.llOtherParts);
        etOtherParts = findViewById(R.id.etOtherParts);

        etPrinterReceiveDate = findViewById(R.id.etPrinterReceiveDate);
        etPrinterReceiveDate.setOnClickListener(this);

        etApprovedQuantityIssuedToEmbroidery = findViewById(R.id.etApprovedQuantityIssuedToEmbroidery);
        etCurrentAlterQuantity = findViewById(R.id.etCurrentAlterQuantity);

        etApprovedQuantityIssuedToEmbroidery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!TextUtils.isEmpty(etQuantityReceivedFromAdmin.getText().toString()) &&

                        !TextUtils.isEmpty(etApprovedQuantityIssuedToEmbroidery.getText().toString())) {

                        int currentAlterAfterPrinting = Integer.parseInt(etQuantityReceivedFromAdmin.getText().toString().trim()) -
                                Integer.parseInt(etApprovedQuantityIssuedToEmbroidery.getText().toString().trim());
                        etCurrentAlterQuantity.setText(String.valueOf(currentAlterAfterPrinting));
                }
            }
        });

        etEmbroiderName = findViewById(R.id.etEmbroiderName);
        etReceivedQuantityToEmbroidery = findViewById(R.id.etReceivedQuantityToEmbroidery);
        etApprovedQuantityIssuedToMaker = findViewById(R.id.etApprovedQuantityIssuedToMaker);
        etCurrentAlterQuantityAfterIssuedToMaker = findViewById(R.id.etCurrentAlterQuantityAfterIssuedToMaker);
        etApprovedQuantityIssuedToMaker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(etReceivedQuantityToEmbroidery.getText().toString()) &&

                        !TextUtils.isEmpty(etApprovedQuantityIssuedToMaker.getText().toString())) {

                    int currentAlterAfterMaker = Integer.parseInt(etReceivedQuantityToEmbroidery.getText().toString().trim()) -
                            Integer.parseInt(etApprovedQuantityIssuedToMaker.getText().toString().trim());
                    etCurrentAlterQuantityAfterIssuedToMaker.setText(String.valueOf(currentAlterAfterMaker));
                }
            }
        });
        etMakerName = findViewById(R.id.etMakerName);

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
                datePickerDialog = new DatePickerDialog(CreateProductionManagerRequest.this,
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
                datePickerDialog = new DatePickerDialog(CreateProductionManagerRequest.this,
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
                datePickerDialog = new DatePickerDialog(CreateProductionManagerRequest.this,
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
                Helper.showDropDown(etParts, new ArrayAdapter<>(CreateProductionManagerRequest.this,
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

                Intent order_details_intent = new Intent(this, CuttingInChargeViewJobCardDetails.class);
                order_details_intent.putExtra("extraWithOrder", bundle);
                startActivity(order_details_intent);
                break;
        }
    }

    private void validate() {


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


        if (TextUtils.isEmpty(etApprovedQuantityIssuedToEmbroidery.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_shortage_quantity));
            etApprovedQuantityIssuedToEmbroidery.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(etMakerIssueDate.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_maker_issue_date));
            etMakerIssueDate.requestFocus();
            return;
        }

        updateJobCardByPM();

    }

    private void updateJobCardByPM() {

    }
}