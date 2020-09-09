package com.cottonclub.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.cottonclub.R;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cottonclub.interfaces.DialogListener;
import com.cottonclub.utilities.AppSession;
import com.cottonclub.utilities.Constants;
import com.cottonclub.utilities.Helper;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView tvLoggedInAs;
    public Menu customizedMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_create_order, R.id.nav_job_card,
                R.id.nav_alter_request, R.id.nav_view_order, R.id.nav_view_job_card)
                .setDrawerLayout(drawer)
                .build();
        View header = navigationView.getHeaderView(0);
        tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);

        Menu menu = navigationView.getMenu();
        MenuItem nav_logout = menu.findItem(R.id.nav_logout);
        MenuItem create_item = menu.findItem(R.id.create_item);
        MenuItem view_item = menu.findItem(R.id.view_item);
        MenuItem cutting_in_charge_view_item = menu.findItem(R.id.cutting_in_charge_view_item);
        MenuItem production_manager_view_item = menu.findItem(R.id.production_manager_view_item);
        MenuItem receiving_in_charge_view_item = menu.findItem(R.id.receiving_in_charge_view_item);
        MenuItem finishing_in_charge_view_item = menu.findItem(R.id.finishing_in_charge_view_item);

        if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.ADMIN)) {
            //Admin Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.admin)));
            cutting_in_charge_view_item.setVisible(false);

        } else if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.CUTTING_IN_CHARGE_KM)) {
            tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);
            //Cutting In-charge Kids Magic Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.cutting_in_charge_km)));
            create_item.setVisible(false);
            view_item.setVisible(false);
            cutting_in_charge_view_item.setVisible(true);

        } else if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.CUTTING_IN_CHARGE_BB)) {
            tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);
            //Cutting In-charge Be Baby Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.cutting_in_charge_bb)));
            create_item.setVisible(false);
            view_item.setVisible(false);
            cutting_in_charge_view_item.setVisible(true);

        } else if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.CUTTING_IN_CHARGE_CB)) {
            tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);
            //Cutting In-charge Cotton Blue Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.cutting_in_charge_cb)));
            create_item.setVisible(false);
            view_item.setVisible(false);
            cutting_in_charge_view_item.setVisible(true);

        } else if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.PRODUCTION_MANAGER_KM)) {
            tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);
            //Cutting In-charge Cotton Blue Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.production_manager_km)));
            create_item.setVisible(false);
            view_item.setVisible(false);
            cutting_in_charge_view_item.setVisible(false);
            production_manager_view_item.setVisible(true);
        } else if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.PRODUCTION_MANAGER_BB)) {
            tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);
            //Cutting In-charge Cotton Blue Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.production_manager_bb)));
            create_item.setVisible(false);
            view_item.setVisible(false);
            cutting_in_charge_view_item.setVisible(false);
            production_manager_view_item.setVisible(true);
        } else if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.PRODUCTION_MANAGER_CB)) {
            tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);
            //Cutting In-charge Cotton Blue Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.production_manager_cb)));
            create_item.setVisible(false);
            view_item.setVisible(false);
            cutting_in_charge_view_item.setVisible(false);
            production_manager_view_item.setVisible(true);
        } else if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.RECEIVING_IN_CHARGE_KM)) {
            tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);
            //Cutting In-charge Cotton Blue Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.receiving_in_charge_km)));
            create_item.setVisible(false);
            view_item.setVisible(false);
            cutting_in_charge_view_item.setVisible(false);
            production_manager_view_item.setVisible(false);
            receiving_in_charge_view_item.setVisible(true);
        } else if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.RECEIVING_IN_CHARGE_BB)) {
            tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);
            //Cutting In-charge Cotton Blue Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.production_manager_cb)));
            create_item.setVisible(false);
            view_item.setVisible(false);
            cutting_in_charge_view_item.setVisible(false);
            production_manager_view_item.setVisible(true);
        } else if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.RECEIVING_IN_CHARGE_CB)) {
            tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);
            //Cutting In-charge Cotton Blue Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.production_manager_cb)));
            create_item.setVisible(false);
            view_item.setVisible(false);
            cutting_in_charge_view_item.setVisible(false);
            production_manager_view_item.setVisible(false);
            receiving_in_charge_view_item.setVisible(true);
        } else if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.FINISHING_IN_CHARGE_KM)) {
            tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);
            //Cutting In-charge Cotton Blue Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.finishing_in_charge_km)));
            create_item.setVisible(false);
            view_item.setVisible(false);
            cutting_in_charge_view_item.setVisible(false);
            production_manager_view_item.setVisible(false);
            finishing_in_charge_view_item.setVisible(true);
        } else if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.FINISHING_IN_CHARGE_BB)) {
            tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);
            //Cutting In-charge Cotton Blue Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.finishing_in_charge_bb)));
            create_item.setVisible(false);
            view_item.setVisible(false);
            cutting_in_charge_view_item.setVisible(false);
            production_manager_view_item.setVisible(false);
            finishing_in_charge_view_item.setVisible(true);
        } else if (AppSession.getInstance().getSaveLoggedInUser(BaseActivity.this).equals(Constants.FINISHING_IN_CHARGE_CB)) {
            tvLoggedInAs = header.findViewById(R.id.tvLoggedInAs);
            //Cutting In-charge Cotton Blue Login
            tvLoggedInAs.setText(String.format("%s", getString(R.string.logged_in_as)
                    + getString(R.string.finishing_in_charge_cb)));
            create_item.setVisible(false);

            view_item.setVisible(false);
            cutting_in_charge_view_item.setVisible(false);
            production_manager_view_item.setVisible(false);
            finishing_in_charge_view_item.setVisible(true);
        }

        nav_logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Helper.showOkCancelDialog(BaseActivity.this,
                        getString(R.string.do_want_to_logout_from_application),
                        getString(R.string.yes), getString(R.string.no), new DialogListener() {
                            @Override
                            public void onButtonClicked(int type) {
                                if (Dialog.BUTTON_POSITIVE == type) {
                                    //AppSession.getInstance().clearSharedPreference(BaseActivity.this);
                                    //AppSession.getInstance().saveLoginStatus(BaseActivity.this, false);
                                    Intent mainIntent = new Intent(BaseActivity.this, LoginActivity.class);
                                    startActivity(mainIntent);
                                    //overridePendingTransition(R.anim.fade_in_act, R.anim.fade_out_act);
                                    finish();
                                }
                            }
                        });
                return false;
            }
        });


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
