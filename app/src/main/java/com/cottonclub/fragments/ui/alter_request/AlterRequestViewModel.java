package com.cottonclub.fragments.ui.alter_request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlterRequestViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AlterRequestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}