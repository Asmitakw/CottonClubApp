package com.cottonclub.fragments.ui.view_job_card;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewJobCardModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ViewJobCardModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}