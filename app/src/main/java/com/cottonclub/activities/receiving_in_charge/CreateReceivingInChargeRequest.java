package com.cottonclub.activities.receiving_in_charge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cottonclub.R;
import com.cottonclub.activities.cutting_in_charge.CuttingInChargeViewJobCardDetails;
import com.cottonclub.models.JobCardItem;
import com.cottonclub.models.SizeListItem;

import java.util.ArrayList;
import java.util.Objects;

public class CreateReceivingInChargeRequest extends AppCompatActivity implements View.OnClickListener {

    private JobCardItem jobCardItem;
    private SizeListItem sizeListItem;
    private String getQuantity, selectedBrand, selectedDesignType, getDesignCode;
    private int position;
    private ArrayList<JobCardItem> jobCardList = new ArrayList<>();
    private EditText etJobCardNumber, etTotalColor1, etTotalColor2, etCheckerName,
            etApprovedQuantityTransferToSetting, etApprovedQuantityTransferToIroning, etUnsetQuantity;
    private Button btnViewJobCardDetails, btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_receving_in_charge_request);
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

        etJobCardNumber = findViewById(R.id.etJobCardNumber);
        etJobCardNumber.setText(String.format("%s:%s", getString(R.string.job_card_number),
                jobCardItem.getJobCardNumber()));

        btnViewJobCardDetails = findViewById(R.id.btnViewJobCardDetails);
        btnViewJobCardDetails.setOnClickListener(this);

        etTotalColor1 = findViewById(R.id.etTotalColor1);
        etTotalColor2 = findViewById(R.id.etTotalColor2);

        etCheckerName = findViewById(R.id.etCheckerName);

        etApprovedQuantityTransferToSetting = findViewById(R.id.etApprovedQuantityTransferToSetting);

        etApprovedQuantityTransferToIroning = findViewById(R.id.etApprovedQuantityTransferToIroning);

        etApprovedQuantityTransferToIroning.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!TextUtils.isEmpty(etApprovedQuantityTransferToIroning.getText().toString()) &&

                        !TextUtils.isEmpty(etApprovedQuantityTransferToSetting.getText().toString())) {

                    int currentAlterAfterPrinting = Integer.parseInt(etApprovedQuantityTransferToIroning.getText().toString().trim()) -
                            Integer.parseInt(etApprovedQuantityTransferToSetting.getText().toString().trim());
                    etUnsetQuantity.setText(String.valueOf(currentAlterAfterPrinting));
                }
            }
        });

        etUnsetQuantity = findViewById(R.id.etUnsetQuantity);

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        TextView tvInit = new TextView(this);
        tvInit.setText("Size");
        tvInit.setTextSize(18);
        tvInit.setPadding(25, 10, 100, 0);
        tvInit.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tbrow0.addView(tvInit);

        TextView tv0 = new TextView(this);
        tv0.setText("2");
        tv0.setPadding(0, 0, 50, 20);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextSize(18);
        tv0.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(this);
        tv1.setText("3");
        tv1.setTextSize(18);
        tv1.setPadding(0, 10, 50, 20);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText("4");
        tv2.setTextSize(18);
        tv2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tv2.setPadding(0, 10, 50, 20);
        tv2.setGravity(Gravity.CENTER);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText("5");
        tv3.setTextSize(18);
        tv3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tv3.setPadding(0, 10, 50, 20);
        tv3.setGravity(Gravity.CENTER);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText("6");
        tv4.setTextSize(18);
        tv4.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tv4.setPadding(0, 10, 50, 20);
        tv4.setGravity(Gravity.CENTER);
        tbrow0.addView(tv4);

        stk.addView(tbrow0);

        for (int i = 0; i < 2; i++) {
            TableRow tbrow = new TableRow(this);

            EditText t2v = new EditText(this);
            if (i == 0) {
                t2v.setText("Color 1");
                t2v.setPadding(25, 50, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            if (i == 1) {
                t2v.setText("Color 2");
                t2v.setPadding(25, 50, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            final EditText et1 = new EditText(this);
            et1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            et1.setWidth(100);
            et1.setInputType(InputType.TYPE_CLASS_NUMBER);
            et1.setSingleLine();
            et1.setPadding(0, 0, 0, 20);
            et1.setGravity(Gravity.CENTER);
            tbrow.addView(et1);

            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) et1.getLayoutParams();
            p.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et1.requestLayout();

            final EditText et2 = new EditText(this);
            et2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            et2.setWidth(100);
            et2.setInputType(InputType.TYPE_CLASS_NUMBER);
            et2.setSingleLine();
            et2.setPadding(0, 0, 0, 20);
            et2.setGravity(Gravity.CENTER);
            tbrow.addView(et2);

            ViewGroup.MarginLayoutParams p2 = (ViewGroup.MarginLayoutParams) et2.getLayoutParams();
            p2.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et2.requestLayout();

            final EditText et3 = new EditText(this);
            et3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            et3.setWidth(100);
            et3.setInputType(InputType.TYPE_CLASS_NUMBER);
            et3.setSingleLine();
            et3.setPadding(0, 0, 0, 20);
            et3.setGravity(Gravity.CENTER);
            tbrow.addView(et3);

            ViewGroup.MarginLayoutParams p3 = (ViewGroup.MarginLayoutParams) et3.getLayoutParams();
            p3.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et3.requestLayout();

            final EditText et4 = new EditText(this);
            et4.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            et4.setWidth(100);
            et4.setInputType(InputType.TYPE_CLASS_NUMBER);
            et4.setSingleLine();
            et4.setPadding(0, 0, 0, 20);
            et4.setGravity(Gravity.CENTER);
            tbrow.addView(et4);

            ViewGroup.MarginLayoutParams p4 = (ViewGroup.MarginLayoutParams) et4.getLayoutParams();
            p4.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et4.requestLayout();

            final EditText et5 = new EditText(this);
            et5.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            et5.setWidth(100);
            et5.setInputType(InputType.TYPE_CLASS_NUMBER);
            et5.setSingleLine();
            et5.setPadding(0, 0, 0, 20);
            et5.setGravity(Gravity.CENTER);
            tbrow.addView(et5);

            ViewGroup.MarginLayoutParams p5 = (ViewGroup.MarginLayoutParams) et5.getLayoutParams();
            p5.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et5.requestLayout();

            stk.addView(tbrow);

            if (i == 0) {
                et5.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!TextUtils.isEmpty(et1.getText().toString()) &&

                                !TextUtils.isEmpty(et2.getText().toString()) &&
                                !TextUtils.isEmpty(et3.getText().toString()) &&
                                !TextUtils.isEmpty(et4.getText().toString()) &&
                                !TextUtils.isEmpty(et4.getText().toString()) &&
                                !TextUtils.isEmpty(et5.getText().toString())) {

                            int totalColor1 = Integer.parseInt(et1.getText().toString().trim())
                                    + Integer.parseInt(et2.getText().toString().trim())
                                    + Integer.parseInt(et3.getText().toString().trim())
                                    + Integer.parseInt(et4.getText().toString().trim())
                                    + Integer.parseInt(et5.getText().toString().trim());

                            etTotalColor1.setText((getString(R.string.total_color_one) + " " + totalColor1));
                        }
                    }
                });
            } else {
                et5.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (!TextUtils.isEmpty(et1.getText().toString()) &&

                                !TextUtils.isEmpty(et2.getText().toString()) &&
                                !TextUtils.isEmpty(et3.getText().toString()) &&
                                !TextUtils.isEmpty(et4.getText().toString()) &&
                                !TextUtils.isEmpty(et4.getText().toString()) &&
                                !TextUtils.isEmpty(et5.getText().toString())) {

                            int totalColor2 = Integer.parseInt(et1.getText().toString().trim())
                                    + Integer.parseInt(et2.getText().toString().trim())
                                    + Integer.parseInt(et3.getText().toString().trim())
                                    + Integer.parseInt(et4.getText().toString().trim())
                                    + Integer.parseInt(et5.getText().toString().trim());

                            etTotalColor2.setText((getString(R.string.total_color_two) + " " + totalColor2));
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
}