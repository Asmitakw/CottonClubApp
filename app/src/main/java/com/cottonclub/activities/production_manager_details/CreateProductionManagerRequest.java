package com.cottonclub.activities.production_manager_details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cottonclub.R;
import com.cottonclub.activities.BaseActivity;
import com.cottonclub.activities.cutting_in_charge.CuttingInChargeViewJobCardDetails;
import com.cottonclub.interfaces.DialogListener;
import com.cottonclub.models.JobCardItem;
import com.cottonclub.models.ProductionManagerItem;
import com.cottonclub.models.SizeListItem;
import com.cottonclub.utilities.Helper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class CreateProductionManagerRequest extends AppCompatActivity implements View.OnClickListener {

    private EditText etQuantityReceivedFromAdmin, etPrinterName, etPrinterIssueDate, etParts, etOtherParts,
            etPrinterReceiveDate, etApprovedQuantityIssuedToEmbroidery,
            etCurrentAlterQuantityAfterIssuedToMaker,
            etCurrentAlterQuantity, etEmbroiderName, etPrintingReceivedQuantity,
            etReceivedQuantityToEmbroidery, etApprovedQuantityIssuedToMaker,
            etMakerName, etMakerIssueDate, etJobCardNumber, etAddNote;

    private Button btnUpdateJobCard, btnViewJobCardDetails;
    private Dialog mDialog;
    private DatePickerDialog datePickerDialog;
    private String[] partsArray;
    private LinearLayout llOtherParts;
    private boolean isOtherPartsDetailsVisible = false;
    private JobCardItem jobCardItem;
    private ProductionManagerItem productionManagerItem;
    private SizeListItem sizeListItem;
    private String getQuantity, getDesignCode, selectedBrand, selectedDesignType;
    private ArrayList<JobCardItem> jobCardList = new ArrayList<>();
    private int position;
    private TextView tvDateOrderCreation;
    private TextWatcher textWatcher;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference productionManagerJobCardRef = mRootRef.child("ProductionManager");
    private long maxId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_production_manager_request);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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

        etPrintingReceivedQuantity = findViewById(R.id.etPrintingReceivedQuantity);

        etApprovedQuantityIssuedToEmbroidery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!TextUtils.isEmpty(etPrintingReceivedQuantity.getText().toString()) &&

                        !TextUtils.isEmpty(etApprovedQuantityIssuedToEmbroidery.getText().toString())) {

                    int currentAlterAfterPrinting = Integer.parseInt(etPrintingReceivedQuantity.getText().toString().trim()) -
                            Integer.parseInt(etApprovedQuantityIssuedToEmbroidery.getText().toString().trim());
                    etCurrentAlterQuantity.setText((currentAlterAfterPrinting));
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

        etAddNote = findViewById(R.id.etAddNote);

        btnUpdateJobCard = findViewById(R.id.btnUpdateJobCard);
        btnUpdateJobCard.setOnClickListener(this);

        btnViewJobCardDetails = findViewById(R.id.btnViewJobCardDetails);
        btnViewJobCardDetails.setOnClickListener(this);

        productionManagerItem = new ProductionManagerItem();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (jobCardItem.getIsUpdatedByCuttingInCharge().equals("true")) {
            productionManagerJobCardRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        maxId = (dataSnapshot.getChildrenCount());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("TAG", "onCancelled", databaseError.toException());
                }
            });
        }
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
            Helper.showOkDialog(this, getString(R.string.please_enter_approved_quantity_issued_to_embroider));
            etApprovedQuantityIssuedToEmbroidery.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etEmbroiderName.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_embroider_name));
            etEmbroiderName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etReceivedQuantityToEmbroidery.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_received_quantity_to_embroider));
            etReceivedQuantityToEmbroidery.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etApprovedQuantityIssuedToMaker.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_approved_quantity_issued_to_maker));
            etApprovedQuantityIssuedToMaker.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etMakerName.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_marker_name));
            etMakerName.requestFocus();
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

        mDialog = Helper.showProgressDialog(this);

        SimpleDateFormat format = new SimpleDateFormat("d");
        format = new SimpleDateFormat("d MMM");
        final String currentDate = format.format(new Date());

        String pmUpdatedDate = currentDate;
        String printerName = etPrinterName.getText().toString();
        String printerIssueDate = etPrinterIssueDate.getText().toString();
        String parts = etParts.getText().toString();
        String otherParts = "";
        if (isOtherPartsDetailsVisible) {
            otherParts = etOtherParts.getText().toString();
        }
        String printerReceiveDate = etPrinterReceiveDate.getText().toString();
        String printerReceivedQuantity = etPrintingReceivedQuantity.getText().toString();
        String printerApprovedQuantityToEmbroidery = etApprovedQuantityIssuedToEmbroidery.getText().toString();
        String currentAlterAfterPrinter = etCurrentAlterQuantity.getText().toString();
        String embroiderName = etEmbroiderName.getText().toString();
        String receivedQuantityToEmbroidery = etReceivedQuantityToEmbroidery.getText().toString();
        String approvedQuantityIssuedToMaker = etApprovedQuantityIssuedToMaker.getText().toString();
        String currentAlterQuantityAfterEmbroidery = etCurrentAlterQuantityAfterIssuedToMaker.getText().toString();
        String makerName = etMakerName.getText().toString();
        String makerIssueDate = etMakerIssueDate.getText().toString();
        String additionalNote = etAddNote.getText().toString();

        productionManagerItem.setPrinterName(printerName);
        productionManagerItem.setPrinterIssueDate(printerIssueDate);
        productionManagerItem.setSelectParts(parts);
        productionManagerItem.setOtherParts(otherParts);
        productionManagerItem.setPrinterReceiveDate(printerReceiveDate);
        productionManagerItem.setPrintingReceiveQuantity(printerReceivedQuantity);
        productionManagerItem.setApprovedQuantityToEmbroidery(printerApprovedQuantityToEmbroidery);
        productionManagerItem.setCurrentAlterQuantityAfterPrinting(currentAlterAfterPrinter);
        productionManagerItem.setEmbroiderName(embroiderName);
        productionManagerItem.setReceivedQuantityToEmbroider(receivedQuantityToEmbroidery);
        productionManagerItem.setApprovedQuantityIssuedToMaker(approvedQuantityIssuedToMaker);
        productionManagerItem.setCurrentAlterQuantityAfterEmbroidery(currentAlterQuantityAfterEmbroidery);
        productionManagerItem.setMakerName(makerName);
        productionManagerItem.setMakerIssueDate(makerIssueDate);
        productionManagerItem.setNote(additionalNote);
        productionManagerItem.setIsUpdatedByProductionManager("true");

        productionManagerJobCardRef.child(String.valueOf(maxId + 1)).setValue(productionManagerItem, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                mDialog.dismiss();
               /* Helper.sendNotification(CreateProductionManagerRequest.this, getString(R.string.job_card_with)
                        + jobCardItem.getJobCardNumber()
                        + " "
                        + getString(R.string.updated)
                        + " "
                        + jobCardItem.getBrand(), String.valueOf(maxId));*/
                Helper.showOkClickDialog(CreateProductionManagerRequest.this, getString(R.string.job_card_updated_successfully), new DialogListener() {
                    @Override
                    public void onButtonClicked(int type) {
                        Intent homeIntent = new Intent(CreateProductionManagerRequest.this, BaseActivity.class);
                        startActivity(homeIntent);
                        finish();

                    }
                });

            }
        });
    }
}