package com.cottonclub.activities.production_manager_details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cottonclub.R;
import com.google.android.material.textfield.TextInputLayout;

public class CreateProductionManagerRequest extends AppCompatActivity {

    EditText etEmployeeName;
    TextInputLayout tlNumbering;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_production_manager_request);
        tlNumbering = findViewById(R.id.tlNumbering);
        etEmployeeName = findViewById(R.id.etEmployeeName);
        etEmployeeName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                tlNumbering.setHint(getString(R.string.numbering));
                etEmployeeName.setHint(getString(R.string.please_enter_employee_name));
            }
        });
    }
}