package com.cottonclub.activities.ui.create_order;

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
import com.cottonclub.models.OrderItem;
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


public class CreateOrderFragment extends Fragment implements View.OnClickListener {

    private EditText etPartyName, etBrandName, etDesignNumber, etOrderNumber,
            etDeliveryDate, etSelectSize;

    private TextView tvDateOrderCreation;

    private Button btnCreateOrder;
    private long maxId = 0;
    private OrderItem orderItem;
    private String[] brandArray;
    private String selectedBrand;
    private String[] kidsMagicSizesArray;
    private String[] bbabySizesArray;
    private String[] cottonBlueArray;
    private Dialog mDialog;
    private DatePickerDialog datePickerDialog;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference orderRef = mRootRef.child("Orders");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_order, container, false);
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
        etPartyName = view.findViewById(R.id.etPartyName);
        etBrandName = view.findViewById(R.id.etBrandName);
        etBrandName.setOnClickListener(this);
        etDesignNumber = view.findViewById(R.id.etDesignNumber);
        etOrderNumber = view.findViewById(R.id.etOrderNumber);
        etDeliveryDate = view.findViewById(R.id.etDeliveryDate);
        etDeliveryDate.setOnClickListener(this);
        etSelectSize = view.findViewById(R.id.etSelectSize);
        etSelectSize.setOnClickListener(this);
        btnCreateOrder = view.findViewById(R.id.btnCreateOrder);
        orderItem = new OrderItem();

    }

    @Override
    public void onStart() {
        super.onStart();

        btnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        orderRef.addValueEventListener(new ValueEventListener() {
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
        if (TextUtils.isEmpty(etPartyName.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_party_name));
            etPartyName.requestFocus();
            return;
        }

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

        if (TextUtils.isEmpty(etOrderNumber.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_enter_order_number));
            etOrderNumber.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etDeliveryDate.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_select_delivery_date));
            etDeliveryDate.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(etSelectSize.getText().toString().trim())) {
            Helper.showOkDialog(getActivity(), getString(R.string.please_select_size));
            etSelectSize.requestFocus();
            return;
        }

        sendOrderDetails();
    }

    private void sendOrderDetails() {
        mDialog = Helper.showProgressDialog(getActivity());
        String orderCreationDate = tvDateOrderCreation.getText().toString();
        String partyName = etPartyName.getText().toString();
        String brandName = etBrandName.getText().toString();
        String designNumber = etDesignNumber.getText().toString();
        String orderNumber = etOrderNumber.getText().toString();
        String deliveryDate = etDeliveryDate.getText().toString();
        String selectSize = etSelectSize.getText().toString();

        orderItem.setOrderCreationDate(orderCreationDate);
        orderItem.setPartyName(partyName);
        orderItem.setBrandName(brandName);
        orderItem.setDesignNumber(designNumber);
        orderItem.setOrderNumber(orderNumber);
        orderItem.setDeliveryDate(deliveryDate);
        orderItem.setSelectSize(selectSize);

        orderRef.child(String.valueOf(maxId + 1)).setValue(orderItem, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                mDialog.dismiss();
                Helper.showOkDialog(getActivity(), getString(R.string.order_created_successfully));
                etPartyName.setText("");
                etBrandName.setText("");
                etDesignNumber.setText("");
                etOrderNumber.setText("");
                etDeliveryDate.setText("");
                etSelectSize.setText("");
                etPartyName.requestFocus();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.etBrandName:
                Helper.showDropDown(etBrandName, new ArrayAdapter<>(getActivity(),
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

            case R.id.etDeliveryDate:
                final Calendar c = Calendar.getInstance();
                int year, month, day;
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                etDeliveryDate.setText(dayOfMonth + "/" + (monthOfYear + 1)
                                        + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System
                        .currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;

            case R.id.etSelectSize:
                if (selectedBrand.equals("KidsMagic")) {
                    //Kids Magic
                    Helper.showDropDown(etSelectSize, new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_list_item_1, kidsMagicSizesArray), new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            etSelectSize.setText(kidsMagicSizesArray[position]);
                        }
                    });
                } else if (selectedBrand.equals("BBaby")) {
                    //BBbay
                    Helper.showDropDown(etSelectSize, new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_list_item_1, bbabySizesArray), new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            etSelectSize.setText(bbabySizesArray[position]);
                        }
                    });
                } else {
                    //Cotton Blue
                    Helper.showDropDown(etSelectSize, new ArrayAdapter<>(getActivity(),
                            android.R.layout.simple_list_item_1, cottonBlueArray), new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            etSelectSize.setText(cottonBlueArray[position]);
                        }
                    });
                }

                break;
        }
    }

}