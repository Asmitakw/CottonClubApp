package com.cottonclub.fragments.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cottonclub.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        return root;
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