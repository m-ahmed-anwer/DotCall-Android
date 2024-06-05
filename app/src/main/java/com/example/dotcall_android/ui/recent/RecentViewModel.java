package com.example.dotcall_android.ui.recent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecentViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RecentViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Recents fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}