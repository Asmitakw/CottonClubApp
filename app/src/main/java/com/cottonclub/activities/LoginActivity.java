package com.cottonclub.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cottonclub.R;
import com.cottonclub.utilities.AppSession;
import com.cottonclub.utilities.Constants;
import com.cottonclub.utilities.Helper;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialise();
    }

    private void initialise() {
        etUserId = findViewById(R.id.etUserId);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etUserId.getText().toString().trim())) {
                    Helper.showOkDialog(LoginActivity.this, getString(R.string.please_enter_user_id));
                    etUserId.requestFocus();
                    return;
                }
                if (etUserId.getText().toString().equals(Constants.ADMIN)
                        || etUserId.getText().toString().equals(Constants.CUTTING_IN_CHARGE_KM)
                        || etUserId.getText().toString().equals(Constants.CUTTING_IN_CHARGE_BB)
                        || etUserId.getText().toString().equals(Constants.CUTTING_IN_CHARGE_CB)
                        || etUserId.getText().toString().equals(Constants.PRODUCTION_MANAGER_KM)
                        || etUserId.getText().toString().equals(Constants.PRODUCTION_MANAGER_BB)
                        || etUserId.getText().toString().equals(Constants.PRODUCTION_MANAGER_CB)
                        || etUserId.getText().toString().equals(Constants.FINISHING_IN_CHARGE_KM)
                        || etUserId.getText().toString().equals(Constants.FINISHING_IN_CHARGE_BB)
                        || etUserId.getText().toString().equals(Constants.FINISHING_IN_CHARGE_CB)
                ) {
                    Intent mainIntent = new Intent(LoginActivity.this, BaseActivity.class);
                    startActivity(mainIntent);
                    AppSession.getInstance().setSaveLoggedInUser(LoginActivity.this, etUserId.getText().toString());
                    finish();
                    AppSession.getInstance().saveLoginStatus(LoginActivity.this, true);

                } else {
                    Helper.showOkDialog(LoginActivity.this, getString(R.string.please_enter_valid_user_id));
                }
            }
        });

    }
}
