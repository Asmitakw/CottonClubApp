package com.cottonclub.activities.ui.create_job_card;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JobCardModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JobCardModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}