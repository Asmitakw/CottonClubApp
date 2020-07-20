package com.cottonclub.activities.ui.create_order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateOrderModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CreateOrderModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}