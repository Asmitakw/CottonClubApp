package com.cottonclub.fragments.ui;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
    private RequestQueue requestQueue;
    private String firebaseURl = "https://fcm.googleapis.com/fcm/send";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btnSendNotification = root.findViewById(R.id.btnSendNotification);
        requestQueue = Volley.newRequestQueue(getActivity());
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });
        return root;
    }

    private void sendNotification() {
        JSONObject mainObject = new JSONObject();
        try {
            mainObject.put("to", "/topics/" + "news");
            JSONObject notificationObject = new JSONObject();
            notificationObject.put("title", "Cotton Club");
            notificationObject.put("body", "any body");
            mainObject.put("notification", notificationObject);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, firebaseURl,
                    mainObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=AAAA0How4PA:APA91bExSt4jGOv5jIdb0OR0P2lv13qVZHN6Wo_TqwUdKLcs_rL_a8taAe5rBCfE1Ko3GrN6F9trOBYCxXWByYhjkbXb2b8Ta9VmTOcg2bRBaBrgKvHOfPTlgnEQz99R9uf85b_OFug1");
                    return header;
                }
            };

            requestQueue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
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


}