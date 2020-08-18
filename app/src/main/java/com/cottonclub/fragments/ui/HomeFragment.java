package com.cottonclub.fragments.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cottonclub.R;
import com.cottonclub.utilities.AppSession;
import com.cottonclub.utilities.Helper;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class HomeFragment extends Fragment {

    private Button btnSendNotification;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*btnSendNotification = root.findViewById(R.id.btnSendNotification);

        btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/
        return root;
    }



    }

    /*@Override
    public void onBackPressed() {
        Helper.showOkCancelDialog(getActivity(), getString(R.string.do_you_want_to_exit_application),
                getString(R.string.yes), getString(R.string.no), new DialogListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onButtonClicked(int type) {
                        if (Dialog.BUTTON_POSITIVE == type) {
                            getActivity().finishAffinity();
                        }
                    }
                });
    }*/
