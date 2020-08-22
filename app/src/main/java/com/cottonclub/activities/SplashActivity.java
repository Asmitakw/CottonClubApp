package com.cottonclub.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cottonclub.R;
import com.cottonclub.utilities.AppSession;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startSplash();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(SplashActivity.this, getString(R.string.permission_denied)
                        + deniedPermissions.toString(), Toast.LENGTH_SHORT)
                        .show();
            }

        };

        TedPermission.with(SplashActivity.this)
                .setPermissionListener(permissionlistener)
                .setRationaleTitle(R.string.app_name)
                .setRationaleMessage(getString(R.string.run_without_interruption))
                .setDeniedTitle(getString(R.string.permission_denied))
                .setDeniedMessage(getString(R.string.if_you_reject_permission))
                .setGotoSettingButtonText(getString(R.string.go_to_setting))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();

    }

    private void startSplash() {

        if (AppSession.getInstance().getLoginStatus(SplashActivity.this)) {
            Intent mainIntent = new Intent(SplashActivity.this, BaseActivity.class);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
            finish();
        } else {
            Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
            finish();
        }

    }
}
