package com.cottonclub.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cottonclub.R;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startSplash();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void startSplash() {

        // updateAppLanguage(AppSession.getInstance().getLanguage(SplashActivity.this));

        Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(mainIntent);
        //  overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
        finish();

        /*if (AppSession.getInstance().getLoginStatus(SplashActivity.this)) {
            Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
            finish();
        } else {
            Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
            finish();
        }*/

    }
}
