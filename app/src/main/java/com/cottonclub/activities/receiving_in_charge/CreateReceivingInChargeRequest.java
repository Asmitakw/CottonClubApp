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
import com.cottonclub.utilities.Constants;

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

        if (selectedBrand.equals(Constants.KIDS_MAGIC)) {

            switch (getDesignCode) {
                case "MN":
                    kidsMagicMN();
                    break;

                case "GT":
                    kidsMagicGT();
                    break;

                case "KMS(B)":
                    kidsMagicBoysSuit();
                    break;

                case "KMS(G)":
                    kidsMagicGirlsSuit();
                    break;

                case "Numeric":
                    kidsMagicPant8by16();
                    break;
            }
        } else if(selectedBrand.equals(Constants.BBABY)){

            //switch ()
        }
    }

    private void kidsMagicMN() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        TextView tvInit = new TextView(this);
        tvInit.setText(R.string.size);
        tvInit.setTextSize(18);
        tvInit.setPadding(25, 15, 100, 0);
        tvInit.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tbrow0.addView(tvInit);

        TextView tv0 = new TextView(this);
        customTextView(tv0, tbrow0, "2");

        TextView tv1 = new TextView(this);
        customTextView(tv1, tbrow0, "3");

        TextView tv2 = new TextView(this);
        customTextView(tv2, tbrow0, "4");

        TextView tv3 = new TextView(this);
        customTextView(tv3, tbrow0, "5");

        TextView tv4 = new TextView(this);
        customTextView(tv4, tbrow0, "6");

        stk.addView(tbrow0);

        for (int i = 0; i < 2; i++) {
            TableRow tbrow = new TableRow(this);

            EditText t2v = new EditText(this);
            if (i == 0) {
                t2v.setText("Color 1");
                t2v.setPadding(25, 55, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            if (i == 1) {
                t2v.setText("Color 2");
                t2v.setPadding(25, 55, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            final EditText et1 = new EditText(this);
            customEditText(et1, tbrow);

            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) et1.getLayoutParams();
            p.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et1.requestLayout();

            final EditText et2 = new EditText(this);
            customEditText(et2, tbrow);

            ViewGroup.MarginLayoutParams p2 = (ViewGroup.MarginLayoutParams) et2.getLayoutParams();
            p2.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et2.requestLayout();

            final EditText et3 = new EditText(this);
            customEditText(et3, tbrow);

            ViewGroup.MarginLayoutParams p3 = (ViewGroup.MarginLayoutParams) et3.getLayoutParams();
            p3.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et3.requestLayout();

            final EditText et4 = new EditText(this);
            customEditText(et4, tbrow);

            ViewGroup.MarginLayoutParams p4 = (ViewGroup.MarginLayoutParams) et4.getLayoutParams();
            p4.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et4.requestLayout();

            final EditText et5 = new EditText(this);
            customEditText(et5, tbrow);

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

    private void kidsMagicGT() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        TextView tvInit = new TextView(this);
        tvInit.setText(R.string.size);
        tvInit.setTextSize(18);
        tvInit.setPadding(25, 15, 100, 0);
        tvInit.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tbrow0.addView(tvInit);

        TextView tv0 = new TextView(this);
        customTextView(tv0, tbrow0, "20");

        TextView tv1 = new TextView(this);
        customTextView(tv1, tbrow0, "22");

        TextView tv2 = new TextView(this);
        customTextView(tv2, tbrow0, "24");

        TextView tv3 = new TextView(this);
        customTextView(tv3, tbrow0, "26");

        TextView tv4 = new TextView(this);
        customTextView(tv4, tbrow0, "28");

        TextView tv5 = new TextView(this);
        customTextView(tv5, tbrow0, "30");

        TextView tv6 = new TextView(this);
        customTextView(tv6, tbrow0, "32");

        TextView tv7 = new TextView(this);
        customTextView(tv7, tbrow0, "34");

        stk.addView(tbrow0);

        for (int i = 0; i < 2; i++) {
            TableRow tbrow = new TableRow(this);

            EditText t2v = new EditText(this);
            if (i == 0) {
                t2v.setText("Color 1");
                t2v.setPadding(25, 55, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            if (i == 1) {
                t2v.setText("Color 2");
                t2v.setPadding(25, 55, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            final EditText et1 = new EditText(this);
            customEditText(et1, tbrow);

            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) et1.getLayoutParams();
            p.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et1.requestLayout();

            final EditText et2 = new EditText(this);
            customEditText(et2, tbrow);

            ViewGroup.MarginLayoutParams p2 = (ViewGroup.MarginLayoutParams) et2.getLayoutParams();
            p2.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et2.requestLayout();

            final EditText et3 = new EditText(this);
            customEditText(et3, tbrow);

            ViewGroup.MarginLayoutParams p3 = (ViewGroup.MarginLayoutParams) et3.getLayoutParams();
            p3.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et3.requestLayout();

            final EditText et4 = new EditText(this);
            customEditText(et4, tbrow);

            ViewGroup.MarginLayoutParams p4 = (ViewGroup.MarginLayoutParams) et4.getLayoutParams();
            p4.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et4.requestLayout();

            final EditText et5 = new EditText(this);
            customEditText(et5, tbrow);

            ViewGroup.MarginLayoutParams p5 = (ViewGroup.MarginLayoutParams) et5.getLayoutParams();
            p5.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et5.requestLayout();

            final EditText et6 = new EditText(this);
            customEditText(et6, tbrow);

            ViewGroup.MarginLayoutParams p6 = (ViewGroup.MarginLayoutParams) et6.getLayoutParams();
            p6.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et6.requestLayout();

            final EditText et7 = new EditText(this);
            customEditText(et7, tbrow);

            ViewGroup.MarginLayoutParams p7 = (ViewGroup.MarginLayoutParams) et7.getLayoutParams();
            p7.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et7.requestLayout();

            final EditText et8 = new EditText(this);
            customEditText(et8, tbrow);

            ViewGroup.MarginLayoutParams p8 = (ViewGroup.MarginLayoutParams) et8.getLayoutParams();
            p8.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et8.requestLayout();

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

    private void kidsMagicPant8by16() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        TextView tvInit = new TextView(this);
        tvInit.setText(R.string.size);
        tvInit.setTextSize(18);
        tvInit.setPadding(25, 15, 100, 0);
        tvInit.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tbrow0.addView(tvInit);

        TextView tv0 = new TextView(this);
        customTextView(tv0, tbrow0, "8");

        TextView tv1 = new TextView(this);
        customTextView(tv1, tbrow0, "10");

        TextView tv2 = new TextView(this);
        customTextView(tv2, tbrow0, "12");

        TextView tv3 = new TextView(this);
        customTextView(tv3, tbrow0, "14");

        TextView tv4 = new TextView(this);
        customTextView(tv4, tbrow0, "16");

        stk.addView(tbrow0);

        for (int i = 0; i < 2; i++) {
            TableRow tbrow = new TableRow(this);

            EditText t2v = new EditText(this);
            if (i == 0) {
                t2v.setText("Color 1");
                t2v.setPadding(25, 55, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            if (i == 1) {
                t2v.setText("Color 2");
                t2v.setPadding(25, 55, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            final EditText et1 = new EditText(this);
            customEditText(et1, tbrow);

            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) et1.getLayoutParams();
            p.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et1.requestLayout();

            final EditText et2 = new EditText(this);
            customEditText(et2, tbrow);

            ViewGroup.MarginLayoutParams p2 = (ViewGroup.MarginLayoutParams) et2.getLayoutParams();
            p2.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et2.requestLayout();

            final EditText et3 = new EditText(this);
            customEditText(et3, tbrow);

            ViewGroup.MarginLayoutParams p3 = (ViewGroup.MarginLayoutParams) et3.getLayoutParams();
            p3.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et3.requestLayout();

            final EditText et4 = new EditText(this);
            customEditText(et4, tbrow);

            ViewGroup.MarginLayoutParams p4 = (ViewGroup.MarginLayoutParams) et4.getLayoutParams();
            p4.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et4.requestLayout();

            final EditText et5 = new EditText(this);
            customEditText(et5, tbrow);

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

    private void kidsMagicBoysSuit() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        TextView tvInit = new TextView(this);
        tvInit.setText(R.string.size);
        tvInit.setTextSize(18);
        tvInit.setPadding(25, 15, 100, 0);
        tvInit.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tbrow0.addView(tvInit);

        TextView tv0 = new TextView(this);
        customTextView(tv0, tbrow0, "1");

        TextView tv1 = new TextView(this);
        customTextView(tv1, tbrow0, "2");

        TextView tv2 = new TextView(this);
        customTextView(tv2, tbrow0, "3");

        TextView tv3 = new TextView(this);
        customTextView(tv3, tbrow0, "4");

        TextView tv4 = new TextView(this);
        customTextView(tv4, tbrow0, "5");

        stk.addView(tbrow0);

        for (int i = 0; i < 2; i++) {
            TableRow tbrow = new TableRow(this);

            EditText t2v = new EditText(this);
            if (i == 0) {
                t2v.setText("Color 1");
                t2v.setPadding(25, 55, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            if (i == 1) {
                t2v.setText("Color 2");
                t2v.setPadding(25, 55, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            final EditText et1 = new EditText(this);
            customEditText(et1, tbrow);

            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) et1.getLayoutParams();
            p.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et1.requestLayout();

            final EditText et2 = new EditText(this);
            customEditText(et2, tbrow);

            ViewGroup.MarginLayoutParams p2 = (ViewGroup.MarginLayoutParams) et2.getLayoutParams();
            p2.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et2.requestLayout();

            final EditText et3 = new EditText(this);
            customEditText(et3, tbrow);

            ViewGroup.MarginLayoutParams p3 = (ViewGroup.MarginLayoutParams) et3.getLayoutParams();
            p3.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et3.requestLayout();

            final EditText et4 = new EditText(this);
            customEditText(et4, tbrow);

            ViewGroup.MarginLayoutParams p4 = (ViewGroup.MarginLayoutParams) et4.getLayoutParams();
            p4.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et4.requestLayout();

            final EditText et5 = new EditText(this);
            customEditText(et5, tbrow);

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

    private void kidsMagicGirlsSuit() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        TextView tvInit = new TextView(this);
        tvInit.setText(R.string.size);
        tvInit.setTextSize(18);
        tvInit.setPadding(25, 15, 100, 0);
        tvInit.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tbrow0.addView(tvInit);

        TextView tv0 = new TextView(this);
        customTextView(tv0, tbrow0, "16");

        TextView tv1 = new TextView(this);
        customTextView(tv1, tbrow0, "18");

        TextView tv2 = new TextView(this);
        customTextView(tv2, tbrow0, "20");

        TextView tv3 = new TextView(this);
        customTextView(tv3, tbrow0, "22");

        TextView tv4 = new TextView(this);
        customTextView(tv4, tbrow0, "24");

        stk.addView(tbrow0);

        for (int i = 0; i < 2; i++) {
            TableRow tbrow = new TableRow(this);

            EditText t2v = new EditText(this);
            if (i == 0) {
                t2v.setText("Color 1");
                t2v.setPadding(25, 55, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            if (i == 1) {
                t2v.setText("Color 2");
                t2v.setPadding(25, 55, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            final EditText et1 = new EditText(this);
            customEditText(et1, tbrow);

            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) et1.getLayoutParams();
            p.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et1.requestLayout();

            final EditText et2 = new EditText(this);
            customEditText(et2, tbrow);

            ViewGroup.MarginLayoutParams p2 = (ViewGroup.MarginLayoutParams) et2.getLayoutParams();
            p2.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et2.requestLayout();

            final EditText et3 = new EditText(this);
            customEditText(et3, tbrow);

            ViewGroup.MarginLayoutParams p3 = (ViewGroup.MarginLayoutParams) et3.getLayoutParams();
            p3.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et3.requestLayout();

            final EditText et4 = new EditText(this);
            customEditText(et4, tbrow);

            ViewGroup.MarginLayoutParams p4 = (ViewGroup.MarginLayoutParams) et4.getLayoutParams();
            p4.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et4.requestLayout();

            final EditText et5 = new EditText(this);
            customEditText(et5, tbrow);

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

    private void beBabyDefault(){
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        TextView tvInit = new TextView(this);
        tvInit.setText(R.string.size);
        tvInit.setTextSize(18);
        tvInit.setPadding(25, 15, 100, 0);
        tvInit.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        tbrow0.addView(tvInit);

        TextView tv0 = new TextView(this);
        customTextView(tv0, tbrow0, "S");

        TextView tv1 = new TextView(this);
        customTextView(tv1, tbrow0, "M");

        TextView tv2 = new TextView(this);
        customTextView(tv2, tbrow0, "L");

        TextView tv3 = new TextView(this);
        customTextView(tv3, tbrow0, "XL");

        TextView tv4 = new TextView(this);
        customTextView(tv4, tbrow0, "XXL");

        TextView tv5 = new TextView(this);
        customTextView(tv5, tbrow0, "XXXL");

        stk.addView(tbrow0);

        for (int i = 0; i < 2; i++) {
            TableRow tbrow = new TableRow(this);

            EditText t2v = new EditText(this);
            if (i == 0) {
                t2v.setText("Color 1");
                t2v.setPadding(25, 55, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            if (i == 1) {
                t2v.setText("Color 2");
                t2v.setPadding(25, 55, 50, 0);
                t2v.setBackground(null);
                t2v.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tbrow.addView(t2v);
            }

            final EditText et1 = new EditText(this);
            customEditText(et1, tbrow);

            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) et1.getLayoutParams();
            p.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et1.requestLayout();

            final EditText et2 = new EditText(this);
            customEditText(et2, tbrow);

            ViewGroup.MarginLayoutParams p2 = (ViewGroup.MarginLayoutParams) et2.getLayoutParams();
            p2.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et2.requestLayout();

            final EditText et3 = new EditText(this);
            customEditText(et3, tbrow);

            ViewGroup.MarginLayoutParams p3 = (ViewGroup.MarginLayoutParams) et3.getLayoutParams();
            p3.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et3.requestLayout();

            final EditText et4 = new EditText(this);
            customEditText(et4, tbrow);

            ViewGroup.MarginLayoutParams p4 = (ViewGroup.MarginLayoutParams) et4.getLayoutParams();
            p4.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et4.requestLayout();

            final EditText et5 = new EditText(this);
            customEditText(et5, tbrow);

            ViewGroup.MarginLayoutParams p5 = (ViewGroup.MarginLayoutParams) et5.getLayoutParams();
            p5.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et5.requestLayout();

            final EditText et6 = new EditText(this);
            customEditText(et6, tbrow);

            ViewGroup.MarginLayoutParams p6 = (ViewGroup.MarginLayoutParams) et6.getLayoutParams();
            p6.setMargins(10, 0, 50, 100); // You can change the margins for your need
            et6.requestLayout();

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

    private void customEditText(EditText editText, TableRow tableRow) {
        editText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        editText.setWidth(100);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setSingleLine();
        editText.setPadding(0, 0, 0, 20);
        editText.setGravity(Gravity.CENTER);
        tableRow.addView(editText);
    }

    private void customTextView(TextView textView, TableRow tableRow, String value) {
        textView.setText(value);
        textView.setTextSize(18);
        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        textView.setPadding(0, 10, 50, 20);
        textView.setGravity(Gravity.CENTER);
        tableRow.addView(textView);
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