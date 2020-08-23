package com.cottonclub.activities.cutting_in_charge;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cottonclub.R;
import com.cottonclub.activities.BaseActivity;
import com.cottonclub.adapters.FabricListAdapter;
import com.cottonclub.interfaces.DialogListener;
import com.cottonclub.models.FabricListItem;
import com.cottonclub.models.JobCardItem;
import com.cottonclub.models.SizeListItem;
import com.cottonclub.utilities.AppSession;
import com.cottonclub.utilities.Constants;
import com.cottonclub.utilities.Helper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class CuttingInChargeViewJobCardDetails extends AppCompatActivity implements View.OnClickListener {

    private EditText etFabricType, etBrandName, etDesignNumber, etJobCardNumber,
            etCuttingIssueDate, etSelectSize, etMasterName, etQuantity;

    private TextView tvDateOrderCreation;

    private JobCardItem jobCardItem;
    private SizeListItem sizeListItem;
    private FabricListItem fabricListItem;
    private String[] brandArray;
    private String selectedBrand;
    private String selectedDesignType;
    private String[] typeArray;
    private String[] pantArray;
    private String[] unitArray;
    private String[] fabricTypeArray;
    private String[] kidsMagicDesignTypeArray;
    private String[] bBabyDesignTypeArray;
    private String[] cottonBlueDesignTypeArray;
    private Dialog mDialog;
    private ArrayList<CharSequence> selectedFabricType = new ArrayList<>();
    private DatePickerDialog datePickerDialog;
    private LinearLayout llBBabyS3XLParent, llBBabyNB912Parent, llKidsMagicMN,
            llKidsMagicNumeric, llKidsMagicGT, llKMS, llSizeS, llKMSGirls, llFileLayout;
    private EditText etKidsMagicMNSize2, etKidsMagicMNSize3, etKidsMagicMNSize4, etKidsMagicMNSize5,
            etKidsMagicMNSize6, etKidsMagicNumeric8, etKidsMagicNumeric10,
            etKidsMagicNumeric12, etKidsMagicNumeric14, etKidsMagicNumeric16, etKidsMagicGT20,
            etKidsMagicGT22, etKidsMagicGT24, etKidsMagicGT26, etKidsMagicGT28, etKidsMagicGT30,
            etKidsMagicGT32, etKidsMagicGT34, etKidsMagicGT36, etKMS1, etKMS2, etKMS3, etKMS4, etKMS5,
            etKMSG1, etKMSG2, etKMSG3, etKMSG4, etKMSG5,
            etTotalNumberPieces, etBbabySizeS, etBbabySizeM, etBbabySizeL, etBbabySizeXL, etBbabySizeXXL,
            etBbabySizeXXXL, etBbabyNB, etBbaby03, etBbaby36, etBbaby69, etBbaby912, etDesignCode;
    private boolean isKidsMagicP = false;
    private boolean isSizeChartVisible = false;
    private boolean isClicked = false;
    private TextWatcher textWatcher;
    private View viewS;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference cuttingInChargeJobCardRef = mRootRef.child("JobCard");
    private DatabaseReference parentRef;

    private String getQuantity, getDesignCode;
    private Menu customizedMenu;
    private String designCode;
    private ImageView ivJobCardFile, ivCancelFile, ivUploadFile;
    private Button btnCreateJobCard, btnAddItem;
    private EditText etFabricItem, etFabricQuantity, etFabricCodeUnit, etCuttingCompleteDate,
            etWastage, etWastageUnit;
    private ArrayList<FabricListItem> fabricCodeList = new ArrayList<FabricListItem>();
    private RecyclerView rvFabricItem;
    private FabricListAdapter fabricListAdapter;
    private boolean isImageShowing = true;
    private long maxId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutting_in_charge_job_card);
        setTitle(getString(R.string.view_job_card_details));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        AppSession.getInstance()
                .saveCuttingInChargeUpdate(CuttingInChargeViewJobCardDetails.this,"false");
        initialise();
        setValues();
        setViewsDisabled();
    }

    private void initialise() {
        Bundle bundle = getIntent().getBundleExtra("extraWithOrder");
        if (bundle != null) {
            jobCardItem = bundle.getParcelable("jobCard");
            sizeListItem = bundle.getParcelable("size");
            getQuantity = jobCardItem.getQuantity();
            getDesignCode = bundle.getString("designCode");
            selectedBrand = jobCardItem.getBrand();
            selectedDesignType = sizeListItem.getDesignType();
        }
        parentRef = cuttingInChargeJobCardRef.child(jobCardItem.getJobCardId()).child("Fabric Consumed");
        if (brandArray == null)
            brandArray = getResources().getStringArray(R.array.brand);

        if (typeArray == null)
            typeArray = getResources().getStringArray(R.array.type);

        if (pantArray == null)
            pantArray = getResources().getStringArray(R.array.pant);

        if (unitArray == null)
            unitArray = getResources().getStringArray(R.array.units);

        if (fabricTypeArray == null)
            fabricTypeArray = getResources().getStringArray(R.array.fabricType);

        if (kidsMagicDesignTypeArray == null)
            kidsMagicDesignTypeArray = getResources().getStringArray(R.array.kidsMagicDesignType);

        if (bBabyDesignTypeArray == null)
            bBabyDesignTypeArray = getResources().getStringArray(R.array.bBabyDesignType);

        if (cottonBlueDesignTypeArray == null)
            cottonBlueDesignTypeArray = getResources().getStringArray(R.array.cottonBlueDesignType);

        tvDateOrderCreation = findViewById(R.id.tvDateOrderCreation);
        tvDateOrderCreation.setText(jobCardItem.getJobCardCreateDate());

        etBrandName = findViewById(R.id.etBrandName);
        etBrandName.setText(jobCardItem.getBrand());
        etBrandName.setOnClickListener(this);

        etJobCardNumber = findViewById(R.id.etJobCardNumber);
        etJobCardNumber.setText(jobCardItem.getJobCardNumber());

        etDesignNumber = findViewById(R.id.etDesignNumber);
        etDesignNumber.setText(jobCardItem.getDesignNumber());

        etDesignCode = findViewById(R.id.etDesignCode);
        etDesignCode.setText(getDesignCode);
        etDesignCode.setOnClickListener(this);

        etQuantity = findViewById(R.id.etQuantity);
        etQuantity.setText(getQuantity);

        etSelectSize = findViewById(R.id.etSelectSize);
        etSelectSize.setText(jobCardItem.getSize());
        etSelectSize.setOnClickListener(this);
        etSelectSize.setEnabled(false);

        etTotalNumberPieces = findViewById(R.id.etTotalNumberPieces);
        etTotalNumberPieces.setText(jobCardItem.getTotalPieces());

        etMasterName = findViewById(R.id.etMasterName);
        etMasterName.setText(jobCardItem.getMasterName());

        etFabricType = findViewById(R.id.etFabricType);
        etFabricType.setText(jobCardItem.getFabricType());

        etCuttingIssueDate = findViewById(R.id.etCuttingIssueDate);
        etCuttingIssueDate.setText(jobCardItem.getCuttingIssueDate());
        etCuttingIssueDate.setOnClickListener(this);

        btnCreateJobCard = findViewById(R.id.btnCreateJobCard);
        btnCreateJobCard.setText(R.string.update_job_Card);

        llFileLayout = findViewById(R.id.llFileLayout);
        llFileLayout.setVisibility(View.VISIBLE);
        isClicked = false;

        ivUploadFile = findViewById(R.id.ivUploadFile);
        ivUploadFile.setVisibility(View.GONE);
        ivCancelFile = findViewById(R.id.ivCancelFile);
        ivCancelFile.setOnClickListener(this);
        ivJobCardFile = findViewById(R.id.ivJobCardFile);
        ivJobCardFile.setOnClickListener(this);
        Picasso.get()
                .load(jobCardItem.getJobCardFilePath()).placeholder(R.drawable.image_placeholder)
                .into(ivJobCardFile);

        etKidsMagicMNSize2 = findViewById(R.id.etKidsMagicMNSize2);

        etKidsMagicMNSize3 = findViewById(R.id.etKidsMagicMNSize3);
        etKidsMagicMNSize4 = findViewById(R.id.etKidsMagicMNSize4);
        etKidsMagicMNSize5 = findViewById(R.id.etKidsMagicMNSize5);
        etKidsMagicMNSize6 = findViewById(R.id.etKidsMagicMNSize6);

        etKidsMagicNumeric8 = findViewById(R.id.etKidsMagicNumeric8);
        etKidsMagicNumeric10 = findViewById(R.id.etKidsMagicNumeric10);
        etKidsMagicNumeric12 = findViewById(R.id.etKidsMagicNumeric12);
        etKidsMagicNumeric14 = findViewById(R.id.etKidsMagicNumeric14);
        etKidsMagicNumeric16 = findViewById(R.id.etKidsMagicNumeric16);

        etKidsMagicGT20 = findViewById(R.id.etKidsMagicGT20);
        etKidsMagicGT22 = findViewById(R.id.etKidsMagicGT22);
        etKidsMagicGT24 = findViewById(R.id.etKidsMagicGT24);
        etKidsMagicGT26 = findViewById(R.id.etKidsMagicGT26);
        etKidsMagicGT28 = findViewById(R.id.etKidsMagicGT28);
        etKidsMagicGT30 = findViewById(R.id.etKidsMagicGT30);
        etKidsMagicGT32 = findViewById(R.id.etKidsMagicGT32);
        etKidsMagicGT34 = findViewById(R.id.etKidsMagicGT34);
        etKidsMagicGT36 = findViewById(R.id.etKidsMagicGT36);

        etBbabySizeS = findViewById(R.id.etBbabySizeS);
        etBbabySizeM = findViewById(R.id.etBbabySizeM);
        etBbabySizeL = findViewById(R.id.etBbabySizeL);
        etBbabySizeXL = findViewById(R.id.etBbabySizeXL);
        etBbabySizeXXL = findViewById(R.id.etBbabySizeXXL);
        etBbabySizeXXXL = findViewById(R.id.etBbabySizeXXXL);

        etBbabyNB = findViewById(R.id.etBbabyNB);
        etBbaby03 = findViewById(R.id.etBbaby03);
        etBbaby36 = findViewById(R.id.etBbaby36);
        etBbaby69 = findViewById(R.id.etBbaby69);
        etBbaby912 = findViewById(R.id.etBbaby912);

        etKMS1 = findViewById(R.id.etKMS1);
        etKMS2 = findViewById(R.id.etKMS2);
        etKMS3 = findViewById(R.id.etKMS3);
        etKMS4 = findViewById(R.id.etKMS4);
        etKMS5 = findViewById(R.id.etKMS5);

        etKMSG1 = findViewById(R.id.etKMSG1);
        etKMSG2 = findViewById(R.id.etKMSG2);
        etKMSG3 = findViewById(R.id.etKMSG3);
        etKMSG4 = findViewById(R.id.etKMSG4);
        etKMSG5 = findViewById(R.id.etKMSG5);

        llBBabyS3XLParent = findViewById(R.id.llBBabyS3XLParent);
        llBBabyNB912Parent = findViewById(R.id.llBBabyNB912Parent);

        llKidsMagicMN = findViewById(R.id.llKidsMagicMN);
        llKidsMagicNumeric = findViewById(R.id.llKidsMagicNumeric);
        llKidsMagicGT = findViewById(R.id.llKidsMagicGT);
        llKMS = findViewById(R.id.llKMS);

        llKMSGirls = findViewById(R.id.llKMSGirls);

        llSizeS = findViewById(R.id.llSizeS);
        viewS = findViewById(R.id.viewS);

        setDefaultSizeByQuantity();
        setDefaultTextWatcher();
        setUiByBrand();

        btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(this);

        etFabricItem = findViewById(R.id.etFabricItem);
        etFabricQuantity = findViewById(R.id.etFabricQuantity);
        rvFabricItem = findViewById(R.id.rvFabricItem);

        fabricListAdapter = new FabricListAdapter(CuttingInChargeViewJobCardDetails.this, fabricCodeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CuttingInChargeViewJobCardDetails.this);
        rvFabricItem.setLayoutManager(mLayoutManager);
        rvFabricItem.setItemAnimator(new DefaultItemAnimator());
        rvFabricItem.setAdapter(fabricListAdapter);

        etFabricCodeUnit = findViewById(R.id.etFabricCodeUnit);
        etFabricCodeUnit.setOnClickListener(this);

        etCuttingCompleteDate = findViewById(R.id.etCuttingCompleteDate);
        etCuttingCompleteDate.setOnClickListener(this);

        etWastage = findViewById(R.id.etWastage);
        etWastageUnit = findViewById(R.id.etWastageUnit);

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

        parentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot fabricSnapshot : dataSnapshot.getChildren()) {
                    //fabricListItem = fabricSnapshot.getValue(FabricListItem.class);
                    addFabricConsumedDetails((Map<String, Object>) dataSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "onCancelled", databaseError.toException());
            }
        });
    }

    private void addFabricConsumedDetails(Map<String, Object> users) {

        ArrayList<FabricListItem> fabricListItemArrayList = new ArrayList<>();

        for (Map.Entry<String, Object> entry : users.entrySet()) {
            Map singleUser = (Map) entry.getValue();

            FabricListItem fabricListItem1 = new FabricListItem();
            fabricListItem1.setFabricCode((String) singleUser.get("fabricCode"));
            fabricListItem1.setFabricUnit((String) singleUser.get("fabricUnit"));
            fabricListItem1.setFabricQuantity((String) singleUser.get("fabricQuantity"));
            fabricListItemArrayList.add(fabricListItem1);
        }

        fabricListAdapter = new FabricListAdapter(CuttingInChargeViewJobCardDetails.this, fabricListItemArrayList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CuttingInChargeViewJobCardDetails.this);
        rvFabricItem.setLayoutManager(mLayoutManager);
        rvFabricItem.setItemAnimator(new DefaultItemAnimator());
        rvFabricItem.setAdapter(fabricListAdapter);

    }

    private void validate() {

        if (fabricCodeList.isEmpty()) {
            Helper.showOkDialog(this, getString(R.string.please_enter_an_item));
            etFabricItem.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etCuttingCompleteDate.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_cutting_complete_date));
            etCuttingCompleteDate.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etWastage.getText().toString().trim())) {
            Helper.showOkDialog(this, getString(R.string.please_enter_wastage_quantity));
            etWastage.requestFocus();
            return;
        }

        sendOrderDetails();
    }

    private void sendOrderDetails() {
        mDialog = Helper.showProgressDialog(this);
        SimpleDateFormat format = new SimpleDateFormat("d");

        format = new SimpleDateFormat("d MMM");

        String currentDate = format.format(new Date());

        String cuttingCompleteDate = etCuttingCompleteDate.getText().toString();
        String wastage = etWastage.getText().toString();
        String wastageUnit = etWastageUnit.getText().toString();

        jobCardItem.setCuttingCompleteDate(cuttingCompleteDate);
        jobCardItem.setFabricListItem(fabricListItem);
        jobCardItem.setWastage(wastage);
        jobCardItem.setWastageUnit(wastageUnit);
        jobCardItem.setJobCardUpdatedByCuttingInChargeDate(currentDate);
        jobCardItem.setIsUpdatedByCuttingInCharge("true");

        cuttingInChargeJobCardRef.child(jobCardItem.getJobCardId()).setValue(jobCardItem, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                mDialog.dismiss();
                Helper.showOkClickDialog(CuttingInChargeViewJobCardDetails.this, getString(R.string.job_card_updated_successfully), new DialogListener() {
                    @Override
                    public void onButtonClicked(int type) {
                        Intent homeIntent = new Intent(CuttingInChargeViewJobCardDetails.this, BaseActivity.class);
                        startActivity(homeIntent);
                        finish();

                    }
                });

            }
        });

        for (int i = 0; i < fabricCodeList.size(); i++) {
            cuttingInChargeJobCardRef.child(jobCardItem.getJobCardId()).child(getString(R.string.fabric_consumed)).push().setValue(fabricCodeList.get(i));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.etWastageUnit:
                Helper.showDropDown(etWastageUnit, new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, unitArray), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        etWastageUnit.setText(unitArray[position]);
                    }
                });
                break;

            case R.id.etFabricCodeUnit:
                Helper.showDropDown(etFabricCodeUnit, new ArrayAdapter<>(CuttingInChargeViewJobCardDetails.this,
                        android.R.layout.simple_list_item_1, unitArray), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        etFabricCodeUnit.setText(unitArray[position]);
                    }
                });
                break;

            case R.id.etCuttingCompleteDate:
                final Calendar c = Calendar.getInstance();
                int year, month, day;
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                etCuttingCompleteDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System
                        .currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;

            case R.id.btnAddItem:
                if (etFabricItem.getText().toString().isEmpty()) {
                    Helper.showOkDialog(CuttingInChargeViewJobCardDetails.this,
                            getString(R.string.please_enter_fabric_code));
                    return;
                }

                if (etFabricQuantity.getText().toString().isEmpty()) {
                    Helper.showOkDialog(CuttingInChargeViewJobCardDetails.this,
                            getString(R.string.please_enter_fabric_quantity));
                    return;
                }

                fabricListItem = new FabricListItem();
                fabricListItem.setFabricCode(etFabricItem.getText().toString());
                fabricListItem.setFabricUnit(etFabricCodeUnit.getText().toString());
                fabricListItem.setFabricQuantity(etFabricQuantity.getText().toString());
                fabricCodeList.add(fabricListItem);
                etFabricItem.setText("");
                etFabricQuantity.setText("");
                etFabricItem.requestFocus();
                fabricListAdapter.notifyDataSetChanged();
                break;

            case R.id.ivJobCardFile:
                Dialog dialog = new Dialog(CuttingInChargeViewJobCardDetails.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));

                ImageView imageView = new ImageView(CuttingInChargeViewJobCardDetails.this);
                imageView.setLayoutParams(new RelativeLayout.LayoutParams
                        (ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));

                Picasso.get()
                        .load(jobCardItem.getJobCardFilePath())
                        .into(imageView);

                dialog.addContentView(imageView, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

                imageView.setPadding(50, 50, 50, 50);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(true);

                if (isImageShowing) {
                    dialog.show();
                    dialog.getWindow().setAttributes(lp);
                    isImageShowing = false;
                }

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
                        isImageShowing = true;
                    }
                });
                break;
        }

    }

    private void showSelectReceiversDialog() {
        boolean[] checkedReceivers = new boolean[fabricTypeArray.length];
        int count = fabricTypeArray.length;

        for (int i = 0; i < count; i++)
            checkedReceivers[i] = selectedFabricType.contains(fabricTypeArray[i]);

        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked)
                    selectedFabricType.add(fabricTypeArray[which]);
                else
                    selectedFabricType.remove(fabricTypeArray[which]);

                onChangeSelectedReceivers();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(CuttingInChargeViewJobCardDetails.this);
        builder
                .setTitle(getString(R.string.selectFabric))
                .setMultiChoiceItems(fabricTypeArray, checkedReceivers, receiversDialogListener)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void onChangeSelectedReceivers() {
        StringBuilder stringBuilder = new StringBuilder();

        for (CharSequence receivers : selectedFabricType)
            stringBuilder.append(receivers + ", ");

        if (stringBuilder.length() != 0) {
            String selectedFabric = stringBuilder.substring(0, stringBuilder.length() - 2);
            etFabricType.setText(selectedFabric);
        } else {
            etFabricType.setHint(getString(R.string.fabric_type));
        }
    }

    private void setDefaultSizeByQuantity() {

        etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String getDefaultQuantity = etQuantity.getText().toString();

                if (etQuantity.getText().toString().length() > 0) {

                    //MN
                    etKidsMagicMNSize2.addTextChangedListener(textWatcher);
                    etKidsMagicMNSize3.addTextChangedListener(textWatcher);
                    etKidsMagicMNSize4.addTextChangedListener(textWatcher);
                    etKidsMagicMNSize5.addTextChangedListener(textWatcher);
                    etKidsMagicMNSize6.addTextChangedListener(textWatcher);

                    //Numeric Value
                    etKidsMagicNumeric8.addTextChangedListener(textWatcher);
                    etKidsMagicNumeric10.addTextChangedListener(textWatcher);
                    etKidsMagicNumeric12.addTextChangedListener(textWatcher);
                    etKidsMagicNumeric14.addTextChangedListener(textWatcher);
                    etKidsMagicNumeric16.addTextChangedListener(textWatcher);

                    //GT
                    etKidsMagicGT20.addTextChangedListener(textWatcher);
                    etKidsMagicGT22.addTextChangedListener(textWatcher);
                    etKidsMagicGT24.addTextChangedListener(textWatcher);
                    etKidsMagicGT26.addTextChangedListener(textWatcher);
                    etKidsMagicGT28.addTextChangedListener(textWatcher);
                    etKidsMagicGT30.addTextChangedListener(textWatcher);
                    etKidsMagicGT32.addTextChangedListener(textWatcher);
                    etKidsMagicGT34.addTextChangedListener(textWatcher);
                    etKidsMagicGT36.addTextChangedListener(textWatcher);

                    //KMSB Boys
                    etKMS1.addTextChangedListener(textWatcher);
                    etKMS2.addTextChangedListener(textWatcher);
                    etKMS3.addTextChangedListener(textWatcher);
                    etKMS4.addTextChangedListener(textWatcher);
                    etKMS5.addTextChangedListener(textWatcher);

                    //KMSB Girls
                    etKMSG1.addTextChangedListener(textWatcher);
                    etKMSG2.addTextChangedListener(textWatcher);
                    etKMSG3.addTextChangedListener(textWatcher);
                    etKMSG4.addTextChangedListener(textWatcher);
                    etKMSG5.addTextChangedListener(textWatcher);

                    //BBaby
                    etBbabySizeS.addTextChangedListener(textWatcher);
                    etBbabySizeM.addTextChangedListener(textWatcher);
                    etBbabySizeL.addTextChangedListener(textWatcher);
                    etBbabySizeXL.addTextChangedListener(textWatcher);
                    etBbabySizeXXL.addTextChangedListener(textWatcher);
                    etBbabySizeXXXL.addTextChangedListener(textWatcher);

                    etBbabyNB.addTextChangedListener(textWatcher);
                    etBbaby03.addTextChangedListener(textWatcher);
                    etBbaby36.addTextChangedListener(textWatcher);
                    etBbaby69.addTextChangedListener(textWatcher);
                    etBbaby912.addTextChangedListener(textWatcher);
                }

                if (!getDefaultQuantity.isEmpty()) {
                    //Kids Magic MN
                    etKidsMagicMNSize2.setText(getDefaultQuantity);
                    etKidsMagicMNSize3.setText(getDefaultQuantity);
                    etKidsMagicMNSize4.setText(getDefaultQuantity);
                    etKidsMagicMNSize5.setText(getDefaultQuantity);
                    etKidsMagicMNSize6.setText(getDefaultQuantity);

                    //Kids Magic Numeric Value
                    etKidsMagicNumeric8.setText(getDefaultQuantity);
                    etKidsMagicNumeric10.setText(getDefaultQuantity);
                    etKidsMagicNumeric12.setText(getDefaultQuantity);
                    etKidsMagicNumeric14.setText(getDefaultQuantity);
                    etKidsMagicNumeric16.setText(getDefaultQuantity);

                    //Kids Magic GT
                    etKidsMagicGT20.setText(getDefaultQuantity);
                    etKidsMagicGT22.setText(getDefaultQuantity);
                    etKidsMagicGT24.setText(getDefaultQuantity);
                    etKidsMagicGT26.setText(getDefaultQuantity);
                    etKidsMagicGT28.setText(getDefaultQuantity);
                    etKidsMagicGT30.setText(getDefaultQuantity);
                    etKidsMagicGT32.setText(getDefaultQuantity);
                    etKidsMagicGT34.setText(getDefaultQuantity);
                    etKidsMagicGT36.setText(getDefaultQuantity);

                    //Kids Magic Suit
                    etKMS1.setText(getDefaultQuantity);
                    etKMS2.setText(getDefaultQuantity);
                    etKMS3.setText(getDefaultQuantity);
                    etKMS4.setText(getDefaultQuantity);
                    etKMS5.setText(getDefaultQuantity);

                    etKMSG1.setText(getDefaultQuantity);
                    etKMSG2.setText(getDefaultQuantity);
                    etKMSG3.setText(getDefaultQuantity);
                    etKMSG4.setText(getDefaultQuantity);
                    etKMSG5.setText(getDefaultQuantity);

                    //BBaby S-3XL
                    etBbabySizeM.setText(getDefaultQuantity);
                    etBbabySizeL.setText(getDefaultQuantity);
                    etBbabySizeXL.setText(getDefaultQuantity);
                    etBbabySizeXXL.setText(getDefaultQuantity);

                    //Default Value for 3XL will always be 0 until manually entered
                    // only for Cotton Blue

                    if (selectedBrand.equals(Constants.COTTON_BLUE)) {
                        etBbabySizeXXXL.setText(getString(R.string.zero));
                    } else {
                        etBbabySizeXXXL.setText(getDefaultQuantity);
                    }

                    if (etQuantity.getText().toString().length() > 1 || etQuantity.getText().toString().length() == 1) {
                        int getQuantity = Integer.parseInt(etQuantity.getText().toString());

                        int sizeS = (int) (getQuantity * (50.0f / 100.0f));
                        //Default Value for S will be 50% of the given Quantity
                        String sValue = String.valueOf(sizeS);
                        etBbabySizeS.setText(sValue);
                    }

                    //Cotton Blue
                    etBbabyNB.setText(getDefaultQuantity);
                    etBbaby03.setText(getDefaultQuantity);
                    etBbaby36.setText(getDefaultQuantity);
                    etBbaby69.setText(getDefaultQuantity);
                    etBbaby912.setText(getDefaultQuantity);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isSizeChartVisible) {
                    getTotalNoPieces();
                } else {
                    etTotalNumberPieces.setText("");
                }
            }
        });
        textWatcherToSetValues();

    }

    private void textWatcherToSetValues() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isSizeChartVisible) {
                    getTotalNoPieces();
                } else {
                    etTotalNumberPieces.setText(jobCardItem.getTotalPieces());
                }
            }
        };
    }

    private void setDefaultTextWatcher() {
        textWatcherToSetValues();
        //MN
        etKidsMagicMNSize2.addTextChangedListener(textWatcher);
        etKidsMagicMNSize3.addTextChangedListener(textWatcher);
        etKidsMagicMNSize4.addTextChangedListener(textWatcher);
        etKidsMagicMNSize5.addTextChangedListener(textWatcher);
        etKidsMagicMNSize6.addTextChangedListener(textWatcher);

        //Numeric Value
        etKidsMagicNumeric8.addTextChangedListener(textWatcher);
        etKidsMagicNumeric10.addTextChangedListener(textWatcher);
        etKidsMagicNumeric12.addTextChangedListener(textWatcher);
        etKidsMagicNumeric14.addTextChangedListener(textWatcher);
        etKidsMagicNumeric16.addTextChangedListener(textWatcher);

        //GT
        etKidsMagicGT20.addTextChangedListener(textWatcher);
        etKidsMagicGT22.addTextChangedListener(textWatcher);
        etKidsMagicGT24.addTextChangedListener(textWatcher);
        etKidsMagicGT26.addTextChangedListener(textWatcher);
        etKidsMagicGT28.addTextChangedListener(textWatcher);
        etKidsMagicGT30.addTextChangedListener(textWatcher);
        etKidsMagicGT32.addTextChangedListener(textWatcher);
        etKidsMagicGT34.addTextChangedListener(textWatcher);
        etKidsMagicGT36.addTextChangedListener(textWatcher);

        //KMSB Boys
        etKMS1.addTextChangedListener(textWatcher);
        etKMS2.addTextChangedListener(textWatcher);
        etKMS3.addTextChangedListener(textWatcher);
        etKMS4.addTextChangedListener(textWatcher);
        etKMS5.addTextChangedListener(textWatcher);

        //KMSB Girls
        etKMSG1.addTextChangedListener(textWatcher);
        etKMSG2.addTextChangedListener(textWatcher);
        etKMSG3.addTextChangedListener(textWatcher);
        etKMSG4.addTextChangedListener(textWatcher);
        etKMSG5.addTextChangedListener(textWatcher);

        //BBaby
        etBbabySizeS.addTextChangedListener(textWatcher);
        etBbabySizeM.addTextChangedListener(textWatcher);
        etBbabySizeL.addTextChangedListener(textWatcher);
        etBbabySizeXL.addTextChangedListener(textWatcher);
        etBbabySizeXXL.addTextChangedListener(textWatcher);
        etBbabySizeXXXL.addTextChangedListener(textWatcher);

        etBbabyNB.addTextChangedListener(textWatcher);
        etBbaby03.addTextChangedListener(textWatcher);
        etBbaby36.addTextChangedListener(textWatcher);
        etBbaby69.addTextChangedListener(textWatcher);
        etBbaby912.addTextChangedListener(textWatcher);
    }

    @SuppressLint("SetTextI18n")
    private void getTotalNoPieces() {
        if (!etQuantity.getText().toString().equals("")) {

            setDefaultZero();

            if (etQuantity.getText().toString().length() > 1) {

                if (selectedBrand.equals(Constants.KIDS_MAGIC)) {
                    if (selectedDesignType.equals(Constants.MN)) {
                        //Kids Magic MN
                        if (!TextUtils.isEmpty(etKidsMagicMNSize2.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicMNSize3.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicMNSize4.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicMNSize5.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicMNSize6.getText().toString().trim())) {


                            int answer = Integer.parseInt(etKidsMagicMNSize2.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicMNSize3.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicMNSize4.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicMNSize5.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicMNSize6.getText().toString().trim());

                            etTotalNumberPieces.setText(getString(R.string.tot_no_pieces) + ":"
                                    + Constants.EMPTY_STRING + String.valueOf(answer));


                        } else {
                            etTotalNumberPieces.setText("");
                        }
                    } else if (selectedDesignType.equals(Constants.NUMERIC)) {
                        if (!TextUtils.isEmpty(etKidsMagicNumeric8.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicNumeric10.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicNumeric12.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicNumeric14.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicNumeric16.getText().toString().trim())) {


                            int answer = Integer.parseInt(etKidsMagicNumeric8.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicNumeric10.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicNumeric12.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicNumeric14.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicNumeric16.getText().toString().trim());

                            etTotalNumberPieces.setText(getString(R.string.tot_no_pieces) + ":"
                                    + Constants.EMPTY_STRING + String.valueOf(answer));


                        } else {
                            etTotalNumberPieces.setText("");
                        }
                    } else if (selectedDesignType.equals(Constants.GT)) {
                        if (!TextUtils.isEmpty(etKidsMagicGT20.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicGT22.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicGT24.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicGT26.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicGT28.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicGT30.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicGT32.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicGT34.getText().toString().trim())
                                || !TextUtils.isEmpty(etKidsMagicGT36.getText().toString().trim())) {

                            int answer = Integer.parseInt(etKidsMagicGT22.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicGT24.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicGT26.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicGT28.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicGT30.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicGT32.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicGT34.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicGT36.getText().toString().trim()) +
                                    Integer.parseInt(etKidsMagicGT20.getText().toString().trim());

                            etTotalNumberPieces.setText(getString(R.string.tot_no_pieces) + ":"
                                    + Constants.EMPTY_STRING + (answer));

                        } else {
                            etTotalNumberPieces.setText("");
                        }
                    } else if (selectedDesignType.equals(Constants.PANT)) {
                        //2*6 Pant
                        if (etSelectSize.getText().toString().equals(getString(R.string.pant_2_6))) {
                            if (!TextUtils.isEmpty(etKidsMagicMNSize2.getText().toString().trim())
                                    || !TextUtils.isEmpty(etKidsMagicMNSize3.getText().toString().trim())
                                    || !TextUtils.isEmpty(etKidsMagicMNSize4.getText().toString().trim())
                                    || !TextUtils.isEmpty(etKidsMagicMNSize5.getText().toString().trim())
                                    || !TextUtils.isEmpty(etKidsMagicMNSize6.getText().toString().trim())) {


                                int answer = Integer.parseInt(etKidsMagicMNSize2.getText().toString().trim()) +
                                        Integer.parseInt(etKidsMagicMNSize3.getText().toString().trim()) +
                                        Integer.parseInt(etKidsMagicMNSize4.getText().toString().trim()) +
                                        Integer.parseInt(etKidsMagicMNSize5.getText().toString().trim()) +
                                        Integer.parseInt(etKidsMagicMNSize6.getText().toString().trim());

                                etTotalNumberPieces.setText(getString(R.string.tot_no_pieces) + ":"
                                        + Constants.EMPTY_STRING + String.valueOf(answer));


                            } else {
                                etTotalNumberPieces.setText("");
                            }
                        }
                        //8*16 Pant
                        else if (etSelectSize.getText().toString().equals(getString(R.string.pant_8_16))) {
                            if (!TextUtils.isEmpty(etKidsMagicNumeric8.getText().toString().trim())
                                    || !TextUtils.isEmpty(etKidsMagicNumeric10.getText().toString().trim())
                                    || !TextUtils.isEmpty(etKidsMagicNumeric12.getText().toString().trim())
                                    || !TextUtils.isEmpty(etKidsMagicNumeric14.getText().toString().trim())
                                    || !TextUtils.isEmpty(etKidsMagicNumeric16.getText().toString().trim())) {


                                int answer = Integer.parseInt(etKidsMagicNumeric8.getText().toString().trim()) +
                                        Integer.parseInt(etKidsMagicNumeric10.getText().toString().trim()) +
                                        Integer.parseInt(etKidsMagicNumeric12.getText().toString().trim()) +
                                        Integer.parseInt(etKidsMagicNumeric14.getText().toString().trim()) +
                                        Integer.parseInt(etKidsMagicNumeric16.getText().toString().trim());

                                etTotalNumberPieces.setText(getString(R.string.tot_no_pieces) + ":"
                                        + Constants.EMPTY_STRING + String.valueOf(answer));


                            } else {
                                etTotalNumberPieces.setText("");
                            }
                        }

                    } else if (selectedDesignType.equals(Constants.KMSB)) {

                        if (!TextUtils.isEmpty(etKMS1.getText().toString().trim())
                                || !TextUtils.isEmpty(etKMS2.getText().toString().trim())
                                || !TextUtils.isEmpty(etKMS3.getText().toString().trim())
                                || !TextUtils.isEmpty(etKMS4.getText().toString().trim())
                                || !TextUtils.isEmpty(etKMS5.getText().toString().trim())) {

                            int answer = Integer.parseInt(etKMS1.getText().toString().trim()) +
                                    Integer.parseInt(etKMS2.getText().toString().trim()) +
                                    Integer.parseInt(etKMS3.getText().toString().trim()) +
                                    Integer.parseInt(etKMS4.getText().toString().trim()) +
                                    Integer.parseInt(etKMS5.getText().toString().trim());

                            etTotalNumberPieces.setText(getString(R.string.tot_no_pieces) + ":"
                                    + Constants.EMPTY_STRING + String.valueOf(answer));

                        } else {
                            etTotalNumberPieces.setText("");
                        }

                    } else if (selectedDesignType.equals(Constants.KMSG)) {

                        if (!TextUtils.isEmpty(etKMSG1.getText().toString().trim())
                                || !TextUtils.isEmpty(etKMSG2.getText().toString().trim())
                                || !TextUtils.isEmpty(etKMSG3.getText().toString().trim())
                                || !TextUtils.isEmpty(etKMSG4.getText().toString().trim())
                                || !TextUtils.isEmpty(etKMSG5.getText().toString().trim())) {

                            int answer = Integer.parseInt(etKMSG1.getText().toString().trim()) +
                                    Integer.parseInt(etKMSG2.getText().toString().trim()) +
                                    Integer.parseInt(etKMSG3.getText().toString().trim()) +
                                    Integer.parseInt(etKMSG4.getText().toString().trim()) +
                                    Integer.parseInt(etKMSG5.getText().toString().trim());

                            etTotalNumberPieces.setText(getString(R.string.tot_no_pieces) + ":"
                                    + Constants.EMPTY_STRING + (answer));

                        } else {
                            etTotalNumberPieces.setText("");
                        }

                    }
                } else if (selectedBrand.equals(Constants.BBABY)) {

                    if (selectedDesignType.equals("BBB") || selectedDesignType.equals("BBF")
                            || selectedDesignType.equals("BBG") || selectedDesignType.equals("BBS")
                            || selectedDesignType.equals("BT") || selectedDesignType.equals("BBNSG")
                            || selectedDesignType.equals("BBNSB") || selectedDesignType.equals("GT")) {

                        if (!TextUtils.isEmpty(etBbabySizeS.getText().toString().trim())
                                || !TextUtils.isEmpty(etBbabySizeM.getText().toString().trim())
                                || !TextUtils.isEmpty(etBbabySizeL.getText().toString().trim())
                                || !TextUtils.isEmpty(etBbabySizeXL.getText().toString().trim())
                                || !TextUtils.isEmpty(etBbabySizeXXL.getText().toString().trim())
                                || !TextUtils.isEmpty(etBbabySizeXXXL.getText().toString().trim())) {

                            int answer = Integer.parseInt(etBbabySizeS.getText().toString().trim()) +
                                    Integer.parseInt(etBbabySizeM.getText().toString().trim()) +
                                    Integer.parseInt(etBbabySizeL.getText().toString().trim()) +
                                    Integer.parseInt(etBbabySizeXL.getText().toString().trim()) +
                                    Integer.parseInt(etBbabySizeXXL.getText().toString().trim()) +
                                    Integer.parseInt(etBbabySizeXXXL.getText().toString().trim());

                            etTotalNumberPieces.setText(getString(R.string.tot_no_pieces) + ":"
                                    + Constants.EMPTY_STRING + (answer));

                        } else {
                            etTotalNumberPieces.setText("");
                        }
                    } else if (selectedDesignType.equals("BBGR")
                            || selectedDesignType.equals("BBBR")) {

                        if (!TextUtils.isEmpty(etBbabyNB.getText().toString().trim())
                                || !TextUtils.isEmpty(etBbaby03.getText().toString().trim())
                                || !TextUtils.isEmpty(etBbaby36.getText().toString().trim())
                                || !TextUtils.isEmpty(etBbaby69.getText().toString().trim())
                                || !TextUtils.isEmpty(etBbaby912.getText().toString().trim())) {

                            int answer = Integer.parseInt(etBbabyNB.getText().toString().trim()) +
                                    Integer.parseInt(etBbaby03.getText().toString().trim()) +
                                    Integer.parseInt(etBbaby36.getText().toString().trim()) +
                                    Integer.parseInt(etBbaby69.getText().toString().trim()) +
                                    Integer.parseInt(etBbaby912.getText().toString().trim());

                            etTotalNumberPieces.setText(getString(R.string.tot_no_pieces) + ":"
                                    + Constants.EMPTY_STRING + String.valueOf(answer));

                        } else {
                            etTotalNumberPieces.setText("");
                        }

                    }

                } else if (selectedBrand.equals(Constants.COTTON_BLUE)) {
                    if (!TextUtils.isEmpty(etBbabySizeS.getText().toString().trim())
                            || !TextUtils.isEmpty(etBbabySizeM.getText().toString().trim())
                            || !TextUtils.isEmpty(etBbabySizeL.getText().toString().trim())
                            || !TextUtils.isEmpty(etBbabySizeXL.getText().toString().trim())
                            || !TextUtils.isEmpty(etBbabySizeXXL.getText().toString().trim())
                            || !TextUtils.isEmpty(etBbabySizeXXXL.getText().toString().trim())) {

                        int answer = Integer.parseInt(etBbabySizeS.getText().toString().trim()) +
                                Integer.parseInt(etBbabySizeM.getText().toString().trim()) +
                                Integer.parseInt(etBbabySizeL.getText().toString().trim()) +
                                Integer.parseInt(etBbabySizeXL.getText().toString().trim()) +
                                Integer.parseInt(etBbabySizeXXL.getText().toString().trim()) +
                                Integer.parseInt(etBbabySizeXXXL.getText().toString().trim());

                        etTotalNumberPieces.setText(getString(R.string.tot_no_pieces) + ":"
                                + Constants.EMPTY_STRING + String.valueOf(answer));

                    } else {
                        etTotalNumberPieces.setText("");
                    }
                }

            }
        }
    }

    private void setDefaultZero() {

        if (etKidsMagicMNSize2.getText().toString().equals("")) {
            etKidsMagicMNSize2.setText("0");
        }

        if (etKidsMagicMNSize3.getText().toString().equals("")) {
            etKidsMagicMNSize3.setText("0");
        }

        if (etKidsMagicMNSize4.getText().toString().equals("")) {
            etKidsMagicMNSize4.setText("0");
        }

        if (etKidsMagicMNSize5.getText().toString().equals("")) {
            etKidsMagicMNSize5.setText("0");
        }

        if (etKidsMagicMNSize6.getText().toString().equals("")) {
            etKidsMagicMNSize6.setText("0");
        }

        if (etKidsMagicNumeric8.getText().toString().equals("")) {
            etKidsMagicNumeric8.setText("0");
        }

        if (etKidsMagicNumeric10.getText().toString().equals("")) {
            etKidsMagicNumeric10.setText("0");
        }

        if (etKidsMagicNumeric12.getText().toString().equals("")) {
            etKidsMagicNumeric12.setText("0");
        }

        if (etKidsMagicNumeric14.getText().toString().equals("")) {
            etKidsMagicNumeric14.setText("0");
        }

        if (etKidsMagicNumeric16.getText().toString().equals("")) {
            etKidsMagicNumeric16.setText("0");
        }

        if (etKidsMagicGT20.getText().toString().equals("")) {
            etKidsMagicGT20.setText("0");
        }

        if (etKidsMagicGT22.getText().toString().equals("")) {
            etKidsMagicGT22.setText("0");
        }

        if (etKidsMagicGT24.getText().toString().equals("")) {
            etKidsMagicGT24.setText("0");
        }

        if (etKidsMagicGT26.getText().toString().equals("")) {
            etKidsMagicGT26.setText("0");
        }

        if (etKidsMagicGT28.getText().toString().equals("")) {
            etKidsMagicGT28.setText("0");
        }

        if (etKidsMagicGT30.getText().toString().equals("")) {
            etKidsMagicGT30.setText("0");
        }

        if (etKidsMagicGT32.getText().toString().equals("")) {
            etKidsMagicGT32.setText("0");
        }

        if (etKidsMagicGT34.getText().toString().equals("")) {
            etKidsMagicGT34.setText("0");
        }

        if (etKidsMagicGT36.getText().toString().equals("")) {
            etKidsMagicGT36.setText("0");
        }

        if (etKMS1.getText().toString().equals("")) {
            etKMS1.setText("0");
        }
        if (etKMS2.getText().toString().equals("")) {
            etKMS2.setText("0");
        }
        if (etKMS3.getText().toString().equals("")) {
            etKMS3.setText("0");
        }
        if (etKMS4.getText().toString().equals("")) {
            etKMS4.setText("0");
        }
        if (etKMS5.getText().toString().equals("")) {
            etKMS5.setText("0");
        }

        if (etKMSG1.getText().toString().equals("")) {
            etKMSG1.setText("0");
        }

        if (etKMSG2.getText().toString().equals("")) {
            etKMSG2.setText("0");
        }

        if (etKMSG3.getText().toString().equals("")) {
            etKMSG3.setText("0");
        }

        if (etKMSG4.getText().toString().equals("")) {
            etKMSG4.setText("0");
        }

        if (etKMSG5.getText().toString().equals("")) {
            etKMSG5.setText("0");
        }

        if (etBbabySizeS.getText().toString().equals("")) {
            etBbabySizeS.setText("0");
        }

        if (etBbabySizeM.getText().toString().equals("")) {
            etBbabySizeM.setText("0");
        }

        if (etBbabySizeL.getText().toString().equals("")) {
            etBbabySizeL.setText("0");
        }

        if (etBbabySizeXL.getText().toString().equals("")) {
            etBbabySizeXL.setText("0");
        }

        if (etBbabySizeXXL.getText().toString().equals("")) {
            etBbabySizeXXL.setText("0");
        }

        if (etBbabySizeXXXL.getText().toString().equals("")) {
            etBbabySizeXXXL.setText("0");
        }

        if (etBbabyNB.getText().toString().equals("")) {
            etBbabyNB.setText("0");
        }

        if (etBbaby03.getText().toString().equals("")) {
            etBbaby03.setText("0");
        }

        if (etBbaby36.getText().toString().equals("")) {
            etBbaby36.setText("0");
        }

        if (etBbaby69.getText().toString().equals("")) {
            etBbaby69.setText("0");
        }

        if (etBbaby912.getText().toString().equals("")) {
            etBbaby912.setText("0");
        }

    }

    private void setUiByBrand() {
        etDesignCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (etBrandName.getText().toString().equals(Constants.BBABY)) {
                    selectedBrand = Constants.BBABY;

                        /*String bBabyQuantity = etDesignNumber.getText().toString()
                                .replaceAll("[0-9]", "");*/

                    String bBabyQuantity = etDesignCode.getText().toString();

                    if (bBabyQuantity.matches("BBB") ||
                            bBabyQuantity.matches("BBF") ||
                            bBabyQuantity.matches("BBG") ||
                            bBabyQuantity.matches("BBS") ||
                            bBabyQuantity.matches("BT") ||
                            bBabyQuantity.matches("BBNSG") ||
                            bBabyQuantity.matches("BBNSB") ||
                            bBabyQuantity.matches("GT")) {

                        selectedDesignType = bBabyQuantity;
                        String sizeName = "";
                        switch (bBabyQuantity) {
                            case "BBB":
                                sizeName = getString(R.string.boys_top);
                                break;
                            case "BBF":
                                sizeName = getString(R.string.frock);
                                break;
                            case "BBG":
                                sizeName = getString(R.string.girls_top);
                                break;

                            case "BBS":
                                sizeName = getString(R.string.baba_suit);
                                break;

                            case "BT":
                                sizeName = getString(R.string.boys_trouser);
                                break;

                            case "BBNSG":
                                sizeName = getString(R.string.girls_night_suit);
                                break;

                            case "BBNSB":
                                sizeName = getString(R.string.boys_night_suit);
                                break;

                            case "GT":
                                sizeName = getString(R.string.girls_trouser);
                                break;
                        }
                        etSelectSize.setText(sizeName);
                        llBBabyS3XLParent.setVisibility(View.VISIBLE);
                        llSizeS.setVisibility(View.VISIBLE);
                        viewS.setVisibility(View.VISIBLE);
                        isSizeChartVisible = true;
                        llBBabyNB912Parent.setVisibility(View.GONE);

                    } else if (bBabyQuantity.matches("BBGR") ||
                            bBabyQuantity.matches("BBBR")) {

                        if (bBabyQuantity.equals("BBGR")) {
                            etSelectSize.setText(getString(R.string.girls_romper));
                        } else if (bBabyQuantity.equals("BBBR")) {
                            etSelectSize.setText(getString(R.string.boys_romper));
                        }

                        selectedDesignType = bBabyQuantity;
                        llBBabyS3XLParent.setVisibility(View.GONE);
                        llBBabyNB912Parent.setVisibility(View.VISIBLE);
                        isSizeChartVisible = true;
                    } else {
                        Helper.showOkDialog(CuttingInChargeViewJobCardDetails.this, getString(R.string.please_enter_valid_quantity_number));
                    }


                } else if (etBrandName.getText().toString().equals(Constants.KIDS_MAGIC)) {
                    selectedBrand = Constants.KIDS_MAGIC;

                    //Numeric Value
                    if (etDesignCode.getText().toString().equals(Constants.NUMERIC)) {
                        isKidsMagicP = false;
                        etSelectSize.setText(getString(R.string.boys_tee_8_6));
                        llBBabyS3XLParent.setVisibility(View.GONE);
                        llBBabyNB912Parent.setVisibility(View.GONE);
                        llKidsMagicGT.setVisibility(View.GONE);
                        llKMS.setVisibility(View.GONE);
                        llBBabyS3XLParent.setVisibility(View.GONE);
                        llKidsMagicNumeric.setVisibility(View.GONE);
                        llKMSGirls.setVisibility(View.GONE);
                        llKidsMagicMN.setVisibility(View.GONE);

                        llKidsMagicNumeric.setVisibility(View.VISIBLE);
                        isSizeChartVisible = true;
                        selectedDesignType = Constants.NUMERIC;

                    } else {
                        isKidsMagicP = false;
                        String kidsMagicQuantity = etDesignCode.getText().toString();

                        //MN
                        if (kidsMagicQuantity.matches(Constants.MN)) {
                            isKidsMagicP = false;
                            selectedDesignType = Constants.MN;
                            llBBabyS3XLParent.setVisibility(View.GONE);
                            llBBabyNB912Parent.setVisibility(View.GONE);
                            llKidsMagicGT.setVisibility(View.GONE);
                            llKMS.setVisibility(View.GONE);
                            llBBabyS3XLParent.setVisibility(View.GONE);
                            llKidsMagicNumeric.setVisibility(View.GONE);
                            llKMSGirls.setVisibility(View.GONE);

                            llKidsMagicMN.setVisibility(View.VISIBLE);
                            isSizeChartVisible = true;

                            etSelectSize.setText(getString(R.string.boys_tee_2_6));

                            //Girls Top
                        } else if (kidsMagicQuantity.matches(Constants.GT)) {
                            isKidsMagicP = false;
                            selectedDesignType = Constants.GT;
                            llBBabyS3XLParent.setVisibility(View.GONE);
                            llBBabyNB912Parent.setVisibility(View.GONE);
                            llKMS.setVisibility(View.GONE);
                            llBBabyS3XLParent.setVisibility(View.GONE);
                            llKidsMagicMN.setVisibility(View.GONE);
                            llKidsMagicNumeric.setVisibility(View.GONE);
                            llKMSGirls.setVisibility(View.GONE);

                            llKidsMagicGT.setVisibility(View.VISIBLE);
                            isSizeChartVisible = true;

                            etSelectSize.setText(getString(R.string.girls_tee_20_36));

                            //Pant
                        } else if (kidsMagicQuantity.matches(Constants.PANT)) {

                            etSelectSize.setEnabled(true);
                            etSelectSize.setText("");
                            selectedDesignType = Constants.PANT;
                            isKidsMagicP = true;
                            Helper.showDropDown(etSelectSize, new ArrayAdapter<>(CuttingInChargeViewJobCardDetails.this,
                                    android.R.layout.simple_list_item_1, pantArray), new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    etSelectSize.setText(pantArray[position]);
                                    if (position == 0) {
                                        llBBabyS3XLParent.setVisibility(View.GONE);
                                        llBBabyNB912Parent.setVisibility(View.GONE);
                                        llKMS.setVisibility(View.GONE);
                                        llBBabyS3XLParent.setVisibility(View.GONE);
                                        llKidsMagicGT.setVisibility(View.GONE);
                                        llKidsMagicNumeric.setVisibility(View.GONE);

                                        llKidsMagicMN.setVisibility(View.VISIBLE);
                                        llKMSGirls.setVisibility(View.GONE);
                                        isSizeChartVisible = true;
                                    } else {
                                        llBBabyS3XLParent.setVisibility(View.GONE);
                                        llBBabyNB912Parent.setVisibility(View.GONE);
                                        llKMS.setVisibility(View.GONE);
                                        llBBabyS3XLParent.setVisibility(View.GONE);
                                        llKidsMagicGT.setVisibility(View.GONE);
                                        llKidsMagicMN.setVisibility(View.GONE);
                                        llKMSGirls.setVisibility(View.GONE);
                                        isSizeChartVisible = true;
                                        llKidsMagicNumeric.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                            //Kids Magic Suit Boys
                        } else if (kidsMagicQuantity.equalsIgnoreCase(Constants.KMSB)) {
                            isKidsMagicP = false;
                            selectedDesignType = Constants.KMSB;
                            llBBabyS3XLParent.setVisibility(View.GONE);
                            llBBabyNB912Parent.setVisibility(View.GONE);
                            etSelectSize.setText(getString(R.string.kmsb));

                            llBBabyS3XLParent.setVisibility(View.GONE);
                            llBBabyNB912Parent.setVisibility(View.GONE);
                            llBBabyS3XLParent.setVisibility(View.GONE);
                            llKidsMagicGT.setVisibility(View.GONE);
                            llKidsMagicMN.setVisibility(View.GONE);
                            llKidsMagicNumeric.setVisibility(View.GONE);
                            llKMSGirls.setVisibility(View.GONE);

                            llKMS.setVisibility(View.VISIBLE);
                            isSizeChartVisible = true;

                            //Kids Magic Suit Girls
                        } else if (kidsMagicQuantity.equalsIgnoreCase(Constants.KMSG)) {
                            isKidsMagicP = false;
                            selectedDesignType = Constants.KMSG;
                            llBBabyS3XLParent.setVisibility(View.GONE);
                            llBBabyNB912Parent.setVisibility(View.GONE);
                            etSelectSize.setText(getString(R.string.kmsg));

                            llBBabyS3XLParent.setVisibility(View.GONE);
                            llBBabyNB912Parent.setVisibility(View.GONE);
                            llBBabyS3XLParent.setVisibility(View.GONE);
                            llKidsMagicGT.setVisibility(View.GONE);
                            llKidsMagicMN.setVisibility(View.GONE);
                            llKidsMagicNumeric.setVisibility(View.GONE);
                            llKMS.setVisibility(View.GONE);
                            llKMSGirls.setVisibility(View.VISIBLE);
                            isSizeChartVisible = true;
                        }

                    }


                } else if (etBrandName.getText().toString().equals(Constants.COTTON_BLUE)) {
                    selectedBrand = Constants.COTTON_BLUE;

                    //Numeric
                    if (etDesignCode.getText().toString().equals(Constants.NUMERIC)) {
                        etSelectSize.setText(Constants.DESIGNER);
                        selectedDesignType = Constants.NUMERIC;
                        llBBabyS3XLParent.setVisibility(View.VISIBLE);
                        llSizeS.setVisibility(View.VISIBLE);
                        viewS.setVisibility(View.VISIBLE);
                        isSizeChartVisible = true;
                        llSizeS.setVisibility(View.GONE);
                        viewS.setVisibility(View.GONE);

                    } else {
                        String bBabyQuantity = etDesignCode.getText().toString();

                        if (bBabyQuantity.matches(Constants.ST) ||
                                bBabyQuantity.matches(Constants.GM) ||
                                bBabyQuantity.matches(Constants.PR)) {

                            selectedDesignType = bBabyQuantity;

                            String sizeName = "";
                            switch (bBabyQuantity) {
                                case "ST":
                                    sizeName = Constants.STRIPE;
                                    break;
                                case "GM":
                                    sizeName = Constants.GRACE_MARSADISE;
                                    break;
                                case "PR":
                                    sizeName = Constants.PREMIUM;
                                    break;
                            }
                            etSelectSize.setText(sizeName);
                            llBBabyS3XLParent.setVisibility(View.VISIBLE);
                            llSizeS.setVisibility(View.VISIBLE);
                            viewS.setVisibility(View.VISIBLE);
                            isSizeChartVisible = true;
                            llSizeS.setVisibility(View.GONE);
                            viewS.setVisibility(View.GONE);

                        }
                    }


                }


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
    }

    private void disableView(TextView view) {
        view.setEnabled(false);
        view.setTextColor(getResources().getColor(R.color.colorGreyDark));
    }

    private void setViewsDisabled() {
        disableView(etFabricType);
        disableView(etBrandName);
        disableView(etJobCardNumber);
        disableView(etDesignCode);
        disableView(etDesignNumber);
        disableView(etQuantity);
        disableView(etSelectSize);
        disableView(etTotalNumberPieces);
        disableView(etMasterName);
        disableView(etCuttingIssueDate);

        disableView(etKidsMagicMNSize2);
        disableView(etKidsMagicMNSize3);
        disableView(etKidsMagicMNSize4);
        disableView(etKidsMagicMNSize5);
        disableView(etKidsMagicMNSize6);

        disableView(etKidsMagicNumeric8);
        disableView(etKidsMagicNumeric10);
        disableView(etKidsMagicNumeric12);
        disableView(etKidsMagicNumeric14);
        disableView(etKidsMagicNumeric16);

        disableView(etKidsMagicGT20);
        disableView(etKidsMagicGT22);
        disableView(etKidsMagicGT24);
        disableView(etKidsMagicGT26);
        disableView(etKidsMagicGT28);
        disableView(etKidsMagicGT30);
        disableView(etKidsMagicGT32);
        disableView(etKidsMagicGT34);
        disableView(etKidsMagicGT36);

        disableView(etKMS1);
        disableView(etKMS2);
        disableView(etKMS3);
        disableView(etKMS4);
        disableView(etKMS5);

        disableView(etKMSG1);
        disableView(etKMSG2);
        disableView(etKMSG3);
        disableView(etKMSG4);
        disableView(etKMSG5);

        disableView(etBbabySizeS);
        disableView(etBbabySizeM);
        disableView(etBbabySizeL);
        disableView(etBbabySizeXL);
        disableView(etBbabySizeXXL);
        disableView(etBbabySizeXXXL);

        disableView(etBbabyNB);
        disableView(etBbaby03);
        disableView(etBbaby36);
        disableView(etBbaby69);
        disableView(etBbaby912);

        if (jobCardItem.getIsUpdatedByCuttingInCharge().equals("true")) {
            AppSession.getInstance()
                    .saveCuttingInChargeUpdate(CuttingInChargeViewJobCardDetails.this, "true");
            disableView(etFabricItem);
            disableView(etFabricQuantity);
            disableView(etFabricCodeUnit);

            btnAddItem.setVisibility(View.GONE);
            etFabricItem.setVisibility(View.GONE);
            etFabricCodeUnit.setVisibility(View.GONE);
            etFabricQuantity.setVisibility(View.GONE);

            etWastage.setText(getString(R.string.wastage) + ":" + jobCardItem.getWastage());
            etCuttingCompleteDate.setText(jobCardItem.getCuttingCompleteDate());

            disableView(etWastage);
            disableView(etWastageUnit);
            disableView(etCuttingCompleteDate);

            btnCreateJobCard.setVisibility(View.INVISIBLE);

        }
    }

    private void setValues() {
        etKidsMagicMNSize2.setText(sizeListItem.getSize2());

        etKidsMagicMNSize3.setText(sizeListItem.getSize3());

        etKidsMagicMNSize4.setText(sizeListItem.getSize4());

        etKidsMagicMNSize5.setText(sizeListItem.getSize5());

        etKidsMagicMNSize6.setText(sizeListItem.getSize6());

        etKidsMagicNumeric8.setText(sizeListItem.getSize8());

        etKidsMagicNumeric10.setText(sizeListItem.getSize10());

        etKidsMagicNumeric12.setText(sizeListItem.getSize12());

        etKidsMagicNumeric14.setText(sizeListItem.getSize14());

        etKidsMagicNumeric16.setText(sizeListItem.getSize16());

        etKidsMagicGT20.setText(sizeListItem.getSize20());

        etKidsMagicGT22.setText(sizeListItem.getSize22());

        etKidsMagicGT24.setText(sizeListItem.getSize24());

        etKidsMagicGT26.setText(sizeListItem.getSize26());

        etKidsMagicGT28.setText(sizeListItem.getSize28());

        etKidsMagicGT30.setText(sizeListItem.getSize30());

        etKidsMagicGT32.setText(sizeListItem.getSize32());

        etKidsMagicGT34.setText(sizeListItem.getSize34());

        etKidsMagicGT36.setText(sizeListItem.getSize36());

        etKMS1.setText(sizeListItem.getSize1());
        etKMS2.setText(sizeListItem.getSize2());
        etKMS3.setText(sizeListItem.getSize3());
        etKMS4.setText(sizeListItem.getSize4());
        etKMS5.setText(sizeListItem.getSize5());

        etKMSG1.setText(sizeListItem.getSize16());
        etKMSG2.setText(sizeListItem.getSize18());
        etKMSG3.setText(sizeListItem.getSize20());
        etKMSG4.setText(sizeListItem.getSize22());
        etKMSG5.setText(sizeListItem.getSize24());

        etBbabySizeS.setText(sizeListItem.getS());

        etBbabySizeM.setText(sizeListItem.getM());

        etBbabySizeL.setText(sizeListItem.getL());

        etBbabySizeXL.setText(sizeListItem.getXL());

        etBbabySizeXXL.setText(sizeListItem.getXXL());

        etBbabySizeXXXL.setText(sizeListItem.getXXXL());

        etBbabyNB.setText(sizeListItem.getNB());

        etBbaby03.setText(sizeListItem.getSize0b3());

        etBbaby36.setText(sizeListItem.getSize3b6());

        etBbaby69.setText(sizeListItem.getSize6b9());

        etBbaby912.setText(sizeListItem.getSize9b12());


        if (etBrandName.getText().toString().equals(Constants.BBABY)) {
            selectedBrand = Constants.BBABY;

            String bBabyQuantity = etDesignCode.getText().toString();

            if (bBabyQuantity.matches("BBB") ||
                    bBabyQuantity.matches("BBF") ||
                    bBabyQuantity.matches("BBG") ||
                    bBabyQuantity.matches("BBS") ||
                    bBabyQuantity.matches("BT") ||
                    bBabyQuantity.matches("BBNSG") ||
                    bBabyQuantity.matches("BBNSB") ||
                    bBabyQuantity.matches("GT")) {

                selectedDesignType = bBabyQuantity;
                String sizeName = "";
                switch (bBabyQuantity) {
                    case "BBB":
                        sizeName = getString(R.string.boys_top);
                        break;
                    case "BBF":
                        sizeName = getString(R.string.frock);
                        break;
                    case "BBG":
                        sizeName = getString(R.string.girls_top);
                        break;

                    case "BBS":
                        sizeName = getString(R.string.baba_suit);
                        break;

                    case "BT":
                        sizeName = getString(R.string.boys_trouser);
                        break;

                    case "BBNSG":
                        sizeName = getString(R.string.girls_night_suit);
                        break;

                    case "BBNSB":
                        sizeName = getString(R.string.boys_night_suit);
                        break;

                    case "GT":
                        sizeName = getString(R.string.girls_trouser);
                        break;
                }
                etSelectSize.setText(sizeName);
                llBBabyS3XLParent.setVisibility(View.VISIBLE);
                llSizeS.setVisibility(View.VISIBLE);
                viewS.setVisibility(View.VISIBLE);
                isSizeChartVisible = true;
                llBBabyNB912Parent.setVisibility(View.GONE);

            } else if (bBabyQuantity.matches("BBGR") ||
                    bBabyQuantity.matches("BBBR")) {

                if (bBabyQuantity.equals("BBGR")) {
                    etSelectSize.setText(getString(R.string.girls_romper));
                } else if (bBabyQuantity.equals("BBBR")) {
                    etSelectSize.setText(getString(R.string.boys_romper));
                }

                selectedDesignType = bBabyQuantity;
                llBBabyS3XLParent.setVisibility(View.GONE);
                llBBabyNB912Parent.setVisibility(View.VISIBLE);
                isSizeChartVisible = true;
            } else {
                Helper.showOkDialog(this, getString(R.string.please_enter_valid_quantity_number));
            }


        } else if (etBrandName.getText().toString().equals(Constants.KIDS_MAGIC)) {
            selectedBrand = Constants.KIDS_MAGIC;

            //Numeric Value
            if (etDesignCode.getText().toString().equals(Constants.NUMERIC)) {
                isKidsMagicP = false;
                etSelectSize.setText(getString(R.string.boys_tee_8_6));
                llBBabyS3XLParent.setVisibility(View.GONE);
                llBBabyNB912Parent.setVisibility(View.GONE);
                llKidsMagicGT.setVisibility(View.GONE);
                llKMS.setVisibility(View.GONE);
                llBBabyS3XLParent.setVisibility(View.GONE);
                llKidsMagicNumeric.setVisibility(View.GONE);
                llKMSGirls.setVisibility(View.GONE);
                llKidsMagicMN.setVisibility(View.GONE);

                llKidsMagicNumeric.setVisibility(View.VISIBLE);
                isSizeChartVisible = true;
                selectedDesignType = Constants.NUMERIC;

            } else {
                isKidsMagicP = false;
                String kidsMagicQuantity = etDesignCode.getText().toString();

                //MN
                if (kidsMagicQuantity.matches(Constants.MN)) {
                    isKidsMagicP = false;
                    selectedDesignType = Constants.MN;
                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llBBabyNB912Parent.setVisibility(View.GONE);
                    llKidsMagicGT.setVisibility(View.GONE);
                    llKMS.setVisibility(View.GONE);
                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llKidsMagicNumeric.setVisibility(View.GONE);
                    llKMSGirls.setVisibility(View.GONE);

                    llKidsMagicMN.setVisibility(View.VISIBLE);
                    isSizeChartVisible = true;

                    etSelectSize.setText(getString(R.string.boys_tee_2_6));

                    //Girls Top
                } else if (kidsMagicQuantity.matches(Constants.GT)) {
                    isKidsMagicP = false;
                    selectedDesignType = Constants.GT;
                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llBBabyNB912Parent.setVisibility(View.GONE);
                    llKMS.setVisibility(View.GONE);
                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llKidsMagicMN.setVisibility(View.GONE);
                    llKidsMagicNumeric.setVisibility(View.GONE);
                    llKMSGirls.setVisibility(View.GONE);

                    llKidsMagicGT.setVisibility(View.VISIBLE);
                    isSizeChartVisible = true;

                    etSelectSize.setText(getString(R.string.girls_tee_20_36));

                    //Pant
                } else if (kidsMagicQuantity.matches(Constants.PANT)) {

                    etSelectSize.setEnabled(true);
                    etSelectSize.setText("");
                    selectedDesignType = Constants.PANT;
                    isKidsMagicP = true;
                    Helper.showDropDown(etSelectSize, new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, pantArray), new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            etSelectSize.setText(pantArray[position]);
                            if (position == 0) {
                                llBBabyS3XLParent.setVisibility(View.GONE);
                                llBBabyNB912Parent.setVisibility(View.GONE);
                                llKMS.setVisibility(View.GONE);
                                llBBabyS3XLParent.setVisibility(View.GONE);
                                llKidsMagicGT.setVisibility(View.GONE);
                                llKidsMagicNumeric.setVisibility(View.GONE);

                                llKidsMagicMN.setVisibility(View.VISIBLE);
                                llKMSGirls.setVisibility(View.GONE);
                                isSizeChartVisible = true;
                            } else {
                                llBBabyS3XLParent.setVisibility(View.GONE);
                                llBBabyNB912Parent.setVisibility(View.GONE);
                                llKMS.setVisibility(View.GONE);
                                llBBabyS3XLParent.setVisibility(View.GONE);
                                llKidsMagicGT.setVisibility(View.GONE);
                                llKidsMagicMN.setVisibility(View.GONE);
                                llKMSGirls.setVisibility(View.GONE);
                                isSizeChartVisible = true;
                                llKidsMagicNumeric.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    //Kids Magic Suit Boys
                } else if (kidsMagicQuantity.equalsIgnoreCase(Constants.KMSB)) {
                    isKidsMagicP = false;
                    selectedDesignType = Constants.KMSB;
                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llBBabyNB912Parent.setVisibility(View.GONE);
                    etSelectSize.setText(getString(R.string.kmsb));

                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llBBabyNB912Parent.setVisibility(View.GONE);
                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llKidsMagicGT.setVisibility(View.GONE);
                    llKidsMagicMN.setVisibility(View.GONE);
                    llKidsMagicNumeric.setVisibility(View.GONE);
                    llKMSGirls.setVisibility(View.GONE);

                    llKMS.setVisibility(View.VISIBLE);
                    isSizeChartVisible = true;

                    //Kids Magic Suit Girls
                } else if (kidsMagicQuantity.equalsIgnoreCase(Constants.KMSG)) {
                    isKidsMagicP = false;
                    selectedDesignType = Constants.KMSG;
                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llBBabyNB912Parent.setVisibility(View.GONE);
                    etSelectSize.setText(getString(R.string.kmsg));

                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llBBabyNB912Parent.setVisibility(View.GONE);
                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llKidsMagicGT.setVisibility(View.GONE);
                    llKidsMagicMN.setVisibility(View.GONE);
                    llKidsMagicNumeric.setVisibility(View.GONE);
                    llKMS.setVisibility(View.GONE);
                    llKMSGirls.setVisibility(View.VISIBLE);
                    isSizeChartVisible = true;
                }

            }


        } else if (etBrandName.getText().toString().equals(Constants.COTTON_BLUE)) {
            selectedBrand = Constants.COTTON_BLUE;

            //Numeric
            if (etDesignCode.getText().toString().equals(Constants.NUMERIC)) {
                etSelectSize.setText(Constants.DESIGNER);
                selectedDesignType = Constants.NUMERIC;
                llBBabyS3XLParent.setVisibility(View.VISIBLE);
                llSizeS.setVisibility(View.VISIBLE);
                viewS.setVisibility(View.VISIBLE);
                isSizeChartVisible = true;
                llSizeS.setVisibility(View.GONE);
                viewS.setVisibility(View.GONE);

            } else {
                String bBabyQuantity = etDesignCode.getText().toString();

                if (bBabyQuantity.matches(Constants.ST) ||
                        bBabyQuantity.matches(Constants.GM) ||
                        bBabyQuantity.matches(Constants.PR)) {

                    selectedDesignType = bBabyQuantity;

                    String sizeName = "";
                    switch (bBabyQuantity) {
                        case "ST":
                            sizeName = Constants.STRIPE;
                            break;
                        case "GM":
                            sizeName = Constants.GRACE_MARSADISE;
                            break;
                        case "PR":
                            sizeName = Constants.PREMIUM;
                            break;
                    }
                    etSelectSize.setText(sizeName);
                    llBBabyS3XLParent.setVisibility(View.VISIBLE);
                    llSizeS.setVisibility(View.VISIBLE);
                    viewS.setVisibility(View.VISIBLE);
                    isSizeChartVisible = true;
                    llSizeS.setVisibility(View.GONE);
                    viewS.setVisibility(View.GONE);

                }
            }


        }
    }
}
