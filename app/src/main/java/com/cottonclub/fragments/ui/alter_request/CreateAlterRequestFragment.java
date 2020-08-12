package com.cottonclub.fragments.ui.alter_request;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cottonclub.R;
import com.cottonclub.models.AlterRequestItem;
import com.cottonclub.models.OrderItem;
import com.cottonclub.models.SizeListItem;
import com.cottonclub.utilities.Constants;
import com.cottonclub.utilities.Helper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class CreateAlterRequestFragment extends Fragment implements View.OnClickListener {

    private EditText etDesignNumber, etAlterQuantity, etMasterName, etCuttingIssueDate,
            etParts, etOtherParts, etBrandName, etDesignCode, etSelectSize;

    private TextView tvDateOrderCreation;

    private Button btnCreateAlterRequest;
    private long maxId = 0;
    private AlterRequestItem alterRequestItem;
    private Dialog mDialog;
    private DatePickerDialog datePickerDialog;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference alterRequestRef = mRootRef.child("AlterRequest");
    private String[] partsArray;
    private LinearLayout llOtherParts;

    private String[] brandArray;
    private String selectedBrand;
    private String selectedDesignType;
    private String[] typeArray;
    private String[] pantArray;
    private String[] kidsMagicDesignTypeArray;
    private String[] bBabyDesignTypeArray;
    private String[] cottonBlueDesignTypeArray;
    private LinearLayout llBBabyS3XLParent, llBBabyNB912Parent, llKidsMagicMN,
            llKidsMagicNumeric, llKidsMagicGT, llKMS, llSizeS, llKMSGirls;
    private EditText etKidsMagicMNSize2, etKidsMagicMNSize3, etKidsMagicMNSize4, etKidsMagicMNSize5,
            etKidsMagicMNSize6, etKidsMagicNumeric8, etKidsMagicNumeric10,
            etKidsMagicNumeric12, etKidsMagicNumeric14, etKidsMagicNumeric16, etKidsMagicGT20,
            etKidsMagicGT22, etKidsMagicGT24, etKidsMagicGT26, etKidsMagicGT28, etKidsMagicGT30,
            etKidsMagicGT32, etKidsMagicGT34, etKidsMagicGT36, etKMS1, etKMS2, etKMS3, etKMS4, etKMS5,
            etKMSG1, etKMSG2, etKMSG3, etKMSG4, etKMSG5,
            etTotalNumberPieces, etBbabySizeS, etBbabySizeM, etBbabySizeL, etBbabySizeXL, etBbabySizeXXL,
            etBbabySizeXXXL, etBbabyNB, etBbaby03, etBbaby36, etBbaby69, etBbaby912;
    private boolean isKidsMagicP = false;
    private boolean isSizeChartVisible = false;
    private boolean isOtherPartsDetailsVisible = false;
    private TextWatcher textWatcher;
    private View viewS;
    private SizeListItem sizeListItem;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_alter_request, container, false);
        initialise(root);
        return root;
    }

    private void initialise(View view) {

        if (brandArray == null)
            brandArray = getResources().getStringArray(R.array.brand);

        if (typeArray == null)
            typeArray = getResources().getStringArray(R.array.type);

        if (pantArray == null)
            pantArray = getResources().getStringArray(R.array.pant);

        if (kidsMagicDesignTypeArray == null)
            kidsMagicDesignTypeArray = getResources().getStringArray(R.array.kidsMagicDesignType);

        if (bBabyDesignTypeArray == null)
            bBabyDesignTypeArray = getResources().getStringArray(R.array.bBabyDesignType);

        if (cottonBlueDesignTypeArray == null)
            cottonBlueDesignTypeArray = getResources().getStringArray(R.array.cottonBlueDesignType);

        tvDateOrderCreation = view.findViewById(R.id.tvDateOrderCreation);
        tvDateOrderCreation.setText(Helper.getCurrentTime());


        etBrandName = view.findViewById(R.id.etBrandName);
        etBrandName.setOnClickListener(this);

        etDesignNumber = view.findViewById(R.id.etDesignNumber);

        etSelectSize = view.findViewById(R.id.etSelectSize);
        etSelectSize.setOnClickListener(this);
        etSelectSize.setEnabled(false);

        etKidsMagicMNSize2 = view.findViewById(R.id.etKidsMagicMNSize2);

        etKidsMagicMNSize3 = view.findViewById(R.id.etKidsMagicMNSize3);
        etKidsMagicMNSize4 = view.findViewById(R.id.etKidsMagicMNSize4);
        etKidsMagicMNSize5 = view.findViewById(R.id.etKidsMagicMNSize5);
        etKidsMagicMNSize6 = view.findViewById(R.id.etKidsMagicMNSize6);

        etKidsMagicNumeric8 = view.findViewById(R.id.etKidsMagicNumeric8);
        etKidsMagicNumeric10 = view.findViewById(R.id.etKidsMagicNumeric10);
        etKidsMagicNumeric12 = view.findViewById(R.id.etKidsMagicNumeric12);
        etKidsMagicNumeric14 = view.findViewById(R.id.etKidsMagicNumeric14);
        etKidsMagicNumeric16 = view.findViewById(R.id.etKidsMagicNumeric16);

        etKidsMagicGT20 = view.findViewById(R.id.etKidsMagicGT20);
        etKidsMagicGT22 = view.findViewById(R.id.etKidsMagicGT22);
        etKidsMagicGT24 = view.findViewById(R.id.etKidsMagicGT24);
        etKidsMagicGT26 = view.findViewById(R.id.etKidsMagicGT26);
        etKidsMagicGT28 = view.findViewById(R.id.etKidsMagicGT28);
        etKidsMagicGT30 = view.findViewById(R.id.etKidsMagicGT30);
        etKidsMagicGT32 = view.findViewById(R.id.etKidsMagicGT32);
        etKidsMagicGT34 = view.findViewById(R.id.etKidsMagicGT34);
        etKidsMagicGT36 = view.findViewById(R.id.etKidsMagicGT36);

        etBbabySizeS = view.findViewById(R.id.etBbabySizeS);
        etBbabySizeM = view.findViewById(R.id.etBbabySizeM);
        etBbabySizeL = view.findViewById(R.id.etBbabySizeL);
        etBbabySizeXL = view.findViewById(R.id.etBbabySizeXL);
        etBbabySizeXXL = view.findViewById(R.id.etBbabySizeXXL);
        etBbabySizeXXXL = view.findViewById(R.id.etBbabySizeXXXL);

        etBbabyNB = view.findViewById(R.id.etBbabyNB);
        etBbaby03 = view.findViewById(R.id.etBbaby03);
        etBbaby36 = view.findViewById(R.id.etBbaby36);
        etBbaby69 = view.findViewById(R.id.etBbaby69);
        etBbaby912 = view.findViewById(R.id.etBbaby912);

        etTotalNumberPieces = view.findViewById(R.id.etTotalNumberPieces);

        etKMS1 = view.findViewById(R.id.etKMS1);
        etKMS2 = view.findViewById(R.id.etKMS2);
        etKMS3 = view.findViewById(R.id.etKMS3);
        etKMS4 = view.findViewById(R.id.etKMS4);
        etKMS5 = view.findViewById(R.id.etKMS5);

        etKMSG1 = view.findViewById(R.id.etKMSG1);
        etKMSG2 = view.findViewById(R.id.etKMSG2);
        etKMSG3 = view.findViewById(R.id.etKMSG3);
        etKMSG4 = view.findViewById(R.id.etKMSG4);
        etKMSG5 = view.findViewById(R.id.etKMSG5);

        llBBabyS3XLParent = view.findViewById(R.id.llBBabyS3XLParent);
        llBBabyNB912Parent = view.findViewById(R.id.llBBabyNB912Parent);

        llKidsMagicMN = view.findViewById(R.id.llKidsMagicMN);
        llKidsMagicNumeric = view.findViewById(R.id.llKidsMagicNumeric);
        llKidsMagicGT = view.findViewById(R.id.llKidsMagicGT);
        llKMS = view.findViewById(R.id.llKMS);

        llKMSGirls = view.findViewById(R.id.llKMSGirls);

        llSizeS = view.findViewById(R.id.llSizeS);
        viewS = view.findViewById(R.id.viewS);

        etDesignCode = view.findViewById(R.id.etDesignCode);
        etDesignCode.setOnClickListener(this);

        if (partsArray == null)
            partsArray = getResources().getStringArray(R.array.parts);

        tvDateOrderCreation = view.findViewById(R.id.tvDateOrderCreation);
        tvDateOrderCreation.setText(Helper.getCurrentTime());

        etDesignNumber = view.findViewById(R.id.etDesignNumber);
        etAlterQuantity = view.findViewById(R.id.etAlterQuantity);

        etMasterName = view.findViewById(R.id.etMasterName);

        etCuttingIssueDate = view.findViewById(R.id.etCuttingIssueDate);
        etCuttingIssueDate.setOnClickListener(this);

        etParts = view.findViewById(R.id.etParts);
        etParts.setOnClickListener(this);

        llOtherParts = view.findViewById(R.id.llOtherParts);

        etOtherParts = view.findViewById(R.id.etOtherParts);

        etBrandName = view.findViewById(R.id.etBrandName);

        etDesignCode = view.findViewById(R.id.etDesignCode);
        etDesignCode.setOnClickListener(this);

        etSelectSize = view.findViewById(R.id.etSelectSize);
        etSelectSize.setOnClickListener(this);

        btnCreateAlterRequest = view.findViewById(R.id.btnCreateAlterRequest);
        alterRequestItem = new AlterRequestItem();

        setDefaultSizeByQuantity();
        setUiByBrand();
    }

    private void setDefaultSizeByQuantity() {
        etAlterQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String getDefaultQuantity = etAlterQuantity.getText().toString();

                if (etAlterQuantity.getText().toString().length() > 0) {

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

                    if (etAlterQuantity.getText().toString().length() > 1 || etAlterQuantity.getText().toString().length() == 1) {
                        int getQuantity = Integer.parseInt(etAlterQuantity.getText().toString());

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
                    etTotalNumberPieces.setText("");
                }
            }
        };
    }

    @SuppressLint("SetTextI18n")
    private void getTotalNoPieces() {
        if (!etAlterQuantity.getText().toString().equals("")) {

            setDefaultZero();

            if (etAlterQuantity.getText().toString().length() > 1) {

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
                    if (!TextUtils.isEmpty(etBbabySizeM.getText().toString().trim())
                            || !TextUtils.isEmpty(etBbabySizeL.getText().toString().trim())
                            || !TextUtils.isEmpty(etBbabySizeXL.getText().toString().trim())
                            || !TextUtils.isEmpty(etBbabySizeXXL.getText().toString().trim())
                            || !TextUtils.isEmpty(etBbabySizeXXXL.getText().toString().trim())) {

                        int answer = Integer.parseInt(etBbabySizeM.getText().toString().trim()) +
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

    private void setUiByBrand() {
        etDesignCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*if (etDesignNumber.getText().toString().equals("")) {
                    clearSizeFields();
                    etSelectSize.setText("");
                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llBBabyNB912Parent.setVisibility(View.GONE);
                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llBBabyNB912Parent.setVisibility(View.GONE);
                    llKidsMagicGT.setVisibility(View.GONE);
                    llKMS.setVisibility(View.GONE);
                    llBBabyS3XLParent.setVisibility(View.GONE);
                    llKidsMagicNumeric.setVisibility(View.GONE);
                    llKidsMagicMN.setVisibility(View.GONE);
                }*/
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
                        Helper.showOkDialog(getActivity(), getString(R.string.please_enter_valid_quantity_number));
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
                            Helper.showDropDown(etSelectSize, new ArrayAdapter<>(requireActivity(),
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

    private void clearSizeFields() {
        etKidsMagicMNSize2.setText("0");

        etKidsMagicMNSize3.setText("0");

        etKidsMagicMNSize4.setText("0");

        etKidsMagicMNSize5.setText("0");

        etKidsMagicMNSize6.setText("0");

        etKidsMagicNumeric8.setText("0");

        etKidsMagicNumeric10.setText("0");

        etKidsMagicNumeric12.setText("0");

        etKidsMagicNumeric14.setText("0");

        etKidsMagicNumeric16.setText("0");

        etKidsMagicGT20.setText("0");

        etKidsMagicGT22.setText("0");

        etKidsMagicGT24.setText("0");

        etKidsMagicGT26.setText("0");

        etKidsMagicGT28.setText("0");

        etKidsMagicGT30.setText("0");

        etKidsMagicGT32.setText("0");

        etKidsMagicGT34.setText("0");

        etKidsMagicGT36.setText("0");

        etKMS1.setText("0");
        etKMS2.setText("0");
        etKMS3.setText("0");
        etKMS4.setText("0");
        etKMS5.setText("0");

        etKMSG1.setText("0");
        etKMSG2.setText("0");
        etKMSG3.setText("0");
        etKMSG4.setText("0");
        etKMSG5.setText("0");

        etBbabySizeS.setText("0");

        etBbabySizeM.setText("0");

        etBbabySizeL.setText("0");

        etBbabySizeXL.setText("0");

        etBbabySizeXXL.setText("0");

        etBbabySizeXXXL.setText("0");

        etBbabyNB.setText("0");

        etBbaby03.setText("0");

        etBbaby36.setText("0");

        etBbaby69.setText("0");

        etBbaby912.setText("0");
    }

    private void sendSizeDetails() {
        if (selectedBrand.equals(Constants.BBABY)) {
            if (selectedDesignType.equals("BBB") || selectedDesignType.equals("BBF") ||
                    selectedDesignType.equals("BBG") || selectedDesignType.equals("BBS") ||
                    selectedDesignType.equals("BT") || selectedDesignType.equals("BBNSG") ||
                    selectedDesignType.equals("BBNSB") || selectedDesignType.equals("GT")) {

                sizeListItem = new SizeListItem();

                sizeListItem.setS(etBbabySizeS.getText().toString());
                sizeListItem.setM(etBbabySizeM.getText().toString());
                sizeListItem.setL(etBbabySizeL.getText().toString());
                sizeListItem.setXL(etBbabySizeXL.getText().toString());
                sizeListItem.setXXL(etBbabySizeXXL.getText().toString());
                sizeListItem.setXXXL(etBbabySizeXXXL.getText().toString());
                sizeListItem.setDesignType(selectedDesignType);

            } else if (selectedDesignType.equals("BBGR") || selectedDesignType.equals("BBBR")) {
                sizeListItem = new SizeListItem();

                sizeListItem.setNB(etBbabyNB.getText().toString());
                sizeListItem.setSize0b3(etBbaby03.getText().toString());
                sizeListItem.setSize3b6(etBbaby36.getText().toString());
                sizeListItem.setSize6b9(etBbaby69.getText().toString());
                sizeListItem.setSize9b12(etBbaby912.getText().toString());
                sizeListItem.setDesignType(selectedDesignType);

            }


        } else if (selectedBrand.equals(Constants.KIDS_MAGIC)) {

            if (selectedDesignType.equals(Constants.MN)) {

                sizeListItem = new SizeListItem();
                sizeListItem.setSize2(etKidsMagicMNSize2.getText().toString());
                sizeListItem.setSize3(etKidsMagicMNSize3.getText().toString());
                sizeListItem.setSize4(etKidsMagicMNSize4.getText().toString());
                sizeListItem.setSize5(etKidsMagicMNSize5.getText().toString());
                sizeListItem.setSize6(etKidsMagicMNSize6.getText().toString());
                sizeListItem.setDesignType(selectedDesignType);

            } else if (selectedDesignType.equals(Constants.NUMERIC)) {
                sizeListItem = new SizeListItem();
                sizeListItem.setSize8(etKidsMagicNumeric8.getText().toString());
                sizeListItem.setSize10(etKidsMagicNumeric10.getText().toString());
                sizeListItem.setSize12(etKidsMagicNumeric12.getText().toString());
                sizeListItem.setSize14(etKidsMagicNumeric14.getText().toString());
                sizeListItem.setSize16(etKidsMagicNumeric16.getText().toString());
                sizeListItem.setDesignType(selectedDesignType);

            } else if (selectedDesignType.equals(Constants.PANT)) {
                if (etSelectSize.getText().toString().equals(getString(R.string.pant_2_6))) {
                    sizeListItem = new SizeListItem();
                    sizeListItem.setSize2(etKidsMagicMNSize2.getText().toString());
                    sizeListItem.setSize3(etKidsMagicMNSize3.getText().toString());
                    sizeListItem.setSize4(etKidsMagicMNSize4.getText().toString());
                    sizeListItem.setSize5(etKidsMagicMNSize5.getText().toString());
                    sizeListItem.setSize6(etKidsMagicMNSize6.getText().toString());
                    sizeListItem.setDesignType(selectedDesignType);

                } else if (etSelectSize.getText().toString().equals(getString(R.string.pant_8_16))) {
                    sizeListItem = new SizeListItem();
                    sizeListItem.setSize8(etKidsMagicNumeric8.getText().toString());
                    sizeListItem.setSize10(etKidsMagicNumeric10.getText().toString());
                    sizeListItem.setSize12(etKidsMagicNumeric12.getText().toString());
                    sizeListItem.setSize14(etKidsMagicNumeric14.getText().toString());
                    sizeListItem.setSize16(etKidsMagicNumeric16.getText().toString());
                    sizeListItem.setDesignType(selectedDesignType);
                }
            } else if (selectedDesignType.equals(Constants.GT)) {
                sizeListItem = new SizeListItem();
                sizeListItem.setSize20(etKidsMagicGT20.getText().toString());
                sizeListItem.setSize22(etKidsMagicGT22.getText().toString());
                sizeListItem.setSize24(etKidsMagicGT24.getText().toString());
                sizeListItem.setSize26(etKidsMagicGT26.getText().toString());
                sizeListItem.setSize28(etKidsMagicGT28.getText().toString());
                sizeListItem.setSize30(etKidsMagicGT30.getText().toString());
                sizeListItem.setSize32(etKidsMagicGT32.getText().toString());
                sizeListItem.setSize34(etKidsMagicGT34.getText().toString());
                sizeListItem.setSize36(etKidsMagicGT36.getText().toString());
                sizeListItem.setDesignType(selectedDesignType);

            } else if (selectedDesignType.equals(Constants.KMSB)) {
                sizeListItem = new SizeListItem();
                sizeListItem.setSize1(etKMS1.getText().toString());
                sizeListItem.setSize2(etKMS2.getText().toString());
                sizeListItem.setSize3(etKMS3.getText().toString());
                sizeListItem.setSize4(etKMS4.getText().toString());
                sizeListItem.setSize5(etKMS5.getText().toString());
                sizeListItem.setDesignType(selectedDesignType);
            } else if (selectedDesignType.equals(Constants.KMSG)) {
                sizeListItem = new SizeListItem();
                sizeListItem.setSize16(etKMSG1.getText().toString());
                sizeListItem.setSize18(etKMSG2.getText().toString());
                sizeListItem.setSize20(etKMSG3.getText().toString());
                sizeListItem.setSize22(etKMSG4.getText().toString());
                sizeListItem.setSize24(etKMSG5.getText().toString());
                sizeListItem.setDesignType(selectedDesignType);
            }


        } else if (selectedBrand.equals(Constants.COTTON_BLUE)) {
            sizeListItem = new SizeListItem();
            sizeListItem.setM(etBbabySizeM.getText().toString());
            sizeListItem.setL(etBbabySizeL.getText().toString());
            sizeListItem.setXL(etBbabySizeXL.getText().toString());
            sizeListItem.setXXL(etBbabySizeXXL.getText().toString());
            sizeListItem.setXXXL(etBbabySizeXXXL.getText().toString());
            sizeListItem.setDesignType(selectedDesignType);

        }

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

            case R.id.etBrandName:
                Helper.showDropDown(etBrandName, new ArrayAdapter<>(requireActivity(),
                        android.R.layout.simple_list_item_1, brandArray), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        clearData();
                        clearSizeFields();
                        etBrandName.setText(brandArray[position]);
                        if (position == 0) {
                            selectedBrand = Constants.KIDS_MAGIC;
                        } else if (position == 1) {
                            selectedBrand = Constants.BBABY;
                        } else {
                            selectedBrand = Constants.COTTON_BLUE;
                        }
                    }
                });
                break;
            case R.id.etDesignCode:
                if (selectedBrand.equals(Constants.KIDS_MAGIC)) {
                    Helper.showDropDown(etDesignCode, new ArrayAdapter<>(requireActivity(),
                            android.R.layout.simple_list_item_1, kidsMagicDesignTypeArray), new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            etDesignCode.setText(kidsMagicDesignTypeArray[position]);
                        }
                    });
                } else if (selectedBrand.equals(Constants.BBABY)) {
                    Helper.showDropDown(etDesignCode, new ArrayAdapter<>(requireActivity(),
                            android.R.layout.simple_list_item_1, bBabyDesignTypeArray), new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            etDesignCode.setText(bBabyDesignTypeArray[position]);
                        }
                    });

                } else if (selectedBrand.equals(Constants.COTTON_BLUE)) {
                    Helper.showDropDown(etDesignCode, new ArrayAdapter<>(requireActivity(),
                            android.R.layout.simple_list_item_1, cottonBlueDesignTypeArray), new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            etDesignCode.setText(cottonBlueDesignTypeArray[position]);
                        }
                    });
                }

                break;
            case R.id.etSelectSize:
                if (isKidsMagicP) {
                    Helper.showDropDown(etSelectSize, new ArrayAdapter<>(requireActivity(),
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
                            } else {
                                llBBabyS3XLParent.setVisibility(View.GONE);
                                llBBabyNB912Parent.setVisibility(View.GONE);
                                llKMS.setVisibility(View.GONE);
                                llBBabyS3XLParent.setVisibility(View.GONE);
                                llKidsMagicGT.setVisibility(View.GONE);
                                llKidsMagicMN.setVisibility(View.GONE);

                                llKidsMagicNumeric.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
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

            case R.id.etParts:
                Helper.showDropDown(etParts, new ArrayAdapter<>(getActivity(),
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

    private void validate() {

        if (TextUtils.isEmpty(etBrandName.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_brand_name));
            etBrandName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etDesignCode.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.design_code));
            etDesignCode.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etDesignNumber.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_design_number));
            etDesignNumber.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etParts.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_select_parts));
            etParts.requestFocus();
            return;
        }

        if (isOtherPartsDetailsVisible) {
            if (TextUtils.isEmpty(etOtherParts.getText().toString().trim())) {
                Helper.showOkDialog(getActivity(), getString(R.string.please_select_other_parts));
                etOtherParts.requestFocus();
                return;
            }
        }

        if (TextUtils.isEmpty(etAlterQuantity.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_alter_quantity));
            etAlterQuantity.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etSelectSize.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_select_size));
            etSelectSize.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etTotalNumberPieces.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_total_no_pieces));
            etTotalNumberPieces.requestFocus();
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

    private void clearData() {
        etBrandName.setText("");
        etDesignCode.setText("");
        etDesignNumber.setText("");
        etParts.setText("");
        etOtherParts.setText("");
        etCuttingIssueDate.setText("");
        etSelectSize.setText("");
        etMasterName.setText("");
        etTotalNumberPieces.setText("");
        etAlterQuantity.setText("");
        selectedBrand = "";
        selectedDesignType = "";

        etAlterQuantity.setText("");
        etBrandName.requestFocus();

        llBBabyNB912Parent.setVisibility(View.GONE);
        llBBabyS3XLParent.setVisibility(View.GONE);
        llKidsMagicGT.setVisibility(View.GONE);
        llKidsMagicMN.setVisibility(View.GONE);
        llKidsMagicNumeric.setVisibility(View.GONE);
        llKMS.setVisibility(View.GONE);
        llKMSGirls.setVisibility(View.GONE);
    }

    private void sendAlterRequestDetails() {
        mDialog = Helper.showProgressDialog(getActivity());

        SimpleDateFormat format = new SimpleDateFormat("d");
        format = new SimpleDateFormat("d MMM");
        final String currentDate = format.format(new Date());

        String jobCardCreationDate = tvDateOrderCreation.getText().toString();
        String brandName = etBrandName.getText().toString();
        String designCode = etDesignCode.getText().toString();
        String designNumber = etDesignNumber.getText().toString();
        String selectedParts = etParts.getText().toString();
        String otherParts = etOtherParts.getText().toString();
        String alterQuantity = etAlterQuantity.getText().toString();
        String masterName = etMasterName.getText().toString();
        String totalPieces = etTotalNumberPieces.getText().toString();
        String cuttingIssueDate = etCuttingIssueDate.getText().toString();

        sendSizeDetails();

        alterRequestItem.setAlterId(String.valueOf(maxId + 1));
        alterRequestItem.setAlterRequestCreationDate(jobCardCreationDate);
        alterRequestItem.setBrandName(brandName);
        alterRequestItem.setDesignCode(designCode);
        alterRequestItem.setDesignNumber(designNumber);
        alterRequestItem.setSelectedParts(selectedParts);
        alterRequestItem.setOtherParts(otherParts);
        alterRequestItem.setAlterQuantity(alterQuantity);
        alterRequestItem.setMaster(masterName);
        alterRequestItem.setAlterRequestDate(currentDate);
        alterRequestItem.setTotalPieces(totalPieces);
        alterRequestItem.setCuttingIssueDate(cuttingIssueDate);
        alterRequestItem.setSizeListItem(sizeListItem);

        alterRequestRef.child(String.valueOf(maxId + 1)).setValue(alterRequestItem,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        mDialog.dismiss();
                        Helper.showOkDialog(getActivity(), getString(R.string.alter_request_created_successfully));
                        clearData();
                    }
                });
    }
}