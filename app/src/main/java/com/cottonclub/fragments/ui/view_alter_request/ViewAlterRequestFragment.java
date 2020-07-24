package com.cottonclub.fragments.ui.view_alter_request;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cottonclub.R;
import com.cottonclub.fragments.ui.view_job_card.ViewJobCardModel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


public class ViewAlterRequestFragment extends Fragment {

    private ViewJobCardModel viewJobCardModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewJobCardModel =
                ViewModelProviders.of(this).get(ViewJobCardModel.class);
        View root = inflater.inflate(R.layout.fragment_view_order, container, false);

        return root;
    }
}