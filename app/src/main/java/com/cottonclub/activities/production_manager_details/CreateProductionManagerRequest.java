package com.cottonclub.activities.production_manager_details;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cottonclub.R;
import com.cottonclub.utilities.Helper;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class CreateProductionManagerRequest extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmployeeName, etPrinterName, etPrinterIssueDate, etParts, etOtherParts,
            etPrinterReceiveDate, etShortageQuantity, etApprovedQuantityToEmbroidery, etAlterQuantity,
            etEmbroideryQuantityRcv, etCheckerName, etTotApprovedQuantity, etMakerIssueDate;
    private TextInputLayout tlNumbering;
    private Button btnUpdateAlterRequest;
    private Dialog mDialog;
    private DatePickerDialog datePickerDialog;
    private String[] partsArray;
    private LinearLayout llOtherParts;
    private boolean isOtherPartsDetailsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_production_manager_request);
        initialise();
        tlNumbering = findViewById(R.id.tlNumbering);
    }

    private void initialise() {
        if (partsArray == null)
            partsArray = getResources().getStringArray(R.array.parts);

        etEmployeeName = findViewById(R.id.etEmployeeName);
        etPrinterName = findViewById(R.id.etPrinterName);
        etPrinterIssueDate = findViewById(R.id.etPrinterIssueDate);
        etParts = findViewById(R.id.etParts);
        etParts.setOnClickListener(this);

        llOtherParts = findViewById(R.id.llOtherParts);

        etOtherParts = findViewById(R.id.etOtherParts);
        etPrinterReceiveDate = findViewById(R.id.etPrinterReceiveDate);
        etShortageQuantity = findViewById(R.id.etShortageQuantity);
        etApprovedQuantityToEmbroidery = findViewById(R.id.etApprovedQuantityToEmbroidery);
        etAlterQuantity = findViewById(R.id.etAlterQuantity);
        etEmbroideryQuantityRcv = findViewById(R.id.etEmbroideryQuantityRcv);
        etCheckerName = findViewById(R.id.etCheckerName);
        etTotApprovedQuantity = findViewById(R.id.etTotApprovedQuantity);
        etMakerIssueDate = findViewById(R.id.etMakerIssueDate);

        btnUpdateAlterRequest = findViewById(R.id.btnUpdateAlterRequest);
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

    }
}