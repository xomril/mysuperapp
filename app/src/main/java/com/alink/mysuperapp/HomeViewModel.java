package com.alink.mysuperapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> timeRemaining = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getTimeRemaining(){
        return timeRemaining;
    }

    public void setTimeRemaining(long timeRemaining){

        if ((int)timeRemaining==-1){
            this.timeRemaining.setValue(getApplication().getString(R.string.time_is_up));
        }
        this.timeRemaining.setValue(String.valueOf(timeRemaining));

    }
}